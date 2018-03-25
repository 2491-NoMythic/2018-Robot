package com._2491nomythic.tempest.commands.autonomous;

import com._2491nomythic.tempest.commands.CommandBase;
import com._2491nomythic.tempest.commands.cubestorage.TransportCubeTime;
import com._2491nomythic.tempest.commands.drivetrain.DrivePath;
import com._2491nomythic.tempest.commands.drivetrain.RotateDrivetrainWithGyroPID;
import com._2491nomythic.tempest.commands.shooter.RunShooterCustom;
import com._2491nomythic.tempest.settings.Constants;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;

/**
 *
 */
public class PathAutoScale extends CommandBase {
	private double[][] leftVelocitiesArray, rightVelocitiesArray, headingsArray;
	private DrivePath path;
	private TransportCubeTime fire;
	private RunShooterCustom autoShoot;
	private String gameData;
	private boolean isFinished;
	private Timer timer;

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
		isFinished = false;
    	
    	/* Retrieve GameData to select direction */
    	gameData = DriverStation.getInstance().getGameSpecificMessage();
    	
    	/* Select side based on gameData */
		switch(gameData.substring(1, 2)) {
		case "L":
			leftVelocitiesArray = Constants.leftVelocitiesATcenterPosFORleftSwitch;
			rightVelocitiesArray = Constants.rightVelocitiesATcenterPosFORleftSwitch;
			headingsArray = Constants.anglesATcenterPosFORleftSwitch;
			break;
		case "R":
			leftVelocitiesArray = Constants.leftVelocitiesATrightPosFORrightScale;
			rightVelocitiesArray = Constants.rightVelocitiesATrightPosFORrightScale;
			headingsArray = Constants.anglesATrightPosFORrightScale;
			break;
		default:
			System.out.println("Unexpected value for GameSpecificMessage: " + gameData);
			end();
			break;
		}
		path = new DrivePath(leftVelocitiesArray, rightVelocitiesArray, headingsArray, true);
		timer.reset();
		path.start();	
    }

    
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    		if (path.isCompleted() && timer.get() == 0) {
    			timer.start();
    			intake.activate();
    			shooter.setScalePosition();
    			autoShoot.start();
    			
    		}
    		if (path.isCompleted() && timer.get() > 2 && !isFinished) {
				fire.start();
				isFinished = true;
    		}
    		
    }

    
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
       return path.isCompleted() && timer.get() > 4;
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
