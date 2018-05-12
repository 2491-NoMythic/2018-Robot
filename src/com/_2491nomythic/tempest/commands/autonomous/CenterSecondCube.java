package com._2491nomythic.tempest.commands.autonomous;

import com._2491nomythic.tempest.commands.CommandBase;
import com._2491nomythic.tempest.commands.ImprovedAutoIntake;
import com._2491nomythic.tempest.commands.drivetrain.DriveStraightToPositionPID;
import com._2491nomythic.tempest.commands.drivetrain.RotateDrivetrainWithGyroPID;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;

/**
 *
 */
public class CenterSecondCube extends CommandBase {
	private DriveStraightToPositionPID backAwayFromSwitch, getCube, getBack, approachSwitch;
	private RotateDrivetrainWithGyroPID aimForCubes, aimForSwitch;
	private ImprovedAutoIntake autoIntake;
	private String gameData;
	private int state;
	private Timer timer;

    public CenterSecondCube() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	timer = new Timer();
    	backAwayFromSwitch = new DriveStraightToPositionPID(-10);
    	getCube = new DriveStraightToPositionPID(40);
    	autoIntake = new ImprovedAutoIntake(0.5);
    	getBack = new DriveStraightToPositionPID(-40);
    	approachSwitch = new DriveStraightToPositionPID(20);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	state = 0;
    	gameData = DriverStation.getInstance().getGameSpecificMessage();
    	
    	switch(gameData.substring(0, 1)) {
    	case "L":
    		aimForCubes = new RotateDrivetrainWithGyroPID(90, false);
    		aimForSwitch = new RotateDrivetrainWithGyroPID(-90, false);
    		break;
    	case "R":
    		aimForCubes = new RotateDrivetrainWithGyroPID(-90, false);
    		aimForSwitch = new RotateDrivetrainWithGyroPID(90, false);
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
    		backAwayFromSwitch.start();
    		state++;
    		break;
    	case 1:
    		if(!backAwayFromSwitch.isRunning()) {
    			aimForCubes.start();
    			intake.openArms();
    			shooter.setScalePosition();
    			timer.start();
    			timer.reset();
    			state++;
    		}
    		break;
    	case 2:
    		if(!aimForCubes.isRunning() || timer.get() > 1.2) {
    			aimForCubes.cancel();
    			getCube.start();
    			autoIntake.start();
    			state++;
    		}
    		break;
    	case 3:
    		if(!getCube.isRunning()) {
    			getBack.start();
    			state++;
    		}
    		break;
    	case 4:
    		if(!getBack.isRunning()) {
    			shooter.setSwitchPosition();
    			timer.reset();
    			aimForSwitch.start();
    			autoIntake.cancel();
    			state++;
    		}
    		break;
    	case 5:
    		if(!aimForSwitch.isRunning() || timer.get() > 1.2) {
    			aimForSwitch.cancel();
    			intake.retractArms();
    			timer.reset();
    			approachSwitch.start();
    			state++;
    		}
    		break;
    	case 6:
    		if(!approachSwitch.isRunning() || timer.get() > 1) {
    			timer.reset();
    			cubeStorage.run(-1);
    			state++;
    		}
    		break;
    	case 7:
    		break;
    	default:
    		System.out.println("Invalid state in CenterSecondCube. State: " + state);
    		break;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return state == 7 && timer.get() > 1.5;
    }

    // Called once after isFinished returns true
    protected void end() {
    	backAwayFromSwitch.cancel();
    	getCube.cancel();
    	getBack.cancel();
    	autoIntake.cancel();
    	approachSwitch.cancel();
    	aimForCubes.cancel();
    	aimForSwitch.cancel();
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
