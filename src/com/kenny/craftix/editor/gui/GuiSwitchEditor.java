package com.kenny.craftix.editor.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.gui.GuiMainMenu;
import com.kenny.craftix.client.gui.GuiQuad;
import com.kenny.craftix.client.gui.button.GuiButton;
import com.kenny.craftix.main.Side;
import com.kenny.craftix.main.SideMachine;

@SideMachine(Side.EDITOR)
public class GuiSwitchEditor extends GuiEditorBackground
{
	protected static GuiButton b1, b2;
	public List<GuiQuad> textures = new ArrayList<GuiQuad>();
	public static boolean f;
	
	/**
	 * Initialize drawing componetns, such a buttons, scroll bar or textures.
	 */
	@Override
	public void initGui(Craftix craftixIn) 
	{
		super.initGui(craftixIn);
		this.textures.add(new GuiQuad(GUI_LOCATION + "menu/title/craftix_logo_editor", 0f, 0.6f, this.width / 2, this.height / 5));
		b1 = new GuiButton(1000, BUTTON_RECT_TEXTURE, -0.2F, -0.4F, 2, "button.switchToEditor");
		b2 = new GuiButton(1001, BUTTON_RECT_TEXTURE, 0.2F, -0.4F, 2, "button.back");
	}
	
	@Override
	protected void buttonClick(GuiButton button, Craftix craftixIn) throws IOException 
	{
		super.buttonClick(button, craftixIn);
		
		if(button.id == 1000)
		{
			
		}
		
		if(button.id == 1001)
		{
			GuiSwitchEditor.removePage();
			Pages.isSwitchPage = false;
			com.kenny.craftix.client.gui.GuiBackground.Pages.isMainMenuPage = true;
			f = true;
		}
	}
	
	/**
	 * Add button to the main list of buttons.
	 */
	public static void addPage()
	{
		buttonList.add(b1);
		buttonList.add(b2);
	}
	
	/**
	 * Remove button from the main list of buttons.
	 */
	public static void removePage()
	{
		buttonList.remove(b1);
		buttonList.remove(b2);
	}
	
	@Override
	public void eventAfterClick() 
	{
		if(!Pages.isSwitchPage && f)
		{
			GuiMainMenu.addPage();
			GuiSwitchEditor.f = false;
		}
	}
}
