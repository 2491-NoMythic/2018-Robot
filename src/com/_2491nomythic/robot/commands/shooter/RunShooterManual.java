package com._2491nomythic.robot.commands.shooter;

import com._2491nomythic.robot.commands.CommandBase;
import com._2491nomythic.robot.settings.ControllerMap;

/**
 *
 */
public class RunShooterManual extends CommandBase {
	private double currentSpeed;

    public RunShooterManual() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	currentSpeed = -oi.getAxisDeadzonedSquared(ControllerMap.operatorController, ControllerMap.shooterAxis, 0.2);
    	shooter.run(currentSpeed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	shooter.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
