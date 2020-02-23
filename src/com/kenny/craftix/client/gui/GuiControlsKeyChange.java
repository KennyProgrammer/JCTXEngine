package com.kenny.craftix.client.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.gui.button.GuiButton;
import com.kenny.craftix.client.settings.InGameSettings;
import com.kenny.craftix.client.text.Text;
import com.kenny.craftix.client.text.TextChat;

public class GuiControlsKeyChange extends GuiBackground
{
	protected static GuiButton b1, b2, b3;
	public List<GuiQuad> textures = new ArrayList<GuiQuad>();
	public TextChat textChat = new TextChat();
	
	@Override
	public void initGui(Craftix craftixIn)
	{
		super.initGui(craftixIn);
		this.textures.add(new GuiQuad(GUI_LOCATION + "menu/background", 0.0f, 0.0f, this.width / 1.2f, this.height / 1f));
		this.textures.add(new GuiQuad(GUI_LOCATION + "menu/background", 0.0f, 0.0f, this.width / 2.4f, this.height / 8));
		this.textures.add(new GuiQuad(GUI_LOCATION + "menu/gui_border", -0.55f, 0.83f, this.width / 4, this.height / 10));
		
		b1 = new GuiButton(77, BUTTON_RECT_TEXTURE, 0.0f, -0.72f, 2, "button.back");
		b2 = new GuiButton(78, BUTTON_RECT_TEXTURE, 0.0f, -0.52f, 2, "button.save");

	}
	
	@Override
	protected void buttonClick(GuiButton button) throws IOException 
	{
		super.buttonClick(button);
		
		if(button.id == 77)
		{
			GuiControlsKeyChange.removePage();
			Pages.isControlsKeyChangePage = false;
			Pages.isControlsPage = true;
			GuiControls.addPage();
			this.cleanUpOptionBuffers();
		}
		
		if(button.id == 78)
		{
			GuiControlsKeyChange.removePage();
			Pages.isControlsKeyChangePage = false;
			Pages.isControlsPage = true;
			GuiControls.addPage();
			InGameSettings.saveOptions();
			this.cleanUpOptionBuffers();
		}
	}
	
	public void cleanUpOptionBuffers()
	{
		GuiControls.keyForward = false;
		GuiControls.keyBackward = false;
		GuiControls.keyLeftward = false;
		GuiControls.keyRightward = false;
		GuiControls.keyJump = false;
		GuiControls.keyRun = false;
		GuiControls.keyPause = false;
	}
	
	public void updateOption()
	{
		int currentOption = InGameSettings.keyboardMovementForwardIn;
		int selectOptionBuffer = currentOption;
		if(currentOption == selectOptionBuffer)
			this.changeOption(currentOption, TextChat.currentOption, true);
	}
	
	public void changeOption(int currentOption, Text t, boolean saveOption)
	{
		char c = Keyboard.getEventCharacter();
		int changeOption = 0;
		if(Keyboard.getEventKey() == 0 && c >= ' ' || Keyboard.getEventKeyState())
		{
			changeOption = Keyboard.getEventKey();
			currentOption = changeOption;
			this.textChat.parseUpdateIntToStringLetters(t, changeOption);
			if(saveOption)
				InGameSettings.saveOptions();
		}
	}
	
	public void changeOptionForward(Text t1)
	{
		char c = Keyboard.getEventCharacter();
		int currentKey = Keyboard.getEventKey();
		InGameSettings.keyboardMovementForwardIn = currentKey;
		if(Keyboard.getEventKey() == 0 && c >= ' ' || Keyboard.getEventKeyState())
		{
			this.textChat.parseUpdateIntToStringLetters(t1, currentKey);
				InGameSettings.saveOptions();
		}
	}
	
	public void changeOptionBackward(Text t1)
	{
		char c = Keyboard.getEventCharacter();
		int currentKey = Keyboard.getEventKey();
		InGameSettings.keyboardMovementBackwardIn = currentKey;
		if(Keyboard.getEventKey() == 0 && c >= ' ' || Keyboard.getEventKeyState())
		{
			this.textChat.parseUpdateIntToStringLetters(t1, currentKey);
				InGameSettings.saveOptions();
		}
	}
	
	public void changeOptionLeftward(Text t1)
	{
		char c = Keyboard.getEventCharacter();
		int currentKey = Keyboard.getEventKey();
		InGameSettings.keyboardMovementLeftwardIn = currentKey;
		if(Keyboard.getEventKey() == 0 && c >= ' ' || Keyboard.getEventKeyState())
		{
			this.textChat.parseUpdateIntToStringLetters(t1, currentKey);
				InGameSettings.saveOptions();
		}
	}
	
	public void changeOptionRightward(Text t1)
	{
		char c = Keyboard.getEventCharacter();
		int currentKey = Keyboard.getEventKey();
		InGameSettings.keyboardMovementRightwardIn = currentKey;
		if(Keyboard.getEventKey() == 0 && c >= ' ' || Keyboard.getEventKeyState())
		{
			this.textChat.parseUpdateIntToStringLetters(t1, currentKey);
				InGameSettings.saveOptions();
		}
	}
	
	public void changeOptionJump(Text t1)
	{
		char c = Keyboard.getEventCharacter();
		int currentKey = Keyboard.getEventKey();
		InGameSettings.keyboradMovementJumpIn = currentKey;
		if(Keyboard.getEventKey() == 0 && c >= ' ' || Keyboard.getEventKeyState())
		{
			this.textChat.parseUpdateIntToStringLetters(t1, currentKey);
				InGameSettings.saveOptions();
		}
	}
	
	public void changeOptionRun(Text t1)
	{
		char c = Keyboard.getEventCharacter();
		int currentKey = Keyboard.getEventKey();
		InGameSettings.keyboradMovementRunIn = currentKey;
		if(Keyboard.getEventKey() == 0 && c >= ' ' || Keyboard.getEventKeyState())
		{
			this.textChat.parseUpdateIntToStringLetters(t1, currentKey);
				InGameSettings.saveOptions();
		}
	}
	
	public void changeOptionPause(Text t1)
	{
		char c = Keyboard.getEventCharacter();
		int currentKey = Keyboard.getEventKey();
		InGameSettings.keyboardPauseIn = currentKey;
		if(Keyboard.getEventKey() == 0 && c >= ' ' || Keyboard.getEventKeyState())
		{
			this.textChat.parseUpdateIntToStringLetters(t1, currentKey);
				InGameSettings.saveOptions();
		}
	}
	
	public void isSameOption(int currentKey)
	{
		if(currentKey == InGameSettings.keyboardMovementForwardIn || currentKey == InGameSettings.keyboardMovementBackwardIn || 
				currentKey == InGameSettings.keyboardMovementLeftwardIn || currentKey == InGameSettings.keyboardMovementRightwardIn)
		{
			
		}
	}
	
	/**
	 * Add buttons to the button list.
	 */
	public static void addPage()
	{
		buttonList.add(b1);
		buttonList.add(b2);
	}
	
	/**
	 * Remove button from the button list.
	 */
	public static void removePage()
	{
		buttonList.remove(b1);
		buttonList.remove(b2);
	}
}
