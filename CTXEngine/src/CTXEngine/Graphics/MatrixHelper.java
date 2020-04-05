package CTXEngine.Graphics;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

/**
 * This class need to calculate in-engine matrices.
 */
public final class MatrixHelper 
{
	public static Matrix4f createModelM(Vector3f position, Vector3f rotation, Vector3f scale)
	{
		return new Matrix4f()
				.translate(position)
				.rotate((float) Math.toRadians(rotation.x), new Vector3f(1, 0, 0))
				.rotate((float) Math.toRadians(rotation.y), new Vector3f(0, 1, 0))
				.rotate((float) Math.toRadians(rotation.z), new Vector3f(0, 0, 1))
				.scale(scale);
				
	}
	
	/**
	 * Calculate model matrix / transformation matrix of 3d object, just
	 * multiply translation matrix (vector 3d) on rotation matrix (vector 3d)
	 * on scaling matrix (vector 3d), and return result to model matrix.
	 */
	public static Matrix4f calculateModelMatrix(Vector3f position, Vector3f rotation, Vector3f scale)
	{
		Matrix4f identityMatrix = new Matrix4f().identity();
	
		Matrix4f modelMatrix = new Matrix4f(identityMatrix);
		Matrix4f translateMatrix = new Matrix4f().translate(position);
		Matrix4f rotationMatrix = 
				      new Matrix4f().rotate((float) Math.toRadians(rotation.x), new Vector3f(1, 0, 1))
				 .mul(new Matrix4f().rotate((float) Math.toRadians(rotation.y), new Vector3f(0, 1, 0))
				 .mul(new Matrix4f().rotate((float) Math.toRadians(rotation.z), new Vector3f(0, 0, 1))));
		Matrix4f scaleMatrix = new Matrix4f().scale(scale);
		
		if (scale.x == 0 && scale.y == 0 && scale.z == 0)
		{
			modelMatrix = translateMatrix.mul(rotationMatrix);
			return modelMatrix;
		} else {
			modelMatrix = translateMatrix.mul(rotationMatrix).mul(scaleMatrix);
			return modelMatrix;
		}
	}
	
	/**
	 * Calculate model matrix / transformation matrix of 2d object, just
	 * multiply translation matrix (vector 2d) on scaling matrix (vector 2d),
	 * and return result to model matrix.
	 */
	public static Matrix4f calculateModelMatrix2D(Vector3f position, float rotation, Vector2f scale)
	{
		Matrix4f identityMatrix = new Matrix4f().identity();
		
		Matrix4f modelMatrix = new Matrix4f(identityMatrix);
		Matrix4f translateMatrix = new Matrix4f().translate(position);
		Matrix4f rotationMatrix = new Matrix4f().rotate((float) Math.toRadians(rotation), new Vector3f(0, 0, 1));
		Matrix4f scaleMatrix = new Matrix4f().scale(scale.x, scale.y, 0.0f);
		
		if (scale.x == 0 && scale.y == 0)
		{
			modelMatrix = translateMatrix.mul(rotationMatrix);
			return modelMatrix;
		} else {
			modelMatrix = translateMatrix.mul(rotationMatrix).mul(scaleMatrix);
			return modelMatrix;
		}
	}
	
	/**
	 * Calculate model matrix / transformation matrix of 2d object, just
	 * multiply translation matrix (vector 2d) on scaling matrix (vector 2d),
	 * and return result to model matrix.
	 */
	public static Matrix4f calculateModelMatrix2D(Vector3f position, Vector2f scale)
	{
		Matrix4f identityMatrix = new Matrix4f().identity();
		
		Matrix4f modelMatrix = new Matrix4f(identityMatrix);
		Matrix4f translateMatrix = new Matrix4f().translate(position);
		Matrix4f scaleMatrix = new Matrix4f().scale(scale.x, scale.y, 0.0f);
		
		if ((scale.x == 0 && scale.y == 0) == false)
		{
			modelMatrix = translateMatrix.mul(scaleMatrix);
			return modelMatrix;
		}
		
		return modelMatrix;
	}
	
	public static Matrix4f calculateModelMatrix2D(Vector3f position)
	{
		return new Matrix4f().translate(position.x, position.y, position.z);
	}

	/**
	 * Calculate view matrix / eye matrix / camera matrix. Its basically
	 * inverted model matrix without scale.
	 */
	public static Matrix4f calculateViewMatrix(Vector3f position, float pitch, float yaw, float roll)
	{
		return new Matrix4f().invert(MatrixHelper.calculateModelMatrix(position, new Vector3f(pitch, yaw, roll), new Vector3f()));
	}
	
	/**
	 * Calculate view matrix / eye matrix / camera matrix. Its basically
	 * inverted model matrix without scale.
	 */
	public static Matrix4f calculateViewMatrix2D(Vector3f position, float rotation)
	{
		return new Matrix4f().invert(MatrixHelper.calculateModelMatrix2D(position, rotation, new Vector2f()));
	}
	
	/**
	 * Calculate orthographic projection matrix, for 2d usage.
	 */
	public static Matrix4f calculateOrthographicProjectionMatrix(float left, float right, float bottom, float top)
	{
		float zNearDistance = -1.0f, zFarDistance = 1.0F;
		return MatrixHelper.calculateOrthographicProjectionMatrix(left, right, bottom, top, zNearDistance, zFarDistance);
	}
	
	/**
	 * Calculate orthographic projection matrix, for 2d usage.
	 */
	public static Matrix4f calculateOrthographicProjectionMatrix(float left, float right, float bottom, float top, float zNearDistance,
			float zFarDistance)
	{
		return new Matrix4f().ortho(left, right, bottom, top, zNearDistance, zFarDistance);
	}
	
	/**
	 * Calculate perspective projection matrix, with depth for
	 * 3d usage.
	 */
	public static Matrix4f calculatePerspectiveProjectionMatrix(float fieldOfView, float aspectRatio,
		float zNearDistance, float zFarDistance)
	{
		return new Matrix4f().perspective(fieldOfView, aspectRatio, zNearDistance, zFarDistance);
	}
	
	/**
	 * Calculate perspective projection matrix with default variables, with depth for
	 * 3d usage.
	 */
	public static Matrix4f calculatePerspectiveProjectionMatrix()
	{
		return new Matrix4f().perspective(70.0F, 1.777777779F, 1000.0F, 1.0F);
	}
}
