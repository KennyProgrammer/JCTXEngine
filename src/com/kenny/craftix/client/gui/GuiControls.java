package com.kenny.craftix.client.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.gui.button.GuiButton;
import com.kenny.craftix.client.settings.InGameSettings;
import com.kenny.craftix.client.text.TextChat;

import static com.kenny.craftix.client.text.TextLanguage.*;

public class GuiControls extends GuiBackground
{
	public List<GuiQuad> textures = new ArrayList<GuiQuad>();
	public TextChat textChat = new TextChat();
	protected static GuiButton b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14;
	/**Create a visual screen and move this screen with all components on axis Y.*/
	public float scrollY, scrollbY;
	protected GuiControlsKeyChange guiControlsKeyChange = new GuiControlsKeyChange();
	public int currentOption = 0;
	public static boolean keyForward = false;
	public static boolean keyBackward = false;
	public static boolean keyLeftward = false;
	public static boolean keyRightward = false;
	public static boolean keyJump = false;
	public static boolean keyRun = false;
	public static boolean keyPause = false;
	
	@Override
	public void initGui(Craftix craftixIn) 
	{
		super.initGui(craftixIn);
		this.textures.add(new GuiQuad(GUI_LOCATION + "menu/background", 0.0f, 0.0f, this.width / 1.2f, this.height / 1f));
		this.textures.add(new GuiQuad(GUI_LOCATION + "menu/gui_border", -0.55f, 0.83f, this.width / 4, this.height / 10));
		
		b1 = new GuiButton(55, BUTTON_RECT_TEXTURE, 0.68f, -0.72f, 2, "button.back");
		b2 = new GuiButton(56, GUI_LOCATION + "menu/button_up", 0.61f, -0.5f, 1, "button.up");
		b3 = new GuiButton(57, GUI_LOCATION + "menu/button_down", 0.75f, -0.5f, 1, "button.down");
		b4 = new GuiButton(58, BUTTON_QUAD_TEXTURE, 0.3f, 0.75f, 3, "button.control.forward");
		b5 = new GuiButton(59, BUTTON_QUAD_TEXTURE, 0.3f, 0.55f, 3, "button.control.backward");
		b6 = new GuiButton(60, BUTTON_QUAD_TEXTURE, 0.3f, 0.35f, 3, "button.control.leftward");
		b7 = new GuiButton(61, BUTTON_QUAD_TEXTURE, 0.3f, 0.15f, 3, "button.control.rightward");
		b8 = new GuiButton(62, BUTTON_QUAD_TEXTURE, 0.3f, -0.05f, 3, "button.control.jump");
		b9 = new GuiButton(63, BUTTON_QUAD_TEXTURE, 0.3f, -0.25f, 3, "button.control.run");
		b10 = new GuiButton(64, BUTTON_QUAD_TEXTURE, 0.3f, -0.45f, 3, "button.control.inventory");
		b11 = new GuiButton(65, BUTTON_QUAD_TEXTURE, 0.3f, -0.65f, 3, "button.control.pickup");
		b12 = new GuiButton(66, BUTTON_QUAD_TEXTURE, 0.3f, -0.85f, 3, "button.control.pause");
		b13 = new GuiButton(67, BUTTON_QUAD_TEXTURE, 0.3f, -1.05f, 3, "button.control.playerstab");
		b14 = new GuiButton(68, BUTTON_QUAD_TEXTURE, 0.3f, -1.15f, 3, "button.control.infopage");
		
	}
	
	@Override
	protected void buttonClick(GuiButton button) throws IOException 
	{
		super.buttonClick(button);
		char c = Keyboard.getEventCharacter();
		
		if(button.id == 55)
		{
			GuiControls.removePage();
			Pages.isControlsPage = false;
			Pages.isOptionsPage = true;
			GuiOptions.addPage();
		}
		
		if(button.id == 56)
		{
			this.scrollScreen();
			if(this.scrollY >= 0.0f)
				this.scrollY = 0.0f;
			else
				this.scrollY += 0.05f;
		}
		
		if(button.id == 57)
		{
			this.scrollScreen();
			if(this.scrollY <= -0.3f)
				this.scrollY = -0.3f;
			else
				this.scrollY -= 0.05f;	
		}
		
		if(button.id == 58)
		{	
			GuiControls.removePage();
			Pages.isControlsPage = false;
			Pages.isControlsKeyChangePage = true;
			GuiControlsKeyChange.addPage();
			GuiControls.keyForward = true;
			this.textChat.parseUpdateIntToStringLetters(this.textChat.currentOption, InGameSettings.keyboardMovementForwardIn);
			this.currentOption = InGameSettings.keyboardMovementForwardIn;
		}
		
		if(button.id == 59)
		{
			GuiControls.removePage();
			Pages.isControlsPage = false;
			Pages.isControlsKeyChangePage = true;
			GuiControlsKeyChange.addPage();
			GuiControls.keyBackward = true;
			this.textChat.parseUpdateIntToStringLetters(this.textChat.currentOption, InGameSettings.keyboardMovementBackwardIn);
			this.currentOption = InGameSettings.keyboardMovementBackwardIn;
		}
		
		if(button.id == 60)
		{
			GuiControls.removePage();
			Pages.isControlsPage = false;
			Pages.isControlsKeyChangePage = true;
			GuiControlsKeyChange.addPage();
			GuiControls.keyLeftward = true;
			this.textChat.parseUpdateIntToStringLetters(this.textChat.currentOption, InGameSettings.keyboardMovementLeftwardIn);
			this.currentOption = InGameSettings.keyboardMovementLeftwardIn;
		}
		
		if(button.id == 61)
		{
			GuiControls.removePage();
			Pages.isControlsPage = false;
			Pages.isControlsKeyChangePage = true;
			GuiControlsKeyChange.addPage();
			GuiControls.keyRightward = true;
			this.textChat.parseUpdateIntToStringLetters(this.textChat.currentOption, InGameSettings.keyboardMovementRightwardIn);
			this.currentOption = InGameSettings.keyboardMovementRightwardIn;
		}
		
		if(button.id == 62)
		{
			GuiControls.removePage();
			Pages.isControlsPage = false;
			Pages.isControlsKeyChangePage = true;
			GuiControlsKeyChange.addPage();
			GuiControls.keyJump = true;
			this.textChat.parseUpdateIntToStringLetters(this.textChat.currentOption, InGameSettings.keyboradMovementJumpIn);
			this.currentOption = InGameSettings.keyboradMovementJumpIn;
		}
		
		if(button.id == 63)
		{
			GuiControls.removePage();
			Pages.isControlsPage = false;
			Pages.isControlsKeyChangePage = true;
			GuiControlsKeyChange.addPage();
			GuiControls.keyRun = true;
			this.textChat.parseUpdateIntToStringLetters(this.textChat.currentOption, InGameSettings.keyboradMovementRunIn);
			this.currentOption = InGameSettings.keyboradMovementRunIn;
		}
		
		if(button.id == 66)
		{
			GuiControls.removePage();
			Pages.isControlsPage = false;
			Pages.isControlsKeyChangePage = true;
			GuiControlsKeyChange.addPage();
			GuiControls.keyPause = true;
			this.textChat.parseUpdateIntToStringLetters(this.textChat.currentOption, InGameSettings.keyboardPauseIn);
			this.currentOption = InGameSettings.keyboardPauseIn;
		}
		
		this.guiControlsKeyChange.buttonClick(button);
	}
	
	/**
	 * Move all components use the scroll.
	 */
	public void scrollScreen()
	{
		float x = 0.35f;
		float xB = 0.3f;
		t76.setPosition(x, scrollY + 0.1f);
		t77.setPosition(x, scrollY + 0.2f);
		t78.setPosition(x, scrollY + 0.3f);
		t79.setPosition(x, scrollY + 0.4f);
		t80.setPosition(x, scrollY + 0.5f);
		t81.setPosition(x, scrollY + 0.6f);
		t82.setPosition(x, scrollY + 0.7f);
		t83.setPosition(x, scrollY + 0.8f);
		t84.setPosition(x, scrollY + 0.9f);
		t85.setPosition(x, scrollY + 1.0f);
		t86.setPosition(x, scrollY + 1.1f);
		b4.setPosition(xB, -scrollbY + 0.75f);
		b5.setPosition(xB, -scrollbY + 0.55f);
		b6.setPosition(xB, -scrollbY + 0.35f);
		b7.setPosition(xB, -scrollbY + 0.15f);
		b8.setPosition(xB, -scrollbY - 0.05f);
		b9.setPosition(xB, -scrollbY - 0.25f);
		b10.setPosition(xB, -scrollbY - 0.45f);
		b11.setPosition(xB, -scrollbY - 0.65f);
		b12.setPosition(xB, -scrollbY - 0.85f);
		b13.setPosition(xB, -scrollbY - 1.05f);
		b14.setPosition(xB, -scrollbY - 1.25f);
		this.textChat.scrollLetters(this.scrollY);
	}
	
	/**
	 * Add button to the list of buttons.
	 */
	public static void addPage()
	{
		buttonList.add(b1);
		buttonList.add(b2);
		buttonList.add(b3);
		buttonList.add(b4);
		buttonList.add(b5);
		buttonList.add(b6);
		buttonList.add(b7);
		buttonList.add(b8);
		buttonList.add(b9);
		buttonList.add(b10);
		buttonList.add(b11);
		buttonList.add(b12);
		buttonList.add(b13);
		buttonList.add(b14);
	}
	
	/**
	 * Remove button from the list of buttons.
	 */
	public static void removePage()
	{
		buttonList.remove(b1);
		buttonList.remove(b2);
		buttonList.remove(b3);
		buttonList.remove(b4);
		buttonList.remove(b5);
		buttonList.remove(b6);
		buttonList.remove(b7);
		buttonList.remove(b8);
		buttonList.remove(b9);
		buttonList.remove(b10);
		buttonList.remove(b11);
		buttonList.remove(b12);
		buttonList.remove(b13);
		buttonList.remove(b14);
	}
}
