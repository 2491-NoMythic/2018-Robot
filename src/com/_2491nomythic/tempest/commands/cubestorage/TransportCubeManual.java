package com._2491nomythic.tempest.commands.cubestorage;

import com._2491nomythic.tempest.commands.CommandBase;
import com._2491nomythic.tempest.settings.ControllerMap;


/**
 *Allows the cube storage to be run manually by the codriver.
 */
public class TransportCubeManual extends CommandBase {
	private double currentSpeed;
	/**
	 *Allows the cube storage to be run manually by the codriver.
	 */
	public TransportCubeManual() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(cubeStorage);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		currentSpeed = -oi.getAxisDeadzonedSquared(ControllerMap.operatorController, ControllerMap.cubeStorageAxis, 0.05);
		cubeStorage.run(currentSpeed);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		cubeStorage.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
