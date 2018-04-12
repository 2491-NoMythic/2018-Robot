package com._2491nomythic.tempest.commands.climber;

import com._2491nomythic.tempest.commands.CommandBase;

/**
 *Deploys the lineup pins for the climbing subsystem.
 */
public class DeployLineup extends CommandBase {

    public DeployLineup() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	climber.deployLineup();
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
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
