package com._2491nomythic.robot.subsystems;

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
	private TalonSRX left, right;
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
		left = new TalonSRX(Constants.intakeTalonLeftChannel);
		right = new TalonSRX(Constants.intakeTalonRightChannel);
		activateIntakeSolenoid = new Solenoid(Constants.intakeSolenoidActivateChannel);
		intakeOpenSolenoid = new Solenoid(Constants.intakeSolenoidOpenChannel);	
	}
	
	/**
	 * Runs both sides of the intake to intake Power Cubes.
	 * @param speed The speed that the motors will run at.
	 */
	public void runIntake(double speed) {
		runLeftIntake(speed);
		runRightIntake(speed);
	}
	
	/**
	 * Runs the left side of the intake to intake Power Cubes.
	 * @param speed The speed that the motors will run at.
	 */
	public void runLeftIntake(double speed) {
		left.set(ControlMode.PercentOutput, speed);
	}
	
	/**
	 * Runs the right side of the intake to intake Power Cubes.
	 * @param speed the speed that the motors will run at.
	 */
	public void runRightIntake(double speed) {
		right.set(ControlMode.PercentOutput, speed);
	}
	
	/**
	 * Sets the intake out of the frame perimeter.
	 */
	public void activateIntake() {
		activateIntakeSolenoid.set(true);
	}
	
	/**
	 * Sets the intake in the frame perimeter.
	 */
	public void retractIntake() {
		activateIntakeSolenoid.set(false);
	}
	
	/**
	 * Sets the intake to the open state.
	 */
	public void openIntake() {
		intakeOpenSolenoid.set(true);
	}
	
	/**
	 * Sets the intake to the closed state.
	 */
	public void closeIntake() {
		intakeOpenSolenoid.set(false);
	}
	
	
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

