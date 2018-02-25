
package com._2491nomythic.tempest.settings;

/**
 * Various information needed for robot functionality that can be modified by the code itself
 */
public class Variables {
	
	//Drive
	public static boolean useLinearAcceleration = true;
	public static double accelerationSpeed = 0.05;
	public static double driveDefault = 1;
	public static double driveRestriction = 1;
	
	//PID Constants from Watt
	public static double proportional = 0.00625;
	public static double integral = 0.0;
	public static double derivative = 0.0075;
	public static double proportionalForward = 0.023;
	public static double integralForward = 0;
	public static double derivativeForward = 0.000;
	
	public static double autoDelay;
	public static boolean useGyroPID;
	
	//Intake
	public static boolean isDeployed = false;
	public static double rollerCoefficient = 1;
	
	//CubeStorage
	public static boolean cubeHalted = false;
	
	//Shooter
	public static boolean readyToFire = false;
	public static double leftShootSpeed = Constants.shooterMediumScaleSpeed;
	public static double rightShootSpeed = Constants.shooterMediumScaleSpeed;
	public static double shooterSpeed = Constants.shooterMediumScaleSpeed;
	public static double shooterRPS = Constants.shooterMediumScaleRPS;
	public static boolean inSwitchPosition = false;
	public static double reverseCoefficient = 1;
	public static boolean useMonitorRPS = false;
	
	//Shooter PID
	public static double leftShootProportional = .08;
	public static double leftShootIntegral = 0;
	public static double leftShootDerivative = .035;
	public static boolean leftShootReady = false;
	
	public static double rightShootProportional = .075;
	public static double rightShootIntegral = 0;
	public static double rightShootDerivative = .04;
	public static boolean rightShootReady = false;
}
