package com._2491nomythic.robot.commands.lights;

import com._2491nomythic.robot.commands.CommandBase;
import com._2491nomythic.robot.settings.Constants;
import com._2491nomythic.robot.subsystems.SickLights;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;


/**
 *
 */
public class Underglow extends CommandBase {

    public Underglow() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Alliance teamColor = DriverStation.getInstance().getAlliance();
    	String teamColorString = teamColor.toString();
    	if(teamColorString.length() == 3) {
    		SickLights.getInstance().writeSignal(25, Constants.underglowPWM);
    	}
    	else if (teamColorString.length() == 4) {
    		SickLights.getInstance().writeSignal(100, Constants.underglowPWM);
    	}
    	else
    	{
    		SickLights.getInstance().writeSignal(255, Constants.underglowPWM);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}