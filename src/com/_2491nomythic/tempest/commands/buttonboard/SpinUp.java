package com._2491nomythic.tempest.commands.buttonboard;

import com._2491nomythic.tempest.commands.CommandBase;
import com._2491nomythic.tempest.commands.shooter.RunShooterManual;
import com._2491nomythic.tempest.settings.Constants;
import com._2491nomythic.tempest.settings.ControllerMap;
import com._2491nomythic.tempest.settings.Variables;

import edu.wpi.first.wpilibj.Timer;

/**
 *
 */
public class SpinUp extends CommandBase {
	private boolean button1, button2;
	private RunShooterManual spinUp;
	private Timer timer;

    public SpinUp() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	timer = new Timer();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	button1 = oi.getButton(ControllerMap.operatorController, ControllerMap.scaleSpeedButton1);
    	button2 = oi.getButton(ControllerMap.operatorController, ControllerMap.scaleSpeedButton2);
    	
    	if(button1 && !button2) {
    		Variables.shooterSpeed = Constants.shooterLowScaleSpeed;
    	}
    	else if(!button1 && button2) {
    		Variables.shooterSpeed = Constants.shooterHighScaleSpeed;
    	}
    	else if(!button1 && !button2) {
    		Variables.shooterSpeed = Constants.shooterMediumScaleSpeed;
    	}
    	else {
    		System.out.println("Check button board connections.");
    	}
    	
    	spinUp = new RunShooterManual();
    	spinUp.start();
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(!cubeStorage.isHeld()) {
    		timer.start();
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return timer.get() > 2;
    }

    // Called once after isFinished returns true
    protected void end() {
    	shooter.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
