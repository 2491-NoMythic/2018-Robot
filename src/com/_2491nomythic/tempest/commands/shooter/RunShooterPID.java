package com._2491nomythic.tempest.commands.shooter;

import com._2491nomythic.tempest.commands.CommandBase;
import com._2491nomythic.tempest.settings.Variables;

/**
 *
 */
public class RunShooterPID extends CommandBase {
	private RunLeftShootPID runLeft;
	private RunRightShootPID runRight;

    public RunShooterPID() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(shooter);
    	runLeft = new RunLeftShootPID();
    	runRight = new RunRightShootPID();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	runLeft.start();
    	runRight.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
		if (Variables.leftShootReady && Variables.rightShootReady) {
			Variables.readyToFire = true;
		}
		else {
			Variables.readyToFire = false;
		}
    	shooter.runAccelerate(Variables.shooterSpeed);
    	runLeft.setSetPoint(Variables.shooterRPS);
    	runRight.setSetPoint(Variables.shooterRPS);
    	runLeft.setF(Variables.leftShootFeedForward);
    	runRight.setF(Variables.rightShootFeedForward);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	shooter.stop();
    	runLeft.cancel();
    	runRight.cancel();
    	Variables.readyToFire = false;
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
