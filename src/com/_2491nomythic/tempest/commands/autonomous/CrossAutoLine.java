package com._2491nomythic.tempest.commands.autonomous;

import com._2491nomythic.tempest.commands.CommandBase;
import com._2491nomythic.tempest.commands.drivetrain.DriveStraightToPosition;
import com._2491nomythic.tempest.settings.Variables;

import edu.wpi.first.wpilibj.Timer;

/**
 * Attempts to cross the AutoLine during autonomous.
 * @deprecated
 */
public class CrossAutoLine extends CommandBase {
	DriveStraightToPosition crossLine;
	Timer timer, delay;

	/**
	 * Attempts to cross the AutoLine during autonomous.
	 * @deprecated
	 */
	public CrossAutoLine() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);		
		timer = new Timer();
		delay = new Timer();
		crossLine = new DriveStraightToPosition(0.5, 130);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		delay.start();
		System.out.println(Variables.autoDelay);
		
		while(delay.get() < Variables.autoDelay) {
			
		}
		
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
