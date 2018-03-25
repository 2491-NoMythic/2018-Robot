package com._2491nomythic.tempest.commands.drivetrain;

import com._2491nomythic.tempest.commands.CommandBase;
import com._2491nomythic.tempest.settings.Constants;

/**
 *
 */
public class DrivePath extends CommandBase {
	private int currentStep, timeCounter;
	private double initialHeading, headingDiffrence, turnAdjustment, adjustedLeftVelocity, adjustedRightVelocity;
	private double[][] leftVelocites, rigthVelocites, headings;

    public DrivePath(double[][] leftVelocites, double[][] rightVelocites, double[][] headings) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(drivetrain);
    	requires(pathing);
    	this.leftVelocites = leftVelocites;
    	this.rigthVelocites = rightVelocites;
    	this.headings = headings;
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
    	    
			headingDiffrence = pathing.returnAngle(currentStep, headings) + drivetrain.getRawGyroAngle() - initialHeading; //+
			turnAdjustment = Constants.kVelocitykG * headingDiffrence * Constants.kVeloctiyUnitConversion; 
			
			adjustedLeftVelocity = -pathing.returnVelocity(currentStep, leftVelocites) - turnAdjustment; //-
    		adjustedRightVelocity = -pathing.returnVelocity(currentStep, rigthVelocites) + turnAdjustment; //+
    		
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
    	currentStep = 0;
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
