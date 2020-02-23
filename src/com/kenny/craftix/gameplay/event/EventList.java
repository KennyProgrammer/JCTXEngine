package com.kenny.craftix.gameplay.event;

import java.util.Arrays;

import com.kenny.craftix.client.gui.GuiBackground.Pages;

import static com.kenny.craftix.client.text.TextLanguage.*;

public class EventList 
{
	public static boolean eventPlayerDeadHealth;
	public static boolean eventPlayerDeadCommand;
	public static boolean eventPlayerDeadWater;
	public static boolean[] events = new boolean[] {eventPlayerDeadHealth, eventPlayerDeadHealth, eventPlayerDeadWater};
	
	public static void reloadEventDataList()
	{
		Arrays.fill(events, false);
		eventPlayerDeadHealth = false;
		eventPlayerDeadCommand = false;
		eventPlayerDeadWater = false;
	}
	
	/**
	 * Events happens with text string components. Called in update loop.
	 */
	public static void eventWithText()
	{
		if(Pages.isGameOverPage)
		{
			if(eventPlayerDeadCommand)
			{
				t104.r(true);
			} else {
				t104.r(false);
			} 
				
			if(eventPlayerDeadHealth)
			{
				t103.r(true);
			} else {
				t103.r(false);
			}
			
			if(eventPlayerDeadWater)
			{
				t105.r(true);
			} else {
				t105.r(false);
			}
			
		} else
		{
			t104.r(false); t103.r(false); t105.r(false);
		}
			 
	}
}
