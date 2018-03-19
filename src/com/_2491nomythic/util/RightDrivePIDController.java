package com._2491nomythic.util;

import com._2491nomythic.tempest.settings.Variables;
import com._2491nomythic.tempest.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class RightDrivePIDController extends PIDController {
	public RightDrivePIDController(Drivetrain drive) {
		super(Variables.proportionalForward, Variables.integralForward, Variables.derivativeForward, new PIDSource() {
    		PIDSourceType rightDriveSource = PIDSourceType.kRate;
    		
    		@Override
    		public double pidGet() {
    			return drive.getRightEncoderRate();
    		}
    		
    		@Override 
    		public void setPIDSourceType(PIDSourceType pidSource) {
    			rightDriveSource = pidSource;
    		}
    		
    		@Override
    		public PIDSourceType getPIDSourceType() {
    			return rightDriveSource;
    		}
    	},
    	new PIDOutput() {

			@Override
			public void pidWrite(double output) {
				drive.driveRightPercentOutput(output);
			};
    	}
    	);
		setAbsoluteTolerance(.5);
	}
}
