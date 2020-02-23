package com.kenny.craftix.client.audio;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import org.lwjgl.openal.AL10;

public class AlHelper 
{
	/**
	 * Generate a buffers for audio master.
	 */
	public static int alGenBuffers()
	{
		return AL10.alGenBuffers();
	}
	
	/**
	 * Generate a audio source.
	 * @return 
	 */
	public static int alGenSources()
	{
		return AL10.alGenSources();
	}
	
	/**
	 * Just stored a audio data in buffers (In Byte Buffer).
	 */
	public static void alBufferData(int buffer, int format, ByteBuffer data, int freq)
	{
		AL10.alBufferData(buffer, format, data, freq);
	}
	
	/**
	 * Just stored a audio data in buffers (In Int Buffer).
	 */
	public static void alBufferData(int buffer, int format, IntBuffer data, int freq)
	{
		AL10.alBufferData(buffer, format, data, freq);
	}

	/**
	 * Just stored a audio data in buffers (In Short Buffer).
	 */
	public static void alBufferData(int buffer, int format, ShortBuffer data, int freq)
	{
		AL10.alBufferData(buffer, format, data, freq);
	}
	
	/**
	 * Delete all audio buffers.
	 */
	public static void alDeleteBuffers(IntBuffer buffer)
	{
		AL10.alDeleteBuffers(buffer);
	}
	
	public static void alDeleteBuffers(int buffer)
	{
		AL10.alDeleteBuffers(buffer);
	}
	
	/**
	 * Delete all sources.
	 */
	public static void alDeleteSources(IntBuffer source)
	{
		AL10.alDeleteSources(source);
	}
	
	public static void alDeleteSources(int source)
	{
		AL10.alDeleteSources(source);
	}
	
	/**
	 * Set the audio listener.
	 */
	public static void alListener(int pname, FloatBuffer value)
	{
		AL10.alListener(pname, value);
	}
	
	/**
	 * Set the audio listener in position. (3 floats)
	 */
	public static void alListener3f(int pname, float x, float y, float z)
	{
		AL10.alListener3f(pname, x, y, z);
	}
	
	public static void alListener1f(int pname, float value)
	{
		AL10.alListenerf(pname, value);
	}
	
	public static void alListener3i(int pname, int value)
	{
		AL10.alListeneri(pname, value);
	}
	
	/**
	 * Set the source master.
	 */
	public static void alSourcef(int source, int pname, float value)
	{
		AL10.alSourcef(source, pname, value);
	}
	
	public static void alSourcei(int source, int pname, int value)
	{
		AL10.alSourcei(source, pname, value);
	}
	
	public static void alSource3f(int source, int pname, float v1, float v2, float v3)
	{
		AL10.alSource3f(source, pname, v1, v2, v3);
	}

	public static void alSourcePlay(int source)
	{
		AL10.alSourcePlay(source);
	}
	
	public static void alSourcePlay(IntBuffer sources)
	{
		AL10.alSourcePlay(sources);
	}
	
	public static void alGetSourcei(int source, int pname)
	{
		AL10.alGetSourcei(source, pname);
	}
	
	public static void alGetSourcef(int source, int pname)
	{
		AL10.alGetSourcef(source, pname);
	}

}
