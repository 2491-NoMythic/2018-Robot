package com._2491nomythic.tempest.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * A Subsystem that controls access to the pathing arrays
 * <p>
 * @author Emilio Lobo
 * @author Elias 
 * @version 1.0
 * @since 26-3-2018
 */
public class Pathing extends Subsystem {
	private static Pathing instance;
	
	public static Pathing getInstance() {
		if(instance == null) {
			instance = new Pathing();
		}
		return instance;
	}
	
	public Pathing() {
		
		
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
}