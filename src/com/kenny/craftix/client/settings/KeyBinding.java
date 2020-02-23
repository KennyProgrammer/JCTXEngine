package com.kenny.craftix.client.settings;

import java.util.Map;
import java.util.Set;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public class KeyBinding implements Comparable<KeyBinding>
{
	private static final Map<String, KeyBinding> KEYBIND_ARRAY = Maps.<String, KeyBinding>newHashMap();
	private static final Set<String> KEYBIND_SET = Sets.<String>newHashSet();
	/** A map that assigns priorities to all categories, for ordering purposes. */
    public static final Map<String, Integer> CATEGORY_ORDER = Maps.<String, Integer>newHashMap();
    private final String keyDescription;
    private final int keyCodeDefault;
    private final String keyCategory;
    private int keyCode;
    /** Is the key held down? */
    private boolean pressed;
    private int pressTime;
	
    /**
     * Convenience constructor for creating KeyBindings with keyConflictContext and keyModifier set.
     */
    public KeyBinding(String description,  int keyCode, String category)
    {
        this.keyDescription = description;
        this.keyCode = keyCode;
        this.keyCodeDefault = keyCode;
        this.keyCategory = category;
      
        KEYBIND_ARRAY.put(description, this);
        
        KEYBIND_SET.add(category);
    }
    
    @SuppressWarnings("unused")
	public static void setKeyBindState(int keyCode, boolean pressed)
    {
        if (keyCode != 0)
        {
        	KeyBinding keybinding = null;

            if (keybinding != null)
            {
                keybinding.pressed = pressed;
            }
        }
    }
    
    /**
     * Returns true if the key is pressed (used for continuous querying). Should be used in tickers.
     */
    public boolean isKeyDown()
    {
        return this.pressed;
    }
    
    @SuppressWarnings("unused")
	public static void onTick(int keyCode)
    {
        if (keyCode != 0)
        {
            KeyBinding keybinding = null;

            if (keybinding != null)
            {
                ++keybinding.pressTime;
            }
        }
    }
    
	@Override
	public int compareTo(KeyBinding o) 
	{
		return 0;
	}
	
	 public String getKeyCategory()
	    {
	        return this.keyCategory;
	    }

	    /**
	     * Returns true on the initial key press. For continuous querying use {@link isKeyDown()}. Should be used in key
	     * events.
	     */
	    public boolean isPressed()
	    {
	        if (this.pressTime == 0)
	        {
	            return false;
	        }
	        else
	        {
	            --this.pressTime;
	            return true;
	        }
	    }

	    public void unpressKey()
	    {
	        this.pressTime = 0;
	        this.pressed = false;
	    }

	    public String getKeyDescription()
	    {
	        return this.keyDescription;
	    }

	    public int getKeyCodeDefault()
	    {
	        return this.keyCodeDefault;
	    }

	    public int getKeyCode()
	    {
	        return this.keyCode;
	    }
	
	 public void setKeyCode(int keyCode)
	 {
	      this.keyCode = keyCode;
	 }

}
