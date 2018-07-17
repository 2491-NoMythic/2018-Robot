/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com._2491nomythic.tempest;

import com._2491nomythic.tempest.commands.CommandBase;
import com._2491nomythic.tempest.commands.ImprovedAutoIntake;
import com._2491nomythic.tempest.commands.ResetSolenoids;
import com._2491nomythic.tempest.commands.SetCameraMode;
import com._2491nomythic.tempest.commands.UpdateDriverstation;
import com._2491nomythic.tempest.commands.VelocityPrint;
import com._2491nomythic.tempest.commands.autonomous.AutomaticAuto;
import com._2491nomythic.tempest.commands.autonomous.AutomaticAuto.Crossing;
import com._2491nomythic.tempest.commands.autonomous.AutomaticAuto.EndPosition;
import com._2491nomythic.tempest.commands.autonomous.AutomaticAuto.Priority;
import com._2491nomythic.tempest.commands.autonomous.AutomaticAuto.StartPosition;
import com._2491nomythic.tempest.commands.autonomous.AutomaticTwoCube;
import com._2491nomythic.tempest.commands.autonomous.AutomaticTwoCube.SecondCube;
import com._2491nomythic.tempest.commands.drivetrain.DrivePath;
/*import com._2491nomythic.tempest.commands.autonomous.PlaceOnSwitchLeft;
import com._2491nomythic.tempest.commands.autonomous.PlaceOnSwitchRight;
import com._2491nomythic.tempest.commands.autonomous.RightPrioritizeScale;
import com._2491nomythic.tempest.commands.autonomous.RightPrioritizeSwitch;*/
import com._2491nomythic.tempest.commands.drivetrain.DriveStraightToPositionPID;
import com._2491nomythic.tempest.commands.drivetrain.DriveTime;
import com._2491nomythic.tempest.commands.drivetrain.RotateDrivetrainWithGyroPID;
import com._2491nomythic.tempest.commands.lights.SendAllianceColor;
import com._2491nomythic.tempest.commands.lights.SerialConnectivityTest;
import com._2491nomythic.tempest.commands.lights.UpdateLightsPattern;
import com._2491nomythic.tempest.settings.Constants;
import com._2491nomythic.tempest.settings.Variables;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {

	Command m_autonomousCommand;
	ResetSolenoids resetSolenoids;
	UpdateLightsPattern updateLights;
	SerialConnectivityTest staticPurple;
	SetCameraMode setCamera;
	SendAllianceColor sendColor;
	UpdateDriverstation updateDriverstation;
	
	SendableChooser<StartPosition> m_PositionSelector = new SendableChooser<>();
	SendableChooser<Priority> m_PrioritySelector = new SendableChooser<>();
	SendableChooser<Crossing> m_CrossingSelector = new SendableChooser<>();
	SendableChooser<SecondCube> m_SecondCubeSelector = new SendableChooser<>();

	public static boolean isTeleop;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() { 
		CommandBase.init();
		setCamera = new SetCameraMode();
		staticPurple = new SerialConnectivityTest();
		sendColor = new SendAllianceColor();
		updateDriverstation = new UpdateDriverstation();
		updateLights = new UpdateLightsPattern();
		resetSolenoids = new ResetSolenoids();
		
		updateDriverstation.start();
		//updateLights.start();
		
		m_PositionSelector.addObject("LEFT", StartPosition.LEFT);
		m_PositionSelector.addObject("CENTER", StartPosition.CENTER);
		m_PositionSelector.addObject("RIGHT", StartPosition.RIGHT);
		m_PositionSelector.addDefault("CROSS LINE", StartPosition.CROSS_LINE);
		
		m_PrioritySelector.addDefault("SWITCH", Priority.SWITCH);
		m_PrioritySelector.addObject("SCALE", Priority.SCALE);
		
		m_CrossingSelector.addDefault("OFF", Crossing.OFF);
		m_CrossingSelector.addObject("ON", Crossing.ON);
		m_CrossingSelector.addObject("FORCE", Crossing.FORCE);
		
		m_SecondCubeSelector.addDefault("SWITCH", SecondCube.SWITCH);
		m_SecondCubeSelector.addObject("SCALE", SecondCube.SCALE);
		
		/*
		m_chooser.addObject("SwitchLeft", new DriveForwardSwitch(true));
		m_chooser.addObject("SwitchRight", new DriveForwardSwitch(false));
		m_chooser.addObject("PlaceOnScaleLeft", new PlaceOnScaleLeft());
		m_chooser.addObject("PlaceOnScaleRight", new PlaceOnScaleRight());
		m_chooser.addObject("LeftPrioritizeScale", new LeftPrioritizeScale());
		m_chooser.addObject("RightPrioritizeScale", new RightPrioritizeScale());
		m_chooser.addObject("LeftPrioritizeSwitch", new LeftPrioritizeSwitch());
		m_chooser.addObject("RightPrioritizeSwitch", new RightPrioritizeSwitch());
		*/
		
		SmartDashboard.putData("Position", m_PositionSelector);
		SmartDashboard.putData("Priority", m_PrioritySelector);
		SmartDashboard.putData("Crossing", m_CrossingSelector);
		SmartDashboard.putData("SecondLocation", m_SecondCubeSelector);
		
		SmartDashboard.putData("DriveStraightToPositionPID", new DriveStraightToPositionPID(5));
		SmartDashboard.putData("RotateDrivetrainRelative180", new RotateDrivetrainWithGyroPID(180, false));
		SmartDashboard.putData("RotateDrivetrainRelative-180", new RotateDrivetrainWithGyroPID(-180, false));
		SmartDashboard.putData("RotateDrivetrainRelative90", new RotateDrivetrainWithGyroPID(90, false));
		SmartDashboard.putData("RotateDrivetrainRelative-90", new RotateDrivetrainWithGyroPID(-90, false));
		SmartDashboard.putData("HitSwitch", new DriveTime(-0.3, 0.4, 0.8));

		SmartDashboard.putData("ImprovedAutoIntake", new ImprovedAutoIntake(0, true));
		SmartDashboard.putData("Velocity Path", new VelocityPrint());

		SmartDashboard.putNumber("ProportionalRotate", Variables.proportionalRotate);
		SmartDashboard.putNumber("DerivativeRotate", Variables.derivativeRotate);
		SmartDashboard.putNumber("ProportionalForward", Variables.proportionalForward);
		SmartDashboard.putNumber("DerivativeForward", Variables.derivativeForward);
		SmartDashboard.putNumber("DriveDefault", Variables.driveDefault);
		SmartDashboard.putNumber("AutoDelay", Variables.autoDelay);
		SmartDashboard.putNumber("SwitchRPS", Constants.shooterSwitchRPS);
		SmartDashboard.putNumber("LowScaleRPS", Constants.shooterLowScaleRPS);
		SmartDashboard.putNumber("MedScaleRPS", Constants.shooterMediumScaleRPS);
		SmartDashboard.putNumber("HighScaleRPS", Constants.shooterHighScaleRPS);
		SmartDashboard.putNumber("GyroPitchMeasure", 0);
		SmartDashboard.putData("Backup", new DrivePath(StartPosition.LEFT_SWITCH, EndPosition.BACKUP, 0, false));
		System.out.println("Boot Successful");
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
		isTeleop = false;
		updateLights.cancel();
		staticPurple.start();
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
		
		setCamera.start();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		Variables.autoDelay = SmartDashboard.getNumber("AutoDelay", 0);
		sendColor.start();
		
		m_autonomousCommand = new AutomaticAuto(m_PositionSelector.getSelected(), m_PrioritySelector.getSelected(), m_CrossingSelector.getSelected());
		//m_autonomousCommand = new VelocityTestAuto();
		//m_autonomousCommand = new AutomaticTwoCube(m_PositionSelector.getSelected(), m_PrioritySelector.getSelected(), m_CrossingSelector.getSelected(), m_SecondCubeSelector.getSelected());
		//updateLights.start();
		//sendColor.start();

		// schedule the autonomous command (example)
		if (m_autonomousCommand != null) {
			m_autonomousCommand.start();
		}
		
		isTeleop = false;
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
		}
		
		sendColor.start();
		isTeleop = true;
		setCamera.start();
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
