package com._2491nomythic.tempest.commands.autonomous;

import com._2491nomythic.tempest.commands.AutomaticShoot;
import com._2491nomythic.tempest.commands.CommandBase;
import com._2491nomythic.tempest.commands.drivetrain.DriveStraightToPositionPID;
import com._2491nomythic.tempest.commands.drivetrain.RotateDrivetrainWithGyroPID;
import com._2491nomythic.tempest.settings.Variables;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;

/**
 * Attempts to place a cube on either the left scale OR switch, prioritizing switch. If both scale and switch are on the right, the robot crosses the auto line.
 * @deprecated
 */
public class LeftPrioritizeSwitch extends CommandBase {
	private DriveStraightToPositionPID driveToSwitch, driveToScale, approachSwitch, approachScale;
	private CrossAutoLine crossLine;
	private RotateDrivetrainWithGyroPID turnTowardsSwitchOrScale;
	private AutomaticShoot launchCubeScale, launchCubeSwitch;
	private int state;
	private Timer timer, delay;
	private boolean scaleSide, switchSide;

	/**
	 * Attempts to place a cube on either the left scale OR switch, prioritizing switch. If both scale and switch are on the right, the robot crosses the auto line.
	 * @deprecated
	 */
	public LeftPrioritizeSwitch() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);		
		timer = new Timer();
		delay = new Timer();
		crossLine = new CrossAutoLine();
		turnTowardsSwitchOrScale = new RotateDrivetrainWithGyroPID(-90, false);
		driveToScale = new DriveStraightToPositionPID(323.6);
		driveToSwitch = new DriveStraightToPositionPID(168);
		approachScale = new DriveStraightToPositionPID(44);
		approachSwitch = new DriveStraightToPositionPID(-42);
		launchCubeScale = new AutomaticShoot(true);
		launchCubeSwitch = new AutomaticShoot(false);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		state = 0;
		
		String gameData = new String(DriverStation.getInstance().getGameSpecificMessage());
		switchSide = gameData.substring(0, 1).contentEquals("L");
		scaleSide = gameData.substring(1, 2).contentEquals("L");

		delay.start();
		
		while(delay.get() < Variables.autoDelay) {
			
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
				System.out.println("Unexpected state in LeftScaleOrSwitch.java State: " + state);
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
				System.out.println("Unexpected state in LeftScaleOrSwitch.java State: " + state);
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
		launchCubeScale.cancel();
		launchCubeSwitch.cancel();
		
		drivetrain.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
