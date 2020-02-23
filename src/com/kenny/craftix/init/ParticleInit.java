package com.kenny.craftix.init;
import org.lwjgl.util.vector.Vector3f;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.CraftixOld;
import com.kenny.craftix.client.loader.Loader;
import com.kenny.craftix.client.loader.TexturesLoader;
import com.kenny.craftix.client.particles.ParticleSystem;
import com.kenny.craftix.client.particles.ParticleTexture;

public class ParticleInit 
{
	/**Its a core of the game*/
	public Loader openGlObjectsLoader = new Loader();
	/**Load all texture to particle.*/
	public TexturesLoader textureLoader = new TexturesLoader(); 
	public CraftixOld craftix;
	
	/**Patricle List*/
	public ParticleSystem p_Smoke;
	public ParticleSystem p_Fire;
	public ParticleSystem p_Star;
	public ParticleSystem p_Cosmic;
	
	/**Particle Texture List*/
	public ParticleTexture t_Smoke;
	public ParticleTexture t_Fire;
	public ParticleTexture t_Star;
	public ParticleTexture t_Cosmic;
	
	/**
	 * Add a new partice to the game.
	 */
	public ParticleSystem addParticle(ParticleTexture texture, float pps, float speed, float gravity, float lifeLength, 
			float scale)
	{
		return new ParticleSystem(texture, pps, speed, gravity, lifeLength, scale);
	}
	
	/***
	 * Load particle with texture and other settings values in the world.
	 */
	public void loadParticles()
	{
		Craftix.LOGGER.info("Loading world: 90%");
		this.loadParticleTexture();
		
		this.p_Smoke = addParticle(t_Smoke, 50, 25, 0.3f, 2, 15);
			p_Smoke.randomizeRotation();
			p_Smoke.setDirection(new Vector3f(0, 1, 0), 0.1f);
			p_Smoke.setLifeError(0.1f);
			p_Smoke.setSpeedError(0.4f);
			p_Smoke.setScaleError(0.8f);
			
		this.p_Fire = addParticle(t_Fire, 10, 10, 0.4f, 1, 10);
			p_Fire.randomizeRotation();
			p_Fire.setDirection(new Vector3f(0, 1, 0), 0.1f);
			p_Fire.setLifeError(0.1f);
			p_Fire.setScaleError(0.8f);
			p_Fire.setSpeedError(0.4f);
			
		this.p_Star = addParticle(t_Star, 40, 20, 0.4f, 2, 2);
			p_Star.randomizeRotation();
			p_Star.setDirection(new Vector3f(1, 0, 0), 0.1f);
			p_Star.setLifeError(0.1f);
			p_Star.setScaleError(0.8f);
			p_Star.setSpeedError(0.4f);
			
		this.p_Cosmic = addParticle(t_Cosmic, 30, 40, 0.6f, 1, 2);
			p_Cosmic.randomizeRotation();
			p_Cosmic.setDirection(new Vector3f(0, 1, 0), 0.2f);
			p_Cosmic.setLifeError(0.2f);
			p_Cosmic.setScaleError(0.8f);
			p_Cosmic.setSpeedError(0.4f);
			
	}
	
	/**
	 * Just simply load partile to the world.
	 */
	public void loadParticleTexture()
	{
		this.t_Smoke = new ParticleTexture(this.textureLoader.loadTexture("particles/smoke"), 8, false);
		this.t_Fire = new ParticleTexture(this.textureLoader.loadTexture("particles/fire"), 8, true);
		this.t_Star = new ParticleTexture(this.textureLoader.loadTexture("particles/star"), 1, false);
		this.t_Cosmic = new ParticleTexture(this.textureLoader.loadTexture("particles/cosmic"), 4, false);
	}
	
	/***
	 *	Generate particles in the world. This method should be init in update while loop.
	 *	Because particle create and delete every frame. 
	 */
	public void generateParticles()
	{
		this.p_Smoke.generateParticles(new Vector3f(240f, 5f, -214f));
		this.p_Fire.generateParticles(new Vector3f(240f, -6f, -254f));
		
	}
}
