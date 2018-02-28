
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
	
	//PID
	public static double proportionalRotate = 0.00625;
	public static double integralRotate = 0.0;
	public static double derivativeRotate = 0.0075;
	public static double proportionalForward = 0.02;
	public static double integralForward = 0;
	public static double derivativeForward = 0;
	
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
	public static boolean useMonitorRPS = false;
	
	//Shooter PID
	public static double leftShootProportional = .0003;
	public static double leftShootIntegral = 0;
	public static double leftShootDerivative = .0002;
	public static double leftShootFeedForward = Constants.leftShootFeedForwardMed;
	public static boolean leftShootReady = false;
	
	public static double rightShootProportional = .00025;
	public static double rightShootIntegral = 0;
	public static double rightShootDerivative = .0002;
	public static double rightShootFeedForward = Constants.rightShootFeedForwardMed;
	public static boolean rightShootReady = false;
}
