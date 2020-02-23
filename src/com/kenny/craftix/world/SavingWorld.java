package com.kenny.craftix.world;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.IOUtils;

import com.google.common.base.Splitter;
import com.kenny.craftix.client.entity.EntityCamera;
import com.kenny.craftix.client.entity.player.EntityPlayer;
import com.kenny.craftix.client.scenes.MainMenuScene;
import com.kenny.craftix.client.scenes.WorldScene;
import com.kenny.craftix.client.settings.InGameSettings;
import com.kenny.craftix.client.settings.nbt.NBTTagCompound;

public class SavingWorld extends CreatingWorld
{
	public static final Splitter COLON_SPLITTER = Splitter.on(':');
	public PrintWriter writeData;
	/**File with format .world*/
	private static File worldData;
	/**Instance of the world.*/
	protected World world;
	protected WorldScene worldScene;
	public int camMode = 0;
	
	
	public SavingWorld(File worldDataIn, World worldIn, WorldScene ws) 
	{
		this.worldScene = ws;
		this.world = worldIn;
		this.worldData = worldDataIn;
		this.worldData = new File(worldDataIn, "saves/worldData.world");
	}
		
	/**
	 * Return the main world data file.
	 */
	public File getWorldDataMainFile()
	{
		return this.worldData;
	}
	
	@SuppressWarnings("deprecation")
	public void loadWorld(WorldScene worldSceneIn, MainMenuScene mainMenu)
	{
		World worldIn = worldSceneIn.getWorld();
		EntityPlayer playerIn = worldIn.getPlayer();
		
		try
		{	
			List<String> datalist = IOUtils.readLines(new FileInputStream(this.worldData));
			NBTTagCompound worldDataInNbt = new NBTTagCompound();
			
			for(String s: datalist)
			{
				try
				{
					Iterator<String> iterator = COLON_SPLITTER.omitEmptyStrings().limit(2).split(s).iterator();
					worldDataInNbt.setString(iterator.next(), iterator.next());
				}
				catch(Exception e) {}
			}
			
			worldDataInNbt = this.dataFix(worldDataInNbt);
			
			for(String s1 : worldDataInNbt.getKeySet())
			{
				String s2 = worldDataInNbt.getString(s1);
				
				try
				{
					if("Name".equals(s1))
					{
						mainMenu.getCreatingWorld().tempWorldName = s2;
					}
					if("Type".equals(s1))
					{
						mainMenu.getCreatingWorld().tempWorldType = Integer.parseInt(s2);
					}
					if("Seed".equals(s1))
					{
						mainMenu.getCreatingWorld().tempSeed = Integer.parseInt(s2);
					}
					if("Gamemode".equals(s1))
					{
						mainMenu.getCreatingWorld().tempGamemode = Integer.parseInt(s2);
					}
					
					if("Cam_mode".equals(s1))
					{
						camMode = Integer.parseInt(s2);
						if(camMode == 0) 
						{
							EntityCamera.isFirstPersonCamera = true; EntityCamera.isThridPersonCamera = false; EntityCamera.isThridPersonCameraFace = false;
						}
						else if(camMode == 1)
						{
							EntityCamera.isFirstPersonCamera = false; EntityCamera.isThridPersonCamera = true; EntityCamera.isThridPersonCameraFace = false;
						}
						else if(camMode == 2)
						{
							EntityCamera.isFirstPersonCamera = false; EntityCamera.isThridPersonCamera = false; EntityCamera.isThridPersonCameraFace = true;
						}
					}
					
				}
				catch (Exception e)	{}
			}
		}
		catch (Exception e) {}
	}

	@SuppressWarnings("deprecation")
	public void loadWorldTime()
	{
		try
		{	
			List<String> datalist = IOUtils.readLines(new FileInputStream(this.worldData));
			NBTTagCompound worldDataInNbt = new NBTTagCompound();
			for(String s: datalist)
			{
				try
				{
					Iterator<String> iterator = COLON_SPLITTER.omitEmptyStrings().limit(2).split(s).iterator();
					worldDataInNbt.setString(iterator.next(), iterator.next());
				}
				catch(Exception e) {}
			}
			worldDataInNbt = this.dataFix(worldDataInNbt);
			for(String s1 : worldDataInNbt.getKeySet())
			{
				String s2 = worldDataInNbt.getString(s1);
				
				try
				{
					if("Time".equals(s1))
					{
						this.worldScene.worldTime.setGameTime(Float.parseFloat(s2));
					}
					if("Time_type".equals(s1))
					{
						String tc = this.worldScene.worldTime.parseTimeToString();
						tc = s2;
					}
				}
				catch (Exception e)	{}
			}
		}
		catch (Exception e) {}
	}
	
	@SuppressWarnings("deprecation")
	public void loadPlayerConfiguration()
	{
		try
		{	
			List<String> list = IOUtils.readLines(new FileInputStream(this.worldData));
			NBTTagCompound playerDataInNbt = new NBTTagCompound();
			for(String s: list)
			{
				try
				{
					Iterator<String> iterator = COLON_SPLITTER.omitEmptyStrings().limit(2).split(s).iterator();
					playerDataInNbt.setString(iterator.next(), iterator.next());
				}
				catch(Exception e) {}
			}
			playerDataInNbt = this.dataFix(playerDataInNbt);
			for(String s1 : playerDataInNbt.getKeySet())
			{
				String s2 = playerDataInNbt.getString(s1);
				try
				{
					if("Cam_pitch".equals(s1))
					{
						this.world.getPlayer().getCamera().setPitch(Float.parseFloat(s2));
					}
					if("Cam_yaw".equals(s1))
					{
						this.world.getPlayer().getCamera().setYaw(Float.parseFloat(s2));
					}
					if("Cam_roll".equals(s1))
					{
						this.world.getPlayer().getCamera().setRoll(Float.parseFloat(s2));
					}
					if("Health".equals(s1))
					{
						this.world.getPlayer().healthIn = Float.parseFloat(s2);
						if(this.world.getPlayer().healthIn >= 100.0F)
							this.world.getPlayer().healthIn = 100F;
					}
					if("Hunger".equals(s1))
					{
						this.world.getPlayer().playerHunger = Float.parseFloat(s2);
						if(this.world.getPlayer().playerHunger >= 100.0F)
							this.world.getPlayer().playerHunger = 100F;
					}
					if("Statiation".equals(s1))
					{
						this.world.getPlayer().playerSatiation = Float.parseFloat(s2);
						if(this.world.getPlayer().playerSatiation >= 100.0F)
							this.world.getPlayer().playerSatiation = 100F;
					}
					if("Breath".equals(s1))
					{
						this.world.getPlayer().playerBreath = Float.parseFloat(s2);
						if(this.world.getPlayer().playerBreath >= 100.0F)
							this.world.getPlayer().playerBreath = 100F;
					}
					if("Pos_p_X".equals(s1))
					{
						this.world.getPlayer().getPositionVector().x = Float.parseFloat(s2);
					}
					if("Pos_p_Y".equals(s1))
					{
						this.world.getPlayer().getPositionVector().y = Float.parseFloat(s2);
					}
					if("Pos_p_Z".equals(s1))
					{
						this.world.getPlayer().getPositionVector().z = Float.parseFloat(s2);
					}
					if("Rot_p_X".equals(s1))
					{
						this.world.getPlayer().setRotX(Float.parseFloat(s2));
					}
					if("Rot_p_Y".equals(s1))
					{
						this.world.getPlayer().setRotY(Float.parseFloat(s2));
					}
					if("Rot_p_Z".equals(s1))
					{
						this.world.getPlayer().setRotZ(Float.parseFloat(s2));
					}
					if("Scale_p".equals(s1))
					{
						this.world.getPlayer().setObjectSize(Float.parseFloat(s2));
					}
					if("Walk_speed".equals(s1))
					{
						this.world.getPlayer().setWalkSpeed(Float.parseFloat(s2));
					}
					if("Run_speed".equals(s1))
					{
						this.world.getPlayer().setRunSpeed(Float.parseFloat(s2));
					}
					if("Turn_speed".equals(s1))
					{
						this.world.getPlayer().setTurnSpeed(Float.parseFloat(s2));
					}
					
				}
				catch (Exception e)	{}
			}
		}
		catch (Exception e) {}
	}
	
	@SuppressWarnings("deprecation")
	public void loadTerrainConfiguration()
	{
		try
		{	
			List<String> list = IOUtils.readLines(new FileInputStream(this.worldData));
			NBTTagCompound playerDataInNbt = new NBTTagCompound();
			for(String s: list)
			{
				try
				{
					Iterator<String> iterator = COLON_SPLITTER.omitEmptyStrings().limit(2).split(s).iterator();
					playerDataInNbt.setString(iterator.next(), iterator.next());
				}
				catch(Exception e) {}
			}
			playerDataInNbt = this.dataFix(playerDataInNbt);
			for(String s1 : playerDataInNbt.getKeySet())
			{
				String s2 = playerDataInNbt.getString(s1);
				try
				{
					if("Size_X".equals(s1))
					{
						this.world.getTerrain().getTerrainOptions().setXSize(Integer.parseInt(s2));
					}
					if("Size_Z".equals(s1))
					{
						this.world.getTerrain().getTerrainOptions().setZSize(Integer.parseInt(s2));
					}
					if("Max_height".equals(s1))
					{
						this.world.getTerrain().getTerrainOptions().setMaxHeight(Integer.parseInt(s2));
					}
					if("Amplitude".equals(s1))
					{
						this.world.getTerrain().getTerrainOptions().setAmplitude(Integer.parseInt(s2));
					}
					if("Octaves".equals(s1))
					{
						this.world.getTerrain().getTerrainOptions().setOctaves(Integer.parseInt(s2));
					}
					if("Roughness".equals(s1))
					{
						this.world.getTerrain().getTerrainOptions().setRoughness(Integer.parseInt(s2));
					}
					if("Smooth".equals(s1))
					{
						this.world.getTerrain().getTerrainOptions().setSmooth(Float.parseFloat(s2));
					}
					if("Smooth_next".equals(s1))
					{
						this.world.getTerrain().getTerrainOptions().setSmoothNext(Float.parseFloat(s2));
					}
					if("Pos_t_X".equals(s1))
					{
						this.world.getTerrain().setX(Float.parseFloat(s2));
					}
					if("Pos_t_Z".equals(s1))
					{
						this.world.getTerrain().setZ(Float.parseFloat(s2));
					}
					if("Max_pixel_colour".equals(s1))
					{
						this.world.getTerrain().setMaxPixelColour(Float.parseFloat(s2));
					}

				}
				catch (Exception e)	{}
			}
		}
		catch (Exception e) {}
	}
	
	@SuppressWarnings("deprecation")
	public void loadWaterConfiguration()
	{
		try
		{	
			List<String> list = IOUtils.readLines(new FileInputStream(this.worldData));
			NBTTagCompound waterDataInNbt = new NBTTagCompound();
			for(String s: list)
			{
				try
				{
					Iterator<String> iterator = COLON_SPLITTER.omitEmptyStrings().limit(2).split(s).iterator();
					waterDataInNbt.setString(iterator.next(), iterator.next());
				}
				catch(Exception e) {}
			}
			waterDataInNbt = this.dataFix(waterDataInNbt);
			for(String s1 : waterDataInNbt.getKeySet())
			{
				String s2 = waterDataInNbt.getString(s1);
				try
				{
	
					if("Tile_size".equals(s1))
					{
						this.world.getWater().tileSize = Integer.parseInt(s2);
					}
					if("Wave_speed".equals(s1))
					{
						this.world.getWater().getWaterOptions().setWaveSpeed(Float.parseFloat(s2));
					}
					if("Tiling".equals(s1))
					{
						this.world.getWater().getWaterOptions().setTiling(Float.parseFloat(s2));
					}
					if("Wave_strength".equals(s1))
					{
						this.world.getWater().getWaterOptions().setWaveStrength(Float.parseFloat(s2));
					}
					if("Shine_damper".equals(s1))
					{
						this.world.getWater().getWaterOptions().setShineDamper(Float.parseFloat(s2));
					}
					if("Reflectivity".equals(s1))
					{
						this.world.getWater().getWaterOptions().setReflectivity(Float.parseFloat(s2));
					}
					if("Pos_w_X".equals(s1))
					{
						this.world.getWater().x = Integer.parseInt(s2);
					}
					if("Pos_w_Z".equals(s1))
					{
						this.world.getWater().z = Integer.parseInt(s2);
					}
					if("Height_w".equals(s1))
					{
						this.world.getWater().height = Integer.parseInt(s2);
					}
				}
				catch (Exception e)	{}
			}
		}
		catch (Exception e) {}
	}
	
	@SuppressWarnings("deprecation")
	public void saveWorld()
	{
		PrintWriter writeData = null;
		this.saveCamMode();
		
		try 
		{
			writeData = new PrintWriter(new OutputStreamWriter(new FileOutputStream(this.getWorldDataMainFile())));
			writeData.println("[World Information]");
			writeData.println("Name:" + this.world.worldName);
			writeData.println("Type:" + this.world.getWorldType());
			writeData.println("Seed:" + this.world.getWorldSeed());
			writeData.println("Time:" + this.world.worldTime.getGameTime());
			writeData.println("Time_type:" + this.world.worldTime.parseTimeToString());
			writeData.println("[Player Information]");
			writeData.println("Cam_mode:" + this.camMode);
			writeData.println("Cam_pitch:" + this.world.getPlayer().getCamera().getPitch());
			writeData.println("Cam_yaw:" + this.world.getPlayer().getCamera().getYaw());
			writeData.println("Cam_roll:" + this.world.getPlayer().getCamera().getRoll());
			writeData.println("Gamemode:" + this.world.getPlayerGamemode());
			writeData.println("Health:" + this.world.getPlayer().healthIn);
			writeData.println("Hunger:" + this.world.getPlayer().playerHunger);
			writeData.println("Statiation:" + this.world.getPlayer().playerSatiation);
			writeData.println("Breath:" + this.world.getPlayer().playerBreath);
			writeData.println("Pos_p_X:" + this.world.getPlayer().getPositionVector().x);
			writeData.println("Pos_p_Y:" + this.world.getPlayer().getPositionVector().y);
			writeData.println("Pos_p_Z:" + this.world.getPlayer().getPositionVector().z);
			writeData.println("Rot_p_X:" + this.world.getPlayer().getRotX());
			writeData.println("Rot_p_Y:" + this.world.getPlayer().getRotY());
			writeData.println("Rot_p_Z:" + this.world.getPlayer().getRotZ());
			writeData.println("Scale_p:" + this.world.getPlayer().getScale());
			writeData.println("Walk_speed:" + this.world.getPlayer().getEntityWalkSpeed());
			writeData.println("Run_speed:" + this.world.getPlayer().getEntityRunSpeed());
			writeData.println("Turn_speed:" + this.world.getPlayer().getEntityTurnSpeed());
			writeData.println("[Terrain Information]");
			writeData.println("Size_X:" + this.world.getTerrain().getTerrainOptions().getXSize());
			writeData.println("Size_Z:" + this.world.getTerrain().getTerrainOptions().getZSize());
			writeData.println("Max_height:" + this.world.getTerrain().getTerrainOptions().getMaxHeight());
			writeData.println("Amplitude:" + this.world.getTerrain().getTerrainOptions().getAmplitude());
			writeData.println("Octaves:" + this.world.getTerrain().getTerrainOptions().getOctaves());
			writeData.println("Roughness:" + this.world.getTerrain().getTerrainOptions().getRoughness());
			writeData.println("Smooth:" + this.world.getTerrain().getTerrainOptions().getSmooth());
			writeData.println("Smooth_next:" + this.world.getTerrain().getTerrainOptions().getSmoothNext());
			writeData.println("Pos_t_X:" + this.world.getTerrain().getX());
			writeData.println("Height_t_Y:" + "Always be on zero!");
			writeData.println("Pos_t_Z:" + this.world.getTerrain().getZ());
			writeData.println("Max_pixel_colour:" + 1024 * 1024 * 1024);
			writeData.println("[Water Information]");
			writeData.println("Tile_size:" + this.world.getWater().tileSize);
			writeData.println("Wave_speed:" + this.world.getWater().getWaterOptions().getWaveSpeed());
			writeData.println("Tiling:" + this.world.getWater().getWaterOptions().getTiling());
			writeData.println("Wave_strength:" + this.world.getWater().getWaterOptions().getWaveStrength());
			writeData.println("Shine_damper:" + this.world.getWater().getWaterOptions().getShineDamper());
			writeData.println("Reflectivity:" + this.world.getWater().getWaterOptions().getReflectivity());
			writeData.println("Pos_w_X:" + this.world.getWater().getX());
			writeData.println("Height_w:" + this.world.getWater().getHeight());
			writeData.println("Pos_w_Z:" + this.world.getWater().getZ());
			InGameSettings.slot0 = true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			IOUtils.closeQuietly((Writer)writeData);
		}
	}
	
	 private NBTTagCompound dataFix(NBTTagCompound p_189988_1_)
	 {
	        int i = 0;

	        try {}
	        catch (RuntimeException var4) {}
	        return p_189988_1_;
	 }
	 
	 /**
	  * Parses a string into a float.
	  */
	 public float parseFloat(String str)
	 {
	     if ("true".equals(str))
	     {
	         return 1.0F;
	     }
	     else
	     {
	         return "false".equals(str) ? 0.0F : Float.parseFloat(str);
	     }
	 }
	 
	 public void saveCamMode()
	 {
		 if(EntityCamera.isFirstPersonCamera)
			 this.camMode = 0;
		 else if(EntityCamera.isThridPersonCamera)
			 this.camMode = 1;
		 else if(EntityCamera.isThridPersonCameraFace)
			 this.camMode = 2;
	 }
	 
	 public static File getFileWorldData()
	 {
		 return worldData;
	 }
	 
}
