package com.kenny.craftix.client.settings;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.kenny.craftix.client.Craftix;

public class Session 
{
	/**This name of the current or random nickname.*/
	public String username;
	/**Id of the current player.*/
	private final String playerId;
	/**Id of the session.*/
	public final String sessionId;
	private static final Logger LOGGER = LogManager.getLogger(Craftix.LOGGER_INSTANCE);
	/**Check if user has license, or not.*/
	private final boolean hasLicence;
	
	public Session(String usernameIn, String playerIdIn, String sessionTypeIn, boolean hasLicenseIn) 
	{
		if(usernameIn == null || usernameIn.isEmpty())
		{
			usernameIn = "UnknownName";
			playerIdIn = "NotConfirmedId";
			hasLicenseIn = false;
			
			LOGGER.warn("==============================");
			LOGGER.warn("The player's nickname for this session has not been set, respectively,");
			LOGGER.warn("the player's ID has not been set either. Check whether you have started");
			LOGGER.warn("the game client correctly. Now you nick name is 'UnknownName' and player");
			LOGGER.warn("id is 'NotConfirmedId'");
			LOGGER.warn("==============================");
		}
		
		this.username = usernameIn;
		this.playerId = playerIdIn;
		this.sessionId = sessionTypeIn;
		this.hasLicence = hasLicenseIn;
	}
	
	public String getSessionId()
	{
	    return this.sessionId;
	}
	
	public String getPlayerId()
	{
	    return this.playerId;
	}

	public String getUsername()
	{
	    return this.username;
	}
	
	public void writeUsername(String newUsernameIn)
	{
	    this.username = newUsernameIn;
	}
	
	/**
	 * Return the boolean value hasLicense.
	 */
	public boolean getUserLicense()
	{
		return this.hasLicence;
	}
}
