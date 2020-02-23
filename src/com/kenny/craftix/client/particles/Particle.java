package com.kenny.craftix.client.particles;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import com.kenny.craftix.client.entity.EntityCamera;
import com.kenny.craftix.client.entity.player.EntityPlayer;
import com.kenny.craftix.utils.Timer;

public class Particle 
{
	/**Represent a patricle in 3d world*/
	private Vector3f position;
	/**Velocity show witch direction particle of traveling.*/
	private Vector3f velocity;
	/**Its simple a gravitation effect.*/
	private float gravityEffect;
	/**Represents how much particle is still alive.*/
	private float lifeLength;
	/**This is a parts of trasformation matrix*/
	private float rotation;
	private float scale;
	/**Its a instance texture for paritcle.*/
	private ParticleTexture texture;
	
	private Vector2f texOffset1 = new Vector2f();
	private Vector2f texOffset2 = new Vector2f();
	/**
	 * This is using the same parameters but with changed some.
	 */
	private Vector3f reusableChange = new Vector3f();
	private float blend;
	
	private float elapsedTime = 0;
	private float distance;
	private Timer timer = new Timer();
	/***
	 * Check of particle is alive or not.
	 */
	public boolean isAlive = false;
	
	public Particle(ParticleTexture texture, Vector3f position, Vector3f velocity, 
			float gravityEffect, float lifeLength, float rotation,float scale) 
	{
		this.texture = texture;
		this.position = position;
		this.velocity = velocity;
		this.gravityEffect = gravityEffect;
		this.lifeLength = lifeLength;
		this.rotation = rotation;
		this.scale = scale;
		ParticleMaster.addParticle(this);
	}

	
	public void setActive(ParticleTexture texture, Vector3f position, Vector3f velocity, 
			float gravityEffect, float lifeLength, float rotation,float scale) 
	{
		this.isAlive = true;
		this.position = position;
		this.velocity = velocity;
		this.gravityEffect = gravityEffect;
		this.lifeLength = lifeLength;
		this.rotation = rotation;
		this.scale = scale;
		this.texture = texture;
	}

	
	public float getDistance() 
	{
		return distance;
	}


	public Vector2f getTexOffset1() 
	{
		return texOffset1;
	}

	public Vector2f getTexOffset2() 
	{
		return texOffset2;
	}

	public float getBlend() 
	{
		return blend;
	}

	public ParticleTexture getTexture() 
	{
		return texture;
	}

	public void setTexture(ParticleTexture texture) {
		this.texture = texture;
	}

	public Vector3f getPosition() 
	{
		return position;
	}

	public float getRotation() 
	{
		return rotation;
	}

	public float getScale() 
	{
		return scale;
	}
	
	protected boolean update(EntityCamera camera, EntityPlayer player)
	{
		this.velocity.y += player.getEntityGravity() * gravityEffect * 
				this.timer.getFrameTimeSeconds();
		this.reusableChange.set(this.velocity);
		this.reusableChange.scale(this.timer.getFrameTimeSeconds());
		Vector3f.add(this.reusableChange, this.position, this.position);
		this.distance = Vector3f.sub(camera.getPosition(), position, null).lengthSquared();
		this.updateTextureCoordsInfo();
		this.elapsedTime += this.timer.getFrameTimeSeconds();
			return elapsedTime < lifeLength;
	}
	
	/**
	 * protected boolean update(EntityCamera camera)
	{
		this.velocity.y += EntityPlayer.GRAVITY * gravityEffect * 
				this.timer.getFrameTimeSeconds();
		Vector3f change = new Vector3f(this.velocity);
		change.scale(this.timer.getFrameTimeSeconds());
		Vector3f.add(change, this.position, this.position);
		this.distance = Vector3f.sub(camera.getPosition(), position, null).lengthSquared();
		this.updateTextureCoordsInfo();
		this.elapsedTime += this.timer.getFrameTimeSeconds();
			return elapsedTime < lifeLength;
	}
	
	 */
	
	private void updateTextureCoordsInfo()
	{
		float lifeFactor = elapsedTime / lifeLength;
		int stageCount = texture.getNumberOfRows() * texture.getNumberOfRows();
		float atlasProgression = lifeFactor * stageCount;
		int index1 = (int) Math.floor(atlasProgression);
		int index2 = index1 > stageCount - 1 ? index1 + 1 : index1;
		this.blend = atlasProgression % 1;
		this.setTextureOffset(texOffset1, index1);
		this.setTextureOffset(texOffset2, index2);
	}
	
	private void setTextureOffset(Vector2f offset, int index)
	{
		int column = index % texture.getNumberOfRows();
		int row = index / texture.getNumberOfRows();
		offset.x = (float) column / texture.getNumberOfRows();
		offset.y = (float) row / texture.getNumberOfRows();
	}
	
}
