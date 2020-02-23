package com.kenny.craftix.client.text;

import com.kenny.craftix.client.gui.GuiBackground.Pages;
import com.kenny.craftix.client.settings.InGameSettings;

public class TextChat 
{
	public static Text a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z;
	public static Text space, shift, alt, ctrl, esc, caps, backspace, enter;
	public static Text comboF3_1;
	public static Text currentOption;
	public boolean keyForward;
	
	public void loadChatWords()
	{
		w = new Text("W", 1.6f, 0.35f, 0.2f);
		a = new Text("A", 1.6f, 0.35f, 0.1f);
		s = new Text("S", 1.6f, 0.35f, 0.3f);
		d = new Text("D", 1.6f, 0.35f, 0.4f);
		space = new Text("Space", 1.2f, 0.35f, 0.51f);
		shift = new Text("Shift", 1.2f, 0.35f, 0.61f);
		e = new Text("E", 1.6f, 0.35f, 0.7f);
		q = new Text("Q", 1.6f, 0.35f, 0.8f);
		esc = new Text("Esc", 1.3f, 0.35f, 0.91f);
		caps = new Text("Caps", 1.3f, 0.35f, 1.01f);
		comboF3_1 = new Text("F3 + 1", 1.2f, 0.35f, 1.11f);
		currentOption = new Text("options.controls.currentOption", 0.2f, 0.47f, true);
		
		this.parseIntToStringLetters(w, InGameSettings.keyboardMovementForwardIn);
		this.parseIntToStringLetters(a, InGameSettings.keyboardMovementBackwardIn);
		this.parseIntToStringLetters(s, InGameSettings.keyboardMovementLeftwardIn);
		this.parseIntToStringLetters(d, InGameSettings.keyboardMovementRightwardIn);
		this.parseIntToStringLetters(space, InGameSettings.keyboradMovementJumpIn);
		this.parseIntToStringLetters(shift, InGameSettings.keyboradMovementRunIn);
		this.parseIntToStringLetters(esc, InGameSettings.keyboardPauseIn);
		
		TextLoader.addText(a); TextLoader.addText(w); TextLoader.addText(s);TextLoader.addText(d); TextLoader.addText(space);
		TextLoader.addText(shift); TextLoader.addText(e); TextLoader.addText(q); TextLoader.addText(esc); TextLoader.addText(caps);
		TextLoader.addText(comboF3_1); TextLoader.addText(currentOption);
	}
	
	public void parseUpdateIntToStringLetters(Text currentLetter, int currentOption)
	{
		if(currentOption == 30) 
			TextLoader.updateText(currentLetter, "A");
		else if(currentOption == 48)
			TextLoader.updateText(currentLetter, "B");
		else if(currentOption == 46)
			TextLoader.updateText(currentLetter, "C");
		else if (currentOption == 32)
			TextLoader.updateText(currentLetter, "D");
		else if (currentOption == 18)
			TextLoader.updateText(currentLetter, "E");
		else if (currentOption == 33)
			TextLoader.updateText(currentLetter, "F");
		else if (currentOption == 34)
			TextLoader.updateText(currentLetter, "G");
		else if (currentOption == 35)
			TextLoader.updateText(currentLetter, "H");
		else if (currentOption == 23)
			TextLoader.updateText(currentLetter, "I");
		else if (currentOption == 36)
			TextLoader.updateText(currentLetter, "J");
		else if (currentOption == 37)
			TextLoader.updateText(currentLetter, "K");
		else if (currentOption == 38)
			TextLoader.updateText(currentLetter, "L");
		else if (currentOption == 50)
			TextLoader.updateText(currentLetter, "M");
		else if (currentOption == 49)
			TextLoader.updateText(currentLetter, "N");
		else if (currentOption == 24)
			TextLoader.updateText(currentLetter, "O");
		else if (currentOption == 25)
			TextLoader.updateText(currentLetter, "P");
		else if (currentOption == 16)
			TextLoader.updateText(currentLetter, "Q");
		else if (currentOption == 19)
			TextLoader.updateText(currentLetter, "R");
		else if (currentOption == 31)
			TextLoader.updateText(currentLetter, "S");
		else if (currentOption == 20)
			TextLoader.updateText(currentLetter, "T");
		else if (currentOption == 22)
			TextLoader.updateText(currentLetter, "U");
		else if (currentOption == 47)
			TextLoader.updateText(currentLetter, "V");
		else if (currentOption == 17)
			TextLoader.updateText(currentLetter, "W");
		else if (currentOption == 45)
			TextLoader.updateText(currentLetter, "X");
		else if (currentOption == 21)
			TextLoader.updateText(currentLetter, "Y");
		else if (currentOption == 44)
			TextLoader.updateText(currentLetter, "Z");
		else if (currentOption == 15)
			TextLoader.updateText(currentLetter, "Tab");
		else if (currentOption == 58)
			TextLoader.updateText(currentLetter, "Caps");
		else if (currentOption == 42 || currentOption == 54)
			TextLoader.updateText(currentLetter, "Shift");
		else if (currentOption == 29 || currentOption == 157)
			TextLoader.updateText(currentLetter, "Ctrl");
		else if (currentOption == 56 || currentOption == 184)
			TextLoader.updateText(currentLetter, "Alt");
		else if (currentOption == 28)
			TextLoader.updateText(currentLetter, "Enter");
		else if (currentOption == 57)
			TextLoader.updateText(currentLetter, "Space");
		else if(currentOption == 1)
			TextLoader.updateText(currentLetter, "Esc");
		else if(currentOption == 2)
			TextLoader.updateText(currentLetter, "1");
		else if(currentOption == 3)
			TextLoader.updateText(currentLetter, "2");
		else if(currentOption == 4)
			TextLoader.updateText(currentLetter, "3");
		else if(currentOption == 5)
			TextLoader.updateText(currentLetter, "4");
		else if(currentOption == 6)
			TextLoader.updateText(currentLetter, "5");
		else if(currentOption == 7)
			TextLoader.updateText(currentLetter, "6");
		else if(currentOption == 8)
			TextLoader.updateText(currentLetter, "7");
		else if(currentOption == 9)
			TextLoader.updateText(currentLetter, "8");
		else if(currentOption == 10)
			TextLoader.updateText(currentLetter, "9");
		else if(currentOption == 11)
			TextLoader.updateText(currentLetter, "0");
		else if(currentOption == 12)
			TextLoader.updateText(currentLetter, "-");
		else if(currentOption == 13)
			TextLoader.updateText(currentLetter, "=");
		else if(currentOption >= 59 && currentOption <= 88)
			TextLoader.updateText(currentLetter, "None");
	}
	
	public void parseIntToStringLetters(Text currentLetter, int currentOption)
	{
		if(currentOption == 30)
			currentLetter.setText("A");
		else if(currentOption == 48)
			currentLetter.setText("B");
		else if(currentOption == 46)
			currentLetter.setText("C");
		else if(currentOption == 32)
			currentLetter.setText("D");
		else if(currentOption == 18)
			currentLetter.setText("E");
		else if(currentOption == 33)
			currentLetter.setText("F");
		else if(currentOption == 34)
			currentLetter.setText("G");
		else if(currentOption == 35)
			currentLetter.setText("H");
		else if(currentOption == 23)
			currentLetter.setText("I");
		else if(currentOption == 36)
			currentLetter.setText("J");
		else if(currentOption == 37)
			currentLetter.setText("K");
		else if(currentOption == 38)
			currentLetter.setText("L");
		else if(currentOption == 50)
			currentLetter.setText("M");
		else if(currentOption == 49)
			currentLetter.setText("N");
		else if(currentOption == 24)
			currentLetter.setText("O");
		else if(currentOption == 25)
			currentLetter.setText("P");
		else if(currentOption == 16)
			currentLetter.setText("Q");
		else if(currentOption == 19)
			currentLetter.setText("R");
		else if(currentOption == 31)
			currentLetter.setText("S");
		else if(currentOption == 20)
			currentLetter.setText("T");
		else if(currentOption == 22)
			currentLetter.setText("U");
		else if(currentOption == 47)
			currentLetter.setText("V");
		else if(currentOption == 17)
			currentLetter.setText("W");
		else if(currentOption == 45)
			currentLetter.setText("X");
		else if(currentOption == 21)
			currentLetter.setText("Y");
		else if(currentOption == 44)
			currentLetter.setText("Z");
		else if(currentOption == 15)
			currentLetter.setText("Tab");
		else if(currentOption == 58)
			currentLetter.setText("Caps");
		else if(currentOption == 42 || currentOption == 54)
			currentLetter.setText("Shift");
		else if(currentOption == 29 || currentOption == 157)
			currentLetter.setText("Ctrl");
		else if(currentOption == 56 || currentOption == 184)
			currentLetter.setText("Alt");
		else if(currentOption == 28)
			currentLetter.setText("Enter");
		else if(currentOption == 57)
			currentLetter.setText("Space");
		else if(currentOption == 1)
			currentLetter.setText("Esc");
		else if(currentOption == 2)
			currentLetter.setText("1");
		else if(currentOption == 3)
			currentLetter.setText("2");
		else if(currentOption == 4)
			currentLetter.setText("3");
		else if(currentOption == 5)
			currentLetter.setText("4");
		else if(currentOption == 6)
			currentLetter.setText("5");
		else if(currentOption == 7)
			currentLetter.setText("6");
		else if(currentOption == 8)
			currentLetter.setText("7");
		else if(currentOption == 9)
			currentLetter.setText("8");
		else if(currentOption == 10)
			currentLetter.setText("9");
		else if(currentOption == 11)
			currentLetter.setText("0");
		else if(currentOption == 12)
			currentLetter.setText("-");
		else if(currentOption == 13)
			currentLetter.setText("=");
		else if(currentOption >= 59 && currentOption <= 88)
			currentLetter.setText("None");
		
	}
	
	
	
	public void scrollLetters(float scrollY)
	{
		float x = 0.35f;
		w.setPosition(x, 0.1f + scrollY);
		a.setPosition(x, 0.2f + scrollY);
		s.setPosition(x, 0.3f + scrollY);
		d.setPosition(x, 0.4f + scrollY);
		space.setPosition(x, 0.51f + scrollY);
		shift.setPosition(x, 0.61f + scrollY);
		e.setPosition(x, 0.7f + scrollY);
		q.setPosition(x, 0.8f + scrollY);
		esc.setPosition(x, 0.91f + scrollY);
		caps.setPosition(x, 1.01f + scrollY);
		comboF3_1.setPosition(x, 1.11f + scrollY);
	}
	
	public void renderChatWords()
	{
		if(Pages.isControlsPage)
		{
			a.r(true); w.r(true); s.r(true); d.r(true); space.r(true); shift.r(true); e.r(true); q.r(true);
			esc.r(true); caps.r(true); comboF3_1.r(true);
		}
		else
		{
			a.r(false); w.r(false); s.r(false); d.r(false); space.r(false); shift.r(false); e.r(false); q.r(false);
			esc.r(false); caps.r(false); comboF3_1.r(false);
		}
		if(Pages.isControlsKeyChangePage)
		{
			currentOption.r(true);
		} else
		{
			currentOption.r(false);
		}
	}
}
