package com._2491nomythic.tempest.settings;

/**
 * Contains all variables affecting use of controllers, to keep it in a centralized and clean location
 */
public class ControllerMap {
	
	//Controllers
	public static final int driveController = 0;
	public static final int operatorController = 1;
	
	//Driver
	public static final int driveTurnAxis = 2;
	public static final int driveMainAxis = 1;
	public static final int killSwitchButton1 = 9;
	public static final int killSwitchButton2 = 10;
	public static final int adjustmentButton1 = 11;
	public static final int adjustmentButton2 = 12;
	
	//Operator
	
	public static final int setSwitchRPS = 1;//2;
	public static final int setLowScaleRPS = 3;//1;
	public static final int setMediumScaleRPS = 4;
	public static final int setHighScaleRPS = 2;//3;
	public static final int deployIntake = 5;
	public static final int openIntake = 2;//7; if 2, is an axis button
	public static final int raiseShooter = 6;
	public static final int intakeAxis = 1;
	public static final int cubeStorageAxis = 3;
	public static final int shooterButton = 3;//8; if 3, is an axis button
	public static final int shooterReverseButton = 7;//14;
	public static final int IntakeRollerlessButton = 9;//11;
	
	//Probably not going to be used

	public static final int driverScaleShootButton = 8;
	public static final int driverAutoShootButton = 2;
	public static final int driverSwitchShootButton = 7;

}
