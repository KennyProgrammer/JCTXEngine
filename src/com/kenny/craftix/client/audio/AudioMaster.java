package com.kenny.craftix.client.audio;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.util.WaveData;

public class AudioMaster 
{
	public static final Logger LOGGER = LogManager.getLogger("Craftix");
	private static List<Integer> buffers = new ArrayList<Integer>();
	/**Wave data help load audio files.*/
	private static WaveData waveFile;
	
	/**
	 * Initialization OpenAl API.
	 */
	public static void init()
	{
		createAudioMaster();
		setListenerData();
		LOGGER.info("Audio Master loading.");
	}
	
	/**
	 * Create a main OpenAl function and audio master.
	 */
	public static void createAudioMaster()
	{
		try 
		{
			AL.create();
		} 
		catch (LWJGLException e) 
		{
			LOGGER.info("Cannot create a audio master!");
			e.printStackTrace();
		}
	}
	
	/**
	 * Set information (data) about listener.
	 */
	public static void setListenerData()
	{
		AlHelper.alListener3f(AL10.AL_POSITION, 0, 0, 0);
		AlHelper.alListener3f(AL10.AL_VELOCITY, 0, 0, 0);
	}
	
	/**
	 * Set information (data) about 3d listener.
	 */
	public static void setListenerData(float x, float y ,float z)
	{
		AlHelper.alListener3f(AL10.AL_POSITION, x, y, z);
		AlHelper.alListener3f(AL10.AL_VELOCITY, 0, 0, 0);
	}
	
	/**
	 * Load a sound file at assets folder.
	 * @throws FileNotFoundException 
	 */
	
	public static int loadSound(InputStream file) throws FileNotFoundException
	{
		int buffer = AlHelper.alGenBuffers();
		buffers.add(buffer);
		waveFile = WaveData.create(file);
		AlHelper.alBufferData(buffer, waveFile.format, waveFile.data, waveFile.samplerate);
		waveFile.dispose();
			return buffer;
	}
	
	/**
	 * Close all audio sources when game closed.
	 */
	public static void shutdownAudioMaster()
	{
		for (int buffer : buffers) 
		{
			AlHelper.alDeleteBuffers(buffer);
		}
		AL.destroy();
	}
}
