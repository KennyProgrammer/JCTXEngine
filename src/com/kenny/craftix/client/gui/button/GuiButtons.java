package com.kenny.craftix.client.gui.button;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.audio.sound.SoundLoader;
import com.kenny.craftix.client.gui.GuiQuad;
import com.kenny.craftix.client.gui.GuiRenderManager;
import com.kenny.craftix.client.gui.GuiRenderer;
import com.kenny.craftix.client.gui.GuiScreen;
import com.kenny.craftix.client.loader.TexturesLoader;
import com.kenny.craftix.client.renderer.GlHelper;
import com.kenny.craftix.client.renderer.GlHelper.Blend;
import com.kenny.craftix.client.renderer.textures.TextureManager;
import com.kenny.craftix.utils.math.Maths;

public abstract class GuiButtons extends GuiScreen
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
	/**Current Gui Object.*/
	public GuiQuad currentGui;
	/**File name in the "assets" folder.*/
	public int textureId;
	public int buttonId;
	/**Loader data about button texture in the Vbo.*/
	public TexturesLoader textureLoader = new TexturesLoader();
	public GuiRenderer buttonRenderer = new GuiRenderer(this.cx);
	public boolean visible;
	public boolean enable;
	public boolean renderer;
	protected boolean hovered;
	public boolean nextButtonTouches;
	public boolean onButton;
	/**Necessary to control the number of clicks or taps on the screen.*/
	public int clickValue;
	/**When an event occurs when the button is clicked.*/
	public int eventButton;
	protected long lastMouseEvent;
	public float buttonTextSize;
	public float buttonTextLength;
	public float textX; 
	public float textY;
	public String text;

	
	public GuiButtons(int buttonIdIn, String fileIn, float x, float y, int typeIn, String buttonTextIn) 
	{
		if(typeIn == 0)
		{
			this.width = 0.26f;
			this.height = 0.08f;
		}
		if(typeIn == 1)
		{
			this.width = 0.055f;
			this.height = 0.098f;
		}
		if(typeIn == 2)
		{
			this.width = 0.18f;
			this.height = 0.08f;
		}
		this.buttonId = buttonIdIn;
		this.textureId = this.textureLoader.loadTexture(fileIn);
		this.x = x;
		this.y = y;
		this.buttonPos = new Vector2f(this.x, this.y);
		this.buttonScale = new Vector2f(this.width, this.height);
		this.visible = true;
		this.enable = true;
		this.text = buttonTextIn;
	}
	
	/**
	 * Return 0 if the button is not enabled, and 1if the mouse is bot hovering over this button. 
	 * and 2 if it IS hovering over.
	 * @param mouseOver - false if mouse not hovering over this button.
	 */
	protected int getHoverState(boolean mouseOver)
	{
		int j = 1;
		if(!this.enable)
		{
			j = 0;
			
		} else if (mouseOver)
		{
			j = 2;
		}
			return j;
	}
	
	/**
	 * Render the button into the screen.
	 */
	public void renderButton(boolean isUpdate, boolean isLink)
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
	
	public void isUpdate(boolean isUpdate)
	{
		if(isUpdate && this.enable)
		{
			this.checkHover();
			this.onButtonClick();
		}
	}

	/**
	 * Brings together all the methods responsible for click events 
	 * and sets limits for each of nick.
	 */
	public void onButtonClick()
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
				this.onClick(this);

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
	
	public void checkHover()
	{
		if(this.visible)
		{
			Vector2f buttonPos = this.getPosition();
			Vector2f buttonScale = this.getScale();
			Vector2f mouseCoords = Craftix.getNormalizedMouseCoords();
			
			if (buttonPos.y + buttonScale.y > -mouseCoords.y && buttonPos.y - buttonScale.y < -mouseCoords.y 
					&& buttonPos.x + buttonScale.x > mouseCoords.x && buttonPos.x - buttonScale.x < mouseCoords.x) 
			
			{
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
				}
			}
		}
	}
	
	public void buttonBackCheck()
	{
		//this.nextButtonTouches = true;
	}
	
	public void initText(GuiButtons button)
	{
		
	}

	public void hide()
	{
		this.setScale(new Vector2f(0, 0));
	}
	
	public void show(int typeIn)
	{
		if(typeIn == 0)
		{
			this.width = 0.26f;
			this.height = 0.08f;
		}
		if(typeIn == 1)
		{
			this.width = 0.055f;
			this.height = 0.098f;
		}
		if(typeIn == 2)
		{
			this.width = 0.18f;
			this.height = 0.08f;
		}
	}
	
	public void onClick(GuiButtons button) 
	{
		this.playSound();
	}
	
	/**
	 * Called when a mouse button is pressed and the mouse is 
	 * moved around.
	 */
	public void onMoveClick(GuiButtons button, int clickedMouseButton, long timeLastClick)
	{
		
	}
	
	public void onRelease(GuiButtons button)
	{
		
	}

	public void playHoverAnimation(float scaleFactor)
	{
		this.setScale(new Vector2f(buttonScale.x + scaleFactor, buttonScale.y + scaleFactor));
	}
    
    public void playSound()
	{
		SoundLoader.soundPlay(SoundLoader.sourceButtonClick, 
				SoundLoader.bufferButtonClick);
		if(!SoundLoader.sourceButtonClick.isPlaying())
		{
			SoundLoader.sourceMenu1.play(SoundLoader.bufferMenu1);
		}
	
	}
    
    /**
     * Reloads the data about the button such as size, etc.
     */
    public void reload()
    {
    	float minusScaleFactor = 0.004f;
    	this.setScale(new Vector2f(this.buttonScale.x - minusScaleFactor, this.buttonScale.y - minusScaleFactor));
    }
	
	/**
    * Fired when the mouse button is dragged.
    */
    protected void mouseDragged(Craftix cx, int mouseX, int mouseY)
    {
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
    
}
