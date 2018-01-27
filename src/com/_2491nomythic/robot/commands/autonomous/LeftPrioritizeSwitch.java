package com._2491nomythic.robot.commands.autonomous;

import com._2491nomythic.robot.commands.CommandBase;
import com._2491nomythic.robot.commands.drivetrain.DriveStraightToPositionPID;
import com._2491nomythic.robot.commands.drivetrain.RotateDrivetrainWithGyroPID;
import com._2491nomythic.robot.settings.Variables;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;

/**
 * Attempts to place a cube on either the left scale OR switch, prioritizing switch. If both scale and switch are on the right, the robot crosses the auto line.
 */
public class LeftPrioritizeSwitch extends CommandBase {
	private DriveStraightToPositionPID driveToSwitch, driveToScale, approachSwitch, approachScale;
	private CrossAutoLine crossLine;
	private RotateDrivetrainWithGyroPID turnTowardsSwitchOrScale;
	private int state;
	private Timer timer, delay;
	private boolean scaleSide, switchSide;

	/**
	 * Attempts to place a cube on either the left scale OR switch, prioritizing switch. If both scale and switch are on the right, the robot crosses the auto line.
	 */
    public LeftPrioritizeSwitch() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);    	
    	timer = new Timer();
    	delay = new Timer();
    	crossLine = new CrossAutoLine();
    	turnTowardsSwitchOrScale = new RotateDrivetrainWithGyroPID(90, false);
    	driveToScale = new DriveStraightToPositionPID(323.6);
    	driveToSwitch = new DriveStraightToPositionPID(168);
    	approachScale = new DriveStraightToPositionPID(-44);
    	approachSwitch = new DriveStraightToPositionPID(42);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	state = 0;
    	
    	scaleSide = DriverStation.getInstance().getGameSpecificMessage().substring(1, 2) == "L";
    	switchSide = DriverStation.getInstance().getGameSpecificMessage().substring(0, 1) == "L";
    	
    	delay.start();
    	
    	while(delay.get() < Variables.autoDelay) {
    		
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	timer.start();
    	
    	if(switchSide) {
    		switch(state) {
    		case 0:
    			driveToSwitch.start();
    			state++;
    			break;
    		case 1:
    			if(!driveToSwitch.isRunning()) {
    				turnTowardsSwitchOrScale.start();
    				timer.reset();
    				state++;
    			}
    			break;
    		case 2:
    			if(!turnTowardsSwitchOrScale.isRunning() || timer.get() > 1.5) {
    				turnTowardsSwitchOrScale.cancel();
    				approachSwitch.start();
    				state++;
    			}
    			break;
    		case 3:
    			if(!approachSwitch.isRunning()) {
    				//Drop cube here
    				state++;
    			}
    			break;
    		case 4:
    			break;
    		default:
    			System.out.println("Unexpected state in LeftScaleOrSwitch.java State: " + state);
    			break;
    		}
    	}
    	else if(scaleSide) {
    		switch(state) {
    		case 0:
    			driveToScale.start();
    			state++;
    			break;
    		case 1:
    			if(!driveToScale.isRunning()) {
    				timer.reset();
    				turnTowardsSwitchOrScale.start();
    				state++;
    			}
    			break;
    		case 2:
    			if(!turnTowardsSwitchOrScale.isRunning() || timer.get() > 1.5) {
    				turnTowardsSwitchOrScale.cancel();
    				approachScale.start();
    				state++;
    			}
    			break;
    		case 3:
    			if(!approachScale.isRunning()) {
    				//Drop cube here
    				state++;
    			}
    			break;
    		case 4:
    			break;
    		default:
    			System.out.println("Unexpected state in LeftScaleOrSwitch.java State: " + state);
    			break;
    		}
    	}
    	else {
    		crossLine.start();
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if(switchSide || scaleSide) {
            return !approachSwitch.isRunning() && state == 4;
    	}
    	else {
    		return !crossLine.isRunning() && timer.get() > 1;
    	}
    }

    // Called once after isFinished returns true
    protected void end() {
    	crossLine.cancel();
    	approachSwitch.cancel();
    	driveToSwitch.cancel();
    	driveToScale.cancel();
    	approachScale.cancel();
    	turnTowardsSwitchOrScale.cancel();
    	
    	drivetrain.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    	
    	
    }
}
