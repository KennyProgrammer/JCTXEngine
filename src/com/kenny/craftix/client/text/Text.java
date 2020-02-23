package com.kenny.craftix.client.text;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.gui.GuiScale;
import com.kenny.craftix.client.text.component.font.FontType;
 
/**
 * Represents a piece of text in the game.
 */
public class Text 
{
	/**Text string in the game enigne.*/
    private String textString;
    /**Size of the font text.*/
    private float fontSize;
    /**Vao id of the text mesh.*/
    private int textMeshVao;
    /**This is count of the verices.*/
    private int vertexCount;
    /**Color of the text.*/
    private Vector3f colour = new Vector3f(1f, 1f, 1f);
    /**The position on the screen where the top left corner of the text 
     * should be rendered. The top left corner of the screen is(0, 0) and the bottom right 
     * is (1, 1).*/
    private Vector2f position;
    private float lineMaxSize;
    /**Basically the width of the virtual page in terms of screen width (1 is full screen width, 
     * 0.5 is half the width of the screen, etc.) Text cannot go off the edge of the page, so if 
     * the text is longer than this length it will go onto the next line. When text is centered it 
     * is centered into the middle of the line, based on this line length value.*/
    private int numberOfLines;
    /**Whether the text should be centered or not.*/
    private boolean centerText;
    private FontType font;
    public boolean render;
    public GuiScale scaleManager = new GuiScale();
    public boolean onText;
 
    /**
     * Creates a new text, loads the text's quads into a VAO, and adds the text
     * to the screen.
     */
    public Text(String text, float x, float y, boolean centered) 
    {
        this.textString = text;
        this.fontSize = 1.6f - this.scaleManager.getTextScale();
        this.font = TextLoader.setDefalutFont();
        this.position = new Vector2f(x, y + this.scaleManager.getTextScalePos());
        this.lineMaxSize = 0.6f;
        this.centerText = centered;
        this.render = true;
    }
    
    public Text(String letter, float fontSize, float x, float y) 
    {
    	this(letter, x, y, true);
    	this.fontSize = fontSize - this.scaleManager.getTextScale();
    }
    
    public Text(String text, float x, float y, boolean centered, boolean render, float scale, 
    		float r, float g, float b)
    {
    	this(text, x, y, centered);
    	this.fontSize = scale - this.scaleManager.getTextScale();
    	this.colour = new Vector3f(r, g, b);
    	if(render)
    		this.render = true;
    	else
    		this.render = false;
    }
    
    public boolean checkHover(Text text)
    {
    	boolean isHovered = false;
    	float textSize = text.getFontSize();
    	Vector2f textPos = text.getPosition();
    	Vector2f mouseCoords = Craftix.getNormalizedMouseCoords();
    	
    	if(textPos.y > -mouseCoords.y && textPos.y < -mouseCoords.y
    			&& textPos.x > mouseCoords.x && textPos.x < mouseCoords.x)
    	{
    		isHovered = true;
    		if(!this.onText)
    		{
    			text.setColour(1F, 0.95F, 0.85F);
    			System.out.println("Hey!");
    			this.onText = true;
    		}
    	}
    	else
    	{
    		if(this.onText)
    		{
    			this.onText = false;
    			text.setColour(1, 1, 1);
    		}
    	}
    	
    	return isHovered && textPos.y > -mouseCoords.y && textPos.y < -mouseCoords.y
    			&& textPos.x > mouseCoords.x && textPos.x < mouseCoords.x;
    }
    
    /**
     * Remove the text from the screen.
     */
    public void remove() 
    {
       TextLoader.removeText(this);
    }
 
    /**
     * @return The font used by this text.
     */
    public FontType getFont() 
    {
        return font;
    }
 
    /**
     * Set the colour of the text.
     * 
     * @param r - red value, between 0 and 1.
     * @param g - green value, between 0 and 1.
     * @param b - blue value, between 0 and 1.
     */
    public void setColour(float r, float g, float b) 
    {
        colour.set(r, g, b);
    }
 
    /**
     * @return the colour of the text.
     */
    public Vector3f getColour() 
    {
        return colour;
    }
 
    /**
     * @return The number of lines of text. This is determined when the text is
     *         loaded, based on the length of the text and the max line length
     *         that is set.
     */
    public int getNumberOfLines() 
    {
        return numberOfLines;
    }
 
    /**
     * @return The position of the top-left corner of the text in screen-space.
     *         (0, 0) is the top left corner of the screen, (1, 1) is the bottom
     *         right.
     */
    public Vector2f getPosition() 
    {
        return position;
    }
    
    /**
     * Set position of the text. (Called in update loop).
     * @param x - X position of the text.
     * @param y - Y position of the text.
     */
    public void setPosition(float x, float y)
    {
    	this.position = new Vector2f(x, y);
    }
 
    /**
     * @return the ID of the text's VAO, which contains all the vertex data for
     *         the quads on which the text will be rendered.
     */
    public int getMesh() 
    {
        return textMeshVao;
    }
 
    /**
     * Set the VAO and vertex count for this text.
     * 
     * @param vao - the VAO containing all the vertex data for the quads on
     *            which the text will be rendered.
     * @param verticesCount - the total number of vertices in all of the quads.
     */
    public void setMeshInfo(int vao, int verticesCount) 
    {
        this.textMeshVao = vao;
        this.vertexCount = verticesCount;
    }
 
    /**
     * @return The total number of vertices of all the text's quads.
     */
    public int getVertexCount() 
    {
        return this.vertexCount;
    }
 
    /**
     * @return the font size of the text (a font size of 1 is normal).
     */
    protected float getFontSize() 
    {
        return fontSize;
    }
    
    protected void setFontSize(float fontSize) 
    {
        this.fontSize = fontSize;
    }
 
 
    /**
     * Sets the number of lines that this text covers (method used only in
     * loading).
     */
    protected void setNumberOfLines(int number) 
    {
        this.numberOfLines = number;
    }
 
    /**
     * @return {@code true} if the text should be centered.
     */
    public boolean isCentered() 
    {
        return centerText;
    }
 
    /**
     * @return The maximum length of a line of this text.
     */
    protected float getMaxLineSize() 
    {
        return lineMaxSize;
    }
 
    public void setText(String text)
    {
    	this.textString = text;
    }
    
    /**
     * @return The string of text.
     */
    public String getText() 
    {
        return textString;
    }
    
    public boolean isRender()
    {
    	return this.render;
    }
    
    public void r(boolean render)
    {
    	this.render = render;
    	if(render)
    	{
    		this.checkHover(this);
    	}
    }
 
}