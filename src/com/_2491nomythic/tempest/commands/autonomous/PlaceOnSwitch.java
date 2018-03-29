package com._2491nomythic.tempest.commands.autonomous;

import com._2491nomythic.tempest.commands.AutomaticShoot;
import com._2491nomythic.tempest.commands.CommandBase;
import com._2491nomythic.tempest.commands.drivetrain.DriveStraightToPositionPID;
import com._2491nomythic.tempest.commands.drivetrain.RotateDrivetrainWithGyroPID;
import com._2491nomythic.tempest.settings.Variables;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;

/**
 * Attempts to place a cube on the correct side of the switch during autonomous.
 * @deprecated
 */
public class PlaceOnSwitch extends CommandBase {
	private DriveStraightToPositionPID approachCubes, moveTowardsWall, approachSwitch;
	private RotateDrivetrainWithGyroPID turnTowardsWall, turnTowardsSwitch;
	private AutomaticShoot launchCube;
	private int state;
	private Timer timer, delay;
	
	/**
	 * Attempts to place a cube on the correct side of the switch during autonomous.
	 * @deprecated
	 */
	public PlaceOnSwitch() {
		//Use this command if the robot is in front of DriverStation 2.
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);		
		timer = new Timer();
		delay = new Timer();
		approachCubes = new DriveStraightToPositionPID(-42);
		approachSwitch = new DriveStraightToPositionPID(-50);
		launchCube = new AutomaticShoot(false);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		drivetrain.resetEncoders();
		state = 0;
		
		String gameData = DriverStation.getInstance().getGameSpecificMessage();
		
		switch(gameData.substring(0, 1)) {
		case "L":
			moveTowardsWall = new DriveStraightToPositionPID(-50);
			turnTowardsWall = new RotateDrivetrainWithGyroPID(-90, false);
			turnTowardsSwitch = new RotateDrivetrainWithGyroPID(90, false);
			break;
		case "R":
			moveTowardsWall = new DriveStraightToPositionPID(-46.5);
			turnTowardsWall = new RotateDrivetrainWithGyroPID(90, false);
			turnTowardsSwitch = new RotateDrivetrainWithGyroPID(-90, false);
			break;
		default:
			System.out.println("Unexpected value for GameSpecificMessage: " + gameData);
			break;
		}
				
		delay.start();
		
		while(delay.get() < Variables.autoDelay) {
			
		}
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		switch(state) {
		case 0:
			approachCubes.start();
			state++;
			break;
		case 1:
			if(!approachCubes.isRunning()) {
				timer.start();
				turnTowardsWall.start();
				state++;
			}
			break;
		case 2:
			if(!turnTowardsWall.isRunning() || timer.get() > 1.5) {
				turnTowardsWall.cancel();
				moveTowardsWall.start();
				state++;
			}
			break;
		case 3:
			if(!moveTowardsWall.isRunning()) {
				timer.reset();
				turnTowardsSwitch.start();
				state++;
			}
			break;
		case 4:
			if(!turnTowardsSwitch.isRunning() || timer.get() > 1.5) {
				turnTowardsSwitch.cancel();
				approachSwitch.start();
				timer.reset();
				state++;
				timer.reset();
			}
			break;
		case 5:
			if(!approachSwitch.isRunning() || timer.get() > 1.5) {
				launchCube.start();
				state++;
			}
			break;
		case 6:
			break;
		default:
			System.out.println("Invalid state in PlaceOnSwitch.java State: " + state);
			break;
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return !launchCube.isRunning() && state == 6;
	}

	// Called once after isFinished returns true
	protected void end() {
		drivetrain.stop();
		approachCubes.cancel();
		turnTowardsWall.cancel();
		moveTowardsWall.cancel();
		turnTowardsSwitch.cancel();
		approachSwitch.cancel();
		launchCube.cancel();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
