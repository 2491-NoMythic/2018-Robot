package com._2491nomythic.tempest.commands.drivetrain;

import com._2491nomythic.tempest.commands.CommandBase;
import com._2491nomythic.tempest.settings.Variables;

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
		drivetrain.setSetpointRelative(relativeAngle);
		destinationAngle = (drivetrain.getGyroAngle() + relativeAngle) % 360;
		
		drivetrain.enable();
		timer.reset();
		
		while(timer.get() < 12) {
			if(timer.get() > 5) {
				if(drivetrain.getPosition() < minimumAngle) {
					minimumAngle = drivetrain.getPosition();
				}
				else if(drivetrain.getPosition() > maximumAngle) {
					maximumAngle = drivetrain.getPosition();
				}
			}
		}

		drivetrain.disable();
		
		System.out.println("Maximum Angle: " + maximumAngle);
		System.out.println("Minimum Angle: " + minimumAngle);
		avgAngle = (maximumAngle + minimumAngle) / 2;
		higher[i] = avgAngle < destinationAngle;
		
		System.out.println("Average Angle: " + avgAngle);
		proportional[i] = Variables.proportionalRotate;
		System.out.println("Proportional Value: " + proportional[i]);
	   	averageAngle[i] = avgAngle;
		
		timer.reset();
					
		if(!higher[i] && higher[i - 1]) {
			Variables.proportionalRotate += 0.0001;
		}
   		else if(higher[i]  && !higher[i - 1]) {
   			Variables.proportionalRotate -= 0.0001;
   		}
   		else if(higher[i]) {
   			Variables.proportionalRotate -= 0.001;
		}
   		else {
   			Variables.proportionalRotate += 0.001;
		}

		drivetrain.getPIDController().setPID(Variables.proportionalRotate, Variables.integralRotate, Variables.derivativeRotate);
		
		while(timer.get() < 5) {
			
		}

		i++;
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return i >= numberOfTrials;
	}

	// Called once after isFinished returns true
	protected void end() {
		drivetrain.disable();
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
