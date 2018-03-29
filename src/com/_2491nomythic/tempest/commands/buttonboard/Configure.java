package com._2491nomythic.tempest.commands.buttonboard;

import com._2491nomythic.tempest.commands.CommandBase;
import com._2491nomythic.tempest.commands.shooter.ToggleShooterPosition;
import com._2491nomythic.tempest.settings.ControllerMap;
import com._2491nomythic.tempest.settings.Variables;

import edu.wpi.first.wpilibj.Timer;

/**
 *
 */
public class Configure extends CommandBase {
	private int intakeHeight, shotHeight, state;
	private ToggleShooterPosition shiftShooter;
	private Timer timer;
	private boolean configuredPosition;
	

    public Configure() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	timer = new Timer();
    	shiftShooter = new ToggleShooterPosition();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	state = 0;
    	configuredPosition = false;
    	
    	intakeHeight = (int) oi.getAxis(ControllerMap.buttonBoard, ControllerMap.inputAxis);
    	shotHeight = (int) oi.getAxis(ControllerMap.buttonBoard, ControllerMap.outputAxis);
    	
    	System.out.println("Configuring for " + intakeHeight + " and " + shotHeight);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	switch(intakeHeight) {
    	case -1:
    		if(shotHeight == -1) {
    			configuredPosition = !shooter.inScalePosition();
    		}
    		else {
    			configuredPosition = shooter.inScalePosition();
    		}
    		break;
    	default:
    		configuredPosition = !shooter.inScalePosition();
    		break;
    	}
    	
    	switch(shotHeight) {
    	case -1:
    		Variables.motorChoice = 1;
    		break;
    	case 0:
    		Variables.motorChoice = 2;
    		break;
    	case 1:
    		Variables.motorChoice = 3;
    		break;
    	}
    	
    	if(!configuredPosition) {
    		switch(state) {
    		case 0:
    			intake.activate();
    			timer.start();
    			state++;
    			break;
    		case 1:
    			if(timer.get() > 0.25) {
    				shiftShooter.start();
    				timer.reset();
    				state++;
    			}
    			break;
    		case 2:
    			if(timer.get() > 0.25) {
    				if(intakeHeight != -1) {
    					intake.retract();
    				}
    				state++;
    			}
    			break;
    		case 3:
    			configuredPosition = true;
    			break;
    		default:
    			System.out.println("Invalid state in Configure. State: " + state);
    			break;
    		}
    	}
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
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
