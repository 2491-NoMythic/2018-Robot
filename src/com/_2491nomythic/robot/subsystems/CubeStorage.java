package com._2491nomythic.robot.subsystems;

import com._2491nomythic.robot.commands.cubestorage.TransportCubeManual;
import com._2491nomythic.robot.settings.Constants;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Stores the cubes and moves them in and out of the storage
 */
public class CubeStorage extends Subsystem {
	private static CubeStorage instance;
	private TalonSRX left, right;
	
	public static CubeStorage getInstance() {
		if (instance == null) {
			instance = new CubeStorage();
		}
		return instance;
	}
	
	/**
	 * Stores the cubes and moves them in and out of the storage
	 */
	private CubeStorage() {
		left = new TalonSRX(Constants.cubeStorageTalonLeftChannel);
		right = new TalonSRX(Constants.cubeStorageTalonRightChannel);
	}

	/**
	 * Runs the storage motors at a specified power
	 * @param power The power at which the motors are run. Positive values push the cube into the shooter, negative values push it into the intake.
	 */
	public void run(double power) {
		left.set(ControlMode.PercentOutput, power);
		right.set(ControlMode.PercentOutput, power);
	}
	
	/**
	 * Stops all movement of the cube storage motors.
	 */
	public void stop() {
		run(0);
	}
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new TransportCubeManual());
    }
}

