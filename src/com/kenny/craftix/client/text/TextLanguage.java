package com.kenny.craftix.client.text;

import com.kenny.craftix.client.renderer.GlHelperError;
import com.kenny.craftix.client.scenes.MainMenuScene;
import com.kenny.craftix.client.scenes.WorldScene;
import com.kenny.craftix.client.settings.InGameSettings;
import com.kenny.craftix.gameplay.event.EventList;
import com.kenny.craftix.server.gui.GuiServerLog;
import com.kenny.craftix.utils.Timer;
import com.kenny.craftix.world.SaveSlotLoader;

import static com.kenny.craftix.client.language.Language.*;
import static com.kenny.craftix.client.text.TextLoader.*;

import org.lwjgl.util.vector.Vector3f;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.entity.player.EntityPlayer;
import com.kenny.craftix.client.gui.GuiLoadingSplash;
import com.kenny.craftix.client.gui.GuiMainMenu;
import com.kenny.craftix.client.gui.GuiBackground.Pages;
import com.kenny.craftix.client.gui.GuiGraphics;

public class TextLanguage 
{
	public static Text t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,t12,t13,t14,t15,t16,t17,t18,t19,t19b,t20,t21,t22,t23,t24,t25,t26,t27,t28,t29,t30,t31,t32,t33,t34,t35,t36,t37,t38,t39,t40,t41,t42,t43,t44,t45,t46,t47,t48,t49,t50, t51,t52,t53,t54,t55,t56,t57,t57b,t58,t59,t60,t61,t62,t63,t64,t65,t66,t67,t68,t69,t70,t71,t72,t73,t74,t75,t76,t77,t78,t79,t80,t81,t82,t83,t84,t85,t86,t87,t88,t89,t90,t91,t92,t93,t94,t95,t96,t97,t98,t99,t100,
		t101,t102,t103,t104,t105,t106,t107,t108,t109,t109b,t110,t111,t112,t113,t114,t115,t116,t117,t118,t119,t120,t120b,t121,t122,t123,t124,t125,t126,t127,t128,t129,t130,t131,t132,t133,t134,t135,t136,t137,t138,t139,t140,t141,t142,t143,t144,t145,t146,t147,t148,t149,t150;
	public static Text c1, c2, c3, c4, c5, c6, c7, c8;
	public static Text tg1, tg5, tg14, tg18;
	public static Text tt1, tt2, tt3, tt4, tt5;
	public int languageId;
	public Timer timer = new Timer();
	protected GuiServerLog guiServer = new GuiServerLog();

	public void loadEnigneText(Craftix cx)
	{
		tg1 = new Text("gui.back", 0.545f, 0.835f, true);
		tg5 = new Text("gui.continue", 0f, 0f, true);
		tg14 = new Text("gui.save", 0f, 0f, true);
		tg18 = new Text("40523 lines of code!", 0.3f, 0.27f, true);
		
		t1 = new Text("mainmenu.singleplayer", 0.202f, 0.476f, true);
		t2 = new Text("mainmenu.multiplayer", 0.202f, 0.576f, true);
		t3 = new Text("mainmenu.options", 0.202f, 0.676f, true);
		t4 = new Text("mainmenu.credits", 0.202f, 0.776f, true);
		t5 = new Text("mainmenu.exit", 0.202f, 0.876f, true);
		t6 = new Text("mainmenu.news", -0.202f, 0.676f, true);
		t7 = new Text("mainmenu.profile", -0.202f, 0.776f, true);
		t8 = new Text("mainmenu.website", 0.602f, 0.676f, true);
		t9 = new Text("mainmenu.editor", 0.602f, 0.776f, true);
		t10 = new Text("mainmenu.version", 0.010f, 0.940f, false);
		t11 = new Text("mainmenu.company", 0.0f+cX, 0.0f+cY, false);
		t12 = new Text("mainmenu.fps", 0.0f, 0.0f, false);
		t13 = new Text("profile.title", 0.05f, 0.15f, false);
		t14 = new Text("profile.info.name", 0.05f, 0.25f, false);
		t15 = new Text("profile.info.version", 0.05f, 0.30f, false);
		t16 = new Text("profile.info.versiontype", 0.05f, 0.35f, false);
		t17 = new Text("profile.info.username", 0.05f, 0.40f, false);
		t18 = new Text("profile.info.userid", 0.05f, 0.45f, false);
		t19 = new Text("profile.info.sessionid", 0.05f, 0.50f, false);
		t19b = new Text("profile.info.hasLicense", 0.05f, 0.55f, false);
		t20 = new Text("profile.myplayer", 0.5f, 0.15f, true);
		t21 = new Text("news.title", 0.05f, 0.15f, false);
		t22 = new Text("news.version14", 0.05f, 0.25f, false);
		t23 = new Text("news.desc14", 0.05f, 0.35f, false);
		t24 = new Text("news.version17", 0.05f, 0.25f, false);
		t25 = new Text("news.desc17", 0.05f, 0.35f, false);
		t26 = new Text("url.title", 0.75f, 0.30f, false);
		t27 = new Text("url.continue", 0.615f, 0.475f, true);
		t28 = new Text("singleplayer.generateWorld", -0.100f, 0.33f, true);
		t29 = new Text("singleplayer.generateLowpolyWorld", -0.100f, 0.44f, true);
		t30 = new Text("singleplayer.generateFlatWorld", -0.100f, 0.55f, true);
		t31 = new Text("singleplayer.generateEmptyWorld", -0.100f, 0.66f, true);
		t32 = new Text("singleplayer.loadWorld", -0.100f, 0.83f, true);
		t33 = new Text("quit.desc", 0.202f, 0.386f, true);
		t34 = new Text("quit.return", 0.102f, 0.650f, true);
		t35 = new Text("quit.quitgame", 0.302f, 0.650f, true);
		t36 = new Text("credits.desc", 0.20f, 0.30f, false);
		t37 = new Text("options.title", 0.196f, 0.15f, true);
		t38 = new Text("options.graphics", 0.052f, 0.325f, true);
		t39 = new Text("options.controls", 0.356f, 0.325f, true);
		t40 = new Text("options.audio", 0.052f, 0.425f, true);
		t41 = new Text("options.language", 0.356f, 0.425f, true);
		t42 = new Text("options.other", 0.052f, 0.525f, true);
		t43 = new Text("options.openglSettings", 0.356f, 0.525f, true);
		t44 = new Text("options.option.fov", 0.200f, 0.625f, true);
		t45 = new Text("options.saveChangesMessage", 0.20f, 0.850f, true);
		t46 = new Text("options.graphics.title", 0.45f, 0.05f, false);
		t47 = new Text("options.graphics.option.fullscreen", 0.05f, 0.225f, true);
		t48 = new Text("options.graphics.option.postEffects", 0.35f, 0.225f, true);
		t49 = new Text("options.graphics.option.guiScale", 0.05f, 0.325f, true);
		t50 = new Text("options.graphics.option.fog", 0.35f, 0.325f, true);
		t51 = new Text("options.graphics.option.renderDistance", 0.05f, 0.425f, true);
		t52 = new Text("options.graphics.option.renderSkybox", 0.35f, 0.425f, true);
		t53 = new Text("options.graphics.option.renderWater", 0.05f, 0.525f, true);
		t54 = new Text("options.graphics.option.shadows", 0.35f, 0.525f, true);
		t55 = new Text("options.graphics.option.maxFps", 0.05f, 0.625f, true);
		t56 = new Text("options.graphics.option.vSync", 0.35f, 0.625f, true);
		t57 = new Text("options.graphics.option.particles", 0.05f, 0.725f, true);
		t57b = new Text("options.graphics.option.brightness", 0.35f, 0.725f, true);
		t58 = new Text("options.openglSettings.title", 0.05f, 0.15f, false);
		t59 = new Text("options.openglSettings.desc", 0.20f, 0.25f, true);
		t60 = new Text("options.openglSettings.arbVbo", 0.05f, 0.40f, false);
		t61 = new Text("options.openglSettings.vbo", 0.05f, 0.45f, false);
		t62 = new Text("options.openglSettings.multitexture", 0.05f, 0.50f, false);
		t63 = new Text("options.openglSettings.textureCombine", 0.05f, 0.55f, false);
		t64 = new Text("options.openglSettings.shaders", 0.05f, 0.60f, false);
		t65 = new Text("options.openglSettings.framebuffer", 0.05f, 0.65f, false);
		t66 = new Text("options.openglSettings.openGl14", 0.05f, 0.70f, false);
		t67 = new Text("options.openglSettings.openGl21", 0.05f, 0.75f, false);
		t68 = new Text("options.language.title", 0.05f, 0.15f, false);
		t69 = new Text("options.language.desc", 0.2f, 0.25f, true);
		t70 = new Text("options.language.lang.en", 0.20f, 0.43f, true);
		t71 = new Text("options.language.lang.ru", 0.20f, 0.53f, true);
		t72 = new Text("options.other.title", 0.196f, 0.05f, true);
		t73 = new Text("options.other.fpsTabOnMenu", 0.05f, 0.225f, true);
		t74 = new Text("options.other.renderCursor", 0.35f, 0.225f, true);
		t75 = new Text("options.controls.title", 0.15f, 0.05f, false);
		t76 = new Text("options.controls.control.forward", 0.1f, 0.1f, false);
		t77 = new Text("options.controls.control.backward", 0.1f, 0.2f, false);
		t78 = new Text("options.controls.control.leftward", 0.1f, 0.3f, false);
		t79 = new Text("options.controls.control.rightward", 0.1f, 0.4f, false);
		t80 = new Text("options.controls.control.jump", 0.1f, 0.5f, false);
		t81 = new Text("options.controls.control.run", 0.1f, 0.6f, false);
		t82 = new Text("options.controls.control.inventory", 0.1f, 0.7f, false);
		t83 = new Text("options.controls.control.pickup", 0.1f, 0.8f, false);
		t84 = new Text("options.controls.control.pause", 0.1f, 0.9f, false);
		t85 = new Text("options.controls.control.playerstab", 0.1f, 1.0f, false);
		t86 = new Text("options.controls.control.infopage", 0.1f, 1.1f, false);
		t87 = new Text("loadingWorld.title", 0.2f, 0.45f, true);
		t88 = new Text("loadingWorld.buildingTerrain", 0.2f, 0.55f, true);
		t89 = new Text("loadingWorld.generateTerrain", 0.2f, 0.55f, true);
		t90 = new Text("loadingWorld.processingEntity", 0.2f, 0.55f, true);
		t91 = new Text("gamePause.title", -0.09f, 0.115f, true);
		t92 = new Text("gamePause.resume", 0.05f, 0.279f, true);
		t93 = new Text("gamePause.options", 0.05f, 0.379f, true);
		t94 = new Text("gamePause.saveWorld", 0.05f, 0.479f, true);
		t95 = new Text("gamePause.help", 0.05f, 0.579f, true);
		t96 = new Text("gamePause.backToMenu", 0.05f, 0.679f, true);
		t97 = new Text("gamePause.saveAndExit", 0.05f, 0.779f, true);
		t98 = new Text("options.controls.selectControl.desc", 0.2f, 0.2f, true);
		t99 = new Text("loadingWorld.savingWorld", 0.2f, 0.45f, true);
		t100 = new Text("gameOver.title", 0.20f, 0.3f, true);
		t101 = new Text("gameOver.respawn", 0.325f, 0.68f, true);
		t102 = new Text("gameOver.backToMenu", 0.075f, 0.68f, true);
		t103 = new Text("gameOver.reason.health", 0.20f, 0.5f, true);
		t104 = new Text("gameOver.reason.command", 0.20f, 0.5f, true);
		t105 = new Text("gameOver.reason.water", 0.20f, 0.5f, true);
		t106 = new Text("multiplayer.title", -0.1f, 0.2f, true);
		t107 = new Text("multiplayer.serverVersion", 0.010f, 0.940f, false);
		t108 = new Text("multiplayer.createServer", -0.1f, 0.44f, true);
		t109 = new Text("multiplayer.connectToServer", -0.1f, 0.55f, true);
		t109b = new Text("connecting.title", 0.2f, 0.45f, true);
		t110 = new Text("disconnected.title", 0.195f, 0.25f, true);
		t111 = new Text("disconnected.backToMenu", 0.1f, 0.675f, true);
		t112 = new Text("disconnected.quit", 0.3f, 0.675f, true);
		t113 = new Text("disconnected.clientUnknownIp", 0.2f, 0.40f, true);
		t114 = new Text("disconnected.serverUnknownIp", 0.2f, 0.40f, true);
		t115 = new Text("connecting.checkIp", 0.2f, 0.55f, true);
		t116 = new Text("connecting.server.waitClient", 0.2f, 0.55f, true);
		t117 = new Text("connectitg.client.joinToServer", 0.2f, 0.55f, true);
		t118 = new Text("createWorld.title", 0.195f, 0.05f, true);
		t119 = new Text("createWorld.create", 0.3f, 0.825f, true);
		t120 = new Text("createWorld.worldName", 0.2f, 0.2f, true);
		t120b = new Text("createWorld.worldName_user", 0.2f, 0.325f, true);
		t121 = new Text("createWorld.gamemode", 0.05f, 0.53f, true);
		t122 = new Text("createWorld.worldType", 0.35f, 0.53f, true);
		t123 = new Text("createWorld.worldSeed", 0.05f, 0.63f, true);
		t124 = new Text("createWorld.otherSettings", 0.35f, 0.63f, true);
		t125 = new Text("createWorld.saveAs", 0.38f, 0.39f, false);
		t126 = new Text("editor.switchEditor", 0.2f, 0.4f, true);
		t127 = new Text("mainmenu.hasntLicense", 0.0f, 0.0f, true);
		t128 = new Text("options.graphics.option.postEffects.more", 0.2f, 0.15f, true);
		t129 = new Text("options.graphics.option.postEffects.more.contrast", 0.2f, 0.30f, true);
		t130 = new Text("options.graphics.option.postEffects.more.brightFilter", 0.2f, 0.40f, true);
		t131 = new Text("options.graphics.option.postEffects.more.gaussianBlur", 0.2f, 0.50f, true);
		
		tt1 = new Text("helpPage.playerCoords", 0.0f, 0.0f, false);
		tt2 = new Text("helpPage.gameTime", 0.0f, 0.05f, false);
		tt3 = new Text("helpPage.timeString", 0.0f, 0.1f, false);
		tt4 = new Text("helpPage.playerAABB", 0.0f, 0.15f, false);
		tt5 = new Text("helpPage.worldName", 0.0f, 0.2f, false);
		
		c1 = new Text("chat.fKeysInfo.keyf1", 0f, 0f, false);
		c2 = new Text("chat.fKeysInfo.keyf2", 0f, 0f, false);
		c5 = new Text("chat.fKeysInfo.keyf5", 0f, 0f, false);
		c8 = new Text("chat.fKeysInfo.keyf8", 0f, 0f, false);
		
		t125.setFontSize(1f);
		this.initTextCode(cx);
		SaveSlotLoader.loadTextWorldData();
		TextData.loadPreTextData();
	}
	
	public void renderEnigneText(Craftix craftixIn)
	{
		Vector3f playerPos = null;
		MainMenuScene mms = craftixIn.menuScene;
		
		if(craftixIn.getWorldScene().playerIn)
		{
			WorldScene wc = craftixIn.getWorldScene();
			EntityPlayer p = wc.getPlayer();
			playerPos = new Vector3f(0F, 0F, 0F);
		}
		
		
		if(Pages.isMainMenuPage)
		{
			t1.r(true); t2.r(true); t3.r(true); t4.r(true); t5.r(true); t6.r(true); t7.r(true);
			t8.r(true); t9.r(true); t10.r(true); t11.r(true); tg18.r(true);
			if(InGameSettings.fpsTabOnMenuIn)
			{t12.r(true); TextLoader.updateText(t12, "Fps: " + this.timer.lastFrameTime);}
		} else {
			t1.r(false); t2.r(false); t3.r(false); t4.r(false); t5.r(false); t6.r(false); t7.r(false);
			t8.r(false); t9.r(false); t10.r(false); t11.r(false); tg18.r(false);
			if(InGameSettings.fpsTabOnMenuIn)
			{t12.r(false);}
		}

		t127.setPosition(0.2F, 0.5F);
			
		if(GuiMainMenu.hasntLicensePage)
			t127.r(true);
		else
			t127.r(false);
		if(Pages.isProfilePage)
		{
			t13.r(true); t14.r(true); t15.r(true); t16.r(true); t17.r(true); t18.r(true); t19.r(true);
			t20.r(true); t19b.r(true); tg1.r(true);
		} else {
			t13.r(false); t14.r(false); t15.r(false); t16.r(false); t17.r(false); t18.r(false); t19.r(false);
			t20.r(false); t19b.r(false);  tg1.r(false);
		}
		if(Pages.isNewsPage)
		{
			tg1.r(true); t21.r(true);
			if(Pages.isNews1)
			{
				t22.r(true); t23.r(true);
			} else {
				t22.r(false); t23.r(false);
			}
			if(Pages.isNews2)
			{
				t24.r(true); t25.r(true);
			} else {
				t24.r(false); t25.r(false);
			}
		} else {
			t21.r(false); t22.r(false); t23.r(false); t24.r(false); t25.r(false);
		}
		if(Pages.isLinkWarning)
		{
			if(!Pages.isProfilePage || !Pages.isNewsPage || !Pages.isSingleplayerPage || !Pages.isOptionsPage
					|| !mms.guiCredits.isLoaded() || !Pages.isLanguagePage || !Pages.isQuitPage)
			{
				tg1.r(true); tg1.setPosition(0.455f, 0.475f); t26.r(true); t27.r(true);
			}
		} else {
			t26.r(false); t27.r(false); tg1.setPosition(0.545f, 0.835f);
		}
		if(Pages.isSingleplayerPage)
		{
			t28.r(true); t29.r(true); t30.r(true); t31.r(true); t32.r(true); t32.setPosition(-0.100f, 0.83f); tg1.r(true);
		} else {
			t28.r(false); t29.r(false); t30.r(false); t31.r(false); t32.r(false);
		}
		if(Pages.isMultiplayerPage)
		{
			t106.r(true); t107.r(true); t108.r(true); t109.r(true);  tg1.r(true);
		} else {
			t106.r(false); t107.r(false); t108.r(false); t109.r(false);
		}
		if(Pages.isConnectingPage)
		{
			t109b.r(true);
		} else {
			t109b.r(false);
		}
		if(Pages.isDisconnectedPage)
		{
			t110.r(true); t111.r(true); t112.r(true); 
		} else {
			 t110.r(false); t111.r(false); t112.r(false);
		}
		if(Pages.isCreateWorldPage)
		{
			t118.r(true); t119.r(true); t120.r(true); t121.r(true); t122.r(true); tg1.r(true); tg1.setPosition(0.1f, 0.825f);
			t123.r(true); t124.r(true); t120b.r(true); t125.r(true);
		} else {
			t118.r(false); t119.r(false); t120.r(false); t121.r(false); t122.r(false);
			t123.r(false); t124.r(false); t120b.r(false); t125.r(false);
		}
		if(Pages.isLoadWorldPage)
		{
			tg1.r(true); tg1.setPosition(-0.148f, 0.830f); t32.r(true); t32.setPosition(-0.15f, 0.115f);
		} else {
			
		}
		if(Pages.isQuitPage)
		{
			t33.r(true); t34.r(true); t35.r(true);
		} else 
		{
			t33.r(false); t34.r(false); t35.r(false);
		}
		if(mms.guiCredits.isLoaded())
		{
			tg1.r(true); t36.r(true);
		} else
		{
			t36.r(false);
		}
		if(Pages.isOptionsPage)
		{
			t37.r(true); t38.r(true); t39.r(true); t40.r(true); t41.r(true); t42.r(true); t43.r(true); t44.r(true);
			tg1.r(true);
		} else {
			t37.r(false); t38.r(false); t39.r(false); t40.r(false); t41.r(false); t42.r(false); t43.r(false); t44.r(false);
		}
		if(Pages.isGraphicsPage)
		{
			t46.r(true);
			
			if(!GuiGraphics.isMorePostProcessing)
			{
			    t47.r(true); t48.r(true); t49.r(true); t50.r(true); t51.r(true); t52.r(true);
				t53.r(true); t54.r(true); t55.r(true); t56.r(true); t57.r(true);t57b.r(true);  tg1.r(true);
				t128.r(false); t129.r(false); t130.r(false); t131.r(false);
			} else {
				t128.r(true); t129.r(true); t130.r(true); t131.r(true); t47.r(false); t48.r(false); t49.r(false); t50.r(false); t51.r(false); t52.r(false);
				t53.r(false); t54.r(false); t55.r(false); t56.r(false); t57.r(false); t57b.r(false);
			}
		} else {
			t46.r(false); t47.r(false); t48.r(false); t49.r(false); t50.r(false); t51.r(false); t52.r(false); t57b.r(false);
			t53.r(false); t54.r(false); t55.r(false); t56.r(false); t57.r(false); t128.r(false); t129.r(false); t130.r(false); t131.r(false);
		}
		if(Pages.isGlOptionsPage)
		{
			t58.r(true); t59.r(true); t60.r(true); t61.r(true); t62.r(true); t63.r(true); t64.r(true); t65.r(true); 
			t66.r(true); t67.r(true); tg1.r(true);
		} else {
			t58.r(false); t59.r(false); t60.r(false); t61.r(false); t62.r(false); t63.r(false); t64.r(false); t65.r(false); 
			t66.r(false); t67.r(false);
		}
		if(Pages.isLanguagePage)
		{
			t68.r(true); t69.r(true); t70.r(true); t71.r(true); tg1.r(true);
		} else {
			t68.r(false); t69.r(false); t70.r(false); t71.r(false);
		}
		if(Pages.isOtherOptionsPage)
		{
			t72.r(true); t73.r(true); t74.r(true); tg1.r(true);
		} else {
			t72.r(false); t73.r(false); t74.r(false);
		}
		if(Pages.isControlsPage)
		{
			t75.r(true); t76.r(true); t77.r(true); t78.r(true); t79.r(true); t80.r(true); t81.r(true); tg1.r(true);
			t82.r(true); t83.r(true); t84.r(true); t85.r(true); t86.r(true);
		} else {
			t75.r(false); t76.r(false); t77.r(false); t78.r(false); t79.r(false); t80.r(false); t81.r(false);
			t82.r(false); t83.r(false); t84.r(false); t85.r(false); t86.r(false);
		}
		if(Pages.isControlsKeyChangePage)
		{
			tg1.r(true); tg14.r(true); t75.r(true); t98.r(true); tg1.setPosition(0.2f, 0.835f); tg14.setPosition(0.2f, 0.735f);
		} else {
			t98.r(false); tg14.r(false);
		}
		if(Pages.isLoadingWorldPage)
		{
			t87.r(true); 
			if(GuiLoadingSplash.progress25)
				t88.r(true);
			else
				t88.r(false);
			if(GuiLoadingSplash.progress50)
				t89.r(true);
			else
				t89.r(false);
			if(GuiLoadingSplash.progress75)
				t90.r(true);
			else
				t90.r(false);
		} else {
			t87.r(false); t88.r(false); t89.r(false); t90.r(false);
		}
		if(Pages.isMainGamePausePage)
		{
			t91.r(true); t92.r(true); t93.r(true); t94.r(true); t95.r(true); t96.r(true); t97.r(true);
		} else {
			t91.r(false); t92.r(false); t93.r(false); t94.r(false); t95.r(false); t96.r(false); t97.r(false);
		}
		if(Pages.isHelpPage && ! Pages.isMainGamePausePage)
		{
			tt1.r(true); tt2.r(true);  tt3.r(true); tt4.r(true); tt5.r(true);
			TextLoader.updateText(tt1, "Player Coordintes: " + "x: " + (int) playerPos.x + ", y: " + (int) playerPos.y + ", z: " + (int) playerPos.z);
			TextLoader.updateText(tt2, "Game Time: " + (int) craftixIn.getWorldScene().worldTime.getGameTime() / 240);
			TextLoader.updateText(tt3, "Time Type: " + craftixIn.getWorldScene().worldTime.parseTimeToString());
			TextLoader.updateText(tt4, "Player AABB: " + craftixIn.getWorldScene().aabbPlayer.maxX + ", " + craftixIn.getWorldScene().aabbPlayer.maxY + ", " + craftixIn.getWorldScene().aabbPlayer.maxZ);
			TextLoader.updateText(tt5, "World Name: " + craftixIn.getWorldScene().getWorld().worldName);
		} else {
			tt1.r(false); tt2.r(false); tt3.r(false); tt4.r(false); tt5.r(false);
		}
		if(Pages.isGameOverPage)
		{
			t100.r(true); t101.r(true); t102.r(true); 
		} else {
			t100.r(false); t101.r(false); t102.r(false);
		}
		
		if(Pages.isSaveWorldPage)
		{
			t99.r(true);
		} else {
			t99.r(false);
		}
		
		if(!Pages.isGraphicsPage)
		{
			t45.r(false);
		}
		
		if(com.kenny.craftix.editor.gui.GuiEditorBackground.Pages.isSwitchPage)
		{
			t126.r(true); tg1.r(true); tg1.setPosition(0.3f, 0.675f); tg5.r(true); tg5.setPosition(0.1f, 0.675f);
		} else {
			t126.r(false); tg5.r(false);
		}
		
		SaveSlotLoader.renderText();
		this.guiServer.updateServerLog();
		EventList.eventWithText();
		this.setTextScale();
				
	}
	
	public void setTextScale()
	{
		if(InGameSettings.guiScaleIn == 2 && !Pages.isLinkWarning)
			tg1.setPosition(0.545f, 0.842f);
		else if(InGameSettings.guiScaleIn == 2 && Pages.isLinkWarning)
			tg1.setPosition(0.455f, 0.480f);
		
		if(InGameSettings.guiScaleIn == 1 && !Pages.isLinkWarning)
			tg1.setPosition(0.545f, 0.840f);
		else if(InGameSettings.guiScaleIn == 1 && Pages.isLinkWarning)
			tg1.setPosition(0.455f, 0.480f);
	}
	
	public void initTextCode(Craftix cx)
	{
		MainMenuScene menu = cx.menuScene;
		
		if(tg1.getText() == "gui.back")
			tg1.setText(GUI_BACK);
		if(tg5.getText() == "gui.continue")
			tg5.setText(GUI_CONTINUE);
		if(tg14.getText() == "gui.save")
			tg14.setText(GUI_SAVE);
		if(t1.getText() == "mainmenu.singleplayer")
			t1.setText(MAINMENU_SINGLEPLAYER_TITLE);
		if(t2.getText() == "mainmenu.multiplayer")
			t2.setText(MAINMENU_MULTIPLAYER_TITLE);
		if(t3.getText() == "mainmenu.options")
			t3.setText(MAINMENU_OPTIONS_TITLE);
		if(t4.getText() == "mainmenu.credits")
			t4.setText(MAINMENU_CREDITS_TITLE);
		if(t5.getText() == "mainmenu.exit")
			t5.setText(MAINMENU_QUIT_TITLE);
		if(t6.getText() == "mainmenu.news")
			t6.setText(MAINMENU_NEWS_TITLE);
		if(t7.getText() == "mainmenu.profile")
			t7.setText(PROFILE_TITLE);
		if(t8.getText() == "mainmenu.website")
			t8.setText(MAINMENU_WEBSITE_TITLE);
		if(t9.getText() == "mainmenu.editor")
			t9.setText(MAINMENU_EDITOR);
		if(t10.getText() == "mainmenu.version")
			t10.setText(CLIENT_VERSION);
		if(t11.getText() == "mainmenu.company")
			t11.setText(COMPANY);
		if(t12.getText() == "mainmenu.fps")
			t12.setText("Fps: " + this.timer.lastFrameTime);
		if(t13.getText() == "profile.title")
			t13.setText(PROFILE_TITLE);
		if(t14.getText() == "profile.info.name")
			t14.setText(PROFILE_INFO_ENIGNE);
		if(t15.getText() == "profile.info.version")
			t15.setText(PROFILE_INFO_VERSION);
		if(t16.getText() == "profile.info.versiontype")
			t16.setText(PROFILE_INFO_VERSION_TYPE + ": " + cx.getGameConfig().engineInfo.typeOfVersion);
		if(t17.getText() == "profile.info.username")
			t17.setText(PROFILE_INFO_USERNAME + ": " + cx.getSession().getUsername());
		if(t18.getText() == "profile.info.userid")
			t18.setText(PROFILE_INFO_USER_ID + ": " + cx.getSession().getPlayerId());
		if(t19.getText() == "profile.info.sessionid")
			t19.setText(PROFILE_INFO_SESSION_ID + ": " + cx.getSession().getSessionId());
		if(t19b.getText() == "profile.info.hasLicense")
			t19b.setText(PROFILE_INFO_HAS_LICENSE + ": " + cx.getSession().getUserLicense());
		if(t20.getText() == "profile.myplayer")
			t20.setText(PROFILE_YOUR_PLAYER);
		if(t21.getText() == "news.title")
			t21.setText(MAINMENU_NEWS_TITLE);
		if(t22.getText() == "news.version14")
			t22.setText(NEWS_VERSION_14);
		if(t23.getText() == "news.desc14")
			t23.setText(NEWS_LOG_DESC_14);
		if(t24.getText() == "news.version17")
			t24.setText(NEWS_VERSION_17);
		if(t25.getText() == "news.desc17")
			t25.setText(NEWS_LOG_DESC_17);
		if(t26.getText() == "url.title")
			t26.setText(URI_TITLE);
		if(t27.getText() == "url.continue")
			t27.setText(GUI_CONTINUE);
		if(t28.getText() == "singleplayer.generateWorld")
			t28.setText(GEN_WORLD);
		if(t29.getText() == "singleplayer.generateLowpolyWorld")
			t29.setText(GEN_LP_WORLD);
		if(t30.getText() == "singleplayer.generateFlatWorld")
			t30.setText(GEN_F_WORLD);
		if(t31.getText() == "singleplayer.generateEmptyWorld")
			t31.setText(GEN_E_WORLD);
		if(t32.getText() == "singleplayer.loadWorld")
			t32.setText(LOAD_WORLD);
		if(t33.getText() == "quit.desc")
			t33.setText(QUIT_GAME_DESC);
		if(t34.getText() == "quit.return")
			t34.setText(GUI_RETURN);
		if(t35.getText() == "quit.quitgame")
			t35.setText(QUIT_GAME);
		if(t36.getText() == "credits.desc")
			t36.setText(CREDITS_DESC);
		if(t37.getText() == "options.title")
			t37.setText(MAINMENU_OPTIONS_TITLE);
		if(t38.getText() == "options.graphics")
			t38.setText(GRAPHICS_TITLE);
		if(t39.getText() == "options.controls")
			t39.setText(CONTROLS_TITLE);
		if(t40.getText() == "options.audio")
			t40.setText(AUDIO_TITLE);
		if(t41.getText() == "options.language")
			t41.setText(LANGUAGE_TITLE);
		if(t42.getText() == "options.other")
			t42.setText(OTHER_TITLE);
		if(t43.getText() == "options.openglSettings")
			t43.setText(OPENGL_SETTINGS_TITLE);
		if(t44.getText() == "options.option.fov")
			t44.setText(FOV + InGameSettings.fovIn);
		if(t45.getText() == "options.saveChangesMessage")
			t45.setText(OPTION_CHANGE_DESC);
		if(t46.getText() == "options.graphics.title")
			t46.setText(GRAPHICS_TITLE);
		if(t47.getText() == "options.graphics.option.fullscreen")
			t47.setText(OPTION_FULLSCREEN + ": " + InGameSettings.useFullscreenIn);
		if(t48.getText() == "options.graphics.option.postEffects")
			t48.setText(OPTION_POST_EFFECTS + ": " + InGameSettings.usePostEffectsIn);
		if(t49.getText() == "options.graphics.option.guiScale")
			t49.setText(OPTION_GUI_SCALE + ": " + InGameSettings.guiScaleIn);
		if(t50.getText() == "options.graphics.option.fog")
			t50.setText(OPTION_FOG + ": " + "null");
		if(t51.getText() == "options.graphics.option.renderDistance")
			t51.setText(OPTION_RENDER_DISTANCE + ": " + InGameSettings.renderDistanceIn);
		if(t52.getText() == "options.graphics.option.renderSkybox")
			t52.setText(OPTION_RENDER_SKYBOX + ": " + InGameSettings.renderSkyBoxIn);
		if(t53.getText() == "options.graphics.option.renderWater")
			t53.setText(OPTION_RENDER_WATER + ": " + InGameSettings.renderWaterIn);
		if(t54.getText() == "options.graphics.option.shadows")
			t54.setText(OPTION_SHADOWS + ": " + InGameSettings.useShadowsIn);
		if(t55.getText() == "options.graphics.option.maxFps")
			t55.setText(OPTION_MAX_FPS + ": " + InGameSettings.limitFpsMenuIn);
		if(t56.getText() == "options.graphics.option.vSync")
			t56.setText(OPTION_VSYNC + ": " + InGameSettings.useVSync);
		if(t57.getText() == "options.graphics.option.particles")
			t57.setText(OPTION_PARTICLES + ": " + InGameSettings.renderParticlesIn);
		if(t57b.getText() == "options.graphics.option.brightness")
			t57b.setText(OPTION_BRIGHTNESS + ": " + InGameSettings.brightnessIn);
		if(t58.getText() == "options.openglSettings.title")
			t58.setText(OPENGL_SETTINGS_TITLE + ":");
		if(t59.getText() == "options.openglSettings.desc")
			t59.setText(GL_SETTINGS_DESC);
		if(t60.getText() == "options.openglSettings.arbVbo")
			t60.setText(GL_SETTINGS_ARB_VBO + ": " + GlHelperError.isArbVboSupported());
		if(t61.getText() == "options.openglSettings.vbo")
			t61.setText(GL_SETTINGS_VBO + ": " + GlHelperError.isVboSupported());
		if(t62.getText() == "options.openglSettings.multitexture")
			t62.setText(GL_SETTINGS_MULTITEXTURE + ": " + GlHelperError.isMultitextureSupported());
		if(t63.getText() == "options.openglSettings.textureCombine")
			t63.setText(GL_SETTINGS_TEXTURE_COMBINE + ": " + GlHelperError.isTextureCombineSupported());
		if(t64.getText() == "options.openglSettings.shaders")
			t64.setText(GL_SETTINGS_SHADERS + ": " + GlHelperError.isShaderSupported());
		if(t65.getText() == "options.openglSettings.framebuffer")
			t65.setText(GL_SETTINGS_FRAMEBUFFER + ": " + GlHelperError.isFramebufferSupported());
		if(t66.getText() == "options.openglSettings.openGl14")
			t66.setText(GL_SETTINGS_GL14 + ": " + GlHelperError.versionGl14());
		if(t67.getText() == "options.openglSettings.openGl21")
			t67.setText(GL_SETTINGS_GL21 + ": " + GlHelperError.versionGl21());
		if(t68.getText() == "options.language.title")
			t68.setText(LANGUAGE_TITLE + ":");
		if(t69.getText() == "options.language.desc")
			t69.setText(LANG_DESC);
		if(t70.getText() == "options.language.lang.en")
			t70.setText(LANG_EN);
		if(t71.getText() == "options.language.lang.ru")
			t71.setText(LANG_RU);
		if(t72.getText() == "options.other.title")
			t72.setText(OTHER_TITLE);
		if(t73.getText() == "options.other.fpsTabOnMenu")
			t73.setText(OTHER_FPS_TAB + ": " + InGameSettings.fpsTabOnMenuIn);
		if(t74.getText() == "options.other.renderCursor")
			t74.setText(OTHER_RENDER_CURSOR + ": " + InGameSettings.renderCursorIn);
		if(t75.getText() == "options.controls.title")
			t75.setText(CONTROLS_TITLE);
		if(t76.getText() == "options.controls.control.forward")
			t76.setText(CONTROLS_CONTROL_FORWARD);
		if(t77.getText() == "options.controls.control.backward")
			t77.setText(CONTROLS_CONTROL_BACKWARD);
		if(t78.getText() == "options.controls.control.leftward")
			t78.setText(CONTROLS_CONTROL_LEFTWARD);
		if(t79.getText() == "options.controls.control.rightward")
			t79.setText(CONTROLS_CONTROL_RIGHTWARD);
		if(t80.getText() == "options.controls.control.jump")
			t80.setText(CONTROLS_CONTROL_JUMP);
		if(t81.getText() == "options.controls.control.run")
			t81.setText(CONTROLS_CONTROL_RUN);
		if(t82.getText() == "options.controls.control.inventory")
			t82.setText(CONTROLS_CONTROL_INVENTORY);
		if(t83.getText() == "options.controls.control.pickup")
			t83.setText(CONTROLS_CONTROL_PICKUP);
		if(t84.getText() == "options.controls.control.pause")
			t84.setText(CONTROLS_CONTROL_PAUSE);
		if(t85.getText() == "options.controls.control.playerstab")
			t85.setText(CONTROLS_CONTROL_PLAYERTAB);
		if(t86.getText() == "options.controls.control.infopage")
			t86.setText(CONTROLS_CONTROL_INFOPAGE);
		if(t87.getText() == "loadingWorld.title")
			t87.setText(LOADING_WORLD);
		if(t88.getText() == "loadingWorld.buildingTerrain")
			t88.setText(LOADING_BUILDING_TERRAIN + "...");
		if(t89.getText() == "loadingWorld.generateTerrain")
			t89.setText(LOADING_GENERATE_TERRIAN + "...");
		if(t90.getText() == "loadingWorld.processingEntity")
			t90.setText(LOADING_PROCESSING_ENTITY + "...");
		if(t91.getText() == "gamePause.title")
			t91.setText(GAMEPAUSE_TITLE);
		if(t92.getText() == "gamePause.resume")
			t92.setText(GAMEPAUSE_RESUME);
		if(t93.getText() == "gamePause.options")
			t93.setText(GAMEPAUSE_OPTIONS);
		if(t94.getText() == "gamePause.saveWorld")
			t94.setText(GAMEPAUSE_SAVE_WORLD);
		if(t95.getText() == "gamePause.help")
			t95.setText(GAMEPAUSE_HELP);
		if(t96.getText() == "gamePause.backToMenu")
			t96.setText(GAMEPAUSE_BACK_MENU);
		if(t97.getText() == "gamePause.saveAndExit")
			t97.setText(GAMEPAUSE_SAVE_AND_EXIT);
		if(t98.getText() == "options.controls.selectControl.desc")
			t98.setText(CONTROLS_SELECT_CONTROL_DESC);
		if(t99.getText() == "loadingWorld.savingWorld")
			t99.setText(LOADING_SAVING_WORLD);
		if(t100.getText() == "gameOver.title")
			t100.setText(GAMEOVER_TITLE);
		if(t101.getText() == "gameOver.respawn")
			t101.setText(GAMEOVER_RESPAWN);
		if(t102.getText() == "gameOver.backToMenu")
			t102.setText(GAMEOVER_BACK_TO_MENU);
		if(t103.getText() == "gameOver.reason.health")
			t103.setText(GAMEOVER_REASON_HEALTH);
		if(t104.getText() == "gameOver.reason.command")
			t104.setText(GAMEOVER_REASON_COMMAND);
		if(t105.getText() == "gameOver.reason.water")
			t105.setText(GAMEOVER_REASON_WATER);
		if(t106.getText() == "multiplayer.title")
			t106.setText(MAINMENU_MULTIPLAYER_TITLE);
		if(t107.getText() == "multiplayer.serverVersion")
			t107.setText(SERVER_VERSION);
		if(t108.getText() == "multiplayer.createServer")
			t108.setText(MULTIPLAYER_CREATE_SERVER);
		if(t109.getText() == "multiplayer.connectToServer")
			t109.setText(MULTIPLAYER_CONNECT_TO_SERVER);
		if(t109b.getText() == "connecting.title")
			t109b.setText(CONNECTING_TITLE);
		if(t110.getText() == "disconnected.title")
			t110.setText(DISCONNECTING_TITLE);
		if(t111.getText() == "disconnected.backToMenu")
			t111.setText(DISCONNECTING_BACK_TO_MENU);
		if(t112.getText() == "disconnected.quit")
			t112.setText(DISCONNECTING_QUIT);
		if(t113.getText() == "disconnected.clientUnknownIp")
			t113.setText(DISCONNECTING_CLINET_UN_IP);
		if(t114.getText() == "disconnected.serverUnknownIp")
			t114.setText(DISCONNECTING_SERVER_UN_IP);
		if(t115.getText() == "connecting.checkIp")
			t115.setText(CONNECTING_CHECK_IP + "...");
		if(t116.getText() == "connecting.server.waitClient")
			t116.setText(CONNECTING_SERVER_WAIT_CLIENT + "...");
		if(t117.getText() == "connectitg.client.joinToServer")
			t117.setText(CONNECTING_CLIENT_JOIN_SERVER);
		if(t118.getText() == "createWorld.title")
			t118.setText(CREATE_WORLD_TITLE);
		if(t119.getText() == "createWorld.create")
			t119.setText(CREATE_WORLD_CREATE);
		if(t120.getText() == "createWorld.worldName")
			t120.setText(CREATE_WORLD_WORLD_NAME);
		if(t120b.getText() == "createWorld.worldName_user")
			t120b.setText(menu.getCreatingWorld().getTempWorldName());
		if(t121.getText() == "createWorld.gamemode")
			t121.setText(CREATE_WORLD_GAMEMODE + ": " + menu.getCreatingWorld().stringModes);
		if(t122.getText() == "createWorld.worldType")
			t122.setText(CREATE_WORLD_WORLD_TYPE + ": " + menu.getCreatingWorld().stringTypes);
		if(t123.getText() == "createWorld.worldSeed")
			t123.setText(CREATE_WORLD_WORLD_SEED + ": " + menu.getCreatingWorld().getTempSeed());
		if(t124.getText() == "createWorld.otherSettings")
			t124.setText(CREATE_WORLD_OTHER_SETTINGS);
		if(t125.getText() == "createWorld.saveAs")
			t125.setText(CREATE_WORLD_SAVE_AS + " - " + menu.getCreatingWorld().getTempWorldName());
		if(t126.getText() == "editor.switchEditor")
			t126.setText(EDITOR_SWITCH);
		if(t127.getText() == "mainmenu.hasntLicense")
			t127.setText(MAINMENU_HAVENT_LICENSE);
		if(t128.getText() == "options.graphics.option.postEffects.more")
			t128.setText(OPTION_POST_EFFECTS_MORE);
		if(t129.getText() == "options.graphics.option.postEffects.more.contrast")
			t129.setText(OPTION_POST_EFFECTS_MORE_CONTRAST + ": " + InGameSettings.useContrastIn);
		if(t130.getText() == "options.graphics.option.postEffects.more.brightFilter")
			t130.setText(OPTION_POST_EFFECTS_MORE_BRIGHT_FILTER + ": " + InGameSettings.useBloomIn);
		if(t131.getText() == "options.graphics.option.postEffects.more.gaussianBlur")
			t131.setText(OPTION_POST_EFFECTS_MORE_GAUSSIAN_BLUR + ": " + InGameSettings.useGaussianBlurIn);
		if(tt1.getText() == "helpPage.playerCoords")
			tt1.setText("Player Coordinates: ");
	}

}