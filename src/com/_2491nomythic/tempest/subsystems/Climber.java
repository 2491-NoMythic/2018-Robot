package com._2491nomythic.tempest.subsystems;

import com._2491nomythic.tempest.commands.climber.Climb;
import com._2491nomythic.tempest.settings.Constants;
import com._2491nomythic.tempest.settings.Variables;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *The system of motors, solenoids and gyro that is used to help the robot climb.
 */
public class Climber extends Subsystem {
	private static Climber instance;
	private TalonSRX leftClimber, rightClimber;
	//private Solenoid lineup, grappleLauncher;
	
	private Drivetrain gyroAccess;
	
	public static Climber getInstance() {
		if (instance == null) {
			instance = new Climber();
		}
		return instance;
	}
	
	/**
	 * The system of motors, solenoids and gyro that is used to help the robot climb.
	 */
	private Climber() {
		leftClimber = new TalonSRX(Constants.leftClimber);
		rightClimber = new TalonSRX(Constants.rightClimber);
		
		//lineup = new Solenoid(Constants.grappleLauncherSolenoid);
		//grappleLauncher = new Solenoid(Constants.lineupDeploySolenoid);
	}
	
	/**
	 * Has the robot's climbing motors run at the same designated speed, raising both sides at a constant rate.
	 * @param speed The speed at which the robot's motors run.
	 */
	public void ascend(double speed) {
		ascend(speed, speed);
	}
	
	/**
	 * Has the robot's climbing motors run independently at designated speeds, raising the sides at different rates.
	 * @param leftSpeed The speed of the left climb motor.
	 * @param rightSpeed The speed of the right climb motor.
	 */
	public void ascend(double leftSpeed, double rightSpeed) {
		setLeftClimbMotor(leftSpeed);
		setRightClimbMotor(rightSpeed);
	}
	
	/**
	 * Sets the left climb motor to a designated speed.
	 * @param speed The speed the left climb motor runs at.
	 */
	public void setLeftClimbMotor(double speed) {
	}
	
	/**
	 * Sets the right climb motor to a designated speed.
	 * @param speed The speed the right climb motor runs at.
	 */
	public void setRightClimbMotor(double speed) {
	}
	
	/**
	 * Deploys the lineup arms to stabilize the robot.
	 */
	public void deployLineup() { //Find the state of this
		//lineup.set(true);
		Variables.lineupDeployed = true;
	}
	
	/**
	 * Launches the grappling hook using the totally safe slingshot.
	 */
	public void grappleHookLaunch() {
		//grappleLauncher.set(true);
		Variables.grappleHookDeployed = true;
	}
	
	/**
	 * Tells whether or not the grappling hook has launched
	 * @return Returns if it has deployed
	 */
	public boolean isGrappleHookLaunched() { //Says whether or not it is extended
		//return grappleLauncher.get();
		return Variables.grappleHookDeployed;
	}
	
	/**
	 * Tells whether or not the lineup pins have been deployed
	 * @return Returns if it has deployed
	 */
	public boolean isLineupDeployed() {
		//return lineup.get();
		return Variables.lineupDeployed;
	}
	/**
	 * Stops the climb
	 */
	public void stop() {
		ascend(0,0);
		ascend(0);
	}
	
	public void initDefaultCommand() {
		setDefaultCommand(new Climb(1, 1));
		//setDefaultCommand(new ClimbManual());
	}
	
	
}

