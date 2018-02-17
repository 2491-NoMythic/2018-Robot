package com._2491nomythic.robot.subsystems;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The system which controls the data sent to the lights.
 */
public class SickLights extends Subsystem {
	private static SickLights instance;
	private I2C shooter;

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
		shooter = new I2C(Port.kOnboard, 1);
	}
	
	/**
	 * Returns whether or not there is a device connected to the I2C port at the correct address.
	 * @return Whether or not it is connected.
	 */
	public boolean isConnected() {
		return !shooter.addressOnly();
	}
	
	/**
	 * Writes a byte array to the I2C output.
	 * @param data The byte array to be written.
	 */
	public void writeSignal(byte[] data) {
		shooter.transaction(data, data.length, null, 0);
	}
	
	public void initDefaultCommand() {
		
	}
}

