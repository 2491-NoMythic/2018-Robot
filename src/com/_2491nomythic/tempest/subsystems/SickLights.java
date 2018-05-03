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
	 * Writes a byte (int from -128 to 127) to the serial port to be read by the RIOduino.
	 * Sending 1 or 2 tells the RIOduino what the robot's alliance color is, the rest change the pattern displayed on the shooter.
	 * @param data The byte to be sent.
	 * 0: Purple sparkle, default
	 * 1: Red Alliance
	 * 2: Blue Alliance
	 * 3: Shooter is running in reverse
	 * 4: Shooter is running at switch speed
	 * 5: Shooter is running at low scale speed
	 * 6: Shooter is running at medium scale speed
	 * 7: Shooter is running at high scale speed
	 * 8: Ultrasonic detected a cube
	 * 9: The shooter is spun up and ready to fire
	 */
	
	/**
	 * Actually just writes RED or BLUE at the moment.
	 * @param data RED or BLUE, your alliance color for the match.
	 */
	public void writeData(String data) {
		port.writeString(data);
	}
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

