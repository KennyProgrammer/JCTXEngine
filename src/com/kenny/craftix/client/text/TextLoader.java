package com.kenny.craftix.client.text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.Display;

import com.kenny.craftix.client.loader.Loader;
import com.kenny.craftix.client.loader.TexturesLoader;
import com.kenny.craftix.client.settings.InGameSettings;
import com.kenny.craftix.client.text.component.font.FontType;
import com.kenny.craftix.client.text.render.FontRenderer;

public class TextLoader 
{
	private static boolean load;
	/**Loader the Open GL Objects.*/
	private static Loader openGlObjectLoader = new Loader();
	/**Loader textures to font.*/
	private static TexturesLoader textureLoader = new TexturesLoader();
	/**Map of all text loaded and represented in the game.*/
	private static Map<FontType, List<Text>> texts = new HashMap<FontType, List<Text>>();
	/**Renderer for the text.*/
	private static FontRenderer renderer;
	/**This old scale factor varible replaced from old TextInit class.*/
	protected static float cX;
	protected static float cY;
	protected static float posY;
	protected static float posX;
	
	/**
	 * Created and added the text in vao, with a predetermined pre-information.
	 * 
	 * @param id - return id of the text.
	 */
	public static Text createText(Text id, float x, float y, boolean centered, String textCode)
	{
		id = new Text(textCode, x, y, centered);
	    	return id;
	}
	
	/**
	 * Init all componets of the text.
	 */
	public static void init()
	{
		renderer = new FontRenderer();
		loadTextScaleFactor();
	}
	
	public static void render()
	{
		renderer.render(texts);
	}
	
	/**
	 * Clean up all text rendering stuff when game has close.
	 */
	public static void cleanUp()
	{
		renderer.cleanUp();
	}
	
	/**
	 * Return value of load.
	 */
	public boolean isLoad()
	{
		return this.load;
	}
	
	public static FontType setDefalutFont()
	{ 
		FontType fontCandara = new FontType(textureLoader.loadFontAtlas("candara"), "candara");
			return fontCandara;
	}
	
	
	/**
	 * Add text to the memory of Vao, and stored there until deleted.
	 */
	public static void addText(Text text)
	{
		FontType font = text.getFont();
		TextMeshData data = font.loadText(text);
		int vao = openGlObjectLoader.loadToVao(data.getVertexPositions(), data.getTextureCoords());
		text.setMeshInfo(vao, data.getVertexCount());
		List<Text> textBatch = texts.get(font);
		if(textBatch == null)
		{
			textBatch = new ArrayList<Text>();
			texts.put(font, textBatch);
		}
		textBatch.add(text);
		load = true;
	}
	
	/**
	 * Here to text add just mesh info. Im create this method for update text.
	 */
	public static void addTextMesh(Text text)
	{
		FontType font = text.getFont();
		TextMeshData data = font.loadText(text);
		int vao = openGlObjectLoader.loadToVao(data.getVertexPositions(), data.getTextureCoords());
		text.setMeshInfo(vao, data.getVertexCount());
	}
	
	/**
	 * Update text first per seconds.
	 */
	public static void updateText(Text textId, String text)
	{
		Loader.deleteVaoFromCache(textId.getMesh());
		textId.setText(text);
		addTextMesh(textId);
	}
	
	/**
	 * Remove text from the memory of Vao.
	 */
	public static void removeText(Text text)
	{
		List<Text> textBatch = texts.get(text.getFont());
		textBatch.remove(text);
		if(textBatch.isEmpty())
		{
			texts.remove(text.getFont());
		}
		load = false;
	}
	
	/***
	 * Initiale scale text for each scale factor size.
	 */
	public static void loadTextScaleFactor()
	{
		if(InGameSettings.guiScaleLargeIn)
		{
			cX = Display.getHeight() / Display.getWidth() + 0.82f; cY = 0.94f;
			posX = 0f; posY = 0f;
		}
		
		if(InGameSettings.guiScaleMediumIn)
		{
			cX = Display.getHeight() / Display.getWidth() + 0.83f; cY = 0.94f;
			posX = -0.005f; posY = -0.002f;
		}
		
		if(InGameSettings.guiScaleSmallIn)
		{
			cX = Display.getHeight() / Display.getWidth() + 0.85f; cY = 0.94f;
			posX = -0.01f; posY = -0.005f;
		}
	}
	
}
