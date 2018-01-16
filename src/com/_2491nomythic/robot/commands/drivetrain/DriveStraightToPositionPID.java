/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com._2491nomythic.robot.commands.drivetrain;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

import com._2491nomythic.robot.commands.CommandBase;

/**
 * Drive the given distance straight (negative values go backwards). Uses a
 * local PID controller to run a simple PID loop that is only enabled while this
 * command is running. The input is the averaged values of the left and right
 * encoders.
 */
public class DriveStraightToPositionPID extends CommandBase {
	private PIDController pid;

	public DriveStraightToPositionPID(double distance) {
		requires(drivetrain);
		pid = new PIDController(4, 0, 0, new PIDSource() {
			PIDSourceType m_sourceType = PIDSourceType.kDisplacement;

			@Override
			public double pidGet() {
				return drivetrain.getLeftEncoderDistance();
			}

			@Override
			public void setPIDSourceType(PIDSourceType pidSource) {
				m_sourceType = pidSource;
			}

			@Override
			public PIDSourceType getPIDSourceType() {
				return m_sourceType;
			}
		}, new PIDOutput() {
			@Override
			public void pidWrite(double d) {
				drivetrain.drive(d, d);
			}
		});
		pid.setAbsoluteTolerance(0.01);
		pid.setSetpoint(distance);
		
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		// Get everything in a safe starting state.
		drivetrain.resetLeftEncoder();
		drivetrain.resetGyro();
		pid.reset();
		pid.enable();
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return pid.onTarget();
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		// Stop PID and the wheels
		pid.disable();
		drivetrain.stop();
	}
	
	protected void interrupted() {
		end();
	}
}