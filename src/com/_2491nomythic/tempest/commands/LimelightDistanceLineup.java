package com._2491nomythic.tempest.commands;

/**
 *
 */
public class LimelightDistanceLineup extends CommandBase {
	private double speed;
	private boolean goodToGo;

    public LimelightDistanceLineup(double speed) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(drivetrain);
    	this.speed = speed;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	drivetrain.setVisionMode();
    	goodToGo = false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(drivetrain.getArea() > 55) {
        	drivetrain.drivePercentOutput(-speed, -speed);
        }
        else if(drivetrain.getArea() < 50) {
        	drivetrain.drivePercentOutput(speed, speed);
        }
        else {
        	goodToGo = true;
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return goodToGo;
    }

    // Called once after isFinished returns true
    protected void end() {
    	drivetrain.setCameraMode();
    	drivetrain.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
