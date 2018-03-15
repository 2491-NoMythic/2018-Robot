package com._2491nomythic.util;

import com._2491nomythic.tempest.subsystems.Shooter;
//should probably be 3 files; one for the controller, one for the right controller, and one for the left controller
//be wary of outputs getting too small

/**
 * The framework that allows processing of shooter values through a custom control loop
 * @author Silas
 */
public class ShooterController {
	private double kF, kC, setPoint, tolerance, leftOut, rightOut;
	private Shooter source;
	private int leftDivisor, rightDivisor;
	private boolean hasLeftPassedAbove, hasRightPassedAbove, hasLeftPassedBelow, hasRightPassedBelow, leftStartAbove, rightStartAbove, enabled;

	
	/**
	 * The framework that allows processing of shooter values through a custom control loop
	 * @param shooter The shooter that provides input and output data
	 * @param C The error coefficient that determines the rate of increase/decrease based on distance from target
	 * @param F The feed forward value that determines a base power to run output motors at
	 */
	public ShooterController(Shooter shooter, double C, double F) {
		kC = C;
		kF = F;
		source = shooter;
		leftDivisor = 1;
		rightDivisor = 1;
	}
	
	/**
	 * Sets a tolerance for checking whether on target or not
	 * @param tolerance Tolerance to set, in the same units that the setpoint and input are in
	 */
	public void setTolerance(double tolerance) {
		this.tolerance = tolerance;
	}
	
	/**
	 * Checks if left side of controller is on target
	 * @return True if left side is on target
	 */
	public boolean leftOnTarget() {
		return Math.abs(source.getLeftShootVelocity() - setPoint) <= 0 + tolerance;
	}
	
	/**
	 * Checks if right side of controller is on target
	 * @return True if right side is on target
	 */
	public boolean rightOnTarget() {
		return Math.abs(source.getRightShootVelocity() - setPoint) <= 0 + tolerance;
	}
	
	/**
	 * Checks if both sides of controller are on target
	 * @return True if both sides are on target
	 */
	public boolean onTarget() {
		return leftOnTarget() && rightOnTarget();
	}
	
	/**
	 * Checks if left speed is above the setpoint
	 * @return True if left speed is above setpoint
	 */
	public boolean isLeftAbove() {
		return source.getLeftShootVelocity() > setPoint;
	}
	
	/**
	 * Checks if right speed is above the setpoint
	 * @return True if right speed is above setpoint
	 */
	public boolean isRightAbove() {
		return source.getRightShootVelocity() > setPoint;
	}
	
	/**
	 * Resets the left output calculator's divisor to default
	 */
	public void resetLeftDivisor() {
		leftDivisor = 1;
	}
	
	/**
	 * Resets the right output calculator's divisor to default
	 */
	public void resetRightDivisor() {
		rightDivisor = 1;
	}
	
	/**
	 * Sets the controller's feed-forward value
	 * @param F The feed-forward value to set for the controller
	 */
	public void setF(double F) {
		kF= F;
	}
	
	/**
	 * Sets the controller's error coefficient constant
	 * @param C The coefficient to set for the controller
	 */
	public void setC(double C) {
		kC = C;
	}
	
	/**
	 * Sets the setpoint, or desired target outcome of the controller
	 * @param setPoint The target of the controller
	 */
	public void setSetPoint(double setPoint) {
		this.setPoint = setPoint;
	}
	
	/**
	 * Gets left distance from setpoint
	 * @return Distance from setpoint, if negative, it is above the setpoint
	 */
	public double getLeftError() {
		return setPoint - source.getLeftShootVelocity();
	}
	
	/**
	 * Gets right distance from setpoint
	 * @return Distance from setpoint, if negative, it is above the setpoint
	 */
	public double getRightError() {
		return setPoint - source.getRightShootVelocity();
	}
	
	/**
	 * Calculates the output value for the left side of the shooter
	 * @return The output value to be fed to motors
	 */
	public double calculateLeftOutput() {
		return kF + ((kC * getLeftError()) / leftDivisor);
	}
	
	/**
	 * Calculates the output value for the right side of the shooter
	 * @return The output value to be fed to motors
	 */
	public double calculateRightOutput() {
		return kF + ((kC * getRightError()) / rightDivisor);
	}
	
	/**
	 * Checks the left side of the shooter for passes over the setpoint
	 */
	public void checkLeft() {
		if (isLeftAbove()) {
			if (!leftStartAbove) {
				hasLeftPassedAbove = true;
			}
			else {
				hasLeftPassedAbove = false;
			}
		}
		else {
			if (leftStartAbove) {
				hasLeftPassedBelow = true;
			}
			else {
				hasLeftPassedBelow = false;
			}
		}
	}
	
	/**
	 * Checks the right side of the shooter for passes over the setpoint
	 */
	public void checkRight() {
		if (isRightAbove()) {
			if (!rightStartAbove) {
				hasRightPassedAbove = true;
			}
			else {
				hasRightPassedAbove = false;
			}
		}
		else {
			if (rightStartAbove) {
				hasRightPassedBelow = true;
			}
			else {
				hasRightPassedBelow = false;
			}
		}
	}
	
	/**
	 * Resets all left side loop variables to prepare for another check
	 */
	public void resetLeftLoopVariables() {
		hasLeftPassedAbove = false;
		hasLeftPassedBelow = false;
		if (isLeftAbove()) {
			leftStartAbove = true;
		}
		else { 
			leftStartAbove = false;
		}
	}
	
	/**
	 * Resets all right side loop variables to prepare for another check
	 */
	public void resetRightLoopVariables() {
		hasRightPassedAbove = false;
		hasRightPassedBelow = false;
		if (isRightAbove()) {
			rightStartAbove = true;
		}
		else {
			rightStartAbove = false;
		}
	}
	
	/**
	 * Checks the left loop variables
	 * @return True if both left loop variables are true
	 */
	public boolean checkLoopLeft() {
		return hasLeftPassedAbove && hasLeftPassedBelow;
	}
	
	/**
	 * Checks the right loop variables
	 * @return True if both right loop variables are true
	 */
	public boolean checkLoopRight() {
		return hasRightPassedAbove && hasRightPassedBelow;
	}
	
	/**
	 * Runs the left loop
	 */
	public void loopLeft() {
		checkLeft();
		if (checkLoopLeft()) {
			leftDivisor++;
			resetLeftLoopVariables();
		}
	}
	
	/**
	 * Runs the right loop
	 */
	public void loopRight() {
		checkRight();
		if (checkLoopRight()) {
			rightDivisor++;
			resetRightLoopVariables();
		}
	}
	
	/**
	 * Runs both sides of the shooter loop
	 * @param run Boolean that determines whether the loop stays enabled or not
	 */
	public void enable() {
		enabled = true;
		while (enabled) {
			loopLeft();
			loopRight();
			leftOut = calculateLeftOutput();
			rightOut = calculateRightOutput();
			leftSet();
			rightSet();
		}
	}
	
	/**
	 * Disables the current control loop
	 */
	public void disable() {
		enabled = false;
	}
	
	/**
	 * Sets the left side of the shooter to the calculated loop outputs
	 */
	public void leftSet() {
		source.runLeftShoot(leftOut);
	}
	
	/**
	 * Sets the right side of the shooter to the calculated loop outputs
	 */
	public void rightSet() {
		source.runRightShoot(rightOut);
	}
}
