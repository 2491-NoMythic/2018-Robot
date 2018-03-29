package com._2491nomythic.tempest.commands.intake;

import com._2491nomythic.tempest.commands.CommandBase;
import com._2491nomythic.tempest.settings.Variables;

/**
 *Runs the intake without the roller, though why we would ever want to do that is beyond me
 */
public class RunIntakeRollerless extends CommandBase {

    /**
     * Runs the intake without the roller, though why we would ever want to do that is beyond me
     */
	public RunIntakeRollerless() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Variables.rollerReverseCoefficient = 0;
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
    	Variables.rollerReverseCoefficient = 1;
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
