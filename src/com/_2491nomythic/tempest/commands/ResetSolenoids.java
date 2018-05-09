package com._2491nomythic.tempest.commands;

/**
 *Resets the solenoids to their starting position
 */
public class ResetSolenoids extends CommandBase {
	
	/**
	 * Resets the solenoids to their starting position
	 */

    public ResetSolenoids() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(shooter);
    	requires(intake);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	System.out.println("Test");
    	shooter.setScalePosition();
    	intake.retractArms();
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
