package com._2491nomythic.tempest.commands.buttonboard;

import com._2491nomythic.tempest.commands.CommandBase;
import com._2491nomythic.tempest.settings.ControllerMap;

/**
 *
 */
public class Input extends CommandBase {
	private int intakeHeight;
	
    public Input() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(intake);
    	requires(cubeStorage);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	intakeHeight = (int) oi.getAxis(ControllerMap.buttonBoard, ControllerMap.inputAxis);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
   		switch(intakeHeight) {
   		case -1:
    		if(!cubeStorage.isHeld()) {
    			intake.run(1, 1);
    			cubeStorage.run(1);
    			shooter.runAccelerate(-0.2);
    		}
    		break;
    	default:
    		shooter.run(-0.2);
    		break;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return cubeStorage.isHeld();
    }

    // Called once after isFinished returns true
    protected void end() {
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
