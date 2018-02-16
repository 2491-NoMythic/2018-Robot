package com._2491nomythic.robot.subsystems;

import com._2491nomythic.robot.settings.Constants;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The system of motors and encoders that is used to launch Power Cubes from the robot into the Switch and Scale
 */
public class Shooter extends Subsystem {
	private static Shooter instance;
	private TalonSRX leftAccelerate, rightAccelerate, leftShoot, rightShoot;
	private Solenoid elevator;
	
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
		leftAccelerate = new TalonSRX(Constants.shooterTalonLeftAccelerateChannel);
		rightAccelerate = new TalonSRX(Constants.shooterTalonRightAccelerateChannel);
		leftShoot = new TalonSRX(Constants.shooterTalonLeftShootChannel);
		rightShoot = new TalonSRX(Constants.shooterTalonRightShootChannel);
		
		elevator = new Solenoid(Constants.shooterElevatorChannel);
		
		leftAccelerate.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		rightAccelerate.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		leftShoot.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		rightShoot.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
	}
	
	
	//Motors
	
	/**
	 * Runs the motors used to initially speed up Power Cubes within the robot, readying them for launch, with a given power
	 * @param speed The speed that the motors are set to run at.
	 */
	public void runAccelerate(double speed) {
		leftAccelerate.set(ControlMode.PercentOutput, speed);
		rightAccelerate.set(ControlMode.PercentOutput, speed);
	}
	
	/**
	 * Runs the motors used to maintain momentum of, and finally launch, the Power Cubes from the robot with a given power
	 * @param speed The speed that the motors are set to run at.
	 */
	public void runShoot(double speed) {
		leftShoot.set(ControlMode.PercentOutput, speed);
		rightShoot.set(ControlMode.PercentOutput, speed);
	}
	
	/**
	 * 
	 * @param speed Speed to set the motors
	 */
	public void run(double speed) {
		runAccelerate(speed);
		runShoot(speed);
	}
	
	
	//Encoders
	
	
	/**
	 * Gets the encoder velocity of the left accelerate motor
	 * @return The encoder velocity of the left accelerate motor in RPS
	 */
	public double getLeftAccelerateVelocity() {
		return 0;// leftAccelerate.getSelectedSensorVelocity(0) * Constants.shootEncoderVelocityToRPS;
	}
	
	/**
	 * Gets the encoder velocity of the right accelerate motor
	 * @return The encoder velocity of the right accelerate motor in RPS
	 */
	public double getRightAccelerateVelocity() {
		return 0;//rightAccelerate.getSelectedSensorVelocity(0) * Constants.shootEncoderVelocityToRPS;
	}
	
	/**
	 * Gets the encoder velocity of the left shoot motor
	 * @return The encoder velocity of the left shoot motor in RPS
	 */
	public double getLeftShootVelocity() {
		return 0;//leftShoot.getSelectedSensorVelocity(0) * Constants.shootEncoderVelocityToRPS;
	}
	
	/**
	 * Gets the encoder velocity of the right shoot motor
	 * @return The encoder velocity of the right shoot motor in RPS
	 */
	public double getRightShootVelocity() {
		return 0;//rightShoot.getSelectedSensorVelocity(0) * Constants.shootEncoderTicksToRPS;
	}
	
	/**
	 * Gets the average encoder velocity of the two accelerate motors
	 * @return The average encoder velocity of the accelerate motors
	 */
	public double getAccelerateVelocity() {
		return 0;//(getLeftAccelerateVelocity() + getRightAccelerateVelocity())/2;
	}
	
	/**
	 * Gets the average encoder velocity of the two shoot motors
	 * @return The average encoder velocity of the shoot motors
	 */
	public double getShootVelocity() {
		return 0;//(getLeftShootVelocity() + getRightShootVelocity())/2;
	}
	
	/**
	 * Gets the average encoder velocity of all motors within the shooter subsystem
	 * @return The average encoder velocity of all shooter motors
	 */
	public double getAllMotorVelocity() {
		return 0;//(getShootVelocity() + getAccelerateVelocity())/2;
	}
	
	//Solenoid
	
	/**
	 * Raises the shooter to shoot Power Cubes into the scale
	 */
	public void raiseShooter() {
		elevator.set(true);
	}
	/**
	 * Lowers the shooter to shoot Power Cubes into the switch
	 */
	public void lowerShooter() {
		elevator.set(false);
	}
	
	/**
	 * Checks whether the shooter is raised
	 * @return The status of whether the shooter is raised
	 */
	public boolean isRaised() {
		return elevator.get();
	}

	public void initDefaultCommand() {
	}
	
	/**
	 * Stops all motors within the shooter subsystem
	 */
	public void stop() {
		runAccelerate(0);
		runShoot(0);
	}
}

