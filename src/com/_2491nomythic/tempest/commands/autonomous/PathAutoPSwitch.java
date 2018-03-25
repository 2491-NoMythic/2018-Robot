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
public class PathAutoPSwitch extends CommandBase {
	private double[][] leftVelocitiesArray, rightVelocitiesArray, headingsArray;
	
	private TransportCubeTime fire;
	private RunShooterCustom autoShoot;
	private String gameData;
	private DrivePath path;
	private Timer timer;
	private SetShooterSpeed switchSpeed;
	private RotateDrivetrainWithGyroPID rotate;
	private boolean check;

    public PathAutoPSwitch() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	switchSpeed = new SetShooterSpeed(Constants.shooterMediumScaleSpeed);
		autoShoot = new RunShooterCustom();
   		fire = new TransportCubeTime(1, 1);
    	timer = new Timer();
    	rotate = new RotateDrivetrainWithGyroPID(-10,false);   	
    }

    // Called just before this Command runs the first time
    protected void initialize() {    	
    	/* Reset Variables */
    	switchSpeed.start();
    	check = false;
    	
    	/* Retrieve GameData to select direction */
    	gameData = DriverStation.getInstance().getGameSpecificMessage();
    	
    	/* Select side based on gameData */
		switch(gameData.substring(0, 1)) {
		case "L":
			leftVelocitiesArray = Constants.leftVelocitiesATrightPosFORleftScale;
			rightVelocitiesArray = Constants.rightVelocitiesATrightPosFORleftScale;
			headingsArray = Constants.anglesATrightPosFORleftScale;
			break;
		case "R":
			leftVelocitiesArray = Constants.leftVelocitiesATrightPosFORrightSwitch;
			rightVelocitiesArray = Constants.rightVelocitiesATrightPosFORrightSwtich;
			headingsArray = Constants.anglesATrightPosFORrightSwitch;
			break;
		default:
			System.out.println("Unexpected value for GameSpecificMessage: " + gameData);
			end();
			break;
		}
		path = new DrivePath(leftVelocitiesArray, rightVelocitiesArray, headingsArray, true,  true);
		timer.reset();
		path.start();
    }

    
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(path.isCompleted() && timer.get() == 0) {
    		autoShoot.start();
        	intake.deploy();
        	shooter.setScalePosition();
        	timer.start();	
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
    		autoShoot.cancel();
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
