package com._2491nomythic.tempest.commands;

import com._2491nomythic.tempest.commands.shooter.RunShooterManual;
import com._2491nomythic.tempest.commands.shooter.SetShooterSpeed;

import edu.wpi.first.wpilibj.Timer;

/**
 *
 */
public class SystemsCheck extends CommandBase {
	private Timer timer;
	private int state;
	private SetShooterSpeed switchSpeed, lowScale, medScale, highScale;
	private RunShooterManual fireSwitch, fireLow, fireMed, fireHigh;

    public SystemsCheck() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(drivetrain);
    	requires(cubeStorage);
    	requires(shooter);
    	requires(intake);
    	
    	timer = new Timer();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	switch(state) {
    	case 0:
    		drivetrain.drivePercentOutput(0.6, 0.6);
    		timer.start();
    		state++;
    		break;
    	case 1:
    		if(timer.get() > 1) {
    			drivetrain.drivePercentOutput(-0.6,  -0.6);
    			state++;
    		}
    		break;
    	case 2:
    		if(timer.get() > 2) {
    			drivetrain.drivePercentOutput(0.6, -0.6);
    			state++;
    		}
    		break;
    	case 3:
    		if(timer.get() > 3) {
    			drivetrain.drivePercentOutput(-0.6, 0.6);
    			state++;
    		}
    		break;
    	case 4:
    		if(timer.get() > 4) {
    			drivetrain.stop();
    			intake.run(-0.7, -0.7, -0.7);
    			state++;
    		}
    		break;
    	case 5:
    		if(timer.get() > 5) {
    			intake.run(0.7, 0.7, 0.7);
    			state++;
    		}
    		break;
    	case 6:
    		if(timer.get() > 6) {
    			intake.stop();
    			cubeStorage.run(-1);
    			state++;
    		}
    		break;
    	case 7:
    		if(timer.get() > 7) {
    			cubeStorage.run(1);
    			state++;
    		}
    		break;
    	case 8:
    		if(timer.get() > 8) {
    			cubeStorage.stop();
    			switchSpeed.start();
    			fireSwitch.start();
    			state++;
    		}
    		break;
    	case 9:
    		if(timer.get() > 9.5) {
    			lowScale.start();
    			fireLow.start();
    			state++;
    		}
    		break;
    	case 10:
    		if(timer.get() > 11) {
    			medScale.start();
    			fireMed.start();
    			state++;
    		}
    		break;
    	case 11:
    		if(timer.get() > 12.5) {
    			highScale.start();
    			fireHigh.start();
    			state++;
    		}
    		break;
    	case 12:
    		if(timer.get() > 14) {
    			shooter.stop();
    			intake.activate();
    			state++;
    		}
    		break;
    	case 13:
    		if(timer.get() > 14.5) {
    			intake.open();
    			state++;
    		}
    		break;
    	case 14:
    		if(timer.get() > 15) {
    			intake.close();
    			state++;
    		}
    		break;
    	case 15:
    		if(timer.get() > 15.5) {
    			shooter.setScalePosition();
    			state++;
    		}
    		break;
    	case 16:
    		if(timer.get() > 18) {
    			shooter.setSwitchPosition();
    			state++;
    		}
    		break;
    	case 17:
    		if(timer.get() > 20) {
    			intake.retract();
    			state++;
    		}
    		break;
    	case 18:
    		break;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return timer.get() > 21;
    }

    // Called once after isFinished returns true
    protected void end() {
    	drivetrain.stop();
    	cubeStorage.stop();
    	intake.stop();
    	shooter.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
