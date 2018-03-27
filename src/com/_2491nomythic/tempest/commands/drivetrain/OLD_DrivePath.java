package com._2491nomythic.tempest.commands.drivetrain;

import com._2491nomythic.tempest.commands.CommandBase;
import com._2491nomythic.tempest.settings.Constants;

/**
 *
 */
public class OLD_DrivePath extends CommandBase {
	private int currentStep, timeCounter, reverseDirection, invertRotation;
	private double initialHeading, headingDiffrence, turnAdjustment, adjustedLeftVelocity, adjustedRightVelocity;
	private double[][] leftVelocites, rightVelocites, headings;
	/**
	 * 
	 * @param leftVelocites A velocities array for the left side of the robot
	 * @param rightVelocites A velocities array for the right side of the robot
	 * @param headings A headings array for the path
	 * @param swap Swaps the start position
	 * @param reverse Reverse the robot drive direction
	 * @author Emilio Alvarez
	 */
    public OLD_DrivePath(double[][] leftVelocites, double[][] rightVelocites, double[][] headings, boolean swap, boolean reverse) {
    	
    	requires(drivetrain);
    	requires(old_pathing);
    	
    	this.headings = headings;
    	
    	/* Sets field side*/
    	if (swap) {
    		this.leftVelocites = rightVelocites;
    		this.rightVelocites = leftVelocites;
    		invertRotation = -1;
    	}
    	else {
    		this.leftVelocites = leftVelocites;
    		this.rightVelocites = rightVelocites;
    		invertRotation = 1;
    	}
    	
    	/* Sets drivetrain drive direction */
    	if (reverse) {
    		reverseDirection = -1;
    	}
    	else {
    		reverseDirection = 1;
    	}
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	/* Reset Variables */
    	initialHeading = drivetrain.getRawGyroAngle();
    	currentStep = 0;
    	timeCounter = 4;
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(timeCounter == 4) {
    	    
    	//	headingDiffrence = invertRotation * old_pathing.getHeading(currentStep, headings) + drivetrain.getRawGyroAngle() - initialHeading; //+
			turnAdjustment = Constants.kVelocitykG * Constants.kVeloctiyUnitConversion * headingDiffrence; 
			
		//	adjustedLeftVelocity = reverseDirection * pathing.returnVelocity(currentStep, leftVelocites) - turnAdjustment; //-
    	//	adjustedRightVelocity = reverseDirection * pathing.returnVelocity(currentStep, rightVelocites) + turnAdjustment; //+
    		
    		//System.out.println("H Diff: " + headingDiffrence + " Path: " + pathing.returnAngle(currentStep, headings) + " Gyro: " + -(headingDiffrence - pathing.returnAngle(currentStep, headings)) +  " Turn: " + turnAdjustment + " aL " + adjustedRightVelocity);

    		drivetrain.driveVelocity(adjustedLeftVelocity , adjustedRightVelocity); //+
			        		
			timeCounter = 0;
			currentStep++;
			
		} 
		else {
			timeCounter++;
		}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return headings.length == currentStep;
    }

    // Called once after isFinished returns true
    protected void end() {
    	drivetrain.stop();
    	currentStep = 0;
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
