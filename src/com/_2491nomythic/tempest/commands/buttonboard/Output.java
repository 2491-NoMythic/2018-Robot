package com._2491nomythic.tempest.commands.buttonboard;

import com._2491nomythic.tempest.commands.CommandBase;
import com._2491nomythic.tempest.settings.Constants;
import com._2491nomythic.tempest.settings.Variables;

import edu.wpi.first.wpilibj.Timer;

/**
 *
 */
public class Output extends CommandBase {
	private Timer timer;

    public Output() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(cubeStorage);
    	requires(intake);
    	
    	timer = new Timer();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	switch(Variables.motorChoice) {
    	case 1:
    		intake.run(-0.8, -0.8);
    		break;
    	case 2:
    		shooter.run(Constants.shooterSwitchSpeed);
    		cubeStorage.run(1);
    		break;
    	case 3:
    		cubeStorage.run(1);
    		break;
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(Variables.motorChoice == 3 && !cubeStorage.isHeld()) {
    		timer.start();
    	}
    	else {
    		timer.reset();
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return timer.get() > 2;
    }

    // Called once after isFinished returns true
    protected void end() {
    	intake.stop();
    	shooter.stop();
    	cubeStorage.stop();
    	timer.stop();
    	timer.reset();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
