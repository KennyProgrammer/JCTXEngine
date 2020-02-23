package com.kenny.craftix.client.audio.music;

import com.kenny.craftix.client.audio.sound.Sound;

public class MusicTrack 
{
	/**Id of the music track.*/
	private final int id;
	/**This is simple sound component for music.*/
	private final Sound sound;
	/**This is a name of the music.*/
	private final String name;
	/**If it is bonus track then this equals to true.*/
	private final boolean isBonusTrack;
	/**Check if this track not locked.*/
	private boolean locked;
	/**Check if this sound track needs to be update.*/
	private boolean needUpdate;
	
	public MusicTrack(int idIn, Sound soundIn, String nameIn, boolean isBonus)
	{
		this.sound = soundIn;
		this.id = idIn;
		this.name = nameIn;
		this.isBonusTrack = isBonus;
		this.locked = this.isBonusTrack;
		this.needUpdate = true;
	}

	/**
	 * Simply unlock the music track.
	 */
	public void unlock()
	{
		this.locked = false;
		this.needUpdate = true;
	}
	
	/**
	 * Reset the lock system. Just check it again.
	 */
	public void resetLock()
	{
		this.locked = this.isBonusTrack;
	}
	
	/**
	 * Return locked value. If true then sound id lock.
	 */
	public boolean isLocked() 
	{
		return this.locked;
	}
	
	public void setLocked(boolean locked) 
	{
		this.locked = locked;
	}

	public boolean isNeedUpdate() 
	{
		return this.needUpdate;
	}

	public void setNeedUpdate(boolean needUpdate) 
	{
		this.needUpdate = needUpdate;
	}

	public int getId() 
	{
		return this.id;
	}

	public Sound getSound() 
	{
		return this.sound;
	}

	public String getName() 
	{
		return this.name;
	}

	public boolean isBonusTrack() 
	{
		return this.isBonusTrack;
	}
	
	
}
