package com._2491nomythic.robot.commands.shooter;

import com._2491nomythic.robot.commands.CommandBase;

/**
 *Runs the shooter system at a specified speed
 */
public class RunShooter extends CommandBase {
	public double speed;

	/**
	 * Runs the shooter system at a specified speed
	 * @param speed the speed at which to set the motors.
	 */
    public RunShooter(double speed) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(shooter);
    	this.speed = speed;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	shooter.run(speed);
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
    	shooter.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	shooter.stop();
    }
}
