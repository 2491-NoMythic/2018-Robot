package com._2491nomythic.tempest.commands.climber;

import com._2491nomythic.tempest.commands.CommandBase;

import edu.wpi.first.wpilibj.Timer;

/**
 *Runs the climb motors to raise the robot at a given speed. We probably want to add a time restriction, or some other way to make it stop.
 */
public class Ascend extends CommandBase {
	
	private double speedL, speedR, time;
	Timer timer;

    public Ascend(double speedL, double speedR, double time) {
    	this.speedL = speedL;
    	this.speedR = speedR;
    	this.time = time;
        requires(climber);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	timer.start();
    	climber.ascend(speedL, speedR);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
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
