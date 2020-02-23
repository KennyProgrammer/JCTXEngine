package com.kenny.craftix.client.entity;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import com.kenny.craftix.client.entity.player.EntityPlayer;
import com.kenny.craftix.client.renderer.WorldRenderer;
import com.kenny.craftix.client.scenes.WorldScene;
import com.kenny.craftix.init.EntityInit;
import com.kenny.craftix.utils.Timer;
import com.kenny.craftix.world.World;
import com.kenny.craftix.world.terrain.Terrain;

public class EntityCamera 
{
	/**Previous position on X axis.*/
	public float prevX;
	/**Previous position on Y axis.*/
	public float prevY;
	/**Previous position on Z axis.*/
	public float prevZ;
	/**This varible help us to zoom the camera.*/
	private float distanceFromPlayer;
	/**This varible help us to change angle around the player.*/
	private float angleAroundPlayer;
	/**This is a position of our camera (X, Y, Z). If camara connection to player,
	 * then position camera will be a position of the player./
	 */
	private Matrix4f viewMatrix = new Matrix4f();
	private Vector3f position = new Vector3f(0, 20, 15);
	/**This is a rotation camera of each axis*/
	private float pitch;
	/**This is how max left or right is camera angle*/
	public float yaw;
	/**Its its how much its tilted to one side*/
	public float roll;
	/**Enable or disable base collision with terrain.*/
	private boolean useBaseCollision;
	/**Terrain collision height.*/
	protected float terrainHeight;
	private boolean isRotateWhenCloseToGround = true;
	public static boolean isFirstPersonCamera;
	public static boolean isThridPersonCamera;
	public static boolean isThridPersonCameraFace;
	public WorldScene worldScene;
	private EntityPlayer player;
	@SuppressWarnings("unused")
	private EntityInit playerInit;
	public Timer timer = new Timer();
	
	/**
	 * Setup base camera component connected to player object.
	 */
	public EntityCamera(EntityPlayer player) 
	{
		this.player = player;
		if(!WorldScene.isLoadWorld)
		{
			isFirstPersonCamera = true;
			isThridPersonCameraFace = false;
			isThridPersonCamera = false;
		}
		this.setCameraPlayerSettings();
	}
	
	/**
	 * Setup base camera component.
	 */
	public EntityCamera(float x, float y, float z, WorldScene worldSceneIn) 
	{
		this.worldScene = worldSceneIn;
		this.setPosition(x, y, z);
	}
	
	/**
	 * This is first and thrid person camera's settings for player.
	 */
	public void setCameraPlayerSettings()
	{
		if(isFirstPersonCamera)
		{
			this.distanceFromPlayer = 5f;
			this.angleAroundPlayer = 0f;
			this.pitch = 5f;
			this.yaw = 0f;
			this.roll = 0f;
		}
		if(isThridPersonCamera)
		{
			this.distanceFromPlayer = 15f;
			this.angleAroundPlayer = 0f;
			this.pitch = 25f;
			this.yaw = 0f;
			this.roll = 0f;
		}
		if(isThridPersonCameraFace)
		{
			this.distanceFromPlayer = 15f;
			this.angleAroundPlayer = 180f;
			this.pitch = 25f;
			this.yaw = 0f;
			this.roll = 0f;
		}
	}
	
	/**
	 * Move standard camera on 3d space (X,Y,Z)
	 */
	public void moveCamera(float forward, float backward, float right, 
			float left, float fly, float down, World worldIn)
	{
		Terrain terrain = worldIn.getTerrain();
		if(this.useBaseCollision)
		{
			this.terrainHeight = terrain.getHeightOfTerrain(this.getPosition().x, 
					this.getPosition().z);
			
			if(Keyboard.isKeyDown(Keyboard.KEY_SPACE))
			{	
				this.position.y += fly;
			}
			else
			{
				if(this.getPosition().y <= this.terrainHeight + 4F)
				{
					this.position.y = this.terrainHeight + 2F;
				}
			}	
		}
		else
		{
			if(Keyboard.isKeyDown(Keyboard.KEY_SPACE))
			{	
				this.position.y += fly;
			}
			
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_W))
		{
			this.position.z -= forward;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_D))
		{
			this.position.x += backward;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_A))
		{
			this.position.x -= left;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_S))
		{
			this.position.z += right;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
		{
			this.position.y -= down;
		}
		
		this.cameraCollisionXZ();
		this.prevX = this.getPosition().x;
		this.prevY = this.getPosition().y;
		this.prevZ = this.getPosition().z;
		
		this.yaw = 0;
		
		float y = 100;
		float angleChange = Mouse.getDX() * 0.3f;
		y -= angleChange;
		this.roll += y * this.timer.getFrameTimeSeconds();
	}
	
	/**
	 * Easy way to move camera. This just use a speed value.
	 * 
	 * @param speedIn - speed camera on all world sides.
	 */
	public void moveCamera(float speedIn, World worldIn)
	{
		this.moveCamera(speedIn, speedIn, speedIn, speedIn, speedIn, speedIn, worldIn);
	}
	
	/**
	 * Collides camera with another game objects on Z and X axis.
	 */
	public void cameraCollisionXZ()
	{
		for(GameObject object : this.worldScene.objects)
		{
			if(object.getPhysicalObject())
			{
				if(Math.abs(object.getPositionVector().x - this.getPosition().x) <= object.getScale() + 0.8f &&
						Math.abs(object.getPositionVector().y - this.getPosition().y) <= object.getScale() + 0.8f &&
							Math.abs(object.getPositionVector().z - this.getPosition().z) <= object.getScale() + 0.8f)
				{
					this.getPosition().x = this.prevX;
					this.getPosition().y = this.prevY;
					this.getPosition().z = this.prevZ;
				}
			}		
		}
	}
	
	/**
	 * Move camera's witch player. Called in "!Display.isCloseRequiested" state.
	 */
	public void moveCamera()
	{
		this.moveCameraThridPerson();
		this.moveCameraFirstPerson();
		this.moveCameraThridPersonFace();
	}
	
	private void moveCameraThridPerson()
	{
		if(isThridPersonCamera)
		{
			this.calculateZoomThridPerson();
			this.calculatePitchThridPerson();
			this.calculateAngleAroundPlayerThridPerson();
			float horizontalDistance = calcutalteHorizontalDistance();
			float verticalDistance  =  10;//calculateVerticalDistance();
			this.calculateCameraPosition(horizontalDistance, verticalDistance);
			this.yaw = 180 - (this.player.getRotY() + angleAroundPlayer);
		}
	}
	
	private void moveCameraThridPersonFace()
	{
		if(isThridPersonCameraFace)
		{
			this.calculateZoomThridPerson();
			this.calculatePitchThridPerson();
			this.calculateAngleAroundPlayerThridPersonFace();
			float horizontalDistance = calcutalteHorizontalDistance();
			float verticalDistance  =  10;//calculateVerticalDistance();
			this.calculateCameraPosition(horizontalDistance, verticalDistance);
			this.yaw = 180 - (this.player.getRotY() + angleAroundPlayer);
		}
	}
	
	private void moveCameraFirstPerson()
	{
		if(isFirstPersonCamera)
		{
			this.calculateZoomFirstPerson();
			this.calculatePitchFirstPerson();
			this.calculateAngleAroundPlayerFirstPerson();
			float horizontalDistance = -1;
			float verticalDistance = 6;
			this.calculateCameraPosition(horizontalDistance, verticalDistance);
			this.yaw = 180 - (this.player.getRotY() + angleAroundPlayer);
		}
	}
	
	
	/**
	 * This method is responsible for regulating the movement of the first 
	 * and thrid person camera relative to the player.
	 */
	
	private void calculateZoomThridPerson()
	{
		if(isRotateWhenCloseToGround)
		{
			float zoomLevel = Mouse.getDWheel() * 0.1f;
			this.distanceFromPlayer -= zoomLevel;
			if(distanceFromPlayer < 15)
			{
				distanceFromPlayer = 15;
			}
			else if(distanceFromPlayer > 25)
			{
				distanceFromPlayer = 25;
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_F9))
			{
				distanceFromPlayer = 25;
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_F10))
			{
				distanceFromPlayer = 15;
			}
			
		}
	}
	
	private void calculateZoomFirstPerson()
	{
		if(isRotateWhenCloseToGround)
		{
			float zoomLevel = Mouse.getDWheel() * 0.1f;
			this.distanceFromPlayer -= zoomLevel;
			if(distanceFromPlayer < 5)
			{
				distanceFromPlayer = 5;
			}
			else if(distanceFromPlayer > 5)
			{
				distanceFromPlayer = 5;
			}
			
		}
	}
	
	/**
	 * This method is responsible for adjusting the first and thrid person
	 * camera tilt relative to the player.
	 */
	private void calculatePitchThridPerson()
	{
		if(Mouse.isButtonDown(1))
		{
			float pitchChange = Mouse.getDY() * 0.1f;
			this.pitch -= pitchChange;
			if(pitch < 0)
			{
				pitch = 0;
			}
			else if(pitch > 40)
			{
				pitch = 40;
			}
		}
	}
	
	private void calculatePitchFirstPerson()
	{
		if(Mouse.isButtonDown(1))
		{
			float pitchChange = Mouse.getDY() * 0.1f;
			this.pitch -= pitchChange;
			if(pitch < -190) //-15
			{
				pitch = -15;
			}
			else if(pitch > 25)
			{
				pitch = 25;
			}
		}
	}
	
	/**
	 * This method is responsible for turning the camera around the player.
	 */
	private void calculateAngleAroundPlayerThridPerson()
	{
		float angleChange = Mouse.getDX() * 0.3f;
		this.angleAroundPlayer -= angleChange;
		//this.player.currentTurnSpeed = -this.player.getEntityTurnSpeed();
		if(this.angleAroundPlayer > 100)
		{
			this.angleAroundPlayer = 100;
		}
		if(this.angleAroundPlayer < -100)
		{
			this.angleAroundPlayer = -100;
		}
	}
	
	private void calculateAngleAroundPlayerThridPersonFace()
	{
		float angleChange = Mouse.getDX() * 0.3f;
		this.angleAroundPlayer -= angleChange;
		if(this.angleAroundPlayer <= 100)
		{
			this.angleAroundPlayer = 100;
		}
		if(this.angleAroundPlayer >= 300)
		{
			this.angleAroundPlayer = 300;
		}
	}
	
	private void calculateAngleAroundPlayerFirstPerson()
	{
		float angleChange = Mouse.getDX() * 0.2f;
		this.angleAroundPlayer -= angleChange;
		if(this.angleAroundPlayer > 90)
		{
			this.angleAroundPlayer = 90;
		}
		if(this.angleAroundPlayer < -90)
		{
			this.angleAroundPlayer = -90;
		}
	}
	
	private void calculateCameraPosition(float horizDistance, float verticDistance)
	{
		float theta = this.player.getRotY() + angleAroundPlayer;
		float offsetX = (float) (horizDistance * Math.sin(Math.toRadians(theta)));
		float offsetZ = (float) (horizDistance * Math.cos(Math.toRadians(theta)));
		this.position.x = this.player.getPositionVector().x - offsetX;
		this.position.z = this.player.getPositionVector().z - offsetZ;
		this.position.y = this.player.getPositionVector().y + verticDistance;
		/***
		 * Its a change in future because a will add to terrain height.
		 */
		if(position.y < this.player.terrainHeight + 4){
				this.isRotateWhenCloseToGround = false;
			   position.y = this.player.terrainHeight + 2;
		}
	
	}
	
	/**
	 * Calculates the horizontal distance from the camera to the player
	 * using the cosine of the radiant transformations.
	 */
	private float calcutalteHorizontalDistance()
	{
		return (float) (this.distanceFromPlayer * 
				Math.cos(Math.toRadians(this.pitch)));
	}
	
	/**
	 * Calculates the vertical distance from the camera to the player
	 * using the sin of the radiant transformations.
	 */
	protected float calculateVerticalDistance()
	{
		return (float) (this.distanceFromPlayer * 
				Math.sin(Math.toRadians(this.pitch)));
	}
	
	public void invertPicth()
	{
		this.pitch = -pitch;
	}
	
	/**
	 * Includes collision of the camera with terrain.
	 */
	public EntityCamera enableCollision()
	{
		this.useBaseCollision = true;
			return this;
	}
	
	/**
	 * Disable collision of the camera with terrain.
	 */
	public EntityCamera disableCollision()
	{
		this.useBaseCollision = false;
			return this;
	}

	public Vector3f getPosition() 
	{
		return position;
	}

	public float getPitch() 
	{
		return pitch;
	}

	public float getYaw() 
	{
		return yaw;
	}

	public float getRoll() 
	{
		return roll;
	}
	
	public void setPitch(float pitchIn) 
	{
		this.pitch = pitchIn;
	}

	public void setYaw(float yawIn) 
	{
		this.yaw = yawIn;
	}

	public void setRoll(float rollIn) 
	{
		this.roll = rollIn;
	}
	
	/**
	 * Set position vector to camera.
	 * 
	 * @param x - x position on axis.
	 * @param y - y position on axis.
	 * @param z - z position on axis.
	 */
	public void setPosition(float x, float y, float z)
	{
		this.position = new Vector3f(x, y, z);
	}
	
	public Matrix4f getProjectionViewMatrix(WorldRenderer worldRenderer) 
	{
		return Matrix4f.mul(worldRenderer.getProjectionMatrix(), this.viewMatrix, null);
	}
	
	public Matrix4f getViewMatrix() 
	{
		return this.viewMatrix;
	}
}
