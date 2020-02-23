package com.kenny.craftix.client.audio.music;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Playlist 
{
	
	private Map<Integer, MusicTrack> musics = new LinkedHashMap<>();
	
	/**
	 * Simply adds a new music track to the track system.
	 */
	public void addMusic(MusicTrack musicIn)
	{
		this.musics.put(Integer.valueOf(musicIn.getId()), musicIn);
	}
	
	/**
	 * Clears the entire track system. Removes all tracks from their map.
	 */
	public void clear()
	{
		this.musics.clear();
	}
	
	/**
	 * Will check already for the sound whether it is loaded or not.
	 */
	public boolean isLoaded()
	{
		for(MusicTrack sound : this.musics.values())
		{
			if(!sound.getSound().isLoaded())
			{
				return false;
			}
		}
		return true;
	}
	
	public Collection<MusicTrack> getOrderedTrack()
	{
		return this.musics.values();
	}
	
	/**
	 * Return the music track object use id.
	 */
	public MusicTrack getTrack(int id)
	{
		return (MusicTrack)this.musics.get(Integer.valueOf(id));
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected List<MusicTrack> getShuffledMusicList(MusicTrack prevPlayTrack)
	{
		List<MusicTrack> tempList = new ArrayList();
		tempList.addAll(this.musics.values());
		List<MusicTrack> shuffledList = new ArrayList();
		while(!tempList.isEmpty())
		{
			MusicTrack track = removeRandomTrackFromList(tempList);
			if(!track.isLocked())
			{
				shuffledList.add(track);
			}
		}
		ensurePreviousTrackNotRepeated(shuffledList, prevPlayTrack);
		return shuffledList;
	}
	
	/**
	 * Remove the random track from the map.
	 */
	private MusicTrack removeRandomTrackFromList(List<MusicTrack> listOfMusic)
	{
	    int index = new Random().nextInt(listOfMusic.size());
	    return (MusicTrack)listOfMusic.remove(index);
	}
	
	/**
	 * It is guaranteed that the previous track from the list will 
	 * not be repeated.
	 */
	private void ensurePreviousTrackNotRepeated(List<MusicTrack> newPlaylist, MusicTrack previouslyPlayed)
	{
	   if ((!newPlaylist.isEmpty()) && (newPlaylist.get(0) == previouslyPlayed))
	   {
	       MusicTrack track = (MusicTrack)newPlaylist.remove(0);
	       newPlaylist.add(track);
	   }
	}
}
