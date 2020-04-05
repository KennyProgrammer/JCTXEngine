package CTXEngine.Core;

import CTXEngine.Core.CoreConfigurationsImpl;

public class CoreConfigurations implements CoreConfigurationsImpl
{
	private String  ctxEngineName;
	private String  ctxEngineVersion;
	private boolean ctxIsWindowed;
	private boolean ctxIsVsync;
	private boolean ctxIsResized;
	public String   ctxAppName;
	public String   ctxAppVersion;
	public int  	ctxWindowWidth;
	public int 		ctxWindowHeight;
	public boolean  ctxWindowFullscreen;
	
	/**
		Creates class core configuration with default engine and application
		settings and options.
		
		List with all CTX Engine settings a configuration to lunch. Each
		application, or game written on this engine can change this
		settings.
	*/
	public CoreConfigurations()
	{
		this.ctxEngineName = "CTXEngine";
		this.ctxEngineVersion = "0.1.20_5";
		this.ctxAppName = "unknown";
		this.ctxAppVersion = "unknown";
		this.ctxWindowFullscreen = false;
		this.ctxWindowWidth = this.ctxWindowFullscreen ? 1600 : 1280;
		this.ctxWindowHeight = this.ctxWindowFullscreen ? 900 : 720;
		this.ctxIsWindowed = !(ctxWindowFullscreen);
		this.ctxIsVsync = this.ctxWindowFullscreen;
		this.ctxIsResized = !(ctxWindowFullscreen) & true;
	}
	
	public CoreConfigurations setIsWindowed(boolean ctxIsWindowed) 
	{
		this.ctxIsWindowed = ctxIsWindowed;
		return this;
	}

	public CoreConfigurations setIsVsync(boolean ctxIsVsync) 
	{
		this.ctxIsVsync = ctxIsVsync;
		return this;
	}

	public CoreConfigurations setIsResized(boolean ctxIsResized) 
	{
		this.ctxIsResized = ctxIsResized;
		return this;
	}

	public CoreConfigurations setAppName(String ctxAppName) 
	{
		this.ctxAppName = ctxAppName;
		return this;
	}

	public CoreConfigurations setAppVersion(String ctxAppVersion)
	{
		this.ctxAppVersion = ctxAppVersion;
		return this;
	}

	public CoreConfigurations setWindowWidth(int ctxWindowWidth) 
	{
		this.ctxWindowWidth = ctxWindowWidth;
		return this;
	}

	public CoreConfigurations setWindowHeight(int ctxWindowHeight)
	{
		this.ctxWindowHeight = ctxWindowHeight;
		return this;
	}

	public CoreConfigurations setWindowFullscreen(boolean ctxWindowFullscreen)
	{
		this.ctxWindowFullscreen = ctxWindowFullscreen;
		return this;
	}

	@Override
	public String getEngineName() { return this.ctxEngineName; }
	
	@Override
	public String getEngineVersion() { return this.ctxEngineVersion; }
	
	@Override
	public String getAppName() { return this.ctxAppName; }

	@Override
	public String getAppVersion() { return this.ctxAppVersion; }
	
	@Override
	public int getWindowWidth() { return this.ctxWindowWidth; }
	
	@Override
	public int getWindowHeight() { return this.ctxWindowHeight; }
	
	@Override
	public boolean isFullscreen() { return this.ctxWindowFullscreen; }
	
	@Override
	public boolean isWindowed() { return this.ctxIsWindowed; }
	
	@Override
	public boolean isVSync() { return this.ctxIsVsync; }
	
	@Override
	public boolean isResized() { return this.ctxIsResized; }
};
