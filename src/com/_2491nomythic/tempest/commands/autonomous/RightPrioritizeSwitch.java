package com._2491nomythic.tempest.commands.autonomous;

import com._2491nomythic.tempest.commands.AutomaticShoot;
import com._2491nomythic.tempest.commands.CommandBase;
import com._2491nomythic.tempest.commands.drivetrain.DriveStraightToPositionPID;
import com._2491nomythic.tempest.commands.drivetrain.RotateDrivetrainWithGyroPID;
import com._2491nomythic.tempest.settings.Variables;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;

/**
 * Attempts to place a cube on either the right scale OR switch, prioritizing switch. If both the scale and the switch are on the left, the robot crosses the auto line.
 */
public class RightPrioritizeSwitch extends CommandBase {
	private DriveStraightToPositionPID driveToSwitch, driveToScale, approachSwitch, approachScale;
	private CrossAutoLine crossLine;
	private RotateDrivetrainWithGyroPID turnTowardsSwitchOrScale;
	private AutomaticShoot launchCubeSwitch, launchCubeScale;
	private int state;
	private Timer timer, delay;
	private boolean scaleSide, switchSide;

	/**
	 * Attempts to place a cube on either the right scale OR switch, prioritizing switch. If both the scale and the switch are on the left, the robot crosses the auto line.
	 */
	public RightPrioritizeSwitch() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);		
		timer = new Timer();
		delay = new Timer();
		crossLine = new CrossAutoLine();
		turnTowardsSwitchOrScale = new RotateDrivetrainWithGyroPID(90, false);
		driveToScale = new DriveStraightToPositionPID(323.6);
		driveToSwitch = new DriveStraightToPositionPID(168);
		approachScale = new DriveStraightToPositionPID(44);
		approachSwitch = new DriveStraightToPositionPID(-42);
		launchCubeSwitch = new AutomaticShoot(false);
		launchCubeScale = new AutomaticShoot(true);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		state = 0;
		
		String gameData = DriverStation.getInstance().getGameSpecificMessage();
		scaleSide = gameData.substring(1, 2).contentEquals("R");
		switchSide = gameData.substring(0, 1).contentEquals("R");
		
		delay.start();
		
		if(delay.get() < Variables.autoDelay) {
			
		}
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		timer.start();
		
		if(switchSide) {
			switch(state) {
			case 0:
				driveToSwitch.start();
				state++;
				break;
			case 1:
				if(!driveToSwitch.isRunning()) {
					turnTowardsSwitchOrScale.start();
					timer.reset();
					state++;
				}
				break;
			case 2:
				if(!turnTowardsSwitchOrScale.isRunning() || timer.get() > 1.5) {
					turnTowardsSwitchOrScale.cancel();
					approachSwitch.start();
					state++;
				}
				break;
			case 3:
				if(!approachSwitch.isRunning()) {
					//launchCubeSwitch.start();
					state++;
				}
				break;
			case 4:
				break;
			default:
				System.out.println("Unexpected state in RightScaleOrSwitch.java State: " + state);
				break;
			}
		}
		else if(scaleSide) {
			switch(state) {
			case 0:
				driveToScale.start();
				state++;
				break;
			case 1:
				if(!driveToScale.isRunning()) {
					timer.reset();
					turnTowardsSwitchOrScale.start();
					state++;
				}
				break;
			case 2:
				if(!turnTowardsSwitchOrScale.isRunning() || timer.get() > 1.5) {
					turnTowardsSwitchOrScale.cancel();
					approachScale.start();
					state++;
				}
				break;
			case 3:
				if(!approachScale.isRunning()) {
					//launchCubeScale.start();
					state++;
				}
				break;
			case 4:
				break;
			default:
				System.out.println("Unexpected state in RightScaleOrSwitch.java State: " + state);
				break;
			}
		}
		else {
			crossLine.start();
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		if(switchSide) {
			return !launchCubeSwitch.isRunning() && state == 4;
		}
		else if(scaleSide) {
			return !launchCubeScale.isRunning() && state == 4;
		}
		else {
			return !crossLine.isRunning() && timer.get() > 1;
		}
	}

	// Called once after isFinished returns true
	protected void end() {
		crossLine.cancel();
		approachSwitch.cancel();
		driveToSwitch.cancel();
		driveToScale.cancel();
		approachScale.cancel();
		turnTowardsSwitchOrScale.cancel();
		
		drivetrain.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
