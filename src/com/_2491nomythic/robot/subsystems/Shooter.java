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
	 * @param speed The speed in RPM that the motors are supposed to run at.
	 */
	public void runAccelerate(double speed) {
		leftAccelerate.set(ControlMode.Velocity, speed / Constants.encoderTicksToRPM);
		rightAccelerate.set(ControlMode.Velocity, speed / Constants.encoderTicksToRPM);
	}
	
	/**
	 * Runs the motors used to maintain momentum of, and finally launch, the Power Cubes from the robot with a given power
	 * @param speed The speed in RPM that the motors are supposed to run at.
	 */
	public void runShoot(double speed) {
		leftShoot.set(ControlMode.Velocity, speed / Constants.encoderTicksToRPM);
		rightShoot.set(ControlMode.Velocity, speed / Constants.encoderTicksToRPM);
	}
	
	/**
	 * 
	 * @param speed Speed to set the motors in RPM
	 */
	public void run(double speed) {
		runAccelerate(speed);
		runShoot(speed);
	}
	
	
	//Encoders
	
	
	/**
	 * Gets the encoder velocity of the left accelerate motor
	 * @return The encoder velocity of the left accelerate motor in RPM
	 */
	public double getLeftAccelerateVelocity() {
		return leftAccelerate.getSelectedSensorVelocity(0) * Constants.encoderTicksToRPM;
	}
	
	/**
	 * Gets the encoder velocity of the right accelerate motor
	 * @return The encoder velocity of the right accelerate motor in RPM
	 */
	public double getRightAccelerateVelocity() {
		return rightAccelerate.getSelectedSensorVelocity(0) * Constants.encoderTicksToRPM;
	}
	
	/**
	 * Gets the encoder velocity of the left shoot motor
	 * @return The encoder velocity of the left shoot motor in RPM
	 */
	public double getLeftShootVelocity() {
		return leftShoot.getSelectedSensorVelocity(0) * Constants.encoderTicksToRPM;
	}
	
	/**
	 * Gets the encoder velocity of the right shoot motor
	 * @return The encoder velocity of the right shoot motor in RPM
	 */
	public double getRightShootVelocity() {
		return rightShoot.getSelectedSensorVelocity(0) * Constants.encoderTicksToRPM;
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
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    /**
     * Stops all motors within the shooter subsystem
     */
    public void stop() {
    	runAccelerate(0);
    	runShoot(0);
    }
}

