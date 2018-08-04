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
			//nextRun = nextRun + 0.1;
			//if (drivetrain.getEncoderRate() > maximumSpeed) {
				//maximumSpeed = drivetrain.getEncoderRate();
			//}
            
			//SmartDashboard.putNumber("Right Encoder TICS", drivetrain.getRightEncoderDistanceRaw());
			//SmartDashboard.putNumber("Left Encoder TICS", drivetrain.getLeftEncoderDistanceRaw());
			SmartDashboard.putNumber("Gyro Angle", drivetrain.getGyroAngle());
			SmartDashboard.putBoolean("ShooterReadyToFire", Variables.readyToFire);
			SmartDashboard.putNumber("LeftShootRPS", shooter.getLeftShootVelocity());
			SmartDashboard.putNumber("RightShootRPS", shooter.getRightShootVelocity());
			SmartDashboard.putNumber("Pathing Gyro", -drivetrain.getRawGyroAngle());
			SmartDashboard.putNumber("GyroPitchMeasure", drivetrain.getPitchAngle());
			
			Variables.derivativeRotate = SmartDashboard.getNumber("DerivateRotate", Variables.derivativeRotate);
			Variables.proportionalRotate = SmartDashboard.getNumber("ProportionalRotate", Variables.proportionalRotate);
			Variables.proportionalForward = SmartDashboard.getNumber("ProportionalForward", Variables.proportionalForward);
			Variables.derivativeForward = SmartDashboard.getNumber("DerivativeForward", Variables.derivativeForward);
			Variables.driveDefault = SmartDashboard.getNumber("DriveDefault", 1);
			Constants.shooterHighScaleRPS = SmartDashboard.getNumber("HighScaleRPS", Constants.shooterHighScaleRPS);
			Constants.shooterMediumScaleRPS = SmartDashboard.getNumber("MedScaleRPS", Constants.shooterMediumScaleRPS);
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
