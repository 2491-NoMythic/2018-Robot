package com._2491nomythic.tempest.commands.drivetrain;

import com._2491nomythic.tempest.commands.CommandBase;
import com._2491nomythic.tempest.settings.Constants;

/**
 *
 */
public class DrivePath extends CommandBase {
	private int currentStep, timeCounter, direction;
	private double initialHeading, headingDiffrence, turnAdjustment, adjustedLeftVelocity, adjustedRightVelocity;
	private double[][] leftVelocites, rigthVelocites, headings;
	private boolean reverse;

    public DrivePath(double[][] leftVelocites, double[][] rightVelocites, double[][] headings, String startPos , boolean reverse) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(drivetrain);
    	requires(pathing);
    	this.leftVelocites = leftVelocites;
    	this.rigthVelocites = rightVelocites;
    	this.headings = headings;
    	this.reverse = reverse;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	/* Reset Variables */
    	initialHeading = drivetrain.getRawGyroAngle();
    	currentStep = 0;
    	timeCounter = 4;
    	if (reverse) {
    		direction = -1;
    	}
    	else {
    		direction = 1;
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(timeCounter == 4) {
    	    
			headingDiffrence = -pathing.returnAngle(currentStep, headings) + drivetrain.getRawGyroAngle() - initialHeading; //+
			turnAdjustment = Constants.kVelocitykG * headingDiffrence * Constants.kVeloctiyUnitConversion; 
			
			adjustedLeftVelocity = direction * pathing.returnVelocity(currentStep, leftVelocites) - turnAdjustment; //-
    		adjustedRightVelocity = direction * pathing.returnVelocity(currentStep, rigthVelocites) + turnAdjustment; //+
    		
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
