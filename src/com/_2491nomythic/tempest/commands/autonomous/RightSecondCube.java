package com._2491nomythic.tempest.commands.autonomous;

import com._2491nomythic.tempest.commands.CommandBase;
import com._2491nomythic.tempest.commands.ImprovedAutoIntake;
import com._2491nomythic.tempest.commands.autonomous.AutomaticAuto.EndPosition;
import com._2491nomythic.tempest.commands.autonomous.AutomaticAuto.StartPosition;
import com._2491nomythic.tempest.commands.autonomous.AutomaticTwoCube.SecondCube;
import com._2491nomythic.tempest.commands.cubestorage.TransportCubeTime;
import com._2491nomythic.tempest.commands.drivetrain.DrivePath;
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
public class RightSecondCube extends CommandBase {
	private String gameData, scaleData;
	private boolean goForSwitch, completed, shooterSafety;
	private ImprovedAutoIntake autoIntake;
	private DrivePath getCube, getToScale;
	private DriveTime bumpCounter;
	private RunShooterCustom spinUp;
	private SetShooterSpeed setSpeed;
	private TransportCubeTime fireSwitch, fireScale;
	private Timer timer;
	private int state;

    public RightSecondCube(SecondCube secondLocation) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	autoIntake = new ImprovedAutoIntake(3.6, false);
    	timer = new Timer();
    	
    	gameData = DriverStation.getInstance().getGameSpecificMessage();
    	fireSwitch = new TransportCubeTime(-1, 1.5);
    	setSpeed = new SetShooterSpeed(Constants.shooterMediumScaleSpeed);
    	fireScale = new TransportCubeTime(1, 1);
    	spinUp = new RunShooterCustom();
    	scaleData = gameData.substring(1, 2);
    	getCube = new DrivePath(StartPosition.RIGHT_NULL, EndPosition.CUBE, 0, false);
    	getToScale = new DrivePath(StartPosition.RIGHT_CUBE, EndPosition.NULL, 0, false);
    	bumpCounter = new DriveTime(0.3, 0.3, 0.8);
    	
    	if(secondLocation == SecondCube.SWITCH) {
    		goForSwitch = true;
    	}
    	else {
    		goForSwitch = false;
    	}

    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	state = 1;
    	timer.reset();
    	shooterSafety = true;
    	timer.stop();
    	completed = false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(goForSwitch) {    		
    		switch(state) {
    		case 1:
    			getCube.start();
    			autoIntake.start();
    			state++;
    			break;
    		case 2:
    			if(!autoIntake.isRunning()) { //|| DriverStation.getInstance().getMatchTime() <= 2.5) {
    				System.out.println("Intook!");
    				intake.retractFingers();
    				getCube.cancel();
    				timer.start();
    				timer.reset();
    				shooter.setSwitchPosition();
    				state++;
    			}
    			break;
    		case 3:
    			if(timer.get() > 1.5) {
    				intake.retractArms();
    				bumpCounter.start();
    				timer.reset();
    				state++;
    			}
    			break;
    		case 4:
    			if(timer.get() >= 0.8) {
    				fireSwitch.start();
    				timer.reset();
    				state++;
    			}
    			break;
    		case 5:
    			if(timer.get() > 0.8) {
    				completed = true;
    				state++;
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
    		case 1:
    			getCube.start();
    			autoIntake.start();
    			state++;
    			break;
    		case 2:
    			if(!autoIntake.isRunning()) {// || DriverStation.getInstance().getMatchTime() <= 3.5) {
    				autoIntake.cancel();
    				getCube.cancel();
    				getToScale.start();
    				timer.start();
    				timer.reset();
    				state++;
    			}
    			break;
    		case 3:
    			if(timer.get() > 0.5 && shooterSafety) {
    				shooterSafety = false;
    				spinUp.start();
    			}
    			
    			if(!getToScale.isRunning()) {
    				fireScale.start();
    				timer.reset();
    				state++;
    			}
    			break;
    		case 4:
    			if(timer.get() > 1) {
    				completed = true;
    				state++;
    			}
    			break;
    		case 5:
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
    	bumpCounter.cancel();
    	autoIntake.cancel();
    	fireSwitch.cancel();
    }
}

