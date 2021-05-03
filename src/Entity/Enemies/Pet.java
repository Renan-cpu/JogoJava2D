package Entity.Enemies;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Entity.Animation;
import Entity.Enemy;
import TileMap.TileMap;

public class Pet extends Enemy {
	
	static BufferedImage[] sprites;

	public Pet(TileMap tm) {
		super(tm);
		
		
		moveSpeed = 0.4;
		maxSpeed = 0.5;
		fallSpeed = 0.2;
		maxFallSpeed = 10.0;
		
		width = 101;
		height = 90;
		
		cwidth = 50;
		cheight = 75;
		
		health = maxHealth = 5;
		damage = 1;
		
		
		//load sprites
		try {
			BufferedImage spritesheet  = ImageIO.read(
					getClass().getResourceAsStream(
							"/mobs/lata.png"
							)
					);
			
			
			sprites = new BufferedImage[3];
			for(int i = 0; i < sprites.length; i++) {
				sprites[i] = spritesheet.getSubimage(
						i * width,
						0,
						width,
						height
						);
			}
			
			
			
					
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		animation = new Animation();
		animation.setFrames(sprites);
		animation.setDelay(400);
		
		right = true;
		facingRight = true;
	}

	
	private void getNextPosition() {
		if(left) {
		
			dx -= moveSpeed;
			if(dx < -maxSpeed) {
				dx = -maxSpeed;
			}
		}
		else if(right) {
			dx += moveSpeed;
			if(dx > maxSpeed) {
				dx = maxSpeed;
			}
		}
		
		if(falling) {
			dy += fallSpeed;
		}
	}
	
	public void update() {
		
		System.out.println(health);
		System.out.println(dead);
		//update position
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		
		
		if(flinching) {
			long elapsed = (System.nanoTime() - flinchTimer) / 100000;
		
			if(elapsed > 400) {
				flinching = false;
			}
		
		}
		
		
		if(right && dx == 0) {
			right = false;
			left = true;
			facingRight = false;
		}
		else if( left && dx == 0) {
			right = true;
			left = false;
			facingRight = true;
		}
		
		
		animation.update();

	}
	
	
	public void draw(Graphics2D g) {
		
		//if(notOnScreen()) return;
		setMapPosition();
		
		super.draw(g);
	}
	
	
	
	
	
 }






