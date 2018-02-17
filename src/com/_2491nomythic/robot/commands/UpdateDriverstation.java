package com._2491nomythic.robot.commands;


import com._2491nomythic.robot.settings.Variables;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Updates values in the code from editable values on the SmartDashboard
 */
public class UpdateDriverstation extends CommandBase {
	private Timer timer;
	private double nextRun, maximumSpeed;

	/**
	 * Updates values in the code from editable values on the SmartDashboard
	 */
	public UpdateDriverstation() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		setRunWhenDisabled(true);
		setInterruptible(false);
		timer = new Timer();
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		timer.start();
		timer.reset();
		nextRun = timer.get();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if (timer.get() > nextRun) {
			nextRun = nextRun + 0.1;
			if (drivetrain.getEncoderRate() > maximumSpeed) {
				maximumSpeed = drivetrain.getEncoderRate();
			}
			
			Variables.autoDelay = SmartDashboard.getNumber("AutoDelay", Variables.autoDelay);
			Variables.derivative = SmartDashboard.getNumber("Derivate Rotate", Variables.derivative);
			Variables.proportional = SmartDashboard.getNumber("Proportional Rotate", Variables.proportional);
			Variables.proportionalForward = SmartDashboard.getNumber("Proportional Forward", Variables.proportionalForward);
			Variables.derivativeForward = SmartDashboard.getNumber("Derivative Forward", Variables.derivativeForward);
			Variables.driveDefault = SmartDashboard.getNumber("DriveDefault", 1);
			SmartDashboard.putNumber("Gyro Angle", drivetrain.getGyroAngle());
			SmartDashboard.putNumber("LeftEncoder", drivetrain.getLeftEncoderDistance());
			SmartDashboard.putNumber("RightEncoder", drivetrain.getRightEncoderDistance());
			SmartDashboard.putNumber("Avg. Encoder Distance", drivetrain.getDistance());
			SmartDashboard.putNumber("Current PID Input", drivetrain.returnPIDInput());
			SmartDashboard.putBoolean("Shooter Ready To Fire", Variables.readyToFire);
			SmartDashboard.putNumber("Drivetrain Speed", drivetrain.getEncoderRate());
			SmartDashboard.putNumber("Maximum Speed", maximumSpeed);
			SmartDashboard.putNumber("Ultrasonic Distance", cubeStorage.getRangeInches());
			drivetrain.chooseDefaultCommand(Variables.driveDefault);
		}
			
		
			
			
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		timer.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
