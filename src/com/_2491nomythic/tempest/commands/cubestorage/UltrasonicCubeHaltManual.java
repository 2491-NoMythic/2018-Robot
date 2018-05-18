package com._2491nomythic.tempest.commands.cubestorage;

import com._2491nomythic.tempest.commands.CommandBase;
import com._2491nomythic.tempest.settings.ControllerMap;

/**
 * Runs the cube storage motors until the ultrasonic is activated.
 */
public class UltrasonicCubeHaltManual extends CommandBase {
	boolean doWeNeedToWorryAboutStoppingOrIsTheUltrasonicSensorAlreadyCovered, nowStopRunningTheMotorsPlease;
	
	/**
	 * Runs the cube storage motors until the ultrasonic is activated.
	 */
	public UltrasonicCubeHaltManual() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(cubeStorage);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		doWeNeedToWorryAboutStoppingOrIsTheUltrasonicSensorAlreadyCovered = cubeStorage.isHeld();
		nowStopRunningTheMotorsPlease = false;
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {		
			if (doWeNeedToWorryAboutStoppingOrIsTheUltrasonicSensorAlreadyCovered) {
				cubeStorage.run(oi.getAxisDeadzonedSquared(ControllerMap.operatorController, ControllerMap.cubeStorageAxis, 0.1));
			}
			else {
				if (!nowStopRunningTheMotorsPlease) {
					if (cubeStorage.isHeld()) {
						nowStopRunningTheMotorsPlease = true;
						cubeStorage.stop();
					}
					else {
						cubeStorage.run(oi.getAxisDeadzonedSquared(ControllerMap.operatorController, ControllerMap.cubeStorageAxis, 0.1));
					}
				}
			}
		
			if (!oi.runShooter.get() && !(oi.getAxisDeadzonedSquared(ControllerMap.operatorController, ControllerMap.cubeStorageAxis, 0.05) == 0)) {
				shooter.runAccelerate(-0.3);
			}
			else if (!oi.runShooter.get()) { 
				shooter.runAccelerate(0);
			}
		
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		cubeStorage.stop();
		shooter.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
