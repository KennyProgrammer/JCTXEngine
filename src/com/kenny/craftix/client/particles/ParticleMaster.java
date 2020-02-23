package com.kenny.craftix.client.particles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.lwjgl.util.vector.Matrix4f;

import com.kenny.craftix.client.entity.EntityCamera;
import com.kenny.craftix.client.entity.player.EntityPlayer;
import com.kenny.craftix.client.particles.render.ParticleRenderer;

public class ParticleMaster 
{
	
	private static Map<ParticleTexture, List<Particle>> particles = new HashMap<ParticleTexture, List<Particle>>();
	private static ParticleRenderer renderer;
	public static Particle particle;
	
	public static void init(Matrix4f projectionMatrix)
	{
		renderer = new ParticleRenderer(projectionMatrix);
	}
	
	public static void update(EntityCamera camera, EntityPlayer player)
	{
		Iterator<Entry<ParticleTexture, List<Particle>>> mapIterator = particles.entrySet().iterator();
		while(mapIterator.hasNext())
		{
			List<Particle> list = mapIterator.next().getValue();
			Iterator<Particle> iterator = list.iterator();
			while (iterator.hasNext()) 
			{
				Particle p = iterator.next();
				boolean stillAlive = p.update(camera, player);
				if(!stillAlive)
				{
					iterator.remove();
					if(list.isEmpty())
					{
						mapIterator.remove();
					}
				}
			}
			
			InsertionSort.sortHighToLow(list);
		}

	}
	
	public static void renderParticles(EntityCamera camera)
	{
		renderer.render(particles, camera);
	}
	
	public static void cleanUp()
	{
		renderer.cleanUp();
	}
	
	public static void addParticle(Particle particle)
	{
		List<Particle> list = particles.get(particle.getTexture());
		if(list == null)
		{
			list = new ArrayList<Particle>();
			particles.put(particle.getTexture(), list);
		}
		list.add(particle);
	}
}
