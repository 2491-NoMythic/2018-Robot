package com._2491nomythic.tempest.commands.drivetrain;

import com._2491nomythic.tempest.commands.CommandBase;
import com._2491nomythic.tempest.commands.autonomous.PathAutoSelect.*;
import com._2491nomythic.tempest.settings.Constants;
import com._2491nomythic.tempest.subsystems.Pathing;

/**
 *
 */
public class DrivePath extends CommandBase {
	private int mCurrentStep, timeCounter, reverseDirection, mSwaped, mLength;
	private double initialHeading, headingDiffrence, turnAdjustment, adjustedLeftVelocity, adjustedRightVelocity;
	private String mSelectedLeftPath, mSelectedRightPath, mSelectedHeading, EndPosition;

	/**
	 * 
	 * @param startPosition an robot {@linkplain StartPosition} with respect to the field  
	 * @param endPosition a robot {@linkplain EndPosition} with respect to the field
	 * @author Emilio Lobo
	 */
    public DrivePath(StartPosition startPosition, EndPosition endPosition) {
    	
    	requires(drivetrain);
    	
    	this.EndPosition = String.valueOf(endPosition.toString());
    	
    	/* Sets Drive settings according to startPosition*/
    	switch(startPosition) {
    	case LEFT:
    		configureDrive(true, true);
    		break;
    	case CENTER:
    		configureDrive(false, false);
    		break;
    	case RIGHT:
    		configureDrive(false, true);
    		break;
    	}
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	/* Reset Variables */
    	initialHeading = drivetrain.getRawGyroAngle();
    	mCurrentStep = 0;
    	timeCounter = 4;
    	mLength = Pathing.getHeadingsArray(mSelectedHeading).length;
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(timeCounter == 4) {
    	    
			headingDiffrence = mSwaped * Pathing.getHeading(mCurrentStep, mSelectedHeading) + drivetrain.getRawGyroAngle() - initialHeading; //+
			turnAdjustment = Constants.kVelocitykG * Constants.kVeloctiyUnitConversion * headingDiffrence; 
			
			adjustedLeftVelocity = reverseDirection * Pathing.getVelocity(mCurrentStep, mSelectedLeftPath) - turnAdjustment; //-
    		adjustedRightVelocity = reverseDirection * Pathing.getVelocity(mCurrentStep, mSelectedRightPath) + turnAdjustment; //+
    		
    		//System.out.println("H Diff: " + headingDiffrence + " Path: " + pathing.returnAngle(currentStep, headings) + " Gyro: " + -(headingDiffrence - pathing.returnAngle(currentStep, headings)) +  " Turn: " + turnAdjustment + " aL " + adjustedRightVelocity);

    		drivetrain.driveVelocity(adjustedLeftVelocity , adjustedRightVelocity); //+
			        		
			timeCounter = 0;
			mCurrentStep++;
			
		} 
		else {
			timeCounter++;
		}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return mCurrentStep == mLength;
    }

    // Called once after isFinished returns true
    protected void end() {
    	drivetrain.stop();
    	mCurrentStep = 0;
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
    
    /**
     * Configures the ARCADE related variables.
     * <p> 
     * Assumes a default ROBOT configuration of a right starting position with the Intake facing the SCALE
     * @param swaped swaps the drive rails paths
     * @param reversed reverses the robots drive direction
     */
    private void configureDrive(boolean swaped, boolean reversed) {
    	if (swaped) {
    		mSelectedLeftPath = "rightVelocitiesTO_" + EndPosition;
        	mSelectedRightPath = "leftVelocitiesTO_" + EndPosition;
        	mSwaped = -1;
    	}
    	else {
    		mSelectedLeftPath = "leftVelocitiesTO_" + EndPosition;
        	mSelectedRightPath = "rightVelocitiesTO_" + EndPosition;
        	mSwaped = 1;
    	}
    	if (reversed) {
    		reverseDirection = -1;
    	}
    	else {
    		reverseDirection = 1;
    	}
    	mSelectedHeading = "headingsTO_" + EndPosition;
    }
}
