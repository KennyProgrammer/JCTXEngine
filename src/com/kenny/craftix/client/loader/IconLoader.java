package com.kenny.craftix.client.loader;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class IconLoader 
{
	/**
	 * ByteBuffer help to loads data about the icon.
	 */
	/**
	public ByteBuffer loadIcon(URL url) throws IOException 
	{
	    InputStream is = url.openStream();
	    try {
	        PNGDecoder decoder = new PNGDecoder(is);
	        ByteBuffer bb = ByteBuffer.allocateDirect(decoder.getWidth()*decoder.getHeight()*4);
	        decoder.decode(bb, decoder.getWidth()*4, PNGDecoder.Format.RGBA);
	        bb.flip();
	        return bb;
	    } 
	    finally 
	    {
	        is.close();
	    }
	}
	*/
	
	public ByteBuffer loadIcon(String path) throws IOException 
	{
        InputStream inputStream = new FileInputStream(path);
        try 
        {
            PngDecoder decoder = new PngDecoder(inputStream);
            ByteBuffer bytebuf = ByteBuffer.allocateDirect(decoder.getWidth()*decoder.getHeight()*4);
            decoder.decode(bytebuf, decoder.getWidth()*4, PngDecoder.Format.RGBA);
            bytebuf.flip();
            	return bytebuf;
            
        } 
        finally 
        {
            inputStream.close();
        }
    }
}
