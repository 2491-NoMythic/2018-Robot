package com._2491nomythic.robot.commands;

import com._2491nomythic.robot.settings.Constants;

import edu.wpi.first.wpilibj.Timer;

/**
 * Shoots a cube from the CubeStorage automatically.
 */
public class AutomaticShoot extends CommandBase {
	private int state;
	private Timer timer;
	private boolean scale;

	/**
	 * Shoots a cube from the CubeStorage automatically.
	 * @param scale True tells the shooter to launch for the scale, false tells the shooter to launch for the switch.
	 */
    public AutomaticShoot(boolean scale) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(shooter);
    	requires(cubeStorage);
    	
    	this.scale = scale;
    	timer = new Timer();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	state = 0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	switch(state) {
    	case 0:
    		if(scale) {
    			shooter.raiseShooter();
    		}
    		else {
    			shooter.lowerShooter();
    		}
    		
    		timer.start();
    		state++;
    		break;
    	case 1:
    		if(timer.get() > Constants.timeForShooterToSpinUp && timer.get() > Constants.timeForShooterToRaise) {
    			cubeStorage.run(1);
    			state++;
    		}
    		break;
    	case 2:
    		break;
    	default:
    		System.out.println("Unexpected state in AutomaticShoot. State: " + state);
    		break;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return timer.get() > 5;
    }

    // Called once after isFinished returns true
    protected void end() {
    	shooter.stop();
    	cubeStorage.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
