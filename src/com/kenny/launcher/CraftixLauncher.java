package com.kenny.launcher;


import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollBar;
import javax.swing.JTextField;
import javax.swing.KeyStroke;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.LWJGLException;

import com.kenny.craftix.client.Craftix;
import com.kenny.craftix.client.settings.Session;
import com.kenny.launcher.language.English;
import com.kenny.launcher.language.Russian;
import static com.kenny.launcher.language.Words.*;

public class CraftixLauncher 
{
	public static final Logger LOGGER = LogManager.getLogger(Craftix.LOGGER_INSTANCE);
	/**This is main display of the application.*/
	public JFrame jWindow = new JFrame();
	/**An implementation of a menu a popup window containing JMenuItems that is displayed when
	 *  the user selects an item on the JMenuBar.*/
	/**Create on the display emtpty menu bar.*/
	public JMenuBar jMenuBar = new JMenuBar();
	public JScrollBar jScrollBar = new JScrollBar();
	/**Localization files*/
	public English en_us = new English();
	public Russian ru_ru = new Russian();
	// Текстовые поля
    JTextField smallField, bigField;
	
	public void run(Craftix cx, Session session) throws InterruptedException
	{
		if(!cx.isRunning())
		{
			this.jWindow.setTitle("Craftix Launcher");
			this.jWindow.setSize(800, 600);
			this.jWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.jWindow.setVisible(true);
			this.jWindow.setResizable(false);
			this.ru_ru.loadLocalization(true);
			//this.en_us.loadLocalization(true);
			this.launcherMenu(cx, session);
			LOGGER.info("Launcher have running!");
		}
		else
		{
			System.out.close();
		}
	}
	
	public void launcherMenu(Craftix cx, Session session)
	{
		this.jWindow.setJMenuBar(this.jMenuBar);
		this.jMenuBar.revalidate();
		this.jMenuBar.setFont(new Font("Arial", Font.BOLD, 14));
		
		JMenu mFile = new JMenu(T_FILE);
		this.jMenuBar.add(mFile);
		JMenu mSettings = new JMenu(T_SETTINGS);
		this.jMenuBar.add(mSettings);
		JMenu mRun = new JMenu(T_RUN);
		this.jMenuBar.add(mRun);
		JMenu mNews = new JMenu(T_NEWS);
		this.jMenuBar.add(mNews);
		JMenu mClose = new JMenu(T_CLOSE);
		this.jMenuBar.add(mClose);
		
		/**
		 * Set the MenuFile's submenus.
		 */
		JMenuItem iOpen = new JMenuItem(T_OPEN);
		mFile.add(iOpen);
		iOpen.setEnabled(false);
		mFile.addSeparator();
		JMenuItem iRestart = new JMenuItem(T_RESTART);
		mFile.add(iRestart);
		JMenuItem iExit = new JMenuItem(T_CLOSE);
		mFile.add(iExit);
		
		JMenuItem iRunCraftix = new JMenuItem(T_RCRAFTIX);
		mRun.add(iRunCraftix);
		iRunCraftix.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
					cx.run();
				} catch (LWJGLException e1) 
				{
					e1.printStackTrace();
				}
				if(cx.isRunning())
				{
					jWindow.setVisible(false);
				}
			}
		});
		
		JMenuItem iCloseApplication = new JMenuItem(T_CLOSE_APP);
		mClose.add(iCloseApplication);
		iCloseApplication.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				System.exit(1);
			}
		});
		iCloseApplication.setAccelerator(KeyStroke.getKeyStroke("ctrl Z"));
		
		
	}
	
}
