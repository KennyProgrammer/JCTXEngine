package com.kenny.craftix.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.renderer.models.TexturedModel;
import com.kenny.craftix.init.EntityInit;

public class CommandSpawnEntity extends Command
{
	private static final Logger LOGGER = LogManager.getLogger(Craftix.LOGGER_INSTANCE);
	public EntityInit entityInit;
	
	public CommandSpawnEntity(String commandNameIn, int commandIdIn) 
	{
		super(commandNameIn, commandIdIn);
		this.entityInit = new EntityInit();
	}
	
	/**
	 * This team is responsible for spawn entity in the world.
	 * @param type - type of entity. 0 - its a standard entity, 1 - its a normal maps entity.
	 * @param entityIn - an entity that will be spawned.
	 */
	public void runCommand(int type, TexturedModel entityIn, float posX, float posY, float posZ,
			float rotX, float rotY, float rotZ, float scale) 
	{
		if(type == 0)
		{
			//this.entity.add(new Entity(entityIn, posX, posY, posZ,
					//rotX, rotY, rotZ, scale));
		}
		if(type == 1)
		{
			//this.entityInit.entitiesNm.add(new Entity(entityIn,  posX, posY, posZ,
					//rotX, rotY, rotZ, scale));
		}
		if(type < 0 || type > 1)
		{
			LOGGER.warn("Entity type will not find. Error spawning entity.");
		}
	}
}
