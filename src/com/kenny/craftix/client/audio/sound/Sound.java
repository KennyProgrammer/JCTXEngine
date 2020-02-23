package com.kenny.craftix.client.audio.sound;

import com.kenny.craftix.client.ResourceLocation;
import com.kenny.craftix.client.audio.AlHelper;
import com.kenny.craftix.utils.math.Maths;

/**
 *This class contains everything about the sound object. The sound 
 *is completely like the game objects can be controlled from here.
 *
 * @author Kenny
 */
public class Sound 
{
	/**This is string type id of sound.*/
	public final String id;
	/**Id of the sound buffer/storage.*/
	private int bufferId;
	/**The volume of the source sound.*/
	private float volume;
	/**Check if sound the actually loaded or not.*/
	protected boolean loaded;
	/**If true then pitch of sound be set randomlly.*/
	protected boolean randomPitch;
	/**Check if needs stram to this source sound.*/
	protected boolean needsStreaming;
	/**Check if this sound is a ogg file format.*/
	private final boolean oggFile;
	/**Minimum voice pitch*/
	private float minPitch;
	/**Maximum voice pitch*/
	private float maxPitch;
	/**Number of bytes read.*/
	private int bytesRead;
	/**Sound file*/
	private ResourceLocation soundfile;
	
	public Sound(ResourceLocation soundFileIn) 
	{
		this.soundfile = soundFileIn;
		String[] names = this.soundfile.getName().split("\\.");
		this.id = names[0];
		this.volume = 1.0F;
		this.randomPitch = false;
		this.oggFile = names[1].contains("ogg");
		this.loaded = true;
	}
	
	public Sound(ResourceLocation soundFileIn, float volumeIn, boolean useRandomPitch)
	{
		this(soundFileIn);
		this.volume = volumeIn;
		this.randomPitch = useRandomPitch;
	}
	
	/**
	 * Create a new sound object from this constructor to actually load the sound.
	 */
	public static Sound loadSoundObject(ResourceLocation soundFileIn)
	{
		Sound sound = new Sound(soundFileIn);
		SoundLoader.doInitialSoundLoad(sound);
		return sound;
	}
	
	/**
	 * Enable the random pitch of the sound and set bounds of the pitch.
	 */
	public Sound randomizePitch(float minIn, float maxIn)
	{
		this.randomPitch = true;
		this.minPitch = minIn;
		this.maxPitch = maxIn;
			return this;
	}
	
	/**
	 * Delete the sound buffer id, and remove the sound source.
	 */
	public void deleteSound()
	{
		if(!this.loaded)
			return;
		AlHelper.alDeleteBuffers(this.bufferId);
		this.loaded = false;
	}
	
	/**
	 * Set the volume of the sound source.
	 */
	public Sound setVolume(float volumeIn)
	{
		this.volume = volumeIn;
			return this;
	}
	
	/**
	 * Return the random numer between the values.
	 */
	public float getPitch() 
	{
		if(!this.randomPitch)
			return 1.0F;
		return Maths.randomNumberBetween(this.minPitch, this.maxPitch);
	}
	
	public void setBuffer(int bufferIn, int bytesRead)
	{
		this.bufferId = bufferIn;
		this.bytesRead = bytesRead;
		this.loaded = true;
	}
	
	/**
	 * Get sound value volume.
	 */
	public float getVolume()
	{
		return this.volume;
	}
	
	/**
	 * Return the buffer/storage to user.
	 */
	public int getBuffer()
	{
		return this.bufferId;
	}
	
	/**
	 * Return the file of the current sound source.
	 */
	public ResourceLocation getFile()
	{
		return this.soundfile;
	}

	/**
	 * Set the pitch of the sound source.
	 */
	public void setPitch(float minPitch, float maxPitch)
	{
		this.maxPitch = maxPitch;
		this.minPitch = minPitch;
	}

	public int getBytesRead() 
	{
		return bytesRead;
	}

	public void setBytesRead(int bytesRead) 
	{
		this.bytesRead = bytesRead;
	}

	public boolean isOggFile() 
	{
		return oggFile;
	}

	public void setNeedsStreaming(boolean b) 
	{
		this.needsStreaming = b;
		
	}
	
	/**
	 * Return true if sound actually loaded.
	 */
	public boolean isLoaded()
	{
		return this.loaded;
	}

	public String getId() 
	{
		return this.id;
	}
}
