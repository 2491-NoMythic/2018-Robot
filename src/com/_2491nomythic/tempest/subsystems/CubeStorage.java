package com._2491nomythic.tempest.subsystems;

import com._2491nomythic.tempest.settings.Constants;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Stores the cubes and moves them in and out of the storage
 */
public class CubeStorage extends Subsystem {
	private static CubeStorage instance;
	private TalonSRX left, right, roller;
	private Ultrasonic sonic;
	
	public static CubeStorage getInstance() {
		if (instance == null) {
			instance = new CubeStorage();
		}
		return instance;
	}
	
	/**
	 * Stores the cubes and moves them in and out of the storage
	 */
	private CubeStorage() {
		left = new TalonSRX(Constants.cubeStorageTalonLeftChannel);
		right = new TalonSRX(Constants.cubeStorageTalonRightChannel);
		roller = new TalonSRX(Constants.intakeTalonBottomChannel);
		sonic = new Ultrasonic(Constants.ultrasonicPingChannel, Constants.ultrasonicEchoChannel);
		sonic.setAutomaticMode(true);
	}

	
	//Motors
	/**
	 * Runs the storage motors at a specified power
	 * @param power The power at which the motors are run. Positive values push the cube into the shooter, negative values push it into the intake.
	 */
	public void run(double power) {
		left.set(ControlMode.PercentOutput, -power);
		right.set(ControlMode.PercentOutput, power);
		roller.set(ControlMode.PercentOutput, power);
	}
	
	public void runWithoutRoller(double power) {
		left.set(ControlMode.PercentOutput, -power);
		right.set(ControlMode.PercentOutput, power);
	}
	
	/**
	 * Stops all movement of the cube storage motors.
	 */
	public void stop() {
		run(0);
	}
	
	
	//Ultrasonic
	
	/**
	 * Finds the current range of the ultrasonic. If no target is within range, returns 0
	 * @return The current range of objects detected by the ultrasonic
	 */
	public double getRangeInches() {
		return sonic.getRangeInches();
	}
	
	/**
	 * Returns whether or not the last measurement made by the ultrasonic was valid/expected.
	 */
	public boolean getValidMeasurement() {
		return sonic.isRangeValid();
	}
	
	/**
	 * Checks if a cube is currently held within the CubeStorage system
	 * @return True if a cube is held, else returns false
	 */
	public boolean isHeld() {
		return (getRangeInches() <= (Constants.heldCubeRange) && getValidMeasurement());
	}
	

	public void initDefaultCommand() {
	}
}

