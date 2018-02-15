package com._2491nomythic.robot.commands.lights;

import com._2491nomythic.robot.commands.CommandBase;

/**
 *
 */
public class LightTest extends CommandBase {

    public LightTest() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(sickLights);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	System.out.println("SickLights Connectivity: " + sickLights.isConnected());
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
