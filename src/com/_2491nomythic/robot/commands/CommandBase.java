package com._2491nomythic.robot.commands;

import com._2491nomythic.robot.OI;
import com._2491nomythic.robot.subsystems.*;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The base for all commands. All atomic commands should subclass CommandBase. CommandBase stores creates and stores each control system. To access a subsystem elsewhere in your code in your code use CommandBase.exampleSubsystem
 */
public abstract class CommandBase extends Command {
	protected static OI oi;
	protected static Drivetrain drivetrain;
	protected static Intake intake;
	protected static Shooter shooter;
	protected static CubeStorage cubeStorage;
	protected static Lights lights;
	
	public static void init() {
		oi = new OI();
		
		drivetrain = Drivetrain.getInstance();
		intake = Intake.getInstance();
		shooter = Shooter.getInstance();
		cubeStorage = CubeStorage.getInstance();
		lights = Lights.getInstance();
		
		oi.init();
		// This MUST be here. If the OI creates Commands (which it very likely
		// will), constructing it during the construction of CommandBase (from
		// which commands extend), subsystems are not guaranteed to be
		// yet. Thus, their requires() statements may grab null pointers. Bad
		// news. Don't move it.
		
		// Show what command your subsystem is running on the SmartDashboard
		SmartDashboard.putData(drivetrain);
		SmartDashboard.putData(lights);
	}
	
	/**
	 * The base for all commands. All atomic commands should subclass CommandBase. CommandBase stores creates and stores each control system. To access a subsystem elsewhere in your code in your code use CommandBase.exampleSubsystem
	 * 
	 * @param name
	 *			The name that shows up on the SmartDashboard in association with any command created using this parameter.
	 */
	public CommandBase(String name) {
		super(name);
	}
	
	/**
	 * The base for all commands. All atomic commands should subclass CommandBase. CommandBase stores creates and stores each control system. To access a subsystem elsewhere in your code in your code use CommandBase.exampleSubsystem
	 */
	public CommandBase() {
		super();
	}
}
