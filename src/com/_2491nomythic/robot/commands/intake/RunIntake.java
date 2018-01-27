package com._2491nomythic.robot.commands.intake;

import com._2491nomythic.robot.commands.CommandBase;

/**
 *Runs the intake system.
 */
public class RunIntake extends CommandBase {
	public double speed;
	

	/**
	 * Runs the intake system.
	 * @param speed the speed at which to set the motors.
	 */
    public RunIntake(double speed) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(intake);
    	this.speed = speed;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	intake.run(speed);
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
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
