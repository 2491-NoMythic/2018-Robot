package com._2491nomythic.tempest.commands;

/**
 * Rotates the drivetrain until it has centered on a cube as according to the limelight
 */
public class LocateCube extends CommandBase {
	private double leftSpeed, rightSpeed, tolerance;
	private boolean direction, tune;
	/**
	 * Rotates the drivetrain until it has centered on a cube as according to the limelight
	 * @param direction Pass true to rotate left, false to rotate right
	 */
    public LocateCube(boolean direction) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(drivetrain);
    	this.direction = direction;
    	leftSpeed = .25;
    	rightSpeed = .25;
    	tolerance = .5;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if (direction) {
    		leftSpeed *= -1;
    	}
    	else {
    		rightSpeed *= -1;
    	}
    	drivetrain.setVisionMode();
    	tune = false;
    	System.out.println("Target Status: " + drivetrain.hasTarget());
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (!drivetrain.hasTarget() && !tune) {
    		drivetrain.drivePercentOutput(leftSpeed, rightSpeed);
    	}
    	else if (Math.abs(drivetrain.getX()) > 0 + tolerance && drivetrain.hasTarget() && !tune) {
    		drivetrain.drivePercentOutput(leftSpeed, rightSpeed);
    		if (Math.abs(drivetrain.getX()) > 0 + (tolerance * 2)) {
    			tune = true;
    		}
    	}
    	if (tune && drivetrain.getX() < 0 - tolerance) {
    		drivetrain.drivePercentOutput(Math.abs(-leftSpeed), 
    				Math.abs(rightSpeed));
    	}
    	else if (tune && drivetrain.getX() > 0 + tolerance) {
    		drivetrain.drivePercentOutput(Math.abs(leftSpeed), -Math.abs(rightSpeed));
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	drivetrain.setCameraMode();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
