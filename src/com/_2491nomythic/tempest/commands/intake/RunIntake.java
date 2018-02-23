package com._2491nomythic.tempest.commands.intake;

import com._2491nomythic.tempest.commands.CommandBase;
import com._2491nomythic.tempest.settings.Variables;

/**
 * Runs the intake indefinitely at a set power
 */
public class RunIntake extends CommandBase {
	double power;
	
    public RunIntake(double power) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(intake);
    	this.power = power;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	intake.run(power, power, power * Variables.rollerCoefficient);
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
    	intake.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
