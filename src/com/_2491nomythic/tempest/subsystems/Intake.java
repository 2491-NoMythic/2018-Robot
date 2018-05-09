package com._2491nomythic.tempest.subsystems;

import com._2491nomythic.tempest.settings.Constants;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The subsystem that takes Power Cubes from the field.
 */
public class Intake extends Subsystem {
	private static Intake instance;
	private TalonSRX left, right;
	private DoubleSolenoid activateIntakeSolenoid, intakeOpenSolenoid; 
	
	public static Intake getInstance() {
		if (instance == null) {
			instance = new Intake();
		}
		return instance;
	}
	
	/**
	 * The subsystem that takes Power Cubes from the field
	 */
	public Intake() {
		left = new TalonSRX(Constants.intakeTalonLeftChannel);
		right = new TalonSRX(Constants.intakeTalonRightChannel);
		activateIntakeSolenoid = new DoubleSolenoid(Constants.intakeSolenoidActivateChannelForward, Constants.intakeSolenoidActivateChannelReverse);
		intakeOpenSolenoid = new DoubleSolenoid(Constants.intakeSolenoidOpenChannelForward, Constants.intakeSolenoidOpenChannelReverse);
	}
	
	/**
	 * Runs both sides of the intake to capture Power Cubes.
	 * @param speed The speed that the motors will run at.
	 */
	public void run(double speed) {
		runLeft(speed);
		runRight(speed);
	}
	
	public void run(double leftSpeed, double rightSpeed) {
		runLeft(leftSpeed);
		runRight(rightSpeed);
	}
	
	/**
	 * Runs the left side of the intake to capture Power Cubes.
	 * @param speed The speed that the motors will run at.
	 */
	public void runLeft(double speed) {
		left.set(ControlMode.PercentOutput, -speed);
	}
	
	/**
	 * Runs the right side of the intake to capture Power Cubes.
	 * @param speed the speed that the motors will run at.
	 */
	public void runRight(double speed) {
		right.set(ControlMode.PercentOutput, speed);
	}
	
	/**
	 * Sets the intake out of the frame perimeter.
	 */
	public void openArms() {
		activateIntakeSolenoid.set(Value.kForward);
	}
	
	/**
	 * Sets the intake in the frame perimeter.
	 */
	public void retractArms() {
		activateIntakeSolenoid.set(Value.kReverse);
		retractFingers();
	}
	
	/**
	 * Sets the intake fingers to the open state.
	 */
	public void openFingers() {
		intakeOpenSolenoid.set(Value.kForward);
	}
	
	/**
	 * Sets the intake fingers to the closed state.
	 */
	public void retractFingers() {
		intakeOpenSolenoid.set(Value.kReverse);
	}
	
	/**
	 * Returns whether or not the intake arms are extended.
	 * @return Whether or not the intake arms are extended.
	 */
	public boolean armsRetracted() {
		return activateIntakeSolenoid.get() == Value.kReverse || activateIntakeSolenoid.get() == Value.kOff;
	}
	
	/**
	 * Returns whether or not the intake fingers are open.
	 * @return Whether or not the intake fingers are open.
	 */
	public boolean fingersOpened() {
		return intakeOpenSolenoid.get() == Value.kForward;
	}
	
	/**
	 * Stops the intake motors.
	 */
	public void stop() {
		run(0);
	}
	
	
	

	public void initDefaultCommand() {
	}
}

