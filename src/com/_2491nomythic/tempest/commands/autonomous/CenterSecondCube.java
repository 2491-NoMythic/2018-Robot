package com._2491nomythic.tempest.commands.autonomous;

import com._2491nomythic.tempest.commands.CommandBase;
import com._2491nomythic.tempest.commands.ImprovedAutoIntake;
import com._2491nomythic.tempest.commands.autonomous.AutomaticAuto.EndPosition;
import com._2491nomythic.tempest.commands.autonomous.AutomaticAuto.StartPosition;
import com._2491nomythic.tempest.commands.cubestorage.TransportCubeTime;
import com._2491nomythic.tempest.commands.drivetrain.DrivePath;
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
public class CenterSecondCube extends CommandBase {
	private ImprovedAutoIntake autoIntake;
	private DriveTime bumpCounter;
	private RunShooterCustom spinUp;
	private SetShooterSpeed setSpeed;
	private TransportCubeTime fire;
	private DrivePath backup, getCube, getToSwitch;
	private String gameData;
	private int state;
	private Timer timer;

    public CenterSecondCube() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	timer = new Timer();
    	bumpCounter = new DriveTime(-0.2, -0.2, 1);
    	setSpeed = new SetShooterSpeed(Constants.shooterSwitchSpeed);
    	spinUp = new RunShooterCustom();
    	fire = new TransportCubeTime(1, 0.8);
    	backup = new DrivePath(StartPosition.LEFT_SWITCH, EndPosition.BACKUP, 0, false);
    	autoIntake = new ImprovedAutoIntake(0.5, false);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	timer.reset();
    	state = 0;
    	gameData = DriverStation.getInstance().getGameSpecificMessage();
    	
    	switch(gameData.substring(0, 1)) {
    	case "L":
    		getToSwitch = new DrivePath(StartPosition.LEFT_PYRAMID, EndPosition.LEFT_SWITCH, 0, false);
    		getCube = new DrivePath(StartPosition.LEFT_BACKUP, EndPosition.LEFT_PYRAMID, 0, false);
    		break;
    	case "R":
    		getToSwitch = new DrivePath(StartPosition.RIGHT_PYRAMID, EndPosition.RIGHT_SWITCH, 0, false);
    		getCube = new DrivePath(StartPosition.RIGHT_BACKUP, EndPosition.RIGHT_PYRAMID, 0, false);
    		break;
    	default:
    		System.out.println("Invalid GameSpecificMessage in CenterSecondCube.");
    		break;
    	}  	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	switch(state) {
    	case 0:
    		backup.start();
    		break;
    	case 1:
    		if(!backup.isRunning()) {
    			intake.openArms();
    			shooter.setScalePosition();
    			//getCube.start();
    			//autoIntake.start();
    			state++;
    		}
    		break;
    	case 2:
    		/*if(!autoIntake.isRunning() || DriverStation.getInstance().getMatchTime() <= 4) {
    			autoIntake.cancel();
    			getToSwitch.start();
    			setSpeed.start();
    			spinUp.start();
    			timer.start();
    			timer.reset();
    			state++;
    		}*/
    		break;
    	case 3:
    		if(!getToSwitch.isRunning() || DriverStation.getInstance().getMatchTime() <= 1) {
    			bumpCounter.start();
    			fire.start();
    			state++;
    		}
    		break;
    	case 4:
    		break;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return state == 4 && !fire.isRunning();
    }

    // Called once after isFinished returns true
    protected void end() {
    	timer.stop();
    	backup.cancel();
    	fire.cancel();
    	spinUp.cancel();
    	bumpCounter.cancel();
    	getToSwitch.cancel();
    	getCube.cancel();
    	autoIntake.cancel();
    	shooter.stop();
    	cubeStorage.stop();
    	intake.stop();
    	drivetrain.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
