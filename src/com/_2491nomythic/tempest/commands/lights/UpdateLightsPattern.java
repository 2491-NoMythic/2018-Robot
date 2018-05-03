package com._2491nomythic.tempest.commands.lights;

import com._2491nomythic.tempest.commands.CommandBase;
import com._2491nomythic.tempest.settings.Constants;
import com._2491nomythic.tempest.settings.Variables;

import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.Timer;

/**
 *
 */
public class UpdateLightsPattern extends CommandBase {
	private Timer sonicTimer, readyToFireTimer;
	private boolean newCube, readyEnough;
	private int data;

    public UpdateLightsPattern() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	sonicTimer = new Timer();
    	readyToFireTimer = new Timer();
    	
    	setRunWhenDisabled(true);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	newCube = false;
    	readyEnough = false;
    	sonicTimer.start();
    	readyToFireTimer.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(!cubeStorage.isHeld()) {
    		newCube = true;
    		sonicTimer.reset();
    	}
    	else {
    		if(sonicTimer.get() > 2) {
    			newCube = false;
    		}
    	}
    	
    	if(Variables.readyToFire && cubeStorage.isHeld()) {
    		readyToFireTimer.reset();
    	}
    	else {
    		if(readyToFireTimer.get() >= 0.5) {
    			readyEnough = false;
    		}
    	}
    	
    	if(Variables.readyToFire || readyEnough) {
    		readyEnough = true;
    		data = 9;
    	}
    	else if(shooter.getShootVelocity() >= 1) {
    		if(Variables.shooterSpeed > Constants.shooterHighScaleSpeed - 0.03) {
    			data = 7;
    		}
    		else if(Variables.shooterSpeed > Constants.shooterMediumScaleSpeed - 0.03) {
    			data = 6;
    		}
    		else if(Variables.shooterSpeed > Constants.shooterLowScaleSpeed - 0.03) {
    			data = 5;
    		}
    		else {
    			data = 4;
    		}
    	}
    	else if(shooter.getShootVelocity() <= -1) {
    		data = 3;
    	}
    	else if(cubeStorage.isHeld() && newCube) {
    		data = 8;
    	}
    	else {
    		data = 0;
    	}
    	
    	if(RobotController.getBatteryVoltage() <= 7.5 || !Variables.useLights) {
    		data = 100;
    		//sickLights.writeData(data);
    	}
    	else {
        	//sickLights.writeData(data);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}

