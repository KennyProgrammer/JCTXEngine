package com.kenny.craftix.client.audio.ogg;

import com.kenny.craftix.client.audio.sound.Sound;
import com.kenny.craftix.client.audio.wav.WavDataStream;

public class DataStreamFactory 
{
	/**
	 * Open the stream source ogg, or wav format, contain sound object.
	 */
	public static IDataStream openStream(Sound soundIn) throws Exception
	{
		if(soundIn.isOggFile())
		{
			return OggDataStream.openOggStream(soundIn.getFile(), 100000);
		}
		return WavDataStream.openWavStream(soundIn.getFile(), 100000);
	}
}
