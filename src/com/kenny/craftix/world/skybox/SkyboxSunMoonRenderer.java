package com.kenny.craftix.world.skybox;

import org.lwjgl.util.vector.Matrix4f;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.entity.EntityCamera;
import com.kenny.craftix.client.loader.Loader;
import com.kenny.craftix.client.loader.TexturesLoader;
import com.kenny.craftix.client.renderer.GlHelper;
import com.kenny.craftix.client.renderer.GlHelper.Texture;
import com.kenny.craftix.client.renderer.models.Model;
import com.kenny.craftix.client.renderer.textures.TextureManager;

public class SkyboxSunMoonRenderer 
{
	public static final String SKYBOX_FOLDER = new String("skybox/");
	/**This is a Raw Model for skybox cube*/
	private Model cube;
	/**This is a textures for day time*/
	private int skyboxSunMoonTextures;
	private SkyboxSunMoonShader shader;
	/**This is a size of skybox*/
	private static final float SIZE = 500f;
	/**Its a simple verties of skybox*/
	private static final float[] VERTICES = {        
		    -SIZE,  SIZE, -SIZE,
		    -SIZE, -SIZE, -SIZE,
		    SIZE, -SIZE, -SIZE,
		     SIZE, -SIZE, -SIZE,
		     SIZE,  SIZE, -SIZE,
		    -SIZE,  SIZE, -SIZE,

		    -SIZE, -SIZE,  SIZE,
		    -SIZE, -SIZE, -SIZE,
		    -SIZE,  SIZE, -SIZE,
		    -SIZE,  SIZE, -SIZE,
		    -SIZE,  SIZE,  SIZE,
		    -SIZE, -SIZE,  SIZE,

		     SIZE, -SIZE, -SIZE,
		     SIZE, -SIZE,  SIZE,
		     SIZE,  SIZE,  SIZE,
		     SIZE,  SIZE,  SIZE,
		     SIZE,  SIZE, -SIZE,
		     SIZE, -SIZE, -SIZE,

		    -SIZE, -SIZE,  SIZE,
		    -SIZE,  SIZE,  SIZE,
		     SIZE,  SIZE,  SIZE,
		     SIZE,  SIZE,  SIZE,
		     SIZE, -SIZE,  SIZE,
		    -SIZE, -SIZE,  SIZE,

		    -SIZE,  SIZE, -SIZE,
		     SIZE,  SIZE, -SIZE,
		     SIZE,  SIZE,  SIZE,
		     SIZE,  SIZE,  SIZE,
		    -SIZE,  SIZE,  SIZE,
		    -SIZE,  SIZE, -SIZE,

		    -SIZE, -SIZE, -SIZE,
		    -SIZE, -SIZE,  SIZE,
		     SIZE, -SIZE, -SIZE,
		     SIZE, -SIZE, -SIZE,
		    -SIZE, -SIZE,  SIZE,
		     SIZE, -SIZE,  SIZE
		};
	
	private static String[] SKYBOX_SUN_MOON = 
	{
		SKYBOX_FOLDER + "s_alpha",
		SKYBOX_FOLDER + "s_alpha",
		SKYBOX_FOLDER + "s_sun", //up
		SKYBOX_FOLDER + "s_moon", // down
		SKYBOX_FOLDER + "s_alpha",
		SKYBOX_FOLDER + "s_alpha"
	};

	public SkyboxSunMoonRenderer(Craftix craftixIn, Loader loader, TexturesLoader textureLoader, Matrix4f projectionMatrix)
	{
		this.cube = loader.loadToVao(VERTICES, 3);
		this.skyboxSunMoonTextures = textureLoader.loadCubeMapNew(SKYBOX_SUN_MOON);
		this.shader = new SkyboxSunMoonShader();
		this.shader.start();
		this.shader.connectTextureUnits(craftixIn);
		this.shader.loadProjectionMatrix(projectionMatrix);
		this.shader.stop();
	}
	
	public void render(EntityCamera camera, float r, float g, float b)
	{
		this.shader.start();
		this.shader.loadViewMatrix(camera);
		this.shader.loadFogColour(r, g, b);
		GlHelper.glBindVertexArray(cube.getVaoID());
		GlHelper.glEnableVertexAttribArray(0);
		this.bindTextures();
		GlHelper.glDrawArrays(Texture.TRIANGLES, 0, cube.getVertexCount());
		GlHelper.glDisableVertexAttribArray(0);
		GlHelper.glBindVertexArray(0);
		this.shader.stop();
	}
	
	private void bindTextures()
	{
		int bindTexture0;
		bindTexture0 = this.getSunMoon();
		TextureManager.activeTexture0();
		TextureManager.bindTextureCubeMap(bindTexture0);
		
	}
	
	public int getSunMoon()
	{
		return this.skyboxSunMoonTextures;
	}
	
}
