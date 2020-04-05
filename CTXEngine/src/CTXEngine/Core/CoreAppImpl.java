package CTXEngine.Core;

public interface CoreAppImpl 
{
	/*This method will actual run the CTXEngine.*/
	void run() throws CoreException;
	/*This method will shutdwon the CTXEngine.*/
	void shutdown();
	/*This method will be render global stuff, like I'm gui*/
	void onRenderGlobal();
	/*This method be initialize all core engine classes.*/
	void initCoreEngine();
	/*This method will be use already intitalized objects and update it.*/
	void onUpdate() throws CoreException;
	/*This method will be delete all intitalized game engine stuff.*/
	void destroy();
	/*This method will dispatch the events.*/
    void onEvent();
}
