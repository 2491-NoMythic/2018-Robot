package com._2491nomythic.tempest.subsystems;

import com._2491nomythic.tempest.settings.Constants;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Pathing extends Subsystem {
	private static Pathing instance;
	
	public static Pathing getInstance() {
		//Elias this doesn't make sense, why does the pathing constructor take in a path? Shouldn't you be giving it a path in a command?
		if(instance == null) {
			instance = new Pathing();
		}
		return instance;
	}
	
	public Pathing() {
		
	}
	
	/**
	 * @param step the desired time step within the array
	 * @param velocityArray a array of velocities by step
	 * @return Velocity in Native Units per 100Ms for current step based on designated velocityArray
	 */
	
	public double returnVelocity(int step,double[][] velocityArray) {
		return velocityArray[step][1] * Constants.kVeloctiyUnitConversion;
	}
	
	/**
	 * @param step Desired time step within the array
	 * @param velocityArray a array of velocities by step
	 * @return Velocity in Feet per Second for current step based on designated velocityArray
	 */
	
	public double returnVelocityRaw(int step,double[][] velocityArray) {
		return velocityArray[step][1];
	}
	
	/**
	 * @param step the desired time step within the array
	 * @param angleArray a array of global coordinate angles
	 * @return Angle for current time step based on designated angleArray
	 */
	public double returnAngle(int step, double[][] angleArray) {
		return angleArray[step][1];
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
}