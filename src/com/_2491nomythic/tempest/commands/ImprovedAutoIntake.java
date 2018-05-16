package com._2491nomythic.tempest.commands;

import com._2491nomythic.tempest.commands.drivetrain.DriveTime;
import com._2491nomythic.tempest.settings.Variables;

import edu.wpi.first.wpilibj.Timer;

/**
 * Automatic intake command for use with multicube autos. Moves the fingers while intaking.
 */
public class ImprovedAutoIntake extends CommandBase {
	private Timer timer, accelerateTimer, waitTimer, timeout;
	private double initialWait;
	private DriveTime backAway, goBack, creep;
	private boolean completed, intaking, startOpened;
	private int state, cycleTimeout, timeoutSafety;

	/**
	 * Automatic intake command for use with multicube autos. Moves the fingers while intaking.
	 * @param initialWait The time to wait in seconds before moving the fingers.
	 * @param frequency How often in seconds the fingers should open.
	 */
    public ImprovedAutoIntake(double initialWait, boolean startOpened) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(intake);
    	requires(cubeStorage);
    	requires(shooter);
    	
    	this.startOpened = startOpened;
    	this.initialWait = initialWait;
    	backAway = new DriveTime(-0.4, -0.4, 0.5);
    	goBack = new DriveTime(0.4, 0.4, 0.7);
    	creep = new DriveTime(0.15, 0.15, 200);
    	timer = new Timer();
    	accelerateTimer = new Timer();
    	timeout = new Timer();
    	waitTimer = new Timer();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	timeoutSafety = 0;
    	cycleTimeout = 0;
    	state = 1;
    	accelerateTimer.reset();
    	waitTimer.reset();
    	timeout.reset();
    	timer.reset();
    	timer.start();
    	waitTimer.start();
    	intake.openArms();
    	intaking = false;
    	completed = false;
    	intake.run(1);
    	cubeStorage.run(1);
    	shooter.runAccelerate(-0.2);
    	
    	if(startOpened) {
    		intake.openFingers();
    	}
    	else {
    		intake.retractFingers();
    	}
    	
    	if(cubeStorage.isHeld()) {
    		completed = true;
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {    	
    	if(accelerateTimer.get() >= 0.5) {
    		completed = true;
    		shooter.runAccelerate(0);
    		waitTimer.stop();
    		waitTimer.reset();
    	}
    	
    	if(waitTimer.get() > initialWait) {
    		intaking = true;
    		waitTimer.reset();
    	}
    	
    	if(cubeStorage.isHeld() && intaking) {
    		cycleTimeout = 0;
    		intaking = false;
    		intake.stop();
    		intake.retractFingers();
    		cubeStorage.stop();
    		accelerateTimer.start();
    	}
    	
    	if(cycleTimeout > 1) {	
    		switch(timeoutSafety) {
    		case 0:
    			intake.openFingers();
    			timeout.start();
    			backAway.start();
    			timeoutSafety++;
    			break;
    		case 1:
    			if(timeout.get() >= 0.5) {
    				backAway.cancel();
    				goBack.start();
    				timeout.reset();
    				timeoutSafety++;
    			}
    			break;
    		case 2:
    			if(timeout.get() >= 0.7) {
    				goBack.cancel();
    				drivetrain.stop();
    				timeoutSafety++;
    			}
    			break;
    		case 3:
    		default:
    			cycleTimeout = 0;
    			timeoutSafety = 0;
    			break;
    		}
    	}
    	else if(!Variables.isPathRunning) {

    	}
    	
    	if(intaking) {    		
    		switch(state) {
    		case 0:    			
    			if(timer.get() > 0.5) {
    				intake.run(1);
    			}
    			
    			if(timer.get() >= 1.5) {
    				timer.reset();
    				intake.openFingers();
    				state = 1;
    				cycleTimeout++;
    			}
    			break;
    		case 1:
    			if(timer.get() > 0.3) {
    				timer.reset();
    				intake.retractFingers();
    				intake.run(-0.2);

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
        if(!backAway.isRunning() && !goBack.isRunning()) {
        	return completed;
        }
        else {
        	return false;

        }
    }

    // Called once after isFinished returns true
    protected void end() {
    	timer.stop();
    	accelerateTimer.stop();
    	timeout.stop();
    	waitTimer.stop();
    	intake.retractFingers();
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
