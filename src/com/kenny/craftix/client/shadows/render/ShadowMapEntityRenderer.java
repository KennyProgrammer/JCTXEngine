package com.kenny.craftix.client.shadows.render;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.util.vector.Matrix4f;

import com.kenny.craftix.client.entity.GameObject;
import com.kenny.craftix.client.renderer.GlHelper;
import com.kenny.craftix.client.renderer.models.Model;
import com.kenny.craftix.client.renderer.models.TexturedModel;
import com.kenny.craftix.client.settings.InGameSettings;
import com.kenny.craftix.utils.crash.Crash;
import com.kenny.craftix.utils.math.Maths;

public class ShadowMapEntityRenderer 
{
	/***
	 * Its matrix cointains a projection, view matriceis.
	 */
	private Matrix4f projectionViewMatrix;
	private ShadowShader shader;
	
	protected ShadowMapEntityRenderer(ShadowShader shader, Matrix4f projectionViewMatrix) 
	{
		this.shader = shader;
		this.projectionViewMatrix = projectionViewMatrix;
	}

	/**
	 * Renders entieis to the shadow map. Each model is first bound and then all
	 * of the entities using that model are rendered to the shadow map.
	 */
	protected void render(Map<TexturedModel, List<GameObject>> entities) 
	{
		for (TexturedModel model : entities.keySet()) 
		{
			Model rawModel = model.getModel();
			bindModel(rawModel);
			GlHelper.glActiveTexture(GL13.GL_TEXTURE0);
			GlHelper.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getTextureId());
			if(model.getTexture().isHasTransparency())
			{
				GlHelper.disableCulling();
			}
			for (GameObject object : entities.get(model)) 
			{
				if(object.isRender() && InGameSettings.useShadowsIn)
				{
					this.prepareInstance(object);
					GlHelper.glDrawElements(GL11.GL_TRIANGLES, rawModel.getVertexCount(),
							GL11.GL_UNSIGNED_INT, 0);
				}
			}
			if(model.getTexture().isHasTransparency())
			{
				GlHelper.enableCulling();
			}
		}
		GlHelper.glDisableVertexAttribArray(0);
		GlHelper.glDisableVertexAttribArray(1);
		GlHelper.glBindVertexArray(0);
	}

	/**
	 * Binds a raw model before rendering. Only the attribute 0 is enabled here
	 * because that is where the positions are stored in the VAO, and only the
	 * positions are required in the vertex shader.
	 */
	private void bindModel(Model rawModel) 
	{
		try 
		{
			GlHelper.glBindVertexArray(rawModel.getVaoID());
			GlHelper.glEnableVertexAttribArray(0);
			GlHelper.glEnableVertexAttribArray(1);
		} catch (Exception e) 
		{
			Crash.crashModelNotFound = true;
		}
	}

	/**
	 * Prepares an entity to be rendered. The model matrix is created in the
	 * usual way and then multiplied with the projection and view matrix (often
	 * in the past we've done this in the vertex shader) to create the
	 * mvp-matrix. This is then loaded to the vertex shader as a uniform.
	 */
	private void prepareInstance(GameObject entity) 
	{
		Matrix4f modelMatrix = Maths.createTransformationMatrix(entity.getPositionVector(),
				entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());
		Matrix4f mvpMatrix = Matrix4f.mul(projectionViewMatrix, modelMatrix, null);
		this.shader.loadMvpMatrix(mvpMatrix);
	}

}
