package com._2491nomythic.robot.commands.drivetrain;

import com._2491nomythic.robot.commands.CommandBase;
import com._2491nomythic.robot.settings.Variables;

import edu.wpi.first.wpilibj.Timer;

/**
 * Tunes the proportional value of the robot to be optimal when rotating.
 * @author Thomas
 *
 */
public class TuneProportional extends CommandBase {
	private double[] proportional, averageAngle;
	private double maximumAngle, minimumAngle, relativeAngle, destinationAngle, avgAngle;
	private int i, bestTrial, numberOfTrials;
	private Timer timer;
	private boolean[] higher;

	/**
	 * Tunes the proportional value of the robot to be optimal when rotating.
	 * @param relativeAngle The distance in degrees that the robot should attempt to rotate each trial, where a negative value rotates to the left.
	 * @param numberOfTrials The number of times the robot should tune itself. Average trials to tune: unknown
	 */
	
    public TuneProportional(double relativeAngle, int numberOfTrials) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(drivetrain);

    	this.relativeAngle = relativeAngle;
    	this.numberOfTrials = numberOfTrials;
    	proportional = new double[numberOfTrials];
    	averageAngle = new double[numberOfTrials];
    	higher = new boolean[numberOfTrials];
    	timer = new Timer();
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
    	
    	drivetrain.setSetpointRelative(relativeAngle);
    	//drivetrain.setSetpoint((drivetrain.getGyroAngle() + relativeAngle) % 360);
    	
    	destinationAngle = (drivetrain.getGyroAngle() + relativeAngle) % 360;
    	System.out.println("Destination Angle: " + destinationAngle);
    	
    	drivetrain.enable();
    	
    	timer.reset();
    	
    	while(timer.get() < 20) {
    		//This prints fine
    		System.out.println(drivetrain.getPosition());
    		
    		if(timer.get() > 5) {
    			//What does getPosition actually return? Could explain why they never print.
    			if(drivetrain.getPosition() < minimumAngle) {
    				//Why doesn't this print?
    				minimumAngle = drivetrain.getPosition();
    				System.out.println("Set minimum to " + minimumAngle);
    			}
    			else if(drivetrain.getPosition() > maximumAngle) {
    				//Why doesn't this print?
    				maximumAngle = drivetrain.getPosition();
    				System.out.println("Set maximum to " + maximumAngle);
    			}
    			else {
    				//Because this prints fine.
    				System.out.println("Measuring...");
    			}
    		}
    	}
    	//This does run somehow, because the oscillation does stop.
    	drivetrain.disable();
    	
    	//Nothing here prints
    	System.out.println("Maximum Angle: " + maximumAngle);
    	System.out.println("Minimum Angle: " + minimumAngle);
    	avgAngle = (maximumAngle + minimumAngle) / 2;
    	higher[i] = avgAngle < destinationAngle;
    	
    	System.out.println("Average Angle: " + avgAngle);
    	proportional[i] = Variables.proportional;
    	System.out.println("Proportional Value: " + proportional[i]);
       	averageAngle[i] = avgAngle;
    	
    	timer.reset();
    	    		
    	if(!higher[i] && higher[i - 1]) {
    		Variables.proportional += 0.0001;
    	}
   		else if(higher[i]  && !higher[i - 1]) {
   			Variables.proportional -= 0.0001;
   		}
   		else if(higher[i]) {
   			Variables.proportional -= 0.001;
    	}
   		else {
   			Variables.proportional += 0.001;
    	}

    	drivetrain.getPIDController().setPID(Variables.proportional, Variables.integral, Variables.derivative);
    	
    	while(timer.get() < 5) {
    		System.out.println("Resetting...");
    	}

    	i++;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return i >= numberOfTrials;
    }

    // Called once after isFinished returns true
    protected void end() {
    	//drivetrain.disable();
    	drivetrain.stop();
    	
    	if(i >= numberOfTrials) {
    		i = 1;
    	    
    		while(i <= numberOfTrials) {
    			switch(i) {
    			case 1:
    				bestTrial = i;
    				break;
    			default:
    				if(averageAngle[i] < averageAngle[bestTrial]) {
            			bestTrial = i;
            		}
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
