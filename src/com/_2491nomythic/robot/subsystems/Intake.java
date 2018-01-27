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
	private Solenoid rightSolenoid, LeftSolenoid; 
	
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
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

