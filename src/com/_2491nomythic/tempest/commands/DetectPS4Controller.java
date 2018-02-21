package com._2491nomythic.tempest.commands;

import com._2491nomythic.tempest.OI.ControllerType;
import com._2491nomythic.tempest.settings.ControllerMap;

/**
 * Detects if there is a PS4 controller connected and remaps the controls accordingly;
 */
public class DetectPS4Controller extends CommandBase {

    public DetectPS4Controller() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if (oi.getControllerName(1).equals("Wireless Controller")) {
    		oi.changeControllerType(1, ControllerMap.operatorController, ControllerType.PS4);
    	}
    	
    	System.out.println("Checking controllers...");
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
