package com._2491nomythic.tempest.settings;

public class GuitarControllerMap {

	//Controllers
	public static final int driveController = 0;
	public static final int operatorController = 1;
	
	//Driver
	public static final int driveTurnAxis = 2;
	public static final int driveMainAxis = 1;
	public static final int killSwitchButton1 = 11;
	public static final int killSwitchButton2 = 12;
	
	//Operator
			
	public static final boolean[] setSwitchRPS = {true, true, true, true, true};
	public static final boolean[] setLowScaleRPS = {true, true, true, true, true};
	public static final boolean[] setMediumScaleRPS = {true, true, true, true, true};
	public static final boolean[] setHighScaleRPS = {true, true, true, true, true};
	public static final boolean[] deployIntake = {true, true, true, true, true};
	public static final boolean[] openIntake = {true, true, true, true, true};
	public static final boolean[] raiseShooter = {true, true, true, true, true};
	public static final int intakeAxis = 1;
	public static final int cubeStorageAxis = 3;
	public static final boolean[] shooterButton = {true, true, true, true, true}; //axis 4
	public static final boolean[] shooterReverseButton = {true, true, true, true, true};
	public static final boolean[] IntakeRollerlessButton = {true, true, true, true, true};
	public static final boolean[] automaticIntakeButton = {true, true, true, true, true};
	
	//Probably not going to be used
	
	public static final int driverScaleShootButton = 8;
	public static final int driverAutoShootButton = 2;
	public static final int driverSwitchShootButton = 7;	

}
