package com._2491nomythic.tempest.subsystems;

import com._2491nomythic.tempest.settings.Constants;
import com._2491nomythic.tempest.settings.Variables;
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
	private TalonSRX left, right, bottom;
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
		bottom = new TalonSRX(Constants.intakeTalonBottomChannel);
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
		runBottom(speed);
	}
	
	public void run(double leftSpeed, double rightSpeed, double bottomSpeed) {
		runLeft(leftSpeed);
		runRight(rightSpeed);
		runBottom(bottomSpeed);
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
	 * Runs the bottom part of the intake to capture Power Cubes.
	 * @param speed the speed that the motors will run at.
	 */
	public void runBottom(double speed) {
		bottom.set(ControlMode.PercentOutput, speed);
	}
	
	/**
	 * Sets the intake out of the frame perimeter.
	 */
	public void activate() {
		activateIntakeSolenoid.set(Value.kForward);
		Variables.isDeployed = true;
		Variables.driveRestriction = 1;
	}
	
	/**
	 * Sets the intake in the frame perimeter.
	 */
	public void retract() {
		if(Variables.inSwitchPosition) {
			
		}
		else {
			activateIntakeSolenoid.set(Value.kReverse);
			close();
			Variables.isDeployed = false;
			Variables.driveRestriction = 1;
		}
	}
	
	/**
	 * Sets the intake to the open state.
	 */
	public void open() {
		intakeOpenSolenoid.set(Value.kForward);
	}
	
	/**
	 * Sets the intake to the closed state.
	 */
	public void close() {
		intakeOpenSolenoid.set(Value.kReverse);
	}
	
	/**
	 * Returns whether or not the intake is extended.
	 * @return Whether or not the intake is extended.
	 */
	public boolean isDeployed() {
		return activateIntakeSolenoid.get() == Value.kForward;
	}
	
	/**
	 * Returns whether or not the intake is open.
	 * @return Whether or not the intake is open.
	 */
	public boolean isOpened() {
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

