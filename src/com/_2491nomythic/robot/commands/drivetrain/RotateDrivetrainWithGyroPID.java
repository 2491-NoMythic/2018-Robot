package com._2491nomythic.robot.commands.drivetrain;

import com._2491nomythic.robot.commands.CommandBase;
import com._2491nomythic.robot.settings.Variables;

/**
 * Rotates the drivetrain to a specified angle using PID
 */
public class RotateDrivetrainWithGyroPID extends CommandBase {
	private double target;
	private boolean type;
	private double relative;
	

	/**
	 * Rotates the drivetrain to a specified angle using PID
	 * @param angle The angle that the robot rotates to, where negative values rotate the robot counterclockwise
	 * @param absolute Set true for absolute, false for relative 
	 */
    public RotateDrivetrainWithGyroPID(double angle, boolean absolute) {
    	type = absolute;
    	target = angle;
        // Use requires() here to declare subsystem dependencies
      	requires(drivetrain);
      	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Variables.useGyroPID = true;
    	relative = ((drivetrain.getGyroAngle() + target) % 360 + 360) % 360;
    	if(type) {
    		drivetrain.setSetpoint(target);
    	}
    	else {
    		drivetrain.setSetpoint(relative);
    	}
    	drivetrain.enable();
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
    	drivetrain.stop();
    	drivetrain.disable();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
