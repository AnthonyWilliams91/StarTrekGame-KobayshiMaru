// Assignment Game2D
// Class	  HowToPlay
// Author	  Emanuel Castro
// Date		  Apr 18, 2016

package game2d;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class HowToPlay extends BasicGameState {
	//image of one of the galaxies 
	private Image galaxy1;
	//galaxy image x coordinates
	private int galaxyX = 0;
	// galaxy timing
	private int galacticT = 0;
	
	// Flash Timer
	private int flashTimer = 0;
		
	@Override
	public void init(GameContainer g, StateBasedGame s) throws SlickException
	{
		//Galaxy image for menu
		galaxy1 = new Image("res/Galaxy3_wide.jpg");
		
	}

	@Override
	public void render(GameContainer c, StateBasedGame s, Graphics g) throws SlickException {
		//Drawing Galaxy Background
		g.drawImage(galaxy1, galaxyX, 0);
		
		g.setFont(Menu.font2);
		g.setColor(Color.black);
		g.fillRect(140, 75, 360, 300);
		g.setColor(Color.blue);
		g.drawString("NCC-1701 Protocols", 220, 85);
		g.setColor(Color.white);
		g.drawString("<-- to move left", 200, 170);
		g.drawString("--> to move right", 200, 205);
		g.drawString("'space bar' to shoot", 200, 240);
		g.drawString("Press enter to play", 200, 275);
		g.resetFont();
		g.setColor(Color.green);
		g.drawString("ENJOY!", 300, 340);
		
		if (flashTimer < 675) {
			g.drawString("Press 'm' to go back to the menu", 170, 10);
		}
		
	}

	@Override
	public void update(GameContainer g, StateBasedGame s, int d) throws SlickException
	{
		// Keep music running on loop WORKS
		if(!Menu.s1.isPlaying() && !Menu.s2.isPlaying()){
			if(!Menu.goLastPlayed){
				Menu.s1.playAsMusic(1f, 1f, false);
				Menu.goLastPlayed = true;
			}else if(Menu.goLastPlayed){
				Menu.s2.playAsMusic(1f, 1f, false);
				Menu.goLastPlayed = false;
			}
		}
		
		// Enter to start game
		if(g.getInput().isKeyDown(Input.KEY_ENTER))
		{
			s.enterState(1,new FadeOutTransition(), new FadeInTransition());
		}
		// M for menu
		if(g.getInput().isKeyDown(Input.KEY_M))
		{	
			s.enterState(0,new FadeOutTransition(),new FadeInTransition());
			Game2dApp.finalScore = 0;
		}
		
		// Update galaxy backround image
		galaxyMover();
		
		// Updating the flashTimer
		textFlash();
	}

/* galactic movement method */
	public void galaxyMover()
	{
		if(galacticT < 100){
			galacticT++;
			if(galacticT == 100){
				galaxyX--;
				galacticT = 0;
			}
		}

		if(galaxyX < -1450){
			galaxyX = 0;
		}
	}
	
/* flashing text method*/	
	public void textFlash()
	{
		if(flashTimer < 1000){
			flashTimer++;
		}else{
			flashTimer = 0;
		}
	}
	
/* Game State ID */	
	@Override
	public int getID()
	{
		return 2;
	}

}
