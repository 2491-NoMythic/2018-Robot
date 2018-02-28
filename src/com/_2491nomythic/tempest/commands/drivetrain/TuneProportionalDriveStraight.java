package com._2491nomythic.tempest.commands.drivetrain;

import com._2491nomythic.tempest.commands.CommandBase;
import com._2491nomythic.tempest.settings.Variables;

import edu.wpi.first.wpilibj.Timer;

/**
 * Tunes the proportional value of the robot to be optimal when driving straight.
 * YOU MUST HAVE PID TURN TUNED BEFORE USING THIS COMMAND!
 */
public class TuneProportionalDriveStraight extends CommandBase {
	private double[] proportional, averageDistance;
	private double minimumDistance, maximumDistance, distance, targetDistance;
	private RotateDrivetrainWithGyroPID rotate;
	private int i, numberOfTrials, bestTrial;
	private Timer timer;
	private boolean[] higher;

	/**
	 * Tunes the proportional value of the robot to be optimal when driving straight.
	 * YOU MUST HAVE PID TURN TUNED BEFORE USING THIS COMMAND!
	 * @param distance The distance in feet the robot will attempt to move forward in each trial.
	 * @param numberOfTrials The number of times the robot should tune itself. Average trials to tune: unknown
	 */
	
	public TuneProportionalDriveStraight(double distance, int numberOfTrials) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(drivetrain);
		
		this.distance = Math.abs(distance);
		this.numberOfTrials = numberOfTrials;
		proportional = new double[numberOfTrials];
		averageDistance = new double[numberOfTrials];
		higher = new boolean[numberOfTrials];
		timer = new Timer();
		rotate = new RotateDrivetrainWithGyroPID(180, false);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		i = 1;
		minimumDistance = 100000;
		maximumDistance = -100000;
		timer.start();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		targetDistance = drivetrain.getLeftEncoderDistance() + distance;
		
		drivetrain.setSetpoint(targetDistance);
		System.out.println("Target Distance: " + targetDistance);
		
		drivetrain.enable();
		timer.reset();
		
		while(timer.get() < 20) {
			//This prints fine
			System.out.println(drivetrain.getPosition());
			
			if(timer.get() > 5) {
				//What does getPosition actually return? Could explain why they never print.
				if(drivetrain.getPosition() < minimumDistance) {
					//Why doesn't this print?
					minimumDistance = drivetrain.getPosition();
					System.out.println("Set minimum to " + minimumDistance);
				}
				else if(drivetrain.getPosition() > maximumDistance) {
					//Why doesn't this print?
					maximumDistance = drivetrain.getPosition();
					System.out.println("Set maximum to " + maximumDistance);
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
		System.out.println("Maximum Distance: " + maximumDistance);
		System.out.println("Minimum Distance: " + minimumDistance);
		averageDistance[i] = (maximumDistance + minimumDistance) / 2;
		higher[i] = averageDistance[i] < targetDistance;
		
		System.out.println("Average Distance: " + averageDistance[i]);
		proportional[i] = Variables.proportionalForward;
		System.out.println("Proportional Value: " + proportional[i]);
		
		if(!higher[i] && higher[i - 1]) {
			Variables.proportionalForward += 0.0001;
		}
		else if(higher[i]  && !higher[i - 1]) {
			Variables.proportionalForward -= 0.0001;
		}
		else if(higher[i]) {
			Variables.proportionalForward -= 0.001;
		}
		else {
			Variables.proportionalForward += 0.001;
		}
		
		drivetrain.getPIDController().setPID(Variables.proportionalForward, Variables.integralForward, Variables.derivativeForward);
		
		rotate.start();
		timer.reset();
		
		while(timer.get() < 5) {
			System.out.println("Resetting...");
		}
		
		rotate.cancel();
		
		i++;
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return i >= numberOfTrials || Variables.proportionalRotate == 0;
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
					if(averageDistance[i] < averageDistance[bestTrial]) {
						bestTrial = i;
					}
				}
				i++;
			}
			
			System.out.println("Best Proportional Value: " + proportional[bestTrial]);
			System.out.println("Best Avg. Distance From Destination: " + (averageDistance[bestTrial] - targetDistance));
		}
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
