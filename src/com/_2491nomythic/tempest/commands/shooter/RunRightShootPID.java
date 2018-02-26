package com._2491nomythic.tempest.commands.shooter;

import com._2491nomythic.tempest.commands.CommandBase;
import com._2491nomythic.tempest.settings.Variables;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

/**
 *
 */
public class RunRightShootPID extends CommandBase {
	private PIDController rightShootControl;

    public RunRightShootPID() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	rightShootControl = new PIDController(Variables.rightShootProportional, Variables.rightShootIntegral, Variables.rightShootDerivative, (Math.sqrt((Variables.shooterRPS - 31) / 52.83)), new PIDSource() {
    		PIDSourceType rightShootSource = PIDSourceType.kRate;
    		
    		@Override
    		public double pidGet() {
    			return shooter.getRightShootVelocity();
    		}
    		
    		@Override 
    		public void setPIDSourceType(PIDSourceType pidSource) {
    			rightShootSource = pidSource;
    		}
    		
    		@Override
    		public PIDSourceType getPIDSourceType() {
    			return rightShootSource;
    		}
    	} ,
    	new PIDOutput() {

			@Override
			public void pidWrite(double output) {
				shooter.runRightShoot(output);
			};
    		
    	}
    	);
    	rightShootControl.setAbsoluteTolerance(1);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	rightShootControl.setSetpoint(Variables.shooterRPS);
    	rightShootControl.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	rightShootControl.setSetpoint(Variables.shooterRPS);
    	rightShootControl.setF(Math.sqrt((Variables.shooterRPS - 31) / 52.83));
    	if (rightShootControl.onTarget()) {
    		Variables.rightShootReady = true;
    	}
    	else {
    		Variables.rightShootReady = false;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	rightShootControl.disable();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
