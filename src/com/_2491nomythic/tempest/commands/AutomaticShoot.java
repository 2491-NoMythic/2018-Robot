package com._2491nomythic.tempest.commands;

import com._2491nomythic.tempest.commands.cubestorage.TransportCubeTime;
import com._2491nomythic.tempest.commands.shooter.RunShooterTime;
import com._2491nomythic.tempest.commands.shooter.ToggleShooterPosition;
import com._2491nomythic.tempest.settings.Constants;

import edu.wpi.first.wpilibj.Timer;

/**
 *
 */
public class AutomaticShoot extends CommandBase {
	private TransportCubeTime fire;
	private Timer timer;
	private RunShooterTime spinUp;
	private ToggleShooterPosition shiftPosition;
	private boolean scale;
	private int state;

    public AutomaticShoot(boolean scale) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	
    	this.scale = scale;
    	timer = new Timer();
    	fire = new TransportCubeTime(1, 1);
    	shiftPosition = new ToggleShooterPosition();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	state = 0;
    	
    	if(scale) {
    		spinUp = new RunShooterTime(Constants.shooterMediumScaleSpeed, 4);
    	}
    	else {
    		spinUp = new RunShooterTime(Constants.shooterSwitchSpeed, 4);
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    		switch(state) {
    		case 0:
    			if(shooter.inScalePosition() && scale) {
    				shiftPosition.start();
    			}
    			else if(shooter.inScalePosition() && !scale) {
    			}
    			else if(!shooter.inScalePosition() && !scale) {
    				shiftPosition.start();
    			}
    			else {
    			}
    			
    			state++;
    			break;
    		case 1:
    			spinUp.start();
    			timer.start();
    			state++;
    			break;
    		case 2:
    			fire.start();
    			state++;
    			break;
    		case 3:
    			if(!fire.isRunning()) {
    				state++;
    			}
    			break;
    		case 4:
    			break;
    		default:
    			break;
    		}
    	}
    

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return !fire.isRunning() && state == 4;
    }

    // Called once after isFinished returns true
    protected void end() {
    	fire.cancel();
    	spinUp.cancel();
    	shiftPosition.cancel();
    }


    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
