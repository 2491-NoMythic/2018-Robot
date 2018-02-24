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
			Variables.leftShootProportional = SmartDashboard.getNumber("LShootP", Variables.leftShootProportional);
			Variables.leftShootIntegral = SmartDashboard.getNumber("LShootI", Variables.leftShootIntegral);
			Variables.leftShootDerivative = SmartDashboard.getNumber("LShootD", Variables.leftShootDerivative);
			Variables.rightShootProportional = SmartDashboard.getNumber("LShootP", Variables.rightShootProportional);
			Variables.rightShootIntegral = SmartDashboard.getNumber("LShootI", Variables.rightShootIntegral);
			Variables.rightShootDerivative = SmartDashboard.getNumber("LShootD", Variables.rightShootDerivative);
			SmartDashboard.putNumber("Gyro Angle", drivetrain.getGyroAngle());
			SmartDashboard.putNumber("LeftEncoder", drivetrain.getLeftEncoderDistance());
			SmartDashboard.putNumber("RightEncoder", drivetrain.getRightEncoderDistance());
			SmartDashboard.putBoolean("ShooterReadyToFire", Variables.readyToFire);
			SmartDashboard.putNumber("LeftShootRPS", shooter.getLeftShootVelocity());
			SmartDashboard.putNumber("RightShootRPS", shooter.getRightShootVelocity());
			Constants.shooterHighScaleRPS = SmartDashboard.getNumber("HighScaleRPS", Constants.shooterHighScaleRPS);
			Constants.shooterMediumScaleRPS = SmartDashboard.getNumber("MediumScaleRPS", Constants.shooterMediumScaleRPS);
			Constants.shooterLowScaleRPS = SmartDashboard.getNumber("LowScaleRPS", Constants.shooterLowScaleRPS);
			Constants.shooterSwitchRPS = SmartDashboard.getNumber("SwitchRPS", Constants.shooterSwitchRPS);
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
