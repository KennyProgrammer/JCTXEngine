package com.kenny.craftix.client.audio.sound;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.kenny.craftix.client.audio.AlHelper;
import com.kenny.craftix.client.audio.AudioLoader;
import com.kenny.craftix.client.audio.AudioMaster;
import com.kenny.craftix.client.audio.Source;
import com.kenny.craftix.client.audio.ogg.DataStreamFactory;
import com.kenny.craftix.client.audio.ogg.IDataStream;
import com.kenny.craftix.client.audio.ogg.OggInputStream;
import com.kenny.craftix.client.settings.InGameSettings;

public class SoundLoader 
{
	private static final Logger LOGGER = LogManager.getLogger("Craftix/AudioLoader"); 
	public static final String AUDIO = "src/com/kenny/craftix/client/audio/sounds/";
	public static float xPos;
	
	/**Buffer for sounds list.*/
	public static int bufferMain;
	public static int bufferButtonClick;
	public static int bufferPlayerJump;
	public static int bufferMenu1;
	public static int bufferInGameSound1;
	public static int bufferInGameSound2;
	
	/**Sounds list*/
	public static Source sourceMain;
	public static Source sourceButtonClick;
	public static Source sourceButtonClickSlow;
	public static Source sourcePlayerJump;
	public static Source sourceMenu1;
	public static Source sourceInGameSound1;
	public static Source sourceInGameSound2;
	
	public static AudioLoader audioLoader;
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static List<Integer> buffers = new ArrayList();
	
	public static void loadGameSounds() throws IOException
	{
		audioLoader = new AudioLoader();
		try
		{
			bufferButtonClick = AudioMaster.loadSound((new BufferedInputStream(new FileInputStream(AUDIO + "button_click.wav"))));
			bufferPlayerJump = AudioMaster.loadSound((new BufferedInputStream(new FileInputStream(AUDIO + "player_jump.wav"))));
			bufferMain = AudioMaster.loadSound((new BufferedInputStream(new FileInputStream(AUDIO + "main.wav"))));
			bufferMenu1 = AudioMaster.loadSound((new BufferedInputStream(new FileInputStream(AUDIO + "menu1.wav"))));
			bufferInGameSound1 = AudioMaster.loadSound((new OggInputStream(new FileInputStream(AUDIO + "horizons.ogg"))));
			bufferInGameSound2 = AudioMaster.loadSound((new BufferedInputStream(new FileInputStream(AUDIO + "ambition.wav"))));
		}
		catch(FileNotFoundException e)
		{
			LOGGER.error("File not found in audio folder.");
			e.printStackTrace();
		}
		
		sourceButtonClick = new Source();
		sourceMain = new Source();
		sourceMenu1 = new Source();
		sourceButtonClickSlow = new Source();
		sourcePlayerJump = new Source();
		sourceInGameSound1 = new Source();	
		sourceInGameSound2 = new Source();
	
	}
	
	public static void decreaseSoundXPosition()
	{
		xPos -= 0.03f;
		sourceButtonClick.setPosition(xPos, 0, 0);
		try 
		{
			Thread.sleep(10);
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
	}
	
	public static void setSoundParameters()
	{
		sourceButtonClickSlow.setPitch(0.5f);
		//sourcePlayerJump.setPitch(2f);
		
	}
	
	public static void soundPlay(Source sound, int soundBuffer)
	{
		if(InGameSettings.useAudioIn)
		sound.play(soundBuffer);
	}
	
	public static void soundPause(Source sound)
	{
		sound.pause();
	}
	
	public static void continiuePlayingSound(Source sound)
	{
		sound.countinuePlaying();
	}
	
	public static void cleanUpSounds()
	{
		sourceButtonClick.delete();
		sourceMain.delete();
	}
	
	  
	  protected static void doInitialSoundLoad(Sound sound)
	  {
	    try
	    {
	    	System.out.println("Loading " + sound.getFile());
	    	IDataStream stream = DataStreamFactory.openStream(sound);
	      	ByteBuffer byteBuffer = stream.loadNextData();
	      	int bufferID = generateBuffer();
	      	loadSoundDataIntoBuffer(bufferID, byteBuffer, stream.getAlFormat(), stream.getSampleRate());
	      	sound.setNeedsStreaming(!stream.hasEnded());
	      	sound.setBuffer(bufferID, byteBuffer.limit());
	      	stream.close();
	    }
	    catch (Exception e)
	    {
	    	e.printStackTrace();
	      	System.err.println("Couldn't load sound file " + sound.getFile());
	    }
	  }
	  
	  protected static int generateBuffer()
	  {
		  int bufferID = AlHelper.alGenBuffers();
	    	buffers.add(Integer.valueOf(bufferID));
	    	return bufferID;
	  }
	  
	  protected static void deleteBuffer(Integer bufferID)
	  {
		  buffers.remove(bufferID);
	    	AlHelper.alDeleteBuffers(bufferID.intValue());
	   
	  }
	  
	  protected static void cleanUp()
	  {
	    for (@SuppressWarnings("rawtypes")
		Iterator localIterator = buffers.iterator(); localIterator.hasNext();)
	    {
	    	int buffer = ((Integer)localIterator.next()).intValue();
	    	AlHelper.alDeleteBuffers(buffer);
	    }
	    
	  }
	  
	  protected static void loadSoundDataIntoBuffer(int bufferID, ByteBuffer data, int format, int sampleRate)
	  {
		  AlHelper.alBufferData(bufferID, format, data, sampleRate);
	  }
}
