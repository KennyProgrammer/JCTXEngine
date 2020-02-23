package com.kenny.craftix.client.text;

import java.util.ArrayList;
import java.util.List;

import com.kenny.craftix.client.text.component.Character;
import com.kenny.craftix.client.text.component.Line;
import com.kenny.craftix.client.text.component.MetaFile;
import com.kenny.craftix.client.text.component.Word;
 
public class TextMeshCreator 
{
    public static final double LINE_HEIGHT = 0.03f;
    public static final int SPACE_ASCII = 32;//32
 
    private MetaFile metaData;
 
    public TextMeshCreator(String fontFile) 
    {
        this.metaData = new MetaFile(fontFile);
    }
 
    /**
     * Create a list of vertecis and textures coords for quad need to render this text, base on 
     * information from the font list.
     */
    public TextMeshData createTextMesh(Text text) 
    {
        List<Line> lines = createStructure(text);
        TextMeshData data = createQuadVertices(text, lines);
        return data;
    }
 
    public List<Line> createStructure(Text text) 
    {
    	char[] myChars = new char[] 
    			{
    				'À','Á','Â','Ã','Ä','Å','¨','Æ','Ç','È','É','Ê','Ë','Ì','Í','Î','Ï','Ð','Ñ','Ò','Ó','Ô','Õ','Ö','×','Ø','Ù','Ú','Û','Ü','Ý','Þ','ß',
    				'à','á','â','ã','ä','å','¸','æ','ç','è','é','ê','ë','ì','í','î','ï','ð','ñ','ò','ó','ô','õ','ö','÷','ø','ù','ú','û','ü','ý','þ','ÿ'
    			};
    	
    	//myChars = text.getText().to
        char[] chars = text.getText().toCharArray();
        List<Line> lines = new ArrayList<Line>();
        Line currentLine = new Line(metaData.getSpaceWidth(), text.getFontSize(), text.getMaxLineSize());
        Word currentWord = new Word(text.getFontSize());
        for (char c : chars) 
        {
            int ascii = (int) c;
            if (ascii == SPACE_ASCII) 
            {
                boolean added = currentLine.attemptToAddWord(currentWord);
                if (!added) 
                {
                    lines.add(currentLine);
                    currentLine = new Line(metaData.getSpaceWidth(), text.getFontSize(), text.getMaxLineSize());
                    currentLine.attemptToAddWord(currentWord);
                }
                currentWord = new Word(text.getFontSize());
                continue;
            }
            Character character = metaData.getCharacter(ascii);
            currentWord.addCharacter(character);
        }
        completeStructure(lines, currentLine, currentWord, text);
        return lines;
    }
 
    public void completeStructure(List<Line> lines, Line currentLine, Word currentWord, Text text) 
    {
        boolean added = currentLine.attemptToAddWord(currentWord);
        if (!added) 
        {
            lines.add(currentLine);
            currentLine = new Line(metaData.getSpaceWidth(), text.getFontSize(), text.getMaxLineSize());
            currentLine.attemptToAddWord(currentWord);
        }
        lines.add(currentLine);
    }
 
    public TextMeshData createQuadVertices(Text text, List<Line> lines) 
    {
        text.setNumberOfLines(lines.size());
        double curserX = 0f;
        double curserY = 0f;
        List<Float> vertices = new ArrayList<Float>();
        List<Float> textureCoords = new ArrayList<Float>();
        for (Line line : lines) 
        {
            if (text.isCentered())
            {
                curserX = (line.getMaxLength() - line.getLineLength()) / 2;
            }
            for (Word word : line.getWords()) 
            {
                for (Character letter : word.getCharacters()) 
                {
                    addVerticesForCharacter(curserX, curserY, letter, text.getFontSize(), vertices);
                    addTexCoords(textureCoords, letter.getxTextureCoord(), letter.getyTextureCoord(),
                            letter.getXMaxTextureCoord(), letter.getYMaxTextureCoord());
                    curserX += letter.getxAdvance() * text.getFontSize();
                }
                curserX += metaData.getSpaceWidth() * text.getFontSize();
            }
            curserX = 0;
            curserY += LINE_HEIGHT * text.getFontSize();
        }       
        return new TextMeshData(listToArray(vertices), listToArray(textureCoords));
    }
 
    /**
     * Use the addVertices add it for the one character.
     */
    public void addVerticesForCharacter(double curserX, double curserY, Character character, double fontSize,
            List<Float> vertices) 
    {
        double x = curserX + (character.getxOffset() * fontSize);
        double y = curserY + (character.getyOffset() * fontSize);
        double maxX = x + (character.getSizeX() * fontSize);
        double maxY = y + (character.getSizeY() * fontSize);
        double properX = (2 * x) - 1;
        double properY = (-2 * y) + 1;
        double properMaxX = (2 * maxX) - 1;
        double properMaxY = (-2 * maxY) + 1;
        addVertices(vertices, properX, properY, properMaxX, properMaxY);
    }
   /**
    * Adds vertices for each coordinate and position using a data list.
    */
    public static void addVertices(List<Float> vertices, double x, double y, double maxX, double maxY) 
    {
        vertices.add((float) x);
        vertices.add((float) y);
        vertices.add((float) x);
        vertices.add((float) maxY);
        vertices.add((float) maxX);
        vertices.add((float) maxY);
        vertices.add((float) maxX);
        vertices.add((float) maxY);
        vertices.add((float) maxX);
        vertices.add((float) y);
        vertices.add((float) x);
        vertices.add((float) y);
    }
 
    /**
     * Adds texture coordidate for each coordinate and position using a data list.
     */
    public static void addTexCoords(List<Float> texCoords, double x, double y, double maxX, double maxY) 
    {
        texCoords.add((float) x);
        texCoords.add((float) y);
        texCoords.add((float) x);
        texCoords.add((float) maxY);
        texCoords.add((float) maxX);
        texCoords.add((float) maxY);
        texCoords.add((float) maxX);
        texCoords.add((float) maxY);
        texCoords.add((float) maxX);
        texCoords.add((float) y);
        texCoords.add((float) x);
        texCoords.add((float) y);
    }
 
     
    /**
     * Convert the list of data to array of data.
     */
    private static float[] listToArray(List<Float> listOfFloats) 
    {
        float[] array = new float[listOfFloats.size()];
        for (int i = 0; i < array.length; i++) 
        {
            array[i] = listOfFloats.get(i);
        }
        return array;
    }
}
 