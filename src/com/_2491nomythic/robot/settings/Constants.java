package com._2491nomythic.robot.settings;

/**
 * Various information needed for robot functionality that cannot be modified by the code itself
 */
public class Constants {
	
	//Drive
	public static final int driveTalonLeft1Channel = 11;
	public static final int driveTalonLeft2Channel = 19;
	public static final int driveTalonLeft3Channel = 14;
	public static final int driveTalonRight1Channel = 12;
	public static final int driveTalonRight2Channel = 15;
	public static final int driveTalonRight3Channel = 13;
	public static final int driveEncoderLeftChannel1 = 3;
	public static final int driveEncoderLeftChannel2 = 4;
	public static final int driveEncoderCenterChannel1 = 5;
	public static final int driveEncoderCenterChannel2 = 6;
	public static final int driveEncoderRightChannel1 = 7;
	public static final int driveEncoderRightChannel2 = 8;
	
	//Intake
	public static final int intakeTalonLeftChannel = 2491; //TODO Change this to an actual talon
	public static final int intakeTalonRightChannel = 2491; //TODO Change this to an actual talon
	
	//Shooter
	public static final int shooterTalonLeftHoldChannel = 2491; //TODO Change this to an actual talon
	public static final int shooterTalonLeftAccelerateChannel = 2491; //TODO Change this to an actual talon
	public static final int shooterTalonLeftShootChannel = 2491; //TODO Change this to an actual talon
	public static final int shooterTalonRightHoldChannel = 2491; //TODO Change this to an actual talon
	public static final int shooterTalonRightAccelerateChannel = 2491; //TODO Change this to an actual talon
	public static final int shooterTalonRightShootChannel = 2491; //TODO Change this to an actual talon
		
	//Computation
	public static final double driveEncoderToInches = 1.066 * 2.4 * 1200.0 / 670 / 256; //TODO update for 2018. Currently in 2017 inches.
	
}