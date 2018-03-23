package com._2491nomythic.tempest.commands.lights;

import com._2491nomythic.tempest.commands.CommandBase;

import edu.wpi.first.wpilibj.DriverStation;

/**
 *
 */
public class SendAllianceColor extends CommandBase {

    public SendAllianceColor() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if(DriverStation.getInstance().getAlliance() == DriverStation.Alliance.Red) {
    		sickLights.writeData(1);
    	}
    	else if(DriverStation.getInstance().getAlliance() == DriverStation.Alliance.Blue) {
    		sickLights.writeData(2);
    	}
    	else {
    		System.out.println("Invalid alliance in SendAllianceColor.");
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
