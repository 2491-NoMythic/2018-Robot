package com._2491nomythic.tempest.commands.buttonboard;

import com._2491nomythic.tempest.commands.CommandBase;
import com._2491nomythic.tempest.commands.shooter.ToggleShooterPosition;
import com._2491nomythic.tempest.settings.ControllerMap;

import edu.wpi.first.wpilibj.Timer;

/**
 *
 */
public class Input extends CommandBase {
	private int intakeHeight, state;
	private boolean configuredPosition;
	private ToggleShooterPosition shiftShooter;
	private Timer timer;

    public Input() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(intake);
    	requires(cubeStorage);
    	
    	timer = new Timer();
    	shiftShooter = new ToggleShooterPosition();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	intakeHeight = (int) oi.getAxis(ControllerMap.buttonBoard, ControllerMap.inputAxis);
    	
    	switch(intakeHeight) {
    	case -1:
    		configuredPosition = !shooter.inSwitchPosition();
    		break;
    	default:
    		configuredPosition = shooter.inSwitchPosition();
    		break;
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(configuredPosition) {
    		switch(intakeHeight) {
    		case -1:
    			if(!cubeStorage.isHeld()) {
    				intake.run(1, 1);
    				cubeStorage.run(1);
    			}
    			else {
    				shooter.setSwitchPosition();
    			}
    			break;
    		default:
    			if(!cubeStorage.isHeld()) {
    				shooter.run(-1);
    			}
    			break;
    		}
    	}
    	else {
    		switch(state) {
    		case 0:
    			intake.deploy();
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
        return cubeStorage.isHeld();
    }

    // Called once after isFinished returns true
    protected void end() {
    	intake.stop();
    	cubeStorage.stop();
    	timer.stop();
    	timer.reset();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
