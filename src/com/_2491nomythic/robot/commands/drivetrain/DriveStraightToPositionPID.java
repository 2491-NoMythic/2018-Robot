/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com._2491nomythic.robot.commands.drivetrain;

import com._2491nomythic.robot.commands.CommandBase;
import com._2491nomythic.robot.settings.Variables;

/**
 * Drive the given distance straight (negative values go backwards). 
 * Which runs a simple PID loop that is only enabled while this
 * command is running. The input is the averaged values of the left and right
 * encoders.
 */
public class DriveStraightToPositionPID extends CommandBase {
	private double target;

	/**
	 * Drive the given distance straight (negative values go backwards). Uses a
	 * local PID controller to run a simple PID loop that is only enabled while this
	 * command is running. The input is the averaged values of the left and right encoders.
	 * @param distance The distance for the robot to drive. Negative values go backwards.
	 */
	public DriveStraightToPositionPID(double distance) {
		target = distance;
		requires(drivetrain);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Variables.useGyroPID = false;
		drivetrain.setInputRange(-100000000, 100000000);
		drivetrain.getPIDController().setContinuous(false);
		drivetrain.setAbsoluteTolerance(1.5);
		drivetrain.getPIDController().setPID(Variables.proportionalForward, Variables.integralForward, Variables.derivativeForward);
		
		drivetrain.setSetpoint(drivetrain.getDistance() + target);
		drivetrain.enable();
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		// Stop PID and the wheels
		drivetrain.disable();
		drivetrain.stop();
	}
	
	protected void interrupted() {
		end();
	}
}