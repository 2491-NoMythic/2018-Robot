package com._2491nomythic.tempest.commands.shooter;

import com._2491nomythic.tempest.commands.CommandBase;
import com._2491nomythic.tempest.settings.Variables;

/**
 *Reverses shooter direction using an operator button
 */
public class ReverseShooter extends CommandBase {

    /**
     * Reverses shooter direction using an operator button
     */
	public ReverseShooter() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
		requires(shooter);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	shooter.run(-1 * Variables.shooterSpeed);
    	if (!oi.cubeStorageControl1.get() && !oi.cubeStorageControl2.get()) {
    		cubeStorage.runWithoutRoller(1);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	shooter.stop();
    	cubeStorage.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
