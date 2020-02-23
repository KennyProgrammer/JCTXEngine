package com.kenny.craftix.client.entity;

public interface IEntityLivingBase 
{
	public void prepareEntitySpawn();
	
	public void setHealth(float health);
	
	public void setHunger(float hunger, float statiation);
	
	public void setThrist(float thrist);
	
	public void setBreath(float breath);
	
}
