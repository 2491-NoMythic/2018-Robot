package com._2491nomythic.tempest.subsystems;

import com._2491nomythic.tempest.settings.Constants;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The system which controls the data sent to the lights.
 */
public class SickLights extends Subsystem {
	private static SickLights instance;
	private Solenoid activator;

	public static SickLights getInstance() {
		if(instance == null) {
			instance = new SickLights();
		}
		return instance;
	}
	
	/**
	 * The system which controls the data sent to the lights.
	 */
	public SickLights() {
		activator = new Solenoid(Constants.sickLightsSolenoidChannel);
	}
	
	/**
	 * Sets the state of the lights.
	 * @param state True for on, false for off.
	 */
	public void set(boolean state) {
		//System.out.println("Setting lights to: " + state);
		activator.set(state);
	}
	
	public void initDefaultCommand() {
		
	}
}

