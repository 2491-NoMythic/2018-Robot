package com._2491nomythic.tempest.commands;


import com._2491nomythic.tempest.settings.Constants;
import com._2491nomythic.tempest.settings.Variables;

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
			Variables.derivative = SmartDashboard.getNumber("DerivateRotate", Variables.derivative);
			Variables.proportional = SmartDashboard.getNumber("ProportionalRotate", Variables.proportional);
			Variables.proportionalForward = SmartDashboard.getNumber("ProportionalForward", Variables.proportionalForward);
			Variables.derivativeForward = SmartDashboard.getNumber("DerivativeForward", Variables.derivativeForward);
			Variables.driveDefault = SmartDashboard.getNumber("DriveDefault", 1);
			//SmartDashboard.putNumber("Gyro Angle", drivetrain.getGyroAngle());
			//SmartDashboard.putNumber("LeftEncoder", drivetrain.getLeftEncoderDistance());
			//SmartDashboard.putNumber("RightEncoder", drivetrain.getRightEncoderDistance());
			//SmartDashboard.putNumber("Encoder Distance", drivetrain.getDistance());
			SmartDashboard.putBoolean("ShooterReadyToFire", Variables.readyToFire);
			//SmartDashboard.putNumber("Maximum Speed", maximumSpeed);
			//SmartDashboard.putNumber("Ultrasonic Distance", cubeStorage.getRangeInches());
			SmartDashboard.putNumber("LeftShootRPS", shooter.getLeftShootVelocity());
			SmartDashboard.putNumber("RightShootRPS", shooter.getRightShootVelocity());
			SmartDashboard.putNumber("LeftAccelRPS", shooter.getLeftAccelerateVelocity());
			SmartDashboard.putNumber("RightAccelRPS", shooter.getRightAccelerateVelocity());
			SmartDashboard.putNumber("AllShooterRPS", shooter.getAllMotorVelocity());
			Constants.shooterHighScaleSpeed[0] = SmartDashboard.getNumber("HighScalePower", Constants.shooterHighScaleSpeed[0]);
			Constants.shooterMediumScaleSpeed[0] = SmartDashboard.getNumber("MediumScalePower", Constants.shooterMediumScaleSpeed[0]);
			Constants.shooterLowScaleSpeed[0] = SmartDashboard.getNumber("LowScalePower", Constants.shooterLowScaleSpeed[0]);
			Constants.shooterSwitchSpeed[0] = SmartDashboard.getNumber("SwitchPower", Constants.shooterSwitchSpeed[0]);
			Constants.shooterHighScaleSpeed[1] = SmartDashboard.getNumber("HighScaleRPS", Constants.shooterHighScaleSpeed[1]);
			Constants.shooterMediumScaleSpeed[1] = SmartDashboard.getNumber("MediumScaleRPS", Constants.shooterMediumScaleSpeed[1]);
			Constants.shooterLowScaleSpeed[1] = SmartDashboard.getNumber("LowScaleRPS", Constants.shooterLowScaleSpeed[1]);
			Constants.shooterSwitchSpeed[1] = SmartDashboard.getNumber("SwitchRPS", Constants.shooterSwitchSpeed[1]);
			drivetrain.chooseDefaultCommand(Variables.driveDefault);
			SmartDashboard.putNumber("LeftShootPower", Variables.leftShootSpeed);
			SmartDashboard.putNumber("RightShootPower", Variables.rightShootSpeed);
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
