package com._2491nomythic.tempest.commands.lights;

import com._2491nomythic.tempest.commands.CommandBase;
import com._2491nomythic.tempest.settings.Variables;

import edu.wpi.first.wpilibj.Timer;

/**
 *
 */
public class UpdateLights extends CommandBase {
	private boolean newCube;
	private Timer timer, sonicTimer;
	private int state;

    public UpdateLights() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	setRunWhenDisabled(true);
    	timer = new Timer();
    	sonicTimer = new Timer();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	timer.start();
    	sonicTimer.start();
    	state = 0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(cubeStorage.isHeld()) {
    		if(sonicTimer.get() < 3) {
    			newCube = true;
    		}
    		else {
    			newCube = false;
    		}
    	}
    	
    	if(Variables.readyToFire || newCube) {    		
    		switch(state) {
    		case 0:
    			sickLights.set(false);
    			if(timer.get() > 0.5) {
    				timer.reset();
    				state++;
    			}
    			break;
    		case 1:
    			sickLights.set(true);
    			if(timer.get() > 0.5) {
    				timer.reset();
    				state++;
    			}
    			
    			break;
    		default:
    			break;
    		}
    	}
    	else {
    		sickLights.set(true);
    		state = 0;
    		sonicTimer.reset();
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
