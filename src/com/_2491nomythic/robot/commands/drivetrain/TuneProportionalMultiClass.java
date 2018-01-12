package com._2491nomythic.robot.commands.drivetrain;

import com._2491nomythic.robot.commands.CommandBase;
import com._2491nomythic.robot.settings.Variables;

import edu.wpi.first.wpilibj.Timer;

/**
 *
 */
public class TuneProportionalMultiClass extends CommandBase {
	private double[] proportional, averageAngle;
	private double maximumAngle, minimumAngle, relativeAngle, destinationAngle, avgAngle;
	private RotateDrivetrainWithGyroPID rotate;
	private int i, bestTrial, numberOfTrials;
	private Timer timer;
	private boolean[] higher;

    public TuneProportionalMultiClass(double relativeAngle, int numberOfTrials) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(drivetrain);
    	
    	this.relativeAngle = relativeAngle;
    	this.numberOfTrials = numberOfTrials;
    	proportional = new double[numberOfTrials];
    	averageAngle = new double[numberOfTrials];
    	higher = new boolean[numberOfTrials];
    	timer = new Timer();
    	rotate = new RotateDrivetrainWithGyroPID(relativeAngle, false);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	i = 1;
    	minimumAngle = 600000;
    	maximumAngle = -600000;
    	timer.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	System.out.println("Execute is running");
    	
    	destinationAngle = (drivetrain.getGyroAngle() + relativeAngle) % 360;
    	System.out.println("Destination Angle: " + destinationAngle);
    	    	
    	timer.reset();
    	//Doesn't rotate at all?
    	rotate.start();
    	
    	while(timer.get() < 20) {
    		System.out.println(drivetrain.getPosition());
    		
    		if(timer.get() > 5) {
    			if(drivetrain.getPosition() < minimumAngle) {
    				minimumAngle = drivetrain.getPosition();
    				System.out.println("Set minimum to " + minimumAngle);
    			}
    			else if(drivetrain.getPosition() > maximumAngle) {
    				maximumAngle = drivetrain.getPosition();
    				System.out.println("Set maximum to " + maximumAngle);
    			}
    			else {
    				System.out.println("Measuring...");
    			}
    		}
    	}
    	
    	rotate.cancel();
    	    	
    	System.out.println("Maximum Angle: " + maximumAngle);
    	System.out.println("Minimum Angle: " + minimumAngle);
    	avgAngle = (maximumAngle + minimumAngle) / 2;
    	higher[i] = avgAngle < destinationAngle;
    	
    	System.out.println("Average Angle: " + avgAngle);
    	proportional[i] = Variables.proportional;
    	System.out.println("Proportional Value: " + proportional[i]);
       	averageAngle[i] = avgAngle;
    	i++;
    	
    	timer.reset();
    	
    	if(!higher[i] && higher[i - 1]) {
    		Variables.proportional -= 0.0001;
    	}
    	else if(higher[i]  && !higher[i - 1]) {
    		Variables.proportional += 0.0001;
    	}
    	else if(higher[i]) {
    		Variables.proportional += 0.001;
    	}
    	else {
    		Variables.proportional -= 0.001;
    	}

    	drivetrain.getPIDController().setPID(Variables.proportional, Variables.integral, Variables.derivative);
    	
    	while(timer.get() < 5) {
    		System.out.println("Resetting...");
    	}

    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return i >= numberOfTrials;
    }

    // Called once after isFinished returns true
    protected void end() {
    	drivetrain.disable();
    	rotate.cancel();
    	drivetrain.stop();
    	
    	if(!(i < numberOfTrials)) {
    		i = 1;
    
    		while(i <= 15) {
    			switch(i) {
    			case 1:
    				bestTrial = i;
    				break;
    			default:
    				if(averageAngle[i] < averageAngle[bestTrial]) {
        				bestTrial = i;
        			}
    				break;
    			}
    			i++;
    		}
    		
    		System.out.println("Best Proportional Value: " + proportional[bestTrial]);
        	System.out.println("Best Distance From Angle: " + (averageAngle[bestTrial] - destinationAngle));
    	}
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
