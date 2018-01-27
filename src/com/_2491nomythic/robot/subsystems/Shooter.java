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
	private TalonSRX leftAccelerate, rightAccelerate, leftShoot, rightShoot;
	
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
		
		leftAccelerate.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		rightAccelerate.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		leftShoot.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		rightShoot.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
	}
	
	
	//Motors
	
	/**
	 * Runs the motors used to initially speed up Power Cubes within the robot, readying them for launch, with a given power
	 * @param speed The speed in RPM that the motors are supposed to run at.
	 */
	public void runAccelerate(double speed) {
		leftAccelerate.set(ControlMode.Velocity, speed / Constants.encoderTicsToRPM);
		rightAccelerate.set(ControlMode.Velocity, speed / Constants.encoderTicsToRPM);
	}
	
	/**
	 * Runs the motors used to maintain momentum of, and finally launch, the Power Cubes from the robot with a given power
	 * @param speed The speed in RPM that the motors are supposed to run at.
	 */
	public void runShoot(double speed) {
		leftShoot.set(ControlMode.Velocity, speed / Constants.encoderTicsToRPM);
		rightShoot.set(ControlMode.Velocity, speed / Constants.encoderTicsToRPM);
	}
	
	
	//Encoders
	
	
	/**
	 * Gets the encoder velocity of the left accelerate motor
	 * @return The encoder velocity of the left accelerate motor in RPM
	 */
	public double getLeftAccelerateEncoder() {
		return leftAccelerate.getSelectedSensorVelocity(0) * Constants.encoderTicsToRPM;
	}
	
	/**
	 * Gets the encoder velocity of the right accelerate motor
	 * @return The encoder velocity of the right accelerate motor in RPM
	 */
	public double getRightAccelerateEncoder() {
		return rightAccelerate.getSelectedSensorVelocity(0) * Constants.encoderTicsToRPM;
	}
	
	/**
	 * Gets the encoder velocity of the left shoot motor
	 * @return The encoder velocity of the left shoot motor in RPM
	 */
	public double getLeftShootEncoder() {
		return leftShoot.getSelectedSensorVelocity(0) * Constants.encoderTicsToRPM;
	}
	
	/**
	 * Gets the encoder velocity of the right shoot motor
	 * @return The encoder velocity of the right shoot motor in RPM
	 */
	public double getRightShootEncoder() {
		return rightShoot.getSelectedSensorVelocity(0) * Constants.encoderTicsToRPM;
	}
	

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void stop() {
    	runAccelerate(0);
    	runShoot(0);
    }
}

