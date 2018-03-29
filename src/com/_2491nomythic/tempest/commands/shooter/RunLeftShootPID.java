package com._2491nomythic.tempest.commands.shooter;

import com._2491nomythic.tempest.commands.CommandBase;
import com._2491nomythic.tempest.settings.Variables;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

/**
 * Runs the left shoot motor PID loop
 * @deprecated
 */
public class RunLeftShootPID extends CommandBase {
	private PIDController leftShootControl;

	/**
	 * Runs the left shoot motor PID loop
	 * @deprecated
	 */
    public RunLeftShootPID() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	leftShootControl = new PIDController(Variables.leftShootProportional, Variables.leftShootIntegral, Variables.leftShootDerivative, Variables.shootFeedForward, new PIDSource() {
    		PIDSourceType leftShootSource = PIDSourceType.kRate;
    		
    		@Override
    		public double pidGet() {
    			return shooter.getLeftShootVelocity();
    		}
    		
    		@Override 
    		public void setPIDSourceType(PIDSourceType pidSource) {
    			leftShootSource = pidSource;
    		}
    		
    		@Override
    		public PIDSourceType getPIDSourceType() {
    			return leftShootSource;
    		}
    	},
    	new PIDOutput() {

			@Override
			public void pidWrite(double output) {
				shooter.runLeftShoot(output);
			};
    	}
    	);
    	leftShootControl.setAbsoluteTolerance(1);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	leftShootControl.setSetpoint(Variables.shooterRPS);
    	leftShootControl.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	leftShootControl.setSetpoint(Variables.shooterRPS);
    	leftShootControl.setF(Variables.shootFeedForward);
    	if (leftShootControl.onTarget()) {
    		Variables.leftShootReady = true;
    	}
    	else {
    		Variables.leftShootReady = false;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	leftShootControl.disable();
    	shooter.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
    
    public void setF(double feedForward) {
    	leftShootControl.setF(feedForward);
    }
    
    public void setSetPoint(double setPoint) {
    	leftShootControl.setSetpoint(setPoint);
    }
}
