package com.kenny.craftix.client.renderer.entity;

import java.util.List;
import java.util.Map;

import org.lwjgl.util.vector.Matrix4f;

import com.kenny.craftix.client.entity.EntityBlock;
import com.kenny.craftix.client.entity.GameObject;
import com.kenny.craftix.client.renderer.GlHelper;
import com.kenny.craftix.client.renderer.GlHelper.Texture;
import com.kenny.craftix.client.renderer.models.Model;
import com.kenny.craftix.client.renderer.models.TexturedModel;
import com.kenny.craftix.client.renderer.textures.ModelTexture;
import com.kenny.craftix.client.renderer.textures.TextureManager;
import com.kenny.craftix.client.shaders.GameObjectShader;
import com.kenny.craftix.utils.math.Maths;

public class EntityRenderer 
{
	private GameObjectShader shader;
	
	public EntityRenderer(GameObjectShader shader, Matrix4f projectionMatrix) 
	{
		this.shader = shader;
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.connectTextureUnits();
		shader.stop();
	}
	
	public void render(Map<TexturedModel, List<GameObject>> entities)
	{
		for (TexturedModel model : entities.keySet()) 
		{
			prepareTexturedModel(model);
			List<GameObject> batch = entities.get(model);
			for (GameObject object : batch) 
			{
				if(object.isRender())
				{
					prepareInstance(object);
					GlHelper.glDrawElements(Texture.TRIANGLES, model.getModel().getVertexCount(), 
							Texture.UNSINGED_INT, 0);
				}
			}
			
			unbindTexturedModel();
		}
	}
	
	public void renderBlock(Map<TexturedModel, List<EntityBlock>> blocks)
	{
		for (TexturedModel model : blocks.keySet()) 
		{
			prepareTexturedModel(model);
			List<EntityBlock> batch = blocks.get(model);
			for (EntityBlock block : batch) 
			{
				Matrix4f transformationMatrix = Maths.createTransformationMatrix(block.getPositionVector(), 
						block.getRotX(), block.getRotY(), block.getRotZ(), block.getScale());
				this.shader.loadTransformationMatrix(transformationMatrix);
				this.shader.loadOffset(block.getTextureXOffset(), block.getTextureYOffset());
				GlHelper.glDrawElements(Texture.TRIANGLES, model.getModel().getVertexCount(), 
						Texture.UNSINGED_INT, 0);
			}
			
			unbindTexturedModel();
		}
	}
	
	private void prepareTexturedModel(TexturedModel model)
	{
		Model rawModel = model.getModel();
		GlHelper.glBindVertexArray(rawModel.getVaoID());
		GlHelper.glEnableVertexAttribArray(0);
		GlHelper.glEnableVertexAttribArray(1);
		GlHelper.glEnableVertexAttribArray(2);
		ModelTexture texture = model.getTexture();
		this.shader.loadNumberOfRows(texture.getNumberOfRows());
		if(texture.isHasTransparency())
		{
			GlHelper.disableCulling();
		}
		this.shader.loadFakeLightingVarible(texture.isUseFakeLighting());
		this.shader.loadShineVaribles(texture.getShineDumper(), texture.getReflectivity());
		TextureManager.activeTexture0();
		TextureManager.bindTexture2d(model.getTexture().getTextureId());
		this.shader.loadUseSpecularMap(texture.hasSpecularMap());
		if(texture.hasSpecularMap())
		{
			TextureManager.activeTexture1();
			TextureManager.bindTexture2d(texture.getSpecularMap());
		}
	}
	
	private void unbindTexturedModel()
	{
		GlHelper.enableCulling();
		GlHelper.glDisableVertexAttribArray(0);
		GlHelper.glDisableVertexAttribArray(1);
		GlHelper.glDisableVertexAttribArray(2);
		GlHelper.glBindVertexArray(0);
	}
	
	private void prepareInstance(GameObject entity)
	{
		Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPositionVector(), 
				entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());
		this.shader.loadTransformationMatrix(transformationMatrix);
		this.shader.loadOffset(entity.getTextureXOffset(), entity.getTextureYOffset());
	}
	
	/**
	 * This method a render entities, models, textures and all stuff.
	 * OLD METHOD!!!!!!!!!!!!
	 */
	/**
	public void render(Entity entity, StaticShader shader)
	{
		TexturedModel model = entity.getModel();
		Model rawModel = model.getModel();
		GlHelper.glBindVertexArray(rawModel.getVaoID());
		GlHelper.glEnableVertexAttribArray(0);
		GlHelper.glEnableVertexAttribArray(1);
		GlHelper.glEnableVertexAttribArray(2);
		Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPosition(), 
				entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());
		shader.loadTransformationMatrix(transformationMatrix);
		ModelTexture texture = model.getTexture();
		shader.loadShineVaribles(texture.getShineDumper(), texture.getReflectivity());
		GlHelper.glActiveTexture(GL13.GL_TEXTURE0);
		GlHelper.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getTextureID());
		GlHelper.glDrawElements(GL11.GL_TRIANGLES, rawModel.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		GlHelper.glDisableVertexAttribArray(0);
		GlHelper.glDisableVertexAttribArray(1);
		GlHelper.glDisableVertexAttribArray(2);
		GlHelper.glBindVertexArray(0);
	}
	*/

}
