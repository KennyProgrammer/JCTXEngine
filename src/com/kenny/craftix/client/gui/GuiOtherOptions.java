package com.kenny.craftix.client.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.gui.button.GuiButton;
import com.kenny.craftix.client.settings.InGameSettings;
import com.kenny.craftix.client.text.TextLanguage;
import com.kenny.craftix.client.text.TextLoader;
import static com.kenny.craftix.client.language.Language.*;


public class GuiOtherOptions extends GuiBackground
{
	public List<GuiQuad> textures = new ArrayList<GuiQuad>();
	protected static GuiButton b1, b2, b3, b4, b5;
	
	@Override
	public void initGui(Craftix craftixIn) 
	{
		super.initGui(craftixIn);
		this.textures.add(new GuiQuad(GUI_LOCATION + "menu/gui_border", 0.08f, 0.83f, this.width / 3f, this.height / 10));
		this.textures.add(new GuiQuad(GUI_LOCATION + "menu/background", 0.0f, -0.10f, this.width / 1.2f, this.height / 1.2f));
		
		b1 = new GuiButton(51, BUTTON_RECT_TEXTURE, 0.68f, -0.72f, 2, "button.back");
		b2 = new GuiButton(52, BUTTON_RECT_TEXTURE, -0.3f, 0.5f, 0, "button.fpsTabOnMenu");
		b3 = new GuiButton(53, BUTTON_RECT_TEXTURE, 0.3f, 0.5f, 0, "button.renderCursor");
	}
	
	@Override
	protected void buttonClick(GuiButton button) throws IOException 
	{
		super.buttonClick(button);
		
		if(button.id == 51)
		{
			GuiOtherOptions.removePage();
			Pages.isOtherOptionsPage = false;
			Pages.isOptionsPage = true;
			GuiOptions.addPage();
		}
		
		if(button.id == 52)
		{
			if(InGameSettings.fpsTabOnMenuIn)
				InGameSettings.fpsTabOnMenuIn = false;
			else
				InGameSettings.fpsTabOnMenuIn = true;
			
			TextLoader.updateText(TextLanguage.t73, OTHER_FPS_TAB + ": " + InGameSettings.fpsTabOnMenuIn);
		}
		
		if(button.id == 53)
		{
			if(InGameSettings.renderCursorIn)
				InGameSettings.renderCursorIn = false;
			else
				InGameSettings.renderCursorIn = true;
			
			TextLoader.updateText(TextLanguage.t74, OTHER_RENDER_CURSOR + ": " + InGameSettings.renderCursorIn);
		}
	}
	
	/**
	 * Add to the list buttons.
	 */
	public static void addPage()
	{
		buttonList.add(b1);
		buttonList.add(b2);
		buttonList.add(b3);
	}
	
	/**
	 * Remove from the list buttons.
	 */
	public static void removePage()
	{
		buttonList.remove(b1);
		buttonList.remove(b2);
		buttonList.remove(b3);
	}
}
