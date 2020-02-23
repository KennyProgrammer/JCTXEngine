package com.kenny.craftix.client.renderer.normalMapping;

import java.util.List;
import java.util.Map;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector4f;

import com.kenny.craftix.client.entity.GameObject;
import com.kenny.craftix.client.entity.EntityCamera;
import com.kenny.craftix.client.entity.Light;
import com.kenny.craftix.client.renderer.GlHelper;
import com.kenny.craftix.client.renderer.WorldRenderer;
import com.kenny.craftix.client.renderer.GlHelper.Texture;
import com.kenny.craftix.client.renderer.models.Model;
import com.kenny.craftix.client.renderer.models.TexturedModel;
import com.kenny.craftix.client.renderer.textures.ModelTexture;
import com.kenny.craftix.client.renderer.textures.TextureManager;
import com.kenny.craftix.utils.math.Maths;

public class NMRenderer 
{

	/**Get the Normal Map shader for renderer*/
	private NMShader shader;
	public static final float GRADIENT = 5.0f;

	public NMRenderer(Matrix4f projectionMatrix) 
	{
		this.shader = new NMShader();
		this.shader.start();
		this.shader.loadProjectionMatrix(projectionMatrix);
		this.shader.connectTextureUnits();
		this.shader.stop();
	}

	public void render(Map<TexturedModel, List<GameObject>> entities, Vector4f clipPlane, List<Light> lights, 
			EntityCamera camera, WorldRenderer worldRendererIn) 
	{
		this.shader.start();
		prepare(clipPlane, lights, camera);
		this.shader.loadSkyColour(WorldRenderer.FOG_R, WorldRenderer.FOG_G, WorldRenderer.FOG_B);
		this.shader.loadDensity(worldRendererIn.renderDistance.fogDistance);
		this.shader.loadGradient(GRADIENT);
		for (TexturedModel model : entities.keySet()) {
			this.prepareTexturedModel(model);
			List<GameObject> batch = entities.get(model);
			for (GameObject entity : batch) {
				this.prepareInstance(entity);
				GlHelper.glDrawElements(Texture.TRIANGLES, model.getModel().getVertexCount(), Texture.UNSINGED_INT, 0);
			}
			this.unbindTexturedModel();
		}
		this.shader.stop();
	}
	
	public void cleanUp()
	{
		this.shader.cleanUp();
	}

	private void prepareTexturedModel(TexturedModel model) 
	{
		Model rawModel = model.getModel();
		GlHelper.glBindVertexArray(rawModel.getVaoID());
		GlHelper.glEnableVertexAttribArray(0);
		GlHelper.glEnableVertexAttribArray(1);
		GlHelper.glEnableVertexAttribArray(2);
		GlHelper.glEnableVertexAttribArray(3);
		ModelTexture texture = model.getTexture();
		this.shader.loadNumberOfRows(texture.getNumberOfRows());
		if (texture.isHasTransparency()) 
		{
			GlHelper.disableCulling();
		}
		this.shader.loadShineVariables(texture.getShineDumper(), texture.getReflectivity());
		TextureManager.activeTexture0();
		TextureManager.bindTexture2d(model.getTexture().getTextureId());
		TextureManager.activeTexture1();
		TextureManager.bindTexture2d(model.getTexture().getNormalMap());
		this.shader.loadUseSpecularMap(texture.hasSpecularMap());
		if(texture.hasSpecularMap())
		{
			TextureManager.activeTexture2();
			TextureManager.bindTexture2d(model.getTexture().getSpecularMap());
		}
	}

	private void unbindTexturedModel() 
	{
		GlHelper.enableCulling();
		GlHelper.glDisableVertexAttribArray(0);
		GlHelper.glDisableVertexAttribArray(1);
		GlHelper.glDisableVertexAttribArray(2);
		GlHelper.glDisableVertexAttribArray(3);
		GlHelper.glBindVertexArray(0);
	}

	private void prepareInstance(GameObject entity) 
	{
		Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPositionVector(), entity.getRotX(),
				entity.getRotY(), entity.getRotZ(), entity.getScale());
		this.shader.loadTransformationMatrix(transformationMatrix);
		this.shader.loadOffset(entity.getTextureXOffset(), entity.getTextureYOffset());
	}

	private void prepare(Vector4f clipPlane, List<Light> lights, EntityCamera camera) 
	{
		this.shader.loadClipPlane(clipPlane);
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		
		this.shader.loadLights(lights, viewMatrix);
		this.shader.loadViewMatrix(viewMatrix);
	}

}
