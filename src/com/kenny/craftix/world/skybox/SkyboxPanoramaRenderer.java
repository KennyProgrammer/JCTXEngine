package com.kenny.craftix.world.skybox;

import org.lwjgl.util.vector.Matrix4f;

import com.kenny.craftix.client.entity.EntityCamaraInMenu;
import com.kenny.craftix.client.loader.Loader;
import com.kenny.craftix.client.loader.TexturesLoader;
import com.kenny.craftix.client.renderer.GlHelper;
import com.kenny.craftix.client.renderer.GlHelper.Texture;
import com.kenny.craftix.client.renderer.models.Model;
import com.kenny.craftix.client.renderer.textures.TextureManager;

public class SkyboxPanoramaRenderer 
{
	/**This is a size of skybox panorama*/
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

	public static final String SKYBOX_FOLDER = new String("skybox/");
	/**These values are needed to adjust the rendering of the skybox*/
	public static final float LOWER_LIMIT = -690.0f;
	public static final float UPPER_LIMIT = -640.0f;
	/**This is a Raw Model for skybox cube*/
	private Model cube;
	/**This is a panorama textures in main menu.*/
	private int skyboxPanorama;
	private SkyboxPanoramaShader panoramaShader;
	
	private static String[] PANÎRAMA_FILES =
		{
			SKYBOX_FOLDER + "panorama_2", //right
			SKYBOX_FOLDER + "panorama_0", //left
			SKYBOX_FOLDER + "panorama_5", //up
			SKYBOX_FOLDER + "panorama_4", //down
			SKYBOX_FOLDER + "panorama_1", //back
			SKYBOX_FOLDER + "panorama_3"  //front
		};
	
	
	public SkyboxPanoramaRenderer(Loader loader, TexturesLoader textureLoader, Matrix4f projectionMatrix)
	{
		this.cube = loader.loadToVao(VERTICES, 3);
		this.skyboxPanorama = textureLoader.loadCubeMapNew(PANÎRAMA_FILES);
		this.panoramaShader = new SkyboxPanoramaShader();
		this.panoramaShader.start();
		this.panoramaShader.connectTextureUnits();
		this.panoramaShader.loadProjectionMatrix(projectionMatrix);
		this.panoramaShader.stop();
	}
	
	public void renderPanorama(EntityCamaraInMenu camera, float r, float g, float b)
	{
		this.panoramaShader.start();
		this.panoramaShader.loadViewMatrix(camera);
		this.panoramaShader.loadFogColour(r, g, b);
		this.panoramaShader.loadLowerLimit(LOWER_LIMIT);
		this.panoramaShader.loadUpperLimit(UPPER_LIMIT);
		GlHelper.glBindVertexArray(cube.getVaoID());
		GlHelper.glEnableVertexAttribArray(0);
		this.bindTextures();
		GlHelper.glDrawArrays(Texture.TRIANGLES, 0, cube.getVertexCount());
		GlHelper.glDisableVertexAttribArray(0);
		GlHelper.glBindVertexArray(0);
		this.panoramaShader.stop();
	}
	
	private void bindTextures()
	{
		int texture0 = skyboxPanorama;
		TextureManager.activeTexture0();
		GlHelper.glBindTexture(Texture.TEXTURE_CUBE_MAP, texture0);
		
	}
}
