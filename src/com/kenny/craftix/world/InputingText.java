package com.kenny.craftix.world;

import org.lwjgl.input.Keyboard;

import com.kenny.craftix.client.keyboard.KeyboardManager;

public class InputingText extends KeyboardManager
{
	public char[] letters = new char[] {'a','b','c','d','e'};
	public String printString = "W";
	public String[] fullLetter;
	
	public void printText()
	{	
		if(keyDown(Keyboard.KEY_A))
		{
			//TextLoader.updateText(TextLanguage.t120b, this.fullLetter);
		}
		else if(keyDown(Keyboard.KEY_B))
		{
			//TextLoader.updateText(TextLanguage.t120b, this.fullLetter);
		}
		
		System.out.println(this.fullLetter);
		
		
	}
}
