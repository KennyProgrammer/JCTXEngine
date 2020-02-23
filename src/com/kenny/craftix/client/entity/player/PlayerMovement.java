package com.kenny.craftix.client.entity.player;

import com.kenny.craftix.client.keyboard.KeyboardManager;
import com.kenny.craftix.client.settings.InGameSettings;

public class PlayerMovement extends KeyboardManager
{
	/**All player key movement parametrs*/
	public float moveSpeed;
	public float runSpeed;
	public float jumpSpeed;
	private int moveForwardKey;
	private int moveBackwardKey;
	private int moveLeftwardKey;
	private int moveRightwardKey;
	private int jumpKey;
	private int runKey;
	
	public PlayerMovement() 
	{
		this.moveSpeed = 20F;
		this.runSpeed = 25F;
		this.moveForwardKey = InGameSettings.keyboardMovementForwardIn;
		this.moveBackwardKey = InGameSettings.keyboardMovementBackwardIn;
		this.moveLeftwardKey = InGameSettings.keyboardMovementLeftwardIn;
		this.moveRightwardKey = InGameSettings.keyboardMovementRightwardIn;
		this.jumpKey = InGameSettings.keyboradMovementJumpIn;
		this.runKey = InGameSettings.keyboradMovementRunIn;
	}
	
	public void setDefaultPlayerMovement()
	{
		this.moveForwardKey = 17;
		this.moveBackwardKey = 31;
		this.moveLeftwardKey = 30;
		this.moveRightwardKey = 32;
		this.jumpKey = 57;
		this.runKey = 54;
	}
	
	/**
	 * Return this user move rightward entity key down.
	 */
	public int getRightwardKey()
	{
		return this.moveRightwardKey;
	}
	
	/**
	 * Return this user move leftward entity key down.
	 */
	public int getLeftwardKey()
	{
		return this.moveLeftwardKey;
	}
	
	/**
	 * Return this user move backward entity key down.
	 */
	public int getBackwardKey()
	{
		return this.moveBackwardKey;
	}
	
	/**
	 * Return this user move forward entity key down.
	 */
	public int getForwardKey()
	{
		return this.moveForwardKey;
	}
	
	/**
	 * Return this user jump key down.
	 */
	public int getJumpKey()
	{
		return this.jumpKey;
	}
	
	public int getRunKey()
	{
		return this.runKey;
	}
	
}
