package com.kenny.craftix.gameplay;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.renderer.WorldRenderer;
import com.kenny.craftix.client.scenes.Scene;
import com.kenny.craftix.client.scenes.WorldScene;
import com.kenny.craftix.utils.Timer;
import com.kenny.craftix.world.skybox.SkyboxRenderer;

/**
 * Set the time for the game scene that is for the world. And it begins with 
 * the time that will be specified in the constructor class WorldScene.
 * 
 * @author Kenny
 */
public class GameTime 
{
	/**Actually time in the game.*/
	public float time;
	/**Actually of changing a fog colours.*/
	public float timeFog;
	/**Check if in world now day.*/
	public boolean isDay;
	/**Check if in world now night.*/
	public boolean isNight;
	/**Check if in world now morning.*/
	public boolean isMorning;
	/**Check if in world now evening.*/
	public boolean isEvening;
	public String timeCycle;
	
	public int bindTexture1;
	public int bindTexture0;
	public float blendFactor;
	public GameStatus status = new GameStatus();
	
	private Timer timer = new Timer();
	public SkyboxRenderer skybox;
	
	public GameTime(Scene sceneIn, SkyboxRenderer skyboxIn)
	{
		if(!WorldScene.isLoadWorld)
		{
			this.time = this.getWorldTime(sceneIn);
		}
		this.skybox = skyboxIn;
	}
	
	/**
	 * Return the world time.
	 */
	public float getWorldTime(Scene sceneIn)
	{
		return sceneIn.getSceneTime();
	}
	
	/**
	 * Changes the time of each display update tick.
	 */
	public void increseWorldTime(Scene sceneIn, Craftix craftixIn)
	{
		if(!craftixIn.status.isGamePause())
		{
			this.time += this.timer.getFrameTimeSeconds() * 35; //15
			this.time %= 24000;
			this.timeFog += this.timer.getFrameTimeSeconds() * 35;
			this.timeFog %= 240;
			
			if(this.time >= 0 && this.time < 5000)
			{
				this.isNight = true;
				this.bindTexture0 = this.skybox.getNight();
				this.bindTexture1 = this.skybox.getNight();
				this.blendFactor = (this.time - 0) / (5000 - 0);
				WorldRenderer.FOG_R = 0.01f;
				WorldRenderer.FOG_G = 0.01f;
				WorldRenderer.FOG_B = 0.01f;
				
			}
			else if(this.time >= 5000 && this.time < 10000)
			{
				this.isNight = false;
				this.isMorning = true;
				this.bindTexture0 = this.skybox.getNight();
				this.bindTexture1 = this.skybox.getDay();
				this.blendFactor = (this.time - 5000) / (10000 - 5000);
				WorldRenderer.FOG_R = 0.5444f;
				WorldRenderer.FOG_G = 0.62f; //need increase from 0.01 to 0.62
				WorldRenderer.FOG_B = 0.69f; 
			}
			else if(this.time >= 10000 && this.time < 17000)
			{
				this.isMorning = false;
				this.isDay = true;
				this.bindTexture0 = this.skybox.getDay();
				this.bindTexture1 = this.skybox.getDay();
				this.blendFactor = (this.time - 10000) / (17000 - 10000);
				WorldRenderer.FOG_R = 0.5444f;
				WorldRenderer.FOG_G = 0.62f;
				WorldRenderer.FOG_B = 0.69f;
			}
			else if(this.time >= 17000 && this.time < 22000)
			{
				this.isDay = false;
				this.isEvening = true;
				this.bindTexture0 = this.skybox.getDay();
				this.bindTexture1 = this.skybox.getDay();
				this.blendFactor = (this.time - 17000) / (22000 - 17000);
				WorldRenderer.FOG_R = 0.5444f;
				WorldRenderer.FOG_G = 0.62f;
				WorldRenderer.FOG_B = 0.69f;
			}
			else if(this.time >= 22000)
			{
				this.isEvening = false;
				this.isNight = true;
				this.bindTexture0 = this.skybox.getDay();
				this.bindTexture1 = this.skybox.getNight();
				WorldRenderer.FOG_R = 0.01f;
				WorldRenderer.FOG_G = 0.01f; //need increase from 0.62 to 0.01
				WorldRenderer.FOG_B = 0.01f;
			}
			//if(Renderer.FOG_RED < 0.5404f)
			//{
			//	Renderer.FOG_RED  = Renderer.FOG_RED  + ((0.5404f / 3000.0f) * (this.timeFog - 50));
			//}
			//if(Renderer.FOG_GREEN < 0.62f)
			//{
			//	Renderer.FOG_GREEN = Renderer.FOG_GREEN + ((0.62f / 3000.0f) * (this.timeFog - 50));
			//}
			//if(Renderer.FOG_BLUE < 0.69f){
			//	
			//	Renderer.FOG_BLUE = Renderer.FOG_BLUE + ((0.69f / 3000.0f) * (this.timeFog - 50));
			//}
		}
		
		
	}
	
	public float getGameTime()
	{
		return this.time;
	}
	
	public void setGameTime(float time)
	{
		this.time = time;
	}
	
	/**
	 * Parse the game time to string of text.
	 */
	public String parseTimeToString()
	{
		this.timeCycle = null;
		
		if(this.isNight)
			timeCycle = "Night";
		else if(this.isMorning)
			timeCycle = "Morning";
		else if(this.isDay)
			timeCycle = "Day";
		else if(this.isEvening)
			timeCycle = "Evening";
		
			return timeCycle;
		
	}
	
	/**
	 * Pause time when player set on pause mode.
	 */
	public void pauseTime()
	{
		
	}
	
}
