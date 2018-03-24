package com._2491nomythic.tempest.commands.autonomous;

import com._2491nomythic.tempest.commands.CommandBase;
import com._2491nomythic.tempest.commands.cubestorage.TransportCubeTime;
import com._2491nomythic.tempest.settings.Constants;
import edu.wpi.first.wpilibj.DriverStation;

/**
 *
 */
public class PathAutoSwitch extends CommandBase {
	private int currentStep, timeCounter;
	private double adjustedLeftVelocity, adjustedRightVelocity, turnAdjustment, headingDiffrence, headingStart;
	private double[][] leftVelocitiesArray, rightVelocitiesArray, headingsArray;
	
	private TransportCubeTime autoShoot;
	private String gameData;

    public PathAutoSwitch() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    		requires(drivetrain);
    		requires(pathing);
    		autoShoot = new TransportCubeTime(-1, 1);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	/* Prepare robot superStructure*/
		headingStart = drivetrain.getRawGyroAngle();
		intake.activate();
    	
    	/* Reset Variables */
    	currentStep = 0; //was set to 4, therefore we where starting four steps in...
    	timeCounter = 4;
    	
    	/* Retrieve GameData to select direction */
    	gameData = DriverStation.getInstance().getGameSpecificMessage();
    	
    	/* Select side based on gameData */
		switch(gameData.substring(0, 1)) {
		case "L":
			leftVelocitiesArray = Constants.leftVelocitiesATcenterPosFORleftSwitch;
			rightVelocitiesArray = Constants.rightVelocitiesATcenterPosFORleftSwitch;
			headingsArray = Constants.anglesATcenterPosFORleftSwitch;
			break;
		case "R":
			leftVelocitiesArray = Constants.leftVelocitiesATcenterPosFORrightSwitch;
			rightVelocitiesArray = Constants.rightVelocitiesATcenterPosFORrightSwitch;
			headingsArray = Constants.anglesATcenterPosFORrightSwitch;
			break;
		default:
			System.out.println("Unexpected value for GameSpecificMessage: " + gameData);
			end();
			break;
		}
    }

    
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    		    	
    		if(timeCounter == 4) {
    
    			headingDiffrence = pathing.returnAngle(currentStep, headingsArray) + drivetrain.getRawGyroAngle() - headingStart; //kGyro was -1, now -1/80. Now + insted of minus so it is in the corret dir
    			turnAdjustment = Constants.kVelocitykG * headingDiffrence * Constants.kVeloctiyUnitConversion;  //* Constants.kGyroAdjusment
    			
    			adjustedLeftVelocity = pathing.returnVelocity(currentStep, leftVelocitiesArray) - turnAdjustment; //needs new testing and tuning
        		adjustedRightVelocity = pathing.returnVelocity(currentStep, rightVelocitiesArray) + turnAdjustment;
        		
        		System.out.println("H Diff: " + headingDiffrence + " Path: " + pathing.returnAngle(currentStep, headingsArray) + " Gyro: " + -(headingDiffrence - pathing.returnAngle(currentStep, headingsArray)) +  " Turn: " + turnAdjustment + " aL " + adjustedRightVelocity);

        		drivetrain.driveVelocity(adjustedLeftVelocity , adjustedRightVelocity);
    			        		
    			timeCounter = 0;
    			currentStep++;
    			
    		} 
    		else {
    			timeCounter++;
    		}
    }

    
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if(currentStep == leftVelocitiesArray.length-2) {
    		autoShoot.start();
    	}
       return currentStep + 1 == leftVelocitiesArray.length;
    }
    

    // Called once after isFinished returns true
    protected void end() {
    		drivetrain.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    		end();
    }
}
