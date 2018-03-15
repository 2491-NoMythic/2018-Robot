package com._2491nomythic.tempest.subsystems;

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
	 * 
	 * @param step step in array based on time.
	 * @param velocityArray constant velocity array to get
	 * @return velocity for current step by designated array
	 */
	
	public double returnVelocity(int step,double[][] velocityArray) {
		return velocityArray[step][1];
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
}