package com.kenny.craftix.world.water;


import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import com.kenny.craftix.client.Craftix;

import com.kenny.craftix.client.loader.Loader;
import com.kenny.craftix.client.loader.TexturesLoader;
import com.kenny.craftix.client.renderer.GlHelper;
import com.kenny.craftix.client.renderer.GlHelper.Blend;
import com.kenny.craftix.client.renderer.GlHelper.Texture;
import com.kenny.craftix.client.renderer.models.Model;
import com.kenny.craftix.client.renderer.textures.TextureManager;
import com.kenny.craftix.client.scenes.WorldScene;
import com.kenny.craftix.client.settings.InGameSettings;
import com.kenny.craftix.client.shaders.FrameBuffer;
import com.kenny.craftix.utils.Timer;
import com.kenny.craftix.utils.math.Maths;

public class WaterRenderer 
{
	private final static float GRADIENT = 5.0f;
	/**This is simple quad*/
	private Model quad;
	private WaterShader waterShader;
	public FrameBuffer framebuffer;
	private Timer timer = new Timer();
	/**Texture, allowing to calculate the refraction of the light beam in the
	 * collision with the surface, for example water.*/
	private int dudvTexture;
	/**Normal maps are commonly stored as regular RGB images where the RGB components 
	 * correspond to the X, Y, and Z coordinates, respectively, of the surface normal.*/
	private int normalMap;
	/**This is just depth / underwater texture details for not glitched edges.*/
	public int depthMap;
	/**Its a move factor dudv water texture*/
	private float moveFactor = 0;
	public Craftix cx;
	
	public TexturesLoader textureLoader = new TexturesLoader();
	public Loader openGlObjectsLoader = new Loader();
	
	public WaterRenderer(Craftix craftixIn, Matrix4f projectionMatrix) 
	{
		this.cx = craftixIn;
		this.waterShader = new WaterShader();
		this.framebuffer = new FrameBuffer();
		this.dudvTexture = this.textureLoader.loadTexture("maps/dudvMap");
		this.normalMap = this.textureLoader.loadTexture("maps/normalMap/waterN");
		this.waterShader.start();
		this.waterShader.connectTextureUnits();
		this.waterShader.loadProjectionMatrix(projectionMatrix);
		this.waterShader.stop();
		this.setUpVao(this.openGlObjectsLoader);
	}

	public void render(WorldScene worldIn) 
	{
		if(InGameSettings.renderWaterIn)
		{
			this.prepareRender(worldIn);	
			for (Water tile : worldIn.waters) 
			{
				Matrix4f modelMatrix = Maths.createTransformationMatrix(
						new Vector3f(tile.getX(), tile.getHeight(), tile.getZ()), 0, 0, 0,
						tile.tileSize);
				this.waterShader.loadModelMatrix(modelMatrix);
				GlHelper.glDrawArrays(Texture.TRIANGLES, 0, quad.getVertexCount());
			}
			this.unbind();
		}
	}
	
	private void prepareRender(WorldScene worldIn)
	{
		WaterOptions waterOptions = worldIn.getWorld().getWater().getWaterOptions();
		this.waterShader.start();
		this.waterShader.loadViewMatrix(worldIn.getWorld().getPlayerCamera());
		this.waterShader.loadTiling(waterOptions.getTiling());
		this.waterShader.loadDensity(worldIn.getWorld().getRenderer().renderDistance.fogDistance);
		this.waterShader.loadGradient(GRADIENT);
		this.waterShader.loadWaveStrength(waterOptions.getWaveStrength());
		this.waterShader.loadShineDamper(waterOptions.getShineDamper());
		this.waterShader.loadReflectivity(waterOptions.getReflectivity());
		if(worldIn.getPlayer().isPlayerAlive())
		{
			if(!this.cx.status.isGamePause())
			{
				this.moveFactor += waterOptions.getWaveSpeed() * this.timer.getFrameTimeSeconds();
				this.moveFactor %= 1;
			}
		}
		this.waterShader.loadMoveFactor(moveFactor);
		this.waterShader.loadLight(worldIn.getSunLightSource());
		
		GlHelper.glBindVertexArray(quad.getVaoID());
		GlHelper.glEnableVertexAttribArray(0);
		TextureManager.activeTexture0();
		TextureManager.bindTexture2d(this.framebuffer.getReflectionTexture());
		TextureManager.activeTexture1();
		TextureManager.bindTexture2d(this.framebuffer.getRefractionTexture());
		TextureManager.activeTexture2();
		TextureManager.bindTexture2d(this.dudvTexture);
		TextureManager.activeTexture3();
		TextureManager.bindTexture2d(this.normalMap);
		TextureManager.bindTexture2d( this.dudvTexture);
		TextureManager.activeTexture4();
		TextureManager.bindTexture2d(this.framebuffer.getRefractionDepthTexture());
		GlHelper.enableBlend();
		GlHelper.glBlendFunction(Blend.SRC_ALPHA, Blend.ONE_MINUS_SRC_ALPHA);
	}
	
	private void unbind()
	{
		GlHelper.disableBlend();
		GlHelper.glDisableVertexAttribArray(0);
		GlHelper.glBindVertexArray(0);
		this.waterShader.stop();
	}

	private void setUpVao(Loader loader) 
	{
		/**
		 * Just x and z vectex positions here, y is set to 0 in v.shader.
		 */
		float[] vertices = { -1, -1, -1, 1, 1, -1, 1, -1, -1, 1, 1, 1 };
		this.quad = loader.loadToVao(vertices, 2);
	}
	
	public void bindWaterFramebuffer(boolean reflection, boolean refraction)
	{
		if(reflection)
			this.framebuffer.bindReflectionFrameBuffer();
		
		if(refraction)
			this.framebuffer.bindRefractionFrameBuffer();
		
		if(!reflection && !refraction)
			this.framebuffer.bindFrameBuffer();
	}
	
	public void unbindWaterFramebuffer()
	{
		this.framebuffer.unbindFrameBuffer();
	}
	
	public WaterShader getWaterShader()
	{
		return this.waterShader;
	}
	
	/**
	 * Clean up all water renderer components.
	 */
	public void cleanUp()
	{
		this.framebuffer.cleanUp();
		this.waterShader.cleanUp();
	}

}
