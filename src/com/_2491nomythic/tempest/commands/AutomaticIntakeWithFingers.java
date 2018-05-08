package com._2491nomythic.tempest.commands;

import com._2491nomythic.tempest.commands.cubestorage.UltrasonicCubeHalt;
import com._2491nomythic.tempest.commands.intake.RunIntakeUltrasonic;
import com._2491nomythic.tempest.settings.Variables;

import edu.wpi.first.wpilibj.Timer;

/**
 * Goes through the entire cube intake process automatically
 */
public class AutomaticIntakeWithFingers extends CommandBase {
	private UltrasonicCubeHalt loadCube;
	private RunIntakeUltrasonic intakeCube;
	private Timer timer;
	private double period, delay;
	/**
	 * Goes through the entire cube intake process automatically
	 * @param period Amount of time the fingers should be held open for
	 * @param delay Amount of time before intake fingers begin toggling
	 */
    public AutomaticIntakeWithFingers(double period, double delay) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	intakeCube = new RunIntakeUltrasonic(.8);
    	loadCube = new UltrasonicCubeHalt(1);
    	timer = new Timer();
    	this.period = period;
    	this.delay = delay;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Variables.cubeHalted = false;
    	intakeCube.start();
    	loadCube.start();
    	timer.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (timer.get() > delay) {
    		if (timer.get() % period == 0 && timer.get() % (3 * period) != 0) {
    			intake.close();
    		}
    		else if (timer.get() % (3 * period) == 0) {
    			intake.open();
    		}
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Variables.cubeHalted;
    }

    // Called once after isFinished returns true
    protected void end() {
    	timer.stop();
    	intake.close();
    	intakeCube.cancel();
    	loadCube.cancel();
    	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}