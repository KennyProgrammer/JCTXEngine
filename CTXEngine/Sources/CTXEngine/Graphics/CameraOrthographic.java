package CTXEngine.Graphics;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class CameraOrthographic extends Camera
{
	/**
	 * This is box struct.
	 */
	class Box
	{
		/*This is left side of ortho box.*/
		float left;
		/*This is right side of ortho box.*/
		float right;
		/*This is bottom side of ortho box.*/
		float bottom;
		/*This is top side of ortho box.*/
		float top;
	
		/*
		 * *Create new box.
		 */
		public Box(float l, float r, float b, float t)
		{
			this.left = l;
			this.right = r;
			this.bottom = b;
			this.top = t;
		}
	
		/*
			Construct this box.
		*/
		public void set(float l, float r, float b, float t)
		{
			this.left = l;
			this.right = r;
			this.bottom = b;
			this.top = t;
		}
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
	
	/**
	 * Default constructor.
	 */
	public CameraOrthographic() 
	{
		super(true);
	}
	
	/**
	 * Create new orthographic camera.
	 */
	public CameraOrthographic(float l, float r, float b, float t)
	{
		super(true);
		this.projectionMatrix = MatrixHelper.calculateOrthographicProjectionMatrix(l, r, b, t);
		this.viewMatrix = new Matrix4f().identity();
		this.viewProjectionMatrix = this.projectionMatrix.mul(this.viewMatrix);
		this.projectionMatrix.m33(1.0f);
	}
	
	/**
	 * Create new orthographic camera.
	 */
	public CameraOrthographic(Box boxIn)
	{
		this(boxIn.left, boxIn.right, boxIn.bottom, boxIn.top);
	}
	
	/**
	 * Default destructor.
	 */
	public void destroyCameraOrthographic()
	{
		this.destroyCamera();
	}
	
	/**
	 * Calculate / create view matrix. View matrix is just model matrix, but
	 * inverted and not have scale, because camera don't need to be scaled.
	 * 
	 * For note: Model matrix is translation, rotation, and scaling matrices
	 * multiply together.
	 */
	public void calcaulateViewMatrix()
	{
		this.viewMatrix = MatrixHelper.calculateViewMatrix2D(this.position, this.rotation.z);
		this.viewProjectionMatrix = this.projectionMatrix.mul(this.viewMatrix);
	}
	
	public void calcaulateViewMatrixTemp()
	{
		this.viewMatrix = MatrixHelper.calculateViewMatrix(this.position, this.rotation.x, this.rotation.y, this.rotation.z);
		this.viewProjectionMatrix = this.projectionMatrix.mul(this.viewMatrix);
	}
	
	/**
	 * Calculate / update / recreate camera ortho projection matrix.
	 * 
	 * For note: Projection matrix is control output viewport box. In other
	 * words convert from eye space to clip space.
	 */
	public void calcaulateProjectionMatrix(float l, float r, float b, float t)
	{
		this.projectionMatrix = MatrixHelper.calculateOrthographicProjectionMatrix(l, r, b, t);
		this.viewProjectionMatrix = this.projectionMatrix.mul(this.viewMatrix);
	}
	
	/**
	 * Set the position of current camera, and reset the matrix.
	 */
	public final void setPosition(final Vector3f positionIn)
	{
		this.calcaulateViewMatrixTemp();
		this.position = positionIn;
	}

	/**
	 * Set the rotation of current camera, and reset the matrix.
	 */
	public final void setRotation(final Vector3f rotationIn)
	{
		this.calcaulateViewMatrixTemp();
		this.rotation = rotationIn;
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
	
	/**
	 * Return position of this camera.
	 */
	public final Vector3f getPosition()
	{
		return this.position;
	}
	
	/**
	 * Return rotation of this camera.
	 */
	public final Vector3f getRotation()
	{
		return this.rotation;
	}
	
}
