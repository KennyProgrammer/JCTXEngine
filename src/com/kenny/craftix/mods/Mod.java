package com.kenny.craftix.mods;

import com.kenny.craftix.init.EntityInit;

public class Mod 
{
	protected Mod instance;
	public EntityInit entityLoader;
	
	public Mod()
	{
		instance = this;
	}
	
	public void ModEntityLoader()
	{
		this.entityLoader = new EntityInit();
		//this.entityLoader.addEntity(modelFile, textureFile)
	}
	
}
