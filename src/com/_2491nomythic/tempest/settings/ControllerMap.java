package com._2491nomythic.tempest.settings;

/**
 * Contains all variables affecting use of controllers, to keep it in a centralized and clean location
 */
public class ControllerMap {
	
	//Controllers
	public static final int driveController = 0;
	public static final int operatorController = 1;
	public static final int buttonBoard = 2;
	public static final int driveSecondaryController = 3;
	
	//Driver
	public static final int driveTurnAxis = 2;
	public static final int driveMainAxis = 1;
	public static final int killSwitchButton1 = 9;
	public static final int killSwitchButton2 = 10;
	public static final int adjustmentButton1 = 11;
	public static final int adjustmentButton2 = 12;
	public static final int toggleLightsButton1 = 7;
	public static final int togglelightsButton2 = 8;
	public static final int tankTurnForwardButton = 1;
	public static final int tankTurnBackwardButton = 2;
	public static final int climberButton = 4; //TODO renumber this later
    
	//Operator	F310
	public static final int setSwitchRPS = 1;//f310: 1, ps4: 2;
	public static final int setLowScaleRPS = 3;//f310: 3, ps4: 1;
	public static final int setHighScaleRPS = 2;//f310: 2, ps4: 3;
	public static final int openIntakeFingers = 2;//f310: 2, ps4: 3
	public static final int shooterButton = 3;//f310: 3, ps4: 4
	
	//Operator PS4
	//public static final int setSwitchRPS = 2;//f310: 1, ps4: 2;
	//public static final int setLowScaleRPS = 1;//f310: 3, ps4: 1;
	//public static final int setHighScaleRPS = 3;//f310: 2, ps4: 3;
	//public static final int openIntakeFingers = 3;//f310: 2, ps4: 3
	//public static final int shooterButton = 4;//f310: 3, ps4: 4
	
	//Operator General
	public static final int setMediumScaleRPS = 4;
	public static final int deployIntake = 5;
	public static final int raiseShooter = 6;
	public static final int intakeAxis = 1;
	public static final int cubeStorageAxis = 5;
	public static final int shooterReverseButton = 180;
	public static final int convertToClimbMode = 7; //TODO renumber this as needed
	
	//Probably not going to be used

	public static final int driverScaleShootButton = 8;
	public static final int driverAutoShootButton = 2;
	public static final int driverSwitchShootButton = 7;
	
	//Operator Buttonboard
	public static final int killSwitchButton = 11;
	public static final int inputButton = 2;
	public static final int inputAxis = 1;
	public static final int spinUpButton = 12;
	public static final int bigRedButton = 6;
	public static final int outputAxis = 0;
	public static final int configureButton = 1;
	public static final int fingerButton = 3;
	public static final int scaleSpeedButton1 = 5;
	public static final int scaleSpeedButton2 = 4;
	
}
