package com._2491nomythic.tempest.commands.shooter;

import com._2491nomythic.tempest.commands.CommandBase;
import com._2491nomythic.tempest.settings.Constants;
import com._2491nomythic.tempest.settings.Variables;
import com._2491nomythic.util.ShooterController;

/**
 *
 */
public class RunShooterCustom extends CommandBase {
	private ShooterController shootControl;

    public RunShooterCustom() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(shooter);
    	shootControl = new ShooterController(shooter, Constants.shootCLeft, Constants.shootCRight, Constants.shootI1Left, Constants.shootI1Right, Constants.shootI2Left, Constants.shootI2Right, Variables.shooterSpeed);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	shootControl.setTolerance(.5);
    	shootControl.setSetPoint(Variables.shooterRPS);
    	shootControl.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	shooter.runAccelerate(Variables.shooterSpeed);
    	shootControl.setF(Variables.shooterSpeed);
    	shootControl.setSetPoint(Variables.shooterRPS);
    	Variables.readyToFire = shootControl.inSync();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	shootControl.disable();
    	shooter.stop();
    	Variables.readyToFire = false;
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
