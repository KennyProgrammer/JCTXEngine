package com.kenny.craftix.client.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.gui.button.GuiButton;
import com.kenny.craftix.client.scenes.WorldScene;
import com.kenny.craftix.world.SaveSlotLoader;

public class GuiInGameMenu extends GuiBackground
{
	public List<GuiQuad> textures = new ArrayList<GuiQuad>();
	protected static GuiButton b1, b2, b3, b4, b5, b6;
	public static boolean firstSave;

	@Override
	public void initGui(Craftix craftixIn) 
	{
		super.initGui(craftixIn);
		this.textures.add(new GuiQuad(GUI_LOCATION + "game/background", 0.3f, -0.3f, this.width * 1.3f, this.height * 1.3f));
		this.textures.add(new GuiQuad(GUI_LOCATION + "menu/gui_border", -0.5f, 0.7f, this.width / 4, this.height / 10));
		
		b1 = new GuiButton(70, BUTTON_RECT_TEXTURE, -0.3f, 0.4f, 0, "button.return");
		b2 = new GuiButton(71, BUTTON_RECT_TEXTURE, -0.3f, 0.2f, 0, "button.options");
		b3 = new GuiButton(72, BUTTON_RECT_TEXTURE, -0.3f, 0.0f, 0, "button.saveWorld");
		b4 = new GuiButton(73, BUTTON_RECT_TEXTURE, -0.3f, -0.2f, 0, "button.help");
		b5 = new GuiButton(74, BUTTON_RECT_TEXTURE, -0.3f, -0.4f, 0, "button.backToMenu");
		b6 = new GuiButton(75, BUTTON_RECT_TEXTURE, -0.3f, -0.6f, 0, "button.saveAndExit");
	}
	
	@Override
	protected void buttonClick(GuiButton button, Craftix craftixIn) throws IOException 
	{
		super.buttonClick(button, craftixIn);
		
		if(button.id == 70)
		{
			craftixIn.status.setGamePause(false);
			GuiRenderManager.renderInventoryBar = true;
			Pages.isMainGamePausePage = false;
			GuiInGameMenu.removePage();
		}
		
		if(button.id == 71)
		{
			if(!WorldScene.inGameOptions)
			{
				GuiInGameMenu.removePage();
				Pages.isMainGamePausePage = false;
				Pages.isOptionsPage = true;
				GuiOptions.addPage();
				WorldScene.inGameOptions = true;
			}
		}
		
		if(button.id == 72)
		{
			craftixIn.getWorldScene().getWorld().getSavingWorld().saveWorld();
			craftixIn.getWorldScene().getWorld().slotLoader.fullSaveWorldToSlot();
			SaveSlotLoader.updateTextSaveData();
			firstSave = true;
			LOGGER.info("World saved to: " + craftixIn.getWorldScene().getWorld().worldName + " success!");
		}
		
		if(button.id == 74)
		{
			craftixIn.backToMenu();
		}
		
		if(button.id == 75)
		{
			craftixIn.closeDisplay();
		}
	}
	
	/**
	 * Add this button to the list of buttons.
	 */
	public static void addPage()
	{
		buttonList.add(b1);
		buttonList.add(b2);
		buttonList.add(b3);
		buttonList.add(b4);
		buttonList.add(b5);
		buttonList.add(b6);
	}
	
	/**
	 * Remove this button from the list of buttons.
	 */
	public static void removePage()
	{
		buttonList.remove(b1);
		buttonList.remove(b2);
		buttonList.remove(b3);
		buttonList.remove(b4);
		buttonList.remove(b5);
		buttonList.remove(b6);
	}

}
