package com.kenny.craftix.client.keyboard;

import com.kenny.craftix.client.Craftix;

public class KeyboardManager
{
	/**
	 * Simple key down event. When user press on button event is activated.
	 * 
	 * @param keyIn - is key a user can presed.
	 * @return 
	 */
	public static boolean keyDown(int keyIn)
	{
		return org.lwjgl.input.Keyboard.isKeyDown(keyIn);
	}
	
	/**
	 * Returns true if the right shift or left shift button is pressed.
	 */
	public static boolean isShiftKeyDown()
	{
		return keyDown(42) || keyDown(54);
	}
	
	/**
	 * Returns true if key enter or numpad enter button is pressed.
	 */
	public static boolean isEnterKeyDown()
	{
		return keyDown(156);
	}
	
	/**
	 * Returns true if key space button is pressed.
	 */
	public static boolean isSpaceKeyDown()
	{
		return keyDown(57);
	}
	
	/**
	 * Return true if right alt or left alt keys is down.
	 */
	public static boolean isAltKeyDown()
	{
		return keyDown(56) || keyDown(184);
	}
	
	/**
	 * Use for something in the engine a will do in future.
	 */
	public static boolean isF3KeyDown()
	{
		return keyDown(61);
	}
	
	/**
	 * Use for a change a fullscreen mode, in engine.
	 */
	public static boolean isF12KeyDown()
	{
		return keyDown(88);
	}
	
	/**
	 * This method was created because the escape button is used too often in the 
	 * engine. Returns true if the escape key is pressed.
	 */
	public static boolean isEscKeyDown()
	{
		return keyDown(1);
	}
	
	/**
	 * Returns true if the strl button on the Mac is pressed otherwise the 
	 * button is pressed on the Windows.
	 */
	public static boolean isCtrlKeyDown()
	{
		if(Craftix.IS_RUNNING_ON_MAC)
		{
			return keyDown(219) || keyDown(220);
		}
		else
		{
			return keyDown(29) || keyDown(157);
		}
	}
	
	/**
	 * Here a combo pressed button.
	 * 
	 * @return - true if all this button in constructor has been preesed.
	 */
	
	public static boolean isKeyComboCtrlX(int keyID)
    {
        return keyDown(45) && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown();
    }

    public static boolean isKeyComboCtrlV()
    {
        return keyDown(47) && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown();
    }

    public static boolean isKeyComboCtrlC(int keyID)
    {
        return keyDown(46) && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown();
    }

    public static boolean isKeyComboCtrlA(int keyID)
    {
        return keyDown(30) && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown();
    }
    
    public static boolean isKeyComboF3(int comboKeyID)
    {
    	return keyDown(comboKeyID) && isF3KeyDown() && !isShiftKeyDown() && !isAltKeyDown() && !isCtrlKeyDown();
    }
}
