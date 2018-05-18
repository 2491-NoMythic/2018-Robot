/*----------------------------------------------------------------------------*/

/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com._2491nomythic.tempest;

import com._2491nomythic.tempest.commands.KillSwitch;
import com._2491nomythic.tempest.commands.cubestorage.UltrasonicCubeHaltManual;
import com._2491nomythic.tempest.commands.drivetrain.AdjustmentMode;
import com._2491nomythic.tempest.commands.drivetrain.TankTurnBackward;
import com._2491nomythic.tempest.commands.drivetrain.TankTurnForward;
import com._2491nomythic.tempest.commands.intake.RunIntakeManual;
import com._2491nomythic.tempest.commands.intake.ToggleIntakeDeployment;
import com._2491nomythic.tempest.commands.intake.ToggleIntakeOpeningHeld;
import com._2491nomythic.tempest.commands.lights.ToggleLights;
import com._2491nomythic.tempest.commands.shooter.ReverseShooter;
import com._2491nomythic.tempest.commands.shooter.RunShooterCustom;
import com._2491nomythic.tempest.commands.shooter.SetShooterSpeed;
import com._2491nomythic.tempest.commands.shooter.ToggleShooterPosition;
import com._2491nomythic.tempest.settings.Constants;
import com._2491nomythic.tempest.settings.ControllerMap;
import com._2491nomythic.util.JoystickAxisButton;
import com._2491nomythic.util.JoystickPOVButton;
import com._2491nomythic.util.PS4Controller;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	private final Joystick[] controllers = new Joystick[2]; //[4];
	Button killSwitch1, killSwitch2, driverScaleShoot, driverSwitchShoot, driverFeedCube, driverAutoShoot, deployIntake, reverseShooter;
	Button openIntakeFingers, raiseShooter, setLowScaleSpeed, setMediumScaleSpeed, setHighScaleSpeed, setSwitchSpeed;
	public Button cubeStorageControl1, cubeStorageControl2, runShooter;
	Button automaticIntake, intakeControl1, intakeControl2, tankTurnForward, tankTurnBackward;
	Button operatorKillSwitch, output, input, configure, spinUp, fingers, adjustmentDrive, adjustmentDrive2, toggleLights1, toggleLights2;

	public enum ControllerType {
		Standard,
		PS4;
	}
	
	public void init() {
		controllers[0] = new Joystick(ControllerMap.driveController);
		controllers[1] = new Joystick(ControllerMap.operatorController);
		//controllers[1] = new PS4Controller(ControllerMap.operatorController);
		//controllers[2] = new Joystick(ControllerMap.buttonBoard);
		
		//Main Drive Controller
		killSwitch1 = new JoystickButton(controllers[ControllerMap.driveController], ControllerMap.killSwitchButton1);
		killSwitch1.whenPressed(new KillSwitch());
		
		killSwitch2 = new JoystickButton(controllers[ControllerMap.driveController], ControllerMap.killSwitchButton2);
		killSwitch2.whenPressed(new KillSwitch());
		
		tankTurnForward = new JoystickButton(controllers[ControllerMap.driveController], ControllerMap.tankTurnForwardButton);
		tankTurnForward.whileHeld(new TankTurnForward());
		
		tankTurnBackward = new JoystickButton(controllers[ControllerMap.driveController], ControllerMap.tankTurnBackwardButton);
		tankTurnBackward.whileHeld(new TankTurnBackward());
		
		adjustmentDrive = new JoystickButton(controllers[ControllerMap.driveController], ControllerMap.adjustmentButton1);
		adjustmentDrive.whileHeld(new AdjustmentMode());
		
		adjustmentDrive2 = new JoystickButton(controllers[ControllerMap.driveController], ControllerMap.adjustmentButton2);
		adjustmentDrive2.whileHeld(new AdjustmentMode());
		
		toggleLights1 = new JoystickButton(controllers[ControllerMap.driveController], ControllerMap.toggleLightsButton1);
		toggleLights1.whenPressed(new ToggleLights());
		
		toggleLights2 = new JoystickButton(controllers[ControllerMap.driveController], ControllerMap.togglelightsButton2);
		toggleLights2.whenPressed(new ToggleLights());
		
		//Button Board
		/*
		operatorKillSwitch = new JoystickButton(controllers[ControllerMap.buttonBoard], ControllerMap.killSwitchButton);
		operatorKillSwitch.whenPressed(new KillSwitch());
		
		configure = new JoystickButton(controllers[ControllerMap.buttonBoard], ControllerMap.configureButton);
		configure.toggleWhenPressed(new Configure());	
		
		fingers = new JoystickButton(controllers[ControllerMap.buttonBoard], ControllerMap.fingerButton);
		fingers.whileHeld(new OpenFingers());
		
		spinUp = new JoystickButton(controllers[ControllerMap.buttonBoard], ControllerMap.spinUpButton);
		spinUp.whenPressed(new SpinUp());
		
		input = new JoystickButton(controllers[ControllerMap.buttonBoard], ControllerMap.inputButton);
		input.whileHeld(new Input());
		
		output = new JoystickButton(controllers[ControllerMap.buttonBoard], ControllerMap.bigRedButton);
		output.whileHeld(new Output());
		*/
		//Operator Controller
		
		deployIntake = new JoystickButton(controllers[ControllerMap.operatorController], ControllerMap.deployIntake);
		deployIntake.whenPressed(new ToggleIntakeDeployment());
		
		openIntakeFingers = new JoystickAxisButton(controllers[ControllerMap.operatorController], ControllerMap.openIntakeFingers, 0.1);
		openIntakeFingers.whileHeld(new ToggleIntakeOpeningHeld());
		
		intakeControl1 = new JoystickAxisButton(controllers[ControllerMap.operatorController], ControllerMap.intakeAxis, 0.1);
		intakeControl1.whileHeld(new RunIntakeManual());
		
		intakeControl2 = new JoystickAxisButton(controllers[ControllerMap.operatorController], ControllerMap.intakeAxis, -0.1);
		intakeControl2.whileHeld(new RunIntakeManual());
		
		//Shooter
		raiseShooter = new JoystickButton(controllers[ControllerMap.operatorController], ControllerMap.raiseShooter);
		raiseShooter.whenPressed(new ToggleShooterPosition());
		
		setLowScaleSpeed = new JoystickButton(controllers[ControllerMap.operatorController], ControllerMap.setLowScaleRPS);
		setLowScaleSpeed.whenPressed(new SetShooterSpeed(Constants.shooterLowScaleSpeed));
		
		setMediumScaleSpeed = new JoystickButton(controllers[ControllerMap.operatorController], ControllerMap.setMediumScaleRPS);
		setMediumScaleSpeed.whenPressed(new SetShooterSpeed(Constants.shooterMediumScaleSpeed));
		
		setHighScaleSpeed = new JoystickButton(controllers[ControllerMap.operatorController], ControllerMap.setHighScaleRPS);
		setHighScaleSpeed.whenPressed(new SetShooterSpeed(Constants.shooterHighScaleSpeed));
		
		setSwitchSpeed = new JoystickButton(controllers[ControllerMap.operatorController], ControllerMap.setSwitchRPS);
		setSwitchSpeed.whenPressed(new SetShooterSpeed(Constants.shooterSwitchSpeed));

		runShooter = new JoystickAxisButton(controllers[ControllerMap.operatorController], ControllerMap.shooterButton, 0.1);
		runShooter.whileHeld(new RunShooterCustom());
		
		reverseShooter = new JoystickPOVButton(controllers[ControllerMap.operatorController], ControllerMap.shooterReverseButton);
		reverseShooter.whileHeld(new ReverseShooter());
		
		//CubeStorage
		cubeStorageControl1 = new JoystickAxisButton(controllers[ControllerMap.operatorController], ControllerMap.cubeStorageAxis, 0.1);
		cubeStorageControl1.whileHeld(new UltrasonicCubeHaltManual());
		
		cubeStorageControl2 = new JoystickAxisButton(controllers[ControllerMap.operatorController], ControllerMap.cubeStorageAxis, -0.1);
		cubeStorageControl2.whileHeld(new UltrasonicCubeHaltManual());
	}
	
	/**
	 * Get a button from a controller
	 * 
	 * @param joystickID
	 *			The id of the controller. 0 = left or driver, 1 = right or codriver.
	 * @param axisID
	 *			The id of the button (for use in getRawButton)
	 * @return the result from running getRawButton(button)
	 */
	public boolean getButton(int joystickID, int buttonID) {
		return controllers[joystickID].getRawButton(buttonID);
	}
	
	/**
	 * Get an axis from a controller
	 * 
	 * @param joystickID
	 *			The id of the controller. 0 = left or driver, 1 = right or codriver.
	 * @param axisID
	 *			The id of the axis (for use in getRawAxis)
	 * @return the result from running getRawAxis(axis)
	 */
	public double getAxis(int joystickID, int axisID) {
		return controllers[joystickID].getRawAxis(axisID);
	}
	
	/**
	 * Get an axis from a controller that is automatically deadzoned
	 * 
	 * @param joystickID
	 *			The id of the controller. 0 = left or driver, 1 = right or driver
	 * @param axisID
	 *			The id of the axis (for use in getRawAxis)
	 * @return the deadzoned result from running getRawAxis
	 */
	public double getAxisDeadzoned(int joystickID, int axisID, double deadzone) {
		double result = -controllers[joystickID].getRawAxis(axisID);
		return Math.abs(result) > deadzone ? result : 0;
	}
	
	/**
	 * Get an axis from a controller that is automatically squared and deadzoned
	 * 
	 * @param joystickID
	 *			The id of the controller. 0 = left or driver, 1 = right or driver
	 * @param axisID
	 *			The id of the axis (for use in getRawAxis)
	 * @return the squared, deadzoned result from running getRawAxis
	 */
	public double getAxisDeadzonedSquared(int joystickID, int axisID, double deadzone) {
		double result = controllers[joystickID].getRawAxis(axisID);
		result = result * Math.abs(result);
		return Math.abs(result) > deadzone ? result : 0;
	}
	
	/**
	 * @param joystickID The ID of the controller whose name is returned
	 * @return The name of the controller with the given ID
	 */
	public String getControllerName(int joystickID) {
		return controllers[joystickID].getName();
	}
	
	/**
	 * Changes the controller layout to support compatibility with PS4 controllers and any future options.
	 * @param joystickID The ID of the controller as set in OI
	 * @param controllerID The ID of the controller as set in ControllerMap
	 * @param type The type of controller to change to
	 */
	public void changeControllerType(int joystickID, int controllerID, ControllerType type) {
		switch(type) {
		case PS4:
			controllers[joystickID] = new PS4Controller(controllerID);
			break;
		case Standard:
			controllers[joystickID] = new Joystick(controllerID);
			break;
		default:
			System.out.println("Oops! It looks like you tried to map a controller to a type that doesn't exist.");
			break;
		}
	}
	
	//// CREATING BUTTONS
	// One type of button is a joystick button which is any button on a
	//// joystick.
	// You create one by telling it which joystick it's on and which button
	// number it is.
	// Joystick stick = new Joystick(port);
	// Button button = new JoystickButton(stick, buttonNumber);

	// There are a few additional built in buttons you can use. Additionally,
	// by subclassing Button you can create custom triggers and bind those to
	// commands the same as any other Button.

	//// TRIGGERING COMMANDS WITH BUTTONS
	// Once you have a button, it's trivial to bind it to a button in one of
	// three ways:

	// Start the command when the button is pressed and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenPressed(new ExampleCommand());

	// Run the command while the button is being held down and interrupt it once
	// the button is released.
	// button.whileHeld(new ExampleCommand());

	// Start the command when the button is released and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenReleased(new ExampleCommand());
}
