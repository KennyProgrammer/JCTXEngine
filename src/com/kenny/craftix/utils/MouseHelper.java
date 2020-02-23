package com.kenny.craftix.utils;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public class MouseHelper 
{
	/**Mouse delta X this frame.*/
	public int deltaX;
	/**Mouse delta Y this frame.*/
	public int deltaY;
	public static boolean mousePressed;
	public static boolean mouseRelesed;
	
	/**
	 * Checks if the button is downed then the action takes place.
	 */
	public boolean isKeyDown(int keyCode) 
	{
		return Keyboard.isKeyDown(keyCode);
	}
	
	/**
	 * Checks if the mouse button is downed then the action takes place.
	 */
	public static boolean isMouseDown(int mouseButton)
	{
		return Mouse.isButtonDown(mouseButton);
	}
	
	public static boolean mousePressed()
	{
		 if (Mouse.getEventButton() == 0) 
	     {
			 mousePressed = true;
	     }
		 
		 return mousePressed;
	}
	
	public static boolean mouseRelesed()
	{
		 if (Mouse.getEventButton() == 0) 
	     {
	        mouseRelesed = true;
	     }
		 
		 return mouseRelesed;
	}
	
	/**
	 * Grab the mouse cursor it doesn't move and isn't seen.
	 */
	public void grabMouseCursor()
	{
		Mouse.setGrabbed(true);
		this.deltaX = 0;
		this.deltaY = 0;
	}
	
	/**
     * Ungrabs the mouse cursor so it can be moved and set it to the center of 
     * the screen.
     */
	public void ungrabMouseCursor()
	{
		Mouse.setCursorPosition(Display.getWidth() / 2, Display.getHeight() / 2);
		Mouse.setGrabbed(false);
	}
	
	public void mouseXYChange()
	{
		this.deltaX = Mouse.getDX();
		this.deltaY = Mouse.getDY();
	}
	
}
