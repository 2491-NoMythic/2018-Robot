package com._2491nomythic.tempest.commands.autonomous;

import com._2491nomythic.tempest.commands.CommandBase;
import com._2491nomythic.tempest.commands.autonomous.AutomaticAuto.Crossing;
import com._2491nomythic.tempest.commands.autonomous.AutomaticAuto.EndPosition;
import com._2491nomythic.tempest.commands.autonomous.AutomaticAuto.Priority;
import com._2491nomythic.tempest.commands.autonomous.AutomaticAuto.StartPosition;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;

/**
 *
 */
public class AutomaticTwoCube extends CommandBase {
	private AutomaticAuto path;
	private CenterSecondCube center;
	private LeftSecondCube left;
	private RightSecondCube right;
	private boolean isFinishedSafety;
	private EndPosition ending;
	private String gameData;
	private StartPosition position;
	private Timer timer, overallTimer;
	
	public static enum SecondCube {
		SWITCH, SCALE
	}

    public AutomaticTwoCube(StartPosition position, Priority priority, Crossing crossing, SecondCube secondLocation) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	path = new AutomaticAuto(position, priority, crossing);
    	this.position = position;
    	
    	System.out.println("Definitely deployed");

    	gameData = DriverStation.getInstance().getGameSpecificMessage();
    	
    	if(secondLocation == SecondCube.SWITCH && ((gameData.substring(0, 1).contentEquals("R") && gameData.substring(1, 2).contentEquals("R")) || (gameData.substring(0, 1).contentEquals("L") && gameData.substring(1, 2).contentEquals("L")))) {
    		
    	}
    	else {
    		secondLocation = SecondCube.SCALE;
    	}
    	
    	timer = new Timer();
    	overallTimer = new Timer();
    	left = new LeftSecondCube(secondLocation);
    	center = new CenterSecondCube();
    	right = new RightSecondCube(secondLocation);
    }

    // Called just before this Command runs the first time
    protected void initialize() {   	
    	isFinishedSafety = false;
    	path.start();
    	overallTimer.start();
    	timer.start();
    	timer.reset();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	ending = path.getEndPosition();
    	
    	if(!path.isRunning() && timer.get() > 0.05) {
    		System.out.println(ending.toString());
    		
    		if(ending == EndPosition.SCALE || ending == EndPosition.OPPOSITE_SCALE) {
    			System.out.println("Going for second cube!");
    			
	    		switch(DriverStation.getInstance().getGameSpecificMessage().substring(1, 2)) {
	    		case "L":
	    			left.start();
	    			break;
	    		case "R":
	    			right.start();
	    			break;
	    		default:
	    			break;
	    		}
	    		
	    		isFinishedSafety = true;
    		}
    		
    		if(ending == EndPosition.LEFT_SWITCH || ending == EndPosition.RIGHT_SWITCH) {
    			//center.start();
    		}
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        if(isFinishedSafety) {
        	return !left.isRunning() && !right.isRunning() && !center.isRunning();
        }
        else {
        	return false;
        }
    }

    // Called once after isFinished returns true
    protected void end() {
    	System.out.println("Time taken: " + overallTimer.get());
    	
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
