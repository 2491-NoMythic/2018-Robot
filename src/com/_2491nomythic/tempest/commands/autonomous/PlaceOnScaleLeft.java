package com._2491nomythic.tempest.commands.autonomous;

import com._2491nomythic.tempest.commands.AutomaticShoot;
import com._2491nomythic.tempest.commands.CommandBase;
import com._2491nomythic.tempest.commands.drivetrain.DriveStraightToPositionPID;
import com._2491nomythic.tempest.commands.drivetrain.RotateDrivetrainWithGyroPID;
import com._2491nomythic.tempest.settings.Variables;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;

/**
 * Attempts to place a cube on the correct side of the Scale during autonomous, starting in front of DriverStation 1.
 * @deprecated
 */
public class PlaceOnScaleLeft extends CommandBase {
	private DriveStraightToPositionPID driveToCenter, driveToNullZone, approachScale, driveToCorrectSide;
	private RotateDrivetrainWithGyroPID turnTowardsCenter, turnTowardsNullZone, turnTowardsScale;
	private AutomaticShoot launchCube;
	private Timer timer, delay;
	private int state;
	private boolean left;

	/**
	 * Attempts to place a cube on the correct side of the Scale during autonomous, starting in front of DriverStation 1.
	 * @deprecated
	 */
	public PlaceOnScaleLeft() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);		
		timer = new Timer();
		delay = new Timer();
		driveToCenter = new DriveStraightToPositionPID(235.4);
		approachScale = new DriveStraightToPositionPID(44);
		approachScale = new DriveStraightToPositionPID(10);
		driveToCorrectSide = new DriveStraightToPositionPID(218.63);
		turnTowardsCenter = new RotateDrivetrainWithGyroPID(90, false);
		turnTowardsNullZone = new RotateDrivetrainWithGyroPID(-90, false);
		launchCube = new AutomaticShoot(true);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		state = 0;
		
		String gameData = DriverStation.getInstance().getGameSpecificMessage();
		
		switch(gameData.substring(1, 2)) {
		case "L":
			left = true;
			driveToNullZone = new DriveStraightToPositionPID(323.6);
			turnTowardsScale = new RotateDrivetrainWithGyroPID(-90, false);
			break;
		case "R":
			driveToNullZone = new DriveStraightToPositionPID(88.2);
			turnTowardsScale = new RotateDrivetrainWithGyroPID(90, false);
			left = false;
			break;
		default:
			System.out.println("Unexpected GameSpecificMessage in PlaceOnScaleLeft. gameData: " + gameData);
			break;
		}
		
		delay.start();
		
		while(delay.get() < Variables.autoDelay) {
			
		}
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if(left) {
			switch(state) {
			case 0:
				driveToNullZone.start();
				state++;
				break;
			case 1:
				if(!driveToNullZone.isRunning()) {
					turnTowardsScale.start();
					timer.start();
					state++;
				}
				break;
			case 2:
				if(!turnTowardsScale.isRunning() || timer.get() > 1.5) {
					turnTowardsScale.cancel();
					timer.reset();
					approachScale.start();
					state++;
				}
				break;
			case 3:
				if(!approachScale.isRunning() || timer.get() > 2) {
					launchCube.start();
					state++;
				}
				break;
			case 4:
				break;
			default:
				System.out.println("Unexpected state in PlaceOnScaleLeft.java State: " + state);
				break;
			}
		}
		else {
			switch(state) {
			case 0:
				driveToCenter.start();
				state++;
				break;
			case 1:
				if(!driveToCenter.isRunning()) {
					turnTowardsCenter.start();
					timer.start();
					state++;
				}
				break;
			case 2:
				if(!turnTowardsCenter.isRunning() || timer.get() > 1.5) {
					turnTowardsCenter.cancel();
					driveToCorrectSide.start();
					state++;
				}
				break;
			case 3:
				if(!driveToCorrectSide.isRunning()) {
					turnTowardsNullZone.start();
					timer.reset();
					state++;
				}
				break;
			case 4:
				if(!turnTowardsNullZone.isRunning() || timer.get() > 1.5) {
					turnTowardsNullZone.cancel();
					driveToNullZone.start();
					state++;
				}
				break;
			case 5:
				if(!driveToNullZone.isRunning()) {
					turnTowardsScale.start();
					timer.reset();
					state++;
				}
				break;
			case 6:
				if(!turnTowardsScale.isRunning() || timer.get() > 1.5) {
					turnTowardsScale.cancel();
					approachScale.start();
					state++;
				}
				break;
			case 7:
				if(!approachScale.isRunning()) {
					//launchCube.start();
					state++;
				}
				break;
			case 8:
				break;
			default:
				System.out.println("Unexpected state in PlaceOnScaleLeft.java State: " + state);
				break;
			}
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		if(left) {
			return !launchCube.isRunning() && state == 4;
		}
		else {
			return !launchCube.isRunning() && state == 8;
		}
	}

	// Called once after isFinished returns true
	protected void end() {
		driveToCenter.cancel();
		driveToNullZone.cancel();
		approachScale.cancel();
		turnTowardsCenter.cancel();
		turnTowardsNullZone.cancel();
		turnTowardsScale.cancel();
		launchCube.cancel();
		
		drivetrain.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
