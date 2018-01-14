package com._2491nomythic.robot.commands.autonomous;

import com._2491nomythic.robot.commands.CommandBase;
import com._2491nomythic.robot.commands.drivetrain.DriveStraightToPosition;
import com._2491nomythic.robot.commands.drivetrain.RotateDrivetrainWithGyroPID;

import edu.wpi.first.wpilibj.DriverStation;

/**
 * Attempts to place a cube on the correct side of the switch during autonomous.
 */
public class PlaceOnSwitch extends CommandBase {
	private DriveStraightToPosition approachCubes, moveTowardsWall, approachSwitch;
	private RotateDrivetrainWithGyroPID turnTowardsWall, turnTowardsSwitch;
	private int state;
	
	/**
	 * Attempts to place a cube on the correct side of the switch during autonomous.
	 */
    public PlaceOnSwitch() {
    	//Use this command if the robot is in front of DriverStation 2.
    	
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(drivetrain);
    	
    	approachCubes = new DriveStraightToPosition(0.8, 98);
    	approachSwitch = new DriveStraightToPosition(0.8, 76);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	state = 0;
    	
    	String gameData = new String(DriverStation.getInstance().getGameSpecificMessage());
    	
    	switch(gameData.substring(0, 1)) {
    	case "L":
    		moveTowardsWall = new DriveStraightToPosition(0.8, 58);
    		turnTowardsWall = new RotateDrivetrainWithGyroPID(-90, false);
    		turnTowardsSwitch = new RotateDrivetrainWithGyroPID(90, false);
    		break;
    	case "R":
    		moveTowardsWall = new DriveStraightToPosition(0.8, 50.5);
    		turnTowardsWall = new RotateDrivetrainWithGyroPID(90, false);
    		turnTowardsSwitch = new RotateDrivetrainWithGyroPID(-90, false);
    		break;
    	default:
    		System.out.println("Unexpected value for GameSpecificMessage: " + gameData);
    		break;
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
    			turnTowardsWall.start();
    			state++;
    		}
    		break;
    	case 2:
    		if(!turnTowardsWall.isRunning()) {
    			moveTowardsWall.start();
    			state++;
    		}
    		break;
    	case 3:
    		if(!moveTowardsWall.isRunning()) {
    			turnTowardsSwitch.start();
    			state++;
    		}
    		break;
    	case 4:
    		if(!turnTowardsSwitch.isRunning()) {
    			approachSwitch.start();
    			state++;
    		}
    		break;
    	case 5:
    		if(!approachSwitch.isRunning()) {
    			//Drop cube here
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
        return !approachSwitch.isRunning();
    }

    // Called once after isFinished returns true
    protected void end() {
    	drivetrain.stop();
    	approachCubes.cancel();
    	turnTowardsWall.cancel();
    	moveTowardsWall.cancel();
    	turnTowardsSwitch.cancel();
    	approachSwitch.cancel();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
