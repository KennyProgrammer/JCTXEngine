package com.kenny.craftix.client.settings;

import org.lwjgl.input.Keyboard;

public class KeyBindingMap 
{
     public boolean matches(int keyCode)
     {
         return keyCode == Keyboard.KEY_LSHIFT || keyCode == Keyboard.KEY_RSHIFT;
     }
}
