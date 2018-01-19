package com._2491nomythic.robot.commands.autonomous;

import com._2491nomythic.robot.commands.CommandBase;
import com._2491nomythic.robot.commands.drivetrain.DriveStraightToPosition;

import edu.wpi.first.wpilibj.Timer;

/**
 * Attempts to cross the AutoLine during autonomous.
 */
public class CrossAutoLine extends CommandBase {
	DriveStraightToPosition crossLine;
	Timer timer;

	/**
	 * Attempts to cross the AutoLine during autonomous.
	 */
    public CrossAutoLine() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(drivetrain);
    	
    	timer = new Timer();
    	crossLine = new DriveStraightToPosition(1, 130);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	crossLine.start();
    	timer.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return !crossLine.isRunning() && timer.get() > 1;
    }

    // Called once after isFinished returns true
    protected void end() {
    	drivetrain.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
