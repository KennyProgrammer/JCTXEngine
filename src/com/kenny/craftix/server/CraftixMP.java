package com.kenny.craftix.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.gui.GuiBackground.Pages;
import com.kenny.craftix.client.gui.GuiDisconnected;
import com.kenny.craftix.client.text.TextLoader;
import com.kenny.craftix.main.Side;
import com.kenny.craftix.main.SideMachine;
import com.kenny.craftix.server.gui.GuiServerLog;
import com.kenny.craftix.server.socket.SocketRenderText;
import com.kenny.craftix.server.socket.SocketUpdateDisplay;
import com.kenny.craftix.server.world.WorldServer;
import com.kenny.craftix.utils.Timer;

@SideMachine(Side.SERVER)
public class CraftixMP
{
	public static final Logger LOGGER = LogManager.getLogger(Craftix.LOGGER_INSTANCE);
	private static final String SERVER_VERSION = " 0.0.2b";
	private static final String SERVER_VERSION_TYPE = " Alpha";
	/**Time on the server side.*/
	public Timer timer = new Timer();
	/**Server side of this server.*/
	protected ServerSocket serverSide;
	/**Server side of this client socket.*/
	protected Socket clientSide;
	/**Client side socket.*/
	protected Socket socket;
	/**Ip address of the server. Server socket will bind to this address.*/
	private String serverIP;
	/**Connection Ip addres. Client will connect to this address.*/
	private String connectIP;
	/**Client and server sides will connect/host on this port.*/
	private int port;
	public boolean isHost;
	/**Main class and buffer for the others networking packets.*/
	protected PacketBuffer packetBuffer;
	public static ObjectOutputStream out;
	public static ObjectInputStream in;
	public SocketUpdateDisplay socketUpdateDisplay = new SocketUpdateDisplay();
	public SocketRenderText socketRenderText = new SocketRenderText();
	public GuiServerLog guiServer = new GuiServerLog();
	/**Check if server is running.*/
	public boolean isServerRunning = false;
	public boolean isConnected = false;
	/**Call to client side of Craftix Engine.*/
	public Craftix cx;
	private WorldServer worldServer;
	public static boolean isWorldGenerated;
	public ServerGetterInformation serverInfo;
	
	public CraftixMP(Craftix craftixIn) 
	{
		this.cx = craftixIn;
		ServerGetterInformation serverGetterInformation = new ServerGetterInformation(SERVER_VERSION, SERVER_VERSION_TYPE);
		this.serverInfo = serverGetterInformation;
		this.worldServer = new WorldServer();
	}
	
	public void runServer(boolean isHost) throws IOException
	{
		this.checkIfHost(isHost);
		this.connection();
	}
	
	/**
	 * Connection is established between the client and server. There can only be
	 * one client per server. After all this, two threads are created. A thread for sending data, and a thread for
	 * receiving and parsing data.
	 */
	public void connection() throws IOException
	{
		boolean flag = true;
		this.collectInfoAboutServer();
		this.packetBuffer = new PacketBuffer(this.cx);
		
		if(this.isHost)
		{
			try
			{
				this.cx.menuScene.renderScreenPages();
				this.socketRenderText.renderText(this.cx);
				Pages.isConnectingPage = true;
				this.socketUpdateDisplay.updateDisplayOnce();
				this.isServerRunning = true;
				System.out.println("Hosting...");
				this.socketUpdateDisplay.updateDisplayOnce();
				try
				{	
					GuiServerLog.checkIpAddress = true;
					this.socketRenderText.renderText(this.cx);
					this.socketUpdateDisplay.updateDisplayOnce();
					this.serverSide = new ServerSocket(this.getPort(), 4, InetAddress.getByName(this.getServerIp()));
					GuiServerLog.checkIpAddress = false;
				}	
				catch (Exception e) 
				{
					this.disconnection();
					LOGGER.error("You can't create the server, because your IP Address not avalible.");
					Pages.isConnectingPage = false;
					this.guiServer.clientUnknownIpAddress = true;
					GuiDisconnected.addPage();
					Pages.isDisconnectedPage = true;
					e.printStackTrace();
				}
				this.guiServer.serverWaitClient = true;
				this.socketUpdateDisplay.updateDisplayOnce();
				this.cx.textLang.renderEnigneText(this.cx);
				this.cx.menuScene.renderScreenPages();
				TextLoader.render();
				this.socketUpdateDisplay.updateDisplayOnce();
				System.out.println("Host ready!\nWaiting the client...");
				this.clientSide = this.serverSide.accept();
				System.out.println("Client connected!\nBuffering...");
				this.guiServer.serverWaitClient = false;
				this.socketUpdateDisplay.updateDisplayOnce();
				out = new ObjectOutputStream(this.clientSide.getOutputStream());
				in = new ObjectInputStream(this.clientSide.getInputStream());
				System.out.println("Buffered!\nPinging for 256 bytes...");
				long start = System.currentTimeMillis();
				byte[] ping = new byte[256];
				in.read(ping);
				out.writeLong(start);
				out.flush();
				Pages.isConnectingPage = false;
				System.out.println("Starting threads...");
				this.createServerWorld();
				
				//here a connection operation, like a position player, or other game objects.
				
				System.out.println("Started!\nGenerate world...");
			}
			catch(Exception e)
			{
				e.printStackTrace();
				this.disconnection();
			}
		}
		else if(!this.isHost)
		{
			try
			{
				this.cx.menuScene.renderScreenPages();
				this.socketRenderText.renderText(this.cx);
				Pages.isConnectingPage = true;
				this.socketUpdateDisplay.updateDisplayOnce();
				System.out.println("Connecting...");
				this.socketUpdateDisplay.updateDisplayOnce();
				try
				{
					GuiServerLog.checkIpAddress = true;
					this.socketRenderText.renderText(this.cx);
					this.socketUpdateDisplay.updateDisplayOnce();
					this.socket = new Socket(this.getConnectIp(), this.getPort());
					GuiServerLog.checkIpAddress = false;
				}
				catch(Exception e)
				{
					this.guiServer.serverUnknownIpAdreess = true;
					Pages.isConnectingPage = false;
					GuiDisconnected.addPage();
					Pages.isDisconnectedPage = true;
					this.disconnection();
					LOGGER.error("Can't connect to the server, because your Ip Address not avaliable!");
				}
				this.socketUpdateDisplay.updateDisplayOnce();
				System.out.println("Connected!\\nBuffering...");
				in = new ObjectInputStream(this.socket.getInputStream());
				out = new ObjectOutputStream(this.socket.getOutputStream());
				byte[] ping = new byte[256];
				new Random().nextBytes(ping);
				System.out.println("Buffered\nPinging for 256 bytes...");
				out.write(ping);
				out.flush();
				GuiServerLog.clientJoinServer = true;
				this.socketRenderText.renderText(this.cx);
				this.socketUpdateDisplay.updateDisplayOnce();
				GuiServerLog.clientJoinServer = false;
				Pages.isConnectingPage = false;
				this.createServerWorld();
				
				//here a send operation, like a position player, or other game objects.
				
				System.out.println("Starting threads...");
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				this.disconnection();
			}
		}
	}
	
	/**
	 * Set the flag isServerRunning on false and disconnect user.
	 * @throws IOException 
	 */
	public void disconnection() throws IOException
	{
		this.isServerRunning = false;
		this.cx.status.setServer(false);
		this.cleanConnectTextFlags();
		if(cx.status.isServer())
		{
			this.isConnected = false;
			this.socket.close();
		}
	}
	
	public void cleanConnectTextFlags()
	{
		GuiServerLog.checkIpAddress = false;
		GuiServerLog.serverWaitClient = false;
		GuiServerLog.clientJoinServer = false;
	}
	
	/**
	 * Create a new world server. Other player can be connected to this world.
	 */
	public void createServerWorld()
	{
		this.cx.status.setGameMenu(false);
		this.cx.status.setServer(true);
		this.cx.status.setGameWorld(true);
		this.cx.menuScene.isGoToNexScene = true;
	}
	
	/**
	 * Checks if it is a host/server then loads a new world and waits for the 
	 * client. If not that there is a connection of the client to the server.
	 */
	public void checkIfHost(boolean isHostIn)
	{
		this.isHost = isHostIn;
	}
	
	/**
	 * Set the port, Ip address, and server Ip address for connection.
	 */
	public void collectInfoAboutServer()
	{
		this.serverIP = "localhost";
		this.connectIP = "localhost";
		this.port = 25565;
	}
	
	public void collectInfoAboutServer(String serverIpIn, String connectIpIn, int portIn)
	{
		this.serverIP = serverIpIn;
		this.connectIP = connectIpIn;
		this.port = portIn;
	}
	
	/**
	 * Return the server Ip addrees.
	 */
	public String getServerIp()
	{
		return this.serverIP;
	}
	
	/**
	 * Return this connection client Ip address.
	 */
	public String getConnectIp()
	{
		return this.connectIP;
	}
	
	/***
	 * Return the port of host.
	 */
	public int getPort()
	{
		return this.port;
	}
	
	public PacketBuffer getPacketBuffer()
	{
		return this.packetBuffer;
	}
	
	public WorldServer getWorldServer()
	{
		return this.worldServer;
	}

}
