package game2d;

import java.awt.Font;
import java.io.InputStream;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.util.ResourceLoader;

public class Credits extends BasicGameState{
	

    private int creditTimer = 0;
    private int nimoyCounter = 0;

    Image nimoy;

    TrueTypeFont font;
    TrueTypeFont font1;
    TrueTypeFont font2;
    
    public void init(GameContainer GameContainer, StateBasedGame sbg) throws SlickException{
    	// load a default java font
	    Font awtFont = new Font("Times New Roman", Font.BOLD, 50);
	    font = new TrueTypeFont(awtFont, false);
	         
	    // load font from a .ttf file
	    try {
	        InputStream inputStream = ResourceLoader.getResourceAsStream("res/Star Trek_future.ttf");
	        InputStream inputStream2 = ResourceLoader.getResourceAsStream("res/Star Trek_future.ttf");
	        
	        Font awtFont1 = Font.createFont(Font.TRUETYPE_FONT, inputStream);
	        Font awtFont2 = Font.createFont(Font.TRUETYPE_FONT, inputStream2);
	        
	        awtFont1 = awtFont1.deriveFont(30f); // set font size
	        awtFont2 = awtFont2.deriveFont(50f); // set font size
	        
	        font1 = new TrueTypeFont(awtFont1, false);
	        font2 = new TrueTypeFont(awtFont2, false);  
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    
	    nimoy = new Image("res/Spock.jpg");
    }
 
    public void update(GameContainer g, StateBasedGame s, int delta) throws SlickException {
    	// Keep music running on loop WORKS
    	if(!Menu.s1.isPlaying() && !Menu.s2.isPlaying())
    	{
    		if(!Menu.goLastPlayed){
    			Menu.s1.playAsMusic(1f, 1f, false);
    			Menu.goLastPlayed = true;
    		}else if(Menu.goLastPlayed){
    			Menu.s2.playAsMusic(1f, 1f, false);
    			Menu.goLastPlayed = false;
    		}
    	}	
    	
    	updateCredits();
    	
		//Back to menu
		if(g.getInput().isKeyDown(Input.KEY_M))
		{	
			s.enterState(0,new FadeOutTransition(),new FadeInTransition());
			creditTimer = 0;
			nimoyCounter = 0;
			Game2dApp.finalScore = 0;
		}
    }
    
    public void render(GameContainer c, StateBasedGame s, Graphics g) throws SlickException, NullPointerException
    {
        g.setBackground(Color.black);
        g.setFont(font2);
		g.setColor(Color.yellow);
        if(creditTimer < 3000){
        	g.drawString("Development Team", 80, 220); // 0
        }else if(creditTimer > 3500 && creditTimer < 6500){
        	g.drawString("Project Leader - Anthony Williams", 40, 220); // 1
        }else if(creditTimer > 7000 && creditTimer < 10000){
        	g.drawString("Programmer - Miguel Castro", 80, 220); // 2
        }else if(creditTimer > 10500 && creditTimer < 13500){
        	g.drawString("Programmer - Ines Hernandez", 80, 220); // 3
        }else if(creditTimer > 14000 && creditTimer < 17000){
        	g.drawString("Art - unknown", 80, 220); // 4
        }else if(creditTimer > 17500 && creditTimer < 20500){
        	g.drawString("Music - Michael Giacchino", 80, 220); // 5
        }else if(creditTimer > 22000 && creditTimer < 24000){
        	g.drawString("Special thanks to...", 80, 220); // 6
        }else if(creditTimer > 24500 && creditTimer < 27500){
        	g.drawString("Gene Roddenberry", 80, 220); // 7
        }else if(creditTimer > 28000 && creditTimer < 31000){
        	g.drawString("Developers of Slick2D", 80, 220); // 8
        }else if(creditTimer > 35000 && creditTimer < 40000){
        	g.setFont(font2);
        	g.drawString("\"Live Long and Prosper\"", 80, 220);
        	g.setColor(Color.white);
        	g.drawString("-Spock", 300, 270);
        }else if(creditTimer == 40000){
        	g.drawImage(nimoy, 0, 103);
        	nimoyCounter++;
        	if(nimoyCounter > 5000 && nimoyCounter < 6000){
        		if(nimoyCounter < 5800){
        			g.setColor(Color.green);
        			g.setFont(font1);
        			g.drawString("Press \"m\" to get back to the menu", 100, 25);
        		}
        	}else if(nimoyCounter == 6000){
        		nimoyCounter = 5001;
        	}
        }
    }	
	
    public void updateCredits() {
    	//Times credit intervals
    	if(creditTimer != 40000){
    		creditTimer++;
    	}	
    }
    
	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 4;
	}
}
