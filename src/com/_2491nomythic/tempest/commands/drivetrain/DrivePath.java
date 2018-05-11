package com._2491nomythic.tempest.commands.drivetrain;

import com._2491nomythic.tempest.commands.CommandBase;
import com._2491nomythic.tempest.commands.autonomous.AutomaticAuto.*;
import com._2491nomythic.tempest.settings.Constants;
import com._2491nomythic.tempest.subsystems.Pathing;

/**
 * A command for streaming a selected TalonSRX Velocity mode path at a rate of one point per 20ms
 */
public class DrivePath extends CommandBase {
	private int mCurrentStep, mDriveDirection, mHeadingDir, mPathLength, mLeftChannel, mRightChannel, mHeadingChannel;
	private double mInitialHeading, mHeadingDiffrence, mTurnAdjustment, mAdjustedLeftVelocity, mAdjustedRightVelocity;
	private double[][] mSelectedPath;

	/**
	 * 
	 * @param startPosition an robot {@linkplain StartPosition} with respect to the field  
	 * @param endPosition a robot {@linkplain EndPosition} with respect to the field
	 * @author Emilio Lobo
	 */
    public DrivePath(StartPosition origin, EndPosition target) {
    	
    	requires(drivetrain);
    	
    	this.mSelectedPath = Pathing.getPathArray(target);  //String.valueOf(endPosition.toString());
    	
    	this.mLeftChannel = origin.leftChannel();
    	this.mRightChannel = origin.rigthChannel();
    	this.mHeadingChannel = 2;
    	this.mHeadingDir = origin.headingDir();
    	this.mDriveDirection = origin.driveDir();
    	
    }

    protected void initialize() {
    	resetVariables();
    }

    protected void execute() {
    	adjustVelocities();
    	drivetrain.driveVelocity(mAdjustedLeftVelocity , mAdjustedRightVelocity);        		
		mCurrentStep++;			
    }

    protected boolean isFinished() {
        return mCurrentStep == mPathLength;
    }

    protected void end() {
    	drivetrain.stop();
    }

    protected void interrupted() {
    	end();
    }
    
    /**
     * Adjusts the paths velocities at every step to account for drivetrain scrub. 
     * <p>
     * The heading difference in-between the path and the gyroscope is used to increase or decrease the speed of each drive rail proportionally
     */
    private synchronized void adjustVelocities() {
    	mHeadingDiffrence = mHeadingDir * mSelectedPath[mCurrentStep][mHeadingChannel] + drivetrain.getRawGyroAngle() - mInitialHeading;
		mTurnAdjustment = Constants.kVelocitykG * Constants.kVeloctiyUnitConversion * mHeadingDiffrence; 
		
		mAdjustedLeftVelocity = mDriveDirection * mSelectedPath[mCurrentStep][mLeftChannel] - mTurnAdjustment;
		mAdjustedRightVelocity = mDriveDirection * mSelectedPath[mCurrentStep][mRightChannel] + mTurnAdjustment;
    }
    
    public int getCurrentStep() {
    	return mCurrentStep;
    }
    
    private synchronized void resetVariables() {
    	mInitialHeading = drivetrain.getRawGyroAngle();
    	mCurrentStep = 0;
    	mPathLength = mSelectedPath.length;
    }
}
