package com._2491nomythic.robot.subsystems;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SerialPort.WriteBufferMode;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The system which controls the data sent to the lights.
 */
public class SickLights extends Subsystem {
	private SerialPort serialPort;
	private static SickLights instance;
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

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
		serialPort = new SerialPort(115200, SerialPort.Port.kOnboard);
		serialPort.setWriteBufferMode(WriteBufferMode.kFlushOnAccess);
		serialPort.setWriteBufferSize(1);
	}
	
	/**
	 * Writes a string to the SerialPort, then sends it.
	 * @param data The string to send.
	 */
	public void writeSignal(String data) {
		int sentData = serialPort.writeString(data);
		System.out.println("Sent data! " + sentData);
	}
	
	/**
	 * Clears the buffer of the SerialPort.
	 */
	public void clearData() {
		serialPort.reset();
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

