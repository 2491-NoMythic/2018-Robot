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
	public static double shooterHighScaleSpeed = .75;
	public static double shooterMediumScaleSpeed = .625;
	public static double shooterLowScaleSpeed = .5;
	public static double shooterSwitchSpeed = .4;
	public static double shooterHighScaleRPS = 93; // accelerate highscale rps = 28.5
	public static double shooterMediumScaleRPS = 70; // accelerate medscale rps = 24.75
	public static double shooterLowScaleRPS = 58; // accelerate lowscale rps = 19
	public static double shooterSwitchRPS = 48; //accelerate switch rps = 11.3
	public static final double shooterMaxSpeedRPS = 125; // Approximate function of power input to RPS output: f(x) = 52.83x^2 + 44.54 RPS input to power output = f(x) = sqrt((x - 44.54) / 52.83)
	
	public static double shootFeedForwardHigh = .005;
	public static double shootFeedForwardMed = .00433;
	public static double shootFeedForwardLow = .0033;
	public static double shootFeedForwardSwitch = .002;
	
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
	public static final double[][] leftVelocityCenterStartPosLeftSwitchAutoPath = {{0.0,0.0},{0.1,1.6292753331788101},{0.2,2.4310090916350706},{0.30000000000000004,2.8734147495792595},{0.4,3.1082934370906226},{0.5,3.220015768299531},{0.6,3.2547380717451864},{0.7,3.2369366204940144},{0.7999999999999999,3.178522986925846},{0.8999999999999999,3.0857432502093425},{0.9999999999999999,2.9635273093297965},{1.0999999999999999,2.8170182270869604},{1.1999999999999997,2.659214247614932},{1.2999999999999998,2.5142455801521484},{1.4,2.4073276732840414},{1.5,2.375819832428266},{1.6,2.45202329339779},{1.7000000000000002,2.599442427713199},{1.8000000000000003,2.7789764029710837},{1.9000000000000004,2.9656792179706533},{2.0000000000000004,3.136834009293007},{2.1000000000000005,3.2816907842525858},{2.2000000000000006,3.398580164623628},{2.3000000000000007,3.488392881599486},{2.400000000000001,3.554252703330426},{2.500000000000001,3.600359047789072},{2.600000000000001,3.631503987559653},{2.700000000000001,3.649977165732579},{2.800000000000001,3.654358187321627},{2.9000000000000012,3.643610931112098},{3.0000000000000013,3.6009825098243162},{3.1000000000000014,3.4951918770700394},{3.2000000000000015,3.34328690024991},{3.3000000000000016,3.1656302065461723},{3.4000000000000017,2.974317526682408},{3.5000000000000018,2.79031857368011},{3.600000000000002,2.6257404040457017},{3.700000000000002,2.4822054152674538},{3.800000000000002,2.358348460880975},{3.900000000000002,2.2469713948936536},{4.000000000000002,2.134767970128804},{4.100000000000001,2.0019813307739547},{4.200000000000001,1.8167244266063265},{4.300000000000001,1.5258199788670366},{4.4,1.038690362686145},{4.5,0.0}};
	public static final double[][] rightVelocityCenterStartPosLeftSwitchAutoPath = {{0.0,0.0},{0.1,1.6513561313159402},{0.2,2.479266454800016},{0.30000000000000004,2.954091097545952},{0.4,3.2312843204119313},{0.5,3.3997218673517393},{0.6,3.510562550201762},{0.7,3.593711495455256},{0.7999999999999999,3.667552486319459},{0.8999999999999999,3.7428665266345895},{0.9999999999999999,3.825240421573291},{1.0999999999999999,3.917908991646768},{1.1999999999999997,4.016592288069206},{1.2999999999999998,4.1083808129685915},{1.4,4.181848099274452},{1.5,4.212356943026488},{1.6,4.1747554260129},{1.7000000000000002,4.100284989904457},{1.8000000000000003,4.0130674217871345},{1.9000000000000004,3.92075103430016},{2.0000000000000004,3.82966027877963},{2.1000000000000005,3.7384952851217257},{2.2000000000000006,3.6406334990959293},{2.3000000000000007,3.528877328269431},{2.400000000000001,3.3950472460646477},{2.500000000000001,3.2308099380258395},{2.600000000000001,3.028594711422372},{2.700000000000001,2.785194178292143},{2.800000000000001,2.5061593283270205},{2.9000000000000012,2.204932289465367},{3.0000000000000013,1.9168239848370667},{3.1000000000000014,1.7004052167249073},{3.2000000000000015,1.5706663201598607},{3.3000000000000016,1.5213542428521918},{3.4000000000000017,1.540139692981569},{3.5000000000000018,1.5983390094413958},{3.600000000000002,1.6713258970083664},{3.700000000000002,1.743632161252973},{3.800000000000002,1.8022756028180125},{3.900000000000002,1.83783357154813},{4.000000000000002,1.841142507843521},{4.100000000000001,1.797237372937141},{4.200000000000001,1.679996816224384},{4.300000000000001,1.4422755936498486},{4.4,0.9987385077177254},{4.5,0.0}};
	
	//Computation
	public static final double driveEncoderToInches = 1 / 6.0 * Math.PI / 4096.0 ;
	public static final double pathingTestEncoderCoversion= 4096.0 / Math.PI * 6.0; //Inches to encoder multiplication encoder to inches divisionS
	public static final double speedModeRPSToTalonOutput = 4096.0 / 10.0;
	public static final double driveEncoderVelocityToRPS = 1.0 / 4096.0 * 10;
	public static final double driveMaxSpeedRPS = 8.0; //approximately
	public static final double testEndcoderTicksToInches = 211.761452;
	public static final double shootEncoderVelocityToRPS = (4.0 * Math.PI) / 4096;
	
	//TalonSRX
	public static final int kTimeoutMs = 10;
	public static final int kPIDLoopIdx = 0;
	public static final double feetPerSecToNativeUnitsPer100Ms = 260.767149451;
}
