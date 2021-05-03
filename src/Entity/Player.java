package Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import TileMap.TileMap;

public class Player extends MapObject {
	
	private int health;
	private int maxHealth;
	private int power;
	private int maxPower;
	private boolean dead;
	private boolean dodging;
	private long dodgeTime;
	
	
	//atacks
	private boolean attacking;
	private int attackCost;
	private int attackDamage;
	private int attackRange;
	protected boolean flinching;
	protected long flinchTimer;
	
	private boolean scratching;
	private int scratchDamage;
	private int scratchRange;
	
	
	//animations
	public static ArrayList<BufferedImage[]> sprites;
	private final int[] numFrames = {
			4, 8, 3, 1, 9
	};
	
	public static final int IDLE = 0;
	public static final int WALKING = 1;
	private static final int JUMPING = 2;
	private static final int FALLING = 3;
	private static final int ATTACK = 4;
	private static final int SCRATCHING = 0;

	

	public Player(TileMap tm) {
		super(tm);
		// TODO Auto-generated constructor stub
		width = 128;
		height = 128;
		cwidth = 50;
		cheight = 110;
		
		moveSpeed = 3;
		maxSpeed = 3.4;
		stopSpeed = 0.85;
		fallSpeed = 0.32;
		maxFallSpeed = 8.5;
		jumpStart = -14.24;
		stopJumpSpeed = 0.3;
		
		
		facingRight = true;
		health = maxHealth = 5;
		
		scratchDamage = 8;
		scratchRange = 40;
		
		attackRange = 20;
		attackDamage = 1;
		
		//load sprites
		try {
			
			
			 //carrega sprite
			BufferedImage spritesheet = ImageIO.read(
					getClass().getResourceAsStream(
						//	"/person/persontilset.png"
							"/person/personwattack.png"
							)
					);
			
			sprites = new ArrayList<BufferedImage[]>();
			
			
				//recorda cada parte do sprite e coloca no vetor de animações
				for(int i = 0; i < 5; i++) {
					BufferedImage[] bi = new BufferedImage[numFrames[i]];
					
					for(int j = 0; j < numFrames[i]; j++ ) {
						
						if(i == 4) {
							bi[j] = spritesheet.getSubimage(
									j * 161,
									i * height,
									161,
									height
									);
							continue;
						}
				
						
						bi[j] = spritesheet.getSubimage(
								j * width,
								i * height,
								width,
								height
								);
						
						
						
					}
					
					
					sprites.add(bi);
				}
				
				
				
					
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		animation = new Animation();
		currentAction = IDLE;
		animation.setFrames(sprites.get(IDLE));
		animation.setDelay(150);
		
	}
	
	public int getHealth() {return health;}
	
	public int getMaxHealth() {return maxHealth;}
	
	public void setScratching() {
		scratching = true;
	}
	

	public void setAttacking() {
	
			attacking = true;
	
		
	
	}
	
	
	public void checkAttack(ArrayList<Enemy> enemies) {
		
		
		
		
		//loop enemies
		for(int i = 0; i < enemies.size(); i++) {
			
			Enemy e = enemies.get(i);
		
			if(attacking) {
				if(facingRight) {
					
					if(
							e.getX() > x &&
							e.getX() < x + attackRange &&
							e.getY() > y - height / 2 && 
							e.getY() < y + height / 2
							) {
						
						
						e.hit(attackDamage);
						
						
					}
					
					
				}else {
					
					
					if(
							e.getX() < x && 
							e.getX() > x - attackRange &&
							e.getY() > y - height / 2 &&
							e.getY() < y + height / 2
	 					  ) {
							e.hit(attackDamage);
						}  
					
				}
			}
			
			
			//check enemy collision
			if(intersects(e)) {
				hit(e.getDamage());
			}
			
		}
		
		
	
		
		
	}
	
	
	
	public void hit(int damage) {
		if(flinching) return;
		health -= damage;
		if(health < 0) health = 0;
		if(health == 0) dead = true;
		flinching = true;
		flinchTimer = System.nanoTime();
	
	}
	
	
	//verifica tecla apertada e move personagem
	private void getNextPosition() {
	
		//movement
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
		else {
			if(dx > 0) {
				dx -= stopSpeed;
				if(dx < 0) {
					dx = 0;
				}
			}
			
			else if(dx < 0) {
				dx += stopSpeed;
				if(dx > 0) {
					dx = 0;
					
				}
			}
		}
		
		
		
		
		//cannot move while attackng, except in air
		if(currentAction == ATTACK) {
		
			dx = 0;
			
			
		}
	
		
		//jumping
		if(jumping && !falling) {
			dy = jumpStart;
			falling = true;
		}
		
		//falling 
		if(falling) {
			
			dy += fallSpeed;
			
			if(dy > 0) jumping = false;
			
			if(dy < 0 && !jumping) dy += stopJumpSpeed;
			
			if(dy > maxFallSpeed) dy = maxFallSpeed;
		}
		
		
		if(attacking) {
			if(facingRight) {
				dx+= 3;
			}else {
				dx -= 3;
			}
		
		
		}
		
		
		
	}
	
	
	
	public void update() {
		
		//update position
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);						
		System.out.println(x);
		System.out.println(y);
		
		//set animations
	/*	if(scratching) {
			if(currentAction != SCRATCHING ) {
				currentAction = SCRATCHING;
				animation.setFrames(sprites.get(SCRATCHING));
				animation.setDelay(120);
				width = 161;
			}
		}*/
	
		
		if(currentAction == ATTACK) {
				if(animation.hasPlayedOnce()) {
					attacking = false;
		
				}
			}
		
		 if(attacking) {
			 if(currentAction != ATTACK) {
				 currentAction = ATTACK;
					animation.setFrames(sprites.get(ATTACK));
					animation.setDelay(50);
					width = 161;
				}
		 }
		 
	
		 else if(dy > 0) {
			if(currentAction != FALLING) {
			
		
				currentAction = FALLING;
				animation.setFrames(sprites.get(FALLING));
				animation.setDelay(-1);
				width = 128;
			
			
				
			
			}
			
		}
		else if(dy < 0) {
			if(currentAction != JUMPING) {
		
				currentAction = JUMPING;
				animation.setFrames(sprites.get(JUMPING));
				animation.setDelay(90);
				width = 128;
				
				
			}
		
		}
		 
	
			 
		else if(left || right) {
			if(currentAction != WALKING) {
			
				currentAction = WALKING;
				animation.setFrames(sprites.get(WALKING));
				animation.setDelay(60);
			
				width = 128;
				
			}
			
		}
		 
		
		
		 
		
		
		 
		else {
			
			if(currentAction != IDLE) {
				currentAction = IDLE;
				animation.setFrames(sprites.get(IDLE));
				animation.setDelay(150);
				width = 128;
				
			}
			
	
			
		}
		 
		//flinching

		 if(flinching) {
			 long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			 if(elapsed > 1000) {
				 flinching = false;
			 }
		 }
		 
		 animation.update();
		 //set direction
		 
		 if(currentAction != ATTACK ) {
			 if(right) facingRight = true;
			 if(left) facingRight = false;
		 }
		 
		
	}
	
	public void draw(Graphics2D g) {
		
		setMapPosition();
		
		//draw player
	super.draw(g);
	}
	
	

}
