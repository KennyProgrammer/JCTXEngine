package com.kenny.craftix.client.renderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector4f;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.entity.GameObject;
import com.kenny.craftix.client.entity.EntityBlock;
import com.kenny.craftix.client.entity.EntityCamera;
import com.kenny.craftix.client.entity.Light;
import com.kenny.craftix.client.entity.LightSun;
import com.kenny.craftix.client.particles.ParticleMaster;
import com.kenny.craftix.client.renderer.GlHelper.Texture;
import com.kenny.craftix.client.renderer.entity.EntityRenderer;
import com.kenny.craftix.client.renderer.models.Model;
import com.kenny.craftix.client.renderer.models.TexturedModel;
import com.kenny.craftix.client.renderer.normalMapping.NMRenderer;
import com.kenny.craftix.client.renderer.normalMapping.NMShader;
import com.kenny.craftix.client.renderer.postEffects.PostProcessing;
import com.kenny.craftix.client.renderer.textures.TextureManager;
import com.kenny.craftix.client.scenes.WorldScene;
import com.kenny.craftix.client.settings.InGameSettings;
import com.kenny.craftix.client.shaders.GameObjectShader;
import com.kenny.craftix.client.shaders.FrameBuffer;
import com.kenny.craftix.client.shaders.TerrainShader;
import com.kenny.craftix.client.shadows.render.ShadowMapMasterRenderer;
import com.kenny.craftix.utils.math.Maths;
import com.kenny.craftix.world.World;
import com.kenny.craftix.world.skybox.SkyboxRenderer;
import com.kenny.craftix.world.sun.SunRenderer;
import com.kenny.craftix.world.terrain.Terrain;
import com.kenny.craftix.world.water.WaterRenderer;

/**
 * World Render or Master Renderer. (Rendering everything in the world.)
 * @author Kenny
 */
public class WorldRenderer extends Renderer
{
	public Craftix cx;
	/**Field of view-the angular space visible to the eye with a fixed view 
	 * and a fixed head*/
	public static final float FOV = 70; /**This option now in controlling options txt file.*/
	/**Near plane visible eye*/
	public static final float NEAR_PLANE = 0.1f;
	/**Far plane visible eye*/
	public static final float FAR_PLANE = 2000;
	/**This effect adjusts the number of light shadow transitions on the model.*/
	public static final float LIGHT_LEVELS = 4.0f;
	public static final float GRADIENT = 5.0f;
	/**Its three (+ 1) colors. (Together RGBA) or Sky Colour*/
	public static float SKY_RED = 0.743288f;
	public static float SKY_GREEN = 0.827576f;
	public static float SKY_BLUE = 0.944943f;
	/**Its a three fog distance colour.*/
	public static float FOG_R = 0.743288f;
	public static float FOG_G = 0.827576f;
	public static float FOG_B = 0.944943f;
	/**This is alpha channel color transparency.*/
	public static final float ALPHA = 1;
	/**Setup projection matrix*/
	private Matrix4f projectionMatrix;
	/**Its a instance for static shader class*/
	private GameObjectShader entityShader = new GameObjectShader();
	private EntityRenderer entityRenderer;
	private SkyboxRenderer skyboxRenderer;
	private ShadowMapMasterRenderer shadowRenderer;
	/**Get the NM render for this class*/
	private NMRenderer nmRenderer;
	private TerrainRenderer terrainRenderer;
	private World world;
	private TerrainShader terrainShader = new TerrainShader();
	private NMShader nmShader = new NMShader();
	/**List of the entities.*/
	private Map<TexturedModel, List<EntityBlock>> blocks = new HashMap<TexturedModel, List<EntityBlock>>();
	private Map<TexturedModel, List<GameObject>> objects = new HashMap<TexturedModel, List<GameObject>>();
	/**List of the entity with normal map effect*/
	private Map<TexturedModel, List<GameObject>> normalEntities = new HashMap<TexturedModel, List<GameObject>>();
	public RenderDistance renderDistance;
	public static boolean isUseTringles = false;
	public static boolean isUseShaders = true;
	/**Load water componets such a shaders, and framebuffers.*/
	private WaterRenderer waterRenderer;
	private LightSun sun;
	private SunRenderer sunRenderer;
	
	public WorldRenderer(Craftix craftixIn, EntityCamera cameraIn, World worldIn)
	{
		super(craftixIn, cameraIn);
		this.cx = craftixIn;
		this.world = worldIn;
		GlHelper.enableCulling();
		this.createWorldProjectionMatrix();
		this.sun = new LightSun().enableSun();
		this.world.worldScene.lights.add(this.sun);
		this.cx.LOGGER.info("Preparing world: 15%");
		this.skyboxRenderer = new SkyboxRenderer(craftixIn, this.projectionMatrix);
		this.cx.LOGGER.info("Preparing world: 20%");
		this.sunRenderer = new SunRenderer();
		this.cx.LOGGER.info("Preparing world: 30%");
		this.shadowRenderer = new ShadowMapMasterRenderer(cameraIn);
		this.cx.LOGGER.info("Preparing world: 45%");
		this.terrainRenderer = new TerrainRenderer(this.terrainShader, this.projectionMatrix);
		this.cx.LOGGER.info("Preparing world: 50%");
		this.waterRenderer = new WaterRenderer(craftixIn, this.projectionMatrix);
		this.cx.LOGGER.info("Preparing world: 65%");
		this.nmRenderer = new NMRenderer(this.projectionMatrix);
		this.cx.LOGGER.info("Preparing world: 70%");
		this.entityRenderer = new EntityRenderer(this.entityShader, this.projectionMatrix);
		this.cx.LOGGER.info("Preparing world: 90%");
		ParticleMaster.init(this.getProjectionMatrix());
	}

	/**
	 * It gathers all rendering methods of all engine components such as 
	 * player, objects, earth, sky, water and draws them on the 
	 * screen.
	 */
	public void renderWorld(WorldScene worldIn, Vector4f clipPlane)
	{
		for (GameObject entity : worldIn.objects) 
		{
			this.processEntity(entity);
		}
		for (GameObject entity : worldIn.objectsN) 
		{
			this.processNormalEntity(entity);
		}
		
		this.render(worldIn, clipPlane);
		this.processEntity(this.world.getPlayer());
		this.processTerrain(this.world.getTerrain());
		this.world.getWaterRenderer().render(worldIn);
		ParticleMaster.renderParticles(this.world.getPlayerCamera());
	}
	
	public void renderMainCraftixBox(GameObject entityMainCraftixBox)
	{	
		render(entityMainCraftixBox, this.entityShader);
	}
	
	public Matrix4f getProjectionMatrix()
	{
		return projectionMatrix;
	}
	
	/**
	 * Compat all rendering stuff in one render method. For easy using.
	 */
	public void render(WorldScene worldIn, Vector4f clipPlane)
	{
		super.render(worldIn.lights);
		if(worldIn.getWorld().worldTime.isDay || worldIn.getWorld().worldTime.isMorning || worldIn.getWorld().worldTime.isEvening)
			this.prepare(0.55f, 0.70f, 0.62f, ALPHA);
		else if(worldIn.getWorld().worldTime.isNight)
			this.prepare(0f, 0f, 0f, ALPHA);
		if(InGameSettings.renderSkyBoxIn)
		{
			//this.skyboxRenderer.render(worldIn, FOG_R, FOG_G, FOG_B);
			this.sunRenderer.render(this.world.sunObject, this, this.world.getPlayerCamera());
		}
		
		this.entityShader.start();
		this.entityShader.loadPlane(clipPlane);
		this.entityShader.loadSkyColour(FOG_R, FOG_G, FOG_B);
		this.entityShader.loadLights(this.world.worldScene.lights);
		this.entityShader.loadViewMatrix(this.world.getPlayerCamera());
		this.entityShader.loadLightLevels(LIGHT_LEVELS);
		this.entityShader.loadDensity(this.renderDistance.fogDistance);
		this.entityShader.loadGradient(GRADIENT);
		this.entityRenderer.render(this.objects);
		this.entityShader.stop();

		this.nmRenderer.render(this.normalEntities, clipPlane, this.world.worldScene.lights, this.world.getPlayerCamera(), this);
		this.terrainShader.start();
		this.terrainShader.loadPlane(clipPlane);
		this.terrainShader.loadSkyColour(FOG_R, FOG_G, FOG_B);
		this.terrainShader.loadLights(this.world.worldScene.lights);
		this.terrainShader.loadViewMatrix(this.world.getPlayerCamera());
		this.terrainRenderer.render(this.world.terrains, 
				this.shadowRenderer.getToShadowMapSpaceMatrix(), this);
		this.terrainShader.stop();
		
		this.nmShader.start();
		this.nmShader.loadViewMatrix(this.world.getPlayerCamera());
		this.nmShader.loadSkyColour(FOG_R, FOG_G, FOG_B);
		this.nmShader.stop();
		
		this.objects.clear();
		this.normalEntities.clear();
		this.world.terrains.clear();
		
	}
	
	public void render(GameObject entityMainCraftixBox, GameObjectShader shader)
	{
		TexturedModel model = entityMainCraftixBox.texturedModel;
		Model rawModel = model.getModel();
		GlHelper.glBindVertexArray(rawModel.getVaoID());
		GlHelper.glEnableVertexAttribArray(0);
		GlHelper.glEnableVertexAttribArray(1);
		Matrix4f transormationMatrix = Maths.createTransformationMatrix
				(entityMainCraftixBox.getPositionVector(),entityMainCraftixBox.getRotX(), entityMainCraftixBox.getRotY(), entityMainCraftixBox.getRotZ(), entityMainCraftixBox.getScale());
		shader.loadTransformationMatrix(transormationMatrix);
		TextureManager.activeTexture0();
		TextureManager.bindTexture2d(model.getTexture().getTextureId());
		GlHelper.glDrawElements(Texture.TRIANGLES, rawModel.getVertexCount(), Texture.UNSINGED_INT, 0);
		GlHelper.glDisableVertexAttribArray(0);
		GlHelper.glDisableVertexAttribArray(1);
		GlHelper.glBindVertexArray(0);
		
	}
	
	public void processTerrain(Terrain terrain)
	{
		this.world.terrains.add(terrain);
	}
	
	/**
	 * Procces default entities.
	 */
	public void processEntity(GameObject entity)
	{
		TexturedModel entityModel = entity.texturedModel;
		List<GameObject> batch = objects.get(entityModel);
		if(batch != null)
		{
			batch.add(entity);
		} else {
			List<GameObject> newBatch = new ArrayList<GameObject>();
			newBatch.add(entity);
			this.objects.put(entityModel, newBatch);
		}
	}
	
	public void processBlock(EntityBlock block)
	{
		TexturedModel entityModel = block.texturedModel;
		List<EntityBlock> batch = blocks.get(entityModel);
		if(batch != null)
		{
			batch.add(block);
		} else {
			List<EntityBlock> newBatch = new ArrayList<EntityBlock>();
			newBatch.add(block);
			this.blocks.put(entityModel, newBatch);
		}
	}
	
	/**
	 * Procces normal map effect entities.
	 */
	public void processNormalEntity(GameObject entity)
	{
		TexturedModel entityModel = entity.texturedModel;
		List<GameObject> batch = normalEntities.get(entityModel);
		if(batch != null)
		{
			batch.add(entity);
		} else {
			List<GameObject> newBatch = new ArrayList<GameObject>();
			newBatch.add(entity);
			this.normalEntities.put(entityModel, newBatch);
		}
	}
	
	/**
	 * Render shadow map into the game world.
	 */
	public void renderShadowMap(List<GameObject> entityList, Light sun)
	{
		for (GameObject entity : entityList) 
		{
			this.processEntity(entity);
		}
		this.shadowRenderer.render(this.objects, sun);
		this.objects.clear();
	}
	
	public int getShadowMapTexture()
	{
		return shadowRenderer.getShadowMap();
	}
	
	/**
	 * Prepares everything for rendering. Clears previous buffers and 
	 * includes depth test.
	 */
	@Override
	public void prepare(float r, float g, float b, float a) 
	{
		super.prepare(r, g, b, a);
		TextureManager.activeTexture5();
		TextureManager.bindTexture2d(this.getShadowMapTexture());
	}
	
	/**
	 * Here a created world projection matrix.
	 */
    protected void createWorldProjectionMatrix()
    {
    	float renderDistance = 0;
    	float fovDistance = 0;
    	this.renderDistance = new RenderDistance(InGameSettings.renderDistanceIn);
    	this.renderDistance.convertFloatToPartsInt();
    	renderDistance = this.renderDistance.renderDistanceFloat;
    	fovDistance = this.renderDistance.fogDistance;
    	this.setDataToProjectionMatrix(FOV, NEAR_PLANE, renderDistance);
    	this.projectionMatrix = new Matrix4f();
    	this.createProjectionMatrix(this.projectionMatrix);
    }
    

 
    /**
   	 * Clean up all shader from previus frame.
   	 */
	@Override
	public void cleanUp() 
	{
		if(!this.world.isWorldCleanUp)
		{
			super.cleanUp();
			this.entityShader.cleanUp();
			this.terrainShader.cleanUp();
			this.nmShader.cleanUp();
			this.nmRenderer.cleanUp();
			this.shadowRenderer.cleanUp();
			this.sunRenderer.cleanUp();
			PostProcessing.cleanUp();
			ParticleMaster.cleanUp();
			this.world.isWorldCleanUp = true;
		}
	}
	
	public void reloadRenderer(EntityCamera cameraIn)
	{
		this.createWorldProjectionMatrix();
		this.cx.LOGGER.info("Reloading world renderer: 15%");
		this.skyboxRenderer = new SkyboxRenderer(this.cx, this.projectionMatrix);
		this.cx.LOGGER.info("Reloading world renderer: 30%");
		//this.shadowRenderer = new ShadowMapMasterRenderer(cameraIn);
		this.cx.LOGGER.info("Reloading world renderer: 45%");
		this.terrainRenderer = new TerrainRenderer(this.terrainShader, this.projectionMatrix);
		this.cx.LOGGER.info("Reloading world renderer: 50%");
		//this.waterRenderer = new WaterRenderer(this.cx, this.getProjectionMatrix());
		this.cx.LOGGER.info("Reloading world renderer: 65%");
		//this.nmRenderer = new NMRenderer(this.projectionMatrix);
		this.cx.LOGGER.info("Reloading world renderer: 70%");
		this.entityRenderer = new EntityRenderer(this.entityShader, this.projectionMatrix);
		PostProcessing.init(Display.getWidth(), Display.getHeight());
		this.cx.LOGGER.info("Reloading world renderer: 100%");
		
		while(!Display.isCloseRequested())
		{
			this.world.worldScene.renderScene();
			Display.update();
		}
	}
	
	/**
	 * Return the skybox renderer component.
	 */
	public SkyboxRenderer getSkyboxRenderer()
	{
		return this.skyboxRenderer;
	}
	/**
	 * Return the water renderer component.
	 */
	public WaterRenderer getWaterRenderer()
	{
		return this.waterRenderer;
	}
	
	public FrameBuffer getWaterFramebuffer()
	{
		return this.getWaterRenderer().framebuffer;
	}
	
	public SunRenderer getSunRenderer()
	{
		return this.sunRenderer;
	}
	
	/**
	 * Return the light world scene.
	 */
	public LightSun getSunLightSource()
	{
		return this.sun;
	}

}
