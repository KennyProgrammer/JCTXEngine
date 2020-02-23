package com.kenny.craftix.client.scenes;

import java.util.ArrayList;
import java.util.List;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.entity.GameObject;

public class Scene implements IScene
{
	/**Name of the current scene.*/
	private String sceneName;
	/**Time of the current scene.*/
	public float sceneTime;
	/**Entity generator. Generate all entitis to the current world.*/
	public List<GameObject> objects = new ArrayList<GameObject>();
	public List<GameObject> objectsN = new ArrayList<GameObject>();
	public List<GameObject> objectsM = new ArrayList<GameObject>();
	
	/**
	 * Its a basicly a main scene initializator.
	 */
	public Scene(String sceneNameIn, float sceneTimeIn)
	{
		this.sceneName = sceneNameIn;
		this.sceneTime = sceneTimeIn;
	}
	
	public String getSceneName()
	{
		return this.sceneName;
	}
	
	/**
	 * Return the scene time.
	 */
	public float getSceneTime()
	{
		return this.sceneTime;
	}

	public void update()
	{
	}
	
	/**
	 * This method will add objects, entities, and various engine components.
	 */
	@Override
	public void loadScene(Craftix craftixIn) 
	{	
	}

	/**
	 * This is the rendering of the whole scene. Display GUI/UI, light. Everything 
	 * that will be added to this method will be rendered in the scene.
	 */
	@Override
	public void renderScene() 
	{
	}

	/**
	 * Setup other scene components. Like a light, test buttons, hitboxes, 
	 * collisions and etc...
	 */
	@Override
	public void otherSetup() 
	{
	}

	/**
	 * Remove unnecessary buffer shaders to re-create this scene. 
	 * Or to exit the game
	 */
	@Override
	public void cleanUpScene() 
	{
	}
	
}
