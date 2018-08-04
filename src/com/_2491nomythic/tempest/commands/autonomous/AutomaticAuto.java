package com._2491nomythic.tempest.commands.autonomous;

import com._2491nomythic.tempest.commands.CommandBase;
import com._2491nomythic.tempest.commands.cubestorage.TransportCubeTime;
import com._2491nomythic.tempest.commands.drivetrain.DrivePath;
import com._2491nomythic.tempest.commands.drivetrain.DriveTime;
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
	private DrivePath mPath;
	private DriveTime hitSwitch;
	private SetShooterSpeed mSetSwitchSpeed, mSetScaleSpeed;
	private TransportCubeTime mFireCubeScale, mFireCubeSwitch;
	private RunShooterCustom mRevShoot, mRevShoot2;
	private String mGameData;
	private boolean timerSafety;
	private Timer mTimer;
	
	public static enum StartPosition {
		LEFT, CENTER, RIGHT, CROSS_LINE, LEFT_NULL, RIGHT_NULL, LEFT_CUBE, RIGHT_CUBE, LEFT_SWITCH, RIGHT_SWITCH, LEFT_BACKUP, RIGHT_BACKUP, LEFT_PYRAMID, RIGHT_PYRAMID;
	}
	
	public static enum EndPosition {
		SWITCH, LEFT_SWITCH, RIGHT_SWITCH, OPPOSITE_SWTICH, SCALE, OPPOSITE_SCALE, CROSS_LINE, BUMP_COUNTER, MAX, CUBE, NULL, BACKUP, LEFT_PYRAMID, RIGHT_PYRAMID, SECOND_LEFT_SWITCH, SECOND_RIGHT_SWITCH;
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
    	mRevShoot2 = new RunShooterCustom();
    	mTimer = new Timer();
    	mWaitTime = 15;
    	
    	mFireCubeSwitch = new TransportCubeTime(-1, 2);
    	mFireCubeScale = new TransportCubeTime(1, 1);
    	hitSwitch = new DriveTime(0.2, 0.2, 1);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	timerSafety = false;
    	
    	selectEndPosition(mStartPosition);

    	if(mEndPosition == EndPosition.LEFT_SWITCH || mEndPosition == EndPosition.RIGHT_SWITCH || mEndPosition == EndPosition.CROSS_LINE || mEndPosition == EndPosition.SWITCH || mEndPosition == EndPosition.OPPOSITE_SCALE) {
    		mPath = new DrivePath(mStartPosition, mEndPosition, 4, false);
    	}
    	else {
    		mPath = new DrivePath(mStartPosition, mEndPosition, 0, false); //mEndPosition
    	}
		mTimer.reset();
		mTimer.stop();
		
		mPath.start();	
    }

    
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {	
	    switch(mEndPosition) {	    	
	    	case OPPOSITE_SCALE:
	    		if(mPath.getCurrentStep() == Pathing.getVelocityArray("leftVelocitiesTO_OPPOSITE_SCALE").length - 12 || mPath.getCurrentStep() == Pathing.getVelocityArray("leftVelocitiesTO_OPPOSITE_SCALE").length - 13) {
		   			intake.openArms();
		   			shooter.setScalePosition();
		   			mWaitTime = 0.1;
		   		} else if(mPath.getCurrentStep() == Pathing.getVelocityArray("leftVelocitiesTO_OPPOSITE_SCALE").length - 17 || mPath.getCurrentStep() == Pathing.getVelocityArray("leftVelocitiesTO_OPPOSITE_SCALE").length - 18) {
		   			mSetScaleSpeed.start();
		   			mRevShoot.start();
		   		}
		   		break;
		   	case SCALE:		    		
		   		if(mPath.getCurrentStep() == Pathing.getVelocityArray("leftVelocitiesTO_SCALE").length - 60 || mPath.getCurrentStep() == Pathing.getVelocityArray("leftVelocitiesTO_SCALE").length - 61) {	
		   			intake.openArms();
		   			shooter.setScalePosition();
		   			mWaitTime = 0.1;
		   		} else if(mPath.getCurrentStep() == Pathing.getVelocityArray("leftVelocitiesTO_SCALE").length - 85 || mPath.getCurrentStep() == Pathing.getVelocityArray("leftVelocitiesTO_SCALE").length - 86) {
		   			mSetScaleSpeed.start();		    			
	    			mRevShoot.start();
	    		}
	    		break;
	    	case NULL:
	    		if(mPath.getCurrentStep() == Pathing.getVelocityArray("leftVelocitiesTO_NULL").length - 60 || mPath.getCurrentStep() == Pathing.getVelocityArray("leftVelocitiesTO_NULL").length - 61) {	
	    			intake.openArms();
	    			shooter.setScalePosition();
	    			mWaitTime = 0.1;
	    		} else if(mPath.getCurrentStep() == Pathing.getVelocityArray("leftVelocitiesTO_NULL").length - 85 || mPath.getCurrentStep() == Pathing.getVelocityArray("leftVelocitiesTO_NULL").length - 86) {
	    			mSetScaleSpeed.start();
	    			mRevShoot.start();
	    		}
	    	default:
	    		break;
	   	}
    	
    	if(mPath.isCompleted() && !timerSafety) {    		
    		switch(mEndPosition) {
    		case LEFT_SWITCH:
    		case RIGHT_SWITCH:
    			mFireCubeSwitch.start();
    			hitSwitch.start();
    			break;
    		case CROSS_LINE:
    			break;
    		case SWITCH:
    			mSetSwitchSpeed.start();
    			mRevShoot2.start();
    			mFireCubeScale.start();
    			break;
    		case OPPOSITE_SCALE:
    		case SCALE:
    			mFireCubeScale.start();
    			break;
    		default:
    			break;    			
    		}
    		
			timerSafety = true;
			mTimer.start();

    	}
    }

    
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {    	
    	return mTimer.get() > 2;
    }
    

    // Called once after isFinished returns true
    protected void end() {
    	System.out.println("AutomaticAuto ending");
    	mTimer.stop();
    	mTimer.reset();
    	mRevShoot.cancel();
    	mRevShoot2.cancel();
    	mFireCubeScale.cancel();
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
    	default:
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
    
    public EndPosition getEndPosition() {
    	return mEndPosition;
    }
}
	
