package com._2491nomythic.robot.subsystems;

import com._2491nomythic.robot.commands.drivetrain.Drive;
import com._2491nomythic.robot.settings.Constants;
import com._2491nomythic.robot.settings.Variables;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

/**
 * The system of motors, solenoids, encoders, and a gyro that allows us to drive the robot
 */
public class Drivetrain extends PIDSubsystem {
	private TalonSRX left1, left2, left3, right1, right2, right3;
	private AHRS gyro;
	private double currentPIDOutput;
	
	private static Drivetrain instance;
	
	
	public static Drivetrain getInstance() {
		if (instance == null) {
			instance = new Drivetrain();
		}
		return instance;
	}
	
	/**
	 * The system of motors, solenoids, encoders, and a gyro that allows us to drive the robot
	 */
	private Drivetrain() {
		super("Drive", Variables.proportional, Variables.integral, Variables.derivative);
		setInputRange(0, 360);
		getPIDController().setContinuous();
		
		left1 = new TalonSRX(Constants.driveTalonLeft1Channel);
		left2 = new TalonSRX(Constants.driveTalonLeft2Channel);
		left3 = new TalonSRX(Constants.driveTalonLeft3Channel);
		right1 = new TalonSRX(Constants.driveTalonRight1Channel);
		right2 = new TalonSRX(Constants.driveTalonRight2Channel);
		right3 = new TalonSRX(Constants.driveTalonRight3Channel);
		
		left2.follow(left1);
		left3.follow(left1);
		right2.follow(right1);
		right3.follow(right1);
		
		left1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		right1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
				
		gyro = new AHRS(SerialPort.Port.kUSB);
	}
	
	/**
	 * Drives the robot forward or backward only
	 * @param speed The power fed to the vertical drive motors, ranging from -1 to 1, where negative values run the motors backwards
	 */
	public void drive(double speed){
		drive(speed, speed);
	}
	
	/**
	 * Drives the robot with each set of motors receiving an individual specific speed
	 * @param leftSpeed The power fed to the left drive motors, ranging from -1 to 1, where negative values run the motors backwards
	 * @param rightSpeed The power fed to the right drive motors, ranging from -1 to 1, where negative values run the motors backwards
	 */
	public void drive(double leftSpeed, double rightSpeed){
		driveLeft(leftSpeed);
		driveRight(rightSpeed);
	}
	
	/**
	 * Drives the left side of the robot
	 * @param speed The power fed to the motors, ranging from -1 to 1, where negative values run the motors backwards
	 */
	public void driveLeft(double speed){
		left1.set(ControlMode.PercentOutput, -speed);
	}
	
	/**
	 * Drives the right side of the robot
	 * @param speed The power fed to the motors, ranging from -1 to 1, where negative values run the motors backwards
	 */
	public void driveRight(double speed){
		right1.set(ControlMode.PercentOutput, speed);
	}
	
	/**
	 * Stops all drive motion
	 */
	public void stop(){
		drive(0, 0);
	}
	
	/**
	 * Changes drive motors to Coast mode
	 */
	public void enableCoastMode() {
		left1.setNeutralMode(NeutralMode.Coast);
		left2.setNeutralMode(NeutralMode.Coast);
		left3.setNeutralMode(NeutralMode.Coast);
		right1.setNeutralMode(NeutralMode.Coast);
		right2.setNeutralMode(NeutralMode.Coast);
		right3.setNeutralMode(NeutralMode.Coast);
	}
	
	/**
	 * Changes drive motors to Brake mode
	 */
	public void enableBrakeMode() {
		left1.setNeutralMode(NeutralMode.Brake);
		left2.setNeutralMode(NeutralMode.Brake);
		left3.setNeutralMode(NeutralMode.Brake);
		right1.setNeutralMode(NeutralMode.Brake);
		right2.setNeutralMode(NeutralMode.Brake);
		right3.setNeutralMode(NeutralMode.Brake);
	}
	
	/**
	 * Resets the left drive encoder value to 0
	 */
	public void resetLeftEncoder() {
		left1.setSelectedSensorPosition(0, 0, 0);
	}
	
	/**
	 * Resets the right drive encoder value to 0
	 */
	public void resetRightEncoder() {
		right1.setSelectedSensorPosition(0, 0, 0);
	}
	
	/**
	 * Resets the left and right encoders
	 */
	public void resetEncoders() {
		resetLeftEncoder();
		resetRightEncoder();
	}
	
	/**
	 * @return The value of the left drive encoder in inches
	 */
	public double getLeftEncoderDistance() {
		return left1.getSelectedSensorPosition(0) * Constants.driveEncoderToInches;
	}
	
	/**
	 * @return The value of the right drive encoder in inches
	 */
	public double getRightEncoderDistance() {
		return -right1.getSelectedSensorPosition(0) * Constants.driveEncoderToInches;
	}
	
	/**
	 * @return The average value of the two encoders in inches
	 */
	public double getDistance() {
		return (getRightEncoderDistance() + getLeftEncoderDistance()) / 2;
	}
	
	/**
	 * @return The speed of the left motor in feet per second
	 */
	public double getLeftEncoderRate() {
		return left1.getSelectedSensorVelocity(0) * 10 * Constants.driveEncoderToInches;
	}
	
	/**
	 * @return The speed of the right motor in feet per second
	 */
	public double getRightEncoderRate() {
		return -right1.getSelectedSensorVelocity(0) * 10 * Constants.driveEncoderToInches;
	}
	
	/**
	 * Resets the value of the gyro to 0
	 */
	public void resetGyro() {
		gyro.reset();
	}
	
	/**
	 * @return The value of the gyro, corrected to a 0-360 range
	 */
	public double getGyroAngle(){
		return (gyro.getAngle()  % 360 + 360) % 360;
	}	
	
	/**
	 * @return The angle of the gyro, unmodified
	 */
	public double getRawGyroAngle(){
		return gyro.getAngle();
	}
	
	public TalonSRX getLeftTalon() {
		return left1;
	}
	
	public TalonSRX getRightTalon() {
		return right1;
	}
	
	public AHRS getGyro() {
		return gyro;
	}
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new Drive());
    }
    
    @Override
    protected double returnPIDInput() {
    	if (Variables.useGyroPID) {
    		return getGyroAngle();
    	}
    	else {
    		return getDistance();
    	}
    }
    
    @Override
    protected void usePIDOutput(double output) {
    	drive(output, -output);
    }
    
    public double getPIDOutput() {
    	return currentPIDOutput;
    }
    
}

