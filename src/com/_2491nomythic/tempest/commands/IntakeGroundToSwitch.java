package com._2491nomythic.tempest.commands;

/**
 * Acquires a cube from the ground and prepares to fire to the switch
 */
public class IntakeGroundToSwitch extends CommandBase {
	private int state;
	
	/**
	 * Acquires a cube from the ground and prepares to fire to the switch
	 */
	public IntakeGroundToSwitch() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(intake);
		requires(cubeStorage);
		requires(shooter);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		state = 0;
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		switch(state) {
		case 0:
			intake.run(1);
			cubeStorage.run(1);
			state++;
			break;
		case 1:
			if(cubeStorage.isHeld()) {
				shooter.setScalePosition();
				state++;
			}
			break;
		case 2:
			break;
		default:
			System.out.println("Unexpected state in IntakeGroundToSwitch! State: " + state);
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return state == 2;
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
