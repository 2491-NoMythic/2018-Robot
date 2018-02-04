package com._2491nomythic.robot.commands.lights;

import com._2491nomythic.robot.commands.CommandBase;
import com._2491nomythic.robot.settings.Constants;
import com._2491nomythic.robot.settings.Variables;

/**
 * Changes the pattern of the lights depending on the RPM of the shooter, and whether or not a cube is held.
 */
public class UpdateLights extends CommandBase {
	private int isRaised;
	private String data;
	
	/**
	 * Changes the pattern of the lights depending on the RPM of the shooter, and whether or not a cube is held.
	 */
    public UpdateLights() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(sickLights);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(shooter.getAllMotorVelocity() >= 0.05) {
    		if(shooter.isRaised()) {
        		isRaised = 1;
        	}
        	else {
        		isRaised = 0;
        	}
        	
        	switch(isRaised) {
        	case 0:
        		if(Variables.currentShooterRPM * 0.25 <= Constants.shooterSwitchSpeed * 0.25) {
        			//Slow strobe
        			data = "1";
        		}
        		else if(Variables.currentShooterRPM * 0.5 <= Constants.shooterSwitchSpeed * 0.5) {
        			//Medium strobe
        			data = "2";
        		}
        		else if(Variables.currentShooterRPM * 0.75 <= Constants.shooterSwitchSpeed * 0.75) {
        			//Fast strobe
        			data = "3";
        		}
        		else {
        			//Blinking or solid
        			data = "4";
        		}
        		break;
        	case 1:
        		if(Variables.currentShooterRPM * 0.25 <= Constants.shooterMediumScaleSpeed * 0.25) {
        			//Slow strobe
        			data = "1";
        		}
        		else if(Variables.currentShooterRPM * 0.5 <= Constants.shooterMediumScaleSpeed * 0.5) {
        			//Medium strobe
        			data = "2";
        		}
        		else if(Variables.currentShooterRPM * 0.75 <= Constants.shooterMediumScaleSpeed * 0.75) {
        			//Fast strobe
        			data = "3";
        		}
        		else {
        			//Blinking or solid
        			data = "4";
        		}
        		break;
        	default:
        		System.out.println("Unexpected value for isRaised in UpdateLights.");
        		break;
        	}        	
    	}
    	else if(cubeStorage.isHeld()) {
    		//Flashing maybe?
    		data = "5";
    	}
    	else {
    		//Solid
    		data = "0";
    	}
    	
    	sickLights.writeSignal(data);
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
    	end();
    }
}
