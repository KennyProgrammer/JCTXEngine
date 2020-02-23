package com.kenny.craftix.client.renderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.util.vector.Matrix4f;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.entity.GameObject;
import com.kenny.craftix.client.entity.EntityCamaraInMenu;
import com.kenny.craftix.client.entity.Light;
import com.kenny.craftix.client.keyboard.KeyboardUserInput;
import com.kenny.craftix.client.loader.TexturesLoader;
import com.kenny.craftix.client.renderer.entity.EntityRenderer;
import com.kenny.craftix.client.renderer.models.TexturedModel;
import com.kenny.craftix.client.renderer.textures.TextureManager;
import com.kenny.craftix.client.scenes.MainMenuScene;
import com.kenny.craftix.client.shaders.GameObjectShader;
import com.kenny.craftix.world.skybox.SkyboxPanoramaRenderer;

public class MainMenuRenderer extends Renderer
{
	/**Setup projection matrix*/
	private static final float PANORAMA_FOV = 40f;
	private Matrix4f projectionMatrix;
	/**Its a instance for static shader class*/
	private GameObjectShader entityShader = new GameObjectShader();
	public EntityRenderer entityRenderer;
	public SkyboxPanoramaRenderer skyboxPanoramaRenderer;
	private Map<TexturedModel, List<GameObject>> entities = new HashMap<TexturedModel, List<GameObject>>();
	public KeyboardUserInput keyboardManager = new KeyboardUserInput();
	public TexturesLoader textureLoader = new TexturesLoader();
	
	public MainMenuRenderer(Craftix craftixIn) 
	{
		super(craftixIn);
		this.setDataToProjectionMatrix(PANORAMA_FOV, 1000F, 0.01F);
		this.projectionMatrix = new Matrix4f();
		this.createProjectionMatrix(this.projectionMatrix);
		this.entityRenderer = new EntityRenderer(entityShader, this.projectionMatrix);
		this.skyboxPanoramaRenderer = new SkyboxPanoramaRenderer(craftixIn.cxLoader, this.textureLoader, this.projectionMatrix);
	}

	/**
	 * Render the main menu stuff. Like a lights, entites.
	 */
	public void render(List<Light> lights, EntityCamaraInMenu camera) 
	{
		super.render(lights);
		this.prepare(0, 0, 0, WorldRenderer.ALPHA);
		this.entityShader.start();
		this.entityShader.loadSkyColour(WorldRenderer.FOG_R, WorldRenderer.FOG_G, WorldRenderer.FOG_B);
		this.entityShader.loadLights(lights);
		this.entityShader.loadViewMatrix(camera);
		this.entityShader.loadLightLevels(WorldRenderer.LIGHT_LEVELS);
		this.entityShader.loadDensity(0.0035F);
		this.entityShader.loadGradient(WorldRenderer.GRADIENT);
		this.skyboxPanoramaRenderer.renderPanorama(camera, WorldRenderer.FOG_R, WorldRenderer.FOG_G, WorldRenderer.FOG_B);
		this.entityShader.stop();
		this.entities.clear();
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
	}
	
	/**
	 * Render a world. Entities, plane, lights, camera, etc..
	 */
	public void renderProfilePlayer(MainMenuScene worldIn, List<GameObject> entities, List<Light> lights,
			EntityCamaraInMenu camera)
	{
		for (GameObject entity : entities) 
		{
			this.processEntity(entity);
		}
		
		this.render(lights, camera);
	}
	
	/**
	 * Procces default entities.
	 */
	public void processEntity(GameObject entity)
	{
		TexturedModel entityModel = entity.texturedModel;
		List<GameObject> batch = entities.get(entityModel);
		if(batch != null)
		{
			batch.add(entity);
		} else {
			List<GameObject> newBatch = new ArrayList<GameObject>();
			newBatch.add(entity);
			this.entities.put(entityModel, newBatch);
		}
	}
	
	@Override
	public void cleanUp() 
	{
		super.cleanUp();
		this.entityShader.cleanUp();
	}

}
