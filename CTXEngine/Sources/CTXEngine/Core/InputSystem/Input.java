package CTXEngine.Core.InputSystem;

import CTXEngine.Core.CoreUtils.std_pair;

/**
 * This class is responsible for entering different devices on
 * different platforms, for example with a mouse, keyboard,
 * joystick, or sensor.
 */
public abstract class Input 
{
	/*This is static instance of input.*/
	private static Input instance;
	
	public Input() { instance = this; }
	
	/**
	 * This is implementation of {@link isKeyDown}
	 */
	protected abstract boolean isKeyPressedImpl(int keyId);
	
	/**
	 * This is implementation of {@link isKeyReleased}
	 */
	protected abstract boolean isKeyReleasedImpl(int keyId);
	
	/**
	 * This is implementation of {@link isKeyDown}
	 */
	protected abstract boolean isKeyDownImpl(int keyId);
	
	/**
	 * This is implementation of {@link isButtonPressed}
	 */
	protected abstract boolean isButtonPressedImpl(int keyId);
	
	/**
	 * This is implementation of {@link isButtonReleased}
	 */
	protected abstract boolean isButtonReleasedImpl(int keyId);
	
	/**
	 * This is implementation of {@link isButtonDown}
	 */
	protected abstract boolean isButtonDownImpl(int keyId);
	
	/**
	 * This is implementation of {@link getMouseX}
	 */
	protected abstract float getMouseXImpl();
	
	/**
	 * This is implementation of {@link getMouseY}
	 */
	protected abstract float getMouseYImpl();
	
	/**
	 * This is implementation of {@link getMousePos}
	 */
	protected abstract std_pair<Float, Float> getMousePosImpl();

	/**
		Simple key down event. When user press on button event is activated.
	*/
	public static boolean isKeyDown(int keyId)
	{
		return instance.isKeyDownImpl(keyId);
	}
	
	/**
	 *	Returns true if key button is pressed.
	 */
	public static boolean isKeyPressed(int keyId)
	{
		return instance.isKeyPressedImpl(keyId);
	}
	
	/**
	 *	Returns true if key button is released.
	 */
	public static boolean isKeyReleased(int keyId)
	{
		return instance.isKeyReleasedImpl(keyId);
	}
	
	/**
	 *	Returns true if mouse button is down.
	 */
	public static boolean isButtonDown(int buttonId)
	{
		return instance.isButtonDownImpl(buttonId);
	}
	
	/**
	 *	Returns true if mouse button is pressed.
	 */
	public static boolean isButtonPressed(int buttonId)
	{
		return instance.isButtonPressedImpl(buttonId);
	}
	
	/**
	 *	Returns true if mouse button is released.
	 */
	public static boolean isButtonReleased(int buttonId)
	{
		return instance.isButtonReleasedImpl(buttonId);
	}
	
	/**
	 *	Return position of mouse on x axis from specific window.
	 */
	public static float getMouseX()
	{
		return instance.getMouseXImpl();
	}
	
	/**
	 *	Return position of mouse on y axis from specific window.
	 */
	public static float getMouseY()
	{
		return instance.getMouseYImpl();
	}
	
	/**
	 *	Return position of mouse from specific window.
	 */
	public static std_pair<Float, Float> getMousePos()
	{
		return instance.getMousePosImpl();
	}	
	
	public static void setInstance(Input instance) 
	{
		Input.instance = instance;
	}
	
}	
