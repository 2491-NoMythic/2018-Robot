package com._2491nomythic.tempest.commands;

import com._2491nomythic.tempest.commands.cubestorage.UltrasonicCubeHalt;
import com._2491nomythic.tempest.commands.intake.RunIntakeTime;

import edu.wpi.first.wpilibj.Timer;

/**
 * Goes through the entire cube intake process automatically
 */
public class AutomaticIntake extends CommandBase {
	private RunIntakeTime intakeCube;
	private UltrasonicCubeHalt loadCube;
	private double totalTime;
	private Timer timer;

	/**
	 * Goes through the entire cube intake process automatically
	 * @param time Time for the entire command to execute. Determines points at which other commands execute
	 */
    public AutomaticIntake(double time) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(intake);
    	requires(cubeStorage);
    	requires(shooter);
    	totalTime = time;
    	timer = new Timer();
    	intakeCube = new RunIntakeTime(1, totalTime * 3/4);
    	loadCube = new UltrasonicCubeHalt(1, totalTime);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	intake.open();
    	intakeCube.start();
    	loadCube.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (timer.get() >= totalTime * 3/4) {
    		intake.close();
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return timer.get() > totalTime;
    }

    // Called once after isFinished returns true
    protected void end() {
    	intakeCube.cancel();
    	loadCube.cancel();
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
