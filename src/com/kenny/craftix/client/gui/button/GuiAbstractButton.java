package com.kenny.craftix.client.gui.button;

import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;

import com.kenny.craftix.client.Craftix;
//import com.kenny.craftix.client.CraftixOld;
import com.kenny.craftix.client.audio.sound.SoundLoader;
import com.kenny.craftix.client.gui.GuiQuad;
import com.kenny.craftix.client.gui.GuiScaled;
import com.kenny.craftix.client.loader.Loader;
import com.kenny.craftix.client.loader.TexturesLoader;
import com.kenny.craftix.client.scenes.MainMenuSceneOld;

public abstract class GuiAbstractButton implements IButton
{
	/**Called GuiTexture class.*/
	private GuiQuad guis;
	/**This is scale of the button*/
	private boolean isHidden = false;
	private boolean isVisible = false;
	private boolean isHovering = false;
	public static boolean isHoldingOnScreen;
	public boolean mousePressed;
	public boolean mouseRelesed;
	public Vector2f originalScale;
	/**The base Open Gl Objects loader.*/
	public Loader openGlObjectsLoader = new Loader();
	/**The base textures loader.*/
	public TexturesLoader textureLoader = new TexturesLoader();
	public GuiScaled guiScaled = new GuiScaled();
	/**Its original X scale of button.*/
	public static float x1 = 0.00f;
	/**Its original Y scale of button.*/
	public static float y1 = 0.00f;
	public static float xYesNo = 0.00f;
	public static float yYesNo = 0.00f;
	public int buttonId;
	public int textureId;
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
	public String buttonText;

	public MainMenuSceneOld mms = new MainMenuSceneOld();
	
	public GuiAbstractButton(String textureName, Vector2f position, Vector2f scale)
	{
		this.openGlObjectsLoader = new Loader();
		this.guis = new GuiQuad(this.textureLoader.loadTexture(textureName), position, scale);
		this.originalScale = scale;	
	}
	
	/**
	 * With use Gui scale system.
	 */
	public GuiAbstractButton(String textureName, Vector2f position)
	{
		this.openGlObjectsLoader = new Loader();
		this.guiScaled = new GuiScaled();
		this.originalScale = new Vector2f(x1, y1);
		this.guis = new GuiQuad(this.textureLoader.loadTexture(textureName), position,
				this.originalScale);
	}
	
	/**
	 * With use Gui scale system and can use custom X or Y for change something button.
	 */
	public GuiAbstractButton(String textureName, Vector2f position, 
			float customX, float customY)
	{
		this.openGlObjectsLoader = new Loader();
		this.guiScaled = new GuiScaled();
		this.originalScale = new Vector2f(x1 + customX, y1 + customY);
		
		this.guis = new GuiQuad(this.textureLoader.loadTexture(textureName), position,
				this.originalScale);
	}
	
	public GuiAbstractButton(String textureName, Vector2f position, 
			float customX, float customY, boolean isMulti)
	{
		this.openGlObjectsLoader = new Loader();
		this.guiScaled = new GuiScaled();
		this.originalScale = new Vector2f(x1 + customX, y1 + customY);
		if(isMulti)
		{
			this.originalScale = new Vector2f(xYesNo + customX, yYesNo + customY);
		}
		this.guis = new GuiQuad(this.textureLoader.loadTexture(textureName), position,
				this.originalScale);
	}
	
	//========================================
	public GuiAbstractButton(int id, String textureName, Vector2f position, 
			float customX, float customY, boolean isMulti)
	{
		this.buttonId = id;
		this.openGlObjectsLoader = new Loader();
		this.guiScaled = new GuiScaled();
		this.originalScale = new Vector2f(x1 + customX, y1 + customY);
		if(isMulti)
		{
			this.originalScale = new Vector2f(xYesNo + customX, yYesNo + customY);
		}
		this.guis = new GuiQuad(this.textureLoader.loadTexture(textureName), position,
				this.originalScale);
	}
	//========================================
	
	public void isHolidingOnScreenNotButton()
	{
	}
	
	/**
	 * This is a simple update method for buttons.
	 */
	public void update()
	{	
		if (!isHidden) 
		{
	        Vector2f location = guis.getPosition();
	        Vector2f scale = guis.getScale();
	        Vector2f mouseCoordinates = Craftix.getNormalizedMouseCoords();
	            
	        if (location.y + scale.y > -mouseCoordinates.y && location.y - scale.y < -mouseCoordinates.y 
	            && location.x + scale.x > mouseCoordinates.x && location.x - scale.x < mouseCoordinates.x)
	        {
	            this.whileHovering();
	            if (!this.isHovering) 
	            {
	                this.isHovering = true;
	                this.onStartHovering();
	                //onStartHover(this);
	            }
	            else
	            {
	            	
	            	if (Mouse.isButtonDown(0) && this.isVisible)
            			onClick(this);
	            	if(Mouse.isButtonDown(0) && this.isVisible)
	            		this.playSound();

	            	 isVisible(this); 	
	            }
	            
	            this.mouseReleased(0, 0);
	            
	            
	        } 
	        else 
	        {
	        	isHoldingOnScreen = true;
	        	
	            if (this.isHovering) 
	            {
	                this.isHovering = false;
	                this.onStopHovering();
	            }
	        }
	    }
	}
	
	/**
	 *This is a simple update method for multi-combinate buttons. Like a Yes-No or 1-2-3 and etc..
	 */
	public void updateMulti()
	{
		
		if (!isHidden) 
		{
	        Vector2f location = guis.getPosition();
	        Vector2f scale = guis.getScale();
	        Vector2f mouseCoordinates = Craftix.getNormalizedMouseCoords();
	            
	        if (location.y + scale.y > -mouseCoordinates.y && location.y - scale.y < -mouseCoordinates.y 
	            && location.x + scale.x > mouseCoordinates.x && location.x - scale.x < mouseCoordinates.x)
	        {
	           this.whileHovering();
	            if (!this.isHovering) 
	            {
	                this.isHovering = true;
	                this.onStartHovering();
	            }
	            boolean isCombinateMulti;
	            isCombinateMulti = true;
	            if(isCombinateMulti)
	            {
	            	while(Mouse.next())
	            		if (Mouse.isButtonDown(0) && this.isVisible)
	            			onClick(this);
	            	
	            		if(Mouse.isButtonDown(0))
	            			this.playSound();
	            		
	            		isVisible(this);
	     	           
	     	       	   
	            }
	            else
	            {
	            	 if (Mouse.isButtonDown(0) && this.isVisible)
		     	           onClick(this);
		     	           isVisible(this);
	            }
	        } 
	        else 
	        {
	            if (this.isHovering) 
	            {
	               this.onStopHovering();
	            }
	        }
	    }
	}
	
	/**
     * Fired when the mouse button is released. Equivalent of MouseListener.mouseReleased(MouseEvent e).
     */
    public void mouseReleased(int mouseX, int mouseY)
    {
    }
    
    /**
     * Returns true if the mouse has been pressed on this control. Equivalent of MouseListener.mousePressed(MouseEvent
     * e).
     */
    public boolean mousePressed(int mouseX, int mouseY)
    {
        return this.isVisible && mouseX >= x1 && mouseY >= y1 && mouseX < x1  && mouseY < y1;
    }
	
	public void isVisible(GuiAbstractButton button)
	{
		this.isVisible = true;
	}
	
	public void stopUpdate()
	{
		boolean update = false;
			if(update == true)
				this.update();
	}
	
	public void update2(GuiAbstractButton button)
	{
		if (!isHidden) 
		{
	        Vector2f location = guis.getPosition();
	        Vector2f scale = guis.getScale();
	        Vector2f mouseCoordinates = Craftix.getNormalizedMouseCoords();
	            
	        if (location.y + scale.y > -mouseCoordinates.y && location.y - scale.y < -mouseCoordinates.y 
	            && location.x + scale.x > mouseCoordinates.x && location.x - scale.x < mouseCoordinates.x)
	        {
	            this.whileHovering();
	            if (!this.isHovering) 
	            {
	                this.isHovering = true;
	                this.onStartHovering();
	                this.playSound();
	            }
	            while (Mouse.next())
	                if (Mouse.isButtonDown(0))
	                this.onClick(button);
	        } 
	        else 
	        {
	            if (this.isHovering) 
	            {
	                this.isHovering = false;
	                this.onStopHovering();
	            }
	        }
	    }
	}

	
	private void startRender(List<GuiQuad> guiTextureList)
	{
	    guiTextureList.add(guis);
	}
 
	private void stopRender(List<GuiQuad> guiTextureList)
	{
	    guiTextureList.remove(guis);
	}
	
	
	public void hide(List<GuiQuad> guiTextures) 
	{
		stopRender(guiTextures);
		isHidden = true;
	}
	 
	public void show(List<GuiQuad> guiTextures) 
	{
	    startRender(guiTextures);
	    isHidden = false;
	}
	
	
	public void resetScale()
	{
		this.guis.setScale(originalScale);
	}
	
	public void onStartHovering()
	{
		this.playHoverAnimation(0.004f);
	}
	
	public void onStopHovering()
	{
		this.resetScale();
	}
	
	public void whileHovering()
	{
		/**
		 * Some stuff be in future.
		 */
		
		this.playHoverAnimation(0.004f);
	}
	/**
	 * In the future am be working on sound system in the game engine, and add click-button
	 * sound.
	 */
	public void playSound()
	{
		SoundLoader.soundPlay(SoundLoader.sourceButtonClick, 
				SoundLoader.bufferButtonClick);
		if(!SoundLoader.sourceButtonClick.isPlaying())
		{
			SoundLoader.sourceMenu1.play(SoundLoader.bufferMenu1);
		}
	
		
	}
	
	public void playHoverAnimation(float scaleFactor)
	{
		this.guis.setScale(new Vector2f(originalScale.x + scaleFactor, originalScale.y + scaleFactor));
	}
	
	public void setButtonScale()
	{
		this.guis.setScale(new Vector2f(originalScale.x - 0.010f, originalScale.y - 0.020f));
	}
	
	public boolean isHidden() 
	{
		return isHidden;
	}
	
	
}
