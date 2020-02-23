package com.kenny.craftix.editor.gui;

import java.io.IOException;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.gui.button.GuiButton;

/**
 * Simple init and connect all gui editor screens.
 * 
 * @author Kenny
 */
public class GuiEditor 
{
	private GuiSwitchEditor guiSwitchEditor = new GuiSwitchEditor();
	
	public GuiEditor(Craftix craftixIn) 
	{
		this.guiSwitchEditor.initGui(craftixIn);
	}
	
	public void buttonClick(GuiButton b, Craftix c) throws IOException
	{
		this.guiSwitchEditor.buttonClick(b, c); 
	}
	
	public GuiSwitchEditor getGuiSwichEditor()
	{
		return this.guiSwitchEditor;
	}
}
