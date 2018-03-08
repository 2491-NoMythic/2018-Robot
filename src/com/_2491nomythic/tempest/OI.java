/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com._2491nomythic.tempest;

import com._2491nomythic.tempest.commands.KillSwitch;
import com._2491nomythic.tempest.commands.buttonboard.Configure;
import com._2491nomythic.tempest.commands.buttonboard.Input;
import com._2491nomythic.tempest.commands.buttonboard.OpenFingers;
import com._2491nomythic.tempest.commands.buttonboard.Output;
import com._2491nomythic.tempest.commands.buttonboard.SpinUp;
import com._2491nomythic.tempest.settings.ControllerMap;
import com._2491nomythic.util.PS4Controller;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	private final Joystick[] controllers = new Joystick[2];
	
	Button killSwitch1, killSwitch2, driverScaleShoot, driverSwitchShoot, driverFeedCube, driverAutoShoot, deployIntake, reverseShooter;
	Button openIntake, raiseShooter, setLowScaleSpeed, setMediumScaleSpeed, setHighScaleSpeed, setSwitchSpeed, runShooter, runIntakeRollerless;
	Button cubeStorageControl1, cubeStorageControl2, automaticIntake, intakeControl1, intakeControl2;
	
	Button operatorKillSwitch, output, input, configure, spinUp, fingers;

	public enum ControllerType {
		Standard,
		PS4;
	}
	
	public void init() {
		controllers[0] = new Joystick(ControllerMap.driveController);
		controllers[1] = new PS4Controller(ControllerMap.operatorController);
		
		killSwitch1 = new JoystickButton(controllers[ControllerMap.driveController], ControllerMap.killSwitchButton1);
		killSwitch1.whenPressed(new KillSwitch());
		
		killSwitch2 = new JoystickButton(controllers[ControllerMap.driveController], ControllerMap.killSwitchButton2);
		killSwitch2.whenPressed(new KillSwitch());
		
		operatorKillSwitch = new JoystickButton(controllers[ControllerMap.operatorController], ControllerMap.killSwitchButton);
		operatorKillSwitch.whenPressed(new KillSwitch());
		
		configure = new JoystickButton(controllers[ControllerMap.operatorController], ControllerMap.configureButton);
		configure.whenPressed(new Configure());	
		
		fingers = new JoystickButton(controllers[ControllerMap.operatorController], ControllerMap.fingerButton);
		fingers.whileHeld(new OpenFingers());
		
		spinUp = new JoystickButton(controllers[ControllerMap.operatorController], ControllerMap.spinUpButton);
		spinUp.toggleWhenPressed(new SpinUp());
		
		input = new JoystickButton(controllers[ControllerMap.operatorController], ControllerMap.inputButton);
		input.toggleWhenPressed(new Input());
		
		output = new JoystickButton(controllers[ControllerMap.operatorController], ControllerMap.bigRedButton);
		output.toggleWhenPressed(new Output());
		
		//driverScaleShoot = new JoystickButton(controllers[ControllerMap.driveController], ControllerMap.driverScaleShootButton);
		//driverScaleShoot.whileHeld(new ScaleShoot());
		
		//driverSwitchShoot = new JoystickButton(controllers[ControllerMap.driveController], ControllerMap.driverSwitchShootButton);
		//driverSwitchShoot.whileHeld(new SwitchShoot());
		
		//driverAutoShoot = new JoystickButton(controllers[ControllerMap.driveController], ControllerMap.driverAutoShootButton);
		//driverAutoShoot.whenPressed(new AutomaticShoot(true));
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
