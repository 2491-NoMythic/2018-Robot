package com._2491nomythic.tempest.settings;

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
	public static final int intakeTalonLeftChannel = 6;
	public static final int intakeTalonRightChannel = 2;
	public static final int intakeTalonBottomChannel = 1;
	public static final int intakeSolenoidActivateChannelForward = 4;
	public static final int intakeSolenoidActivateChannelReverse = 2;
	public static final int intakeSolenoidOpenChannelForward = 6;
	public static final int intakeSolenoidOpenChannelReverse = 3;

	//Shooter
	public static final int shooterTalonLeftAccelerateChannel = 13;
	public static final int shooterTalonRightAccelerateChannel = 14;
	public static final int shooterTalonLeftShootChannel = 15;
	public static final int shooterTalonRightShootChannel = 16;
	public static final int shooterElevatorChannelForward = 1;
	public static final int shooterElevatorChannelReverse = 5;
	public static final int timeForShooterToSpinUp = 2; //TODO time this
	public static final int timeForShooterToRaise = 4; //TODO time this
	public static final int timeForShooterToFire = 2; //TODO Time this
	public static final double shooterHighScaleSpeed = .75;
	public static final double shooterMediumScaleSpeed = .65;
	public static final double shooterLowScaleSpeed = .525;
	public static final double shooterSwitchSpeed = .3;
	public static double shooterHighScaleRPS = 85; // accelerate highscale rps = 28.5
	public static double shooterMediumScaleRPS = 75; // accelerate medscale rps = 24.75
	public static double shooterLowScaleRPS = 55; // accelerate lowscale rps = 19
	public static double shooterSwitchRPS = 33; //accelerate switch rps = 11.3
	
	//Lights
	public static final int underglowPWM = 1;
	public static final int shooterLights = 2;
	
	//CubeStorage
	public static final int cubeStorageTalonLeftChannel = 11;
	public static final int cubeStorageTalonRightChannel = 12;
	public static final int ultrasonicPingChannel = 1;
	public static final int ultrasonicEchoChannel = 0;
	public static final double heldCubeRange = 5;
	
	//Sick Lights
	public static final int sickLightsSolenoidChannel = 7;
	
	//Auto Paths
	public static final double[][] leftVelocityCenterStartPosLeftSwitchAutoPath = {{0.0,0.0},{0.1,1.7381941210937484},{0.2,2.539944628545473},{0.30000000000000004,2.9823730457288073},{0.4,3.2172861997587607},{0.5,3.3290628314202424},{0.6,3.3638717620728986},{0.7,3.346208876994869},{0.7999999999999999,3.288016633549444},{0.8999999999999999,3.1955893781324596},{0.9999999999999999,3.0739309534024435},{1.0999999999999999,2.9282952772385977},{1.1999999999999997,2.7718397120318503},{1.2999999999999998,2.628909412225757},{1.4,2.525001083974949},{1.5,2.4978156270384386},{1.6,2.580081746128342},{1.7000000000000002,2.735991694702631},{1.8000000000000003,2.927404629923813},{1.9000000000000004,3.130636616455376},{2.0000000000000004,3.324674941361481},{2.1000000000000005,3.5007973320266284},{2.2000000000000006,3.6596096804308473},{2.3000000000000007,3.8046254893228686},{2.400000000000001,3.9410636683056857},{2.500000000000001,4.073907217472468},{2.600000000000001,4.207433365984136},{2.700000000000001,4.339127552302324},{2.800000000000001,4.458128550255096},{2.9000000000000012,4.5553891260098816},{3.0000000000000013,4.609051216219102},{3.1000000000000014,4.597286203694043},{3.2000000000000015,4.552905862445104},{3.3000000000000016,4.500226084810956},{3.4000000000000017,4.4471023623798915},{3.5000000000000018,4.400134296088091},{3.600000000000002,4.358892230885848},{3.700000000000002,4.317665797744446},{3.800000000000002,4.268919598779599},{3.900000000000002,4.200316630891923},{4.000000000000002,4.091247819507862},{4.100000000000001,3.9072125081641285},{4.200000000000001,3.588232456838002},{4.300000000000001,3.02902161274461},{4.4,2.0442351287911116},{4.5,0.0}};
	public static final double[][] rightVelocityCenterStartPosLeftSwitchAutoPath = {{0.0,0.0},{0.1,1.7615857516107538},{0.2,2.5895039833372664},{0.30000000000000004,3.064337489883343},{0.4,3.3415417573841752},{0.5,3.509993216331834},{0.6,3.620850571426522},{0.7,3.704017253996546},{0.7999999999999999,3.7778722069048754},{0.8999999999999999,3.853186032249115},{0.9999999999999999,3.935525693408934},{1.0999999999999999,4.028091248257072},{1.1999999999999997,4.126549843443573},{1.2999999999999998,4.217921996438254},{1.4,4.290696611343759},{1.5,4.320168791278376},{1.6,4.2811512400211305},{1.7000000000000002,4.2047762284337775},{1.8000000000000003,4.115065111000537},{1.9000000000000004,4.019642397012619},{2.0000000000000004,3.9248917138618666},{2.1000000000000005,3.829975740697239},{2.2000000000000006,3.7293976540635625},{2.3000000000000007,3.6178926741612036},{2.400000000000001,3.491279293377764},{2.500000000000001,3.3480980516776655},{2.600000000000001,3.1902958249745614},{2.700000000000001,3.0296936897247595},{2.800000000000001,2.890233041626559},{2.9000000000000012,2.796763711948693},{3.0000000000000013,2.7853653003399073},{3.1000000000000014,2.884852599962102},{3.2000000000000015,3.0545573576262957},{3.3000000000000016,3.2530872205243013},{3.4000000000000017,3.4542556962936657},{3.5000000000000018,3.6345201951752615},{3.600000000000002,3.7819581100200512},{3.700000000000002,3.892266787060324},{3.800000000000002,3.9607569617265703},{3.900000000000002,3.980504459139522},{4.000000000000002,3.937377716551568},{4.100000000000001,3.80208395830931},{4.200000000000001,3.5192375739278976},{4.300000000000001,2.987566496733877},{4.4,2.0249306771323496},{4.5,0.0}};
	
	//Computation
	public static final double driveEncoderToInches = 1 / 6.0 * Math.PI / 4096.0 ;
	public static final double pathingTestEncoderCoversion= 4096.0 / Math.PI * 6.0; //Inches to encoder multiplication encoder to inches divisionS
	public static final double speedModeRPSToTalonOutput = 4096.0 / 10.0;
	public static final double driveEncoderVelocityToRPS = 1.0 / 4096.0 * 10;
	public static final double driveMaxSpeedRPS = 8.0; //approximately
	
	public static final double shootEncoderVelocityToRPS = (4.0 * Math.PI) / 4096;
}