package CTXEngine.Component;

/**
 * Thats a not java.lang.Object is custom CTXEngine component object.
 * Base class for all objects Craftix Engine can reference.
 */
public abstract class Object extends java.lang.Object
{
	private static int globalId = 0;
	/**This is 'C' string style name of object.*/
	public String name;
	/**Instanced id of this object.*/
	private int instancedId;
	/**Check should the object be hidden from something or not*/
	private boolean isHidden;
	
	/**
	 * Create new engine object.
	 */
	public Object()
	{
		super();
		this.name = "object";
		this.isHidden = false;
		this.instancedId = globalId++;
	}
	
	/**
	 * Create new engine object.
	 */
	public Object(final String nameIn, boolean hidden)
	{
		this();
		this.name = nameIn;
		this.isHidden = hidden;
		this.instancedId = globalId++;
	}
	
	/**
	 * Return name of this object in 'C' style string. If you want convert
	 * it to 'C++' std::string use str::ctr();
	 */
	public final String getName()
	{
		return this.name;
	}
	
	/**
	 * Return the instanced id of this object.
	 */
	public final int getInstancedId()
	{
		return this.instancedId;
	}
	
	public final boolean isHidden()
	{
		return this.isHidden;
	}
}
