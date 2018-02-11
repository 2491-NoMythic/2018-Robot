package com._2491nomythic.robot.settings;

/**
 * Various information needed for robot functionality that cannot be modified by the code itself
 */
public class Constants {
	
	//Drive
	public static final int driveTalonLeft1Channel = 9;
	public static final int driveTalonLeft2Channel = 10;
	public static final int driveTalonRight1Channel = 4;
	public static final int driveTalonRight2Channel = 5;
	
	//Intake
	public static final int intakeTalonLeft1Channel = 21;
	public static final int intakeTalonLeft2Channel = 6;
	public static final int intakeTalonRight1Channel = 7;
	public static final int intakeTalonRight2Channel = 8;
	public static final int intakeTalonBottomChannel = 22;
	public static final int intakeSolenoidActivateChannel = 2491; //TODO Change this to an actual solenoid channel.
	public static final int intakeSolenoidOpenChannel = 2491; //TODO Change this to an actual solenoid channel.

	//Shooter
	public static final int shooterTalonLeftAccelerateChannel = 12;
	public static final int shooterTalonLeftShootChannel = 14;
	public static final int shooterTalonRightAccelerateChannel = 13;
	public static final int shooterTalonRightShootChannel = 15;
	public static final int shooterElevatorChannel = 12;
	public static final int timeForShooterToSpinUp = 2; //TODO time this
	public static final int timeForShooterToRaise = 4; //TODO time this
	public static final int timeForShooterToFire = 2; //TODO Time this
	public static final double shooterHighScaleSpeed = 1; //TODO Find an actual value for this
	public static final double shooterMediumScaleSpeed = .8; //TODO Find an actual value for this
	public static final double shooterLowScaleSpeed = .6; //TODO Find an actual value for this
	public static final double shooterSwitchSpeed = .3; //TODO Find an actual value for this
	
	//Lights
	public static final int lightSerialChannel = 1;
	
	//CubeStorage
	public static final int cubeStorageTalonLeftChannel = 23;
	public static final int cubeStorageTalonRightChannel = 11;
	public static final int ultrasonicDigitalInput = 1;
	public static final int ultrasonicDigitalOutput = 0;
	public static final double heldCubeRange = 2491;//TODO actual value needed
		
	//Computation
	public static final double driveEncoderToInches = 1.0 / 4096.0 * 6.0 * Math.PI; 
	public static final double speedModeRPSToTalonOutput = 4096.0 / 10.0;
	public static final double driveEncoderVelocityToRPS = 1.0 / 4096.0 * 10;
	public static final double driveMaxSpeedRPS = 8.0; //approximately
	
	public static final double shootEncoderVelocityToRPS = (4.0 * Math.PI) / 4096;
}