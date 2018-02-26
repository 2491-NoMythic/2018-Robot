package com._2491nomythic.tempest.commands.drivetrain;

import com._2491nomythic.tempest.commands.CommandBase;
import com._2491nomythic.tempest.settings.Variables;

import edu.wpi.first.wpilibj.Timer;

//Higher derivative value means greater accuracy at lower speed.
//This one currently makes absolutely no sense. See the actual TuneDerivative for a derivative command that might actually work.

/**
 * Tunes the derivative value of the robot to be optimal when driving straight. 
 * YOU MUST HAVE PID TURN TUNED BEFORE USING THIS COMMAND!
 */
public class TuneDerivativeDriveStraight extends CommandBase {
	private double[] derivative, timeToReachDestination;
	private double distance, targetDistance, maximumAcceptedTime, absoluteError;
	private RotateDrivetrainWithGyroPID rotate;
	private int i, numberOfTrials, bestTrial;
	private Timer maximumTimer, errorTimer;

	/**
	 * Tunes the derivative value of the robot to be optimal when driving straight.
	 * YOU MUST HAVE PID TURN TUNED BEFORE USING THIS COMMAND!
	 * @param distance The distance the robot will attempt to move forward in each trial.
	 * @param maximumAcceptedTime The maximum time in seconds that the robot can take to drive.
	 * @param numberOfTrials The number of times the robot should tune itself. Average trials to tune: unknown
	 */
	
	public TuneDerivativeDriveStraight(double distance, double maximumAcceptedTime, double absoluteError, int numberOfTrials) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(drivetrain);
		
		this.distance = distance;
		this.maximumAcceptedTime = maximumAcceptedTime;
		this.numberOfTrials = numberOfTrials;
		this.absoluteError = absoluteError;
		derivative = new double[numberOfTrials];
		timeToReachDestination = new double[numberOfTrials];
		maximumTimer = new Timer();
		errorTimer = new Timer();
		rotate = new RotateDrivetrainWithGyroPID(180, false);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		i = 1;
		maximumTimer.start();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		targetDistance = drivetrain.getLeftEncoderDistance() + distance;
		
		drivetrain.setSetpoint(targetDistance);
		System.out.println("Target Distance: " + targetDistance);
		
		drivetrain.enable();
		errorTimer.start();
		maximumTimer.reset();
		
		while(maximumTimer.get() < 10) {
			errorTimer.stop();
			errorTimer.reset();
			
			while(drivetrain.getPosition() < targetDistance + absoluteError || drivetrain.getPosition() > targetDistance - absoluteError) {
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
		//This does run somehow, because the oscillation does stop.
		drivetrain.disable();
				
		drivetrain.getPIDController().setPID(Variables.proportionalForward, Variables.integralForward, Variables.derivativeForward);
		
		rotate.start();
		maximumTimer.reset();
		
		while(maximumTimer.get() < 5) {
			System.out.println("Resetting...");
		}
		
		rotate.cancel();

		i++;
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return i >= numberOfTrials || Variables.proportionalForward == 0;
	}

	// Called once after isFinished returns true
	protected void end() {
		//Any other specific lines to change back to normal rotate PID mode here:
		drivetrain.disable();
		rotate.cancel();
		drivetrain.stop();
		
		if(!(i < numberOfTrials)) {
			i = 1;
			
			while(i <= numberOfTrials) {
				switch(i) {
				case 1:
					bestTrial = i;
					break;
				default:
					if(timeToReachDestination[i] < timeToReachDestination[bestTrial]) {
						bestTrial = i;
					}
				}
				i++;
			}
			
			System.out.println("Best Derivative Value: " + derivative[bestTrial]);
			System.out.println("Best Avg. Distance From Destination: " + (timeToReachDestination[bestTrial] - targetDistance));
		}
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
