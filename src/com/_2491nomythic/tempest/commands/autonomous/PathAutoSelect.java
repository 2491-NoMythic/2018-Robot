package com._2491nomythic.tempest.commands.autonomous;

import com._2491nomythic.tempest.commands.CommandBase;
import com._2491nomythic.tempest.commands.cubestorage.TransportCubeTime;
import com._2491nomythic.tempest.commands.drivetrain.DrivePath;
import com._2491nomythic.tempest.commands.drivetrain.RotateDrivetrainWithGyroPID;
import com._2491nomythic.tempest.commands.shooter.RunShooterCustom;
import com._2491nomythic.tempest.commands.shooter.SetShooterSpeed;
import com._2491nomythic.tempest.settings.Constants;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;

/**
 *
 */
public class PathAutoSelect extends CommandBase {
	private double mWaitTime;
	private DrivePath mPath;
	private SetShooterSpeed mSetSwitchSpeed, mSetScaleSpeed;
	private TransportCubeTime mEjectCube, mFireCube;
	private RunShooterCustom mRevShoot;
	private String mGameData;
	private Timer mTimer;
	public static enum StartPosition {
		LEFT, CENTER, RIGHT
	}
	public static enum EndPosition {
		SWITCH, LEFT_SWITCH, RIGHT_SWITCH, OPPOSITE_SWTICH, SCALE, OPPOSITE_SCALE, CROSS_LINE
	}
	public static enum Priority {
		SCALE, SWITCH
	}
	public static enum Crossing {
		OFF, ON, FORCE
	}
	
	private StartPosition mStartPosition;
	private Priority mPriority;
	private Crossing mCrossing;
	public EndPosition mEndPosition;
	
	/**
	 * 
	 * @param position
	 * @param priority
	 * @param crossing
	 */
    public PathAutoSelect(StartPosition position, Priority priority, Crossing crossing) {
    	this.mStartPosition = position;
        this.mPriority = priority;
    	this.mCrossing = crossing;
    	mSetSwitchSpeed = new SetShooterSpeed(0.2);
    	mSetScaleSpeed = new SetShooterSpeed(Constants.shooterMediumScaleSpeed);
    	mRevShoot = new RunShooterCustom();
    	mTimer = new Timer();
    	mWaitTime = 10;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	/*if (endPosition.name() != EndPosition.LEFT_SWITCH.name() && endPosition.name() != EndPosition.RIGHT_SWITCH.name()) {
			mSelectedLeftPath = "leftVelocitiesTO_" + EndPosition.LEFT_SWITCH.name();
        	mSelectedRightPath = "rightVelocitiesTO_" + endPosition.name();
        	mSelectedHeadings = "headingsTO_"+ endPosition.name();
		}*/
    	/* Prepare robot superStructure*/
    	
    	/* Retrieve GameData to select direction */
    	mGameData = DriverStation.getInstance().getGameSpecificMessage();  
    	
    	/* Select path based on gameData */
    	switch(mStartPosition) {
    	case LEFT:
    		switch(mGameData.substring(0, 2)) {
    		case "LL":
    			if (mPriority == Priority.SCALE) {
    				mEndPosition = EndPosition.SCALE;
    			}
    			else {
    				mEndPosition = EndPosition.SWITCH;
    			}
    			break;
    		case "LR":
    			if (mCrossing.equals(Crossing.FORCE) && mPriority.equals(Priority.SCALE)) {
    				mEndPosition = EndPosition.OPPOSITE_SCALE;
    			}
    			else {
    				mEndPosition = EndPosition.SWITCH;
    			}	
    			break;
    		case "RL":
    			mEndPosition = EndPosition.SCALE;
    			break;
    		case "RR":
    			if (mCrossing.equals(Crossing.OFF)) {
    				mEndPosition = EndPosition.CROSS_LINE;
    			}
    			else {
    				mEndPosition = EndPosition.OPPOSITE_SCALE;
    			}
    			break;
    		default:
    			System.out.println("Unexpected value for GameSpecificMessage: " + mGameData);
    			end();
    			break;	
    		}
    		break;	
    	case CENTER:
    		switch(mGameData.substring(0, 1)) {
    		case "L":
    			mEndPosition = EndPosition.LEFT_SWITCH;
    			break;
    		case "R":
    			mEndPosition = EndPosition.RIGHT_SWITCH;
    			break;
    		default:
    			System.out.println("Unexpected value for GameSpecificMessage: " + mGameData);
    			end();
    			break;
    		}
    		break;
    	case RIGHT:
    		switch(mGameData.substring(0, 2)) {
    		case "LL":
    			if (mCrossing.equals(Crossing.OFF)) {
    				mEndPosition = EndPosition.CROSS_LINE;
    			}
    			else {
    				mEndPosition = EndPosition.OPPOSITE_SCALE;
    			}
    			break;
    		case "LR":
    			mEndPosition = EndPosition.SCALE;
    			break;
    		case "RL":
    			if (mCrossing.equals(Crossing.FORCE) && mPriority.equals(Priority.SCALE)) {
    				mEndPosition = EndPosition.OPPOSITE_SCALE;
    			}
    			else {
    				mEndPosition = EndPosition.SWITCH;
    			}		
    			break;
    		case "RR":
    			if (mPriority == Priority.SCALE) {
    				mEndPosition = EndPosition.SCALE;
    			}
    			else {
    				mEndPosition = EndPosition.SWITCH;
    			}
    			break;
    		default:
    			System.out.println("Unexpected value for GameSpecificMessage: " + mGameData);
    			end();
    			break;
    			}
    		break;
    		}
    	System.out.println(mStartPosition);
    	System.out.println(mEndPosition);
		mPath = new DrivePath(mStartPosition, mEndPosition);
		mTimer.reset();
		mPath.start();	
    }

    
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	if (mPath.isCompleted() && mTimer.get() == 0) {
    		mTimer.start();
    		switch(mEndPosition) {
        	case SWITCH:
        	case OPPOSITE_SWTICH:
        		mWaitTime = 0;
        		mSetSwitchSpeed.start();
        		intake.deploy();
        		mRevShoot.start();
            	mFireCube = new TransportCubeTime(1, 1.5);
        		break;
        	case LEFT_SWITCH:
        	case RIGHT_SWITCH:
        		mWaitTime = 0;
        		intake.deploy();
            	mFireCube = new TransportCubeTime(-1, 1.5);
        		break;
        	case SCALE:
        	case OPPOSITE_SCALE:
        		mWaitTime = 2;
        		mSetScaleSpeed.start();
        		intake.deploy();
        		shooter.setScalePosition();
        		mRevShoot.start();
            	mFireCube = new TransportCubeTime(1, 1.5);
        		break;
        	case CROSS_LINE:
        		break;	
        	}
    	}
    }

    
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if (mTimer.get() > 3) {
    		return true;	
    	}
    	else if (mPath.isCompleted() && mTimer.get() > mWaitTime) {
    		mFireCube.start();
			return false;
		}
    	else {
    		return false;
    	}
    }
    

    // Called once after isFinished returns true
    protected void end() {
    	mTimer.stop();
    	mTimer.reset();
    	mRevShoot.cancel();
    	drivetrain.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
