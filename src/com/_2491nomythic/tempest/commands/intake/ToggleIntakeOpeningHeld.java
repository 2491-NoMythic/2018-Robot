package com._2491nomythic.tempest.commands.intake;

import com._2491nomythic.tempest.commands.CommandBase;


/**
 *
 */
public class ToggleIntakeOpeningHeld extends CommandBase {

    public ToggleIntakeOpeningHeld() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	intake.open();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	intake.close();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
