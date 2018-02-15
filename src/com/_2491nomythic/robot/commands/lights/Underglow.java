package com._2491nomythic.robot.commands.lights;

import com._2491nomythic.robot.commands.CommandBase;
import com._2491nomythic.robot.settings.Constants;
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
<<<<<<< HEAD
    	/*Alliance teamColor = DriverStation.getInstance().getAlliance();
    	String teamColorString = teamColor.toString();
=======
    	Alliance teamColor = DriverStation.getInstance().getAlliance();
>>>>>>> c88d1118e35a948160a7858e8ab6afd76fe1b054
    	if(teamColor == DriverStation.Alliance.Red) {
    		sickLights.writeSignal(25, Constants.underglowPWM);
    	}
    	else if (teamColor == DriverStation.Alliance.Blue) {
    		sickLights.writeSignal(100, Constants.underglowPWM);
    	}
    	else
    	{
    		sickLights.writeSignal(255, Constants.underglowPWM);
    	}
    	*/
    	SickLights.getInstance().writeSignal(255, 1);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	SickLights.getInstance().writeSignal(255, 1);
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
