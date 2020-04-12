package CTXEngine.Core;

import CTXEngine.Core.EventSystem.Event;
import CTXEngine.Core.LayerSystem.Layer;
import static CTXEngine.Core.SimplePrint.*;

/**
 * Scene contain the environments and menus of your game. Think of each unique
 * Scene file as a unique level. In each Scene, you place your
 * nvironments, obstacles, and decorations, essentially designin
 * and building your game in pieces.
 */
public abstract class Scene 
{
	public static String ASSET = "/Assets/";
	
	/**This varible check if this scene is initialized.*/
	public boolean initialized;
	/**Numeric id of this scene.*/
	public int id = 0;
	/**Name of this scene.*/
	public String name = "scene";
	/**Check if this scene is active or enable.*/
	private boolean active = true;
	/**If programmer needs custom update interface system set this to true. If this true
	than programmer takes responsibility for the operation of the code.*/
	private boolean customUpdateInterface = false;
	/**Show or not 'note / warn message'.*/
	private boolean customUpdateInterfaceNote = !customUpdateInterface ? false : true;
	
	public Scene() 
	{
		this.initialized = false;
	}
	
	/**
		Push input layer into this stack.
	*/
	public abstract void pushLayer(Layer layerIn);
	
	/**
		Push input overlay into this stack.
	*/
	public abstract void pushOverlay(Layer layerIn);
	
	/**
		Poll stack for each of event, and backward poll event, before it be
		destroyed.
	*/
	public abstract void pollStack(Event eventIn);
	
	/**
		Load this scene in to memory witch initialization other game objects
		and components.
	*/
	public void load()
	{
		CTX_ENGINE_INFO("Loading '" + name + "' scene...");
		return;
	}
	
	/**
		Unload this scene from memory i.e destroy all components and objects.
	*/
	public void unload()
	{
		CTX_ENGINE_INFO("Unloading '" + name + "' scene...");
		return;
	}
	
	/**
	 *	This is global update event which contains onUpdate, onRender etc. In other
	 *	words this event contains while loop and update this scene every frame when
	 *	window is active.
	 */
	public final Scene updateScene(Delta delta)
	{
		if (!this.customUpdateInterface)
		{
			if (this.active)
			{
				this.onUpdate(delta.getDeltaTime());
				this.onRender();
				this.onHandleInput();
			}
		}
		else
		{
			if (this.customUpdateInterfaceNote)
			{
				CTX_ENGINE_INFO("Note: '{0}' scene used custom update interface, and that means");
				CTX_ENGINE_INFO("if happens some error default scene helper messages can't help.");
			}
			
			this.updateScene();
		}
	
		return this;
	}
	
	/**
		This is custom version of update scene. If flag 'customUpdateInterface' true
		that this function must be overridden and realize. Otherwise user can't see
		everything on screen from this scene.
	 */
	protected abstract Scene updateScene();
	
	/**
	 * This event calls every frame (while loop), i.e updates scene.
	 */
	public abstract Scene onUpdate(float delta);
	
	/**
	 * This event calls every frame where need render some object. 
	 */
	protected abstract Scene onRender();
	
	/**
	 * This event handle all input from current window system i.e form mouse, keyboard,
	 * joystick.
	 */
	protected abstract Scene onHandleInput();
	
	/**
		This event calls every frame (while loop), i.e updates events.
		This method don't need to call, it need bind with CTX_BIND_EVENT_FN
		on some window class.
	*/
	public Scene onEvent(Event eventIn)
	{
		this.pollStack(eventIn);
		return this;
	}
	
	public void setCustomUpdateInterface(boolean customUpdateInterface) 
	{
		this.customUpdateInterface = customUpdateInterface;
	}
	
	/**
	 * Enable input scene. Make it active and workable.
	 */
	public static final void enableScene(Scene sceneIn)
	{
		sceneIn.active = true;
	}
	
	/**
	 * Disable input scene. Make it non active and non-workable.
	 */
	public static final void disableScene(Scene sceneIn)
	{
		sceneIn.active = false;
	}
	
	/**
	 * Enable input scene. Make it active and workable, and load it.
	 */
	public static final void enableAndLoadScene(Scene sceneIn)
	{
		Scene.enableScene(sceneIn);
		if (!sceneIn.initialized)
		{
			sceneIn.load();
		}
	}
	
	/**
	 * Disable input scene. Make it non active and non-workable, and
	 * unload it.
	 */
	public static final void disableAndUnloadScene(Scene sceneIn)
	{
		Scene.disableScene(sceneIn);
		if (sceneIn.initialized)
		{
			sceneIn.unload();
		}
	}
}
