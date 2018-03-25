package com._2491nomythic.tempest.commands;

import com._2491nomythic.tempest.commands.cubestorage.TransportCubeTime;
import com._2491nomythic.tempest.commands.shooter.RunShooterCustom;
import com._2491nomythic.tempest.commands.shooter.SetShooterSpeed;
import com._2491nomythic.tempest.commands.shooter.ToggleShooterPosition;
import com._2491nomythic.tempest.settings.Constants;
import com._2491nomythic.tempest.settings.Variables;

import edu.wpi.first.wpilibj.Timer;

/**
 *
 */
public class AutomaticShoot extends CommandBase {
	private TransportCubeTime fire;
	private Timer timer;
	private RunShooterCustom spinUp;
	private ToggleShooterPosition shiftPosition;
	private boolean scale;
	private int state;
	private SetShooterSpeed setSpeed;

    public AutomaticShoot(boolean scale) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	
    	this.scale = scale;
    	timer = new Timer();
    	fire = new TransportCubeTime(1, 1);
    	shiftPosition = new ToggleShooterPosition();
    	spinUp = new RunShooterCustom();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	state = 0;
    	
    	if(scale) {
    		setSpeed = new SetShooterSpeed(Constants.shooterMediumScaleSpeed);
    	}
    	else {
    		setSpeed = new SetShooterSpeed(Constants.shooterLowScaleSpeed);
    	}
    	setSpeed.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    		switch(state) {
    		case 0:
    			if((shooter.inSwitchPosition() && scale) || (!shooter.inSwitchPosition() && !scale)) {
    				shiftPosition.start();
    			}
    			state++;
    			break;
    		case 1:
    			spinUp.start();
    			timer.start();
    			state++;
    			break;
    		case 2:
    			if (scale) {
    				if (Variables.readyToFire || timer.get() > 2) {
    					fire.start();
    					state++;
    				}
    			}
    			else {
    				fire.start();
    				state++;
    			}
    			break;
    		case 3:
    			if(fire.isCompleted() && timer.get() > 4) {
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
        return fire.isCompleted() && state == 4;
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
