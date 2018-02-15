package com._2491nomythic.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The system which controls the data sent to the lights.
 */
public class SickLights extends Subsystem {
	private static SickLights instance;
	private DigitalOutput underglow, shooter;

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
		underglow = new DigitalOutput(3);
		shooter = new DigitalOutput(9);
	}
	
	/**
	 * Sets the value of a PWM out to a raw PWM value ranging from 1 to 255.
	 * @param data The PWM raw int ranging from 1 to 255
	 * @param feather The feather to be addressed.
	 */
	public void writeSignal(int data, int feather) {
		if (feather == 1) {
			underglow.set(true);
		}
		else if(feather == 2) {
			shooter.set(true);;
		}
	}
	
	/**
	 * Resets all PWM outputs
	 */
	public void resetPWM() {
		resetUnderglow();
		resetShooter();
	}
	
	/**
	 * Resets underglow light system
	 */
	public void resetUnderglow(){
	}
	
	/**
	 * Resets shooter light system
	 */
	public void resetShooter() {
	}
	
    public void initDefaultCommand() {
    	
    }
}

