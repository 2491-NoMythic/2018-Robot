package com._2491nomythic.tempest.commands.buttonboard;

import com._2491nomythic.tempest.commands.CommandBase;
import com._2491nomythic.tempest.commands.KillSwitch;
import com._2491nomythic.tempest.commands.shooter.RunShooterManual;
import com._2491nomythic.tempest.settings.Constants;
import com._2491nomythic.tempest.settings.ControllerMap;
import com._2491nomythic.tempest.settings.Variables;

import edu.wpi.first.wpilibj.Timer;

/**
 *
 */
public class SpinUp extends CommandBase {
	private boolean button1, button2, cancel, released;
	private RunShooterManual spinUp;
	private KillSwitch endPlease;
	private Timer delay;

    public SpinUp() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	delay = new Timer();
    	spinUp = new RunShooterManual();
    	endPlease = new KillSwitch();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	cancel = false;
    	released = false;
    	
    	button1 = oi.getButton(ControllerMap.buttonBoard, ControllerMap.scaleSpeedButton1);
    	button2 = oi.getButton(ControllerMap.buttonBoard, ControllerMap.scaleSpeedButton2);
    	
    	if(button1 && !button2) {
    		Variables.shooterSpeed = Constants.shooterLowScaleSpeed;
    		Variables.leftShootSpeed = Constants.shooterLowScaleSpeed;
    		Variables.rightShootSpeed = Constants.shooterLowScaleSpeed;
    	}
    	else if(!button1 && button2) {
    		Variables.shooterSpeed = Constants.shooterHighScaleSpeed;
    		Variables.leftShootSpeed = Constants.shooterHighScaleSpeed;
    		Variables.rightShootSpeed = Constants.shooterHighScaleSpeed;
    	}
    	else if(!button1 && !button2) {
    		Variables.shooterSpeed = Constants.shooterMediumScaleSpeed;
    		Variables.leftShootSpeed = Constants.shooterMediumScaleSpeed;
    		Variables.rightShootSpeed = Constants.shooterMediumScaleSpeed;
    	}
    	else {
    		System.out.println("Check button board connections.");
    	}
    	
    	spinUp.start();
    	delay.start();
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(oi.getButton(ControllerMap.buttonBoard, ControllerMap.spinUpButton)) {
    		if(delay.get() > 0.1 && released) {
    			released = false;
    			cancel = true;
    		}
    	}
    	else {
    		released = true;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return cancel;
    }

    // Called once after isFinished returns true
    protected void end() {
    	System.out.println("Running end");
    	spinUp.cancel();
    	shooter.stop();
    	
    	endPlease.start();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
