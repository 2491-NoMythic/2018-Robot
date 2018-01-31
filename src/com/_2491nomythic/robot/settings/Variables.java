package com._2491nomythic.robot.settings;

/**
 * Various information needed for robot functionality that can be modified by the code itself
 */
public class Variables {
	
	//Drive
	public static boolean useLinearAcceleration = true;
	public static double accelerationSpeed = 0.05;
	
	//PID Constants from Watt
	public static double proportional = 0.016;
	public static double integral = 0.0;
	public static double derivative = 0.007;
	public static double proportionalForward = 0.023;
	public static double integralForward = 0;
	public static double derivativeForward = 0.000;
	
	public static double autoDelay;
	public static boolean useGyroPID;
	
	//Shooter
	public static boolean accelerateReady;
	public static boolean shootReady;
	public static int shooterSpeed;

}
