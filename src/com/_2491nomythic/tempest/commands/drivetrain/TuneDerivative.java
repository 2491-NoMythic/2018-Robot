package com._2491nomythic.tempest.commands.drivetrain;
 
import com._2491nomythic.tempest.commands.CommandBase;
import com._2491nomythic.tempest.settings.Variables;

import edu.wpi.first.wpilibj.Timer;
 
/**
 * Tunes the derivative value of the robot to be optimal when rotating.
 */
public class TuneDerivative extends CommandBase {
	private double relativeAngle, targetAngle, maximumAcceptedTime, absoluteError;
	private Timer maximumTimer, errorTimer;
	private int i, numberOfTrials, bestTrial;
	private double[] derivative, timeToReachDestination;
 
	/**
	 * Tunes the derivative value of the robot to be optimal when rotating.
	 * @param angle The distance in degrees that the robot will attempt to rotate in each trial, where negative values turn to the left.
	 * @param maximumAcceptedTime The maximum acceptable time it can take the robot to turn.
	 * @param absoluteError The maximum acceptable error in degrees.
	 * @param numberOfTrials The number of times the robot should tune itself.
	 */
	public TuneDerivative(double angle, double maximumAcceptedTime, double absoluteError, int numberOfTrials) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);'
		requires(drivetrain);
		
		relativeAngle = angle;
		this.numberOfTrials = numberOfTrials;
		//The +1 is for the time spent checking against the absoluteError. Unsure whether or not it should be counted.
		this.maximumAcceptedTime = maximumAcceptedTime + 1;
		this.absoluteError = absoluteError;
		
		maximumTimer = new Timer();
		errorTimer = new Timer();
		
		derivative = new double[numberOfTrials];
		timeToReachDestination = new double[numberOfTrials];
	}
 
	// Called just before this Command runs the first time
	protected void initialize() {
		i = 1;
	}
 
	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		targetAngle = (drivetrain.getGyroAngle() + relativeAngle) % 360;
		drivetrain.setSetpoint(targetAngle);
		drivetrain.enable();
		maximumTimer.start();
		maximumTimer.reset();
		
		while(maximumTimer.get() < 10) {
			errorTimer.stop();
			errorTimer.reset();
			
			while(drivetrain.getPosition() < targetAngle + absoluteError || drivetrain.getPosition() > targetAngle - absoluteError) {
				errorTimer.start();
				
				if(errorTimer.get() > 1) {
					maximumTimer.stop();
					timeToReachDestination[i] = maximumTimer.get();
					System.out.println("Time to Reach Destination: " + timeToReachDestination[i]);
				}	
			}
		}
		
		drivetrain.disable();
		maximumTimer.stop();
		
		//The first two make sense, but I'm not sure about the last one.
		if(timeToReachDestination[i] > maximumAcceptedTime) {
			Variables.derivativeRotate -= 0.01;
		}
		else if(timeToReachDestination[i] > timeToReachDestination[i - 1]) {
			Variables.derivativeRotate -= 0.001;
		}
		else {
			Variables.derivativeRotate += 0.001;
		}
		
		drivetrain.getPIDController().setPID(Variables.proportionalRotate, Variables.integralRotate, Variables.derivativeRotate);
		
		errorTimer.reset();
		
		while(errorTimer.get() < 5) {
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
		drivetrain.disable();
		drivetrain.stop();
		maximumTimer.stop();
		errorTimer.stop();
		
		i = 1;
		
		while(i <= numberOfTrials) {
			if(i == 1) {
				bestTrial = i;
			}
			else if(timeToReachDestination[i] < timeToReachDestination[bestTrial]) {
				bestTrial = i;
			}
			i++;
		}
		
		
		System.out.println("Best Derivative Value: " + derivative[bestTrial]);
		System.out.println("Fastest Time to Destination: " + timeToReachDestination[bestTrial]);
	}
 
	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}