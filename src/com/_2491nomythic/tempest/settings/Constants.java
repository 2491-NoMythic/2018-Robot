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
	public static double shooterMediumScaleRPS = 75; // accelerate medscale rps = 24.75
	public static double shooterLowScaleRPS = 58; // accelerate lowscale rps = 19
	public static double shooterSwitchRPS = 48; //accelerate switch rps = 11.3
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
	public static final double kVelocitykG = 0.05;
	public static final String[][][] mOptionsMatrix = {{{"Switch", "Switch", "Scale", "CrossLine"}, {"Scale", "Switch", "Scale", "CrossLine"}},{{"Switch","Switch","Scale","Op Scale"},{"Scale","Switch","Scale","Op Scale"}},{}};

	
	/* CENTER Start Position */
	//LEFTSWITCH
	public static final double[][] leftVelocitiesTO_LEFT_SWITCH = {{0.0,0.0},{0.1,0.9886358160087585},{0.2,1.4606305780401971},{0.30000000000000004,1.7153101548976917},{0.4,1.8439844261881615},{0.5,1.8977103184112658},{0.6,1.9057458173005721},{0.7,1.8861627579225184},{0.7999999999999999,1.8515240824653494},{0.8999999999999999,1.8153412428155644},{0.9999999999999999,1.7947443990008392},{1.0999999999999999,1.807802345971868},{1.1999999999999997,1.8789721415901544},{1.2999999999999998,2.0317490712789383},{1.4,2.2695092366190392},{1.5,2.5901463965072806},{1.6,2.9734905167412893},{1.7000000000000002,3.3582400281748757},{1.8000000000000003,3.7071022323013465},{1.9000000000000004,4.004346033020325},{2.0000000000000004,4.240699186047694},{2.1000000000000005,4.415951406256973},{2.2000000000000006,4.534475850521141},{2.3000000000000007,4.600611441346057},{2.400000000000001,4.618450227954056},{2.500000000000001,4.591505684123279},{2.600000000000001,4.523237159196656},{2.700000000000001,4.416162782635814},{2.800000000000001,4.27324202897116},{2.9000000000000012,4.101919276416747},{3.0000000000000013,3.9031107591095435},{3.1000000000000014,3.669199750126452},{3.2000000000000015,3.420860570002544},{3.3000000000000016,3.1705934945653054},{3.4000000000000017,2.9182986327889187},{3.5000000000000018,2.675651620289131},{3.600000000000002,2.4522426713531575},{3.700000000000002,2.251079071457176},{3.800000000000002,2.0750988521809854},{3.900000000000002,1.921069470825321},{4.000000000000002,1.7786901388018626},{4.100000000000001,1.6319233646917328},{4.200000000000001,1.454398877835239},{4.300000000000001,1.2027624479309118},{4.4,0.8048374069621811},{4.5,0.0}};
	public static final double[][] rightVelocitiesTO_LEFT_SWITCH = {{0.0,0.0},{0.1,1.0312963770674153},{0.2,1.5518607677875502},{0.30000000000000004,1.865044326820318},{0.4,2.0672519144387023},{0.5,2.2150197393121718},{0.6,2.3419984328955286},{0.7,2.468530907854164},{0.7999999999999999,2.6075973974994153},{0.8999999999999999,2.7652662597241076},{0.9999999999999999,2.9416426960584894},{1.0999999999999999,3.1350226471136295},{1.1999999999999997,3.3368698512063886},{1.2999999999999998,3.5357406699394156},{1.4,3.730639589091234},{1.5,3.9146706664458075},{1.6,4.07852938433951},{1.7000000000000002,4.230615139359029},{1.8000000000000003,4.36830712417455},{1.9000000000000004,4.482721580703263},{2.0000000000000004,4.567102978607483},{2.1000000000000005,4.616145675035894},{2.2000000000000006,4.6251410328026905},{2.3000000000000007,4.589686880284673},{2.400000000000001,4.504823672600122},{2.500000000000001,4.3647789818898675},{2.600000000000001,4.163152895570576},{2.700000000000001,3.8948032821766896},{2.800000000000001,3.559828398748019},{2.9000000000000012,3.1655489344613774},{3.0000000000000013,2.735666046126812},{3.1000000000000014,2.320796424316312},{3.2000000000000015,1.9609365994132764},{3.3000000000000016,1.6799240547483845},{3.4000000000000017,1.4918513058684024},{3.5000000000000018,1.3861635954330052},{3.600000000000002,1.3417837623841626},{3.700000000000002,1.338135314071298},{3.800000000000002,1.3530120280927154},{3.900000000000002,1.3683381773065422},{4.000000000000002,1.3695290254479056},{4.100000000000001,1.339781647534528},{4.200000000000001,1.2559223567135436},{4.300000000000001,1.080187212998571},{4.4,0.7461120771065821},{4.5,0.0}};
	//RIGHTSWITCH
	public static final double[][] leftVelocitiesTO_RIGHT_SWITCH = {{0.0,0.0},{0.1,1.050554750787697},{0.2,1.5666978271393501},{0.30000000000000004,1.8745299972893588},{0.4,2.0699645382339367},{0.5,2.2089931812735983},{0.6,2.324789683130715},{0.7,2.4373659867485644},{0.7999999999999999,2.559473771712687},{0.8999999999999999,2.697545408068318},{0.9999999999999999,2.8527044435406435},{1.0999999999999999,3.024169217789703},{1.1999999999999997,3.2047907813504386},{1.2999999999999998,3.3837322244110575},{1.4,3.5578815179752765},{1.5,3.7185038130496477},{1.6,3.8554115410960117},{1.7000000000000002,3.9776096914860117},{1.8000000000000003,4.0852721545948265},{1.9000000000000004,4.172385954937904},{2.0000000000000004,4.234474716505126},{2.1000000000000005,4.26759784979892},{2.2000000000000006,4.267849454441956},{2.3000000000000007,4.231489261276937},{2.400000000000001,4.15439847808128},{2.500000000000001,4.032037169606086},{2.600000000000001,3.8596226914319693},{2.700000000000001,3.634552902406822},{2.800000000000001,3.360096753818845},{2.9000000000000012,3.0459809165109535},{3.0000000000000013,2.717860344756654},{3.1000000000000014,2.422629496577536},{3.2000000000000015,2.185858843703433},{3.3000000000000016,2.016731109042937},{3.4000000000000017,1.9167142979568723},{3.5000000000000018,1.8700154001442733},{3.600000000000002,1.857673208524706},{3.700000000000002,1.864281967447418},{3.800000000000002,1.8745380652797137},{3.900000000000002,1.8755032211128284},{4.000000000000002,1.854523374621288},{4.100000000000001,1.7937339278738158},{4.200000000000001,1.664682503986373},{4.300000000000001,1.418365855779409},{4.4,0.9678707193891891},{4.5,0.0}};
	public static final double[][] rightVelocitiesTO_RIGHT_SWITCH = {{0.0,0.0},{0.1,1.0147199007687022},{0.2,1.4907978420781087},{0.30000000000000004,1.75030791714276},{0.4,1.8848922279334082},{0.5,1.9458983796794826},{0.6,1.962651183933887},{0.7,1.9529012098786234},{0.7999999999999999,1.928434009935534},{0.8999999999999999,1.9009029243037643},{0.9999999999999999,1.8843455971850223},{1.0999999999999999,1.8932939582103223},{1.1999999999999997,1.947867668132844},{1.2999999999999998,2.068432141260302},{1.4,2.2596370870537},{1.5,2.5226913474837582},{1.6,2.8436274648035647},{1.7000000000000002,3.1700626939499306},{1.8000000000000003,3.469237589271979},{1.9000000000000004,3.7271744725734086},{2.0000000000000004,3.93543215381034},{2.1000000000000005,4.0938020661031125},{2.2000000000000006,4.2064453212308175},{2.3000000000000007,4.277727024277698},{2.400000000000001,4.311920993332748},{2.500000000000001,4.312747084489883},{2.600000000000001,4.283732142935},{2.700000000000001,4.226616190561114},{2.800000000000001,4.141804334909625},{2.9000000000000012,4.032298724491086},{3.0000000000000013,3.8925329779354385},{3.1000000000000014,3.708918469253431},{3.2000000000000015,3.5008903471236374},{3.3000000000000016,3.2846713654354973},{3.4000000000000017,3.0669729773968806},{3.5000000000000018,2.8621837293291725},{3.600000000000002,2.6789979983665275},{3.700000000000002,2.5182301035162666},{3.800000000000002,2.378711408916871},{3.900000000000002,2.2536781066322296},{4.000000000000002,2.130048171168052},{4.100000000000001,1.9880129205804977},{4.200000000000001,1.7953640483987894},{4.300000000000001,1.4983961883589134},{4.4,1.005815767945985},{4.5,0.0}};
	//Auto Paths Angles
	public static final double[][] headingsTO_LEFT_SWITCH = {{0.0,0.0},{0.1,0.5431527282928591},{0.2,0.7218900889720332},{0.30000000000000004,1.0244419900054806},{0.4,1.4905640998631902},{0.5,2.188356637962446},{0.6,3.20741459468388},{0.7,4.641573794013271},{0.7999999999999999,6.637591406551436},{0.8999999999999999,9.335896211937886},{0.9999999999999999,12.769570154645807},{1.0999999999999999,17.005413628933354},{1.2,21.91839349280541},{1.3,27.001654099808555},{1.4000000000000001,32.00280084987505},{1.5000000000000002,36.517259206083594},{1.6000000000000003,39.80081963325884},{1.7000000000000004,42.11866600130329},{1.8000000000000005,43.77229839920902},{1.9000000000000006,44.880372471869876},{2.0000000000000004,45.596820416834696},{2.1000000000000005,46.02974242308449},{2.2000000000000006,46.22400713595546},{2.3000000000000007,46.2050310235387},{2.400000000000001,45.97164117307035},{2.500000000000001,45.49897266738349},{2.600000000000001,44.71007840147212},{2.700000000000001,43.48368608278521},{2.800000000000001,41.7079704701548},{2.9000000000000012,39.09707996922358},{3.0000000000000013,35.32182440146465},{3.1000000000000014,30.860128155229944},{3.2000000000000015,25.979791680199114},{3.3000000000000016,20.888185247710265},{3.4000000000000017,16.17314219943777},{3.5000000000000018,12.116359402343841},{3.600000000000002,8.776926721367737},{3.700000000000002,6.220617890967125},{3.800000000000002,4.339193697392008},{3.900000000000002,2.9799753822872157},{4.000000000000002,2.0387209221690514},{4.100000000000001,1.4052594905067155},{4.200000000000001,0.9920934451549217},{4.300000000000001,0.7472758407995855},{4.4,0.6341278923388518},{4.5,0.6341278923388518}};
	public static final double[][] headingsTO_RIGHT_SWITCH = {{0.0,0.0},{0.1,-0.44619997386428434},{0.2,-0.5930369268496526},{0.30000000000000004,-0.841599054235194},{0.4,-1.224572997367518},{0.5,-1.7979924000519998},{0.6,-2.635736394826276},{0.7,-3.8156701856541733},{0.7999999999999999,-5.460526534539149},{0.8999999999999999,-7.691137451794073},{0.9999999999999999,-10.545943108829498},{1.0999999999999999,-14.101876614012975},{1.2,-18.288679215127413},{1.3,-22.710137562957986},{1.4000000000000001,-27.16826346805875},{1.5000000000000002,-31.299399874587277},{1.6000000000000003,-34.37349789651544},{1.7000000000000004,-36.578501383390744},{1.8000000000000005,-38.16606526965679},{1.9000000000000006,-39.2300392746841},{2.0000000000000004,-39.90729907378767},{2.1000000000000005,-40.29507132613636},{2.2000000000000006,-40.43052294511812},{2.3000000000000007,-40.32973224240662},{2.400000000000001,-39.98111978734593},{2.500000000000001,-39.35100567359385},{2.600000000000001,-38.347361599962966},{2.700000000000001,-36.837828466835525},{2.800000000000001,-34.72690554596046},{2.9000000000000012,-31.76062319299287},{3.0000000000000013,-27.732832282582994},{3.1000000000000014,-23.331420233154777},{3.2000000000000015,-18.90803985179538},{3.3000000000000016,-14.66515717160069},{3.4000000000000017,-11.020363418409191},{3.5000000000000018,-8.067399188646242},{3.600000000000002,-5.743979177587204},{3.700000000000002,-4.021931576490315},{3.800000000000002,-2.78218896872773},{3.900000000000002,-1.8997898470625276},{4.000000000000002,-1.2947771752685149},{4.100000000000001,-0.8902446532171598},{4.200000000000001,-0.6274961055168996},{4.300000000000001,-0.4722073418402826},{4.4,-0.40053632348288265},{4.5,-0.40053632348288265}};
	
	
	/* RIGHT Start Position, invert for left start*/
	//Same side SCALE
	public static final double[][] leftVelocitiesTO_SCALE = {{0.0,0.0},{0.1,2.380619348183418},{0.2,3.425053826349089},{0.30000000000000004,4.02958164825112},{0.4,4.392190828237267},{0.5,4.627355869177014},{0.6,4.802949939635858},{0.7,4.961021525220272},{0.7999999999999999,5.12898471541039},{0.8999999999999999,5.3252531258114715},{0.9999999999999999,5.560508390923308},{1.0999999999999999,5.837309373385984},{1.1999999999999997,6.146495891910574},{1.2999999999999998,6.468250329781746},{1.4,6.770599975230174},{1.5,7.030327263538549},{1.6,7.232702575158638},{1.7000000000000002,7.371927918792069},{1.8000000000000003,7.447185906320201},{1.9000000000000004,7.460694410567273},{2.0000000000000004,7.4162852131709585},{2.1000000000000005,7.320208806538108},{2.2000000000000006,7.182407121205593},{2.3000000000000007,7.019746131581538},{2.400000000000001,6.854350004638897},{2.500000000000001,6.712983921320938},{2.600000000000001,6.603200688148855},{2.700000000000001,6.5223425335218135},{2.800000000000001,6.4576021462853},{2.9000000000000012,6.391509845953167},{3.0000000000000013,6.3003955000547975},{3.1000000000000014,6.150602747496876},{3.2000000000000015,5.888604594256499},{3.3000000000000016,5.42398585118343},{3.4000000000000017,4.599315304220103},{3.5000000000000018,3.137562833939467},{3.600000000000002,0.0}};
	public static final double[][] rightVelocitiesTO_SCALE = {{0.0,0.0},{0.1,2.3885450992003414},{0.2,3.4387361299107253},{0.30000000000000004,4.05017824397196},{0.4,4.421382703820716},{0.5,4.667211215661446},{0.6,4.855673273236892},{0.7,5.028485165115827},{0.7999999999999999,5.212163006951578},{0.8999999999999999,5.4234662818025035},{0.9999999999999999,5.671168131652105},{1.0999999999999999,5.955799744678769},{1.1999999999999997,6.267935061701905},{1.2999999999999998,6.5887613453534986},{1.4,6.892109313570342},{1.5,7.159479888751281},{1.6,7.380366295688295},{1.7000000000000002,7.552284307063716},{1.8000000000000003,7.677252418699721},{1.9000000000000004,7.759265318275672},{2.0000000000000004,7.802187347476035},{2.1000000000000005,7.808178236447567},{2.2000000000000006,7.777708527445165},{2.3000000000000007,7.708312667618304},{2.400000000000001,7.599209812579366},{2.500000000000001,7.449672935360952},{2.600000000000001,7.280138420905762},{2.700000000000001,7.105226789530915},{2.800000000000001,6.934194339535752},{2.9000000000000012,6.764441216662737},{3.0000000000000013,6.581720985682577},{3.1000000000000014,6.355806003364958},{3.2000000000000015,6.033177643570991},{3.3000000000000016,5.521217185525114},{3.4000000000000017,4.659407346933011},{3.5000000000000018,3.167300857112348},{3.600000000000002,0.0}};
	//Same side SWITCH
	public static final double[][] leftVelocitiesTO_SWITCH = {{0.0,0.0},{0.1,1.5462334942163691},{0.2,2.3795621642390214},{0.30000000000000004,2.850131446463565},{0.4,3.115163004706087},{0.5,3.263531122787577},{0.6,3.3454118257520915},{0.7,3.3889778019249843},{0.7999999999999999,3.40993172153171},{0.8999999999999999,3.41690733358771},{0.9999999999999999,3.4145182562129346},{1.0999999999999999,3.4049995425933592},{1.1999999999999997,3.3887980575660066},{1.2999999999999998,3.365309996572105},{1.4,3.333359576708732},{1.5,3.2914894627710094},{1.6,3.2378434388722916},{1.7000000000000002,3.168723360348671},{1.8000000000000003,3.0797063764948187},{1.9000000000000004,2.966863639059411},{2.0000000000000004,2.828050710854201},{2.1000000000000005,2.663011904617601},{2.2000000000000006,2.467065680701325},{2.3000000000000007,2.238556808829286},{2.400000000000001,1.9880722532434987},{2.500000000000001,1.7485054104744748},{2.600000000000001,1.57285369413428},{2.700000000000001,1.4671722111503032},{2.800000000000001,1.415030308418587},{2.9000000000000012,1.398555691233517},{3.0000000000000013,1.4098484571326657},{3.1000000000000014,1.4490534845674257},{3.2000000000000015,1.50130139654568},{3.3000000000000016,1.553266303432161},{3.4000000000000017,1.5980640968628237},{3.5000000000000018,1.6348925970300279},{3.600000000000002,1.6659904108544306},{3.700000000000002,1.6895432949571663},{3.800000000000002,1.703855327831289},{3.900000000000002,1.7083075500278697},{4.000000000000002,1.7032255147593174},{4.100000000000001,1.6891507652248474},{4.200000000000001,1.6647176180507741},{4.300000000000001,1.6278082612691938},{4.4,1.576238194493565},{4.5,1.5083135787546271},{4.6,1.4229313489268176},{4.699999999999999,1.3171751973799521},{4.799999999999999,1.187905646396992},{4.899999999999999,1.0354586275221949},{4.999999999999998,0.8693842870440723},{5.099999999999998,0.7126053413175388},{5.1999999999999975,0.5773216904695572},{5.299999999999997,0.4655526563254088},{5.399999999999997,0.3754677587667437},{5.4999999999999964,0.3088925929027673},{5.599999999999996,0.2734488479702947},{5.699999999999996,0.26353967582730886},{5.799999999999995,0.2692186979134145},{5.899999999999995,0.28252398595915534},{5.999999999999995,0.3004291361399244},{6.099999999999994,0.3236555890976447},{6.199999999999994,0.3482941891909902},{6.299999999999994,0.37080292767045775},{6.399999999999993,0.38956139504267256},{6.499999999999993,0.4048255712724287},{6.5999999999999925,0.417731822060101},{6.699999999999992,0.42773824060794297},{6.799999999999992,0.4340424056508427},{6.8999999999999915,0.43575391514983514},{6.999999999999991,0.43150068914782885},{7.099999999999991,0.418563633015011},{7.19999999999999,0.39118374807446815},{7.29999999999999,0.3389743994735415},{7.39999999999999,0.24351157984049732},{7.499999999999989,0.0}};
	public static final double[][] rightVelocitiesTO_SWITCH = {{0.0,0.0},{0.1,1.5477132937797407},{0.2,2.382321668551295},{0.30000000000000004,2.8543909168583497},{0.4,3.121242667584818},{0.5,3.2718492820198},{0.6,3.3565267562462027},{0.7,3.403790383641471},{0.7999999999999999,3.429707057820113},{0.8999999999999999,3.443236179423986},{0.9999999999999999,3.4492953986778216},{1.0999999999999999,3.450592247704871},{1.1999999999999997,3.4489216008091543},{1.2999999999999998,3.445108597419201},{1.4,3.4392014046689745},{1.5,3.430777855013809},{1.6,3.4195478932794963},{1.7000000000000002,3.406886647949198},{1.8000000000000003,3.3933845991249623},{1.9000000000000004,3.3783046214962584},{2.0000000000000004,3.3598810312889165},{2.1000000000000005,3.3370780408022522},{2.2000000000000006,3.3165993741190163},{2.3000000000000007,3.300814235593943},{2.400000000000001,3.281679298110187},{2.500000000000001,3.236140827687561},{2.600000000000001,3.1320292587749585},{2.700000000000001,2.9909206633577807},{2.800000000000001,2.841099141001311},{2.9000000000000012,2.699452960570831},{3.0000000000000013,2.5673281648743678},{3.1000000000000014,2.4374565376950024},{3.2000000000000015,2.3187216990561086},{3.3000000000000016,2.218177845797609},{3.4000000000000017,2.136943030622693},{3.5000000000000018,2.0711229006404634},{3.600000000000002,2.014938483606997},{3.700000000000002,1.9675528089242087},{3.800000000000002,1.9282019948951643},{3.900000000000002,1.8951074082206079},{4.000000000000002,1.8656523135539522},{4.100000000000001,1.8371889413297964},{4.200000000000001,1.8090509718869514},{4.300000000000001,1.7804249568866743},{4.4,1.7499605066747719},{4.5,1.7160842270976073},{4.6,1.6779643958832728},{4.699999999999999,1.6382952873594177},{4.799999999999999,1.5986895848057254},{4.899999999999999,1.5579200688232318},{4.999999999999998,1.51011504891862},{5.099999999999998,1.4445018957916844},{5.1999999999999975,1.3690381997100283},{5.299999999999997,1.2933396029381283},{5.399999999999997,1.2223003286475107},{5.4999999999999964,1.1526582620404027},{5.599999999999996,1.07466918538878},{5.699999999999996,0.9930311878976202},{5.799999999999995,0.9152841089673704},{5.899999999999995,0.845912079258086},{5.999999999999995,0.7844220170172864},{6.099999999999994,0.7272892503505656},{6.199999999999994,0.67655646488862},{6.299999999999994,0.6340675213625143},{6.399999999999993,0.5998411981529262},{6.499999999999993,0.5721729711358171},{6.5999999999999925,0.548636197177816},{6.699999999999992,0.5285042480797845},{6.799999999999992,0.5108914886742963},{6.8999999999999915,0.4941359115108628},{6.999999999999991,0.47548075799648903},{7.099999999999991,0.4506786700263145},{7.19999999999999,0.4134319776795708},{7.29999999999999,0.35295771563753686},{7.39999999999999,0.25036518105890176},{7.499999999999989,0.0}};
	//Opposite SCALE
	public static final double[][] leftVelocitiesTO_OPPOSITE_SCALE = {{0.0,0.0},{0.1,2.812063541444917},{0.2,4.30534073379208},{0.30000000000000004,5.147662342858477},{0.4,5.620882273321636},{0.5,5.884269972960295},{0.6,6.027651525132219},{0.7,6.101217105931415},{0.7999999999999999,6.132746473693236},{0.8999999999999999,6.137360100563107},{0.9999999999999999,6.123008923589482},{1.0999999999999999,6.093311887725625},{1.1999999999999997,6.048075919082904},{1.2999999999999998,5.985285502950523},{1.4,5.902416239910503},{1.5,5.797327443693469},{1.6,5.6680009485949325},{1.7000000000000002,5.508127328618275},{1.8000000000000003,5.3131211641396945},{1.9000000000000004,5.085816313910112},{2.0000000000000004,4.8419947601582605},{2.1000000000000005,4.6105830085973905},{2.2000000000000006,4.410891691436416},{2.3000000000000007,4.280787724170841},{2.400000000000001,4.288031251424422},{2.500000000000001,4.50999622201203},{2.600000000000001,4.9682405488784855},{2.700000000000001,5.5377827546689495},{2.800000000000001,6.1135440853712675},{2.9000000000000012,6.643986639751891},{3.0000000000000013,7.11529489438443},{3.1000000000000014,7.527334911742969},{3.2000000000000015,7.87025490323548},{3.3000000000000016,8.142785478477542},{3.4000000000000017,8.351595796333774},{3.5000000000000018,8.506699267340588},{3.600000000000002,8.616995150147114},{3.700000000000002,8.686121502389957},{3.800000000000002,8.716196940572935},{3.900000000000002,8.70841542458654},{4.000000000000002,8.663031603197279},{4.100000000000001,8.579471289678754},{4.200000000000001,8.456339589280558},{4.300000000000001,8.289052879590747},{4.4,8.071106002297702},{4.5,7.797250091014341},{4.6,7.468111864844516},{4.699999999999999,7.09625241837925},{4.799999999999999,6.692368552266973},{4.899999999999999,6.267045106989022},{4.999999999999998,5.832296936635152},{5.099999999999998,5.3945146928334},{5.1999999999999975,5.000620657668655},{5.299999999999997,4.6707170727222},{5.399999999999997,4.389893734887696},{5.4999999999999964,4.110719244488049},{5.599999999999996,3.779426377109839},{5.699999999999996,3.436526573704571},{5.799999999999995,3.133380504402908},{5.899999999999995,2.8920976616906824},{5.999999999999995,2.7071195431248225},{6.099999999999994,2.561272647136965},{6.199999999999994,2.450159557912178},{6.299999999999994,2.3691211780602828},{6.399999999999993,2.311526078651778},{6.499999999999993,2.26943920580406},{6.5999999999999925,2.235000383616174},{6.699999999999992,2.2039980985128085},{6.799999999999992,2.1719498517139244},{6.8999999999999915,2.1321143379850356},{6.999999999999991,2.0735178947324093},{7.099999999999991,1.9780007159865611},{7.19999999999999,1.8154927555072364},{7.29999999999999,1.5334500857445361},{7.39999999999999,1.0392400336163201},{7.499999999999989,0.0}};
	public static final double[][] rightVelocitiesTO_OPPOSITE_SCALE = {{0.0,0.0},{0.1,2.815778793662585},{0.2,4.313467144211269},{0.30000000000000004,5.160960585960131},{0.4,5.6404587528552},{0.5,5.911570674590066},{0.6,6.064606581111839},{0.7,6.150939195812869},{0.7999999999999999,6.199604437993543},{0.8999999999999999,6.226841301669141},{0.9999999999999999,6.241631221639558},{1.0999999999999999,6.249174682046862},{1.1999999999999997,6.253829215231161},{1.2999999999999998,6.258292857308921},{1.4,6.263796654872286},{1.5,6.270801626750203},{1.6,6.280806263176397},{1.7000000000000002,6.301660533901182},{1.8000000000000003,6.33988809160285},{1.9000000000000004,6.398127599057937},{2.0000000000000004,6.47446302360664},{2.1000000000000005,6.56631828516158},{2.2000000000000006,6.69284275806911},{2.3000000000000007,6.859042405346773},{2.400000000000001,7.044268687154548},{2.500000000000001,7.2105687524612785},{2.600000000000001,7.336229903797952},{2.700000000000001,7.4744814600022655},{2.800000000000001,7.645051403559208},{2.9000000000000012,7.840389063566508},{3.0000000000000013,8.042382627305518},{3.1000000000000014,8.230903539624823},{3.2000000000000015,8.395501167268955},{3.3000000000000016,8.531778461466471},{3.4000000000000017,8.638907608103306},{3.5000000000000018,8.716991471224874},{3.600000000000002,8.765325199360886},{3.700000000000002,8.783465634371113},{3.800000000000002,8.770475874493519},{3.900000000000002,8.724355612316513},{4.000000000000002,8.641809541402608},{4.100000000000001,8.518232634220942},{4.200000000000001,8.347247835502804},{4.300000000000001,8.118607885501463},{4.4,7.819435679445111},{4.5,7.437083441209783},{4.6,6.961918365893295},{4.699999999999999,6.384322770452075},{4.799999999999999,5.689315070151829},{4.899999999999999,4.868968448423627},{4.999999999999998,3.94840809709639},{5.099999999999998,3.0191873091142933},{5.1999999999999975,2.1868912095052884},{5.299999999999997,1.5271123717743154},{5.399999999999997,1.092209841909929},{5.4999999999999964,0.8907642625446572},{5.599999999999996,0.7861789229922597},{5.699999999999996,0.8045292068863839},{5.799999999999995,0.9431933856869132},{5.899999999999995,1.122028139793581},{5.999999999999995,1.303531251434027},{6.099999999999994,1.4767284142177743},{6.199999999999994,1.6273772247618628},{6.299999999999994,1.749238167338386},{6.399999999999993,1.8433013512603347},{6.499999999999993,1.914561025912507},{6.5999999999999925,1.9684488251273755},{6.699999999999992,2.005580943253114},{6.799999999999992,2.0248020485229348},{6.8999999999999915,2.02292637709751},{6.999999999999991,1.992852427867991},{7.099999999999991,1.9199970665176083},{7.19999999999999,1.7757980143887968},{7.29999999999999,1.508766332581805},{7.39999999999999,1.02731613556604},{7.499999999999989,0.0}};
	//AUTO PATHS ANGLES
	public static final double[][] headingsTO_SCALE = {{0.0,0.0},{0.1,0.05262663997924686},{0.2,0.07397848460398312},{0.30000000000000004,0.11064133304785422},{0.4,0.16860008417391478},{0.5,0.2559358242778049},{0.6,0.38299072523586175},{0.7,0.5599911867417555},{0.7999999999999999,0.7962274184834268},{0.8999999999999999,1.0919382921992398},{0.9999999999999999,1.4407334241446235},{1.0999999999999999,1.8142472031902557},{1.2,2.187572361339062},{1.3,2.508360782977961},{1.4000000000000001,2.7914562545885517},{1.5000000000000002,3.060155289212156},{1.6000000000000003,3.353405297303011},{1.7000000000000004,3.7159414111830418},{1.8000000000000005,4.205619484343622},{1.9000000000000006,4.8912787680331755},{2.0000000000000004,5.862311233044099},{2.1000000000000005,7.207222646937112},{2.2000000000000006,9.025942035398735},{2.3000000000000007,11.3090001777211},{2.400000000000001,13.989980960764568},{2.500000000000001,16.536687559124427},{2.600000000000001,18.76344402983683},{2.700000000000001,20.530853085163297},{2.800000000000001,21.865801178792797},{2.9000000000000012,22.82271578217403},{3.0000000000000013,23.487701619627174},{3.1000000000000014,23.93453101252303},{3.2000000000000015,24.2266023528308},{3.3000000000000016,24.409571518978325},{3.4000000000000017,24.51551245319966},{3.5000000000000018,24.563831403519007},{3.600000000000002,24.563831403519007}};
	public static final double[][] headingsTO_SWITCH = {{0.0,0.0},{0.1,0.03504874834480336},{0.2,0.040165889720078625},{0.30000000000000004,0.04872097942065016},{0.4,0.06164762122389161},{0.5,0.07978411862099236},{0.6,0.10298830012759742},{0.7,0.13365520082365032},{0.7999999999999999,0.17574057566746956},{0.8999999999999999,0.2339874114638186},{0.9999999999999999,0.31237618716549914},{1.0999999999999999,0.4101639979951},{1.2,0.5372987769352657},{1.3,0.7102795221659175},{1.4000000000000001,0.9489724301337112},{1.5000000000000002,1.2700235132702606},{1.6000000000000003,1.668987634852068},{1.7000000000000004,2.18661181349907},{1.8000000000000005,2.8931503883198535},{1.9000000000000006,3.8765339568223056},{2.0000000000000004,5.211584988447346},{2.1000000000000005,6.856368650571318},{2.2000000000000006,8.969499966056555},{2.3000000000000007,11.852919266660377},{2.400000000000001,15.882206602875618},{2.500000000000001,21.204428585501898},{2.600000000000001,26.598815173156165},{2.700000000000001,31.505057303083696},{2.800000000000001,35.830448345558395},{2.9000000000000012,39.71645843591208},{3.0000000000000013,43.254475833465555},{3.1000000000000014,46.1027711409063},{3.2000000000000015,48.285386501362964},{3.3000000000000016,49.962020950178015},{3.4000000000000017,51.304053992205},{3.5000000000000018,52.42087342980619},{3.600000000000002,53.279209563135154},{3.700000000000002,53.92629291909485},{3.800000000000002,54.4296028883882},{3.900000000000002,54.85063358549279},{4.000000000000002,55.22924969439183},{4.100000000000001,55.56063393308815},{4.200000000000001,55.87019411160566},{4.300000000000001,56.19631591372874},{4.4,56.58186685391972},{4.5,57.06262980934464},{4.6,57.63057383844942},{4.699999999999999,58.34701092819632},{4.799999999999999,59.33378091763246},{4.899999999999999,60.77313105413895},{4.999999999999998,62.841219149831616},{5.099999999999998,65.20722955584462},{5.1999999999999975,67.67950408135773},{5.299999999999997,70.19746775334411},{5.399999999999997,72.81405491661673},{5.4999999999999964,75.57189572338329},{5.599999999999996,78.11896497897789},{5.699999999999996,80.3094181389997},{5.799999999999995,82.15342053839525},{5.899999999999995,83.73858662750584},{5.999999999999995,85.13269259174264},{6.099999999999994,86.24533784651587},{6.199999999999994,87.09572370849021},{6.299999999999994,87.74579753067174},{6.399999999999993,88.2609228362975},{6.499999999999993,88.68367440964754},{6.5999999999999925,89.00360972657651},{6.699999999999992,89.23791912265493},{6.799999999999992,89.41034264030995},{6.8999999999999915,89.54176214191008},{6.999999999999991,89.64505755771832},{7.099999999999991,89.7189916637386},{7.19999999999999,89.76806564341426},{7.29999999999999,89.79747333741851},{7.39999999999999,89.81115345585071},{7.499999999999989,89.81115345585071}};
	public static final double[][] headingsTO_OPPOSITE_SCALE = {{0.0,0.0},{0.1,0.12067299879641794},{0.2,0.13829778953445038},{0.30000000000000004,0.16776746670990858},{0.4,0.21230447735503596},{0.5,0.27480882718187266},{0.6,0.35480763068783855},{0.7,0.4605854149021394},{0.7999999999999999,0.6058409677941243},{0.8999999999999999,0.8070510984086875},{0.9999999999999999,1.0781525883107617},{1.0999999999999999,1.416831618746708},{1.2,1.8579288053640177},{1.3,2.4594256859426107},{1.4000000000000001,3.2917509616761658},{1.5000000000000002,4.415020521101972},{1.6000000000000003,5.815821045190413},{1.7000000000000004,7.6390620599663945},{1.8000000000000005,10.132628242230899},{1.9000000000000006,13.597662047575021},{2.0000000000000004,18.253702302034753},{2.1000000000000005,23.84109197117833},{2.2000000000000006,30.653050495912233},{2.3000000000000007,39.08943768157308},{2.400000000000001,49.049168729845476},{2.500000000000001,59.22478003197984},{2.600000000000001,66.88150218871358},{2.700000000000001,72.21413539096173},{2.800000000000001,76.00289414996341},{2.9000000000000012,78.8669883575062},{3.0000000000000013,81.13078703556886},{3.1000000000000014,82.759026524763},{3.2000000000000015,83.90314702117969},{3.3000000000000016,84.72131410104456},{3.4000000000000017,85.3333447868714},{3.5000000000000018,85.80726556166502},{3.600000000000002,86.13874975207224},{3.700000000000002,86.35042892888607},{3.800000000000002,86.46618770623888},{3.900000000000002,86.50262361019405},{4.000000000000002,86.4672400342222},{4.100000000000001,86.3600358195662},{4.200000000000001,86.16441763834969},{4.300000000000001,85.84779698670273},{4.4,85.36206764771},{4.5,84.65368110943332},{4.6,83.7175474015814},{4.699999999999999,82.4126412722593},{4.799999999999999,80.43254062578902},{4.899999999999999,77.20678486982474},{4.999999999999998,71.851045674925},{5.099999999999998,64.56990780267533},{5.1999999999999975,55.52987782495256},{5.299999999999997,44.90024042031486},{5.399999999999997,32.83156077116621},{5.4999999999999964,20.000357660881388},{5.599999999999996,9.023537995013557},{5.699999999999996,0.7078516644850493},{5.799999999999995,-5.396195884788816},{5.899999999999995,-10.011211920295528},{5.999999999999995,-13.626274837283736},{6.099999999999994,-16.24103199776808},{6.199999999999994,-18.091690540127228},{6.299999999999994,-19.42638051145604},{6.399999999999993,-20.43765142813505},{6.499999999999993,-21.23841642490518},{6.5999999999999925,-21.827619619186184},{6.699999999999992,-22.25022044932092},{6.799999999999992,-22.556500655475094},{6.8999999999999915,-22.787311864965677},{6.999999999999991,-22.96715104696146},{7.099999999999991,-23.095027022492943},{7.19999999999999,-23.179518722781584},{7.29999999999999,-23.23000356206475},{7.39999999999999,-23.253451092551984},{7.499999999999989,-23.253451092551984}};
	
	public static final double[][] leftVelocitiesTO_CROSS_LINE = {{0.0,0.0},{0.1,2.8724109769933612},{0.2,3.964062458439215},{0.30000000000000004,4.581399517194431},{0.4,4.930201170054017},{0.5,5.126734632707361},{0.6,5.236511058237731},{0.7,5.296122588073736},{0.7999999999999999,5.32543973237906},{0.8999999999999999,5.334234873346347},{0.9999999999999999,5.325439725829225},{1.0999999999999999,5.29612257552604},{1.2,5.236511040779591},{1.3,5.126734611929114},{1.4000000000000001,4.930201148001371},{1.5000000000000002,4.581399496307013},{1.6000000000000003,3.964062441476352},{1.7000000000000004,2.872410966947841},{1.8000000000000005,0.0}};
	public static final double[][] rightVelocitiesTO_CROSS_LINE = {{0.0,0.0},{0.1,2.8724109769933612},{0.2,3.964062458439215},{0.30000000000000004,4.581399517194431},{0.4,4.930201170054017},{0.5,5.126734632707361},{0.6,5.236511058237731},{0.7,5.296122588073736},{0.7999999999999999,5.32543973237906},{0.8999999999999999,5.334234873346347},{0.9999999999999999,5.325439725829225},{1.0999999999999999,5.29612257552604},{1.2,5.236511040779591},{1.3,5.126734611929114},{1.4000000000000001,4.930201148001371},{1.5000000000000002,4.581399496307013},{1.6000000000000003,3.964062441476352},{1.7000000000000004,2.872410966947841},{1.8000000000000005,0.0}};
	
	public static final double[][] headingsTO_CROSS_LINE = {{0.0,0.0},{0.1,0.0},{0.2,0.0},{0.30000000000000004,0.0},{0.4,0.0},{0.5,0.0},{0.6,0.0},{0.7,0.0},{0.7999999999999999,0.0},{0.8999999999999999,0.0},{0.9999999999999999,0.0},{1.0999999999999999,0.0},{1.2,0.0},{1.3,0.0},{1.4000000000000001,0.0},{1.5000000000000002,0.0},{1.6000000000000003,0.0},{1.7000000000000004,0.0},{1.8000000000000005,0.0}};
	
	public static final double[][] leftVelocitiesTO_BUMP_COUNTER = {{0.0,0.1},{0.1,0.1},{0.2,0.1},{0.30000000000000004,0.1},{0.4,0.1},{0.5,0.1},{0.6,0.1},{0.7,0.1},{0.7999999999999999,0.1},{0.8999999999999999,0.1},{0.9999999999999999,0.1}};
	public static final double[][] rightVelocitiesTO_BUMP_COUNTER = {{0.0,0.1},{0.1,0.1},{0.2,0.1},{0.30000000000000004,0.1},{0.4,0.1},{0.5,0.1},{0.6,0.1},{0.7,0.1},{0.7999999999999999,0.1},{0.8999999999999999,0.1},{0.9999999999999999,0.1}};
	
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
	public static final double kVelocitykF = 0.2922857143;
	public static final double kVelocitykP = 1;
	public static final double kVelocitykI = 0;
	public static final double kVelocitykD = 0;
	
	//Climber
	public static final int leftClimber = 2491; //TODO change this to an actual value
	public static final int rightClimber = 2491; //TODO change this to an actual value
	public static final int grappleLauncherSolenoid = 2491; //TODO change this to an actual value
	public static final int lineupDeploySolenoid = 2491; //TODO change this to an actual value

}
