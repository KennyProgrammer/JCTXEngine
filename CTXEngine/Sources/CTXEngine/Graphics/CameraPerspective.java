package CTXEngine.Graphics;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import CTXEngine.Core.CoreApp;

/**
 * Thanks you ThinMatrix)))) For everything!
 */
public class CameraPerspective extends Camera
{
	class Frustum
	{
		public Frustum() {}
		public void destroyFrustum() {}
	};
	
	/**Position x, y, z of this camera.*/
	public Vector3f position = new Vector3f( 0.0f, 0.0f, 0.0f );
	/**Rotation rX, rY, rZ of this camera.*/
	public Vector3f rotation = new Vector3f(0.0f, 0.0f, 0.0f);
	/**This is projection matrix for this camera.*/
	public Matrix4f projectionMatrix;
	/**This is view matrix for this ortho matrix.*/
	public Matrix4f viewMatrix;
	/**This matrix transforms for world space to homogeneous space.*/
	public Matrix4f viewProjectionMatrix;
	/*Width / Height.*/
	float aspectRatio;
	
	public CameraPerspective(Frustum frustim) 
	{
		super(false);
	}
	
	public CameraPerspective(float l, float r, float b, float t)
	{
		super(false);
		this.aspectRatio = ((float) CoreApp.get().getConfigurations().getWindowWidth() / (float) CoreApp.get().getConfigurations().getWindowHeight());
		this.projectionMatrix = new Matrix4f().perspective((float) Math.toRadians(45.0f), aspectRatio, 0.1f, 1000.f);
		this.viewMatrix = new Matrix4f().identity();
		this.viewProjectionMatrix = this.projectionMatrix.mul(this.viewMatrix);
	}
	
	public void calcaulateViewMatrix()
	{
		this.viewMatrix = new Matrix4f().translate(this.position);
		this.viewMatrix = new Matrix4f().rotate((float) Math.toRadians(this.rotation.x), new Vector3f(1, 0, 0))
				                        .rotate((float) Math.toRadians(this.rotation.y), new Vector3f(0, 1, 0))
				                        .rotate((float) Math.toRadians(this.rotation.z), new Vector3f(0, 0, 1));
		this.viewMatrix.invert();
		this.viewProjectionMatrix = this.projectionMatrix.mul(this.viewMatrix);
	}
	
	public void calcaulateProjectionMatrix()
	{

	}
	
	/**
	 * Return the instance of projection matrix.
	 */
	@Override
	public final Matrix4f getProjectionMatrix()
	{
		return this.projectionMatrix;
	}
	
	/*
		Return the instance of view matrix.
	*/
	@Override
	public final Matrix4f getViewMatrix()
	{
		return this.viewMatrix;
	}
	
	/*
		Return the instance of view-projection matrix.
	*/
	@Override
	public final Matrix4f getViewProjectionMatrix()
	{
		return this.viewProjectionMatrix;
	}
	
	public final void setPosition(final Vector3f positionIn)
	{
		this.calcaulateViewMatrix();
		this.position = positionIn;
	}

	public final void setRotation(final Vector3f rotationIn)
	{
		this.calcaulateViewMatrix();
		this.rotation = rotationIn;
	}
}
