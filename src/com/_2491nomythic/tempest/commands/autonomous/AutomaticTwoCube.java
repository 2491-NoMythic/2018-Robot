package com._2491nomythic.tempest.commands.autonomous;

import com._2491nomythic.tempest.commands.CommandBase;
import com._2491nomythic.tempest.settings.Constants.Crossing;
import com._2491nomythic.tempest.settings.Constants.Priority;
import com._2491nomythic.tempest.settings.Constants.StartPosition;

/**
 *
 */
public class AutomaticTwoCube extends CommandBase {
	private AutomaticAuto path;
	private CenterSecondCube center;
	private LeftSecondCube left;
	private RightSecondCube right;
	private boolean isFinishedSafety;
	private StartPosition position;
	
	public static enum SecondCube {
		SWITCH, SCALE
	}

    public AutomaticTwoCube(StartPosition position, Priority priority, Crossing crossing, SecondCube secondLocation) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	path = new AutomaticAuto(position, priority, crossing);
    	this.position = position;

    	left = new LeftSecondCube(priority, crossing, secondLocation);
    	center = new CenterSecondCube();
    	right = new RightSecondCube(priority, crossing, secondLocation);
    }

    // Called just before this Command runs the first time
    protected void initialize() {   	
    	isFinishedSafety = false;
    	path.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(!path.isRunning()) {  	
    		switch(position) {
    		case LEFT:
    			left.start();
    			break;
    		case CENTER:
    			center.start();
    			break;
    		case RIGHT:
    			right.start();
    			break;
    		default:
    			break;
    		}
    		
    		isFinishedSafety = true;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        if(isFinishedSafety) {
        	switch(position) {
            case LEFT:
            	return !left.isRunning();
            case CENTER:
            	return !center.isRunning();
            case RIGHT:
            	return !right.isRunning();
            default:
            	return !path.isRunning();
            }
        }
        else {
        	return false;
        }
    }

    // Called once after isFinished returns true
    protected void end() {
    	left.cancel();
    	right.cancel();
    	center.cancel();
    	path.cancel();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
