package com.kenny.craftix.world;

import java.util.ArrayList;
import java.util.List;

import com.kenny.craftix.client.gui.GuiBackground.Pages;
import com.kenny.craftix.client.text.Text;
import com.kenny.craftix.client.text.TextData;
import com.kenny.craftix.client.text.TextLoader;

public class SaveSlotLoader 
{
	/**Helping class to load the current world data from specific slot.*/
	public static SavingWorld savingWorld;
	public List<SaveSlot> slotList = new ArrayList<SaveSlot>();
	public static Text t1_n, t1_f, t1_m;
	public static boolean slot0;
	public static boolean slot1;
	public static boolean slot2;
	public static boolean slot3;
	public static boolean slot4;
	
	public SaveSlotLoader(SavingWorld savingWorldIn) 
	{
		this.savingWorld = savingWorldIn;
	}
	
	/**
	 * Fully save the world to empty slot.
	 */
	public void fullSaveWorldToSlot()
	{
		this.createSaveSlot(0);
		this.saveWorldToSlot(0, t1_n, t1_f, t1_m);
	}
	
	public void createSaveSlot(int id)
	{
		this.slotList.add(new SaveSlot(id));
	}
	
	public void saveWorldToSlot(int indexSlot, Text name, Text file, Text gamemode)
	{
		if(this.slotList.get(indexSlot).isCreated)
		{
			if(this.slotList.get(indexSlot).isEmpty)
			{
				//this.addTextInfo(name, file, gamemode, 0f, 0f, 0f);
				//this.addGuiComponets();
				//this.addWorldData();
			}
		}
	}
	
	public void addTextInfo(Text worldName, Text saveFile, Text gamemode, float y1, float y2, float y3)
	{
		worldName = new Text(this.savingWorld.world.worldName, 0.5F, y1, true);
		saveFile = new Text(this.savingWorld.world.worldName, 0.5F, y2, true);
		gamemode = new Text(this.savingWorld.stringModes, 0.5F, y3, true);
		if(Pages.isLoadWorldPage)
		{
			TextLoader.addText(worldName);
			TextLoader.addText(saveFile);
			TextLoader.addText(gamemode);
		}
		if(!Pages.isLoadWorldPage)
		{
			if(worldName != null && saveFile != null && gamemode != null)
			{
				TextLoader.removeText(worldName);
				TextLoader.removeText(saveFile);
				TextLoader.removeText(gamemode);
			}
		}
	}
	
	/**
	 * Render text on the screen. Called from update loop.
	 */
	public static void renderText()
	{
		if(Pages.isLoadWorldPage)
		{
			t1_n.r(true); t1_f.r(true); t1_m.r(true);
		} else {
			t1_n.r(false); t1_f.r(false); t1_m.r(false);
		}
	}
	
	public static void loadTextWorldData()
	{
		if(!slot0)
		{
			t1_n = new Text("", 0.4F, 0.12F, false, false, 1.5F, 1F, 1F, 1F);
			t1_f = new Text("", 0.4F, 0.17F, false, false, 1.1F, 0.77F, 0.77F, 0.77F);
			t1_m = new Text("", 0.4F, 0.20F, false, false, 1.1F, 0.77F, 0.77F, 0.77F);
		} else {
			t1_n = new Text(savingWorld.world.worldName, 0.4F, 0.12F, false, false, 1.5F, 1F, 1F, 1F);
			t1_f = new Text("Save how: " + savingWorld.world.worldName, 0.4F, 0.17F, false, false, 1.1F, 0.77F, 0.77F, 0.77F);
			t1_m = new Text(savingWorld.stringModes, 0.4F, 0.20F, false, false, 1.1F, 0.77F, 0.77F, 0.77F);
		}
		
		TextData.loadPreTextDataSaveSlots();
	}
	
	public static void updateTextSaveData()
	{
		TextLoader.updateText(t1_n, savingWorld.world.worldName);
		TextLoader.updateText(t1_f, "Save how: " + savingWorld.world.worldName);
		TextLoader.updateText(t1_m, "Gamemode: " + savingWorld.stringModes);
	}

}
