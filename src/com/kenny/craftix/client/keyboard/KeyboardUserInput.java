package com.kenny.craftix.client.keyboard;

import org.lwjgl.input.Keyboard;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.entity.EntityCamera;
import com.kenny.craftix.client.entity.GameObject;
import com.kenny.craftix.client.entity.player.EntityPlayer;
import com.kenny.craftix.client.gui.GuiControls;
import com.kenny.craftix.client.gui.GuiCreateWorld;
import com.kenny.craftix.client.gui.GuiCreditsMenu;
import com.kenny.craftix.client.gui.GuiDisconnected;
import com.kenny.craftix.client.gui.GuiGlOptions;
import com.kenny.craftix.client.gui.GuiGraphics;
import com.kenny.craftix.client.gui.GuiLanguage;
import com.kenny.craftix.client.gui.GuiLinkWarning;
import com.kenny.craftix.client.gui.GuiMainMenu;
import com.kenny.craftix.client.gui.GuiMultiplayer;
import com.kenny.craftix.client.gui.GuiNews;
import com.kenny.craftix.client.gui.GuiOptions;
import com.kenny.craftix.client.gui.GuiOtherOptions;
import com.kenny.craftix.client.gui.GuiProfile;
import com.kenny.craftix.client.gui.GuiSingleplayer;
import com.kenny.craftix.client.scenes.MainMenuScene;
import com.kenny.craftix.client.scenes.WorldScene;
import com.kenny.craftix.client.gui.GuiBackground.Pages;
import com.kenny.craftix.client.settings.InGameSettings;
import com.kenny.craftix.client.text.TextChat;
import com.kenny.craftix.client.text.TextLanguage;
import com.kenny.craftix.client.text.TextLoader;
import com.kenny.craftix.init.PostEffectsInit;
import com.kenny.craftix.world.InputingText;
import com.kenny.craftix.world.World;

public class KeyboardUserInput extends KeyboardManager
{
	/**
	 * This main Craftix Instance.
	 */
	protected static Craftix cx;
	/**Check if keyboard input hold in while loop.*/
	public boolean isKeyboardNext;
	public TextChat textChat = new TextChat();
	public boolean[] p = new boolean[] {Pages.isControlsPage, Pages.isOptionsPage, Pages.isGraphicsPage, Pages.isLanguagePage, Pages.isGlOptionsPage, Pages.isOtherOptionsPage}; 
	
	public KeyboardUserInput() 
	{
	}
	
	public void mainMenuKeysPreesed(Craftix craftixIn)
	{
		this.cx = craftixIn;
		MainMenuScene mms = craftixIn.menuScene;
		
		int i = Keyboard.getEventKey() == 0 ? Keyboard.getEventCharacter() + 256 : Keyboard.getEventKey();
		 
		if (i != 0 && !Keyboard.isRepeatEvent())
	    {
			if (Keyboard.getEventKeyState())
			{	
				this.downRenderPolygonsModel();
				this.downCleanUpPages();
				
				if(GuiMainMenu.hasntLicensePage && isEscKeyDown())
				{
					GuiMainMenu.hasntLicensePage = false;
					Pages.isMainMenuPage = true;
					GuiMainMenu.addPage();
				}
				
				if(isKeyComboCtrlV())
					this.cx.menuScene.playSound();
				
				if(keyDown(33))
					this.cx.setIngameNotInFocus();
				
				if(Pages.isLinkWarning)
				{
					if(isEscKeyDown())
					{
						GuiLinkWarning.removePage();
						Pages.isLinkWarning = false;
					}
				}
				
				if(Pages.isNewsPage)
				{
					if(keyDown(Keyboard.KEY_LEFT))
					{
						if(!Pages.isNews1)
						{
							Pages.isNews1 = true;
							Pages.isNews2 = false;
						}
					}
					
					if(keyDown(Keyboard.KEY_RIGHT))
					{
						if(!Pages.isNews2)
						{
							Pages.isNews2 = true;
							Pages.isNews1 = false;
						}
					}
					
					if(isEscKeyDown())
					{
						GuiNews.removePage();
						Pages.isNewsPage = false;
						Pages.isMainMenuPage = true;
						GuiMainMenu.addPage();
					}
				}
				
				if(Pages.isProfilePage)
				{
					if(isEscKeyDown())
					{
						GuiProfile.removePage();
						Pages.isProfilePage = false;
						Pages.isMainMenuPage = true;
						GuiMainMenu.addPage();
					}
					
				}
				
				if(Pages.isSingleplayerPage)
				{
					if(isEscKeyDown())
					{
						GuiSingleplayer.removePage();
						Pages.isSingleplayerPage = false;
						Pages.isMainMenuPage = true;
						GuiMainMenu.addPage();
					}
				}
				
				if(Pages.isMultiplayerPage)
				{
					if(isEscKeyDown())
					{
						GuiMultiplayer.removePage();
						Pages.isMultiplayerPage = false;
						Pages.isMainMenuPage = true;
						GuiMainMenu.addPage();
					}
				}
				
				if(mms.guiCredits.isLoaded())
				{
					if(isEscKeyDown())
					{
						GuiCreditsMenu.removePage();
						mms.guiCredits.setLoaded(false);
						Pages.isMainMenuPage = true;
						GuiMainMenu.addPage();
					}
				}
				
				if(Pages.isOptionsPage)
				{
					if(keyDown(205))
					{
						this.cx.inGameSettings.increaseFov(true);
						GuiOptions.sb1.increasePosition();
						TextLoader.updateText(TextLanguage.t44, "Fov: " + InGameSettings.fovIn);
					}
	
					if(keyDown(203))
					{
						this.cx.inGameSettings.increaseFov(false);
						GuiOptions.sb1.decreasePosition();
						TextLoader.updateText(TextLanguage.t44, "Fov: " + InGameSettings.fovIn);
					}
					
					if(isEscKeyDown())
					{
						GuiOptions.removePage();
						Pages.isOptionsPage = false;
						Pages.isMainMenuPage = true;
						GuiMainMenu.addPage();
						InGameSettings.saveOptions();
					}
				 }
				
				 if(Pages.isGlOptionsPage)
				 {
					 if(isEscKeyDown())
					 {
						 GuiOptions.addPage();
						 Pages.isOptionsPage = true;
						 Pages.isGlOptionsPage = false;
						 GuiGlOptions.removePage();
					 }
				 }
				 
				 if(Pages.isGraphicsPage)
				 {
					 if(isEscKeyDown() && !GuiGraphics.isMorePostProcessing)
					 {
						 GuiGraphics.removePage();
						 Pages.isGraphicsPage = false;
						 Pages.isOptionsPage = true;
						 GuiOptions.addPage();
					 } else {
						 GuiGraphics.addPage();
						 GuiGraphics.isMorePostProcessing = false;
						 GuiGraphics.buttonList.remove(GuiGraphics.b14);
						 GuiGraphics.buttonList.remove(GuiGraphics.b15);
						 GuiGraphics.buttonList.remove(GuiGraphics.b17);
					 }
				 }
				 
				 if(Pages.isOtherOptionsPage)
				 {
					 if(isEscKeyDown())
					 {
						 GuiOtherOptions.removePage();
						 Pages.isOtherOptionsPage = false;
						 Pages.isOptionsPage = true;
						 GuiOptions.addPage();
					 }
				 }
				 
				 if(Pages.isLanguagePage)
				 {
					 if(isEscKeyDown())
					 {
						GuiLanguage.removePage();
						Pages.isLanguagePage = false;
						if(Pages.fromLangToOptions)
						{
							Pages.isOptionsPage = true;
							Pages.fromLangToOptions = false;
							GuiOptions.addPage();
						} else {
							Pages.isMainMenuPage = true;
							GuiMainMenu.addPage();
						}
					}
				}
				 
				if(Pages.isControlsPage)
				{
					if(isEscKeyDown())
					{
						GuiControls.removePage();
						Pages.isControlsPage = false;
						Pages.isOptionsPage = true;
						GuiOptions.addPage();
					}
				}
				
				if(Pages.isCreateWorldPage)
				{
					InputingText iT = new InputingText();
					iT.printText();
					if(isEscKeyDown())
					{
						GuiCreateWorld.removePage();
						Pages.isCreateWorldPage = false;
						Pages.isSingleplayerPage = true;
						GuiSingleplayer.addPage();
					}
				}
				
				if(keyDown(33))
				{
					GuiMainMenu.removePage();
					Pages.isMainMenuPage = false;
					Pages.isDisconnectedPage = true;
					GuiDisconnected.addPage();
				}
				
				this.downChangeOptions(craftixIn);
            }
	    }
		this.isKeyboardNext = false;
	
	}
	
	public void gameplayKeysPressed(Craftix craftixIn)
	{
		this.cx = craftixIn;
		WorldScene world = craftixIn.getWorldScene();
		EntityPlayer player = world.getPlayer();
		
		this.downCleanUpWorld(world.getWorld());
		this.downReloadResourcePack(world.getWorld());
		this.downReloadWorldRenderer(world.getWorld());
		this.downShowGamePause(craftixIn);
		this.downChangeCameraPosition(craftixIn);
		//this.downChangeOptions(craftixIn);
		//this.downShowHelpPage(craftixIn);
		//this.downRenderPolygonsModel();
		//this.downTeleportPlayerOnY(craftixIn);
		//this.downReloadGameAssets(craftixIn);
				
	}
	
	public void gameplayKeysPressedNotWhile(Craftix craftixIn)
	{
		//this.downScrollControls();
		//this.downSpawnBloomEffect();
		//this.downSpawnParticles(craftixIn);
	}
	
	public void downCleanUpPages()
	{

	}
	
	public void downReloadResourcePack(World worldIn)
	{
		if(isAltKeyDown())
			worldIn.getTTPLoader().removeTerrainTexturePack();
	}
	
	/**
	 * Show the game pause page when user click esc on keyboard.
	 */
	public void downShowGamePause(Craftix craftixIn)
	{	
		/**
		if(craftixIn.getWorldScene().getPlayer().isPlayerAlive())
		{
			if(!craftixIn.status.isGamePause() && !p[0] && !p[1] && !p[2] && !p[3] && !p[4] && !p[5])
			{
				if(keyDown(InGameSettings.keyboardPauseIn))
				{
					craftixIn.status.setGamePause(true);
					Pages.isMainGamePausePage = true;
					GuiInGameMenu.addPage();
				}
			} 
			else if(craftixIn.status.isGamePause() && !p[0] && !p[1] && !p[2] && !p[3] && !p[4] && !p[5])
			{
				if(keyDown(InGameSettings.keyboardPauseIn))
				{
					craftixIn.status.setGamePause(false);
					Pages.isMainGamePausePage = false;
					GuiInGameMenu.removePage();
				}
			}
		}
		*/
		
		if(!craftixIn.status.isGamePause())
		{
			if(keyDown(InGameSettings.keyboardPauseIn))
			{
				craftixIn.status.setGamePause(true);
				
			}
		} 
		else if(craftixIn.status.isGamePause())
		{
			if(keyDown(InGameSettings.keyboardPauseIn))
			{
				craftixIn.status.setGamePause(false);
				
			}
		}
		
		
	}
	
	/**
	 * Show the game help page when user click combo F3 + 2 buttons on keyboard.
	 */
	public void downShowHelpPage(Craftix craftixIn)
	{
		if(isKeyComboF3(2))
		{
			Pages.isHelpPage = true;
		}
		else if(Pages.isHelpPage)
		{
			Pages.isHelpPage = false;
		}
		
		if(keyDown(11))
		{
			craftixIn.getWorldScene().getPlayer().setHunger(0f, 0f);
		}
	}
	
	/**
	 * Show the bloom effect when user click F3 on keyboard.
	 */
	public void downSpawnBloomEffect()
	{
		if(isF3KeyDown() && !isKeyComboF3(2))
		{
			PostEffectsInit.isRenderBloom = false;
		}
		
		if(keyDown(62) && !isKeyComboF3(2))
		{
			PostEffectsInit.isRenderBloom = true;
		}
		
	}
	
	/**
	 * On down the key F5 change the position of player camera.
	 */
	public void downChangeCameraPosition(Craftix craftixIn) 
	{
		EntityCamera camera = craftixIn.getWorldScene().getWorld().getPlayerCamera();
		
		if(!craftixIn.getWorldScene().gamePause) 
		{
			if(keyDown(63) && !isKeyComboF3(2) && EntityCamera.isFirstPersonCamera == true)
			{
				EntityCamera.isThridPersonCamera = true;
				EntityCamera.isThridPersonCameraFace = false;
				EntityCamera.isFirstPersonCamera = false;
				camera.setCameraPlayerSettings();
			}
			else if(keyDown(63) && !isKeyComboF3(2) && EntityCamera.isThridPersonCamera == true)
			{
				EntityCamera.isThridPersonCamera = false;
				EntityCamera.isThridPersonCameraFace = true;
				EntityCamera.isFirstPersonCamera = false;
				camera.setCameraPlayerSettings();
			}
			else if(keyDown(63) && !isKeyComboF3(2) && EntityCamera.isThridPersonCameraFace == true)
			{
				EntityCamera.isThridPersonCamera = false;
				EntityCamera.isThridPersonCameraFace = false;
				EntityCamera.isFirstPersonCamera = true;
				camera.setCameraPlayerSettings();
			}
		}
	}
	
	/**
	 * When user down on key numbers then spawn regular effects.
	 */
	public void downSpawnParticles(Craftix craftixIn)
	{
		if(keyDown(2) && !isKeyComboF3(2))
		{
			craftixIn.getWorldScene().particleInit.p_Smoke.generateParticles(craftixIn.getWorldScene().getPlayer().getPositionVector());
		}
		
		if(keyDown(3) && !isKeyComboF3(2))
		{
			craftixIn.getWorldScene().particleInit.p_Fire.generateParticles(craftixIn.getWorldScene().getPlayer().getPositionVector());
		}
		
		if(keyDown(4) && !isKeyComboF3(2))
		{
			craftixIn.getWorldScene().particleInit.p_Star.generateParticles(craftixIn.getWorldScene().getPlayer().getPositionVector());
		}
	
		if(keyDown(5) && !isKeyComboF3(2))
		{
			craftixIn.getWorldScene().particleInit.p_Cosmic.generateParticles(craftixIn.getWorldScene().getPlayer().getPositionVector());
		}
	}
	
	public void downRenderPolygonsModel()
	{
		if(Keyboard.isKeyDown(59) && !InGameSettings.usePoligonModeIn)
		{
			 InGameSettings.usePoligonModeIn = true;
			 InGameSettings.enableTriangleMode();	  
		}
		else if(Keyboard.isKeyDown(59) && InGameSettings.usePoligonModeIn) 
		{
			 InGameSettings.usePoligonModeIn = false;
			 InGameSettings.disableTriangleMode();
		}
	}

	public void downScrollControls()
	{
		if(Pages.isControlsPage)
		{	
			if(keyDown(208))
			{
				if(cx.menuScene.guiControls.scrollY >= 0.0f)
					cx.menuScene.guiControls.scrollY = 0.0f;
				else
					cx.menuScene.guiControls.scrollY += 0.01f;
				
				if(cx.menuScene.guiControls.scrollbY >= 0.0f)
					cx.menuScene.guiControls.scrollbY = 0.0f;
				else
					cx.menuScene.guiControls.scrollbY += 0.02f;
				
				cx.menuScene.guiControls.scrollScreen();
			}
			if(keyDown(200))
			{
				if(cx.menuScene.guiControls.scrollY <= -0.2f)
					cx.menuScene.guiControls.scrollY = -0.2f;
				else
					cx.menuScene.guiControls.scrollY -= 0.01f;
				
				if(cx.menuScene.guiControls.scrollbY <= -0.4f)
					cx.menuScene.guiControls.scrollbY = -0.4f;
				else
					cx.menuScene.guiControls.scrollbY -= 0.02f;
				
				cx.menuScene.guiControls.scrollScreen();
			}

		}
	}
	
	public void downCleanUpWorld(World worldIn)
	{
		if(isKeyComboF3(11))
			worldIn.saveAndCleanUpWorld();
	}
	
	public void downReloadWorldRenderer(World worldIn)
	{
		if(isSpaceKeyDown())
		{
			worldIn.reloadWorld();
		}
	}
	
	public void downReloadGameAssets(Craftix craftixIn)
	{
		if(keyDown(66))
		{
			craftixIn.LOGGER.info("Starting reaload resources...");
			for(GameObject object : craftixIn.getWorldScene().objects)
			{
				object.reloadGameObjectAssets(object.getObjectName());
			}
			craftixIn.LOGGER.info("Resource pack success reload!");
		}
	}
	
	public void downChangeOptions(Craftix craftixIn)
	{
		if(Pages.isControlsKeyChangePage)
		{
			if(GuiControls.keyForward)
			{
				craftixIn.menuScene.guiControlsChange.changeOptionForward(craftixIn.menuScene.textChat.currentOption);
				this.textChat.parseUpdateIntToStringLetters(craftixIn.menuScene.textChat.w, InGameSettings.keyboardMovementForwardIn);
			}
			if(GuiControls.keyBackward)
			{
				craftixIn.menuScene.guiControlsChange.changeOptionBackward(craftixIn.menuScene.textChat.currentOption);
				this.textChat.parseUpdateIntToStringLetters(craftixIn.menuScene.textChat.a, InGameSettings.keyboardMovementBackwardIn);
			}
			if(GuiControls.keyLeftward)
			{
				craftixIn.menuScene.guiControlsChange.changeOptionLeftward(craftixIn.menuScene.textChat.currentOption);
				this.textChat.parseUpdateIntToStringLetters(craftixIn.menuScene.textChat.s, InGameSettings.keyboardMovementLeftwardIn);
			}
			if(GuiControls.keyRightward)
			{
				craftixIn.menuScene.guiControlsChange.changeOptionRightward(craftixIn.menuScene.textChat.currentOption);
				this.textChat.parseUpdateIntToStringLetters(craftixIn.menuScene.textChat.d, InGameSettings.keyboardMovementRightwardIn);
			}
			if(GuiControls.keyJump)
			{
				craftixIn.menuScene.guiControlsChange.changeOptionJump(craftixIn.menuScene.textChat.currentOption);
				this.textChat.parseUpdateIntToStringLetters(craftixIn.menuScene.textChat.space, InGameSettings.keyboradMovementJumpIn);
			}
			if(GuiControls.keyRun)
			{
				craftixIn.menuScene.guiControlsChange.changeOptionRun(craftixIn.menuScene.textChat.currentOption);
				this.textChat.parseUpdateIntToStringLetters(craftixIn.menuScene.textChat.shift, InGameSettings.keyboradMovementRunIn);
			}
			if(GuiControls.keyPause)
			{
				craftixIn.menuScene.guiControlsChange.changeOptionPause(craftixIn.menuScene.textChat.currentOption);
				this.textChat.parseUpdateIntToStringLetters(craftixIn.menuScene.textChat.esc, InGameSettings.keyboardPauseIn);
			}
		}
	}
	
	public void downTeleportPlayerOnY(Craftix craftixIn)
	{
		EntityPlayer player = craftixIn.getWorldScene().getPlayer();
		if(keyDown(Keyboard.KEY_T))
			craftixIn.getWorldScene().getTheCommand().commandTp.runCommand(craftixIn.getWorldScene(), player.getPositionVector().x, player.getPositionVector().y + 100F, player.getPositionVector().z);
	}
	
	public void mainMenuKeysPreesedNotWhile(Craftix craftix)
	{
		this.cx = craftix;
		
		if(!this.isKeyboardNext)
		{
			if(!this.isKeyboardNext)
			{
				
				//if(Keyboard.isKeyDown(77) || Keyboard.isKeyDown(32))
				//{this.cx.menu.entityInit.profilePlayer.increaseRotation(0, 1.5f, 0);}
				
				//if(Keyboard.isKeyDown(75) || Keyboard.isKeyDown(30))
				//{this.cx.menu.entityInit.profilePlayer.increaseRotation(0, -1.5f, 0);}
				
				this.downScrollControls();
			}
			
		}
	}
}
