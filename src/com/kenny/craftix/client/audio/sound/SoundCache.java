package com.kenny.craftix.client.audio.sound;

import java.util.HashMap;
import java.util.Map;

import com.kenny.craftix.client.ResourceLocation;

public class SoundCache 
{
	/**The main instance of this class.*/
	public static final SoundCache CACHE = new SoundCache();
	/**Audio wav format.*/
	public static final String WAV_EXT = ".wav";
	/**Audio ogg format.*/
	public static final String OGG_EXT = ".ogg";
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Map<String, CachedSound> soundCache = new HashMap();

	public synchronized Sound requestSound(String in, boolean loadNow)
	{
		return requestSound(in, loadNow, false);
	}
	
	public synchronized Sound requestSound(String id, boolean loadNow, boolean oggFile)
	{
	    CachedSound cachedSound = (CachedSound)this.soundCache.get(id);
	    if (cachedSound == null) 
	    {
	    	return loadNewSound(id, loadNow, oggFile);
	    }
	    cachedSound.timesUsed += 1;
	    	return cachedSound.sound;
	}
	  
	public void releaseSound(Sound sound)
	{
	    CachedSound cachedSound = (CachedSound)this.soundCache.get(sound.getId());
	    cachedSound.timesUsed -= 1;
	    if (cachedSound.timesUsed == 0) 
	    {
	    	removeSoundFromCache(sound.getId());
	    }
	}
	  
	private Sound loadNewSound(String id, boolean loadNow, boolean oggFile)
	{
	    String ext = oggFile ? ".ogg" : ".wav";
	    Sound sound = null;
	    Sound sound1 = null;
	    if (loadNow) 
	    {
	    	sound1 = Sound.loadSoundObject(new ResourceLocation(ResourceLocation.SOUND_FOLDER, id + ext));
	    } 
	  
	    CachedSound cachedSound = new CachedSound(sound1);
	    this.soundCache.put(id, cachedSound);
	    	return sound1;
	}
	  
	private void removeSoundFromCache(String id)
	{
	    CachedSound cachedSound = (CachedSound)this.soundCache.remove(id);
	    cachedSound.sound.deleteSound();
	}
	
	private static class CachedSound
	{
		/**How much we use the sound.*/
		private int timesUsed = 1;
		/**This is cached sound.*/
		private Sound sound;
		
		private CachedSound(Sound soundIn)
		{
			this.sound = soundIn;
		}
	}
}


