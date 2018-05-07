package com._2491nomythic.tempest.commands;

import edu.wpi.first.wpilibj.Timer;

/**
 * Automatic intake command for use with multicube autos. Moves the fingers while intaking.
 */
public class ImprovedAutoIntake extends CommandBase {
	private Timer timer, accelerateTimer;
	private double initialWait, frequency;
	private boolean completed, intaking;
	private int state;

	/**
	 * Automatic intake command for use with multicube autos. Moves the fingers while intaking.
	 * @param initialWait The time to wait in seconds before moving the fingers.
	 * @param frequency How often in seconds the fingers should open.
	 */
    public ImprovedAutoIntake(double initialWait, double frequency) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(intake);
    	requires(cubeStorage);
    	requires(shooter);
    	
    	this.initialWait = initialWait;
    	this.frequency = frequency;
    	timer = new Timer();
    	accelerateTimer = new Timer();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	state = 0;
    	accelerateTimer.reset();
    	timer.reset();
    	timer.start();
    	completed = false;
    	intake.run(1);
    	cubeStorage.run(1);
    	shooter.runAccelerate(-0.2);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(accelerateTimer.get() >= 0.5 && !intaking) {
    		completed = true;
    	}
    	
    	if(cubeStorage.isHeld() && intaking) {
    		intaking = false;
    		intake.stop();
    		intake.close();
    		cubeStorage.stop();
    		accelerateTimer.start();
    	}
    	
    	if(timer.get() >= initialWait) {
    		intaking = true;
    		timer.reset();
    	}
    	
    	if(intaking) {
    		switch(state) {
    		case 0:
    			if(timer.get() >= frequency) {
    				timer.reset();
    				intake.open();
    				state = 1;
    			}
    			break;
    		case 1:
    			if(timer.get() > 0.3) {
    				timer.reset();
    				intake.close();
    				state = 0;
    			}
    			break;
    		default:
    			System.out.println("Invalid state in ImprovedAutoIntake. State: " + state);
    			break;
    		}
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return completed;
    }

    // Called once after isFinished returns true
    protected void end() {
    	timer.stop();
    	accelerateTimer.stop();
    	intake.close();
    	intake.stop();
    	cubeStorage.stop();
    	shooter.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
