package com.kenny.craftix.client.renderer;

import java.util.List;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import com.kenny.craftix.client.renderer.GlHelper.Texture;
import com.kenny.craftix.client.renderer.models.Model;
import com.kenny.craftix.client.renderer.textures.TextureManager;
import com.kenny.craftix.client.shaders.TerrainShader;
import com.kenny.craftix.client.shadows.ShadowBox;
import com.kenny.craftix.utils.math.Maths;
import com.kenny.craftix.world.terrain.Terrain;
import com.kenny.craftix.world.terrain.TerrainTexturePack;

public class TerrainRenderer 
{
	public static final float GRADIENT = 5.0f;
	public static final float TRANSITION_DISTANCE = 10.0f;
	public static final int PCF_COUNT = 2;
	public static final float TOTAL_TEXES = (float) ((PCF_COUNT * 2.0 + 1.0) * (PCF_COUNT * 2.0 + 1.0));
	
	/**This is a shader for terrain*/
	private TerrainShader shader;
	
	public TerrainRenderer(TerrainShader shader, Matrix4f projectionMatrix)
	{
		this.shader = shader;
		this.shader.start();
		this.shader.loadProjectionMatrix(projectionMatrix);
		this.shader.connectTextureUnits();
		this.shader.stop();
	}
	
	public void render(List<Terrain> terrains, Matrix4f toShadowSpace, WorldRenderer worldRenderer)
	{
		this.shader.loadToShadowMapSpace(toShadowSpace);
		this.shader.loadDensity(worldRenderer.renderDistance.fogDistance);
		this.shader.loadGradient(GRADIENT);
		this.shader.loadPcfCount(PCF_COUNT);
		this.shader.loadTotalTexels(TOTAL_TEXES);
		this.shader.loadShadowDistance(ShadowBox.SHADOW_DISTANCE);
		this.shader.loadTransitionDistance(TRANSITION_DISTANCE);
		
		for (Terrain terrain : terrains) 
		{
			this.prepareTerrain(terrain);
			loadModelMatrix(terrain);
			GlHelper.glDrawElements(Texture.TRIANGLES, terrain.getModel().getVertexCount(), 
					Texture.UNSINGED_INT, 0);
			this.unbindTerrain();
		}
	}
	
	private void prepareTerrain(Terrain terrain)
	{
		Model rawModel = terrain.getModel();
		GlHelper.glBindVertexArray(rawModel.getVaoID());
		GlHelper.glEnableVertexAttribArray(0);
		GlHelper.glEnableVertexAttribArray(1);
		GlHelper.glEnableVertexAttribArray(2);
		this.bindTextures(terrain);
		this.shader.loadShineVaribles(1, 0);
	}
	
	private void bindTextures(Terrain terrain)
	{
		TerrainTexturePack texturePack = terrain.getTexturePack();
		TextureManager.activeTexture0();
		TextureManager.bindTexture2d(texturePack.getBackgroundTexture().getTextureId());
		TextureManager.activeTexture1();
		TextureManager.bindTexture2d(texturePack.getrTexture().getTextureId());
		TextureManager.activeTexture2();
		TextureManager.bindTexture2d(texturePack.getbTexture().getTextureId());
		TextureManager.activeTexture3();
		TextureManager.bindTexture2d(texturePack.getgTexture().getTextureId());
		TextureManager.activeTexture4();
		TextureManager.bindTexture2d(terrain.getBlendMap().getTextureId());
	}
	
	private void unbindTerrain()
	{
		GlHelper.glDisableVertexAttribArray(0);
		GlHelper.glDisableVertexAttribArray(1);
		GlHelper.glDisableVertexAttribArray(2);
		GlHelper.glBindVertexArray(0);
	}
	
	private void loadModelMatrix(Terrain terrain)
	{
		Matrix4f transformationMatrix = Maths.createTransformationMatrix(new Vector3f(terrain.getX(), 0, terrain.getZ()), 
				0, 0, 0, 1);
		shader.loadTransformationMatrix(transformationMatrix);
	}
}
