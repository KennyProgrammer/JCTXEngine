package com.kenny.craftix.client.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.gui.button.GuiAbstractButton;
import com.kenny.craftix.client.gui.button.GuiButtons;
import com.kenny.craftix.client.gui.button.IButton;
import com.kenny.craftix.client.scenes.MainMenuSceneOld;
import com.kenny.craftix.client.settings.InGameSettings;

public class GuiAudio extends GuiBackground implements IGui 
{
	public List<GuiQuad> guisBackground = new ArrayList<GuiQuad>();
	public List<GuiQuad> guisAudioButtons = new ArrayList<GuiQuad>();
	
	/**All init of Audio Gui Buttons-textures*/;
	public GuiAbstractButton button_soundOn;
	public GuiAbstractButton button_soundOff;
	
	private MainMenuSceneOld scene;
	
	public void loadAudioScreen(Craftix craftixIn, MainMenuSceneOld scene)
	{
		this.cx = craftixIn;
		this.scene = scene;
		this.drawGuis();
		this.drawGuiButtons();
	}
	
	public void loadAudioInGameScreen(Craftix craftixIn)
	{
		this.cx = craftixIn;
		this.drawGuis();
		this.drawGuiButtons();
	}
	
	@Override
	public void drawGuis() 
	{
		this.guisBackground.add(new GuiQuad(GUI_LOCATION + "menu/options", 0.0f, 0.75f, 0.40f, 0.45f));
		this.guisBackground.add(new GuiQuad(GUI_LOCATION + "menu/gui_background_small_rot", -0.21f, -0.35f, 0.70f, 0.60f));
		this.guisBackground.add(new GuiQuad(GUI_LOCATION + "menu/gui_border", -0.73f, 0.63f, 0.20f, 0.10f));
	}

	@Override
	public void drawGuiButtons() 
	{
		/**
		 * This is custom X and Y values for a for multi-size buttons.
		 */
		float x = 0f; float y = 0f;
		float posY = 0f;

		if(InGameSettings.guiScaleSmallIn)
		{
			x = 0.010f; y = 0.015f;
			posY = 0.01f;
		}
		if(InGameSettings.guiScaleMediumIn)
		{
			x = 0.005f; y = 0.010f;
		}
		if(InGameSettings.guiScaleLargeIn)
		{
			x = 0f; y = 0f;
		}
		
		this.b.b29 = new GuiButtons(29, BUTTON_RECT_TEXTURE, 0.68f, -0.72f, 2, "audio.game.back") 
		{
			public void onClick(GuiButtons button) 
			{
				super.onClick(button);
				if(button.buttonId == 29)
				{
				
				
					GuiRenderManager.renderAudioMenu = false;
					GuiRenderManager.renderOptionsInGame = true;
				}
			}
		};
		
		this.b.b30 = new GuiAbstractButton(30, BUTTON_RECT_TEXTURE, new Vector2f(0.69f,-0.72f), -0.08f, 0f, false) 
		{
			public void onClick(IButton button) 
			{
				GuiRenderManager.renderOptionsMenu = true;
				GuiRenderManager.renderAudioMenu = false;
				scene.cx.saveAllGameOptions();
			}
		};
		
		this.button_soundOn = new GuiAbstractButton("guis/menu/button_yes", 
				new Vector2f(-0.49f, 0.05f + posY), this.buttonScaleM_x - x, this.buttonScaleM_y - y, true) 
		{
			public void onClick(IButton button) 
			{
				InGameSettings inGameSettings = new InGameSettings();
				button.hide(guisAudioButtons);
				inGameSettings.disableAudio();
				GuiYesNo.isYesOptionAudio = InGameSettings.useAudioIn;
				button_soundOff.show(guisAudioButtons);
			}
			
		};
		
		this.button_soundOff = new GuiAbstractButton("guis/menu/button_no", 
				new Vector2f(-0.49f, 0.05f + posY), this.buttonScaleM_x - x, this.buttonScaleM_y - y, true) 
		{
			public void onClick(IButton button) 
			{
				InGameSettings inGameSettings = new InGameSettings();
				button.hide(guisAudioButtons);
				inGameSettings.enableAudio();
				GuiYesNo.isYesOptionAudio = InGameSettings.useAudioIn;
				button_soundOn.show(guisAudioButtons);
			}
			
		};
		if(GuiYesNo.isYesOptionAudio)
		{
			this.button_soundOn.show(guisAudioButtons);
		}else{
			this.button_soundOff.show(guisAudioButtons);
		}
		
		this.b.b30.show(this.guisBackground);

	}


	public void renderScreen() 
	{
		if(this.scene.cx.status.isGameMenu() && !this.scene.cx.status.isGameWorld())
		{
			this.b.b30.update();
		}
		if(!this.scene.cx.status.isGameMenu() && this.scene.cx.status.isGameWorld())
		{
			this.b.b29.renderButton(true, false);
		}
	}
	
	@Override
	public void addToList() 
	{
	}

	@Override
	public void updateButtons() 
	{
		if(GuiYesNo.isYesOptionAudio == true)
		{this.button_soundOn.updateMulti(); this.button_soundOff.updateMulti();} 
		if(GuiYesNo.isYesOptionAudio == false)
		{this.button_soundOff.updateMulti(); this.button_soundOn.updateMulti();}
	}
	
}
