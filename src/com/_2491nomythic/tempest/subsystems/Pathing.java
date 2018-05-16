package com._2491nomythic.tempest.subsystems;

import com._2491nomythic.tempest.settings.Constants;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * A Subsystem that controls access to the pathing arrays
 * <p>
 * @author Emilio Lobo
 * @author Elias 
 * @version 1.0
 * @since 26-3-2018
 */
public class Pathing extends Subsystem {
	private static Pathing instance;
	private static double[][] mVelocitiesArray, mHeadingsArray;
	
	public static Pathing getInstance() {
		if(instance == null) {
			instance = new Pathing();
		}
		return instance;
	}
	
	public Pathing() {
		
	}
	
	/**
	 * 
	 * @param step Desired time vector
	 * @param pathName The path's name
	 * @return Velocity in Native Units per 100Ms for current step based on designated path
	 */
	public static double getVelocity(int step, String pathName) {
		return getRawVelocity(step, pathName) * Constants.kVeloctiyUnitConversion;
	}
	
	/**
	 * 
	 * @param step Desired time vector
	 * @param pathName The path's name
	 * @return Velocity in Ft per Sec for current step based on designated path
	 */
	public static double getRawVelocity(int step, String pathName) {
		return getVelocityArray(pathName)[step][1];
	}
	
	/**
	 * 
	 * @param pathName The desire path array's name
	 * @return A double[][] of Velocitiy values in Ft per Sec
	 */
	public static double[][] getVelocityArray(String pathName) {
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
		case "leftVelocitiesTO_BUMP_COUNTER":
			mVelocitiesArray = Constants.leftVelocitiesTO_BUMP_COUNTER;
			break;
		case "rightVelocitiesTO_BUMP_COUNTER":
			mVelocitiesArray = Constants.rightVelocitiesTO_BUMP_COUNTER;
			break;
		case "leftVelocitiesTO_MAX":
			mVelocitiesArray = Constants.leftVelocitiesTO_MAX;
			break;
		case "rightVelocitiesTO_MAX":
			mVelocitiesArray = Constants.rightVelocitiesTO_MAX;
			break;
		case "leftVelocitiesTO_CUBE":
			mVelocitiesArray = Constants.leftVelocitiesTO_CUBE;
			break;
		case "rightVelocitiesTO_CUBE":
			mVelocitiesArray = Constants.rightVelocitiesTO_CUBE;
			break;
		case "leftVelocitiesTO_NULL":
			mVelocitiesArray = Constants.leftVelocitiesTO_NULL;
			break;
		case "rightVelocitiesTO_NULL":
			mVelocitiesArray = Constants.rightVelocitiesTO_NULL;
			break;
		case "leftVelocitiesTO_SECOND_LEFT_SWITCH":
			break;
		case "rightVelocitiesTO_SECOND_LEFT_SWITCH":
			break;
		case "leftVelocitiesTO_SECOND_RIGHT_SWITCH":
			break;
		case "rightVelocitiesTO_SECOND_RIGHT_SWITCH":
			break;
		case "leftVelocitiesTO_LEFT_PYRAMID":
			mVelocitiesArray = Constants.leftVelocitiesTO_LEFT_PYRAMID;
			break;
		case "rightVelocitiesTO_LEFT_PYRAMID":
			mVelocitiesArray = Constants.rightVelocitiesTO_LEFT_PYRAMID;
			break;
		case "leftVelocitiesTO_RIGHT_PYRAMID":
			break;
		case "rightVelocitiesTO_RIGHT_PYRAMID":
			break;
		case "leftVelocitiesTO_BACKUP":
			mVelocitiesArray = Constants.leftVelocitiesTO_BACKOUT;
			break;
		case "rightVelocitiesTO_BACKUP":
			mVelocitiesArray = Constants.rightVelocitiesTO_BACKOUT;
			break;
			
		default:
			DriverStation.reportWarning("Invalid Velocity Path Name!", false);
		}
		return mVelocitiesArray;
	}
	
	/**
	 * 
	 * @param step Desired time vector
	 * @param pathName The path's name
	 * @return Heading angle for current time step based on designated path
	 */
	public static double getHeading(int step, String pathName) {
		return getHeadingsArray(pathName)[step][1];
	}
	
	/**
	 * 
	 * @param pathName The desired path array's name
	 * @return A double[][] array of heading angles
	 */
	public static double[][] getHeadingsArray(String pathName) {
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
		case "headingsTO_LEFT_SWITCH":
			mHeadingsArray = Constants.headingsTO_LEFT_SWITCH;
			break;
		case "headingsTO_RIGHT_SWITCH":
			mHeadingsArray = Constants.headingsTO_RIGHT_SWITCH;
			break;
		case "headingsTO_CROSS_LINE":
			mHeadingsArray = Constants.headingsTO_CROSS_LINE;
			break;
		case "headingsTO_BUMP_COUNTER":
			mHeadingsArray = Constants.headingsTO_BUMP_COUNTER;
			break;
		case "headingsTO_MAX":
			mHeadingsArray = Constants.headingsTO_MAX;
			break;
		case "headingsTO_CUBE":
			mHeadingsArray = Constants.headingsTO_CUBE;
			break;
		case "headingsTO_NULL":
			mHeadingsArray = Constants.headingsTO_NULL;
			break;
		case "headingsTO_BACKUP":
			mHeadingsArray = Constants.headingsTO_BACKOUT;
			break;
		case "headingsTO_LEFT_PYRAMID":
			mHeadingsArray = Constants.headingsTO_LEFT_PYRAMID;
		default:
			DriverStation.reportWarning("Invalid Headings Path Name!", false);
		}
		return mHeadingsArray;
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
}