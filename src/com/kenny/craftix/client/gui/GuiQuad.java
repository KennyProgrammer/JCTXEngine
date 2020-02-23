package com.kenny.craftix.client.gui;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector4f;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.loader.Loader;
import com.kenny.craftix.client.loader.TexturesLoader;
import com.kenny.craftix.client.settings.InGameSettings;

public class GuiQuad extends Gui
{
	public Craftix cx;
	private int textureId;
	/**This is position of gui texture*/
	public Vector2f quadPos;
	/**This is scale size of gui texture*/
	public Vector2f quadScale;
	/**Rotation Gui on X axis*/
	public float rotX;
	/**Rotation Gui on Y axis*/
	public float rotY;
	private Vector4f colour;
	/**Position x quad on the gui screen.*/
	public float x;
	/**Position y quad on the gui screen.*/
	public float y;
	/**This is width of the quad on the screen.*/
	public float width;
	/**This is height of the quad on the screen.*/
	public float height;
	/**Loader textures manager.*/
	public Loader openGlObjectsLoader = new Loader();
	public TexturesLoader textureLoader = new TexturesLoader();
	

	public GuiQuad(int texture, Vector2f position, Vector2f scale, float rotX, float rotY) 
	{
		this.textureId = texture;
		this.quadPos = position;
		this.quadScale = scale;
		this.rotX = rotX;
		this.rotY = rotY;
	}
	
	public GuiQuad(String fileIn, float x, float y, float widthIn, float heightIn) 
	{
		this.textureId = this.textureLoader.loadTexture(fileIn);
		this.x = x;
		this.y = y;
		this.width = widthIn;
		this.height = heightIn;
		this.quadPos = new Vector2f(this.x, this.y);
		this.quadScale = new Vector2f(this.width, this.height);
	}
	
	public GuiQuad(int texture, Vector2f position, Vector2f scale) 
	{
		this.textureId = texture;
		this.quadPos = position;
		this.quadScale = scale;
	}
	
	public GuiQuad(Craftix cxIn, String guiFile, Vector2f position, Vector2f scale) 
	{
		this.cx = cxIn;
		this.textureId = this.textureLoader.loadTexture(guiFile);
		this.quadPos = position;
		this.quadScale = scale;
	}
	
	public void increasePosition()
	{
		this.quadPos.x += InGameSettings.fovIn / 3000f / 4.1f;
		if(quadPos.x > 0.24f)
			quadPos.x = 0.24f;
		if(quadPos.x < -0.22f)
			quadPos.x = -0.22f;
	}
	
	public void increasePosition(float x)
	{
		this.quadPos.x += x;
	}
	
	public void decreasePosition()
	{
		this.quadPos.x += InGameSettings.fovIn / -3000f / 4.1;
		if(quadPos.x > 0.24f)
			quadPos.x = 0.24f;
		if(quadPos.x < -0.22f)
			quadPos.x = -0.22f;
		
	}
	
	public void increaseRotation(float dX, float dY)
	{
		this.rotX += dX;
		this.rotY += dY;
	}
	
	public int getTextureId() 
	{
		return textureId;
	}

	public Vector2f getPosition() 
	{
		return quadPos;
	}
	
	public void setTexture(int texture) 
	{
		this.textureId = texture;
	}

	public void setPosition(Vector2f position) 
	{
		this.quadPos = position;
	}
	
	public Vector2f getScale() 
	{
		return quadScale;
	}

	public void setScale(Vector2f scale)
	{
		this.quadScale = scale;
	}
	
	public Vector4f getColor() 
	{
		return this.colour;
	}


	public void setColor(Vector4f color) 
	{
		this.colour = color;
	}
	
}
