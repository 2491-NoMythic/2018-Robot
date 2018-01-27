package com._2491nomythic.robot.subsystems;

import com._2491nomythic.robot.settings.Constants;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The system of motors and encoders that is used to launch Power Cubes from the robot into the Switch and Scale
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
	
	/**
	 * The system of motors and encoders that is used to launch Power Cubes from the robot into the Switch and Scale
	 */
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
	/**
	 * Runs the motors used to hold and manipulate Power Cubes within the robot with a given power
	 * @param power The power fed to the hold motors
	 */
	public void runHold(double power) {
		leftHold.set(ControlMode.PercentOutput, power);
		rightHold.set(ControlMode.PercentOutput, power);
	}
	
	/**
	 * Runs the motors used to initially speed up Power Cubes within the robot, readying them for launch, with a given power
	 * @param power The power fed to the accelerate motors
	 */
	public void runAccelerate(double power) {
		leftAccelerate.set(ControlMode.PercentOutput, power);
		rightAccelerate.set(ControlMode.PercentOutput, power);
	}
	
	/**
	 * Runs the motors used to maintain momentum of, and finally launch, the Power Cubes from the robot with a given power
	 * @param power The power fed to the shoot motors
	 */
	public void runShoot(double power) {
		leftShoot.set(ControlMode.PercentOutput, power);
		rightShoot.set(ControlMode.PercentOutput, power);
	}
	
	
	//Encoders
	
	
	/**
	 * Gets the encoder velocity of the left accelerate motor
	 * @return The encoder velocity of the left accelerate motor
	 */
	public double getLeftAccelerateEncoder() {
		return leftAccelerate.getSelectedSensorVelocity(0);
	}
	
	/**
	 * Gets the encoder velocity of the right accelerate motor
	 * @return The encoder velocity of the right accelerate motor
	 */
	public double getRightAccelerateEncoder() {
		return rightAccelerate.getSelectedSensorVelocity(0);
	}
	
	/**
	 * Gets the encoder velocity of the left shoot motor
	 * @return The encoder velocity of the left shoot motor
	 */
	public double getLeftShootEncoder() {
		return leftShoot.getSelectedSensorVelocity(0);
	}
	
	/**
	 * Gets the encoder velocity of the right shoot motor
	 * @return The encoder velocity of the right shoot motor
	 */
	public double getRightShootEncoder() {
		return rightShoot.getSelectedSensorVelocity(0);
	}
	

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void stop() {
    	runHold(0);
    	runAccelerate(0);
    	runShoot(0);
    }
}

