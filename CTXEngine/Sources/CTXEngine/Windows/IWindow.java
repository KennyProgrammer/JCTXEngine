package CTXEngine.Windows;

import CTXEngine.Core.CoreException;

/**
	Window is provider, to create a context of graphical API, such
	a OpenGl, or Vulcan, or DirectX3D.
*/
public interface IWindow 
{
	/*Create new window.*/
	static IWindow ñreate() { return null; }
	/**Return the current os window, like windows GLFW of mac .. or linux ..*/
	abstract long getSystemWindow();
	/**Initialize the specific window API for operation system.*/
	abstract void initWinSystemAPI();
	/**Create new window context.*/
	abstract void createWindow() throws CoreException;
	/**Update created window every frame.*/
	abstract void updateWindow() throws CoreException;
	/**Destroy window and terminate window system.*/
	abstract void destroyWindow() throws CoreException;
	/**Handle input from mouse, keyboard or joystick.*/
	abstract void handleInput();
	/**Set vertical synchronization for window.*/
	abstract void setVsync(boolean vSync);
	/**Set window icon.*/
	abstract void setIcon(String path16, String path32);
	/**Set close requested trigger and stop / start update the window.*/
	abstract void setCloseRequested(boolean closeRequested);
	/**Return the title of current window.*/
	abstract String getTitle();
	/**Return the width of current window.*/
	abstract int getWidth();
	/**Return the height of current window.*/
	abstract int getHeight();
	/**Check if this window is close or not.*/
	abstract boolean isCloseRequested();
	/**Check if this window in fullscreen.*/
	abstract boolean isFullscreen();
	/**Check if this window in resized.*/
	abstract boolean isResized();
}
