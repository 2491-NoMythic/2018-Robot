package com._2491nomythic.tempest.commands.intake;

import com._2491nomythic.tempest.commands.CommandBase;
import com._2491nomythic.tempest.settings.ControllerMap;

/**
 *Run intake system manually using the axis of operator controller.
 */
public class RunIntakeManual extends CommandBase {

	/**
	 * Run intake system manually using the axis of operator controller.
	 */
	public RunIntakeManual() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(intake);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		intake.run(oi.getAxisDeadzonedSquared(ControllerMap.driveController, ControllerMap.intakeAxis, 0.05));
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
		end();
	}
}
