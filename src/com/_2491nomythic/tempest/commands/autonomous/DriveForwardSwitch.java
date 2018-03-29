package com._2491nomythic.tempest.commands.autonomous;
import com._2491nomythic.tempest.commands.CommandBase;
import com._2491nomythic.tempest.commands.cubestorage.TransportCubeTime;
import com._2491nomythic.tempest.commands.drivetrain.DriveStraightToPosition;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;

/**
 *Approaches the switch during autonomous
 *@deprecated
 */
public class DriveForwardSwitch extends CommandBase {
	private DriveStraightToPosition approachSwitch;
	private TransportCubeTime load;
	private boolean left, startingPosition;
	private int state;
	private Timer timer;
	
	/**
	 * Approaches the switch during autonomous
	 * StartingPosition is true for left.
	 * @param startingPosition
	 * @deprecated
	 */
    public DriveForwardSwitch(boolean startingPosition) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	this.startingPosition = startingPosition;
    	
    	approachSwitch = new DriveStraightToPosition(0.4, 120);
    	load = new TransportCubeTime(-0.5, 2);
    	timer = new Timer();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	state = 0;
    	String gameData = DriverStation.getInstance().getGameSpecificMessage();
    	
    	if(startingPosition) {
    		left = gameData.substring(0, 1).contentEquals("L");
    	}
    	else {
    		left = gameData.substring(0, 1).contentEquals("R");
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	switch(state) {
    	case 0:
    		approachSwitch.start();
    		timer.start();
    		state++;
    		break;
    	case 1:
    		if(left) {
    			if(!approachSwitch.isRunning()  || timer.get() > 5) {
    				approachSwitch.cancel();
    				timer.reset();
    				load.start();
    				state++;
    			}
    		}
    		break;
    	case 2:    		
    		break;
    	default:
    		System.out.println("Invalid state in DriveForwardSwitch: State: " + state);
    		break;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return state == 2 && !load.isRunning();
    }

    // Called once after isFinished returns true
    protected void end() {
    	approachSwitch.cancel();
    	drivetrain.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
