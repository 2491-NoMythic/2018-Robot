package com._2491nomythic.tempest.commands.climber;

import com._2491nomythic.tempest.commands.CommandBase;
import com._2491nomythic.tempest.settings.ControllerMap;

import edu.wpi.first.wpilibj.DriverStation;

/**
 *Has the climber progress through climb preparation and execution phases based on the number of times the button has been pressed.
 */
public class Climb extends CommandBase {
	
	private double speedL, speedR;
	private int timesPressed;
	private boolean held;

    /**
     * Has the climber progress through climb preparation and execution phases based on the number of times the button has been pressed.
     * @param speedL The speed the left side climber moves at.
     * @param speedR The speed the right side climber moves at.
     */
	public Climb(double speedL, double speedR) {
    	this.speedL = speedL;
    	this.speedR = speedR;
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	timesPressed = -1;
    	held = false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(oi.getButton(ControllerMap.operatorController, ControllerMap.climberButton)) {
    		held = true;
    		
    		if(timesPressed != 2) {
    			timesPressed++;
    		}
    	}
    	else {
    		held = false;
    	}
    	
    	switch(timesPressed) {
    	case 0:
    		if(!climber.isLineupDeployed()) {
        		climber.deployLineup();
    		}
    		break;
    	
    	case 1:
    		if(!climber.isGrappleHookLaunched()) {
        		climber.grappleHookLaunch();
    		}
    		break;
    	
    	case 2:
    		if(DriverStation.getInstance().getMatchTime() < 30 && !DriverStation.getInstance().isAutonomous()) {
    			if (drivetrain.getRollAngle() > 5) {
    				speedL = 0.9;
    			}
    			else if (drivetrain.getRollAngle() < -5) {
    				speedR *= 0.9;
    			}
    			else {
    				speedL = 1;
    				speedR = 1;
    			}
    			
    			if(held) {
    				climber.ascend(speedL, speedR);
    			}
    			else {
    				climber.ascend(0);
    			}
    		}
    		break;
    	default:
    		break;
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
    }
}
