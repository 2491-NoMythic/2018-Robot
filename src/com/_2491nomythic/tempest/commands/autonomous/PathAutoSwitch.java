package com._2491nomythic.tempest.commands.autonomous;

import com._2491nomythic.tempest.commands.CommandBase;
import com._2491nomythic.tempest.commands.drivetrain.DriveStraightToPositionPID;
import com._2491nomythic.tempest.commands.drivetrain.RotateDrivetrainWithGyroPID;
import com._2491nomythic.tempest.settings.Constants;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class PathAutoSwitch extends CommandBase {
	int currentStep = 0;
	int timeCounter = 0;
	Timer timer;
	double[][] leftVelocity;
	double[][] rightVelocity;

    public PathAutoSwitch() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(drivetrain);
    	requires(shooter);
    	requires(pathing);
    	timer = new Timer();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	timer.start();
String gameData = DriverStation.getInstance().getGameSpecificMessage();
		
		switch(gameData.substring(0, 1)) {
		case "L":
			leftVelocity = Constants.leftVelocityCenterStartPosLeftSwitchAutoPath;
			rightVelocity = Constants.rightVelocityCenterStartPosLeftSwitchAutoPath;
			break;
		case "R":
			
			
			break;
		default:
			System.out.println("Unexpected value for GameSpecificMessage: " + gameData);
			break;
		}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double currentLeftVelocity = pathing.returnVelocity(currentStep, leftVelocity);
    	double currentRightVelocity = pathing.returnVelocity(currentStep, rightVelocity);
    	drivetrain.driveVelocity(currentLeftVelocity / 12, currentRightVelocity / 12);
    	if(timeCounter == 4) {
    		currentStep++;
    		timeCounter = 0;
    	}else {
    		timeCounter++;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
       return currentStep >= leftVelocity.length - 1;  
    }

    // Called once after isFinished returns true
    protected void end() {
    	shooter.run(50);
    	try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	shooter.stop();
    	drivetrain.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	drivetrain.stop();
    }
}
