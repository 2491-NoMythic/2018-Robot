package com._2491nomythic.tempest.commands.climber;

import com._2491nomythic.tempest.commands.CommandBase;
import com._2491nomythic.tempest.settings.ControllerMap;

import edu.wpi.first.wpilibj.DriverStation;

/**
 *
 */
public class ClimbManual extends CommandBase {
	
	private double leftSpeed, rightSpeed;

    public ClimbManual() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(climber);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(oi.getButton(ControllerMap.operatorController, ControllerMap.convertToClimbMode) && DriverStation.getInstance().getMatchTime() < 30 && !DriverStation.getInstance().isAutonomous()) {
    		climber.ascend(leftSpeed, rightSpeed);
    		if(oi.getAxisDeadzonedSquared(ControllerMap.operatorController, ControllerMap.intakeAxis, 0.05) >= 0 ) {
    			leftSpeed = oi.getAxisDeadzonedSquared(ControllerMap.operatorController, ControllerMap.intakeAxis, 0.05);
    		}
    		else {
    			leftSpeed = -oi.getAxisDeadzonedSquared(ControllerMap.operatorController, ControllerMap.intakeAxis, 0.05);
    		}
    		if(oi.getAxisDeadzonedSquared(ControllerMap.operatorController, ControllerMap.cubeStorageAxis, 0.05) >= 0 ) {
    			rightSpeed = oi.getAxisDeadzonedSquared(ControllerMap.operatorController, ControllerMap.cubeStorageAxis, 0.05);
    		}
    		else {
    			rightSpeed = -oi.getAxisDeadzonedSquared(ControllerMap.operatorController, ControllerMap.cubeStorageAxis, 0.05);
    		}
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	climber.ascend(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
