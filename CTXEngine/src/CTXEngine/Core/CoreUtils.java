package CTXEngine.Core;

import static CTXEngine.Core.SimplePrint.CTX_ENGINE_ERROR;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.lwjgl.BufferUtils;


public class CoreUtils 
{
	/*
	 * This Pair class is c++ std::pair<a, b> realization in Java.
	 */
	public static final class std_pair<T1, T2> implements Comparable<std_pair<T1, T2>>
	{
	    public final T1 first;
	    public final T2 second;

	    private std_pair(T1 first, T2 second) 
	    {
	        this.first = first;
	        this.second = second;
	    }

	    public static <a, b> std_pair<a, b> of(a first, b second) 
	    {
	        return new std_pair<a, b>(first, second);
	    }

	    /**
	     * Compares this object with the specified object for order.
	     */
	    @Override
	    public int compareTo(std_pair<T1, T2> o) 
	    {
	        int cmp = compare(first, o.first);
	        return cmp == 0 ? compare(second, o.second) : cmp;
	    }

	    /**
	     * Todo move this to a helper class.
	     */
	    @SuppressWarnings("unchecked")
		private static int compare(Object o1, Object o2) 
	    {
	        return o1 == null ? o2 == null ? 0 : -1 : o2 == null ? + 1
	                : ((Comparable<Object>) o1).compareTo(o2);
	    }

	    /**
	     * Returns a hash code value for the object.
	     */
	    @Override
	    public int hashCode() 
	    {
	        return 31 * hashcode(first) + hashcode(second);
	    }

	    /**
	     * Todo move this to a helper class.
	     */
	    private static int hashcode(Object o) 
	    {
	        return o == null ? 0 : o.hashCode();
	    }
	    
	    public T1 getFirst() 
	    {
			return this.first;
		}
	    
	    public T2 getSecond() 
	    {
			return this.second;
		}
	}
	
	private static ByteBuffer resizeBuffer(ByteBuffer buffer, int newCapacity)
	{
	    ByteBuffer newBuffer = BufferUtils.createByteBuffer(newCapacity);
	    buffer.flip();
	    newBuffer.put(buffer);
	    return newBuffer;
	}
	
	/**
	 * Reads the specified resource and returns the raw data as a ByteBuffer.
	 *
	 * @param resource   the resource to read
	 * @param bufferSize the initial buffer size
	 *
	 * @return the resource data
	 *
	 * @throws IOException if an IO error occurs
	 */
	public static ByteBuffer ioResourceToByteBuffer(String resource, int bufferSize) throws IOException 
	{
	    ByteBuffer buffer;

	    Path path = Paths.get(resource);
	    if ( Files.isReadable(path) ) {
	        try (SeekableByteChannel fc = Files.newByteChannel(path)) {
	            buffer = BufferUtils.createByteBuffer((int)fc.size() + 1);
	            while ( fc.read(buffer) != -1 ) ;
	        }
	    } else {
	        try (
	            InputStream source = CoreUtils.class.getClassLoader().getResourceAsStream(resource);
	            ReadableByteChannel rbc = Channels.newChannel(source)
	        ) {
	            buffer = BufferUtils.createByteBuffer(bufferSize);

	            while ( true ) {
	                int bytes = rbc.read(buffer);
	                if ( bytes == -1 )
	                    break;
	                if ( buffer.remaining() == 0 )
	                    buffer = resizeBuffer(buffer, buffer.capacity() * 2);
	            }
	        }
	    }

	    buffer.flip();
	    return buffer;
	}
	
	public String readFile(final String file)
	{
		StringBuilder shaderSource = new StringBuilder();
        try
        {
        	InputStream in = Class.class.getResourceAsStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while((line = reader.readLine())!=null)
            {
                shaderSource.append(line).append("//\n");
            }
            reader.close();
            
            return file;
        }
        catch(IOException e)
        {
        	CTX_ENGINE_ERROR("This file '" + file + "' cound be read!");
            e.printStackTrace();
            Runtime.getRuntime().exit(-1);
        }
        
        return "[Reading Error]: This file '" + file + "' cound be read!";
	}
	
	public static int findFirstOf(String findIn, String letters, int position)
	{
	     Pattern pattern = Pattern.compile("[" + letters + "]");
	     Matcher matcher = pattern.matcher(findIn);
	     if (matcher.find()) 
	     {
	         position = matcher.start();
	     }
	     
	     return position;
	}
	
	public static int findFirstNotOf(String searchIn, String searchFor, int searchFrom) 
	{
	    char[] chars = searchFor.toCharArray();
	    boolean found;
	    for (int i = searchFrom; i < searchIn.length(); i++) 
	    {
	        found = true;
	        for (char c : chars)
	        {
	            if (searchIn.indexOf(c) == -1) 
	            {
	                found = false;
	            }
	        }
	        if (!found) 
	        {
	            return i;
	        }
	    }
	    return -1;
	}

}
