package com.kenny.craftix.client.keyboard;

import org.lwjgl.input.Mouse;

public class MouseManager extends KeyboardManager
{
	/**
	 * Simple button down event. When user press on button event is activated.
	 * 
	 * @param buttonIn - is button a user can presed.
	 * @return 
	 */
	public static boolean buttonDown(int button)
	{
		return Mouse.isButtonDown(button);
	}
}
