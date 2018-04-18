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
	private boolean held, released;

    /**
     * Has the climber progress through climb preparation and execution phases based on the number of times the button has been pressed.
     * @param speedL The speed the left side climber moves at.
     * @param speedR The speed the right side climber moves at.
     */
	public Climb(double speedL, double speedR) {
		requires(climber);
    	this.speedL = speedL;
    	this.speedR = speedR;
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	timesPressed = -1;
    	held = false;
    	released = true;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(oi.getButton(ControllerMap.driveController, ControllerMap.climberButton)) {
    		held = true;
    		
    		if(timesPressed != 2 && released) {
    			timesPressed++;
    			released = false;
    		}
    	}
    	else {
    		held = false;
    		released = true;
    		
    	}
    	
    	switch(timesPressed) {
    	case 0:
    		if(!climber.isLineupDeployed()) {
        		System.out.println("Deploying lineup system");
        		climber.deployLineup();
    		}
    		else {
    			System.out.println("Lineup Deployed");
    		}
    		break;
    	
    	case 1:
    		if(!climber.isGrappleHookLaunched()) {
    			System.out.print("Launching grappling hook");
        		climber.grappleHookLaunch();
    		}
    		else {
    			System.out.println("Hook launched!");
    		}
    		break;
    	
    	case 2:
    		if(DriverStation.getInstance().getMatchTime() < 30 && !DriverStation.getInstance().isAutonomous()) {
    			if (drivetrain.getPitchAngle() > 5) {
    				speedL = 0.9;
    				System.out.println("Tilting to starboard");
    			}
    			else if (drivetrain.getPitchAngle() < -5) {
    				speedR = 0.9;
    				System.out.println("Tilting to port");
    			}
    			else {
    				speedL = 1;
    				speedR = 1;
    			}
    			
    			if(held) {
    				climber.ascend(speedL, speedR);
    				System.out.println("Going up");
    			}
    			else {
    				climber.ascend(0);
    				System.out.println("Not going up");
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
    	climber.ascend(0);
    	System.out.println("We have stopped and now appear to be done. Have a good day.");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
