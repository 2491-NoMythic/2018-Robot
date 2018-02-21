package com._2491nomythic.tempest.commands;

import com._2491nomythic.tempest.commands.cubestorage.UltrasonicCubeHalt;
import com._2491nomythic.tempest.commands.intake.RunIntakeUltrasonic;
import com._2491nomythic.tempest.settings.Variables;

/**
 * Goes through the entire cube intake process automatically
 */
public class AutomaticIntake extends CommandBase {
	private UltrasonicCubeHalt loadCube;
	private RunIntakeUltrasonic intakeCube;
	/**
	 * Goes through the entire cube intake process automatically
	 * @param time Time for the entire command to execute. Determines points at which other commands execute
	 */
    public AutomaticIntake() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(intake);
    	requires(cubeStorage);
    	requires(shooter);
    	intakeCube = new RunIntakeUltrasonic(.8);
    	loadCube = new UltrasonicCubeHalt(1);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Variables.cubeHalted = false;
    	intakeCube.start();
    	loadCube.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Variables.cubeHalted;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
