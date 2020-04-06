package CTXEngine.Core;

import CTXEngine.Core.Delta;

public interface CoreAppClientImpl 
{
	/*This method be initialize all core engine classes after initialized the scene.*/
	void initClient();
	/*This method will be use already intitalized objects and update it.*/
	void onUpdateClient(Delta coreTimeIn);
	/*This method will be accept events for client side and use it.*/
	void onEventClient();
	/*This method will be use already intitalized objects and render it.*/
	void onRenderImGuiClient();
	/*This method will be delete all intitalized client game stuff.*/
	void destroyClient();
	
}
