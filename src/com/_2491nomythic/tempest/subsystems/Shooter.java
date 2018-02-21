package com._2491nomythic.tempest.subsystems;

import com._2491nomythic.tempest.settings.Constants;
import com._2491nomythic.tempest.settings.Variables;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

/**
 * The system of motors and encoders that is used to launch Power Cubes from the robot into the Switch and Scale
 */
public class Shooter extends PIDSubsystem {
	private static Shooter instance;
	private TalonSRX leftAccelerate, rightAccelerate, leftShoot, rightShoot;
	private Solenoid elevator;
	private double currentPIDOutput;
	
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
		super("Shooter", Variables.shooterProportional, Variables.shooterIntegral, Variables.shooterDerivative);
		leftAccelerate = new TalonSRX(Constants.shooterTalonLeftAccelerateChannel);
		rightAccelerate = new TalonSRX(Constants.shooterTalonRightAccelerateChannel);
		leftShoot = new TalonSRX(Constants.shooterTalonLeftShootChannel);
		rightShoot = new TalonSRX(Constants.shooterTalonRightShootChannel);
		
		elevator = new Solenoid(Constants.shooterElevatorChannel);
		
		leftAccelerate.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		rightAccelerate.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		leftShoot.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		rightShoot.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		
		setInputRange(-30, 30);
		setAbsoluteTolerance(1);
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
		leftShoot.set(ControlMode.PercentOutput, speed);
		rightShoot.set(ControlMode.PercentOutput, speed);
	}
	
	/**
	 * 
	 * @param speed Speed to set the motors
	 */
	public void run(double speed) {
		runAccelerate(speed * Variables.reverseCoefficient);
		runShoot(speed * Variables.reverseCoefficient);
	}
	
	public void run(double leftShootSpeed, double rightShootSpeed, double accelSpeed) {
		leftShoot.set(ControlMode.PercentOutput, leftShootSpeed);
		rightShoot.set(ControlMode.PercentOutput, rightShootSpeed);
		runAccelerate(accelSpeed);
	}
	
	
	//Encoders
	
	
	/**
	 * Gets the encoder velocity of the left accelerate motor
	 * @return The encoder velocity of the left accelerate motor in RPS
	 */
	public double getLeftAccelerateVelocity() {
		return -leftAccelerate.getSelectedSensorVelocity(0) * Constants.shootEncoderVelocityToRPS;
	}
	
	/**
	 * Gets the encoder velocity of the right accelerate motor
	 * @return The encoder velocity of the right accelerate motor in RPS
	 */
	public double getRightAccelerateVelocity() {
		return rightAccelerate.getSelectedSensorVelocity(0) * Constants.shootEncoderVelocityToRPS;
	}
	
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
	 * Gets the average encoder velocity of the two accelerate motors
	 * @return The average encoder velocity of the accelerate motors
	 */
	public double getAccelerateVelocity() {
		return (getLeftAccelerateVelocity() + getRightAccelerateVelocity())/2;
	}
	
	/**
	 * Gets the average encoder velocity of the two shoot motors
	 * @return The average encoder velocity of the shoot motors
	 */
	public double getShootVelocity() {
		return (getLeftShootVelocity() + getRightShootVelocity())/2;
	}
	
	/**
	 * Gets the average encoder velocity of all motors within the shooter subsystem
	 * @return The average encoder velocity of all shooter motors
	 */
	public double getAllMotorVelocity() {
		return (getShootVelocity() + getAccelerateVelocity())/2;
	}
	
	//Solenoid
	
	/**
	 * Raises the shooter to shoot Power Cubes into the scale
	 */
	public void raise() {
		elevator.set(false);
		Variables.isLowered = false;
	}
	
	/**
	 * Lowers the shooter to shoot Power Cubes into the switch
	 */
	public void lower() {
		if(!Variables.isDeployed) {
			System.out.println("You dun goofed");
		}
		else {
			elevator.set(true);
			Variables.isLowered = true;
		}
	}
	
	/**
	 * Checks whether the shooter is raised
	 * @return The status of whether the shooter is raised
	 */
	public boolean isRaised() {
		return !elevator.get();
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
	
	@Override
	public double returnPIDInput() {
		return getAllMotorVelocity();
	}
	
	@Override
	public void usePIDOutput(double output) {
		run(output);
	}
	
	public double getPIDOutput() {
		return currentPIDOutput;
	}
}

