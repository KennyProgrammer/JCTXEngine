package CTXEngine.Core;

/**
 * Delta time describes the time difference between the previous
 * frame that was drawn and the current frame in modern game engines
 * like Unity or Unreal, but CTXEngine also be used it.
 * 
 * @author Kenny (Danil Dukhovenko)
 */
public class Delta 
{
	/**To summaries, delta time is usually a variable calculated for us in modern
	game engines that stores the time difference between the last and current frame. */
	private float deltaTime = 0.0f;
	
	Delta(float timeIn)
	{
		this.deltaTime = timeIn;
	}
	
	Delta() { this(0); }
	
	/**
		Set delta time value.
	 */
	public final void setDetlaTime(float valueIn)
	{
		this.deltaTime = valueIn;
	}
	
	/**
		Return delta time instance.
	*/
	public final float getDeltaTime() 
	{
		return this.deltaTime;
	}
	
	/**
		Return delta time instance in seconds.
	*/
	final float getSeconds()
	{
		return this.getDeltaTime();
	}
	
	/**
		Return delta time in milliseconds.
	*/
	final float getMilliseconds()
	{
		return this.getDeltaTime()* 1000.0f;
	}
}
