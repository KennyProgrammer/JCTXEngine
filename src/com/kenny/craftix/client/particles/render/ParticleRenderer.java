package com.kenny.craftix.client.particles.render;

import java.nio.FloatBuffer;
import java.util.List;
import java.util.Map;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import com.kenny.craftix.client.entity.EntityCamera;
import com.kenny.craftix.client.loader.Loader;
import com.kenny.craftix.client.particles.Particle;
import com.kenny.craftix.client.particles.ParticleTexture;
import com.kenny.craftix.client.renderer.GlHelper;
import com.kenny.craftix.client.renderer.GlHelper.Blend;
import com.kenny.craftix.client.renderer.GlHelper.Texture;
import com.kenny.craftix.client.renderer.models.Model;
import com.kenny.craftix.client.renderer.textures.TextureManager;
import com.kenny.craftix.client.settings.InGameSettings;
import com.kenny.craftix.utils.math.Maths;

public class ParticleRenderer 
{
	
	private static final float[] VERTICES = {-0.5f, 0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f, -0.5f};
	/**Its means so how many can be create instances.*/
	private static final int MAX_INSTANCES = 10000;
	/**Its a length of each instance.*/
	private static final int INSTANCE_DATA_LENGTH = 21;
	
	/**Here we create a float buffer. Its max instances * instance_data_length*/
	private static final FloatBuffer buffer = BufferUtils.createFloatBuffer(MAX_INSTANCES * INSTANCE_DATA_LENGTH);
	
	/**Is a just quad for particles*/
	private Model quad;
	/**Load particle shader*/
	private ParticleShader shader;
	/**Its a core of entire engine*/
	private Loader loader = new Loader();
	private int vbo;
	private int pointer = 0;
	
	public ParticleRenderer(Matrix4f projectionMatrix)
	{
		this.vbo = this.loader.createEmptyVbo(INSTANCE_DATA_LENGTH * MAX_INSTANCES);		
		this.quad = this.loader.loadToVao(VERTICES, 2);
		this.loader.addInstancedAttribute(quad.getVaoID(), vbo, 1, 4, INSTANCE_DATA_LENGTH, 0);
		this.loader.addInstancedAttribute(quad.getVaoID(), vbo, 2, 4, INSTANCE_DATA_LENGTH, 4);
		this.loader.addInstancedAttribute(quad.getVaoID(), vbo, 3, 4, INSTANCE_DATA_LENGTH, 8);
		this.loader.addInstancedAttribute(quad.getVaoID(), vbo, 4, 4, INSTANCE_DATA_LENGTH, 12);
		this.loader.addInstancedAttribute(quad.getVaoID(), vbo, 5, 4, INSTANCE_DATA_LENGTH, 16);
		this.loader.addInstancedAttribute(quad.getVaoID(), vbo, 6, 1, INSTANCE_DATA_LENGTH, 20);
		this.shader = new ParticleShader();
		this.shader.start();
		this.shader.loadProjectionMatrix(projectionMatrix);
		this.shader.stop();
	}
	
	public void render(Map<ParticleTexture, List<Particle>> particles, EntityCamera camera)
	{
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		this.prepare();
		if(InGameSettings.renderParticlesIn)
		{
			for (ParticleTexture texture : particles.keySet()) 
			{	
				this.bindTexture(texture);
				List<Particle> particleList = particles.get(texture);
				this.pointer = 0;
				float[] vboData = new float[particleList.size() * INSTANCE_DATA_LENGTH];
				for (Particle particle : particleList) 
				{

					this.updateModelViewMatrix(particle.getPosition(), particle.getRotation(), 
							particle.getScale(), viewMatrix, vboData);
					this.updateTexCoordsInfo(particle, vboData);
				}
				
				this.loader.updateVbo(this.vbo, vboData, buffer);
				GlHelper.glDrawArraysInstanced(Texture.TRIANGLE_STRIP, 0, this.quad.getVertexCount(), particleList.size());
			}
		}
		this.finishRendering();
	}

	private void bindTexture(ParticleTexture texture)
	{
		if(texture.usesAdditives())
		{
			GlHelper.glBlendFunction(Blend.SRC_ALPHA, Blend.ONE);
		}
		else
		{
			GlHelper.glBlendFunction(Blend.SRC_ALPHA, Blend.ONE_MINUS_SRC_ALPHA);
		}
		TextureManager.activeTexture0();
		TextureManager.bindTexture2d(texture.getTextureID());
		this.shader.loadNumberOfRows(texture.getNumberOfRows());
	}
	
	public void cleanUp()
	{
		this.shader.cleanUp();
	}
	
	private void updateTexCoordsInfo(Particle particle, float[] data)
	{
		data[pointer++] = particle.getTexOffset1().x;
		data[pointer++] = particle.getTexOffset1().y;
		data[pointer++] = particle.getTexOffset2().x;
		data[pointer++] = particle.getTexOffset2().y;
		data[pointer++] = particle.getBlend();
	}
	
	
	private void updateModelViewMatrix(Vector3f position, float rotation,
			float scale, Matrix4f viewMatrix, float[] vboData)
	{
		Matrix4f modelMatrix = new Matrix4f();
		Matrix4f.translate(position, modelMatrix, modelMatrix);
		modelMatrix.m00 = viewMatrix.m00;
		modelMatrix.m01 = viewMatrix.m10;
		modelMatrix.m02 = viewMatrix.m20;
		modelMatrix.m10 = viewMatrix.m01;
		modelMatrix.m11 = viewMatrix.m11;
		modelMatrix.m12 = viewMatrix.m21;
		modelMatrix.m20 = viewMatrix.m02;
		modelMatrix.m21 = viewMatrix.m12;
		modelMatrix.m22 = viewMatrix.m22;
		Matrix4f.rotate((float) Math.toRadians(rotation), new Vector3f(0, 0, 1), modelMatrix, modelMatrix);
		Matrix4f.scale(new Vector3f(scale, scale, scale), modelMatrix, modelMatrix);
		Matrix4f modelViewMatrix = Matrix4f.mul(viewMatrix, modelMatrix, null);
		this.storeMatrixData(modelViewMatrix, vboData);
		
	}

	
	private void storeMatrixData(Matrix4f matrix, float[] vboData)
	{
		vboData[pointer++] = matrix.m00;
		vboData[pointer++] = matrix.m01;
		vboData[pointer++] = matrix.m02;
		vboData[pointer++] = matrix.m03;
		vboData[pointer++] = matrix.m10;
		vboData[pointer++] = matrix.m11;
		vboData[pointer++] = matrix.m12;
		vboData[pointer++] = matrix.m13;
		vboData[pointer++] = matrix.m20;
		vboData[pointer++] = matrix.m21;
		vboData[pointer++] = matrix.m22;
		vboData[pointer++] = matrix.m23;
		vboData[pointer++] = matrix.m30;
		vboData[pointer++] = matrix.m31;
		vboData[pointer++] = matrix.m32;
		vboData[pointer++] = matrix.m33;
	}
	
	private void prepare()
	{
		this.shader.start();
		GlHelper.glBindVertexArray(quad.getVaoID());
		GlHelper.glEnableVertexAttribArray(0);
		GlHelper.glEnableVertexAttribArray(1);
		GlHelper.glEnableVertexAttribArray(2);
		GlHelper.glEnableVertexAttribArray(3);
		GlHelper.glEnableVertexAttribArray(4);
		GlHelper.glEnableVertexAttribArray(5);
		GlHelper.glEnableVertexAttribArray(6);
		GlHelper.enableBlend();
		GlHelper.glBlendFunction(Blend.SRC_ALPHA, Blend.ONE_MINUS_SRC_ALPHA);
		GlHelper.glDepthMask(false);
	}
	
	
	private void finishRendering()
	{
		GlHelper.glDepthMask(true);
		GlHelper.disableBlend();
		GlHelper.glDisableVertexAttribArray(0);
		GlHelper.glDisableVertexAttribArray(1);
		GlHelper.glDisableVertexAttribArray(2);
		GlHelper.glDisableVertexAttribArray(3);
		GlHelper.glDisableVertexAttribArray(4);
		GlHelper.glDisableVertexAttribArray(5);
		GlHelper.glDisableVertexAttribArray(6);
		GlHelper.glBindVertexArray(0);
		this.shader.stop();
	}

}
