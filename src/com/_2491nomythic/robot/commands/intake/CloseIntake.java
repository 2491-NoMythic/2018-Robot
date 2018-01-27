package com._2491nomythic.robot.commands.intake;

import com._2491nomythic.robot.commands.CommandBase;

/**
 * Retracts the arms of the intake using pneumatics
 */
public class CloseIntake extends CommandBase {

	/**
	 * Retracts the arms of the intake using pneumatics
	 */
    public CloseIntake() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(intake);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	intake.close();
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
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
