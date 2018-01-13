package com._2491nomythic.robot.commands.autonomous;

import com._2491nomythic.robot.commands.CommandBase;
import com._2491nomythic.robot.commands.drivetrain.DriveStraightToPosition;
import com._2491nomythic.robot.commands.drivetrain.RotateDrivetrainWithGyroPID;

import edu.wpi.first.wpilibj.DriverStation;

/**
 * Attempts to place a cube on the correct side of the scale during autonomous.
 */
public class PlaceOnScale extends CommandBase {
	private DriveStraightToPosition driveDownField, moveToCorrectSide, approachScale;
	private RotateDrivetrainWithGyroPID rotateTowardsCenter, rotateTowardsScale;
	private int state;
	
	/**
	 * Attempts to place a cube on the correct side of the scale during autonomous.
	 */
    public PlaceOnScale() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(drivetrain);
    	
    	driveDownField = new DriveStraightToPosition(0.8, 150);
    	approachScale = new DriveStraightToPosition(0.8, 30);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	state = 0;
    	
    	String gameData = new String(DriverStation.getInstance().getGameSpecificMessage());
    	
    	switch(gameData.substring(1, 2)) {
    	case "L":
    		if(DriverStation.getInstance().getLocation() == 1) {
    			rotateTowardsCenter = new RotateDrivetrainWithGyroPID(90, false);
    			rotateTowardsScale = new RotateDrivetrainWithGyroPID (-90, false);
    			moveToCorrectSide = new DriveStraightToPosition(0.8, 10);
    		}
    		else {
    			rotateTowardsCenter = new RotateDrivetrainWithGyroPID(-90, false);
    			rotateTowardsScale = new RotateDrivetrainWithGyroPID(90, false);
    			moveToCorrectSide = new DriveStraightToPosition(0.8, 70);
    		}
    		break;
    	case "R":
    		if(DriverStation.getInstance().getLocation() == 1) {
    			rotateTowardsCenter = new RotateDrivetrainWithGyroPID(90, false);
    			rotateTowardsScale = new RotateDrivetrainWithGyroPID (-90, false);
    			moveToCorrectSide = new DriveStraightToPosition(0.8, 70);
    		}
    		else {
    			rotateTowardsCenter = new RotateDrivetrainWithGyroPID(-90, false);
    			rotateTowardsScale = new RotateDrivetrainWithGyroPID(90, false);
    			moveToCorrectSide = new DriveStraightToPosition(0.8, 10);
    		}
    		break;
    	default:
    		System.out.println("Unexpected Value for GameSpecificMessage. gameData: " + gameData);
    		break;
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	switch(state) {
    	case 0:
    		driveDownField.start();
    		state++;
    		break;
    	case 1:
    		if(!driveDownField.isRunning()) {
    			rotateTowardsCenter.start();
    			state++;
    		}
    		break;
    	case 2:
    		if(!rotateTowardsCenter.isRunning()) {
    			moveToCorrectSide.start();
    			state++;
    		}
    		break;
    	case 3:
    		if(!moveToCorrectSide.isRunning()) {
    			rotateTowardsScale.start();
    			state++;
    		}
    		break;
    	case 4:
    		if(!rotateTowardsScale.isRunning()) {
    			approachScale.start();
    			state++;
    		}
    		break;
    	case 5:
    		if(!approachScale.isRunning()) {
    			//Drop cube here
    			state++;
    		}
    		break;
    	case 6:
    		break;
    	default:
    		System.out.println("Unexpected state in PlaceOnScale.java State: " + state);
    		break;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return !approachScale.isRunning();
    }

    // Called once after isFinished returns true
    protected void end() {
    	drivetrain.stop();
    	driveDownField.cancel();
    	rotateTowardsCenter.cancel();
    	moveToCorrectSide.cancel();
    	rotateTowardsScale.cancel();
    	approachScale.cancel();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
