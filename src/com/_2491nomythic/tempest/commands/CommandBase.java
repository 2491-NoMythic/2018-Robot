package com._2491nomythic.tempest.commands;

import com._2491nomythic.tempest.OI;
import com._2491nomythic.tempest.subsystems.*;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The base for all commands. All atomic commands should subclass CommandBase. CommandBase creates and stores each control system. To access a subsystem elsewhere in your code in your code use CommandBase.exampleSubsystem
 */
public abstract class CommandBase extends Command {
	protected static OI oi;
	protected static Drivetrain drivetrain;
	protected static Intake intake;
	protected static Shooter shooter;
	protected static CubeStorage cubeStorage;
	protected static SickLights sickLights;
	protected static Pathing pathing;
	protected static Climber climber;
	
	public static void init() {
		oi = new OI();
		
		drivetrain = Drivetrain.getInstance();
		intake = Intake.getInstance();
		shooter = Shooter.getInstance();
		cubeStorage = CubeStorage.getInstance();
		sickLights = SickLights.getInstance();
		pathing = Pathing.getInstance();
		climber = Climber.getInstance();
		
		oi.init();
		// This MUST be here. If the OI creates Commands (which it very likely
		// will), constructing it during the construction of CommandBase (from
		// which commands extend), subsystems are not guaranteed to be
		// yet. Thus, their requires() statements may grab null pointers. Bad
		// news. Don't move it.
		
		// Show what command your subsystem is running on the SmartDashboard
		SmartDashboard.putData(drivetrain);
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
