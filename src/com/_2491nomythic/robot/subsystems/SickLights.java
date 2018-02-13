package com._2491nomythic.robot.subsystems;

import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The system which controls the data sent to the lights.
 */
public class SickLights extends Subsystem {
	private static SickLights instance;
	private PWM underglow, shooter;

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
		underglow = new PWM(0);
		shooter = new PWM(1);
	}
	
	/**
	 * Sets the value of a PWM out to a raw PWM value ranging from 1 to 255.
	 * @param data The PWM raw int ranging from 1 to 255
	 * @param feather The feather to be addressed.
	 */
	public void writeSignal(int data, int feather) {
		if (feather == 1) {
			underglow.setRaw(data);
		}
		else if(feather == 2) {
			shooter.setRaw(data);
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
		underglow.setRaw(0);
	}
	
	/**
	 * Resets shooter light system
	 */
	public void resetShooter() {
		shooter.setRaw(0);
	}
	
    public void initDefaultCommand() {
    }
}

