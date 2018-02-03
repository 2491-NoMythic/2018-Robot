package com._2491nomythic.robot.commands.cubestorage;

import com._2491nomythic.robot.commands.CommandBase;

/**
 *Feeds cube from cube storage to shooter.
 */
public class FeedCube extends CommandBase {

    /**
     * Feeds cube from cube storage to shooter.
     */
	public FeedCube() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(cubeStorage);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	cubeStorage.run(1);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    	cubeStorage.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
