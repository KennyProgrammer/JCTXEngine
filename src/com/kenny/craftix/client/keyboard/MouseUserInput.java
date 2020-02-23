package com.kenny.craftix.client.keyboard;

import org.lwjgl.input.Mouse;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.gui.GuiBackground.Pages;

public class MouseUserInput extends MouseManager
{
	
	/**
	 * This main Craftix Instance.
	 */
	protected static Craftix cx;
	
	public void mainMenuButtonPreesed(Craftix craftixIn)
	{
		this.cx = craftixIn;
		
		if(Mouse.getEventButtonState())
		{
			if(Pages.isControlsPage)
			{	
				if(buttonDown(0))
				{
					cx.menuScene.guiControls.scrollScreen();
					System.out.println(cx.menuScene.guiControls.scrollY);
				}
				if(buttonDown(1))
				{	
					cx.menuScene.guiControls.scrollScreen();
					System.out.println(cx.menuScene.guiControls.scrollY);
				}

			}
		}
	}
}
