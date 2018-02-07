package com._2491nomythic.robot.commands;

/**
 *Runs intake system and cube storage for set power.
 */
public class RunIntakeAndStorage extends CommandBase {
	private double speed;

	public RunIntakeAndStorage() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(cubeStorage);
    	requires(intake);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
		intake.run(speed);
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
    	intake.stop();
    	cubeStorage.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
