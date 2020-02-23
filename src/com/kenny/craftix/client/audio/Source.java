package com.kenny.craftix.client.audio;

import org.lwjgl.openal.AL10;

public class Source 
{
	/**Id of current source*/
	private static int sourceId;
	
	public Source(float gain, float pitch)
	{
		sourceId = AlHelper.alGenSources();
		AlHelper.alSourcef(sourceId, AL10.AL_GAIN, gain);
		AlHelper.alSourcef(sourceId, AL10.AL_PITCH, pitch);
		AlHelper.alSource3f(sourceId, AL10.AL_POSITION, 0, 0, 0);
	}
	
	public Source()
	{
		sourceId = AlHelper.alGenSources();
	}
	
	/**
	 * Simple play current audio.
	 */
	public void play(int buffer)
	{
		this.stop();
		AlHelper.alSourcei(sourceId, AL10.AL_BUFFER, buffer);
		AlHelper.alSourcePlay(sourceId);
	}
	
	/**
	 * Sets and adjusts the 3d sound volume.
	 */
	public void setVolume(float volume)
	{
		AlHelper.alSourcef(sourceId, AL10.AL_GAIN, volume);
	}
	
	public void decreaseVolume(float volume)
	{
		AlHelper.alSourcef(sourceId, AL10.AL_GAIN, 0.5f);
	}
	
	public void increaseVolume(float volume)
	{
		AlHelper.alSourcef(sourceId, AL10.AL_GAIN, 1f);
	}
	
	public void volume00()
	{AlHelper.alSourcef(sourceId, AL10.AL_GAIN, 0.0f);}
	
	public void volume02()
	{AlHelper.alSourcef(sourceId, AL10.AL_GAIN, 0.2f);}
	
	public void volume04()
	{AlHelper.alSourcef(sourceId, AL10.AL_GAIN, 0.4f);}
	
	public void volume06()
	{AlHelper.alSourcef(sourceId, AL10.AL_GAIN, 0.6f);}
	
	public void volume08()
	{AlHelper.alSourcef(sourceId, AL10.AL_GAIN, 0.8f);}
	
	public void volume10()
	{AlHelper.alSourcef(sourceId, AL10.AL_GAIN, 1.0f);}
	
	/**
	 * This picth of a 3d sound source.
	 * @param pitch
	 */
	public void setPitch(float pitch)
	{
		AlHelper.alSourcef(sourceId, AL10.AL_PITCH, pitch);
	}
	
	public void setPosition(float x, float y, float z)
	{
		AlHelper.alSource3f(sourceId, AL10.AL_POSITION, x, y, z);
	}
	
	public void setVelocity(float x, float y, float z)
	{
		AlHelper.alSource3f(sourceId, AL10.AL_VELOCITY, x, y, z);
	}                                                          
                                                               	
	public void setLooping(boolean loop)                       
	{
		AlHelper.alSourcei(sourceId, AL10.AL_LOOPING, loop ? AL10.AL_TRUE : AL10.AL_FALSE);
	}
	
	public boolean isPlaying()
	{
		return AL10.alGetSourcei(sourceId, AL10.AL_SOURCE_STATE) == AL10.AL_PLAYING;
	}
	
	public void pause()
	{
		AL10.alSourcePause(sourceId);
	}
	
	public void countinuePlaying()
	{
		AL10.alSourcePlay(sourceId);
	}
	
	public void stop()
	{
		AL10.alSourceStop(sourceId);
	}
	
	/**
	 * Delete all source when game close.
	 */
	public void delete()
	{
		AlHelper.alDeleteSources(sourceId);
	}
}
