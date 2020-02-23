package com.kenny.craftix.client;

import com.kenny.craftix.client.audio.music.MusicTrack;
import com.kenny.craftix.client.audio.music.Playlist;
import com.kenny.craftix.client.audio.sound.SoundCache;

public class CraftixMusic 
{
	/**This is main craftix engine music playlist.*/
	private static Playlist playList;
	
	/**
	 * Add all music to the main current playlist of tracks.
	 */
	public static void loadMusic()
	{
		playList = new Playlist();
		playList.addMusic(new MusicTrack(1, SoundCache.CACHE.requestSound("horizons", true), "horizons", false));
	}
	
	/**
	 * Will check already for the sound whether it is loaded or not.
	 */
	public static boolean isLoaded()
	{
		return playList.isLoaded();
	}
	
	public static void startPlayingPlayList()
	{
		//Music
	}
	
	public static Playlist getPlayList()
	{
		return playList;
	}
	
	public static void resetMusic()
	{
	    for (MusicTrack track : playList.getOrderedTrack()) 
	    {
	    	track.resetLock();
	    }
	}
	  
	public static MusicTrack getTrack(int id)
	{
	    return playList.getTrack(id);
	}
}
