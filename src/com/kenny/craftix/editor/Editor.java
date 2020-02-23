package com.kenny.craftix.editor;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.editor.gui.GuiEditor;
import com.kenny.craftix.editor.gui.GuiEditorBackground.Pages;
import com.kenny.craftix.gameplay.GameStatus;
import com.kenny.craftix.main.Side;
import com.kenny.craftix.main.SideMachine;

@SideMachine(Side.EDITOR)
public class Editor 
{
	/**The main game instance.*/
	private Craftix cx;
	/**Init all guis screens for editor.*/
	private GuiEditor guiEditor;
	public GameStatus status;
	public static boolean preInit = true;
	
	public Editor(Craftix craftixIn)
	{
		this.cx = craftixIn;
		this.guiEditor = new GuiEditor(this.cx);
		this.status = this.cx.status;
	}
	
	/**
	 * As with any scene, it initializes the editor's main components 
	 * for future use.
	 */
	public void initEditor(Craftix craftixIn)
	{
		this.status.setEditor(true);
	}
	
	/**
	 * Update editor matrices, and other components.
	 */
	public void updateEditorPre()
	{
		this.renderPages();
		this.guiEditor.getGuiSwichEditor().eventAfterClick();
		if(!preInit)
		{
			
		}
	}
	
	public void renderPages()
	{
		if(Pages.isSwitchPage)
			this.cx.guiRenderer.render(this.getGui().getGuiSwichEditor().textures);
	}
	
	/**
	 * Return the game engine main instance.
	 */
	public Craftix getCraftix()
	{
		return this.cx;
	}
	
	/**
	 * Return this simply gui's for editor.
	 */
	public GuiEditor getGui()
	{
		return this.guiEditor;
	}
}
