package com._2491nomythic.tempest.commands.autonomous;

import com._2491nomythic.tempest.commands.CommandBase;
import com._2491nomythic.tempest.commands.ImprovedAutoIntake;
import com._2491nomythic.tempest.commands.autonomous.AutomaticAuto.Crossing;
import com._2491nomythic.tempest.commands.autonomous.AutomaticTwoCube.SecondCube;
import com._2491nomythic.tempest.commands.cubestorage.TransportCubeTime;
import com._2491nomythic.tempest.commands.drivetrain.DriveStraightToPositionPID;
import com._2491nomythic.tempest.commands.drivetrain.DriveTime;
import com._2491nomythic.tempest.commands.drivetrain.RotateDrivetrainWithGyroPID;
import com._2491nomythic.tempest.commands.shooter.RunShooterCustom;
import com._2491nomythic.tempest.commands.shooter.SetShooterSpeed;
import com._2491nomythic.tempest.settings.Constants;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;


/**
 *
 */
public class LeftSecondCube extends CommandBase {
	private String gameData, scaleData;
	private boolean goForSwitch, completed;
	private ImprovedAutoIntake autoIntake;
	private DriveStraightToPositionPID getCube, getToScale;
	private DriveTime hitSwitch;
	private RotateDrivetrainWithGyroPID aimForCube, aimForScale;
	private RunShooterCustom spinUp;
	private SetShooterSpeed setSpeed;
	private TransportCubeTime fireSwitch, fireScale;
	private Timer timer;
	private int state;

    public LeftSecondCube(SecondCube secondLocation) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	autoIntake = new ImprovedAutoIntake(1.3);
    	timer = new Timer();
    	
    	gameData = DriverStation.getInstance().getGameSpecificMessage();
    	fireSwitch = new TransportCubeTime(-1, 1.5);
    	fireScale = new TransportCubeTime(1, 1);
    	setSpeed = new SetShooterSpeed(Constants.shooterMediumScaleSpeed);
    	scaleData = gameData.substring(1, 2);
    	hitSwitch = new DriveTime(0.3, 1.5);
    	getCube = new DriveStraightToPositionPID(70);
    	getToScale = new DriveStraightToPositionPID(-70);
    	spinUp = new RunShooterCustom();
    	
    	if(secondLocation == SecondCube.SWITCH) {
    		goForSwitch = true;
    	}
    	else {
    		goForSwitch = false;
    	}
    	
    	if(scaleData == "L") {
    		aimForCube = new RotateDrivetrainWithGyroPID(-60, false);
    		aimForScale = new RotateDrivetrainWithGyroPID(60, false);
    	}
    	else {
    		aimForCube = new RotateDrivetrainWithGyroPID(60, false);
    		aimForScale = new RotateDrivetrainWithGyroPID(-60, false);
    	}
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	state = 0;
    	timer.reset();
    	timer.stop();
    	completed = false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(goForSwitch) {
    		switch(state) {
    		case 0:
    			aimForCube.start();
    			timer.start();
    			timer.reset();
    			state++;
    			break;
    		case 1:
    			if(!aimForCube.isRunning() || timer.get() > 1.5) {
    				aimForCube.cancel();
    				getCube.start();
    				autoIntake.start();
    				state++;
    			}
    			break;
    		case 2:
    			if((!getCube.isRunning() && !autoIntake.isRunning()) || DriverStation.getInstance().getMatchTime() <= 2.5) {
    				autoIntake.cancel();
    				getCube.cancel();
    				timer.reset();
    				shooter.setSwitchPosition();
    				state++;
    			}
    			break;
    		case 3:
    			if(timer.get() > 1) {
    				hitSwitch.start();
    				timer.reset();
    				state++;
    			}
    			break;
    		case 4:
    			if(!hitSwitch.isRunning() || timer.get() > 0.5) {
    				fireSwitch.start();
    				timer.reset();
    				state++;
    			}
    			break;
    		case 5:
    			if(timer.get() > 0.8) {
    				state++;
    				completed = true;
    			}
    			break;
    		case 6:
    			break;
    		default:
    			System.out.println("Invalid state in LeftSecondCube/Switch. State: " + state);
    			break;
    		}
    	}
    	else {
    		switch(state) {
    		case 0:
    			aimForCube.start();
    			state++;
    			timer.start();
    			timer.reset();
    			break;
    		case 1:
    			if(!aimForCube.isRunning() || timer.get() > 1.2) {
    				aimForCube.cancel();
    				getCube.start();
    				autoIntake.start();
    				state++;
    			}
    			break;
    		case 2:
    			if((!getCube.isRunning() && !autoIntake.isRunning()) || DriverStation.getInstance().getMatchTime() <= 3.5) {
    				autoIntake.cancel();
    				getCube.cancel();
    				getToScale.start();
    				state++;
    			}
    			break;
    		case 3:
    			if(!getToScale.isRunning()) {
    				aimForScale.start();
    				setSpeed.start();
    				spinUp.start();
    				timer.reset();
    				state++;
    			}
    			break;
    		case 4:
    			if(!aimForScale.isRunning() || timer.get() > 1.2) {
    				fireScale.start();
    				timer.reset();
    				state++;
    			}
    			break;
    		case 5:
    			if(!fireScale.isRunning() || timer.get() > 0.6) {
    				completed = true;
    				state++;
    			}
    			break;
    		case 6:
    			break;
    		default:
    			System.out.println("Invalid state in LeftSecondCube/Scale. State: " + state);
    			break;
    		}
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return completed;
    }

    // Called once after isFinished returns true
    protected void end() {
    	drivetrain.stop();
    	shooter.stop();
    	cubeStorage.stop();
    	intake.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	getCube.cancel();
    	getToScale.cancel();
    	hitSwitch.cancel();
    	autoIntake.cancel();
    	fireScale.cancel();
    	fireSwitch.cancel();
    	spinUp.cancel();
    	aimForCube.cancel();
    	aimForScale.cancel();
    }
}
