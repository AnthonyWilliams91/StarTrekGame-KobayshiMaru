package game2d;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
//import java.io.PrintWriter;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
//import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * 
 * @author Anthony Williams, Emanuel Castro, and Ines 
 *
 */
public class Game2dApp extends StateBasedGame {
	
	public static Integer finalScore = 0;
	public static String scoreFile = "res/scoreFile.txt";
	public static int lastHighScore;
	public static boolean save = false;
	
	public Game2dApp(String name) {
		super(name);
	}

	public static void main(String[] arg) throws SlickException, NumberFormatException, IOException {
		String line = null;
		BufferedReader br;
		
		try {
			br = new BufferedReader(new FileReader(scoreFile));
			while((line = br.readLine()) != null){
				lastHighScore = Integer.parseInt(line);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			lastHighScore = 0;
		}
		
		AppGameContainer app = new AppGameContainer(new Game2dApp("Star Trek: Kobayashi Maru"));
		app.setShowFPS(false);
//		app.setDisplayMode(1920, 1080, true);
		app.start();
		app.setAlwaysRender(true);
		app.setTargetFrameRate(60);
		System.out.println(app.getHeight());
		System.out.println(app.getWidth());
	}
	
	

	public void initStatesList(GameContainer c) throws SlickException
	{
		this.addState(new Menu());
		this.addState(new Game());
		this.addState(new HowToPlay());
		this.addState(new GameOver());
		this.addState(new Credits());
	}
	
	public void init(GameContainer g, StateBasedGame s) throws SlickException
	{

	}	
	
	public void render(GameContainer c, StateBasedGame s, Graphics g) throws SlickException {

	}
}
