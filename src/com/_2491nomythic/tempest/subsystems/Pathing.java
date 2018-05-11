package com._2491nomythic.tempest.subsystems;

import com._2491nomythic.tempest.commands.autonomous.AutomaticAuto.EndPosition;
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
	private static double[][] mPathArray;
	
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
	 * @param endPositon
	 * @return A double[][] of Velocity values in Native Units per 100ms
	 */
	public static double[][] getPathArray(EndPosition endPosition) {
		setPathArray(endPosition);
		for (double[] u: mPathArray) {
			u[0] = u[0] * Constants.kVeloctiyUnitConversion;
			u[1] = u[1] * Constants.kVeloctiyUnitConversion;
		}
		return mPathArray;
	}
	
	/**
	 * 
	 * @param pathName The desire path array's name
	 * @return A double[][] of Velocity values in Ft per Sec
	 */
	public static double[][] getRawPathArray(EndPosition endPosition) {
		setPathArray(endPosition);
		return mPathArray;
	}
	
	private static void setPathArray(EndPosition endPosition) {
		switch(endPosition) {
		case SCALE:
			mPathArray = Constants.SCALE;
			break;
		case SWITCH:
			mPathArray = Constants.SWITCH;
			break;
		case OPPOSITE_SCALE:
			mPathArray = Constants.OPPOSITE_SCALE;
			break;
		case LEFT_SWITCH:
			mPathArray = Constants.LEFT_SWITCH;
			break;
		case RIGHT_SWITCH:
			mPathArray = Constants.RIGHT_SWITCH;
			break;
		case CROSS_LINE:
			mPathArray = Constants.CROSS_LINE;
			break;
		case BUMP_COUNTER:
			mPathArray = Constants.BUMP_COUNTER;
			break;
		default:
			DriverStation.reportWarning("Invalid Velocity Path Name!", false);
		}
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
}