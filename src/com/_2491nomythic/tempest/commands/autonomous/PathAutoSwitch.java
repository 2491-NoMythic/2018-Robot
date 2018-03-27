package com._2491nomythic.tempest.commands.autonomous;

import com._2491nomythic.tempest.commands.CommandBase;
import com._2491nomythic.tempest.commands.cubestorage.TransportCubeTime;
import com._2491nomythic.tempest.commands.drivetrain.OLD_DrivePath;
import com._2491nomythic.tempest.settings.Constants;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;

/**
 *
 */
public class PathAutoSwitch extends CommandBase {
	private double[][] leftVelocitiesArray, rightVelocitiesArray, headingsArray;
	
	private TransportCubeTime autoShoot;
	private String gameData;
	private OLD_DrivePath path;
	private Timer timer;

    public PathAutoSwitch() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    		autoShoot = new TransportCubeTime(-1, 1);
    		timer = new Timer();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
		intake.deploy();
    	
    	/* Reset Variables */
    	
    	/* Retrieve GameData to select direction */
    	gameData = DriverStation.getInstance().getGameSpecificMessage();
    	
    	/* Select side based on gameData */
		switch(gameData.substring(0, 1)) {
		case "L":
			leftVelocitiesArray = Constants.leftVelocitiesTO_LEFT_SWITCH;
			rightVelocitiesArray = Constants.headingsTO_LEFT_SWITCH;
			headingsArray = Constants.headingsTO_LEFT_SWITCH;
			break;
		case "R":
			leftVelocitiesArray = Constants.leftVelocitiesTO_RIGHT_SWITCH;
			rightVelocitiesArray = Constants.rightVelocitiesTO_RIGHT_SWITCH;
			headingsArray = Constants.headingsTO_RIGHT_SWITCH;
			break;
		default:
			System.out.println("Unexpected value for GameSpecificMessage: " + gameData);
			end();
			break;
		}
		path = new OLD_DrivePath(leftVelocitiesArray, rightVelocitiesArray, headingsArray, false , false);
		timer.reset();
		path.start();
    }

    
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(path.isCompleted() && timer.get() == 0) {
    		autoShoot.start();
    		timer.start();
    	}
    }

    
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
       return autoShoot.isCompleted() && timer.get() > 1.1;
    }
    

    // Called once after isFinished returns true
    protected void end() {
    		drivetrain.stop();
    		timer.stop();
    		timer.reset();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    		end();
    }
}
