package com._2491nomythic.tempest.commands.drivetrain;

import com._2491nomythic.tempest.commands.CommandBase;
import com._2491nomythic.tempest.commands.autonomous.AutomaticAuto.*;
import com._2491nomythic.tempest.settings.Constants;
import com._2491nomythic.tempest.subsystems.Pathing;

/**
 * A command for streaming a selected TalonSRX Velocity mode path
 */
public class DrivePath extends CommandBase {
	private int mCurrentStep, mTimeCounter, mReverseDirection, mSwaped, mLength;
	private double mInitialHeading, mHeadingDiffrence, mTurnAdjustment, mAdjustedLeftVelocity, mAdjustedRightVelocity;
	private String mSelectedLeftPath, mSelectedRightPath, mSelectedHeading, mSelectedEndPosition;

	/**
	 * 
	 * @param startPosition an robot {@linkplain StartPosition} with respect to the field  
	 * @param endPosition a robot {@linkplain EndPosition} with respect to the field
	 * @author Emilio Lobo
	 */
    public DrivePath(StartPosition startPosition, EndPosition endPosition) {
    	
    	requires(drivetrain);
    	
    	this.mSelectedEndPosition = String.valueOf(endPosition.toString());
    	
    	setPosition(startPosition);
    }

    protected void initialize() {
    	resetVariables();
    }

    protected void execute() {
    	if(mTimeCounter == 4) {
    		
    		adjustVelocities();
    		
    		drivetrain.driveVelocity(mAdjustedLeftVelocity , mAdjustedRightVelocity);
			        		
			mTimeCounter = 0;
			mCurrentStep++;
			
		} 
		else {
			mTimeCounter++;
		}
    }

    protected boolean isFinished() {
        return mCurrentStep == mLength;
    }

    protected void end() {
    	drivetrain.stop();
    }

    protected void interrupted() {
    	end();
    }
    
    /**
     * Sets the starting position of the robot in the ARCADE
     * @param startPosition The ROBOT's starting position
     */
    private synchronized void setPosition(StartPosition startPosition) {
    	switch(startPosition) {
    	case LEFT:
    		configurePath(true, true);
    		break;
    	case CENTER:
    		configurePath(false, false);
    		break;
    	case RIGHT:
    	case CROSS_LINE:
    		configurePath(false, true);
    		break;
    	}
    }
   
    /**
     * Configures the ARCADE related variables.
     * <p> 
     * Assumes a default ROBOT configuration of a right starting position with the Intake facing the SCALE
     * @param swaped swaps the drive rails paths
     * @param reversed reverses the robots drive direction
     */
    private synchronized void configurePath(boolean swaped, boolean reversed) {
    	if (swaped) {
    		mSelectedLeftPath = "rightVelocitiesTO_" + mSelectedEndPosition;
        	mSelectedRightPath = "leftVelocitiesTO_" + mSelectedEndPosition;
        	mSwaped = -1;
    	}
    	else {
    		mSelectedLeftPath = "leftVelocitiesTO_" + mSelectedEndPosition;
        	mSelectedRightPath = "rightVelocitiesTO_" + mSelectedEndPosition;
        	mSwaped = 1;
    	}
    	if (reversed) {
    		mReverseDirection = -1;
    	}
    	else {
    		mReverseDirection = 1;
    	}
    	mSelectedHeading = "headingsTO_" + mSelectedEndPosition;
    }
    
    /**
     * Adjusts the paths velocities at every step to account for drivetrain scrub. 
     * <p>
     * The heading difference in-between the path and the gyroscope is used to increase or decrease the speed of each drive rail proportionally
     */
    private synchronized void adjustVelocities() {
    	mHeadingDiffrence = mSwaped * Pathing.getHeading(mCurrentStep, mSelectedHeading) + drivetrain.getRawGyroAngle() - mInitialHeading;
		mTurnAdjustment = Constants.kVelocitykG * Constants.kVeloctiyUnitConversion * mHeadingDiffrence; 
		
		mAdjustedLeftVelocity = mReverseDirection * Pathing.getVelocity(mCurrentStep, mSelectedLeftPath) - mTurnAdjustment;
		mAdjustedRightVelocity = mReverseDirection * Pathing.getVelocity(mCurrentStep, mSelectedRightPath) + mTurnAdjustment;
    }
    
    private synchronized void resetVariables() {
    	mInitialHeading = drivetrain.getRawGyroAngle();
    	mCurrentStep = 0;
    	mTimeCounter = 4;
    	mLength = Pathing.getHeadingsArray(mSelectedHeading).length;
    }
}
