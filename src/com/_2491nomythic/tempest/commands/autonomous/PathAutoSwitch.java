package com._2491nomythic.tempest.commands.autonomous;

import com._2491nomythic.tempest.commands.CommandBase;
import com._2491nomythic.tempest.commands.shooter.RunShooterTime;
import com._2491nomythic.tempest.settings.Constants;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;

/**
 *
 */
public class PathAutoSwitch extends CommandBase {
	int currentStep = 0;
	int timeCounter = 0;
	Timer timer;
	double[][] leftVelocity;
	double[][] rightVelocity;
	RunShooterTime shooterRun;

    public PathAutoSwitch() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(drivetrain);
    	requires(pathing);
    	timer = new Timer();
    	shooterRun = new RunShooterTime(50, 1.5);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
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
		timer.reset();
		timer.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	for(int i=0;i < 46; i++) {
    		drivetrain.driveVelocity(leftVelocity[i][0], rightVelocity[i][0]);
    		while(timer.get() < 0.1) {}
    		timer.reset();
    		timer.start();
    	}
    
    	/*
    	double currentLeftVelocity = pathing.returnVelocity(currentStep, leftVelocity);
    	double currentRightVelocity = pathing.returnVelocity(currentStep, rightVelocity);
    	drivetrain.driveVelocity(currentLeftVelocity * 12, currentRightVelocity * 12);
    	if(timeCounter == 4) {
    		currentStep++;
    		timeCounter = 0;
    	}else {
    		timeCounter++;
    	}*/
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
       return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    	//shooterRun.start();
    	shooter.stop();
    	drivetrain.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	drivetrain.stop();
    }
}
