package com._2491nomythic.tempest.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The system which controls the data sent to the lights.
 */
public class SickLights extends Subsystem {
	private static SickLights instance;
	private Solenoid shooter1, shooter2;

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
		//shooter1 = new Solenoid(4);
		//shooter2 = new Solenoid(5);
	}
	
	/**
	 * Updates the solenoids to be recognized by the Arduino.
	 * @param pattern Which light pattern to use from 1-4.
	 * Pattern 1: Static Purple
	 * Pattern 2: Shooter Spinning Up
	 * Pattern 3: Ready to Fire
	 * Pattern 4: Picked up Cube (Detected by ultrasonic)
	 */
	public void writePattern(int pattern) {
		switch(pattern) {
		case 1:
			shooter1.set(false);
			shooter2.set(false);
			break;
		case 2:
			shooter1.set(true);
			shooter2.set(false);
			break;
		case 3:
			shooter1.set(false);
			shooter2.set(true);
			break;
		case 4:
			shooter1.set(true);
			shooter2.set(true);
			break;
		default:
			System.out.println("Invalid pattern in SickLights. Pattern: " + pattern);
			break;
		}
	}
	
	public void initDefaultCommand() {
		
	}
}

