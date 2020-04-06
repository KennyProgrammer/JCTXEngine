package CTXEngine.Core.LayerSystem;

import CTXEngine.Core.Delta;
import CTXEngine.Core.EventSystem.Event;

/*
 *	Layer its like scene i.e can be modified, updated or process some event,
 *	but one difference, its that this layer can be switch to down layer or
 *	top layer. Scene can't do it.
 */
public abstract class Layer 
{
	protected String debugName;
	
	public Layer(String name) 
	{
		this.debugName = name;
	}
	
	/**Attach the layer and can me modified in runtime or in static mode.*/
	abstract void onAttach();
	/**Detach the layer and can't me modified in runtime, only in static mode.*/
	abstract void onDetach();
	/**Update this current layer, such a print message, draw cube or move object.*/
	abstract void onUpdate(Delta coreTime);
	/**This event calls every frame where need render some object. */
	abstract void onRender();
	/**Run the events.*/
	abstract void onEvent(Event eventIn);
	
	/**
	 * 	Set the name of this layer.
	 */
	public void setName(String nameIn)
	{
		this.debugName = nameIn;
	}

	/**
	 * Return the name of this layer.
	 */
	public final String getName()
	{
		return this.debugName;
	};
}
