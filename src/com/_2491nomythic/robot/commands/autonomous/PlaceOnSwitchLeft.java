package com._2491nomythic.robot.commands.autonomous;

import com._2491nomythic.robot.commands.AutomaticShoot;
import com._2491nomythic.robot.commands.CommandBase;
import com._2491nomythic.robot.commands.drivetrain.DriveStraightToPositionPID;
import com._2491nomythic.robot.commands.drivetrain.RotateDrivetrainWithGyroPID;

import edu.wpi.first.wpilibj.DriverStation;

/**
 * Attempts to place a cube on the switch starting in front of the left DriverStation.
 */
public class PlaceOnSwitchLeft extends CommandBase {
	private DriveStraightToPositionPID driveToSwitch, drivePastSwitch, driveToCorrectSide, driveBackToWall, approachSwitch; 
	private RotateDrivetrainWithGyroPID aimForCorrectSide, aimForWall, aimForSwitch;
	private AutomaticShoot launchCube;
	private boolean left;
	private int state;
	
	/**
	 * Attempts to place a cube on the switch starting in front of the left DriverStation.
	 */
    public PlaceOnSwitchLeft() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	driveToSwitch = new DriveStraightToPositionPID(2491);
    	drivePastSwitch = new DriveStraightToPositionPID(2491);
    	driveToCorrectSide = new DriveStraightToPositionPID(2491);
    	driveBackToWall = new DriveStraightToPositionPID(2491);
    	approachSwitch = new DriveStraightToPositionPID(2491);
    	aimForCorrectSide = new RotateDrivetrainWithGyroPID(90, false);
    	aimForWall = new RotateDrivetrainWithGyroPID(90, false);
    	aimForSwitch = new RotateDrivetrainWithGyroPID(90, false);
    	launchCube = new AutomaticShoot(false);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	state = 0;
    	
    	String gameData = DriverStation.getInstance().getGameSpecificMessage();
    	left = gameData.substring(0, 1) == "L";
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(left) {
    		switch(state) {
    		case 0:
    			driveToSwitch.start();
    			state++;
    			break;
    		case 1:
    			if(!driveToSwitch.isRunning()) {
    				aimForSwitch.start();
    				state++;
    			}
    			break;
    		case 2:
    			if(!aimForSwitch.isRunning()) {
    				approachSwitch.start();
    				state++;
    			}
    			break;
    		case 3:
    			if(!approachSwitch.isRunning()) {
    				launchCube.start();
    				state++;
    			}
    			break;
    		case 4:
    			break;
    		default:
    			System.out.println("Unexpected state in PlaceOnSwitchLeft. State: " + state);
    			break;
    		}
    	}
    	else {
    		switch(state) {
    		case 0:
    			drivePastSwitch.start();
    			state++;
    			break;
    		case 1:
    			if(!drivePastSwitch.isRunning()) {
    				aimForCorrectSide.start();
    				state++;
    			}
    			break;
    		case 2:
    			if(!aimForCorrectSide.isRunning()) {
    				driveToCorrectSide.start();
    				state++;
    			}
    			break;
    		case 3:
    			if(!driveToCorrectSide.isRunning()) {
    				aimForWall.start();
    				state++;
    			}
    			break;
    		case 4:
    			if(!aimForWall.isRunning()) {
    				driveBackToWall.start();
    				state++;
    			}
    			break;
    		case 5:
    			if(!driveBackToWall.isRunning()) {
    				aimForSwitch.start();
    				state++;
    			}
    			break;
    		case 6:
    			if(!aimForSwitch.isRunning()) {
    				approachSwitch.start();
    				state++;
    			}
    			break;
    		case 7:
    			if(!approachSwitch.isRunning()) {
    				launchCube.start();
    				state++;
    			}
    			break;
    		case 8:
    			break;
    		default:
    			System.out.println("Unexpected state in PlaceOnSwitchLeft. State: " + state);
    			break;
    		}
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        if(left) {
        	return state == 4 && !launchCube.isRunning();
        }
        else {
        	return state == 8 && !launchCube.isRunning();
        }
    }

    // Called once after isFinished returns true
    protected void end() {
    	drivetrain.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	driveToSwitch.cancel();
    	drivePastSwitch.cancel();
    	driveToCorrectSide.cancel();
    	driveBackToWall.cancel();
    	approachSwitch.cancel();
    	aimForSwitch.cancel();
    	aimForCorrectSide.cancel();
    	aimForWall.cancel();
    	launchCube.cancel();
    }
}
