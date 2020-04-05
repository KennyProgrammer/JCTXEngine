package CTXEngine.Core.InputSystem;

import static org.lwjgl.glfw.GLFW.*;

import java.nio.DoubleBuffer;

import org.lwjgl.BufferUtils;

import CTXEngine.Core.CoreApp;
import CTXEngine.Core.CoreUtils.std_pair;

/**
 * This class represents input form window operation system.
 */
public class WinInput extends Input
{
	public WinInput() 
	{
		Input.setInstance(this);
	}
	
	/**
	 *	Returns true if key button is pressed.
	 */
	@Override
	protected boolean isKeyPressedImpl(int keyId) 
	{
		long window = CoreApp.get().getWindow().getSystemWindow(); //maybe static_cast
		int state = glfwGetKey(window, keyId);
		return state == GLFW_PRESS;
	}

	/**
	 *	Returns true if key button is released.
	 */
	@Override
	protected boolean isKeyReleasedImpl(int keyId) 
	{
		long window = CoreApp.get().getWindow().getSystemWindow(); //maybe static_cast
		int state = glfwGetKey(window, keyId);
		return state == GLFW_RELEASE;
	}

	/**
	 *	Simple key down event. When user press on button event is activated.
	 */
	@Override
	protected boolean isKeyDownImpl(int keyId)
	{
		long window = CoreApp.get().getWindow().getSystemWindow(); //maybe static_cast
		int state = glfwGetKey(window, keyId);
		return state == GLFW_REPEAT;
	}

	/**
	 *  Returns true if mouse button is pressed.
	 */
	@Override
	protected boolean isButtonPressedImpl(int buttonId)
	{
		long window = CoreApp.get().getWindow().getSystemWindow(); //maybe static_cast
		int state = glfwGetMouseButton(window, buttonId);
		return state == GLFW_PRESS;
	}

	/**
	 *  Returns true if mouse button is released.
	 */
	@Override
	protected boolean isButtonReleasedImpl(int buttonId)
	{
		long window = CoreApp.get().getWindow().getSystemWindow(); //maybe static_cast
		int state = glfwGetMouseButton(window, buttonId);
		return state == GLFW_RELEASE;
	}

	/**
	 * Returns true if mouse button is down.
	 */
	@Override
	protected boolean isButtonDownImpl(int buttonId)
	{
		long window = CoreApp.get().getWindow().getSystemWindow(); //maybe static_cast
		int state = glfwGetMouseButton(window, buttonId);
		return state == GLFW_REPEAT;
	}

	/**
	 * Return position of mouse on x axis from specific window.
	 */
	@Override
	protected float getMouseXImpl()
	{
		std_pair<Float, Float> mouse = this.getMousePosImpl();
		return mouse.getFirst(); //i think should be return x value.
	}

	/**
	 * Return position of mouse on y axis from specific window.
	 */
	@Override
	protected float getMouseYImpl()
	{
		std_pair<Float, Float> mouse = this.getMousePosImpl();
		return mouse.getSecond(); //i think should be return y value.
	}

	/**
	 *	Return position of mouse from specific window.
	 */
	@Override
	protected std_pair<Float, Float> getMousePosImpl()
	{
		long window = CoreApp.get().getWindow().getSystemWindow(); //maybe static_cast
		DoubleBuffer xPos, yPos;
		xPos = BufferUtils.createDoubleBuffer(1);
		yPos = BufferUtils.createDoubleBuffer(1);
		
		glfwGetCursorPos(window, xPos, yPos);
		
		return std_pair.of
				(
					(float)xPos.get(0), (float)yPos.get(0)
				);
	}

}
