package GameState;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;

import Entity.Enemy;
import Entity.Explosion;
import Entity.HUD;
import Entity.Player;
import Entity.Enemies.Pet;
import Main.GamePanel;
import TileMap.Background;
import TileMap.TileMap;
import handlers.Keys;



public class Level1State extends GameState {

	private TileMap tilemap;
	private Background bg;
	private Background bg2;
	private Player player;

	private ArrayList<Enemy> enemies;
	private ArrayList<Explosion> explosions;
	
	
	private HUD hud;

	public Level1State(GameStateManager gsm) {
		
		this.gsm = gsm;
		
		
		init();
		
		
	}
	
	
	public void init() {
	
		tilemap = new TileMap(64);
		tilemap.loadTiles("/Tilesets/tilehomes.png");
		tilemap.loadMap("/Maps/hehe.map");
		tilemap.setPosition(0, 0);
		tilemap.setBounds(
				tilemap.getWidth() - 1 * tilemap.getTileSize(),
				tilemap.getHeight() - 2 * tilemap.getTileSize(),
				0, 0
			);
		tilemap.setTween(0.07);

			
			bg = new Background("/assets/SKY.png", 0.1);
			bg2 = new Background("/assets/MATO.png", 0.2);
		 
		      
			
			player = new Player(tilemap);
			player.setPosition(150, 460);
			
			
			
	
		populateEnemies();
		explosions = new ArrayList<Explosion>();
		
		hud = new HUD(player);
		
	}

	
	private void populateEnemies() {
		enemies = new ArrayList<Enemy>();
		Pet p;
		
		Point[] points = new Point[] {
		
	
			new Point(1946, 585),
			new Point(2777, 457),
			new Point(5926, 521),
			new Point(7833, 393),
			new Point(8473, 393),
			new Point(12891, 585),
			new Point(13954, 585),
			new Point(14170, 585),
			new Point(14873, 585),

		};
		
		
		for(int i = 0; i < points.length; i++ ) {
			 p = new Pet(tilemap);
			 p.setPosition(points[i].getX(), points[i].getY());
			 enemies.add(p);
		}
		
		
		
;
		
		
	}
	
	
	
	
	public void update() {
		handleInput();
		
		player.update();

		
		//mountains.setPosition(tileMap.getx(), tileMap.gety());
		tilemap.setPosition(
				GamePanel.WIDTH / 2 - player.getX(),
				GamePanel.height / 2 - player.getY()
				);
		
		//set background
		bg2.setPosition(tilemap.getX(), tilemap.getY());
		
		
		//attack enemies
		player.checkAttack(enemies);
		
		//update all enemies

		for(int i = 0; i < enemies.size(); i++) {
			Enemy e = enemies.get(i);
			e.update();
			if(e.isDead()) {
				enemies.remove(i);
				i--;
				explosions.add(
						new Explosion(e.getX(), e.getY())
						);
			}
			
			
		}
		
		//update explisions
		for(int i = 0; i < explosions.size(); i++) {
			explosions.get(i).update();
			if(explosions.get(i).shouldRemove()) {
				explosions.remove(i);
				i--;
			}
		}
		
		
	}
	
	public void draw(Graphics2D g) {
		
		bg.draw(g);
		bg2.draw(g);
		//draw tilemap
		tilemap.draw(g);
		
		
		//draw player
		player.draw(g);
		
		//draw enemies
		for(int i = 0; i < enemies.size(); i++) {
			enemies.get(i).draw(g);
		}
		
		//draw explosions
		for(int i = 0; i < explosions.size(); i++) {
			explosions.get(i).setMapPosition((int)tilemap.getX(), (int)tilemap.getY());
			explosions.get(i).draw(g);
		}
		
		
		//draw hud
		hud.draw(g);
		
	}
	
	
public void handleInput() {

		
		
	}
	
	
	
	public void keyPressed(int k) {

		if(k == KeyEvent.VK_LEFT) player.setLeft(true);
		if(k == KeyEvent.VK_RIGHT) player.setRight(true);
		if(k == KeyEvent.VK_UP) player.setUp(true);
		if(k == KeyEvent.VK_DOWN) player.setDown(true);
		if(k == KeyEvent.VK_SPACE || k == KeyEvent.VK_UP) player.setJumping(true);
		if(k == KeyEvent.VK_R) player.setAttacking(); 
	}
	
	public void keyReleased(int k) {
	
		if(k == KeyEvent.VK_LEFT) player.setLeft(false);
		if(k == KeyEvent.VK_RIGHT) player.setRight(false);
		if(k == KeyEvent.VK_UP) player.setUp(false);
		if(k == KeyEvent.VK_DOWN) player.setDown(false);
		if(k == KeyEvent.VK_SPACE || k == KeyEvent.VK_UP) player.setJumping(false);
	//	if(k == KeyEvent.VK_R) player.setAttacking(false); 
		
	}
	
}
