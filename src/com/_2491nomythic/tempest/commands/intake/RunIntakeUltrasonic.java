package com._2491nomythic.tempest.commands.intake;

import com._2491nomythic.tempest.commands.CommandBase;

/**
 * Runs intake motors
 */
public class RunIntakeUltrasonic extends CommandBase {
	private double power;

	/**
	 * Runs intake motors
	 * @param desiredPower The power fed to the intake motors
	 */
	public RunIntakeUltrasonic(double desiredPower) {
		requires(intake);
		power = desiredPower;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		intake.run(power);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return cubeStorage.isHeld();
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
