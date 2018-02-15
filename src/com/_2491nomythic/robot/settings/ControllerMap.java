package com._2491nomythic.robot.settings;

/**
 * Contains all variables affecting use of controllers, to keep it in a centralized and clean location
 */
public class ControllerMap {
	
	//Controllers
	public static final int driveController = 0;
	public static final int operatorController = 1;;
	
	//Driver
	public static final int driveTurnAxis = 2;
	public static final int driveMainAxis = 1;
	public static final int killSwitchButton1 = 11;
	public static final int killSwitchButton2 = 12;
	
	//Operator
	
	public static final int setSwitchRPM = 2;
	public static final int setScaleRPM = 4;
	public static final int deployIntake = 8;
	public static final int openIntake = 9;
	public static final int raiseShooter = 10;
	public static final int intakeAxis = 3;
	public static final int cubeStorageAxis = 1;
	
	//Probably not going to be used

	public static final int driverScaleShootButton = 8;
	public static final int driverAutoShootButton = 2;
	public static final int driverSwitchShootButton = 7;
	public static final int shooterAxis = 4;

}
