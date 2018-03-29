package com._2491nomythic.util;

import com._2491nomythic.tempest.settings.Variables;
import com._2491nomythic.tempest.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class LeftDrivePIDController extends PIDController {
	public LeftDrivePIDController(Drivetrain drive) {
		super(Variables.proportionalForward, Variables.integralForward, Variables.derivativeForward, new PIDSource() {
    		PIDSourceType leftDriveSource = PIDSourceType.kDisplacement;
    		
    		@Override
    		public double pidGet() {
    			return drive.getLeftEncoderDistance();
    		}
    		
    		@Override 
    		public void setPIDSourceType(PIDSourceType pidSource) {
    			leftDriveSource = pidSource;
    		}
    		
    		@Override
    		public PIDSourceType getPIDSourceType() {
    			return leftDriveSource;
    		}
    	},
    	new PIDOutput() {

			@Override
			public void pidWrite(double output) {
				drive.driveLeftPercentOutput(output);
			};
    	}
    	);
		setAbsoluteTolerance(1);
	}

}
