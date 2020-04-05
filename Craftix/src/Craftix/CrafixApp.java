package Craftix;

import static CTXEngine.Core.InputSystem.InputCodes.*;

import CTXEngine.Core.CoreApp;
import CTXEngine.Core.CoreException;
import CTXEngine.Core.Delta;
import CTXEngine.Core.Scene;
import CTXEngine.Core.InputSystem.Input;
import Craftix.Scenes.ExampleScene;

public class CrafixApp extends CoreApp
{
	public Scene scene;
	
	public CrafixApp(CoreConfigurations configurations) throws CoreException 
	{
		super(configurations);
		
		getConfigurations().ctxAppName = "Craftix";
		getConfigurations().ctxAppVersion = "0.0.1";
		getConfigurations().ctxIsWindowed = true;
		getConfigurations().ctxIsVsync = true;
		getConfigurations().ctxIsResized = true;
		getConfigurations().ctxWindowFullscreen = false;
		getConfigurations().ctxWindowWidth = 1280;
		getConfigurations().ctxWindowHeight = 720;
		
		this.scene = new ExampleScene();
		
	}

	@Override
	public void initClient() 
	{
		if(!this.scene.initialized) this.scene.load();
	}
	
	@Override
	public void onUpdateClient(Delta arg0) 
	{
		if (Input.isKeyPressed(CTX_KEY_ENTER))
		{
			CoreApp.get().isRunning = false;
		}
		
		//this.scene.onUpdate(arg0);
		if(this.scene.initialized) this.scene.updateScene(arg0);
	}
	
	@Override
	public void destroyClient() 
	{
		if(this.scene.initialized) this.scene.unload();
	}

	@Override
	public void onEventClient() 
	{
	
	}

	@Override
	public void onRenderImGuiClient() 
	{
	
	}
}
