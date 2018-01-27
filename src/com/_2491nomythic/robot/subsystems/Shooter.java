package com._2491nomythic.robot.subsystems;

import com._2491nomythic.robot.settings.Constants;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The subsystem that takes Power Cubes from the field.
 */
public class Shooter extends Subsystem {
	private static Shooter instance;
	private TalonSRX leftHold, rightHold, leftAccelerate, rightAccelerate, leftShoot, rightShoot;
	
	public static Shooter getInstance() {
		if (instance == null) {
			instance = new Shooter();
		}
		return instance;
	}
	
	private Shooter() {
		leftHold = new TalonSRX(Constants.shooterTalonLeftHoldChannel);
		rightHold = new TalonSRX(Constants.shooterTalonRightHoldChannel);
		leftAccelerate = new TalonSRX(Constants.shooterTalonLeftAccelerateChannel);
		rightAccelerate = new TalonSRX(Constants.shooterTalonRightAccelerateChannel);
		leftShoot = new TalonSRX(Constants.shooterTalonLeftShootChannel);
		rightShoot = new TalonSRX(Constants.shooterTalonRightShootChannel);
		
		leftHold.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		rightHold.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		leftAccelerate.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		rightAccelerate.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		leftShoot.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		rightShoot.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
	}
	
	
	//Motors
	public void runHold(double power) {
		leftHold.set(ControlMode.PercentOutput, power);
		rightHold.set(ControlMode.PercentOutput, power);
	}
	
	public void runAccelerate(double power) {
		leftAccelerate.set(ControlMode.PercentOutput, power);
		rightAccelerate.set(ControlMode.PercentOutput, power);
	}
	
	public void runShoot(double power) {
		leftShoot.set(ControlMode.PercentOutput, power);
		rightShoot.set(ControlMode.PercentOutput, power);
	}
	
	
	//Encoders
	public double getLeftHoldEncoder() {
		return leftHold.getSelectedSensorVelocity(0);
	}
	
	public double getRightHoldEncoder() {
		return rightHold.getSelectedSensorVelocity(0);
	}
	
	public double getLeftAccelerateEncoder() {
		return leftAccelerate.getSelectedSensorVelocity(0);
	}
	
	public double getRightAccelerateEncoder() {
		return rightAccelerate.getSelectedSensorVelocity(0);
	}
	
	public double getLeftShootEncoder() {
		return leftShoot.getSelectedSensorVelocity(0);
	}
	
	public double getRightShootEncoder() {
		return rightShoot.getSelectedSensorVelocity(0);
	}
	

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

