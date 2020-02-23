package com.kenny.craftix.server.socket;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.text.TextLoader;
import com.kenny.craftix.main.Side;
import com.kenny.craftix.main.SideMachine;

@SideMachine(Side.SERVER)
public class SocketRenderText 
{
	/**
	 * Render text one after update display. Used on server side.
	 */
	public void renderText(Craftix craftixIn)
	{
		craftixIn.textLang.renderEnigneText(craftixIn);
		TextLoader.render();
	}
}
