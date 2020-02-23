package com.kenny.craftix.client.entity.player;

import org.lwjgl.util.vector.Vector3f;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.entity.EntityCamera;
import com.kenny.craftix.client.entity.EntityLivingBase;
import com.kenny.craftix.client.entity.GameObject;
import com.kenny.craftix.client.entity.IEntityLivingBase;
import com.kenny.craftix.client.gui.GuiGameOver;
import com.kenny.craftix.client.gui.GuiRenderer;
import com.kenny.craftix.client.loader.Loader;
import com.kenny.craftix.client.gui.GuiBackground.Pages;
import com.kenny.craftix.client.scenes.WorldScene;
import com.kenny.craftix.gameplay.GameMode;
import com.kenny.craftix.utils.Timer;
import com.kenny.craftix.world.World;
import com.kenny.craftix.world.terrain.Terrain;

import static com.kenny.craftix.client.keyboard.KeyboardManager.*;

import java.util.List;
import java.util.Random;

public class EntityPlayer extends EntityLivingBase implements IEntityLivingBase
{
	/**Previous position on X axis.*/
	public float prevX;
	/**Previous position on Y axis.*/
	public float prevY;
	/**Previous position on Z axis.*/
	public float prevZ;
	/**Previous rotation angle on X axis.*/
	public float prevRotX;
	/**Previous rotation angle on Y axis.*/
	public float prevRotY;
	/**Previous rotation angle on Z axis.*/
	public float prevRotZ;
	/**Its a normal player speed*/
	private float currentSpeed = 0;
	@SuppressWarnings("unused")
	private float currentSpeedZ = 0;
	public float currentTurnSpeed = 0;
	public float moveSpeed = 20F;
	/**Main player stats in the current world.*/
	public float playerHealth;
	public float playerHunger;
	public float playerThrist;
	public float playerBreath;
	public float playerSatiation;
	private boolean playerAlive = true;
	public float healthIn;
	/**This trigger confirms that the player actually died.*/
	private boolean flag = false;
	/**This trigger does not allow the screen of death rendered constantly.*/
	public static boolean flag2 = false;
	private Timer timer = new Timer();
	public GameMode gamemode;
	public String skin = "skins/playerSkin";
	/**Load the player movement from keyboard.*/
	private PlayerMovement movement;
	/**Check if player is spawned already*/
	public static Loader loader = new Loader();
	public WorldScene worldScene;
	private EntityCamera camera;
	/**Check if player on ground/terrain*/
	private boolean onGround;
	/**Check if player in water surface.*/
	private boolean inWater;
	@SuppressWarnings("unused")
	private float fallTime;
	protected boolean isFalling = false;
	protected boolean onGroundAfterFalling = false;
	public Random randomSpawner = new Random();
	public World worldIn;
	/**Inplementation of on first person player hand.*/
	private EntityPlayerHand playerHand;
	
	public EntityPlayer(Craftix craftixIn)
	{
		super("entity_player", getPlayerModel(), "skins/playerSkin4", 0F, 0F, 0F, 0.6F);
		this.worldScene = craftixIn.getWorldScene();
		this.worldIn = worldScene.world;
		this.movement = new PlayerMovement();
		this.prepareEntitySpawn();
		this.prepareLivingEntitySpawn();
		this.waitOnNextEvent();
		this.gamemode = new GameMode(this, 0);
		this.worldScene.playerIn = true;
		this.playerHand = new EntityPlayerHand("player_hand", this).generateObject(this.worldScene);
		this.playerHand.setPosition(new Vector3f(this.getPositionVector().x, this.getPositionVector().y, this.getPositionVector().z));
		this.camera = new EntityCamera(this);
	}
	
	/**
	 * Allows you to move our player on the terrain in the world. Called in an update loop.
	 */
	public void move(Terrain terrain)
	{
		this.playerMovementResponsible(this.movement.moveSpeed, this.movement.runSpeed);
		super.increaseRotation(0, currentTurnSpeed * this.timer.getFrameTimeSeconds(), 0);
		float distance = currentSpeed * this.timer.getFrameTimeSeconds();
		float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
		float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
		super.increasePosition(dx, 0, dz);
		this.playerHand.increasePosition(dx, 0, dz);
		this.upFlySpeed += this.getEntityGravity() * this.timer.getFrameTimeSeconds();
		super.increasePosition(0, this.upFlySpeed * this.timer.getFrameTimeSeconds(), 0);
		this.playerHand.increasePosition(0, this.upFlySpeed * this.timer.getFrameTimeSeconds(), 0);
		this.getTerrianCollision(terrain);
		this.playerCollisionXZ();
		this.prevX = this.getPositionVector().x;
		this.prevY = this.getPositionVector().y;
		this.prevZ = this.getPositionVector().z;
		this.playerHand.setRotation(this.getRotX(), this.getRotY(), this.getRotZ());
		this.playerHand.moveHand();
		this.playerHand.getPositionVector().y = this.getPositionVector().y;
		this.playerHand.followHandForPlayer();
		this.camera.moveCamera();
	}
	
	/**
	 * Its really hard part for me. Here firstlly going to checking on player collision with other game object, and
	 * after that player just back to other side of the object and not going inside of it.
	 * 
	 * @param player - physical object.
	 * @param object - the object with which the conflict will collision.
	 */
	public void playerCollisionDetector(EntityPlayer player, GameObject object)
	{
		if 
		(
			(player.getPositionVector().x <= object.getScale() * 2 + object.getPositionVector().x && player.getScale() * 2 + player.getPositionVector().x >= object.getPositionVector().x) &&
			(player.getPositionVector().y <= object.getScale() * 2 + object.getPositionVector().y && player.getScale() * 2 + player.getPositionVector().y >= object.getPositionVector().y) &&
			(player.getPositionVector().z <= object.getScale() * 2  + object.getPositionVector().z && player.getScale() * 2 + player.getPositionVector().z >= object.getPositionVector().z) 
		) 
		{
			this.getPositionVector().x = this.prevX;
			this.getPositionVector().z = this.prevZ;
		}
	}
	
	/**
	 * Collides player with another game objects on Z and X axis.
	 */
	public void playerCollisionXZ()
	{
		for(GameObject object : this.worldScene.objects)
		{
			if(object.getPhysicalObject())
			{
				if(Math.abs(object.getPositionVector().x - this.getPositionVector().x) <= object.getScale() + 0.8f &&
						Math.abs(object.getPositionVector().y - this.getPositionVector().y) <= object.getScale() + 0.8f &&
							Math.abs(object.getPositionVector().z - this.getPositionVector().z) <= object.getScale() + 0.8f)
				{
					this.getPositionVector().x = this.prevX;
					this.getPositionVector().y = this.prevY;
					this.getPositionVector().z = this.prevZ;
					this.playerHand.getPositionVector().x = this.prevX;
					this.playerHand.getPositionVector().y = this.prevY;
					this.playerHand.getPositionVector().z = this.prevZ;
				}
			}		
		}
	}
	
	/**
	 * This method is responsible for controlling the players in the world 
	 * with the keyboard.
	 */
	private void playerMovementResponsible(float speedIn, float runSpeedIn)
	{
		/**Check W and S keys*/
		if(keyDown(this.movement.getForwardKey()))
		{
			if(!this.inWater)
			{
				this.currentSpeed = this.getEntityWalkSpeed();
				if(keyDown(this.movement.getRunKey()))
				{
					this.currentSpeed = this.getEntityWalkSpeed() + this.getEntityRunSpeed();
				}
			} 
			else 
			{
				this.currentSpeed = this.getEntityWalkSpeed() - 10F;
				if(keyDown(this.movement.getRunKey()))
				{
					this.currentSpeed = this.getEntityWalkSpeed() - 5F;
				}
			}
		}
		else if(keyDown(this.movement.getBackwardKey())) 
		{
			if(!this.inWater)
			{
				this.currentSpeed = -this.getEntityWalkSpeed();
			} 
			else 
			{
				this.currentSpeed = -this.getEntityWalkSpeed() + 10F;
			}
		}
		else 
		{
			this.currentSpeed = 0;
		}
		
		if(keyDown(this.movement.getRightwardKey()))
		{
			this.currentTurnSpeed = -this.getEntityTurnSpeed();
		}
		else if(keyDown(this.movement.getLeftwardKey()))
		{
			this.currentTurnSpeed = this.getEntityTurnSpeed();
		}
		else
		{
			this.currentTurnSpeed = 0F;
		}
		
		/**Check SPACE key*/
		
		if(keyDown(this.movement.getJumpKey()))
		{
			this.isFlying = useUnlimitedMode;
			if(this.isFlying)
				this.onFlying();
			else
			{
				this.onJump();
			}
		}
	}
	
	/**
	 * Here to the usual jump is added to check for the presence of the 
	 * player in the air.
	 */
	@Override
	public void onJump()
	{
		super.onJump();
		if(!this.isInAir() && !useUnlimitedMode)
		{
			this.upFlySpeed = this.jumpPower;
			this.setInAir(true);
			this.isJumping = false;
			if(this.jumpPower == 0.1f)
			{
				this.healthIn =- 0.1f;
			}
			if(this.inWater)
			{
				;
			}
		}

	}
	
	@Override
	public void onFlying() 
	{
		super.onFlying();
		if(!this.isInAir() && this.isFlying)
		{
			if(this.inWater)
			{
				this.upFlySpeed = 6F;
			} else {
				this.upFlySpeed = this.flyPower - 20F;
			}
			this.isFlying = false;
		}
	}
	
	/**
	 * Event for the player that checks every frame that the player is currently 
	 * still alive. If not, it shows the window of death
	 */
	@Override
	protected void onDead() 
	{	
		this.playerHealth = healthIn;
		
		if(healthIn <= 0)
		{
			flag = true;
			
			if(flag && !flag2)
			{
				this.setPlayerAlive(false);
				this.playerDeadAnimation();
				Pages.isGameOverPage = true;
				GuiGameOver.addPage();
				flag2 = true;
			}
		}
	}
	
	/**
	 * Play a player animation when his die.
	 */
	public void playerDeadAnimation()
	{
		this.setRotX(90F);
		this.setRotZ(180F);
	}
	
	public void teleportPlayer(float tpPosX, float tpPosY, float tpPosZ)
	{
		this.setPosition(new Vector3f(tpPosX, tpPosY, tpPosZ));
		this.setRotX(0F);
		this.setRotZ(0F);
	}
	
	/**
	 * Restores the player after death at the initial spawn point.
	 */
	public void respawn()
	{
		this.restorePlayerStats();
		this.setPlayerAlive(true);
		this.teleportPlayer(0f, -2f, -0f);
		flag2 = false;
	}
	
	/**
	 * Event with player when player actually on underwater surface.
	 */
	@Override
	protected void ifEntityUnderWater() 
	{
		super.ifEntityUnderWater();
		if(this.getPositionVector().y <= -8)
			this.inWater = true;
		else
			this.inWater = false;
		
		if(this.inWater)
		{
			this.rotZ = this.prevRotZ;
			this.rotX = this.prevRotX;
			this.gravity = -20F;
			this.jumpPower = 5F;
			
			if(keyDown(this.movement.getJumpKey()))
			{
				this.onFlying();
			}
			
			if(this.getPositionVector().y < -12)
			{
				if(this.playerBreath > 0f)
				{
					this.playerBreath = this.playerBreath - 0.1f;
				}
			} 
				
			if(this.playerBreath == 0f)
			{
				this.healthIn--;
			}
			
		}
		else
		{
			this.gravity = -70F;
			this.jumpPower = 25F;
		}
		//check if player swim in water
		if(this.getPositionVector().y <= -9)
		{
			this.useUnlimitedMode = true;
			this.rotZ = 0;
			this.rotX = 0;
		
		}
		else
		{
			if(keyDown(this.movement.getJumpKey()))
			{
				this.useUnlimitedMode = true;
			}
			this.useUnlimitedMode = false;
			this.rotZ = this.prevRotZ;
			this.rotX = this.prevRotX;
		}
		
		if(this.getPositionVector().y > -12 && this.playerBreath < 100f)
		{
			if(this.playerBreath > 100f)
			{
				this.playerBreath = 100f;
			}
			if(this.playerBreath < 100f)
			{
				this.playerBreath = this.playerBreath + 0.1f;
			}
		}	
	}
	
	public void waitOnNextEvent()
	{
		boolean recovery;
		recovery = true;
		if(recovery)
		{
			
		}
	}
	
	/**
	 * Get player skin for model.
	 * @return 
	 */
	public String getPlayerSkin()
	{
		return this.skin;
	}
	
	/**
	 * Set the player skin.
	 */
	public void setPlayerSkin(String skin)
	{
		this.skin = skin;
	}
	
	/**
	 * Get the player model when contains in the "OBJ" file.
	 */
	public static String getPlayerModel()
	{
		return "entity_player";
	}

	/**
	 * Called frequently so the entity can update its state every tick as required. For example,
	 * the life of an entity is checked or some characteristics are added for it.
	 */
	@Override
	public void onLivingUpdate()
	{
		List<GameObject> gameObjects = this.worldScene.objects;
		this.renderPlayer();
		this.posX = getPositionVector().x;
		this.posY = getPositionVector().y;
		this.posZ = getPositionVector().z;
		if(this.worldScene.cx.status.isServer())
		{
			this.playerLivingStatusCheker();
			this.playerStatusInWorld();
			this.ifEntityUnderWater();
		}
		if(!this.worldScene.cx.status.isServer() && !Pages.isMainGamePausePage)
		{
			this.playerLivingStatusCheker();
			this.playerStatusInWorld();
			this.ifEntityUnderWater();
		}
		this.onDead();
		this.fall();
		if(this.getPositionVector().y == this.terrainHeight)
		{
			for(GameObject gameObject : gameObjects)
			{
				if(getPositionVector().y == gameObject.getPositionVector().y + 1)
					this.onGround = true;
				else
					this.onGround = false;
			}
			this.onGround = true;
		} else {
			this.onGround = false;
		}
		
	}
	
	/**
	 * To render a player model and as constituting its, like, hands, etc.
	 */
	public void renderPlayer()
	{
		EntityPlayer player = this;
		if(player.getCamera().isFirstPersonCamera)
		{
			this.setRenderState(false);
		} else {
			this.setRenderState(true);
		}
		
		this.playerHand.renderHand();
	}
	
	/**
	 * Preparing player entity spawning in the world. Set the stats of the player and other components
	 * to it.
	 */
	@Override
	public void prepareEntitySpawn() 
	{
		if(!WorldScene.isLoadWorld)
		{
			this.randomPlayerSpawner();
			this.setHealth(100f);
			this.setHunger(100f, 100f);
			this.setBreath(100f);
			this.setRotation(0F, 0F, 0F);
			this.entityMovingControll(20f, 10f, 160f);
		}
	}
	
	/**
	 * Set the main entity player health value.
	 */
	public void setHealth(float health) 
	{
		this.healthIn = health;
	}

	/**
	 * Sets the values of hunger for the player.
	 */
	public void setHunger(float hunger, float satiation) 
	{
		this.playerHunger = hunger;
		this.playerSatiation = satiation;
	}

	/**
	 * Sets underwater breathing values for the player.
	 */
	public void setBreath(float breath)
	{
		this.playerBreath = breath;
	}
	
	public void setThrist(float thrist) 
	{
		this.playerThrist = thrist;
	}

	
	/**
	 * Restore all player stats and set it on 100.
	 */
	public void restorePlayerStats()
	{
		this.setHealth(100f);
		this.setHunger(100f, 100f);
		this.setThrist(100f);
		this.setBreath(100f);
	}
	
	/**
	 * Deafult player living status in game world. Change hunger for times.
	 */
	public void playerStatusInWorld()
	{
		if(this.playerSatiation >= 50f && this.playerSatiation <= 100f)
		{
			this.playerSatiation -= 0.005f;
		}
		else if(this.playerSatiation >= 25f && this.playerSatiation <= 50f)
		{
			this.playerSatiation -= 0.006f;
			if(keyDown(this.movement.getForwardKey()))
			{
				this.playerHunger -= 0.0005f;
			}
			else if(keyDown(this.movement.getForwardKey()))
			{
				if(keyDown(this.movement.getRunKey()))
					this.playerHunger -= 0.0008f;
			}
			else if(keyDown(this.movement.getForwardKey()))
			{
				if(keyDown(this.movement.getJumpKey()))
					this.playerHunger -= 0.001f;
			}
			else 
			{
				this.playerHunger -= 0.0002f;
			}
		}
		else if(this.playerSatiation >= 1f && this.playerSatiation <= 25f)
		{
			this.playerSatiation -= 0.007f;
			if(keyDown(this.movement.getForwardKey()))
			{
				this.playerHunger -= 0.001f;
			}
			else if(keyDown(this.movement.getForwardKey()))
			{
				if(keyDown(this.movement.getRunKey()))
					this.playerHunger -= 0.002f;
			}
			else if(keyDown(this.movement.getForwardKey()))
			{
				if(keyDown(this.movement.getJumpKey()))
					this.playerHunger -= 0.003f;
			}
			else 
			{
				this.playerHunger -= 0.0005f;
			}
		}
		else if(this.playerSatiation >= 0 && this.playerSatiation <= 1f)
		{
			if(keyDown(this.movement.getForwardKey()))
			{
				this.playerHunger -= 0.004f;
			}
			else if(keyDown(this.movement.getForwardKey()))
			{
				if(keyDown(this.movement.getRunKey()))
					this.playerHunger -= 0.008f;
			}
			else if(keyDown(this.movement.getForwardKey()))
			{
				if(keyDown(this.movement.getJumpKey()))
					this.playerHunger -= 0.009f;
			}
			else 
			{
				this.playerHunger -= 0.001f;
			}
		}
		
		
	}
	
	/**
	 * Checks the required parameters and sets the boundaries for any parameters.
	 */
	public void playerLivingStatusCheker()
	{
		if(healthIn >= 100)
		{
			healthIn = 100;
		}
		if(healthIn <= 0)
		{
			healthIn = 0;
		}
		if(this.playerBreath <= 0f || this.playerHunger <= 0f)
		{
			this.healthIn = this.healthIn - 0.05f;
		}
		if(this.playerHunger >= 60 && this.healthIn < 100 && this.healthIn >= 1) 
		{
			this.healthIn = this.healthIn + 0.005f;
			this.playerHunger = this.playerHunger - 0.001f;
		}
		if(this.playerHunger >= 100)
		{
			this.playerHunger = 100;
		}
		if(this.playerThrist >= 100)
		{
			this.playerThrist = 100;
		}
		if(this.playerHunger <= 0)
		{
			this.playerHunger = 0;
		}
		
		if(this.playerSatiation >= 100)
			this.playerSatiation = 100;
		
		if(this.playerSatiation <= 0)
			this.playerSatiation = 0;
	}
	
	/**
	 * Check if player alive and return this value.
	 */
		
	public boolean isPlayerAlive()
	{
		return this.playerAlive;
	}
	
	/**
	 * Set the player alive boolean value.
	 */
	public void setPlayerAlive(boolean playerAliveIn)
	{
		this.playerAlive = playerAliveIn;
	}
	
	public PlayerMovement getMovement()
	{
		return this.movement;
	}
	
	public boolean isOnGround()
	{
		return this.onGround;
	}
	
	public boolean isInWater()
	{
		return this.inWater;
	}
	
	/**
	 * Render the background for the player's underwater environment.
	 */
	public void renderUnderwaterBackgroundEffect()
	{
		WorldScene worldIn = this.worldScene;
		GuiRenderer guiRenderer = worldIn.cx.guiRenderer;
		EntityCamera camera = this.getCamera();
		
		if(this.isInWater())
		{
			if(this.getPositionVector().y < -11 && camera.isFirstPersonCamera)
			{
				guiRenderer.render(worldIn.guiPlayerStats.textureUnderwater);
			}
			else if(this.getPositionVector().y < -15 && camera.isThridPersonCamera)
			{
				guiRenderer.render(worldIn.guiPlayerStats.textureUnderwater);
			}
			else if(this.getPositionVector().y < -15 && camera.isThridPersonCameraFace)
			{
				guiRenderer.render(worldIn.guiPlayerStats.textureUnderwater);
			}
			
		}
	}
	
	/**
	 * Spawn a player at a random point in the world at X, Y and Z coordinates.
	 */
	public void randomPlayerSpawner()
	{
		Random randX = new Random(), 
			    randY = new Random(), 
				 randZ = new Random();
		float randPosX = randX.nextInt(300);
		float randPosY = randY.nextInt(5);
		float randPosZ = randZ.nextInt(300);
		this.setPosition(new Vector3f(randPosX, randPosY, randPosZ));
	}
	
	/**
	 * Responsible for the fall of the player from a height. If the fall is from a high position 
	 * then the player will receive damage.
	 */
	public void fall()
	{
		if(this.onGround)
		{
			this.isFalling = true;
			
			if(this.isFalling && this.onGround)
			{
				this.isFalling = false;
				this.onGroundAfterFalling = true;
			}
		}
		
		if(this.onGroundAfterFalling)
		{
			//this.healthIn -= 11.0F;
			this.onGroundAfterFalling = false;
		}
	}
	
	/**
	 * Return the connecting to player virtual camera.
	 */
	public EntityCamera getCamera()
	{
		return this.camera;
	}
	
	/**
	 * Return player hand object.
	 */
	public EntityPlayerHand getPlayerHand()
	{
		return this.playerHand;
	}
	
	/**
	 * Delete player data when world has destroying.
	 */
	public void deletePlayer()
	{
		this.loader.deleteVaoFromCache(this.getObjectModel().getVaoID());
		this.loader.deleteVaoFromCache(this.playerHand.getObjectModel().getVaoID());
	}

}