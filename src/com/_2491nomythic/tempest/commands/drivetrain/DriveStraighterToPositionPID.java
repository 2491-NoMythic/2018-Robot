package com._2491nomythic.tempest.commands.drivetrain;

import com._2491nomythic.tempest.commands.CommandBase;
import com._2491nomythic.util.LeftDrivePIDController;
import com._2491nomythic.util.RightDrivePIDController;

/**
 * Drives to a given position using PID loops to monitor consistency between sides of the drivetrain and distance from target
 */
public class DriveStraighterToPositionPID extends CommandBase {
	private LeftDrivePIDController leftDrive;
	private RightDrivePIDController rightDrive;
	private double distance;

	/**
	 * Drives to a given position using PID loops to monitor consistency between sides of the drivetrain and distance from target
	 * @param distance Distance to drive, in inches
	 */
    public DriveStraighterToPositionPID(double distance) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(drivetrain);
    	leftDrive = new LeftDrivePIDController(drivetrain);
    	rightDrive = new RightDrivePIDController(drivetrain);
    	this.distance = distance;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	leftDrive.setSetpoint(drivetrain.getLeftEncoderDistance() + distance);
    	rightDrive.setSetpoint(drivetrain.getLeftEncoderRate());
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return leftDrive.onTarget();
    }

    // Called once after isFinished returns true
    protected void end() {
    	leftDrive.disable();
    	rightDrive.disable();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
