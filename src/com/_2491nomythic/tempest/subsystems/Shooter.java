package com._2491nomythic.tempest.subsystems;

import com._2491nomythic.tempest.settings.Constants;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The system of motors and encoders that is used to launch Power Cubes from the robot into the Switch and Scale
 */
public class Shooter extends Subsystem {
	private static Shooter instance;
	private TalonSRX leftAccelerate, rightAccelerate, leftShoot, rightShoot;
	private DoubleSolenoid elevator;
	
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
		
		elevator = new DoubleSolenoid(Constants.shooterElevatorChannelForward, Constants.shooterElevatorChannelReverse);
		
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
		rightAccelerate.set(ControlMode.PercentOutput, -speed);
	}
	
	/**
	 * Runs the motors used to maintain momentum of, and finally launch, the Power Cubes from the robot with a given power
	 * @param speed The speed that the motors are set to run at.
	 */
	public void runShoot(double speed) {
		runLeftShoot(speed);
		runRightShoot(speed);
	}

	/**
	 * 
	 * @param speed Speed to set the motors
	 */
	public void run(double speed) {
		runAccelerate(speed);
		runShoot(speed);
	}
	
	/**
	 * Runs the shooter motors at separately identified speeds
	 * @param leftShootSpeed The speed to run the left shoot motor at
	 * @param rightShootSpeed The speed to run the right shoot motor at
	 * @param accelSpeed The speed to run the accelerator motors at
	 */
	public void run(double leftShootSpeed, double rightShootSpeed, double accelSpeed) {
		leftShoot.set(ControlMode.PercentOutput, leftShootSpeed);
		rightShoot.set(ControlMode.PercentOutput, rightShootSpeed);
		runAccelerate(accelSpeed);
	}
	
	/**
	 * Runs the left shoot motor at a given speed
	 * @param speed The speed to run the left shoot motor at
	 */
	public void runLeftShoot(double speed) {
		leftShoot.set(ControlMode.PercentOutput, speed);
	}
	
	/**
	 * Runs the right shoot motor at a given speed
	 * @param speed The speed to run the right shoot motor at
	 */
	public void runRightShoot(double speed) {
		rightShoot.set(ControlMode.PercentOutput, speed);
	}
	
	
	//Encoders
	
	/**
	 * Gets the encoder velocity of the left shoot motor
	 * @return The encoder velocity of the left shoot motor in RPS
	 */
	public double getLeftShootVelocity() {
		return -leftShoot.getSelectedSensorVelocity(0) * Constants.shootEncoderVelocityToRPS;
	}
	
	/**
	 * Gets the encoder velocity of the right shoot motor
	 * @return The encoder velocity of the right shoot motor in RPS
	 */
	public double getRightShootVelocity() {
		return rightShoot.getSelectedSensorVelocity(0) * Constants.shootEncoderVelocityToRPS;
	}
	
	/**
	 * Gets the average encoder velocity of the two shoot motors
	 * @return The average encoder velocity of the shoot motors
	 */
	public double getShootVelocity() {
		return (getLeftShootVelocity() + getRightShootVelocity())/2;
	}
	
	//Solenoid
	
	/**
	 * Lowers the shooter to shoot Power Cubes into the switch
	 */
	public void setSwitchPosition() {
		elevator.set(Value.kForward);
	}
	
	/**
	 * Raises the shooter to shoot Power Cubes into the scale
	 */
	public void setScalePosition() {
		elevator.set(Value.kReverse);
	}
	
	/**
	 * Checks whether the shooter is raised
	 * @return The status of whether the shooter is raised
	 */
	public boolean inSwitchPosition() {
		return elevator.get() == Value.kForward || elevator.get() == Value.kOff;
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

