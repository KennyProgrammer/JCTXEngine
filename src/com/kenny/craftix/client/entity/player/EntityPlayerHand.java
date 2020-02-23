package com.kenny.craftix.client.entity.player;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import com.kenny.craftix.client.entity.GameObject;
import com.kenny.craftix.client.scenes.Scene;

public class EntityPlayerHand extends GameObject 
{
	/**If this is the hand for the player then the player we need.*/
	public EntityPlayer player;
	
	public EntityPlayerHand(String nameIn, EntityPlayer playerIn) 
	{
		super(nameIn, 0F, 0F, 0F, 0F);
		this.player = playerIn;
		this.setObjectName(nameIn);
		this.setPosition(new Vector3f(this.player.getPositionVector().x, 0F, this.getPositionVector().z));
		this.setObjectSize(1.0F);
		this.setRotX(0F);
		this.setRotY(0F);
		this.setRotZ(0F);
		this.disableCollision();
	}

	public void renderHand()
	{
		GameObject hand = this;
		
		if(this.player.getCamera().isFirstPersonCamera) 
		{
			hand.setRenderState(true);
		} else {
			hand.setRenderState(false);
		}
	}
	
	/**
	 * Disable collision for player hand.
	 */
	public EntityPlayerHand disableCollision()
	{
		this.hasPhysicalObject = false;
		return this;
	}
	
	/**
	 * Generate object with settings positions.
	 */
	@Override
	public EntityPlayerHand generateObject(Scene sceneIn)
	{
		sceneIn.objects.add(this);
		return this;
	}
	
	/**
	 * Binds the right hand to the player himself and she begins to 
	 * follow him. Called in the update loop.
	 */
	public void followHandForPlayer()
	{
		this.setPosition(
				this.player.getPositionVector().x - 0f, 
				this.player.getPositionVector().y + 5.4f,
				this.player.getPositionVector().z - 0f, false); //2.5f
	}
	
	public void moveHand()
	{
		if(Keyboard.isKeyDown(Keyboard.KEY_5))
		{
			this.increasePosition(0.02f, 0f, 0f);
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_6))
		{
			this.increasePosition(-0.02f, 0f, 0f);
		}
	}
	
}
