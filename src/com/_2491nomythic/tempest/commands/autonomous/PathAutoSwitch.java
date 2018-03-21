package com._2491nomythic.tempest.commands.autonomous;

import com._2491nomythic.tempest.commands.AutomaticShoot;
import com._2491nomythic.tempest.commands.CommandBase;
import com._2491nomythic.tempest.settings.Constants;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;

/**
 *
 */
public class PathAutoSwitch extends CommandBase {
	private int currentStep, timeCounter;
	private double currentLeftVelocity, currentRightVelocity, turnAdjustment, headingDiffrence;
	private double[] currentAngle;
	private double[][] leftVelocity, rightVelocity;
	
	private Timer timer;
	private AutomaticShoot autoShoot;
	private String gameData;

    public PathAutoSwitch() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    		requires(drivetrain);
    		requires(pathing);
    		timer = new Timer();
    		autoShoot = new AutomaticShoot(false);
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
			leftVelocity = Constants.leftVelocityCenterStartPosLeftSwitchAutoPath;
			rightVelocity = Constants.rightVelocityCenterStartPosLeftSwitchAutoPath;
			currentAngle = Constants.centerStartPosLeftSwitchAutoPathAngles;
			break;
		case "R":
			leftVelocity = Constants.leftVelocityCenterStartPosRightSwitchAutoPath;
			rightVelocity = Constants.rightVelocityCenterStartPosRightSwitchAutoPath;
			currentAngle = Constants.centerStartPosRightSwitchAutoPathAngles;
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
    		    	
    		if(timeCounter == 4) {
    			
    			headingDiffrence = pathing.returnAngle(currentStep, currentAngle) - drivetrain.getGyroAngle();
    			turnAdjustment = Constants.kVelocitykG * headingDiffrence * Constants.kGyroAdjusment;
    			
    			currentLeftVelocity = pathing.returnVelocity(currentStep, leftVelocity) - turnAdjustment;
        		currentRightVelocity = pathing.returnVelocity(currentStep, rightVelocity) + turnAdjustment;
        		
        		drivetrain.driveVelocity(currentLeftVelocity , currentRightVelocity);
    			
    			timeCounter = 0;
    			currentStep++;
    			
    		} 
    		else {
    			timeCounter++;
    		}
    }

    
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
       return currentStep == leftVelocity.length;
    }
    

    // Called once after isFinished returns true
    protected void end() {
    		autoShoot.start();
    		drivetrain.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    		drivetrain.stop();
    }
}
