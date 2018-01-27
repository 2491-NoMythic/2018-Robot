package com._2491nomythic.robot.subsystems;

import com._2491nomythic.robot.settings.Constants;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The subsystem that takes Power Cubes from the field.
 */
public class Shooter extends Subsystem {
	private static Shooter instance;
	private TalonSRX leftHold, rightHold, leftAccelerate, rightAccelerate, leftShoot, rightShoot;
	
	public static Shooter getInstance() {
		if (instance == null) {
			instance = new Shooter();
		}
		return instance;
	}
	
	private Shooter() {
		leftHold = new TalonSRX(Constants.shooterTalonLeftHoldChannel);
		rightHold = new TalonSRX(Constants.shooterTalonRightHoldChannel);
		leftAccelerate = new TalonSRX(Constants.shooterTalonLeftAccelerateChannel);
		rightAccelerate = new TalonSRX(Constants.shooterTalonRightAccelerateChannel);
		leftShoot = new TalonSRX(Constants.shooterTalonLeftShootChannel);
		rightShoot = new TalonSRX(Constants.shooterTalonRightShootChannel);
	}
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

