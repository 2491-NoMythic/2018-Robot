package com._2491nomythic.robot.commands.cubestorage;

import com._2491nomythic.robot.commands.CommandBase;

/**
 *Runs cube storage system.
 */
public class RunCubeStorage extends CommandBase {
	public double speed;

	/**
	 * Runs cube storage system.
	 * @param speed speed at which the cube storage system motors run.
	 */
    public RunCubeStorage(double speed) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	this.speed = speed;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	cubeStorage.run(speed);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	cubeStorage.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	cubeStorage.stop();
    }
}
