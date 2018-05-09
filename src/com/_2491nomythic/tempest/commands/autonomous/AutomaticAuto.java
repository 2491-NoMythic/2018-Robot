package com._2491nomythic.tempest.commands.autonomous;

import com._2491nomythic.tempest.commands.CommandBase;
import com._2491nomythic.tempest.commands.cubestorage.TransportCubeTime;
import com._2491nomythic.tempest.commands.drivetrain.DrivePath;
import com._2491nomythic.tempest.commands.shooter.RunShooterCustom;
import com._2491nomythic.tempest.commands.shooter.SetShooterSpeed;
import com._2491nomythic.tempest.settings.Constants;
import com._2491nomythic.tempest.subsystems.Pathing;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;

/**
 *
 */
public class AutomaticAuto extends CommandBase {
	private double mWaitTime;
	private DrivePath mPath, mBumpCounter;
	private SetShooterSpeed mSetSwitchSpeed, mSetScaleSpeed;
	private TransportCubeTime mFireCube;
	private RunShooterCustom mRevShoot;
	private String mGameData;
	private Timer mTimer;
	
	public static enum StartPosition {
		LEFT, CENTER, RIGHT, CROSS_LINE
	}
	
	public static enum EndPosition {
		SWITCH, LEFT_SWITCH, RIGHT_SWITCH, OPPOSITE_SWTICH, SCALE, OPPOSITE_SCALE, CROSS_LINE, BUMP_COUNTER
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
    public AutomaticAuto(StartPosition position, Priority priority, Crossing crossing) {
    	this.mStartPosition = position;
        this.mPriority = priority;
    	this.mCrossing = crossing;
    	mSetSwitchSpeed = new SetShooterSpeed(0.2);
    	mSetScaleSpeed = new SetShooterSpeed(Constants.shooterMediumScaleSpeed);
    	mRevShoot = new RunShooterCustom();
    	mTimer = new Timer();
    	mWaitTime = 15;
    	mBumpCounter = new DrivePath(StartPosition.CENTER, EndPosition.BUMP_COUNTER);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	selectEndPosition(mStartPosition);
		mPath = new DrivePath(mStartPosition, mEndPosition);
		mTimer.reset();
		mPath.start();	
    }

    
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {	
    	switch(mEndPosition) {
    	case OPPOSITE_SCALE:
    		if(mPath.getCurrentStep() == Pathing.getVelocityArray("leftVelocitiesTO_OPPOSITE_SCALE").length - 12) {
    			intake.openArms();
    			shooter.setScalePosition();
    			mSetScaleSpeed.start();
    			mWaitTime = 0.1;
    		} else if(mPath.getCurrentStep() == Pathing.getVelocityArray("leftVelocitiesTO_OPPOSITE_SCALE").length - 17) {
    			mRevShoot.start();
    		}
    		break;
    	case SCALE:    		
    		if(mPath.getCurrentStep() == Pathing.getVelocityArray("leftVelocitiesTO_SCALE").length - 12) {
    			intake.openArms();
    			shooter.setScalePosition();
    			mSetScaleSpeed.start();
    			mWaitTime = 0.1;
    		} else if(mPath.getCurrentStep() == Pathing.getVelocityArray("leftVelocitiesTO_SCALE").length - 17) {
    			mRevShoot.start();
    		}
    		break;
    	default:
    		break;
    	}
    	
    	if (mPath.isCompleted() && mTimer.get() == 0) {
    		mTimer.start();
    		switch(mEndPosition) {
        	case SWITCH:
        	case OPPOSITE_SWTICH:
        		mWaitTime = 0;
        		mSetSwitchSpeed.start();
        		mRevShoot.start();
            	mFireCube = new TransportCubeTime(1, 1);
        		break;
        	case LEFT_SWITCH:
        	case RIGHT_SWITCH:
        		mWaitTime = 0;
        		mBumpCounter.start();
            	mFireCube = new TransportCubeTime(-1, 1);
        		break;
        	case SCALE:
        	case OPPOSITE_SCALE:
        		//mWaitTime = 2;
        		//mSetScaleSpeed.start();
        		intake.openArms();
        		shooter.setScalePosition();
        		//mRevShoot.start();
            	mFireCube = new TransportCubeTime(1, 1.5); //1, 1.5
        		break;
        	case CROSS_LINE:
        	case BUMP_COUNTER:
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
    
    private synchronized void selectEndPosition(StartPosition startPosition) {
    	getGameData();
    	switch(mStartPosition) {
    	case LEFT:
    		reverseGameData();
    		respondToARCADE(mGameData);
    		break;	
    	case CENTER:
    	case RIGHT:
    		respondToARCADE(mGameData);
    		break;
    	case CROSS_LINE:
    		mEndPosition = EndPosition.CROSS_LINE;
    		break;
    	}
    	System.out.println("Selected Start Position; " + mStartPosition);
    }
    
    private synchronized void respondToARCADE(String gameData) {
    	switch(mGameData.substring(0, 2)) {
    	case "LL":
			if (mStartPosition == StartPosition.CENTER) {
				mEndPosition = EndPosition.LEFT_SWITCH;
			}
			else if (mCrossing.equals(Crossing.OFF)) {
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
		case "LR":
			if (mStartPosition == StartPosition.CENTER) {
				mEndPosition = EndPosition.LEFT_SWITCH;
			}
			else {
				mEndPosition = EndPosition.SCALE;
			}
			break;
		case "RL":
			if (mStartPosition == StartPosition.CENTER) {
				mEndPosition = EndPosition.RIGHT_SWITCH;
			}
			else if (mCrossing.equals(Crossing.FORCE) && mPriority.equals(Priority.SCALE)) {
				mEndPosition = EndPosition.OPPOSITE_SCALE;
			}
			else {
				mEndPosition = EndPosition.SWITCH;
			}
			break;
		case "RR":
			if (mStartPosition == StartPosition.CENTER) {
				mEndPosition = EndPosition.RIGHT_SWITCH;
			}
			else if (mPriority == Priority.SCALE) {
				mEndPosition = EndPosition.SCALE;
			}
			else {
				mEndPosition = EndPosition.SWITCH;
			}
			break;
		}
    	System.out.println("Selected EndPosition: " + mEndPosition);
    }
    
    private synchronized void reverseGameData() {
       String temp = mGameData;
       temp = temp.replace("L", "G");
       temp = temp.replace("R", "L");
       temp = temp.replace("G", "R");
       mGameData = temp;
   	   System.out.println("Rev: " + mGameData);
    }
    
    private synchronized void getGameData() {
    	mGameData = DriverStation.getInstance().getGameSpecificMessage();  
	}
}
	
