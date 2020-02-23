package com.kenny.craftix.client.gui.button;

import java.io.IOException;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.gui.GuiBackground;
import com.kenny.craftix.client.gui.GuiRenderManager;
import com.kenny.craftix.client.gui.GuiRenderer;
import com.kenny.craftix.client.gui.GuiScale;
import com.kenny.craftix.client.loader.TexturesLoader;
import com.kenny.craftix.client.renderer.GlHelper;
import com.kenny.craftix.client.renderer.GlHelper.Blend;
import com.kenny.craftix.client.renderer.textures.TextureManager;
import com.kenny.craftix.client.settings.InGameSettings;
import com.kenny.craftix.client.text.TextLanguage;
import com.kenny.craftix.utils.math.Maths;

public class GuiButton extends GuiBackground
{
	/**Position x of gui button.*/
	public float x;
	/**Position y of gui button.*/
	public float y;
	/**Width button in pixels.*/
	public float width;
	/**Height button in pixels.*/
	public float height;
	public Vector2f buttonScale;
	public Vector2f buttonPos;
	/**File name in the "assets" folder.*/
	public int textureId;
	public int id;
	/**Loader data about button texture in the Vbo.*/
	public TexturesLoader textureLoader = new TexturesLoader();
	public GuiRenderer buttonRenderer = new GuiRenderer(this.cx);
	public boolean visible;
	public boolean enable;
	public String text;
	public boolean renderer;
	protected boolean hovered;
	public boolean nextButtonTouches;
	public boolean onButton;
	/**Necessary to control the number of clicks or taps on the screen.*/
	public int clickValue;
	/**When an event occurs when the button is clicked.*/
	public int eventButton;
	private long lastMouseEvent;
	public GuiScale scaleManager = new GuiScale();
	public TextLanguage textLang = new TextLanguage();
	
	/**
	 * Setup the gui button component.
	 * 
	 * @param buttonIdIn - id of the current button.
	 * @param fileIn - file texture of the button.
	 * @param x - position X of the button.
	 * @param y - position Y of the button.
	 * @param typeIn - type of the button. 0 type is standard rectangle button.
	 * 1 type is quad button. 2 type its small rectangle button. 3 type its small
	 * quad button. 4 type is a scroll bar button.
	 * @param buttonName - name of the current button.
	 */
	public GuiButton(int buttonIdIn, String fileIn, float x, float y, int typeIn, String buttonName) 
	{
		float i = this.scaleManager.getScaleX();
		float j = this.scaleManager.getScaleY();
		float k = this.scaleManager.getScalePosX();
		float l = this.scaleManager.getScalePosY();
		
		
		if(typeIn == 0)
		{
			this.width = 0.26f - i;
			this.height = 0.08f - j;
		}
		if(typeIn == 1)
		{
			if(InGameSettings.guiScaleIn == 2)
			{
				 i += 0.03f;
			}
			this.width = 0.055f - i;
			this.height = 0.098f - j;
		}
		if(typeIn == 2)
		{
			this.width = 0.15f - i;
			this.height = 0.08f - j;
		}
		if(typeIn == 3)
		{
			if(InGameSettings.guiScaleIn == 2)
			{
				i += 0.03f;
			}
			this.width = 0.045f - i;
			this.height = 0.078f - j;
		}
		if(typeIn == 4)
		{
			this.width = 0.03f - i;
			this.height = 0.08f - j;
		}
		this.id = buttonIdIn;
		this.textureId = this.textureLoader.loadTexture(fileIn);
		this.x = x - k;
		this.y = y - l;
		this.buttonPos = new Vector2f(this.x, this.y);
		this.buttonScale = new Vector2f(this.width, this.height);
		this.visible = true;
		this.enable = true;
		this.text = buttonName;
	}
	
	/**
	 * Render the button into the screen. And check if button has been updated.
	 * @throws IOException 
	 */
	public void renderButton(boolean isUpdate, boolean isLink) throws IOException
	{
		if(this.visible)
		{
			this.buttonRenderer.shader.start();
			GlHelper.glBindVertexArray(this.buttonRenderer.quad.getVaoID());
			GlHelper.glEnableVertexAttribArray(0);
			
			TextureManager.activeTexture0();
			TextureManager.bindTexture2d(this.getTextureId());
			GlHelper.enableBlend();
			GlHelper.tryBlendFuncSeperate(Blend.SRC_ALPHA, Blend.ONE_MINUS_SRC_ALPHA, Blend.ONE, Blend.ZERO);
			GlHelper.glBlendFunction(Blend.SRC_ALPHA, Blend.ONE_MINUS_SRC_ALPHA);
			GlHelper.disableDepthTest();
			Matrix4f matrix = Maths.createTransformationMatrix(this.getPosition(), this.getScale());
			this.buttonRenderer.shader.loadTransformation(matrix);
			TextureManager.glDrawTrinangleStrips(0, this.buttonRenderer.quad.getVertexCount());
			
			GlHelper.enableDepthTest();
			GlHelper.disableBlend();
			GlHelper.glDisableVertexAttribArray(0);
			GlHelper.glBindVertexArray(0);
			this.buttonRenderer.shader.stop();
			
			int j = 100;
			
			if(!this.enable)
			{
				j = 105;
			}
			else if(this.hovered)
			{
				j = 160;
			}
			
			if(isUpdate && this.enable)
			{
				if(GuiRenderManager.renderLinkWarning)
				{
					if(isLink)
					{
						this.isUpdate(false);
					}
				}
				this.isUpdate(isUpdate);
			}
			
		}
		
	}
	
	/**
	 * Event onUpdate called in updates methods.
	 * @throws IOException
	 */
	public void isUpdate(boolean isUpdate) throws IOException
	{
		if(isUpdate && this.enable)
		{
			this.checkHover(this.cx);
			this.controllMouse();
		}
	}

	/**
	 * Checks the contact position of the mouse with the button positions.
	 * @param cx 
	 */
	public boolean checkHover(Craftix cx)
	{	
		boolean isHovered = false;
		Vector2f buttonPos = this.getPosition();
		Vector2f buttonScale = this.getScale();
		Vector2f mouseCoords = Craftix.getNormalizedMouseCoords();
		
		if(this.enable && buttonPos.y + buttonScale.y > -mouseCoords.y && buttonPos.y - buttonScale.y < -mouseCoords.y 
				&& buttonPos.x + buttonScale.x > mouseCoords.x && buttonPos.x - buttonScale.x < mouseCoords.x)
		{
			isHovered = true;
			if(!this.onButton)
			{
				this.playHoverAnimation(0.004f);
				this.onButton = true;
			}
		}
		else
		{
			if(this.onButton)
			{
				this.onButton = false;
				this.reload();
				//70.721, 77.629, 86.858
			}
		}

		return isHovered && this.enable && buttonPos.y + buttonScale.y > -mouseCoords.y && buttonPos.y - buttonScale.y < -mouseCoords.y 
				&& buttonPos.x + buttonScale.x > mouseCoords.x && buttonPos.x - buttonScale.x < mouseCoords.x;
	}
	
	/**
	 * On release event.
	 */
	public void onRelease(GuiButton button)
	{
	}
	
	/**
	 * Brings together all the methods responsible for click events 
	 * and sets limits for each of nick.
	 * 
	 * @throws IOException 
	 */
	public void controllMouse() throws IOException
	{
		int k = Mouse.getEventButton();
		
		if(this.enable && this.onButton)
		{
			if(Mouse.getEventButtonState())	
			{
				if(this.clickValue++ > 0)
				{
					return;
				}
				
				this.eventButton = k;
				this.lastMouseEvent = Craftix.getCurrentTime();
				this.mouseClickEvent(k);

			}
			else if(k != -1)
			{
	
				if(--this.clickValue > 0)
				{
					return;
				}
				
				this.eventButton = -1;
				this.onRelease(this);
			}
			else if(this.eventButton != -1 && this.lastMouseEvent > 0L)
			{
				long l = Craftix.getCurrentTime() - lastMouseEvent;
				this.onMoveClick(this, this.eventButton, l);
			}
		}
	}
	
	/**
	 * Called when a mouse button is pressed and the mouse is 
	 * moved around.
	 */
	public void onMoveClick(GuiButton button, int clickedMouseButton, long timeLastClick)
	{
	}
	
	protected void mouseDragged(Craftix cx)
	{
	}
	
	/**
     * Reloads the data about the button such as size, etc.
     */
    public void reload()
    {
    	float minusScaleFactor = 0.004f;
    	this.setScale(new Vector2f(this.buttonScale.x - minusScaleFactor, this.buttonScale.y - minusScaleFactor));
    }
	
	public void playHoverAnimation(float scaleFactor)
	{
		this.setScale(new Vector2f(buttonScale.x + scaleFactor, buttonScale.y + scaleFactor));
	}
   
    
    public int getTextureId()
	{
		return this.textureId;
	}
	
	public void setTextureId(int id)
	{
		this.textureId = id;
	}
	
	public Vector2f getPosition()
	{
		return this.buttonPos;
	}
	
	public void setPosition(int xPos, int yPos)
	{
		this.buttonPos = new Vector2f(xPos, yPos);
	}
	
	public void setPosition(float xPos, float yPos)
	{
		this.buttonPos = new Vector2f(xPos, yPos);
	}
	
	public Vector2f getScale() 
	{
		return buttonScale;
	}

	public void setScale(Vector2f scale)
	{
		this.buttonScale = scale;
	}
	
	public float getButtonWidth()
    {
        return this.width;
    }

    public void setWidth(int width)
    {
        this.width = width;
    }
    
    public void iPos()
    {
    	this.buttonPos.x += 0.2f;
    }

    public void increasePosition()
	{
		this.buttonPos.x += InGameSettings.fovIn / 3000f / 4.1f;
		if(buttonPos.x > 0.24f)
			buttonPos.x = 0.24f;
		if(buttonPos.x < -0.22f)
			buttonPos.x = -0.22f;
	}
	
	public void decreasePosition()
	{
		this.buttonPos.x += InGameSettings.fovIn / -3000f / 4.1;
		if(buttonPos.x > 0.24f)
			buttonPos.x = 0.24f;
		if(buttonPos.x < -0.22f)
			buttonPos.x = -0.22f;
		
	}
    
}
