package com._2491nomythic.tempest.commands.autonomous;

import com._2491nomythic.tempest.commands.CommandBase;
import com._2491nomythic.tempest.commands.cubestorage.TransportCubeTime;
import com._2491nomythic.tempest.commands.drivetrain.OLD_DrivePath;
import com._2491nomythic.tempest.commands.shooter.RunShooterCustom;
import com._2491nomythic.tempest.settings.Constants;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;

/**
 * @deprecated
 */
public class PathAutoScale extends CommandBase {
	private double[][] leftVelocitiesArray, rightVelocitiesArray, headingsArray;
	private OLD_DrivePath path;
	private TransportCubeTime fire;
	private RunShooterCustom autoShoot;
	private String gameData;
	private Timer timer;
	
	public enum startPosition {
		LEFT, CENTER, RIGHT
	}
	public enum priority {
		SCALE, SWITCH
	}
	public enum override {
		OFF, SCALE, FORCE_SCALE
	}
	
	public startPosition mStartPosition;
	public priority mPriority;
	public override mOverride;
	
	/**
	 * @deprecated
	 */
    public PathAutoScale() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    		autoShoot = new RunShooterCustom();
    		fire = new TransportCubeTime(1, 1.5);
    		timer = new Timer();
    		new Timer();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	/* Prepare robot superStructure*/
    	/* Retrieve GameData to select direction */
    	gameData = DriverStation.getInstance().getGameSpecificMessage();
    	//gameData.substring(1, 2)
    	
    	switch(mStartPosition) {
    	case LEFT:
    		break;
    	case CENTER:
    		break;
    	case RIGHT:	
    		break;
    	}
    	
    	switch(mPriority) {
    	case SCALE:
    		break;
    	case SWITCH:
    		break;
    	}
    	
    	switch(mOverride) {
    	case OFF:
    		break;
    	case SCALE:
    		break;
    	case FORCE_SCALE:
    		break;
    	}
    	
    	/* Select side based on gameData */
		switch(gameData) {
		case "L":
			leftVelocitiesArray = Constants.leftVelocitiesTO_SCALE;
			rightVelocitiesArray = Constants.rightVelocitiesTO_SCALE;
			headingsArray = Constants.headingsTO_SCALE;
			break;
		case "R":
			leftVelocitiesArray = Constants.leftVelocitiesTO_SCALE;
			rightVelocitiesArray = Constants.rightVelocitiesTO_SCALE;
			headingsArray = Constants.headingsTO_SCALE;
			break;
		default:
			System.out.println("Unexpected value for GameSpecificMessage: " + gameData);
			end();
			break;
		}
    	
		path = new OLD_DrivePath(leftVelocitiesArray, rightVelocitiesArray, headingsArray, false , true);
		timer.reset();
		path.start();	
    }

    
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    		if (path.isCompleted() && timer.get() == 0) {
    			timer.start();
    			intake.deploy();
    			shooter.setScalePosition();
    			autoShoot.start();
    			
    		}
    		
    }

    
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if (fire.isCompleted() && timer.get() > 2.02) {
    		return true;	
    	}
    	else if (path.isCompleted() && timer.get() > 2) {
			fire.start();
			return false;
		}
    	else {
    		return false;
    	}
    }
    

    // Called once after isFinished returns true
    protected void end() {
    	timer.stop();
    	timer.reset();
    	autoShoot.cancel();
    	drivetrain.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
