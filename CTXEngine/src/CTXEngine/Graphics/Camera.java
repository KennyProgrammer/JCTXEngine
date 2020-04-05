package CTXEngine.Graphics;

import org.joml.Matrix4f;

/**
 * A camera is such a device that allows us to see our scene on the
 * screen by transformation. The idea is that the camera does not
 * exist, it's just an illusion that displays the scene as we look
 * at it. For this, matrices are used, such as the projection matrix,
 * the viewing matrix, and the object / model matrix.
 * 
 * And yet, most importantly, we do not move the camera itself, since
 * it is always on [0,0]. We all-powerful move the whole world relative
 * to the camera through transformation.
 */

public abstract class Camera extends CTXEngine.Component.Object
{
	/**Check Is the camera orthographic or perspective.*/
	private boolean orthographic;
	
	public Camera()
	{
		super("Camera", false);
		this.orthographic = true;
	}
	
	public Camera(boolean orthographicIn)
	{
		this();
		this.orthographic = orthographicIn;
	}
		
	public void destroyCamera() {}
	
	/** Return the projection matrix.*/
	public abstract Matrix4f getProjectionMatrix();
	/** Return the view matrix.*/
	public abstract Matrix4f getViewMatrix();
	/** Return the view-projection matrix.*/
	public abstract Matrix4f getViewProjectionMatrix();
	
	/**
	 * Only return true if is orthographic camera.
	 */
	public final boolean isOrthographic()
	{
		return this.orthographic ? true : false;
	}
	
	/**
	 * Only return true if is perspective camera.
	 */
	public final boolean isPerspective()
	{
		return !this.orthographic ? true : false;
	}
}
