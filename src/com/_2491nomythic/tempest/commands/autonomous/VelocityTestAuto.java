package com._2491nomythic.tempest.commands.autonomous;

import com._2491nomythic.tempest.commands.CommandBase;
import com._2491nomythic.tempest.settings.Constants;

import edu.wpi.first.wpilibj.Timer;

/**
 *
 */
public class VelocityTestAuto extends CommandBase {
	Timer timer;

    public VelocityTestAuto() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(drivetrain);
    	timer = new Timer();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	drivetrain.driveVelocity(2 * Constants.kVeloctiyUnitConversion);
    	//drivetrain.driveVelocity(409.6 * 1.5);
    	timer.reset();
    	timer.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    		System.out.println(drivetrain.getRightVelocity() + ", " + drivetrain.getLeftVelocity());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return timer.get() >= 3;
    }

    // Called once after isFinished returns true
    protected void end() {
    	System.out.println("STOPPED");
    	System.out.println("LEFT DISTANCE: " + drivetrain.getLeftEncoderDistance());
    	System.out.println("RIGHT DISTANCE: " + drivetrain.getLeftEncoderDistance());
    	drivetrain.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
