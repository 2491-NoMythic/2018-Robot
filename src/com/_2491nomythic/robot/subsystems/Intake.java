package com._2491nomythic.robot.subsystems;

import com._2491nomythic.robot.commands.intake.RunIntakeManual;
import com._2491nomythic.robot.settings.Constants;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The subsystem that takes Power Cubes from the field.
 */
public class Intake extends Subsystem {
	private static Intake instance;
	private TalonSRX left1, left2, right1, right2, bottom;
	private Solenoid activateIntakeSolenoid, intakeOpenSolenoid; 
	
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
		left1 = new TalonSRX(Constants.intakeTalonLeft1Channel);
		left2 = new TalonSRX(Constants.intakeTalonLeft2Channel);
		right1 = new TalonSRX(Constants.intakeTalonRight1Channel);
		right2 = new TalonSRX(Constants.intakeTalonRight2Channel);
		bottom = new TalonSRX(Constants.intakeTalonBottomChannel);
		//activateIntakeSolenoid = new Solenoid(Constants.intakeSolenoidActivateChannel);
		//intakeOpenSolenoid = new Solenoid(Constants.intakeSolenoidOpenChannel);
		
		left2.follow(left1);
		right2.follow(right2);
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
	
	/**
	 * Runs the left side of the intake to capture Power Cubes.
	 * @param speed The speed that the motors will run at.
	 */
	public void runLeft(double speed) {
		left1.set(ControlMode.PercentOutput, speed);
	}
	
	/**
	 * Runs the right side of the intake to capture Power Cubes.
	 * @param speed the speed that the motors will run at.
	 */
	public void runRight(double speed) {
		right1.set(ControlMode.PercentOutput, speed);
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
		activateIntakeSolenoid.set(true);
	}
	
	/**
	 * Sets the intake in the frame perimeter.
	 */
	public void retract() {
		activateIntakeSolenoid.set(false);
	}
	
	/**
	 * Sets the intake to the open state.
	 */
	public void open() {
		intakeOpenSolenoid.set(true);
	}
	
	/**
	 * Sets the intake to the closed state.
	 */
	public void close() {
		intakeOpenSolenoid.set(false);
	}
	
	/**
	 * Stops the intake motors.
	 */
	public void stop() {
		run(0);
	}
	
	
	
	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		//setDefaultCommand(new MySpecialCommand());
		//setDefaultCommand(new RunIntakeManual());
	}
}

