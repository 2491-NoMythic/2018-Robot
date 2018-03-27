package com._2491nomythic.tempest.subsystems;

import com._2491nomythic.tempest.settings.Constants;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class OLD_Pathing extends Subsystem {
	private static OLD_Pathing instance;
	private static double[][] mVelocitiesArray, mHeadingsArray;
	public static OLD_Pathing getInstance() {
		//Elias this doesn't make sense, why does the pathing constructor take in a path? Shouldn't you be giving it a path in a command?
		if(instance == null) {
			instance = new OLD_Pathing();
		}
		return instance;
	}
	
	public OLD_Pathing() {
		
	}
	
	/**
	 * @param step Desired time step within the array
	 * @param velocityArray a array of velocities by step
	 * @return Velocity in Feet per Second for current step based on designated velocityArray
	 */
	
	public double getVelocity(int step, String pathName) {
		return getRawVelocity(step, pathName) * Constants.kVeloctiyUnitConversion;
	}
	
	/**
	 * @param step the desired time step within the array
	 * @param angleArray a array of global coordinate angles
	 * @return Angle for current time step based on designated angleArray
	 */
	public double getHeading(int step, String pathName) {
		mHeadingsArray = getHeadingsArray(pathName);
		return mHeadingsArray[step][1];
	}
	
	
	/**
	 * @param step the desired time step within the array
	 * @param velocityArray a array of velocities by step
	 * @return Velocity in Native Units per 100Ms for current step based on designated velocityArray
	 */
	
	public double getRawVelocity(int step, String pathName) {
		mVelocitiesArray = getVelocityArray(pathName);
		return mVelocitiesArray[step][1];
	}
	
	/**
	 * @param step the desired time step within the array
	 * @param angleArray a array of global coordinate angles
	 * @return Angle for current time step based on designated angleArray
	 */
	public double[][] getHeadingsArray(String pathName) {
		switch(pathName) {
		case "headingsTO_SCALE":
			mHeadingsArray = Constants.headingsTO_SCALE;
			break;
		case "headingsTO_SWITCH":
			mHeadingsArray = Constants.headingsTO_SWITCH;
			break;
		case "headingsTO_OPPOSITE_SCALE":
			mHeadingsArray = Constants.headingsTO_OPPOSITE_SCALE;
			break;
		case "headingsTO_LEFT_SWTICH":
			mHeadingsArray = Constants.headingsTO_LEFT_SWITCH;
			break;
		case "headingsTO_RIGHT_SWTICH":
			mHeadingsArray = Constants.headingsTO_RIGHT_SWITCH;
			break;
		case "headingsTO_CROSS_LINE":
			mHeadingsArray = Constants.headingsTO_CROSS_LINE;
			break;
		default:
			DriverStation.reportError("Invalid Headings Path Name!", false);
		}
		return mHeadingsArray;
	}
	
	public double[][] getVelocityArray(String pathName) {
		switch(pathName) {
		case "leftVelocitiesTO_SCALE":
			mVelocitiesArray = Constants.leftVelocitiesTO_SCALE;
			break;
		case "rightVelocitiesTO_SCALE":
			mVelocitiesArray = Constants.rightVelocitiesTO_SCALE;
			break;
		case "leftVelocitiesTO_SWITCH":
			mVelocitiesArray = Constants.leftVelocitiesTO_SWITCH;
			break;
		case "rightVelocitiesTO_SWITCH":
			mVelocitiesArray = Constants.rightVelocitiesTO_SWITCH;
			break;
		case "leftVelocitiesTO_OPPOSITE_SCALE":
			mVelocitiesArray = Constants.leftVelocitiesTO_OPPOSITE_SCALE;
			break;
		case "rightVelocitiesTO_OPPOSITE_SCALE":
			mVelocitiesArray = Constants.rightVelocitiesTO_OPPOSITE_SCALE;
			break;
		case "leftVelocitiesTO_LEFT_SWITCH":
			mVelocitiesArray = Constants.leftVelocitiesTO_LEFT_SWITCH;
			break;
		case "rightVelocitiesTO_LEFT_SWITCH":
			mVelocitiesArray = Constants.rightVelocitiesTO_LEFT_SWITCH;
			break;
		case "leftVelocitiesTO_RIGHT_SWITCH":
			mVelocitiesArray = Constants.leftVelocitiesTO_RIGHT_SWITCH;
			break;
		case "rightVelocitiesTO_RIGHT_SWITCH":
			mVelocitiesArray = Constants.rightVelocitiesTO_RIGHT_SWITCH;
			break;
		case "leftVelocitiesTO_CROSS_LINE":
			mVelocitiesArray = Constants.leftVelocitiesTO_CROSS_LINE;
			break;
		case "rightVelocitiesTO_CROSS_LINE":
			mVelocitiesArray = Constants.rightVelocitiesTO_CROSS_LINE;
			break;
		default:
			DriverStation.reportError("Invalid Velocity Path Name!", false);
		}
		return mVelocitiesArray;
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
}