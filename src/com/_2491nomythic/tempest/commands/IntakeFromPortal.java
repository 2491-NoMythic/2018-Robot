package com._2491nomythic.tempest.commands;

/**
 * Runs shooter motors backwards to intake a cube from the portal
 */
public class IntakeFromPortal extends CommandBase {

	/**
	 * Runs shooter motors backwards to intake a cube from the portal
	 */
	public IntakeFromPortal() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(shooter);
		requires(cubeStorage);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		shooter.run(-1);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return cubeStorage.isHeld();
	}

	// Called once after isFinished returns true
	protected void end() {
		shooter.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
