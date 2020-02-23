package com.kenny.craftix.client.renderer.models;

import com.kenny.craftix.client.renderer.textures.ModelTexture;

public class TexturedModel 
{
	/**This contains a raw model*/
	private Model model;
	/**This contains a textures for model.*/
	private ModelTexture texture;
	
	public TexturedModel(Model modelIn, ModelTexture textureIn)
	{
		this.model = modelIn;
		this.texture = textureIn;
	}

	public Model getModel() 
	{
		return model;
	}

	public ModelTexture getTexture() 
	{
		return texture;
	}
}
