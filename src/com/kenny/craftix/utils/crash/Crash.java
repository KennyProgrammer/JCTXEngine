package com.kenny.craftix.utils.crash;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.Sys;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.ResourceLocation;
import com.kenny.craftix.client.settings.InGameSettings;
import com.kenny.craftix.utils.SimplePrint;
import com.kenny.craftix.utils.Timer;

public class Crash 
{
	public static final Logger LOGGER = LogManager.getLogger(Craftix.LOGGER_INSTANCE);
	
	/**Normal status has index [0].*/
	public boolean statusNormal;
	/**Critical status has index [1].*/
	public boolean statusCritical;
	/**Fatal status has index [2].*/
	public boolean statusFatal;
	/**GlDisplay status has index [10].*/
	public boolean statusGLDisplay;
	
	public static boolean crashModelNotFound;
	
	public void crashManager(int crashStatus)
	{
		if(crashStatus == 0)
		{
			this.statusNormal = true;
			this.crashNormalStatus();
		}
		else if(crashStatus == 1)
		{
			this.statusCritical = true;
			this.crashCriticalStatus();
		}
		else if(crashStatus == 2)
		{
			this.statusFatal = true;
			this.crashFatalStatus();
		}
		else if(crashStatus == 10)
		{
			this.statusGLDisplay = true;
			this.crashGlDisplayStatus();
		}
	}
	
	public void crashOverhead(Craftix craftixIn) throws IOException
	{
		Timer timer = new Timer();
		Date date = new Date();
		String s = null;
		if(craftixIn.status.isServer()) 
			 s = "Server"; 
		else s = "Client";
		@SuppressWarnings("deprecation")
		File f = new File(ResourceLocation.CRAFTIX_FOLDER + "crashReports/crash_report_"
				+ date.getYear() + "-" + date.getMonth() + "-" + date.getDay() + "_" + date.getHours()
					+ "." + date.getMinutes() + "." + date.getSeconds()  + ".txt");
		f.createNewFile();
		FileOutputStream fos = new FileOutputStream(f);
		PrintStream ps = new PrintStream(fos);
		System.setOut(ps);
		this.toCrashReportFile(craftixIn, date, timer, s);
		LOGGER.error("###################### GAME CRASH ########################");
		LOGGER.error("");
		LOGGER.error("Data time: " + date.toString());
		LOGGER.error("Possible error: " + "Varible has NULL value!");
		LOGGER.error("");
		LOGGER.error("There was a crash game session. So you see this message.");
		LOGGER.error("The reasons for this outcome can be many. Let's see which ");
		LOGGER.error("side of the crash happened.");
		LOGGER.error("");
		LOGGER.error("Crash happen on side: " + s);
		LOGGER.error("Session time: " + timer.getFrameTimeSeconds());
		LOGGER.error("");
		LOGGER.error("Items for possible solution: ");
		LOGGER.error("  1) If you deleted files from the resource pack then ");
		LOGGER.error("return them.");
		LOGGER.error("  2) If you changed the original class files then ");
		LOGGER.error("reinstall the game.");
		LOGGER.error("  3) Check if you have enough RAM allocated for java.");
		LOGGER.error("");
		LOGGER.error("==========================================================");
		LOGGER.error("Additional description of the error:");
		LOGGER.error("==========================================================");
		LOGGER.error("");
		LOGGER.error("Current User: {}", (Object)craftixIn.getSession().getUsername());
		LOGGER.error("LWJGL Version: " + (Object)Sys.getVersion());
		LOGGER.error("System 64 bit: " + (Object)Sys.is64Bit());
		LOGGER.error("Game resolution: " + InGameSettings.displayWidthIn + " x " + InGameSettings.displayHeightIn);
		LOGGER.error("Stacktrace: ");
		craftixIn.exError.printStackTrace();
		LOGGER.error("");
		LOGGER.error("Java error has: " + craftixIn.exError);
		if(craftixIn.exError.toString() == "java.lang.NullPointerException")
		{
			LOGGER.error("This error occurs because some variable is not NULL i.e ");
			LOGGER.error("inclusionary. If you are a regular player, please contact the developer.");
		}
		if(craftixIn.exError.toString() == "org.lwjgl.LWJGLException")
		{
			LOGGER.error("The error occurred in the lightweight Java library. Audio Master");
			LOGGER.error("may not be able to start. Just restart the game. If this does not solve");
			LOGGER.error("the problem, please contact the developer");
		}
		if(craftixIn.exError.toString() == "java.lang.IllegalStateException: Zero length vector" || craftixIn.exError.toString() == "java.lang.IllegalStateException")
		{
			LOGGER.error("This error comes out because some object was not called at the right");
			LOGGER.error("time. Here it is recommended to find the root of the problem by stack trace.");
			LOGGER.error("And if next to the error is the inscription 'Zero length vector', then simply ");
			LOGGER.error("specify the length of the vector. If you are a regular player then contact");
			LOGGER.error("the developer.");
		}
		LOGGER.error("");
		LOGGER.error("##########################################################");

	}
	
	public void toCrashReportFile(Craftix c, Date d, Timer t, String s)
	{
		SimplePrint.print("###################### GAME CRASH ########################");
		SimplePrint.print("");
		SimplePrint.print("Data time: " + d.toString());
		SimplePrint.print("Possible error: " + "Varible has NULL value!");
		SimplePrint.print("");
		SimplePrint.print("There was a crash game session. So you see this message.");
		SimplePrint.print("The reasons for this outcome can be many. Let's see which ");
		SimplePrint.print("side of the crash happened.");
		SimplePrint.print("");
		SimplePrint.print("Crash happen on side: " + s);
		SimplePrint.print("Session time: " + t.getFrameTimeSeconds());
		SimplePrint.print("");
		SimplePrint.print("Items for possible solution: ");
		SimplePrint.print("  1) If you deleted files from the resource pack then ");
		SimplePrint.print("return them.");
		SimplePrint.print("  2) If you changed the original class files then ");
		SimplePrint.print("reinstall the game.");
		SimplePrint.print("  3) Check if you have enough RAM allocated for java.");
		SimplePrint.print("");
		SimplePrint.print("==========================================================");
		SimplePrint.print("Additional description of the error:");
		SimplePrint.print("==========================================================");
		SimplePrint.print("");
		SimplePrint.print("Current User: " + (Object)c.getSession().getUsername());
		SimplePrint.print("LWJGL Version: " + (Object)Sys.getVersion());
		SimplePrint.print("System 64 bit: " + (Object)Sys.is64Bit());
		SimplePrint.print("Game resolution: " + InGameSettings.displayWidthIn + " x " + InGameSettings.displayHeightIn);
		SimplePrint.print("Stacktrace: ");
		c.exError.printStackTrace();
		SimplePrint.print("");
		SimplePrint.print("Java error has: " + c.exError);
		if(c.exError.toString() == "java.lang.NullPointerException")
		{
			SimplePrint.print("This error occurs because some variable is not NULL i.e ");
			SimplePrint.print("inclusionary. If you are a regular player, please contact the developer.");
		}
		if(c.exError.toString() == "org.lwjgl.LWJGLException")
		{
			SimplePrint.print("The error occurred in the lightweight Java library. Audio Master");
			SimplePrint.print("may not be able to start. Just restart the game. If this does not solve");
			SimplePrint.print("the problem, please contact the developer");
			
		}
		if(c.exError.toString() == "java.lang.IllegalStateException: Zero length vector" || c.exError.toString() == "java.lang.IllegalStateException")
		{
			SimplePrint.print("This error comes out because some object was not called at the right");
			SimplePrint.print("time. Here it is recommended to find the root of the problem by stack trace.");
			SimplePrint.print("And if next to the error is the inscription 'Zero length vector', then simply ");
			SimplePrint.print("specify the length of the vector. If you are a regular player then contact");
			SimplePrint.print("the developer.");
		}
		SimplePrint.print("");
		SimplePrint.print("##########################################################");

	}
	
	private void crashNormalStatus()
	{
		if(this.statusNormal)
		{
			LOGGER.error("####### CRASH NORMAL STATUS #######");
			LOGGER.error("Please check if you have made a mistake in the shader ");
			LOGGER.error("or maybe just did not call the right method to work.");
			
		}
	}
	
	private void crashCriticalStatus()
	{
		if(this.statusCritical)
		{
			LOGGER.error("####### CRASH CRITICAL STATUS #######");
			LOGGER.error("The critical status of error indicates that you may have ");
			LOGGER.error("instalize a specific class or made a mistake instalize the ");
			LOGGER.error("accuracy of the methods.");
			
		}
	}
	
	private void crashFatalStatus()
	{
		if(this.statusFatal)
		{
			LOGGER.error("####### CRASH FATAL STATUS #######");
			LOGGER.error("If a fatal crash is caused, it is likely that you have made many ");
			LOGGER.error("different errors in the method or the whole class. If you added ");
			LOGGER.error("a new Fitch then go back to that class and review it again.");
			
		}
	}
	
	public void crashGlDisplayStatus()
	{
		if(this.statusGLDisplay)
		{
			LOGGER.error("####### CRASH GL DISPLAY STATUS #######");
			LOGGER.error("Maybe you incorrectly created or installed display. Maybe ");
			LOGGER.error("even forgot the table of contents.");
		}
	}
}
