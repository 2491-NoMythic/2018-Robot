package com._2491nomythic.robot.subsystems;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SerialPort.WriteBufferMode;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
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
	
	public SickLights() {
		serialPort = new SerialPort(115200, SerialPort.Port.kOnboard);
		serialPort.setWriteBufferMode(WriteBufferMode.kFlushOnAccess);
		serialPort.setWriteBufferSize(1);
	}
	
	public void writeSignal(String data) {
		int sentData = serialPort.writeString(data);
		System.out.println("Sent data! " + sentData);
	}
	
	public void clearData() {
		serialPort.reset();
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

