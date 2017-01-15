// Assignment Game2D
// Class	  GameOver
// Author	  Emanuel Castro
// Date		  Apr 18, 2016

package game2d;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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

@SuppressWarnings("unused")
public class GameOver extends BasicGameState{
	private int previousHigh;
	
	

	@Override
	public void init(GameContainer g, StateBasedGame s) throws SlickException 
	{
		previousHigh = Game2dApp.lastHighScore;
	}

	@Override
	public void render(GameContainer c, StateBasedGame s, Graphics g) throws SlickException 
	{
		g.setColor(Color.green);
		// 	IF: 	displays the new high score
		// 	ELSE: 	displays the end of game score
		if(Game2dApp.finalScore > previousHigh)
		{
			g.drawString("Congratulations", 240, 150);
			g.drawString("New High Score: " + Game2dApp.finalScore,  235, 170);
			Game2dApp.lastHighScore = Game2dApp.finalScore;
		}
		else
		{
			g.drawString("GAME OVER", 270, 150);
			g.drawString("" + Game2dApp.finalScore,  300, 170);
		}
		
		// drawing the instructions
		g.setFont(Menu.font2);
		g.setColor(Color.white);
		g.drawString("Press 'r' to replay", 200, 240);
		g.drawString("'m' to go to the main menu", 160, 275);
		g.drawString("or 'c' to view credits", 190, 310);
	}

	@Override
	public void update(GameContainer g, StateBasedGame s, int d) throws SlickException
	{
		/* Keeps music running on a constant loop */
		if(!Menu.s1.isPlaying() && !Menu.s2.isPlaying())
		{
			if(!Menu.goLastPlayed)
			{
				Menu.s1.playAsMusic(1f, 1f, false);
				Menu.goLastPlayed = true;
			}
			else if(Menu.goLastPlayed){
				Menu.s2.playAsMusic(1f, 1f, false);
				Menu.goLastPlayed = false;
			}
		}

		//Replay game
		if(g.getInput().isKeyDown(Input.KEY_R))
		{
			s.enterState(1,new FadeOutTransition(), new FadeInTransition());
			Game2dApp.finalScore = 0;
		}
     
		//Back to menu
		if(g.getInput().isKeyDown(Input.KEY_M))
		{	
			s.enterState(0,new FadeOutTransition(),new FadeInTransition());
			Game2dApp.finalScore = 0;
		}
		
		//Goes to credits
		if(g.getInput().isKeyDown(Input.KEY_C))
		{
			s.enterState(4,new FadeOutTransition(),new FadeInTransition());
			Game2dApp.finalScore = 0;
		}
		
		// Escape to quit
		if(g.getInput().isKeyDown(Input.KEY_ESCAPE))
		{
			g.exit();
		}
	}

	@Override
	public int getID() {
		
		return 3;
	}
	
	

}
