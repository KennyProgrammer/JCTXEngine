package com.kenny.craftix.client.audio;

import java.io.IOException;

import com.kenny.craftix.client.audio.sound.SoundLoader;

/***
 * ITS OLD TEST AUDIO LOADER. SOON THIS CLASS WE BE REMOVED!
 * 
 * @author Kenny
 */
public class AudioMain 
{
	public static void main(String[] args) throws IOException
	{
		AudioMaster.init();
		SoundLoader.loadGameSounds();
		SoundLoader.sourceInGameSound1.play(SoundLoader.bufferInGameSound1);
		//SoundLoader.sourceButtonClick.play(SoundLoader.bufferButtonClick);
		
		char c = ' ';
		while(c != 'q')
		{
			c = (char)System.in.read();
			
			if(c == 'p')
			{	
				if(SoundLoader.sourceButtonClick.isPlaying())
					SoundLoader.sourceButtonClick.pause();
				else
					SoundLoader.sourceButtonClick.countinuePlaying();
			}
			
			if(c == 'o')
			{
				SoundLoader.sourceInGameSound1.play(SoundLoader.bufferButtonClick);
			}
		}
		
		SoundLoader.cleanUpSounds();
		AudioMaster.shutdownAudioMaster();
	}

}
