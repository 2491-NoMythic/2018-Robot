package com._2491nomythic.tempest.commands.climber;

import com._2491nomythic.tempest.commands.CommandBase;

import edu.wpi.first.wpilibj.Timer;

/**
 *Runs the climb motors to raise the robot at a given speed for a given time.
 */
public class Ascend extends CommandBase {
	
	private double speedL, speedR, time;
	Timer timer;

    /**
     * Runs the climb motors to raise the robot at a given speed for a given time.
     * @param speedL The speed the left climb motor runs at
     * @param speedR The speed the right climb motor runs at
     * @param time The time the motors will run for
     */
	public Ascend(double speedL, double speedR, double time) {
    	this.speedL = speedL;
    	this.speedR = speedR;
    	this.time = time;
    	timer = new Timer();
        requires(climber);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	timer.start();
    	climber.ascend(speedL, speedR);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	drivetrain.getRollAngle();
    	if (drivetrain.getRollAngle() > 5) {
    		speedL *= 0.9;
    	}
    	else if (drivetrain.getRollAngle() < -5) {
    		speedR *= 0.9;
    	}
    	else {
    		speedL = 1;
    		speedR = 1;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return timer.get() > time;
    }

    // Called once after isFinished returns true
    protected void end() {
    	climber.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	climber.stop();
    }
}
