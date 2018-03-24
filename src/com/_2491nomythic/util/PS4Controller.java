package com._2491nomythic.util;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;

/**
 * Makes separate controller settings for the PS4
 */
public class PS4Controller extends Joystick {

	private DriverStation m_ds;
	private final int m_port;
	
	public PS4Controller(int port) {
		super(port);
		m_ds = DriverStation.getInstance();
	    m_port = port;
	}
	
	@Override
	/**
	   * Get the value of the axis.
	   *
	   * @param axis The axis to read, starting at 0.
	   * @return The value of the axis.
	   */
	public double getRawAxis(int axis) {
		if (axis == 3) {
			return m_ds.getStickAxis(m_port, 5);
		}
		else {
			return m_ds.getStickAxis(m_port, axis);
		}
	}

}
