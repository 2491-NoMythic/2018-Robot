package com._2491nomythic.tempest.subsystems;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SerialPort.WriteBufferMode;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The system which sends data to the RIOduino and controls the lights on the robot.
 */
public class SickLights extends Subsystem {
	private static SickLights instance;
	private SerialPort port;
	
	public static SickLights getInstance() {
		if(instance == null) {
			instance = new SickLights();
		}
		return instance;
	}
	
	/**
	 * The system which sends data to the RIOduino and controls the lights on the robot.
	 */
	public SickLights() {
		port = new SerialPort(9600, SerialPort.Port.kMXP);
		port.setWriteBufferMode(WriteBufferMode.kFlushOnAccess);
	}
	
	/**
	 * Writes a string to the serial port to be read by the RIOduino.
	 * @param data The string to be sent.
	 */
	public void writeData(String data) {
		System.out.println("Writing string... String: " + data);
		port.writeString(data);
	}
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

