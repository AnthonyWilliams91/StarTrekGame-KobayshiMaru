// Assignment Game2D
// Class	  Menu
// Author	  Emanuel Castro
// Date		  Apr 6, 2016

package game2d;

import java.awt.Font;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.particles.effects.FireEmitter;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.util.ResourceLoader;

@SuppressWarnings("unused")
public class Menu extends BasicGameState {
	//sets rate of high score flashing
	public int flashCount = 0;
	
	//first song file
	public static Audio s1;
	//second song file
	public static Audio s2;
	//boolean to see if s1 was played last
	public static boolean goLastPlayed = false;
	
	//image of one of the galaxies 
	private Image galaxy1;
	//galaxy image x coordinates
	private int galaxyX = 0;
	// galaxy timing
	private int galacticT = 0;
	//image of enterprise
	private Image enterprise;
	private int enterpriseX = -800;
	private int enterpriseTiming = 0;
	
	//title image
	private Image title;
	//Font for drawing strings
	private TrueTypeFont font;
	public static TrueTypeFont font2;
	
/* init Method */
	public void init(GameContainer g, StateBasedGame s) throws SlickException
	{
		// load a default java font
	    Font awtFont = new Font("Times New Roman", Font.BOLD, 50);
	    font = new TrueTypeFont(awtFont, false);
	         
	    // load font from a .ttf file
	    try {
	        InputStream inputStream = ResourceLoader.getResourceAsStream("res/Star Trek_future.ttf");
	         
	        Font awtFont2 = Font.createFont(Font.TRUETYPE_FONT, inputStream);
	        awtFont2 = awtFont2.deriveFont(35f); // set font size
	        font2 = new TrueTypeFont(awtFont2, false);
	             
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		
		//Audio: in game music
		try {
			s1 = AudioLoader.getAudio("OGG",  ResourceLoader.getResourceAsStream(
					"res/14_To_Boldly_Go.ogg"));
			s2 = AudioLoader.getAudio("OGG",  ResourceLoader.getResourceAsStream(
					"res/15_End_Credits.ogg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Galaxy image for menu
		galaxy1 = new Image("res/Galaxy3_wide.jpg");
		//Title image for menu
		title = new Image("res/title.png");
		
		//Enterprise fly by image
		enterprise = new Image("res/MenuEnterprise.png");
	}

/* render Method */
	public void render(GameContainer c, StateBasedGame s, Graphics g) throws SlickException
	{	
		//Drawing Background
		g.drawImage(galaxy1, galaxyX, 0);
		//Drawing Enterprise
		g.drawImage(enterprise, enterpriseX, 150);
		//Setting Backdrop for title
		g.setColor(Color.black);
		g.fillRect(140, 110, 360, 250);
		//Drawing the title
		g.drawImage(title, 145, 115);
		//Flashing High Score
		g.setColor(Color.green);
		if(flashCount < 700)
		{
			g.setFont(font2);
			g.drawString(String.format("HighScore: " + Game2dApp.lastHighScore), 250, 5);
		}

		g.setColor(Color.black);
		g.setFont(font2);
		g.setColor(Color.white);
		g.drawString("Welcome,", 150, 220);
		g.drawString("Press \"enter\" to play", 150, 260);
		g.drawString("\"Q\" for instructions", 150, 290);
		g.drawString("\"Esc\" to exit the game", 150, 320);
	}

/* update Method */
	@Override
	public void update(GameContainer c, StateBasedGame s, int d) throws SlickException
	{
	// Enter to play, Q for instructions, Esc to quit
		if (c.getInput().isKeyDown(Input.KEY_ENTER))
		{
			s.enterState(1, new FadeOutTransition(), new FadeInTransition());	
		}
		if(c.getInput().isKeyDown(Input.KEY_Q))
		{
			s.enterState(2, new FadeOutTransition(), new FadeInTransition());
		}
		if(c.getInput().isKeyDown(Input.KEY_ESCAPE))
		{
			c.exit();
		}
		
	// Keeps music running on loop 
		if(!s1.isPlaying() && !s2.isPlaying())
		{
			if(!goLastPlayed){
				s1.playAsMusic(1f, 1f, false);
				goLastPlayed = true;
			}else if(goLastPlayed){
				s2.playAsMusic(1f, 1f, false);
				goLastPlayed = false;
			}
		}
	// Increments flashCount 
		highScoreFlash();
		
	// Increments galacticT and image position
		galaxyMover();
		
	// Increments enterpriseTiming and position
		enterpriseMover();
	}

/* highScoreFlash Method*/
	public void highScoreFlash(){
		if(flashCount < 1000)
		{
			flashCount++;
		}
		else
		{
			flashCount = 0;
		}	
	}
	
/* galactic movement method */
	public void galaxyMover(){
		if(galacticT < 80){
			galacticT++;
			if(galacticT == 80){
				galaxyX--;
				galacticT = 0;
			}
		}

		if(galaxyX < -1450){
			galaxyX = 0;
			enterpriseX = -1000;
			if(s1.isPlaying()){
				s1.stop();
				goLastPlayed = false;
			}else if(s2.isPlaying()){
				s2.stop();	
			}
		}
	}
	
/*Enterprise movement*/
	public void enterpriseMover(){
		if(enterpriseTiming < 50){
			enterpriseTiming++;
			if(enterpriseTiming == 50){
				enterpriseX ++;
				enterpriseTiming = 0;
			}
		}
		
//		if(enterpriseX > 700){
//			enterpriseX = -1500;
//		}
	}

/* getID Method */
	// GETS THIS STATES ID NUMBER: '0'
	public int getID()
	{
		return 0;
	}
}
