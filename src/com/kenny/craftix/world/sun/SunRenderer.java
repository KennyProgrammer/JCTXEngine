package com.kenny.craftix.world.sun;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import com.kenny.craftix.client.entity.EntityCamera;
import com.kenny.craftix.client.loader.Loader;
import com.kenny.craftix.client.renderer.GlHelper;
import com.kenny.craftix.client.renderer.GlHelper.Blend;
import com.kenny.craftix.client.renderer.GlHelper.Texture;
import com.kenny.craftix.client.renderer.WorldRenderer;
import com.kenny.craftix.client.renderer.models.Model;
import com.kenny.craftix.client.renderer.models.ModelGui;
import com.kenny.craftix.client.renderer.textures.TextureManager;

public class SunRenderer 
{
	/**Shader for the sun.*/
	private SunShader sunShader;
	/**Model position vbo.*/
	protected ModelGui modelPos;
	/**Actually sun quad model.*/
	public Model quad;
	protected Loader openGlObjectsLoader = new Loader();
	
	
	public SunRenderer()
	{
		this.sunShader = new SunShader();
		this.modelPos = new ModelGui();
		this.quad = this.openGlObjectsLoader.loadToVao(this.modelPos.getModelQuad(), 4);
	}
	
	private void prepare(Sun sun, WorldRenderer worldRenderingIn, EntityCamera camera) 
	{
		GlHelper.enableBlend();
		GlHelper.glBlendFunction(Blend.SRC_ALPHA, Blend.ONE_MINUS_SRC_ALPHA);
		this.sunShader.start();
		Matrix4f mvpMat = this.createAndCalculateMvpMatrix(sun, worldRenderingIn, camera);
		this.sunShader.loadMvpMatrix(mvpMat);
		this.sunShader.connectTextureUnits();
		GlHelper.glBindVertexArray(0);
		TextureManager.activeTexture0();
		TextureManager.bindTexture2d(0);
	}
	
	public void render(Sun sun, WorldRenderer worldRender, EntityCamera camera) 
	{
		this.prepare(sun, worldRender, camera);
		GlHelper.glDrawArrays(Texture.TRIANGLE_STRIP, 0, 4);
		this.endRendering();
	}
	
	private void endRendering() 
	{
		GlHelper.glBindVertexArray(0);
		this.sunShader.stop();
		GlHelper.disableBlend();
	}
	
	private Matrix4f createAndCalculateMvpMatrix(Sun sun, WorldRenderer worldRendererIn, EntityCamera cameraIn) 
	{
		Matrix4f modelMatrix = new Matrix4f();
		Vector3f sunPos = sun.getWorldPosition(cameraIn.getPosition());
		Matrix4f.translate(sunPos, modelMatrix, modelMatrix);
		Matrix4f modelViewMat = applyViewMatrix(modelMatrix, cameraIn.getViewMatrix());
		Matrix4f.scale(new Vector3f(sun.getScale(), sun.getScale(), sun.getScale()), modelViewMat, modelViewMat);
		return Matrix4f.mul(worldRendererIn.getProjectionMatrix(), modelViewMat, null);
	}
	
	/** Basically we remove the rotation effect of the view matrix, 
	 *  so that the sun quad is always facing the camera.
	 *  
	 * @return The model-view matrix.
	 */
	private Matrix4f applyViewMatrix(Matrix4f modelMatrix, Matrix4f viewMatrix) 
	{
		modelMatrix.m00 = viewMatrix.m00;
		modelMatrix.m01 = viewMatrix.m10;
		modelMatrix.m02 = viewMatrix.m20;
		modelMatrix.m10 = viewMatrix.m01;
		modelMatrix.m11 = viewMatrix.m11;
		modelMatrix.m12 = viewMatrix.m21;
		modelMatrix.m20 = viewMatrix.m02;
		modelMatrix.m21 = viewMatrix.m12;
		modelMatrix.m22 = viewMatrix.m22;
			return Matrix4f.mul(viewMatrix, modelMatrix, null);
	}
	
	/**
	 * Return the sun shader component.
	 */
	public SunShader getSunShader()
	{
		return this.sunShader;
	}
	
	/**
	 * Clean up the sun shader.
	 */
	public void cleanUp() 
	{
		this.sunShader.cleanUp();
	}
}
