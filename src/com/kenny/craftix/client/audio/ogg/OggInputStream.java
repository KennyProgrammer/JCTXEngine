package com.kenny.craftix.client.audio.ogg;


import com.jcraft.jogg.Packet;
import com.jcraft.jogg.Page;
import com.jcraft.jogg.StreamState;
import com.jcraft.jogg.SyncState;
import com.jcraft.jorbis.Block;
import com.jcraft.jorbis.Comment;
import com.jcraft.jorbis.DspState;
import com.jcraft.jorbis.Info;
import java.io.ByteArrayOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class OggInputStream extends FilterInputStream
{
	  private float[][][] _pcm = new float[1][][];
	  private int[] _index;
	  private boolean eos = false;
	  private SyncState syncState = new SyncState();
	  private StreamState streamState = new StreamState();
	  private Page page = new Page();
	  private Packet packet = new Packet();
	  private Info info = new Info();
	  private Comment comment = new Comment();
	  private DspState dspState = new DspState();
	  private Block block = new Block(this.dspState);
	  private static int convsize = 8192;
	  private static byte[] convbuffer = new byte[convsize];
	  private int convbufferOff = 0;
	  private int convbufferSize = 0;
	  private byte[] readDummy = new byte[1];
	  
	  public OggInputStream(InputStream input)
	  {
	    super(input);
	    try
	    {
	      initVorbis();
	      this._index = new int[this.info.channels];
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	      this.eos = true;
	    }
	  }
	  
	  public int getFormat()
	  {
	    if (this.info.channels == 1) {
	      return 4353;
	    }
	    return 4355;
	  }
	  
	  public int getRate()
	  {
	    return this.info.rate;
	  }
	  
	  public int read()
	    throws IOException
	  {
	    int retVal = read(this.readDummy, 0, 1);
	    return retVal == -1 ? -1 : this.readDummy[0];
	  }
	  
	  public int read(byte[] b, int off, int len)
	    throws IOException
	  {
	    if (this.eos) {
	      return -1;
	    }
	    int bytesRead = 0;
	    while ((!this.eos) && (len > 0))
	    {
	      fillConvbuffer();
	      if (!this.eos)
	      {
	        int bytesToCopy = Math.min(len, this.convbufferSize - this.convbufferOff);
	        System.arraycopy(convbuffer, this.convbufferOff, b, off, bytesToCopy);
	        this.convbufferOff += bytesToCopy;
	        bytesRead += bytesToCopy;
	        len -= bytesToCopy;
	        off += bytesToCopy;
	      }
	    }
	    return bytesRead;
	  }
	  
	  public int read(ByteBuffer b, int off, int len)
	    throws IOException
	  {
	    if (this.eos) {
	      return -1;
	    }
	    b.position(off);
	    int bytesRead = 0;
	    while ((!this.eos) && (len > 0))
	    {
	      fillConvbuffer();
	      if (!this.eos)
	      {
	        int bytesToCopy = Math.min(len, this.convbufferSize - this.convbufferOff);
	        b.put(convbuffer, this.convbufferOff, bytesToCopy);
	        this.convbufferOff += bytesToCopy;
	        bytesRead += bytesToCopy;
	        len -= bytesToCopy;
	      }
	    }
	    return bytesRead;
	  }
	  
	  public boolean isEndOfStream()
	  {
	    return this.eos;
	  }
	  
	  public int available()
	    throws IOException
	  {
	    return this.eos ? 0 : 1;
	  }
	  
	  public void reset()
	    throws IOException
	  {}
	  
	  public boolean markSupported()
	  {
	    return false;
	  }
	  
	  public long skip(long n)
	    throws IOException
	  {
	    int bytesRead = 0;
	    while (bytesRead < n)
	    {
	      int res = read();
	      if (res == -1) {
	        break;
	      }
	      bytesRead++;
	    }
	    return bytesRead;
	  }
	  
	  private void fillConvbuffer()
	    throws IOException
	  {
	    if (this.convbufferOff >= this.convbufferSize)
	    {
	      this.convbufferSize = lazyDecodePacket();
	      this.convbufferOff = 0;
	      if (this.convbufferSize == -1) {
	        this.eos = true;
	      }
	    }
	  }
	  
	  private void initVorbis()
	    throws Exception
	  {
	    this.syncState.init();
	    
	    int index = this.syncState.buffer(4096);
	    byte[] buffer = this.syncState.data;
	    int bytes = this.in.read(buffer, index, 4096);
	    this.syncState.wrote(bytes);
	    if (this.syncState.pageout(this.page) != 1)
	    {
	      if (bytes < 4096) {
	        return;
	      }
	      throw new Exception("Input does not appear to be an Ogg bitstream.");
	    }
	    this.streamState.init(this.page.serialno());
	    
	    this.info.init();
	    this.comment.init();
	    if (this.streamState.pagein(this.page) < 0) {
	      throw new Exception("Error reading first page of Ogg bitstream data.");
	    }
	    if (this.streamState.packetout(this.packet) != 1) {
	      throw new Exception("Error reading initial header packet.");
	    }
	    if (this.info.synthesis_headerin(this.comment, this.packet) < 0) {
	      throw new Exception("This Ogg bitstream does not contain Vorbis audio data.");
	    }
	    int i = 0;
	    while (i < 2)
	    {
	      while (i < 2)
	      {
	        int result = this.syncState.pageout(this.page);
	        if (result == 0) {
	          break;
	        }
	        if (result == 1)
	        {
	          this.streamState.pagein(this.page);
	          while (i < 2)
	          {
	            result = this.streamState.packetout(this.packet);
	            if (result == 0) {
	              break;
	            }
	            if (result == -1) {
	              throw new Exception("Corrupt secondary header. Exiting.");
	            }
	            this.info.synthesis_headerin(this.comment, this.packet);
	            i++;
	          }
	        }
	      }
	      index = this.syncState.buffer(4096);
	      buffer = this.syncState.data;
	      bytes = this.in.read(buffer, index, 4096);
	      if (bytes < 0) {
	        bytes = 0;
	      }
	      if ((bytes == 0) && (i < 2)) {
	        throw new Exception("End of file before finding all Vorbis headers!");
	      }
	      this.syncState.wrote(bytes);
	    }
	    convsize = 4096 / this.info.channels;
	    
	    this.dspState.synthesis_init(this.info);
	    this.block.init(this.dspState);
	  }
	  
	  private int decodePacket(Packet packet)
	  {
	    boolean bigEndian = ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN;
	    if (this.block.synthesis(packet) == 0) {
	      this.dspState.synthesis_blockin(this.block);
	    }
	    int convOff = 0;
	    int samples;
	    while ((samples = this.dspState.synthesis_pcmout(this._pcm, this._index)) > 0)
	    {
	      int samples1 = 0;
	      float[][] pcm = this._pcm[0];
	      int bout = samples1 < convsize ? samples1 : convsize;
	      for (int i = 0; i < this.info.channels; i++)
	      {
	        int ptr = (i << 1) + convOff;
	        
	        int mono = this._index[i];
	        for (int j = 0; j < bout; j++)
	        {
	          int val = (int)(pcm[i][(mono + j)] * 32767.0D);
	          
	          val = Math.max(32768, Math.min(32767, val));
	          val |= (val < 0 ? 32768 : 0);
	          
	          convbuffer[(ptr + 0)] = ((byte)(bigEndian ? val >>> 8 : val));
	          convbuffer[(ptr + 1)] = ((byte)(bigEndian ? val : val >>> 8));
	          
	          ptr += (this.info.channels << 1);
	        }
	      }
	      convOff += 2 * this.info.channels * bout;
	      
	      this.dspState.synthesis_read(bout);
	    }
	    return convOff;
	  }
	  
	  private int lazyDecodePacket()
	    throws IOException
	  {
	    int result = getNextPacket(this.packet);
	    if (result == -1) {
	      return -1;
	    }
	    return decodePacket(this.packet);
	  }
	  
	  private int getNextPacket(Packet packet)
	    throws IOException
	  {
	    boolean fetchedPacket = false;
	    while ((!this.eos) && (!fetchedPacket))
	    {
	      int result1 = this.streamState.packetout(packet);
	      if (result1 == 0)
	      {
	        int result2 = 0;
	        while ((!this.eos) && (result2 == 0))
	        {
	          result2 = this.syncState.pageout(this.page);
	          if (result2 == 0) {
	            fetchData();
	          }
	        }
	        if ((result2 == 0) && (this.page.eos() != 0)) {
	          return -1;
	        }
	        if (result2 == 0)
	        {
	          fetchData();
	        }
	        else
	        {
	          if (result2 == -1)
	          {
	            System.out.println("syncState.pageout(page) result == -1");
	            return -1;
	          }
	          int i = this.streamState.pagein(this.page);
	        }
	      }
	      else
	      {
	        if (result1 == -1)
	        {
	          System.out.println("streamState.packetout(packet) result == -1");
	          return -1;
	        }
	        fetchedPacket = true;
	      }
	    }
	    return 0;
	  }
	  
	  private void fetchData()
	    throws IOException
	  {
	    if (!this.eos)
	    {
	      int index = this.syncState.buffer(4096);
	      if (index < 0)
	      {
	        this.eos = true;
	        return;
	      }
	      int bytes = this.in.read(this.syncState.data, index, 4096);
	      this.syncState.wrote(bytes);
	      if (bytes == 0) {
	        this.eos = true;
	      }
	    }
	  }
	  
	  public String toString()
	  {
	    String s = "";
	    s = s + "version         " + this.info.version + "\n";
	    s = s + "channels        " + this.info.channels + "\n";
	    s = s + "rate (hz)       " + this.info.rate;
	    return s;
	  }
	  
	  public static void main(String[] args)
	  {
	    try
	    {
	      InputStream in = new Object().getClass().getResourceAsStream("/audio/slash.ogg");
	      ByteArrayOutputStream byteOut = new ByteArrayOutputStream(262144);
	      byteOut.reset();
	      byte[] copyBuffer = new byte['?'];
	      OggInputStream oggInput = new OggInputStream(in);
	      boolean done = false;
	      while (!done)
	      {
	        int bytesRead = oggInput.read(copyBuffer, 0, copyBuffer.length);
	        byteOut.write(copyBuffer, 0, bytesRead);
	        done = (bytesRead != copyBuffer.length) || (bytesRead < 0);
	      }
	      System.out.println(byteOut.size() + " bytes read");
	      System.out.println(oggInput);
	    }
	    catch (IOException e)
	    {
	      e.printStackTrace();
	    }
	  }
}
