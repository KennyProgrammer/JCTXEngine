package com.kenny.craftix.main;

import java.io.File;
import java.net.Proxy;
import java.util.List;

import org.lwjgl.LWJGLException;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.settings.Session;
import com.kenny.launcher.CraftixLauncher;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;

public class Main 
{
	public static void main(String [] _main_0) throws LWJGLException, InterruptedException
	{	
		/**Main Option-Config Setup On Default**/
		OptionParser optionParser = new OptionParser();
		optionParser.allowsUnrecognizedOptions();
		optionParser.accepts("demo");
		optionParser.accepts("fullscreenMode");
		OptionSpec<File> option_engineDir = optionParser.accepts("engineDir").withRequiredArg().<File>ofType
				(File.class).defaultsTo(new File("."));
		OptionSpec<File> option_assetsDir = optionParser.accepts("assetsDir").withRequiredArg().<File>ofType
				(File.class);
		OptionSpec<File> option_resourcePackDir = optionParser.accepts("resourcepacksDir").withRequiredArg()
				.<File>ofType(File.class);
		OptionSpec<String> option_username = optionParser.accepts("username").withRequiredArg().defaultsTo
				("Nickname" + Craftix.getCurrentTime() % 1000L);
		OptionSpec<String> option_uuid = optionParser.accepts("uuid").withRequiredArg();
		OptionSpec<String> option_version = optionParser.accepts("version").withRequiredArg().defaultsTo
				(Craftix.getVersion());
		OptionSpec<Integer> option_width = optionParser.accepts("displayWidth").withRequiredArg().<Integer>ofType
				(Integer.class).defaultsTo(Integer.valueOf(960));
		OptionSpec<Integer> option_height = optionParser.accepts("displayHeight").withRequiredArg().<Integer>ofType
			(Integer.class).defaultsTo(Integer.valueOf(540));
		OptionSpec<Boolean> option_licence = optionParser.accepts("hasLicense").withRequiredArg().<Boolean>ofType
				(Boolean.class).defaultsTo(true);
		OptionSpec<String> option_versionType = optionParser.accepts("versionType").withRequiredArg().defaultsTo
				("Alpha");
		OptionSpec<String> option_sessionType = optionParser.accepts("userType").withRequiredArg().defaultsTo
				("Local");
		OptionSpec<String> option_end = optionParser.nonOptions();
		OptionSet optionSet = optionParser.parse(_main_0);
		List<String> list = optionSet.valuesOf(option_end);
		
		if(!list.isEmpty())
		{
			System.out.println("The arguments were completely ignored: " + list);
		}
		
		/**Initialize the Option-Config parameters.*/
		int i = ((Integer)optionSet.valueOf(option_width)).intValue(); /**width*/
	    int j = ((Integer)optionSet.valueOf(option_height)).intValue(); /**height*/
	    boolean flag = optionSet.has("fullscreenMode");
	    boolean flag2 = optionSet.has("demo");
	    String s2 = (String)optionSet.valueOf(option_version);
	    String s3 = (String)optionSet.valueOf(option_versionType);
	    File file1 = (File)optionSet.valueOf(option_engineDir);
	    File file2 = optionSet.has(option_assetsDir) ? (File)optionSet.valueOf(option_assetsDir) : 
	    	new File(file1, "assets/");
	    File file3 = optionSet.has(option_resourcePackDir) ? (File)optionSet.valueOf(option_resourcePackDir) : 
	    	new File(file1, "resourcepacks/");
	    String s5 = optionSet.has(option_uuid) ? (String)option_uuid.value(optionSet) : 
	    	(String)option_username.value(optionSet);
	    Session session = new Session(option_username.value(optionSet), 
	    		s5, option_sessionType.value(optionSet), option_licence.value(optionSet));
		Proxy proxy = Proxy.NO_PROXY;
		GameConfiguration gameConfig = new GameConfiguration(
				new GameConfiguration.FolderInformation(file1, file3, file2), 
				new GameConfiguration.UserInformation(session, proxy),
				new GameConfiguration.GameEngineInformation(flag2, s2, s3),
				new GameConfiguration.DisplayInformation(flag, i, j));
		boolean isRunningWithLauncher = false;
		if(isRunningWithLauncher)
		{
			Thread.currentThread().setName("Launcher thread");
			(new CraftixLauncher()).run(new Craftix(gameConfig), session);
		} else {
			Thread.currentThread().setName("Client thread");
			(new Craftix(gameConfig)).run();
		}
		Thread.currentThread().setName("Server thread");
	}
}
