package CTXEngine.Core.LayerSystem;

import java.util.ArrayList;


/**
 * Layer stack its basically stack of layers. If you play on 'Stack'
 * game, you actually understand what i mean. In other words its
 * group of layer's pushed in one stack. In other other words its
 * magic box where stored pushed layer's.
 * 
 * BUGGING CLASS. REWRITING FROM C++
 */
public class LayerStack 
{
	/**This is list with all layers in this stack.*/
	public ArrayList<Layer> layers;
	/**This is actually layer iterator.*/
	
	public LayerStack() 
	{
		this.layers = new ArrayList<Layer>();
	}
	
	/**
	 * Push input layer into this stack.
	 */
	public void pushLayer(Layer layerIn)
	{
		this.layers.add(layerIn);
		//this.layers.emplace(this.layers.get(0) + this.layerItrIndex, layerIn);
		//this.layerItrIndex++;
	}
	
	/**
	 * Push input overlay into this stack.
	 */
	public void pushOverlay(Layer overlayIn)
	{
		
	}
	
	/**
	 * Pop input layer from this stack.
	 */
	public void popLayer(Layer layerIn)
	{
		//Iterator<Layer> 
		
		//auto itr = std::find(this->layers.begin(), this->layers.end(), layerIn);
		//if (itr != this->layers.end())
		//{
		//	this->layers.erase(itr);
		//	this->layerItrIndex--;
		//}
	}
	
	/**
	 * Pop input overlay from this stack.
	 */
	public void popOverlay(Layer overlayIn) 
	{

	}
	
	public int begin()
	{
		return this.layers.indexOf(layers.get(0));
	}
	
	public int end()
	{
		return this.layers.indexOf(layers.get(layers.size() - 1));
	}
	
}
