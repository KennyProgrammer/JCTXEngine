package com.kenny.craftix.client.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.gui.button.GuiButton;
import com.kenny.craftix.client.scenes.MainMenuScene;
import com.kenny.craftix.client.settings.InGameSettings;
import com.kenny.craftix.client.text.TextLanguage;
import com.kenny.craftix.client.text.TextLoader;
import static com.kenny.craftix.client.language.Language.*;

public class GuiGraphics extends GuiBackground
{
	public List<GuiQuad> textures = new ArrayList<GuiQuad>();
	protected static GuiButton b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13;
	public static GuiButton b14, b15, b16, b17;
	public MainMenuScene scene;
	public static boolean isMorePostProcessing;
	
	
	@Override
	public void initGui(Craftix craftixIn) 
	{
		super.initGui(craftixIn);
		this.scene = craftixIn.menuScene;
		this.textures.add(new GuiQuad(GUI_LOCATION + "menu/gui_border", 0.05f, 0.83f, this.width / 4, this.height / 10));
		this.textures.add(new GuiQuad(GUI_LOCATION + "menu/background", 0.0f, -0.10f, this.width / 1.2f, this.height / 1.2f));
		
		b1 = new GuiButton(39, BUTTON_RECT_TEXTURE, 0.68f, -0.72f, 2, "button.back");
		b2 = new GuiButton(40, BUTTON_RECT_TEXTURE, -0.3f, 0.5f, 0, "button.fullscreen");
		b3 = new GuiButton(41, BUTTON_RECT_TEXTURE, 0.3f, 0.5f, 0, "button.usePostEffects");
		b4 = new GuiButton(42, BUTTON_RECT_TEXTURE, -0.3f, 0.3f, 0, "button.guiScale");
		b5 = new GuiButton(43, BUTTON_RECT_TEXTURE, 0.3f, 0.3f, 0, "button.fog");
		b6 = new GuiButton(44, BUTTON_RECT_TEXTURE, -0.3f, 0.1f, 0, "button.renderDisance");
		b7 = new GuiButton(45, BUTTON_RECT_TEXTURE, 0.3f,  0.1f, 0, "button.renderSkybox");
		b8 = new GuiButton(46, BUTTON_RECT_TEXTURE, -0.3f, -0.1f, 0, "button.renderWater");
		b9 = new GuiButton(47, BUTTON_RECT_TEXTURE, 0.3f, -0.1f, 0, "button.shadows");
		b10 = new GuiButton(48, BUTTON_RECT_TEXTURE, -0.3f, -0.3f, 0, "button.limitFps");
		b11 = new GuiButton(49, BUTTON_RECT_TEXTURE, 0.3f, -0.3f, 0, "button.vSync");
		b12 = new GuiButton(50, BUTTON_RECT_TEXTURE, -0.3f, -0.5f, 0, "button.particles");
		b13 = new GuiButton(110, GUI_LOCATION + "menu/button_question", 0.625F, 0.5F, 3, "button.postEnableOther");
		b14 = new GuiButton(111, BUTTON_RECT_TEXTURE, 0f, 0.35f, 0, "button.contrast");
		b15 = new GuiButton(112, BUTTON_RECT_TEXTURE, 0f, 0.15f, 0, "button.brightFilter");
		b16 = new GuiButton(113, BUTTON_RECT_TEXTURE, 0.3f, -0.5f, 0, "button.brightness");
		b17 = new GuiButton(114, BUTTON_RECT_TEXTURE, 0f, -0.05f, 0, "button.gaussianBlur");
	}
	
	@Override
	protected void buttonClick(GuiButton button) throws IOException 
	{
		super.buttonClick(button);
		int minValue = 1;
		
		if(button.id == 39)
		{
			GuiGraphics.removePage();
			Pages.isGraphicsPage = false;
			Pages.isOptionsPage = true;
			GuiOptions.addPage();
			InGameSettings.saveOptions();
		}
		
		if(button.id == 40)
		{
			if(!InGameSettings.useFullscreenIn)
			{
				InGameSettings.useFullscreenIn = true;
			} else {
				InGameSettings.useFullscreenIn = false;
			}
			TextLoader.updateText(TextLanguage.t47, OPTION_FULLSCREEN + ": " + InGameSettings.useFullscreenIn);
			TextLanguage.t45.r(true);
		}
		
		if(button.id == 41)
		{
			if(!InGameSettings.usePostEffectsIn)
			{
				InGameSettings.usePostEffectsIn = true;
				buttonList.add(b13);
			} else {
				InGameSettings.usePostEffectsIn = false;
				buttonList.remove(b13);
			}
			TextLoader.updateText(TextLanguage.t48, OPTION_POST_EFFECTS + ": " + InGameSettings.usePostEffectsIn);
			TextLanguage.t48.r(true);
		}
		
		if(button.id == 44)
		{
			if(InGameSettings.renderDistanceIn != 12)
			{
				InGameSettings.renderDistanceIn = InGameSettings.renderDistanceIn + minValue;
			} else {
				InGameSettings.renderDistanceIn = minValue;
			}
			if(InGameSettings.renderDistanceIn == 12)
				TextLoader.updateText(TextLanguage.t51, OPTION_RENDER_DISTANCE + ": " + GUI_MAX);
			else if(InGameSettings.renderDistanceIn == minValue)
				TextLoader.updateText(TextLanguage.t51, OPTION_RENDER_DISTANCE + ": " + GUI_MIN);
			else
				TextLoader.updateText(TextLanguage.t51, OPTION_RENDER_DISTANCE + ": " + InGameSettings.renderDistanceIn);
		}
		
		if(button.id == 42)
		{
			 if(InGameSettings.guiScaleIn == 0)
				 InGameSettings.guiScaleIn = 1;
			 else if(InGameSettings.guiScaleIn == 1)
				 InGameSettings.guiScaleIn = 2;
			 else if(InGameSettings.guiScaleIn == 2)
				 InGameSettings.guiScaleIn = 0;
				
			TextLoader.updateText(TextLanguage.t49, OPTION_GUI_SCALE +  ": " + InGameSettings.guiScaleIn);
			TextLanguage.t45.r(true);
		}
		
		if(button.id == 45)
		{
			if(!InGameSettings.renderSkyBoxIn)
			{
				InGameSettings.renderSkyBoxIn = true;
			} else {
				InGameSettings.renderSkyBoxIn = false;
			}
			TextLoader.updateText(TextLanguage.t52, OPTION_RENDER_SKYBOX + ": " + InGameSettings.renderSkyBoxIn);
		}
		
		if(button.id == 46)
		{
			if(!InGameSettings.renderWaterIn)
			{
				InGameSettings.renderWaterIn = true;
			} else {
				InGameSettings.renderWaterIn = false;
			}
			TextLoader.updateText(TextLanguage.t53, OPTION_RENDER_WATER + ": " + InGameSettings.renderWaterIn);
		}
	
		if(button.id == 47)
		{
			if(!InGameSettings.useShadowsIn)
			{
				InGameSettings.useShadowsIn = true;
			} else {
				InGameSettings.useShadowsIn = false;
			}
			TextLoader.updateText(TextLanguage.t54, OPTION_SHADOWS + ": " + InGameSettings.useShadowsIn);
		}
		
		if(button.id == 49)
		{
			if(!InGameSettings.useVSync)
			{
				InGameSettings.useVSync = true;
			} else {
				InGameSettings.useVSync = false;
			}
			TextLoader.updateText(TextLanguage.t56, OPTION_VSYNC + ": " + InGameSettings.useVSync);
		}
		
		if(button.id == 50)
		{
			if(!InGameSettings.renderParticlesIn)
			{
				InGameSettings.renderParticlesIn = true;
			} else {
				InGameSettings.renderParticlesIn = false;
			}
			TextLoader.updateText(TextLanguage.t57, OPTION_PARTICLES + ": " + InGameSettings.renderParticlesIn);
		}
		
		if(button.id == 110)
		{
			GuiGraphics.removePage();
			isMorePostProcessing = true;
			buttonList.add(b14);
			buttonList.add(b15);
			buttonList.add(b17);
		}
		
		if(button.id == 111)
		{
			if(!InGameSettings.useContrastIn)
			{
				InGameSettings.useContrastIn = true;
			} else {
				InGameSettings.useContrastIn = false;
			}
			TextLoader.updateText(TextLanguage.t129, OPTION_POST_EFFECTS_MORE_CONTRAST + ": " + InGameSettings.useContrastIn);
		}
		
		if(button.id == 112)
		{
			if(!InGameSettings.useBloomIn)
			{
				InGameSettings.useBloomIn = true;
			} else {
				InGameSettings.useBloomIn = false;
			}
			TextLoader.updateText(TextLanguage.t130, OPTION_POST_EFFECTS_MORE_BRIGHT_FILTER + ": " + InGameSettings.useBloomIn);
		}
		
		if(button.id == 113)
		{
			if(InGameSettings.brightnessIn != 6)
			{
				InGameSettings.brightnessIn = InGameSettings.brightnessIn + minValue;
			} else {
				InGameSettings.brightnessIn = minValue;
			}
			if(InGameSettings.brightnessIn == 6)
				TextLoader.updateText(TextLanguage.t57b, OPTION_BRIGHTNESS + ": " + GUI_MAX);
			else if(InGameSettings.brightnessIn == minValue)
				TextLoader.updateText(TextLanguage.t57b, OPTION_BRIGHTNESS + ": " + GUI_MIN);
			else
				TextLoader.updateText(TextLanguage.t57b, OPTION_BRIGHTNESS + ": " + InGameSettings.brightnessIn);
			
		}
		
		if(button.id == 114)
		{
			if(!InGameSettings.useGaussianBlurIn)
			{
				InGameSettings.useGaussianBlurIn = true;
			} else {
				InGameSettings.useGaussianBlurIn = false;
			}
			TextLoader.updateText(TextLanguage.t131, OPTION_POST_EFFECTS_MORE_GAUSSIAN_BLUR + ": " + InGameSettings.useGaussianBlurIn);
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
		buttonList.add(b4);
		buttonList.add(b5);
		buttonList.add(b6);
		buttonList.add(b7);
		buttonList.add(b8);
		buttonList.add(b9);
		buttonList.add(b10);
		buttonList.add(b11);
		buttonList.add(b12);
		buttonList.add(b16);
		if(InGameSettings.usePostEffectsIn)
			buttonList.add(b13);
		if(isMorePostProcessing)
		{
			
		}
	}
	
	/**
	 * Remove from the list buttons.
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
		buttonList.remove(b16);
		if(InGameSettings.usePostEffectsIn)
			buttonList.remove(b13);
		if(isMorePostProcessing)
		{
			
		}
	}
}
