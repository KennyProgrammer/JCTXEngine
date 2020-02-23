package com.kenny.craftix.client.text.component;
/**
 * Simple data structure class holding information about a certain glyph in the
 * font texture atlas. All sizes are for a font-size of 1.
 */
public class Character 
{
 
	/**The ASCII value of the character*/
    private int id;
    /**The X texture coord for the top left corner of the character in the texture atlas.*/
    private double xTextureCoord;
    /**The Y texture coord for the top left corner of the character in the texture atlas.*/
    private double yTextureCoord;
    /**The max X texture coord of the character in the texture atlas.*/
    private double xMaxTextureCoord;
    /**The max Y texture coord of the character in the texture atlas.*/
    private double yMaxTextureCoord;
    /**The X distance from the curser to the left edge of the characters quad.*/
    private double xOffset;
    /**The Y distance from the curser to the left edge of the characters quad.*/
    private double yOffset;
    /**The width of the character.*/
    private double sizeX;
    /**The height of the character.*/
    private double sizeY;
    /**How far in pixels the cursor should advance after adding this character.*/
    private double xAdvance;
 
    /**
     * @param xTexSize - the width of the character in the texture atlas.
     * @param yTexSize - the height of the character in the texture atlas.
     */
    protected Character(int id, double xTextureCoord, double yTextureCoord, double xTexSize, double yTexSize,
            double xOffset, double yOffset, double sizeX, double sizeY, double xAdvance) 
    {
        this.id = id;
        this.xTextureCoord = xTextureCoord;
        this.yTextureCoord = yTextureCoord;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.xMaxTextureCoord = xTexSize + xTextureCoord;
        this.yMaxTextureCoord = yTexSize + yTextureCoord;
        this.xAdvance = xAdvance;
    }
 
    public int getId() 
    {
        return id;
    }
 
    public double getxTextureCoord() 
    {
        return xTextureCoord;
    }
 
    public double getyTextureCoord() 
    {
        return yTextureCoord;
    }
 
    public double getXMaxTextureCoord() 
    {
        return xMaxTextureCoord;
    }
 
    public double getYMaxTextureCoord() 
    {
        return yMaxTextureCoord;
    }
 
    public double getxOffset() 
    {
        return xOffset;
    }
 
    public double getyOffset() 
    {
        return yOffset;
    }
 
    public double getSizeX() 
    {
        return sizeX;
    }
 
    public double getSizeY() 
    {
        return sizeY;
    }
 
    public double getxAdvance() 
    {
        return xAdvance;
    }
 
}