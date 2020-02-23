package com.kenny.craftix.client.scenes;

import com.kenny.craftix.client.Craftix;

/**
 * The main components necessary to create a scene.
 * 
 * @author Kenny
 */
public interface IScene 
{
	
	public void loadScene(Craftix cx);

	public void renderScene();

	public void otherSetup();
	
	public void cleanUpScene();
}
