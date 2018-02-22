package com._2491nomythic.util;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.buttons.Button;

/**
 * Creates a joystick button for triggering commands using the POV
 */
public class GuitarStrumButton extends Button {
	
	GenericHID m_joystick;
	boolean[] frets;
	boolean up, down;
	
	/**
	 * Creates a joystick button for triggering commands using a combination of fret buttons and the strum bar on a guitar hero controller
	 * @param joystick The GenericHID object that has the button (e.g. Joystick, Guitar, etc)
	 * @param frets Which frets are activated
	 */
	public GuitarStrumButton(GenericHID joystick, boolean[] frets) {
		m_joystick = joystick;
		this.frets = frets;
		up = true;
		down = true;
	}
	
	/**
	 * Creates a joystick button for triggering commands using a combination of fret buttons and the a specific direciton of the strum bar on a guitar hero controller
	 * @param joystick The GenericHID object that has the button (e.g. Joystick, Guitar, etc)
	 * @param frets Which frets are activated
	 * @param direction Which direction the strum bar is pressed (true == down, false == up)
	 */
	public GuitarStrumButton(GenericHID joystick, boolean[] frets, boolean direction) {
		m_joystick = joystick;
		this.frets = frets;
		up = !direction;
		down = direction;
	}
	
	/**
	 * Gets the value of the guitar strum button
	 * 
	 * @return The value of the guitar strum button
	 */
	public boolean get() {
		boolean result = false;
		
		if (up && down) {
			result = (m_joystick.getPOV() == 0 || m_joystick.getPOV() == 180);
		}
		else if (up) {
			result = m_joystick.getPOV() == 0;
		}
		else if (down) {
			result = m_joystick.getPOV() == 180;
		}
		
		for (int i = 0; i < 5; i++) {
			if (result) {
				if (frets[i]) {
					result = frets[i];
				}
			}
		}
		
		return result;
	}
}
