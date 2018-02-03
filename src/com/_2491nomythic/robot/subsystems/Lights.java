package com._2491nomythic.robot.subsystems;

import com._2491nomythic.robot.settings.Constants;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The system which allows us to interface the lights and the Arduino from the rio.
 */
public class Lights extends Subsystem {
	I2C i2c;
	byte[] toSend;
	private static Lights instance;
	
	public static Lights getInstance() {
		if(instance == null) {
			instance = new Lights();
		}
		return instance;
	}

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	public Lights() {
		i2c = new I2C(I2C.Port.kOnboard, Constants.lightIC2Channel);
		toSend = new byte[1];
	}
	
	public void setByte(int value) {
		toSend[0] = (byte) value;
		sendData();
	}
	
	public byte getByte() {
		return toSend[0];
	}
	
	public void sendData() {
		i2c.transaction(toSend, toSend.length, null, 0);
	}
	
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		//setDefaultCommand(new MySpecialCommand());
	}
}

