package com.kenny.craftix.editor.gui;


import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.gui.GuiBackground;

public abstract class GuiEditorBackground extends GuiBackground
{
	public Craftix cx;
	@SuppressWarnings("unused")
	private float buttonWidth;
	@SuppressWarnings("unused")
	private float buttonHeight;
	
	
	public static class Pages
	{
		public static boolean isSwitchPage;
	}
}
