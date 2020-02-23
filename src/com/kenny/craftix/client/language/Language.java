package com.kenny.craftix.client.language;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.base.Splitter;
import com.kenny.craftix.client.ClientGetterInformation;
import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.language.localization.LocRu;
import com.kenny.craftix.client.settings.InGameSettings;
import com.kenny.craftix.client.settings.nbt.NBTTagCompound;
import com.kenny.craftix.server.ServerGetterInformation;
import com.kenny.craftix.utils.data.FixTypes;

public class Language 
{
	public static final Logger LOGGER = LogManager.getLogger();
	public static final Splitter COLON_SPLITTER = Splitter.on('=');
	
	protected File langFile;
	public File langEn, langRu;
	public Craftix cx;
	public LocRu locRu = new LocRu();
	/**Helps get the information about client side game engine.*/
	protected ClientGetterInformation clientInfo;
	/**Helps get the information about server side game engine.*/
	protected ServerGetterInformation serverInfo;
	
	public static boolean selectLang;
	/**
	 * This is all words useds in the engine. In each of the localization 
	 * files, these constants are equal to words from that language. Soon these 
	 * constants will not be because I will make the translation in 
	 * the text file.
	 */
	public static String GUI_BACK;
	public static String GUI_ON;
	public static String GUI_OFF;
	public static String GUI_CANCEL;
	public static String GUI_CONTINUE;
	public static String GUI_UP;
	public static String GUI_DONW;
	public static String GUI_ALL;
	public static String GUI_NONE;
	public static String GUI_RETURN;
	public static String GUI_NORMAL;
	public static String GUI_HIGH;
	public static String GUI_SMALL;
	public static String GUI_SAVE;
	public static String GUI_MIN;
	public static String GUI_MAX;
	public static String GAME_TITLE;
	public static String ENGINE_NAME;
	public static String CLIENT_VERSION;
	public static String SERVER_VERSION;
	public static String MAINMENU_SINGLEPLAYER_TITLE;
	public static String MAINMENU_MULTIPLAYER_TITLE;
	public static String MAINMENU_OPTIONS_TITLE;
	public static String MAINMENU_CREDITS_TITLE;
	public static String MAINMENU_NEWS_TITLE;
	public static String MAINMENU_WEBSITE_TITLE;
	public static String MAINMENU_EDITOR;
	public static String MAINMENU_QUIT_TITLE;
	public static String MAINMENU_HAVENT_LICENSE;
	public static String PROFILE_TITLE;
	public static String PROFILE_INFO_ENIGNE;
	public static String PROFILE_INFO_VERSION;
	public static String PROFILE_INFO_VERSION_TYPE;
	public static String PROFILE_INFO_USERNAME;
	public static String PROFILE_INFO_USER_ID;
	public static String PROFILE_INFO_SESSION_ID;
	public static String PROFILE_YOUR_PLAYER;
	public static String PROFILE_INFO_HAS_LICENSE;
	public static String NEWS_VERSION_14;
	public static String NEWS_VERSION_17;
	public static String NEWS_LOG_DESC_14;
	public static String NEWS_LOG_DESC_17;
	public static String ENGINE_INFO_TITLE;
	public static String GRAPHICS_TITLE;
	public static String AUDIO_TITLE;
	public static String LANGUAGE_TITLE;
	public static String OTHER_TITLE;
	public static String OPENGL_SETTINGS_TITLE;
	public static String OPTION_FULLSCREEN;
	public static String OPTION_POST_EFFECTS;
	public static String OPTION_POST_EFFECTS_MORE;
	public static String OPTION_POST_EFFECTS_MORE_CONTRAST;
	public static String OPTION_POST_EFFECTS_MORE_BRIGHT_FILTER;
	public static String OPTION_POST_EFFECTS_MORE_GAUSSIAN_BLUR;
	public static String OPTION_GUI_SCALE;
	public static String OPTION_FOG;
	public static String OPTION_RENDER_DISTANCE;
	public static String OPTION_RENDER_SKYBOX;
	public static String OPTION_RENDER_WATER;
	public static String OPTION_SHADOWS;
	public static String OPTION_MAX_FPS;
	public static String OPTION_VSYNC;
	public static String OPTION_PARTICLES;
	public static String OPTION_BRIGHTNESS;
	public static String CONTROLS_TITLE;
	public static String CONTROLS_CONTROL_FORWARD;
	public static String CONTROLS_CONTROL_BACKWARD;
	public static String CONTROLS_CONTROL_RIGHTWARD;
	public static String CONTROLS_CONTROL_LEFTWARD;
	public static String CONTROLS_CONTROL_JUMP;
	public static String CONTROLS_CONTROL_RUN;
	public static String CONTROLS_CONTROL_INVENTORY;
	public static String CONTROLS_CONTROL_PICKUP;
	public static String CONTROLS_CONTROL_PAUSE;
	public static String CONTROLS_CONTROL_PLAYERTAB;
	public static String CONTROLS_CONTROL_INFOPAGE;
	public static String CONTROLS_SELECT_CONTROL_DESC;
	public static String GL_SETTINGS_DESC;
	public static String GL_SETTINGS_ARB_VBO;
	public static String GL_SETTINGS_VBO;
	public static String GL_SETTINGS_MULTITEXTURE;
	public static String GL_SETTINGS_TEXTURE_COMBINE;
	public static String GL_SETTINGS_SHADERS;
	public static String GL_SETTINGS_FRAMEBUFFER;
	public static String GL_SETTINGS_GL14;
	public static String GL_SETTINGS_GL21;
	public static String OTHER_FPS_TAB;
	public static String OTHER_RENDER_CURSOR;
	public static String URI_TITLE;
	public static String CREATE_WORLD_TITLE;
	public static String CREATE_WORLD_CREATE;
	public static String CREATE_WORLD_WORLD_NAME;
	public static String CREATE_WORLD_GAMEMODE;
	public static String CREATE_WORLD_WORLD_TYPE;
	public static String CREATE_WORLD_WORLD_SEED;
	public static String CREATE_WORLD_OTHER_SETTINGS;
	public static String CREATE_WORLD_SAVE_AS;
	public static String CONTINUE;
	public static String COMPANY;
	public static String PLAY;
	public static String FILE_CONFIG_ERROR;
	public static String FBO;
	public static String TRIANGLE_MODE;
	public static String SOON_AUDIO;
	public static String CREDITS_DESC;
	public static String MULTIPLAYER_CREATE_SERVER;
	public static String MULTIPLAYER_CONNECT_TO_SERVER;
	public static String CONNECTING_TITLE;
	public static String CONNECTING_CHECK_IP;
	public static String CONNECTING_SERVER_WAIT_CLIENT;
	public static String CONNECTING_SERVER_CLIENT_CONNECT;
	public static String CONNECTING_CLIENT_JOIN_SERVER;
	public static String CONNECTING_CLIENT_JOINED;
	public static String DISCONNECTING_TITLE;
	public static String DISCONNECTING_BACK_TO_MENU;
	public static String DISCONNECTING_QUIT;
	public static String DISCONNECTING_CLINET_UN_IP;
	public static String DISCONNECTING_SERVER_UN_IP;
	public static String GEN_WORLD;
	public static String LOAD_WORLD;
	public static String GEN_LP_WORLD;
	public static String GEN_F_WORLD;
	public static String GEN_E_WORLD;
	public static String LOADING_WORLD;
	public static String LOADING_BUILDING_TERRAIN;
	public static String LOADING_GENERATE_TERRIAN;
	public static String LOADING_PROCESSING_ENTITY;
	public static String LOADING_SAVING_WORLD;
	public static String GAMEPAUSE_TITLE;
	public static String GAMEPAUSE_RESUME;
	public static String GAMEPAUSE_OPTIONS;
	public static String GAMEPAUSE_SAVE_WORLD;
	public static String GAMEPAUSE_HELP;
	public static String GAMEPAUSE_BACK_MENU;  
	public static String GAMEPAUSE_SAVE_AND_EXIT;
	public static String GAMEOVER_TITLE;
	public static String GAMEOVER_RESPAWN;
	public static String GAMEOVER_BACK_TO_MENU;
	public static String GAMEOVER_REASON_HEALTH;
	public static String GAMEOVER_REASON_COMMAND;
	public static String GAMEOVER_REASON_WATER;
	public static String QUIT_GAME;
	public static String LATEST_UPDATES_TITLE;
	public static String FOV;
	public static String LANG_DESC;
	public static String LANG_EN;
	public static String LANG_RU;
	public static String OPTION_CHANGE_DESC;
	public static String RESOLUTION;
	public static String SCREEN_1600x900;
	public static String SCREEN_1280x720;
	public static String SCREEN_1200x600;
	public static String SCREEN_640x540;
	public static String AUDIO_ON_OFF;
	public static String RETURN;
	public static String QUIT_GAME_DESC;
	public static String RESPAWN;
	public static String GL_ERROR_SHADER_NO_SHADERS;
	public static String GL_ERROR_2;
	public static String GL_ERROR_3;
	public static String GL_ERROR_4;
	public static String GL_ERROR_5;
	public static String NEW_YEAR;
	public static String MERRY_CHRISTMAS;
	public static String EDITOR_SWITCH;
	
	public Language(Craftix craftixIn, File langEn, File langRu) 
	{
		this.cx = craftixIn;
		this.clientInfo = craftixIn.clientInfo;
		this.serverInfo = craftixIn.getServer().serverInfo;
		Locale.setDefault(Locale.ROOT);
		
		if(InGameSettings.languageIn == 0)
		{
			this.langFile = new File(langEn, "lang/lang_en.txt");
		}
		if(InGameSettings.languageIn == 1)
		{
			//this.langFile = new File(langEn, "lang/lang_en.txt");
			this.locRu.loadRuLocalization(this.clientInfo, this.serverInfo);
			//this.langFile = new File(langEn, "lang/lang_en.txt");
		}
	}
	
	/***
	 * Load lang for current lang file.
	 */
	public void loadLanguageFile()
	{
		try
		{
			@SuppressWarnings("deprecation")
			List<String> list = IOUtils.readLines(new FileInputStream(this.langFile));
			NBTTagCompound nbt = new NBTTagCompound();
			
			for(String s: list)
			{
				try
				{
					Iterator<String> iterator = COLON_SPLITTER.omitEmptyStrings().limit(2).split(s).iterator();
					nbt.setString(iterator.next(), iterator.next());
				}
				catch(Exception e)
				{
				}
			}
			nbt = this.dataFix(nbt);
			for(String s1 : nbt.getKeySet())
			{
				String s2 = nbt.getString(s1);
				
				try
				{
					if("gui.back".equals(s1))
						GUI_BACK = s2;
					if("gui.on".equals(s1))
						GUI_ON = s2;
					if("gui.off".equals(s1))
						GUI_OFF = s2;
					if("gui.cancel".equals(s1))
						GUI_CANCEL = s2;
					if("gui.continue".equals(s1))
						GUI_CONTINUE = s2;
					if("gui.up".equals(s1))
						GUI_UP = s2;
					if("gui.down".equals(s1))
						GUI_DONW = s2;
					if("gui.all".equals(s1))
						GUI_ALL = s2;
					if("gui.none".equals(s1))
						GUI_NONE = s2;
					if("gui.return".equals(s1))
						GUI_RETURN = s2;
					if("gui.small".equals(s1))
						GUI_SMALL = s2;
					if("gui.normal".equals(s1))
						GUI_NORMAL = s2;
					if("gui.high".equals(s1))
						GUI_HIGH = s2;
					if("gui.save".equals(s1))
						GUI_SAVE = s2;
					if("gui.min".equals(s1))
						GUI_MIN = s2;
					if("gui.max".equals(s1))
						GUI_MAX = s2;
					if("game.title".equals(s1)) 
						ENGINE_NAME = s2;
					if("mainmenu.singleplayer".equals(s1))
						MAINMENU_SINGLEPLAYER_TITLE = s2;
					if("mainmenu.multiplayer".equals(s1))
						MAINMENU_MULTIPLAYER_TITLE = s2;
					if("mainmenu.options".equals(s1))
						MAINMENU_OPTIONS_TITLE = s2;
					if("mainmenu.credits".equals(s1))
						MAINMENU_CREDITS_TITLE = s2;
					if("mainmenu.quit".equals(s1))
						MAINMENU_QUIT_TITLE = s2;
					if("mainmenu.version".equals(s1))
						CLIENT_VERSION = s2 + ": " + this.clientInfo.getClientVersion();
					if("mainmenu.company".equals(s1))
						COMPANY = s2;
					if("mainmenu.news".equals(s1))
						MAINMENU_NEWS_TITLE = s2;
					if("mainmenu.profile".equals(s1))
						PROFILE_TITLE = s2;
					if("mainmenu.website".equals(s1))
						MAINMENU_WEBSITE_TITLE = s2;
					if("mainmenu.editor".equals(s1))
						MAINMENU_EDITOR = s2;
					if("mainmenu.hasntLicense".equals(s1))
						MAINMENU_HAVENT_LICENSE = s2;
					if("profile.info.name".equals(s1))
						PROFILE_INFO_ENIGNE = s2 + ": " + this.clientInfo.getClientName();
					if("profile.info.version".equals(s1))
						PROFILE_INFO_VERSION = s2 + ": " +  this.clientInfo.getClientVersion();
					if("profile.info.versiontype".equals(s1))
						PROFILE_INFO_VERSION_TYPE = s2;
					if("profile.info.username".equals(s1))
						PROFILE_INFO_USERNAME = s2;
					if("profile.info.userid".equals(s1))
						PROFILE_INFO_USER_ID = s2;
					if("profile.info.sessionid".equals(s1))
						PROFILE_INFO_SESSION_ID = s2;
					if("profile.info.hasLicense".equals(s1))
						PROFILE_INFO_HAS_LICENSE = s2;
					if("profile.yourPlayer".equals(s1))
						PROFILE_YOUR_PLAYER = s2;
					if("news.version14".equals(s1))
						NEWS_VERSION_14 = s2;
					if("news.desc14".equals(s1))
						NEWS_LOG_DESC_14 = s2;
					if("news.version17".equals(s1))
						NEWS_VERSION_17 = s2;
					if("news.desc17".equals(s1))
						NEWS_LOG_DESC_17 = s2;
					if("url.title".equals(s1))
						URI_TITLE = s2;
					if("url.continue".equals(s1))
						CONTINUE = s2;
					if("quit.desc".equals(s1))
						QUIT_GAME_DESC = s2;
					if("quit.return".equals(s1))
						RETURN = s2;
					if("quit.quitgame".equals(s1))
						QUIT_GAME = s2;
					if("credits.desc".equals(s1))
						CREDITS_DESC = s2;
					if("singleplayer.generateWorld".equals(s1))
						GEN_WORLD = s2;
					if("singleplayer.generateLowpolyWorld".equals(s1))
						GEN_LP_WORLD = s2;
					if("singleplayer.generateFlatWorld".equals(s1))
						GEN_F_WORLD = s2;
					if("singleplayer.generateEmptyWorld".equals(s1))
						GEN_E_WORLD = s2;
					if("singleplayer.loadWorld".equals(s1))
						LOAD_WORLD = s2;
					if("createWorld.title".equals(s1))
						CREATE_WORLD_TITLE = s2;
					if("createWorld.create".equals(s1))
						CREATE_WORLD_CREATE = s2;
					if("createWorld.worldName".equals(s1))
						CREATE_WORLD_WORLD_NAME = s2;
					if("createWorld.gamemode".equals(s1))
						CREATE_WORLD_GAMEMODE = s2;
					if("createWorld.worldType".equals(s1))
						CREATE_WORLD_WORLD_TYPE = s2;
					if("createWorld.worldSeed".equals(s1))
						CREATE_WORLD_WORLD_SEED = s2;
					if("createWorld.otherWorldSettings".equals(s1))
						CREATE_WORLD_OTHER_SETTINGS = s2;
					if("createWorld.saveAs".equals(s1))
						CREATE_WORLD_SAVE_AS = s2;
					if("multiplayer.createServer".equals(s1))
						MULTIPLAYER_CREATE_SERVER = s2;
					if("multiplayer.connectToServer".equals(s1))
						MULTIPLAYER_CONNECT_TO_SERVER = s2;
					if("multiplayer.serverVersion".equals(s1))
						SERVER_VERSION = s2 + ": " + this.serverInfo.getServerVersion();
					if("connecting.titie".equals(s1))
						CONNECTING_TITLE = s2;
					if("connecting.checkIp".equals(s1))
						CONNECTING_CHECK_IP = s2;
					if("connecting.server.waitClient".equals(s1))
						CONNECTING_SERVER_WAIT_CLIENT = s2;
					if("connecting.server.clientConnect".equals(s1))
						CONNECTING_SERVER_CLIENT_CONNECT = s2;
					if("connecting.client.joinServer".equals(s1))
						CONNECTING_CLIENT_JOIN_SERVER = s2;
					if("connecting.client.joined".equals(s1))
						CONNECTING_CLIENT_JOINED = s2;
					if("disconnecting.title".equals(s1))
						DISCONNECTING_TITLE = s2;
					if("disconnecting.backToMenu".equals(s1))
						DISCONNECTING_BACK_TO_MENU = s2;
					if("disconnecting.quit".equals(s1))
						DISCONNECTING_QUIT = s2;
					if("disconnecting.clientUnknownIp".equals(s1))
						DISCONNECTING_CLINET_UN_IP = s2;
					if("disconnecting.serverUnknownIp".equals(s1))
						DISCONNECTING_SERVER_UN_IP = s2;
					if("options.graphics".equals(s1))
						GRAPHICS_TITLE = s2;
					if("options.controls".equals(s1))
						CONTROLS_TITLE = s2;
					if("options.audio".equals(s1))
						AUDIO_TITLE = s2;
					if("options.language".equals(s1))
						LANGUAGE_TITLE = s2;
					if("options.other".equals(s1))
						OTHER_TITLE = s2;
					if("options.graphics.option.fullscreen".equals(s1))
						OPTION_FULLSCREEN = s2;
					if("options.graphics.option.postEffects".equals(s1))
						OPTION_POST_EFFECTS = s2;
					if("options.graphics.option.postEffects.more".equals(s1))
						OPTION_POST_EFFECTS_MORE = s2;
					if("options.graphics.option.postEffects.more.contrast".equals(s1))
						OPTION_POST_EFFECTS_MORE_CONTRAST = s2;
					if("options.graphics.option.postEffects.more.brightFilter".equals(s1))
						OPTION_POST_EFFECTS_MORE_BRIGHT_FILTER = s2;
					if("options.graphics.option.postEffects.more.gaussianBlur".equals(s1))
						OPTION_POST_EFFECTS_MORE_GAUSSIAN_BLUR = s2;
					if("options.graphics.option.guiScale".equals(s1))
						OPTION_GUI_SCALE = s2;
					if("options.graphics.option.fog".equals(s1))
						OPTION_FOG = s2;
					if("options.graphics.option.renderDistance".equals(s1))
						OPTION_RENDER_DISTANCE = s2;
					if("options.graphics.option.renderSkybox".equals(s1))
						OPTION_RENDER_SKYBOX = s2;
					if("options.graphics.option.renderWater".equals(s1))
						OPTION_RENDER_WATER = s2;
					if("options.graphics.option.shadows".equals(s1))
						OPTION_SHADOWS = s2;
					if("options.graphics.option.maxFps".equals(s1))
						OPTION_MAX_FPS = s2;
					if("options.graphics.option.vSync".equals(s1))
						OPTION_VSYNC = s2;
					if("options.graphics.option.particles".equals(s1))
						OPTION_PARTICLES = s2;
					if("options.graphics.option.brightness".equals(s1))
						OPTION_BRIGHTNESS = s2;
					if("options.openglSettings".equals(s1))
						OPENGL_SETTINGS_TITLE = s2;
					if("options.openglSettings.desc".equals(s1))
						GL_SETTINGS_DESC = s2;
					if("options.openglSettings.arbVbo".equals(s1))
						GL_SETTINGS_ARB_VBO = s2;
					if("options.openglSettings.vbo".equals(s1))
						GL_SETTINGS_VBO = s2;
					if("options.openglSettings.multitexture".equals(s1))
						GL_SETTINGS_MULTITEXTURE = s2;
					if("options.openglSettings.textureCombine".equals(s1))
						GL_SETTINGS_TEXTURE_COMBINE = s2;
					if("options.openglSettings.shaders".equals(s1))
						GL_SETTINGS_SHADERS = s2;
					if("options.openglSettings.framebuffer".equals(s1))
						GL_SETTINGS_FRAMEBUFFER = s2;
					if("options.openglSettings.openGl14".equals(s1))
						GL_SETTINGS_GL14 = s2;
					if("options.openglSettings.openGl21".equals(s1))
						GL_SETTINGS_GL21 = s2;
					if("options.openglSettings.openGl21".equals(s1))
						GL_SETTINGS_GL21 = s2;
					if("options.controls.control.forward".equals(s1))
						CONTROLS_CONTROL_FORWARD = s2;
					if("options.controls.control.backward".equals(s1))
						CONTROLS_CONTROL_BACKWARD = s2;
					if("options.controls.control.leftward".equals(s1))
						CONTROLS_CONTROL_LEFTWARD = s2;
					if("options.controls.control.rightward".equals(s1))
						CONTROLS_CONTROL_RIGHTWARD = s2;
					if("options.controls.control.jump".equals(s1))
						CONTROLS_CONTROL_JUMP = s2;
					if("options.controls.control.run".equals(s1))
						CONTROLS_CONTROL_RUN = s2;
					if("options.controls.control.inventory".equals(s1))
						CONTROLS_CONTROL_INVENTORY = s2;
					if("options.controls.control.pickup".equals(s1))
						CONTROLS_CONTROL_PICKUP = s2;
					if("options.controls.control.pause".equals(s1))
						CONTROLS_CONTROL_PAUSE = s2;
					if("options.controls.control.playerstab".equals(s1))
						CONTROLS_CONTROL_PLAYERTAB = s2;
					if("options.controls.control.infopage".equals(s1))
						CONTROLS_CONTROL_INFOPAGE = s2;
					if("options.controls.selectControl.desc".equals(s1))
						CONTROLS_SELECT_CONTROL_DESC = s2;
					if("error.opengl.shaders.noSupportShader".equals(s1))
						GL_ERROR_SHADER_NO_SHADERS = s2;
					if("options.option.fov".equals(s1))
						FOV = s2;
					if("options.language.desc".equals(s1))
						LANG_DESC = s2;
					if("options.language.lang.en".equals(s1))
						LANG_EN = s2;
					if("options.language.lang.ru".equals(s1))
						LANG_RU = s2;
					if("options.other.fpsTabOnMenu".equals(s1))
						OTHER_FPS_TAB = s2;
					if("options.other.renderCursor".equals(s1))
						OTHER_RENDER_CURSOR = s2;
					if("options.saveChangesMessage".equals(s1))
						OPTION_CHANGE_DESC = s2;
					if("loadingWorld.title".equals(s1))
						LOADING_WORLD = s2;
					if("loadingWorld.buildingTerrain".equals(s1))
						LOADING_BUILDING_TERRAIN = s2;
					if("loadingWorld.generateTerrain".equals(s1))
						LOADING_GENERATE_TERRIAN = s2;
					if("loadingWorld.processingEntity".equals(s1))
						LOADING_PROCESSING_ENTITY = s2;
					if("loadingWorld.savingWorld".equals(s1))
						LOADING_SAVING_WORLD = s2;
					if("gamePause.title".equals(s1))
						GAMEPAUSE_TITLE = s2;
					if("gamePause.resume".equals(s1))
						GAMEPAUSE_RESUME = s2;
					if("gamePause.options".equals(s1))
						GAMEPAUSE_OPTIONS = s2;
					if("gamePause.saveWorld".equals(s1))
						GAMEPAUSE_SAVE_WORLD = s2;
					if("gamePause.help".equals(s1))
						GAMEPAUSE_HELP = s2;
					if("gamePause.backToMenu".equals(s1))
						GAMEPAUSE_BACK_MENU = s2;
					if("gamePause.saveAndExit".equals(s1))
						GAMEPAUSE_SAVE_AND_EXIT = s2;
					if("gameOver.title".equals(s1))
						GAMEOVER_TITLE = s2;
					if("gameOver.respawn".equals(s1))
						GAMEOVER_RESPAWN = s2;
					if("gameOver.returnToMenu".equals(s1))
						GAMEOVER_BACK_TO_MENU = s2;
					if("gameOver.reason.health".equals(s1))
						GAMEOVER_REASON_HEALTH = s2;
					if("gameOver.reason.command".equals(s1))
						GAMEOVER_REASON_COMMAND = s2;
					if("gameOver.reason.water".equals(s1))
						GAMEOVER_REASON_WATER = s2;
					
					if("other.newYear".equals(s1))
						NEW_YEAR = s2;
					if("other.merryChristmas".equals(s1))
						MERRY_CHRISTMAS = s2;
					if("editor.switchEditor".equals(s1))
						EDITOR_SWITCH = s2;
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		catch (Exception e) {}
	}

	/**
	 * Fixed the data to needed. Example convert string to int.
	 * @return
	 */
	private NBTTagCompound dataFix(NBTTagCompound nbt)
	{
		int i = 0;

	    try
	    {
	        i = Integer.parseInt(nbt.getString("craftixVersion"));
	    }
	    catch (RuntimeException var4) {}
	    
	        return this.cx.getDataFixer().process(FixTypes.OPTIONS, nbt, i);
	}

}
