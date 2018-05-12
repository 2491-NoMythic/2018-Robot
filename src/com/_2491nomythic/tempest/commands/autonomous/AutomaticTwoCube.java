package com._2491nomythic.tempest.commands.autonomous;

import com._2491nomythic.tempest.commands.CommandBase;
import com._2491nomythic.tempest.commands.autonomous.AutomaticAuto.Crossing;
import com._2491nomythic.tempest.commands.autonomous.AutomaticAuto.Priority;
import com._2491nomythic.tempest.commands.autonomous.AutomaticAuto.StartPosition;

import edu.wpi.first.wpilibj.DriverStation;

/**
 *
 */
public class AutomaticTwoCube extends CommandBase {
	private AutomaticAuto path;
	private CenterSecondCube center;
	private LeftSecondCube left;
	private RightSecondCube right;
	private boolean isFinishedSafety;
	private String gameData;
	private StartPosition position;
	
	public static enum SecondCube {
		SWITCH, SCALE
	}

    public AutomaticTwoCube(StartPosition position, Priority priority, Crossing crossing, SecondCube secondLocation) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	path = new AutomaticAuto(position, priority, crossing);
    	this.position = position;

    	gameData = DriverStation.getInstance().getGameSpecificMessage();
    	
    	if(secondLocation == SecondCube.SWITCH && (gameData.substring(0, 1) == gameData.substring(1, 2))) {
    		
    	}
    	else {
    		secondLocation = SecondCube.SCALE;
    	}
    	
    	left = new LeftSecondCube(secondLocation);
    	center = new CenterSecondCube();
    	right = new RightSecondCube(secondLocation);
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
