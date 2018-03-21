package com._2491nomythic.tempest.commands.autonomous;

import com._2491nomythic.tempest.commands.CommandBase;
import com._2491nomythic.tempest.commands.shooter.RunShooterTime;
import com._2491nomythic.tempest.settings.Constants;
import com._2491nomythic.tempest.settings.Variables;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;

/**
 *
 */
public class PathAutoSwitch extends CommandBase {
	private int currentStep, timeCounter;
	private double currentLeftVelocity, currentRightVelocity, turn, angleDiffrence, kG;
	private double[] currentAngle;
	private double[][] leftVelocity, rightVelocity;
	
	private Timer timer;
	private RunShooterTime shooterRun;
	
	private String gameData;


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
    	
    		/* Reset Variables */
    		currentStep = 4;
    		timeCounter = 0;
    	
    		/* Retrieve GameData to select direction */
    		gameData = DriverStation.getInstance().getGameSpecificMessage();
    	
		switch(gameData.substring(0, 1)) {
		case "L":
			leftVelocity = Constants.leftVelocityCenterStartPosLeftScaleAutoPath;
			rightVelocity = Constants.rightVelocityCenterStartPosLeftScaleAutoPath;
			currentAngle = Constants.centerStartPosLeftSwitchAutoPathAngles;
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
    		
    		System.out.println("Right: " + currentRightVelocity + " LEFT: " + currentLeftVelocity);
    	
    		if(timeCounter == 4) {
    			currentStep++;
    			
    			currentLeftVelocity = pathing.returnVelocity(currentStep, leftVelocity) * Constants.feetPerSecToNativeUnitsPer100Ms;
        		currentRightVelocity = pathing.returnVelocity(currentStep, rightVelocity) * Constants.feetPerSecToNativeUnitsPer100Ms;
        		
        		angleDiffrence = pathing.returnAngle(currentStep, currentAngle) - drivetrain.getGyroAngle();
        		kG = 0.8 * -1/80;
        		
        		turn = kG * angleDiffrence;
        		
        		currentRightVelocity = currentRightVelocity - turn;
        		currentLeftVelocity = currentLeftVelocity + turn;
        		
        		drivetrain.driveVelocity(currentLeftVelocity , currentRightVelocity);
    			
    			timeCounter = 0;
    			
    			if (Variables.debugMode) { }
    			
    		} else {
    			timeCounter++;
    		}
    }

    
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
       return currentStep > 44;
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
    		shooter.stop();
    }
}
