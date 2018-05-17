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
	public static final double shooterMediumScaleSpeed = .625;
	public static final double shooterLowScaleSpeed = .5;
	public static final double shooterSwitchSpeed = .4;
	public static final double shooterHighScaleRPS = 93; // accelerate highscale rps = 28.5
	public static final double shooterMediumScaleRPS = 75; // accelerate medscale rps = 24.75
	public static final double shooterLowScaleRPS = 58; // accelerate lowscale rps = 19
	public static final double shooterSwitchRPS = 48; //accelerate switch rps = 11.3
	public static final double shooterMaxSpeedRPS = 125; // Approximate function of power input to RPS output: f(x) = 52.83x^2 + 44.54 RPS input to power output = f(x) = sqrt((x - 44.54) / 52.83)
	
	public static double shootFeedForwardHigh = .01;
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
	
	/* Auto Paths */	
	
	public static enum Priority {
		SCALE, SWITCH
	}
	
	public static enum Crossing {
		OFF, ON, FORCE
	}
	
	public static enum StartPosition {
		LEFT 		(1, 0, 2, -1, -1), 
		CENTER 		(0, 1, 2, 1, 1), 
		RIGHT 		(0, 1, 2, 1, -1), 
		CROSS_LINE 	(0, 1, 2, 1, -1);
		
		private int mLeftIndex, mRightIndex, mHeadingIndex, mHeadingDir, mDriveDir;
		
		StartPosition(int leftIndex, int rightIndex, int headingIndex, int headingDir, int driveDir) {
			this.mLeftIndex = leftIndex;
			this.mRightIndex = rightIndex;
			this.mHeadingIndex = headingIndex;
			this.mHeadingDir = headingDir;
			this.mDriveDir = driveDir;
		}
		
		public int leftIndex() { return mLeftIndex; }
		public int rightIndex() { return mRightIndex; }
		public int headingIndex() { return mHeadingIndex; }
		public int headingDir() { return mHeadingDir; }
		public int driveDir() { return mDriveDir; }
		
	}
	
	public static enum EndPosition {
		SWITCH			(Constants.SWITCH), 
		LEFT_SWITCH		(Constants.LEFT_SWITCH), 
		RIGHT_SWITCH	(Constants.RIGHT_SWITCH), 
		OPPOSITE_SWTICH (Constants.RIGHT_SWITCH), 
		SCALE			(Constants.RIGHT_SWITCH), 
		OPPOSITE_SCALE	(Constants.RIGHT_SWITCH), 
		CROSS_LINE		(Constants.RIGHT_SWITCH), 
		BUMP_COUNTER	(Constants.RIGHT_SWITCH);
	
		private double[][] mPath;
		
		EndPosition(double[][] path) {
			this.mPath = path;
		}
		
		public double[][] extractPath() { return mPath; }
		
	}
	
	/* CENTER Start Position */
	public static final double[][] SWITCH = {{0,0,0}};
	public static final double[][] LEFT_SWITCH = {{0.0,0.0,0.0},{0.9886358160087585,1.0312963770674153,0.5431527282928591},{1.4606305780401971,1.5518607677875502,0.7218900889720332},{1.7153101548976917,1.865044326820318,1.0244419900054806},{1.8439844261881615,2.0672519144387023,1.4905640998631902},{1.8977103184112658,2.2150197393121718,2.188356637962446},{1.9057458173005721,2.3419984328955286,3.20741459468388},{1.8861627579225184,2.468530907854164,4.641573794013271},{1.8515240824653494,2.6075973974994153,6.637591406551436},{1.8153412428155644,2.7652662597241076,9.335896211937886},{1.7947443990008392,2.9416426960584894,12.769570154645807},{1.807802345971868,3.1350226471136295,17.005413628933354},{1.8789721415901544,3.3368698512063886,21.91839349280541},{2.0317490712789383,3.5357406699394156,27.001654099808555},{2.2695092366190392,3.730639589091234,32.00280084987505},{2.5901463965072806,3.9146706664458075,36.517259206083594},{2.9734905167412893,4.07852938433951,39.80081963325884},{3.3582400281748757,4.230615139359029,42.11866600130329},{3.7071022323013465,4.36830712417455,43.77229839920902},{4.004346033020325,4.482721580703263,44.880372471869876},{4.240699186047694,4.567102978607483,45.596820416834696},{4.415951406256973,4.616145675035894,46.02974242308449},{4.534475850521141,4.6251410328026905,46.22400713595546},{4.600611441346057,4.589686880284673,46.2050310235387},{4.618450227954056,4.504823672600122,45.97164117307035},{4.591505684123279,4.3647789818898675,45.49897266738349},{4.523237159196656,4.163152895570576,44.71007840147212},{4.416162782635814,3.8948032821766896,43.48368608278521},{4.27324202897116,3.559828398748019,41.7079704701548},{4.101919276416747,3.1655489344613774,39.09707996922358},{3.9031107591095435,2.735666046126812,35.32182440146465},{3.669199750126452,2.320796424316312,30.860128155229944},{3.420860570002544,1.9609365994132764,25.979791680199114},{3.1705934945653054,1.6799240547483845,20.888185247710265},{2.9182986327889187,1.4918513058684024,16.17314219943777},{2.675651620289131,1.3861635954330052,12.116359402343841},{2.4522426713531575,1.3417837623841626,8.776926721367737},{2.251079071457176,1.338135314071298,6.220617890967125},{2.0750988521809854,1.3530120280927154,4.339193697392008},{1.921069470825321,1.3683381773065422,2.9799753822872157},{1.7786901388018626,1.3695290254479056,2.0387209221690514},{1.6319233646917328,1.339781647534528,1.4052594905067155},{1.454398877835239,1.2559223567135436,0.9920934451549217},{1.2027624479309118,1.080187212998571,0.7472758407995855},{0.8048374069621811,0.7461120771065821,0.6341278923388518},{0.0,0.0,0.6341278923388518}};
	public static final double[][] RIGHT_SWITCH = {{0.0,0.0,0.0},{1.050554750787697,1.0147199007687022,-0.44619997386428434},{1.5666978271393501,1.4907978420781087,-0.5930369268496526},{1.8745299972893588,1.75030791714276,-0.841599054235194},{2.0699645382339367,1.8848922279334082,-1.224572997367518},{2.2089931812735983,1.9458983796794826,-1.7979924000519998},{2.324789683130715,1.962651183933887,-2.635736394826276},{2.4373659867485644,1.9529012098786234,-3.8156701856541733},{2.559473771712687,1.928434009935534,-5.460526534539149},{2.697545408068318,1.9009029243037643,-7.691137451794073},{2.8527044435406435,1.8843455971850223,-10.545943108829498},{3.024169217789703,1.8932939582103223,-14.101876614012975},{3.2047907813504386,1.947867668132844,-18.288679215127413},{3.3837322244110575,2.068432141260302,-22.710137562957986},{3.5578815179752765,2.2596370870537,-27.16826346805875},{3.7185038130496477,2.5226913474837582,-31.299399874587277},{3.8554115410960117,2.8436274648035647,-34.37349789651544},{3.9776096914860117,3.1700626939499306,-36.578501383390744},{4.0852721545948265,3.469237589271979,-38.16606526965679},{4.172385954937904,3.7271744725734086,-39.2300392746841},{4.234474716505126,3.93543215381034,-39.90729907378767},{4.26759784979892,4.0938020661031125,-40.29507132613636},{4.267849454441956,4.2064453212308175,-40.43052294511812},{4.231489261276937,4.277727024277698,-40.32973224240662},{4.15439847808128,4.311920993332748,-39.98111978734593},{4.032037169606086,4.312747084489883,-39.35100567359385},{3.8596226914319693,4.283732142935,-38.347361599962966},{3.634552902406822,4.226616190561114,-36.837828466835525},{3.360096753818845,4.141804334909625,-34.72690554596046},{3.0459809165109535,4.032298724491086,-31.76062319299287},{2.717860344756654,3.8925329779354385,-27.732832282582994},{2.422629496577536,3.708918469253431,-23.331420233154777},{2.185858843703433,3.5008903471236374,-18.90803985179538},{2.016731109042937,3.2846713654354973,-14.66515717160069},{1.9167142979568723,3.0669729773968806,-11.020363418409191},{1.8700154001442733,2.8621837293291725,-8.067399188646242},{1.857673208524706,2.6789979983665275,-5.743979177587204},{1.864281967447418,2.5182301035162666,-4.021931576490315},{1.8745380652797137,2.378711408916871,-2.78218896872773},{1.8755032211128284,2.2536781066322296,-1.8997898470625276},{1.854523374621288,2.130048171168052,-1.2947771752685149},{1.7937339278738158,1.9880129205804977,-0.8902446532171598},{1.664682503986373,1.7953640483987894,-0.6274961055168996},{1.418365855779409,1.4983961883589134,-0.4722073418402826},{0.9678707193891891,1.005815767945985,-0.40053632348288265},{0.0,0.0,-0.40053632348288265}};
	/* RIGHT Start Position*/
	public static final double[][] headingsTO_SCALE = {{0.0,0.0},{0.1,0.14834618225127025},{0.2,0.20853779876432402},{0.30000000000000004,0.31199941867813363},{0.4,0.47586206963519856},{0.5,0.7235201392168513},{0.6,1.0854724840987084},{0.7,1.5929888648076718},{0.7999999999999999,2.2762349126792163},{0.8999999999999999,3.1400974718699377},{0.9999999999999999,4.1694172145068205},{1.0999999999999999,5.277279469970419},{1.2,6.377539420439971},{1.3,7.279498237283849},{1.4000000000000001,7.995367690660622},{1.5000000000000002,8.554287169234218},{1.6000000000000003,9.020321883990848},{1.7000000000000004,9.453116141351167},{1.8000000000000005,9.918531995198878},{1.9000000000000006,10.482652895879097},{2.0000000000000004,11.221456824733279},{2.1000000000000005,12.205767045256815},{2.2000000000000006,13.511687272446453},{2.3000000000000007,15.136869735181225},{2.400000000000001,17.03967048522164},{2.500000000000001,18.849766301800127},{2.600000000000001,20.438184041392258},{2.700000000000001,21.704721797421076},{2.800000000000001,22.665601748490772},{2.9000000000000012,23.35697249794514},{3.0000000000000013,23.838814930149105},{3.1000000000000014,24.163259816039968},{3.2000000000000015,24.37563649460199},{3.3000000000000016,24.508804269340942},{3.4000000000000017,24.585953276446496},{3.5000000000000018,24.62115102585434},{3.600000000000002,24.62115102585434}};
	public static final double[][] headingsTO_SWITCH = {{0.0,0.0},{0.1,0.03504874834480336},{0.2,0.040165889720078625},{0.30000000000000004,0.04872097942065016},{0.4,0.06164762122389161},{0.5,0.07978411862099236},{0.6,0.10298830012759742},{0.7,0.13365520082365032},{0.7999999999999999,0.17574057566746956},{0.8999999999999999,0.2339874114638186},{0.9999999999999999,0.31237618716549914},{1.0999999999999999,0.4101639979951},{1.2,0.5372987769352657},{1.3,0.7102795221659175},{1.4000000000000001,0.9489724301337112},{1.5000000000000002,1.2700235132702606},{1.6000000000000003,1.668987634852068},{1.7000000000000004,2.18661181349907},{1.8000000000000005,2.8931503883198535},{1.9000000000000006,3.8765339568223056},{2.0000000000000004,5.211584988447346},{2.1000000000000005,6.856368650571318},{2.2000000000000006,8.969499966056555},{2.3000000000000007,11.852919266660377},{2.400000000000001,15.882206602875618},{2.500000000000001,21.204428585501898},{2.600000000000001,26.598815173156165},{2.700000000000001,31.505057303083696},{2.800000000000001,35.830448345558395},{2.9000000000000012,39.71645843591208},{3.0000000000000013,43.254475833465555},{3.1000000000000014,46.1027711409063},{3.2000000000000015,48.285386501362964},{3.3000000000000016,49.962020950178015},{3.4000000000000017,51.304053992205},{3.5000000000000018,52.42087342980619},{3.600000000000002,53.279209563135154},{3.700000000000002,53.92629291909485},{3.800000000000002,54.4296028883882},{3.900000000000002,54.85063358549279},{4.000000000000002,55.22924969439183},{4.100000000000001,55.56063393308815},{4.200000000000001,55.87019411160566},{4.300000000000001,56.19631591372874},{4.4,56.58186685391972},{4.5,57.06262980934464},{4.6,57.63057383844942},{4.699999999999999,58.34701092819632},{4.799999999999999,59.33378091763246},{4.899999999999999,60.77313105413895},{4.999999999999998,62.841219149831616},{5.099999999999998,65.20722955584462},{5.1999999999999975,67.67950408135773},{5.299999999999997,70.19746775334411},{5.399999999999997,72.81405491661673},{5.4999999999999964,75.57189572338329},{5.599999999999996,78.11896497897789},{5.699999999999996,80.3094181389997},{5.799999999999995,82.15342053839525},{5.899999999999995,83.73858662750584},{5.999999999999995,85.13269259174264},{6.099999999999994,86.24533784651587},{6.199999999999994,87.09572370849021},{6.299999999999994,87.74579753067174},{6.399999999999993,88.2609228362975},{6.499999999999993,88.68367440964754},{6.5999999999999925,89.00360972657651},{6.699999999999992,89.23791912265493},{6.799999999999992,89.41034264030995},{6.8999999999999915,89.54176214191008},{6.999999999999991,89.64505755771832},{7.099999999999991,89.7189916637386},{7.19999999999999,89.76806564341426},{7.29999999999999,89.79747333741851},{7.39999999999999,89.81115345585071},{7.499999999999989,89.81115345585071}};
	public static final double[][] headingsTO_OPPOSITE_SCALE = {{0.0,0.0},{0.1,0.12236718312570932},{0.2,0.16515671416950234},{0.30000000000000004,0.23877478784396453},{0.4,0.35508999879429426},{0.5,0.5347552660614378},{0.6,0.8081357255404279},{0.7,1.2156489631136256},{0.7999999999999999,1.8253412552599182},{0.8999999999999999,2.733348308101917},{0.9999999999999999,4.059253694430978},{1.0999999999999999,6.011932151279437},{1.2,8.87651749547161},{1.3,12.953630920803663},{1.4000000000000001,18.77268004212517},{1.5000000000000002,26.826992363477963},{1.6000000000000003,36.8479279265241},{1.7000000000000004,48.37023811692702},{1.8000000000000005,59.815337429954475},{1.9000000000000006,68.38528790822143},{2.0000000000000004,74.38536962189869},{2.1000000000000005,78.60062977596485},{2.2000000000000006,81.42147642264592},{2.3000000000000007,83.31139207078944},{2.400000000000001,84.59385703155391},{2.500000000000001,85.4288392738413},{2.600000000000001,85.946454834956},{2.700000000000001,86.23146399452212},{2.800000000000001,86.32177585324712},{2.9000000000000012,86.22979710299907},{3.0000000000000013,85.94082160687117},{3.1000000000000014,85.41575202875114},{3.2000000000000015,84.54952750171907},{3.3000000000000016,83.15908330672525},{3.4000000000000017,81.00612016259011},{3.5000000000000018,77.47768686531208},{3.600000000000002,71.43891728604912},{3.700000000000002,62.5044756832617},{3.800000000000002,50.0371193273794},{3.900000000000002,34.10771724251499},{4.000000000000002,18.03842582258991},{4.100000000000001,4.854952237840974},{4.200000000000001,-4.744649250306594},{4.300000000000001,-11.167206739334064},{4.4,-15.38827138500514},{4.5,-18.19181919120787},{4.6,-20.02915858006094},{4.699999999999999,-21.235662861244144},{4.799999999999999,-22.035023215631064},{4.899999999999999,-22.555890366081766},{4.999999999999998,-22.891185472926136},{5.099999999999998,-23.10261678741115},{5.1999999999999975,-23.225228166871183},{5.299999999999997,-23.28121674583182},{5.399999999999997,-23.28121674583182}};
	//public static final double[][] headingsTO_OPPOSITE_SCALE = {{0.0,0.0},{0.1,0.1168529862057147},{0.2,0.1339216710945809},{0.30000000000000004,0.16246254631531512},{0.4,0.20559834529081997},{0.5,0.26614122082571623},{0.6,0.34363811915029235},{0.7,0.4461225835062795},{0.7999999999999999,0.5868832535485121},{0.8999999999999999,0.7819198856602719},{0.9999999999999999,1.044801124624633},{1.0999999999999999,1.3733694130690215},{1.2,1.8015647369671428},{1.3,2.3859593389763307},{1.4000000000000001,3.1955677172615458},{1.5000000000000002,4.289958808917793},{1.6000000000000003,5.657676959713911},{1.7000000000000004,7.442867602282605},{1.8000000000000005,9.89386044430671},{1.9000000000000006,13.31854377630232},{2.0000000000000004,17.956013671583015},{2.1000000000000005,23.576268520656214},{2.2000000000000006,30.50896042831213},{2.3000000000000007,39.2093383489769},{2.400000000000001,49.61486042974599},{2.500000000000001,60.3410616772065},{2.600000000000001,68.43170553393601},{2.700000000000001,74.05689908029348},{2.800000000000001,78.04206681150556},{2.9000000000000012,81.04614453744499},{3.0000000000000013,83.4147303553361},{3.1000000000000014,85.11533111568973},{3.2000000000000015,86.30962926331829},{3.3000000000000016,87.1646505527089},{3.4000000000000017,87.80641136159957},{3.5000000000000018,88.3064353560116},{3.600000000000002,88.66046614697834},{3.700000000000002,88.89327373964143},{3.800000000000002,89.03152517048339},{3.900000000000002,89.09465883973853},{4.000000000000002,89.09238757874031},{4.100000000000001,89.02448776652602},{4.200000000000001,88.8786023997025},{4.300000000000001,88.62951247099376},{4.4,88.23894790508295},{4.5,87.66341435241583},{4.6,86.89784557120703},{4.699999999999999,85.82461033653014},{4.799999999999999,84.1861945375186},{4.899999999999999,81.49372946313373},{4.999999999999998,76.95074657213193},{5.099999999999998,70.59154742728897},{5.1999999999999975,62.31898959701842},{5.299999999999997,51.926894228813005},{5.399999999999997,39.12660533624761},{5.4999999999999964,24.375576339248695},{5.599999999999996,11.083817826703203},{5.699999999999996,0.8675464092881019},{5.799999999999995,-6.57340194666298},{5.899999999999995,-12.11078105363665},{5.999999999999995,-16.37367197769675},{6.099999999999994,-19.408817989859333},{6.199999999999994,-21.530330153086254},{6.299999999999994,-23.045946672152816},{6.399999999999993,-24.186026664011724},{6.499999999999993,-25.083633366831805},{6.5999999999999925,-25.74114802674039},{6.699999999999992,-26.211194563170796},{6.799999999999992,-26.551047337237886},{6.8999999999999915,-26.80670510229694},{6.999999999999991,-27.0056328427201},{7.099999999999991,-27.146937255938226},{7.19999999999999,-27.240235513500675},{7.29999999999999,-27.295957279607798},{7.39999999999999,-27.32183069603828},{7.499999999999989,-27.32183069603828}};	
	public static final double[][] headingsTO_CROSS_LINE = {{0.0,0.0},{0.1,0.0},{0.2,0.0},{0.30000000000000004,0.0},{0.4,0.0},{0.5,0.0},{0.6,0.0},{0.7,0.0},{0.7999999999999999,0.0},{0.8999999999999999,0.0},{0.9999999999999999,0.0},{1.0999999999999999,0.0},{1.2,0.0},{1.3,0.0},{1.4000000000000001,0.0},{1.5000000000000002,0.0},{1.6000000000000003,0.0},{1.7000000000000004,0.0},{1.8000000000000005,0.0}};
	public static final double[][] headingsTO_BUMP_COUNTER = {{0.0,0.0},{0.1,0.0},{0.2,0.0},{0.30000000000000004,0.0},{0.4,0.0},{0.5,0.0},{0.6,0.0},{0.7,0.0},{0.7999999999999999,0.0},{0.8999999999999999,0.0},{0.9999999999999999,0.0}};
	
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
	public static final int kVelocitySlotId = 0;
	/**
	 * Converts from Ft/Sec to NativeUnits/100Ms 
	 */
	public static final double kVeloctiyUnitConversion = 260.767149451;
	public static final double kVelocitykF = 0.2960069444;
	public static final double kVelocitykP = 1.5;
	public static final double kVelocitykI = 0.0005;
	public static final double kVelocitykD = 27.5;
	public static final double kVelocitykG = 0.05;

	
	//Climber
	public static final int leftClimber = 2491; //TODO change this to an actual value
	public static final int rightClimber = 2491; //TODO change this to an actual value
	public static final int grappleLauncherSolenoid = 2491; //TODO change this to an actual value
	public static final int lineupDeploySolenoid = 2491; //TODO change this to an actual value

}
