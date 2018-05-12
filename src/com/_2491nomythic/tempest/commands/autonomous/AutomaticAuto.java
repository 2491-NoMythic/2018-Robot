package com._2491nomythic.tempest.commands.autonomous;

import com._2491nomythic.tempest.commands.CommandBase;
import com._2491nomythic.tempest.commands.ImprovedAutoIntake;
import com._2491nomythic.tempest.commands.cubestorage.TransportCubeTime;
import com._2491nomythic.tempest.commands.drivetrain.DrivePath;
import com._2491nomythic.tempest.commands.drivetrain.DriveTime;
import com._2491nomythic.tempest.commands.drivetrain.RotateDrivetrainWithGyroPID;
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
	private RotateDrivetrainWithGyroPID aimForCube, aimForScale;
	private DriveTime driveToScale, hitSwitch;
	private ImprovedAutoIntake autoIntake;
	private SetShooterSpeed mSetSwitchSpeed, mSetScaleSpeed, mSetScaleSpeed2;
	private TransportCubeTime mFireCube, mFireCubeScale, mFireCubeSwitch;
	private RunShooterCustom mRevShoot, mRevShoot2;
	private String mGameData;
	private int state;
	private boolean multiCube, goForSwitch;
	private Timer mTimer, turnTimer, raiseTimer;
	
	public static enum StartPosition {
		LEFT, CENTER, RIGHT, CROSS_LINE, LEFT_NULL, RIGHT_NULL;
	}
	
	public static enum EndPosition {
		SWITCH, LEFT_SWITCH, RIGHT_SWITCH, OPPOSITE_SWTICH, SCALE, OPPOSITE_SCALE, CROSS_LINE, BUMP_COUNTER, MAX, CUBE;
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
    	mSetScaleSpeed2 = new SetShooterSpeed(Constants.shooterMediumScaleSpeed);
    	mRevShoot = new RunShooterCustom();
    	mRevShoot2 = new RunShooterCustom();
    	mTimer = new Timer();
    	turnTimer = new Timer();
    	raiseTimer = new Timer();
    	mWaitTime = 15;
    	
    	aimForCube = new RotateDrivetrainWithGyroPID(62.5, false);
    	aimForScale = new RotateDrivetrainWithGyroPID(-62.5, false);
    	mFireCubeSwitch = new TransportCubeTime(-1, 2);
    	mFireCubeScale = new TransportCubeTime(1, 1);
    	driveToScale = new DriveTime(-0.45, 1.3);
    	hitSwitch = new DriveTime(0.4, 1);
    	mBumpCounter = new DrivePath(StartPosition.CENTER, EndPosition.BUMP_COUNTER, 4);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	multiCube = false;
    	state = 0;
    	
    	selectEndPosition(mStartPosition);
		mPath = new DrivePath(StartPosition.RIGHT_NULL, EndPosition.CUBE, 0); //mEndPosition
		mTimer.reset();
		turnTimer.reset();
		
		mPath.start();	
    }

    
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(!multiCube) {
    		
        	System.out.println(drivetrain.getRightVelocity() + ", " + drivetrain.getLeftVelocity());

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
	  	}
    }

    
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return state == 5;
    	/*if (mTimer.get() > 3) {
    		return true;;	
    	}
    	else if (mPath.isCompleted() && mTimer.get() > mWaitTime) {
    		//mFireCube.start();
			return false;
		}
    	else {
    		return false;
    	}*/
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
	
