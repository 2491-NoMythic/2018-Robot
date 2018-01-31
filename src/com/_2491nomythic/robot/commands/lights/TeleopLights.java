package com._2491nomythic.robot.commands.lights;

import com._2491nomythic.robot.commands.CommandBase;
import com._2491nomythic.robot.settings.Variables;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;

/**
 *
 */
public class TeleopLights extends CommandBase {

    public TeleopLights() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(lights);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(Variables.readyToFire) {
    		if(DriverStation.getInstance().getAlliance() == Alliance.Red) {
    			lights.setByte(2);
    		}
    		else {
    			lights.setByte(3);
    		}
    	}
    	else {
    		lights.setByte(1);
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
    	end();
    }
}
