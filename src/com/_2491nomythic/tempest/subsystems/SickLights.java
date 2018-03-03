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
	
	public void set(boolean state) {
		activator.set(state);
	}
	
	public void initDefaultCommand() {
		
	}
}

