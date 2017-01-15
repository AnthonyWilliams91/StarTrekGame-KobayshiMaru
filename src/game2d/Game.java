// Assignment Game2D
// Class	  Game
// Author	  Emanuel Castro
// Date		  Apr 7, 2016

package game2d;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

@SuppressWarnings("unused")
public class Game extends BasicGameState {
	
	private static ArrayList<Rectangle> bop;
	public static int proximityAlert = 0;
	private ArrayList<Image> birdOfPrey;
	private Image enemyShip;
	
	//enterprise objects
	private Image enterprise;
	private Rectangle playerRect;
	private static ArrayList<Rectangle> laser;
	
	private int time;
	public static int level = 1;
	private static int rectX = 300;
	public static int counter = 0;
	public static int lives = 3;
	private int flashCount = 0;
	public static int bopTimeCount = 1000;
	
	private Random rand;
	private Image galaxy2;
	
/* START OF init METHOD */
	public void init(GameContainer g, StateBasedGame s) throws SlickException
	{
		//Enemy Components
		bop = new ArrayList<>();
		birdOfPrey = new ArrayList<>();
		enemyShip = new Image("res/BOP.png");
		
		//Player components
		laser = new ArrayList<>();
		enterprise = new Image("res/Enterprise2.png");
		playerRect = new Rectangle(0, 0, 40, 90); 
		rand = new Random();
		playerRect.setCenterX(rectX);
		playerRect.setCenterY(430);
		
		galaxy2 = new Image("res/Galaxy1.jpg");
	}
/* END OF init METHOD */
	
/* START OF render METHOD */
	@Override
	public void render(GameContainer c, StateBasedGame s, Graphics g) throws SlickException 
	{
		
		g.setAntiAlias(true);
		//draws backgroud
		g.drawImage(galaxy2, -500, -500);
		
		//draws player
//		g.setColor(Color.green);
//		g.draw(playerRect);
		g.drawImage(enterprise, playerRect.getX(), playerRect.getY());
		
		//draws bops
		g.setColor(Color.blue);
		for (Rectangle d : bop) {
			if(proximityAlert < 50 && d.getY() > 200){
				g.setColor(Color.red);
				g.draw(d);
			}
			g.drawImage(enemyShip, d.getX(), d.getY());
		}
		
		//draws lasers
		g.setColor(Color.red);
		for (Shape r : laser) {
			g.fill(r);
		}
		
		//Lives Display
		g.setColor(Color.green);
		g.drawString("Lives:" + lives, 15, 40);
		
		
		g.drawString("Score:" + Game2dApp.finalScore, 15, 25);
		// Level Display
		g.drawString("Level:"+level, 15, 55);
		//Flashing High Score
		g.setColor(Color.green);
		if(flashCount < 1200)
		{
			if (Game2dApp.lastHighScore >= Game2dApp.finalScore) {
				g.drawString(String.format("HighScore: " + Game2dApp.lastHighScore), 250, 5);
			}else{
				g.setColor(Color.red);
				g.drawString(String.format("New HighScore: " + Game2dApp.finalScore), 235, 5);
			}
		}
	}
/* END OF render METHOD */
	
/* START OF update METHOD */
	@Override
	public void update(GameContainer g, StateBasedGame s, int d) throws SlickException 
	{	
		//Flashes High Score by updating flashCount (if statement in render)
		highScoreFlash();
		
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
		
		
		//control of the user
		if(time%2 == 0)
		{
			shipControl(g);
		}	
		
		time += d;
		
		//Adds Bird of Prey and increments bop counter
		if (time > bopTimeCount) {
			int spawnPoint = rand.nextInt((g.getWidth() - 60) + 30);
			
			bop.add(new Rectangle(spawnPoint, -30, 45, 43));
			birdOfPrey.add(enemyShip);
			
			time = 0;
			counter++;
		}
		
		//Sets next level
		if(counter == 25)
		{
			if(bopTimeCount != 500)
			{
				bopTimeCount -= 50;
			}
			counter = 0;
			level++;
		}
		
		// Moves bop's
		for (Rectangle c : bop) {
			c.setCenterX(c.getCenterX());
			c.setCenterY(c.getCenterY() + (1 / 16f));

		}
		//if bop goes below screen then deletes
		removebop();

		//lasers shoot when user presses space bar
		if (g.getInput().isKeyPressed(Input.KEY_SPACE)) {

			laser.add(new Rectangle(playerRect.getCenterX() - 1, playerRect.getCenterY()- 35, 3, 17));

		}
		//makes the lasers go up
		for (Rectangle r : laser) {
			r.setCenterX(r.getCenterX());
			r.setCenterY(r.getCenterY() - (d / 2.5f));
		}
		
		//removes the lasers once out of the screen
		removeLazr();
		
		//removes the bop if it passes you
		for(int i = bop.size()-1; i >= 0; i--)
		{
			Rectangle r = bop.get(i);
			if(r.getCenterY()> 485)
			{
				bop.remove(i);
				birdOfPrey.remove(i);
				lives--;
			}
		}
		
		//if player hits the alien both bop and laser removed and score++
		alienHit();
		if(lives == 0)
		{
			
			if (Game2dApp.finalScore > Game2dApp.lastHighScore) {
				try {

					PrintWriter writer = new PrintWriter(Game2dApp.scoreFile, "UTF-8");
					writer.print(Game2dApp.finalScore);
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
			s.enterState(3, new FadeOutTransition(), new FadeInTransition());
			resetGame();
		}
		
		//Rotates background image
		galaxyRotate();
		
		//proximity alert method
		proxAlert();
		
		// Exits the game
		if(g.getInput().isKeyDown(Input.KEY_ESCAPE))
		{
			g.exit();
		}
	}
/* END OF update METHOD */

	
	
	
//////////////////////////////////////////////////////
////////////////////Custom Methods////////////////////
//////////////////////////////////////////////////////
/* removes stray lasers */
	private void removeLazr(){
		for(int i = laser.size()-1; i >= 0; i--)
		{
			Rectangle r = laser.get(i);
			if(r.getCenterY()<0)
			{
				laser.remove(i);
			}
		}
	}
	
/* moves lasers */
	public static void moveLazr(){
		for (Rectangle r : laser) {
			r.setCenterX(r.getCenterX());
			r.setCenterY(r.getCenterY() - (1 / 2.5f));
		}
	}
	
/* removes bird of prey if it passes the enterprise*/	
	private void removebop() {
		for (int i = 0; i < bop.size(); i++) {
			Rectangle circ = bop.get(i);
			if (circ.getCenterY() == 490) {
				bop.remove(i);
				birdOfPrey.remove(i);
			}
		}
	}
	
/* collision detection between laser and enemy ships*/
	private void alienHit() {
		for (int i = 0; i < laser.size(); i++) {
			Rectangle p = laser.get(i);
			for (int j = 0; j < bop.size(); j++) {
				Rectangle k = bop.get(j);
				if (p.intersects(k)) {
					laser.remove(i);
					bop.remove(j);
					Game2dApp.finalScore ++;
				}
			}
		}
	}
	
/* SHIP CONTROLS METHOD */
	/**
	 * @param g
	 * is the controlers for the user 
	 */
	private void shipControl(GameContainer g) {
		if (g.getInput().isKeyDown(Input.KEY_LEFT)) {
			playerRect.setCenterX(rectX --);
		}
		if (g.getInput().isKeyDown(Input.KEY_RIGHT)) {
			playerRect.setCenterX(rectX ++);
		}
		
		if(playerRect.getCenterX() < 0)
		{
			rectX = 0;
			playerRect.setCenterX(rectX);
		}
		if(playerRect.getCenterX() > 640)
		{
			rectX = 640;
			playerRect.setCenterX(rectX);
		}
	}

/* highScoreFlash Method*/
	public void highScoreFlash(){
		if(flashCount < 1500)
		{
			flashCount++;
		}
		else
		{
			flashCount = 0;
		}	
	}

/* galaxyRotate Method*/
	public void galaxyRotate(){
		galaxy2.rotate(.001f);
	}
	
/* Proximity alert Methods */
	public static void proxAlert(){
		if(proximityAlert < 125){
			proximityAlert++;
		}else{
			proximityAlert = 0;
		}
	}
	
	public static void resetGame()
	{
		lives = 3;
		level = 1;
		laser.removeAll(laser);
		bop.removeAll(bop);
		rectX = 300;
	}
	
	public int getID() 
	{
		return 1;
	}
}