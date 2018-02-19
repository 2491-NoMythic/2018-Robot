package com._2491nomythic.tempest.commands.cubestorage;

import com._2491nomythic.tempest.commands.CommandBase;
import edu.wpi.first.wpilibj.Timer;

/**
 * Runs the cube storage motors until the ultrasonic is activated.
 */
public class UltrasonicCubeHalt extends CommandBase {
	boolean doWeNeedToWorryAboutStoppingOrIsTheUltrasonicSensorAlreadyCovered, nowStopRunningTheMotorsPlease, wouldYouKindlyRunTheAccelerateMotorsBackwardsToPreventThePowerCubeFromGoingTooFar;
	private double speed, time;
	private Timer timer;

	public UltrasonicCubeHalt(double power, double time) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		this.time = time;
		requires(cubeStorage);
		speed = power;
		timer = new Timer();
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		timer.start();
		doWeNeedToWorryAboutStoppingOrIsTheUltrasonicSensorAlreadyCovered = cubeStorage.isHeld();
		nowStopRunningTheMotorsPlease = false;
		wouldYouKindlyRunTheAccelerateMotorsBackwardsToPreventThePowerCubeFromGoingTooFar = false;
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if (!nowStopRunningTheMotorsPlease) {
			cubeStorage.run(speed);
			if (cubeStorage.isHeld()) {
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
		return timer.get() > time;
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
