
package com._2491nomythic.tempest.settings;

/**
 * Various information needed for robot functionality that can be modified by the code itself
 */
public class Variables {
	
	//Drive
	public static boolean useLinearAcceleration = true;
	public static double accelerationSpeed = 0.05;
	public static double driveDefault = 1;
	public static double driveAdjustmentCoefficient = 1;
	
	//PID
	public static double proportionalRotate = 0.01; // 0.00625
	public static double integralRotate = 0.0;
	public static double derivativeRotate = 0.03; //0.0075
    
	public static double proportionalForward = 0.02;
	public static double integralForward = 0;
	public static double derivativeForward = 0;
	
	public static double autoDelay;
	public static boolean useGyroPID;
	
	//Intake
	public static double rollerReverseCoefficient = 1;
	
	//Shooter
	public static boolean readyToFire = false;
	public static double leftShootSpeed = Constants.shooterMediumScaleSpeed;
	public static double rightShootSpeed = Constants.shooterMediumScaleSpeed;
	public static double shooterSpeed = Constants.shooterMediumScaleSpeed;
	public static double shooterRPS = Constants.shooterMediumScaleRPS;
    public static double reverseCoefficient = 1;
	public static boolean useMonitorRPS = false;
	
	//Buttonboard
	public static int motorChoice;
	
	//Lights
	public static boolean useLights = true;
	
	//Shooter PID
	public static double leftShootProportional = .0083;
	public static double leftShootIntegral = 0;
	public static double leftShootDerivative = .002;
	public static boolean leftShootReady = false;
	
	public static double rightShootProportional = .0083;
	public static double rightShootIntegral = 0;
	public static double rightShootDerivative = .002;
	public static boolean rightShootReady = false;
	
	public static double shootFeedForward = Constants.shootFeedForwardMed;
    
    /* Debug Mode */
    public static boolean debugMode = false;
    
    //Drivetrain FPID
    public static double kPID_p = 1;
    public static double kPID_i = 0;
    public static double kPID_d = 0;
    public static boolean letsGo = false;
    
   public static boolean isPathRunning = false;
    
    //Climber
    public static boolean lineupDeployed = false;
    public static boolean grappleHookDeployed = false;

}
