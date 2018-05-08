package com._2491nomythic.tempest.commands.cubestorage;

import com._2491nomythic.tempest.commands.CommandBase;
import edu.wpi.first.wpilibj.Timer;

/**
 * Runs the cube storage motors until the ultrasonic is activated.
 */
public class UltrasonicCubeHalt extends CommandBase {
	boolean doWeNeedToWorryAboutStoppingOrIsTheUltrasonicSensorAlreadyCovered, nowStopRunningTheMotorsPlease, wouldYouKindlyRunTheAccelerateMotorsBackwardsToPreventThePowerCubeFromGoingTooFar;
	private double speed;
	private Timer timer;
	
	/**
	 * Runs the cube storage motors until the ultrasonic is activated.
	 * @param power the power at which the cube storage motors run
	 */

	public UltrasonicCubeHalt(double power) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(cubeStorage);
		speed = power;
		timer = new Timer();
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		doWeNeedToWorryAboutStoppingOrIsTheUltrasonicSensorAlreadyCovered = cubeStorage.isHeld();
		nowStopRunningTheMotorsPlease = false;
		wouldYouKindlyRunTheAccelerateMotorsBackwardsToPreventThePowerCubeFromGoingTooFar = false;
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if (!nowStopRunningTheMotorsPlease) {
			cubeStorage.run(speed);
			if (cubeStorage.isHeld()) {
				timer.start();
				nowStopRunningTheMotorsPlease = true;
				wouldYouKindlyRunTheAccelerateMotorsBackwardsToPreventThePowerCubeFromGoingTooFar = true;
				cubeStorage.stop();
			}
			else {
				cubeStorage.run(speed);
			}
		}
		if (wouldYouKindlyRunTheAccelerateMotorsBackwardsToPreventThePowerCubeFromGoingTooFar) {
			shooter.runAccelerate(-0.2);
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return timer.get() > 2;
	}

	// Called once after isFinished returns true
	protected void end() {
		nowStopRunningTheMotorsPlease = false;
		wouldYouKindlyRunTheAccelerateMotorsBackwardsToPreventThePowerCubeFromGoingTooFar = false;
		cubeStorage.stop();
		shooter.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
