package Entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import Main.GamePanel;
import TileMap.Tile;
import TileMap.TileMap;

public abstract class MapObject {

	//map stuff
	protected TileMap tileMap;
	protected int tileSize;
	protected double xmap;
	protected double ymap;
	
	//positions
	protected double x;
	protected double y;
	protected double dx;
	protected double dy;
	
	
	//dimensions
	protected int width;
	protected int height;
	
	
	//collision box
	protected int cwidth;
	protected int cheight;
	
	// collision
	protected int currRow;
	protected int currCol;
	protected double xdest;
	protected double ydest;
	protected double xtemp;
	protected double ytemp;
	protected boolean topLeft;
	protected boolean topRight;
	protected boolean bottomLeft;
	protected boolean bottomRight;
	
	
	//animation
	protected Animation animation;
	protected int currentAction;
	protected int previousAction;
	protected boolean facingRight;
	
	
	
	//movement
	protected boolean left;
	protected boolean right;
	protected boolean up;
	protected boolean down;
	protected boolean jumping;
	protected boolean falling;
	protected boolean attacking;
	
	
	//movement physics
	protected double moveSpeed;
	protected double maxSpeed;
	protected double stopSpeed;
	protected double fallSpeed;
	//terminal velocity
	protected double maxFallSpeed;
	protected double jumpStart;
	protected double stopJumpSpeed;
	
	
	
	//construtor
	
	public MapObject(TileMap tm) {
		tileMap = tm;
		tileSize = tm.getTileSize();
		
		 
	}
	
	public boolean intersects(MapObject o) {
		Rectangle r1 = getRectangle();
		
		Rectangle r2 = o.getRectangle();
		
		return r1.intersects(r2);
	}
	
	public Rectangle getRectangle() {
	
		return new Rectangle(
				(int)x - cwidth,
				(int)y - cheight,
				cwidth,
				cheight
				);
	
	}
	
	
	
	public void calculateCorners(double x, double y ) {
		
		int leftTile = (int) (x - cwidth / 2) / tileSize;
		int rightTile = (int) (x + cwidth / 2 - 1) / tileSize;
		int topTile = (int) (y - cheight / 2) / tileSize;
		int bottomTile = (int) (y + cheight / 2 - 1) / tileSize;
	
		if(topTile < 0 || bottomTile >= (tileMap.getHeight() / tileMap.getTileSize()) ||
				leftTile < 0 || rightTile >= (tileMap.getWidth() / tileMap.getTileSize())){
				topLeft = topRight = bottomLeft = bottomRight = false;
				return;
				}
		
		
		//top left
		int tl = tileMap.getType(topTile, leftTile);
		//top right
		int tr = tileMap.getType(topTile, rightTile);
		
		int bl = tileMap.getType(bottomTile, leftTile);
		
		int br = tileMap.getType(bottomTile, rightTile);
		
		topLeft = tl == Tile.BLOCKED;
		topRight = tr == Tile.BLOCKED;
		bottomLeft = bl == Tile.BLOCKED;
		bottomRight = br == Tile.BLOCKED;
		
	}
	
	
	public void checkTileMapCollision() {
		
		currCol = (int)x / tileSize;
		currRow = (int)y / tileSize;
		
		xdest = x + dx;
		ydest = y + dy;
		
		xtemp = x;
		ytemp = y;
		
		int totalCol = tileMap.getWidth() / tileMap.getTileSize();
		int totalRow = tileMap.getHeight() / tileMap.getTileSize();
				
	
		
		calculateCorners(x, ydest);
		//para cima
		if(dy < 0) {
			//tocou em algo
			if(topLeft || topRight) {
				//para de andar nessa direção
				//dy = stopSpeed;
				dy = 0;
				ytemp = (currRow ) * tileSize + cheight / 2;
			
		
				
			}/*else if(currRow <= -4) {
				//ytemp = 5;
				dy = 0;
			}*/
			else {
				ytemp += dy;
				
			}
		}
		

		//caindo
		if(dy > 0) {
			
			//tocou em algo
			if(bottomLeft || bottomRight) {
				dy = 0;
				falling = false; 
				ytemp = (currRow + 1) * tileSize - cheight/2;
				
				
			}else if (currRow == totalRow + 3) {
				System.out.println("to no limbo");
				ytemp = 0;
				xtemp = 0;
				
			}
			//não tocou em nada
			else {
				ytemp += dy;
			}
		}
		
		calculateCorners(xdest, y);
		
		//para esquerda
		if(dx < 0) {
			//tocou na parede
			if(topLeft || bottomLeft){
				dx = 0;
				//seta xtemp para direita do tile
				//xtemp = currCol * tileSize - cwidth / 2;
			}
			/*else if(currCol <=0) {
				dx = 0;
			
			}*/
			
			else {
				xtemp += dx;
			}
		}
		
		//para direita
		if(dx > 0) {
			//tocou na parede
			if(topRight || bottomRight) {
				dx = 0;
				xtemp = (currCol+1) * tileSize - cwidth / 2;
			}/*else if (currCol == totalCol-1) {
				//xtemp = (totalCol - 1) / tileSize;
				dx = 0;
			}*/
			else {
				xtemp += dx;
			}
			;
		}
		
		if(!falling) {
			calculateCorners(x, ydest + 1);
			//não está em nenhum chão
			if(!bottomLeft && !bottomRight) {
				falling = true;
			}
		}
	}
	
	
	public int getX() {return (int)x; }
	public int getY() {return (int)y; }
	public int getHeight() {return height;}
	public int getWidth() {return width;}
	
	public int getCWidth() {return cwidth;}
	
	public int getCHeight() {return cheight;}
	
	public void setPosition(double x, double y) {
		this.x = x;
		this.y = y;
	}
    void setVector(double dx, double dy) {
		this.dx = dx;
		this.dy = dy;
	}
	
	
	//global position and localposition
	public void setMapPosition() {
		//designa onde o personagem vai ser desenhado
		xmap = tileMap.getX();
		ymap = tileMap.getY();
	
				
	}
	
	public void setLeft(boolean b) {left = b;}
	
	public void setRight(boolean b) {right = b;}
	
	public void setUp(boolean b) {up = b;}
	
	public void setDown(boolean b) {down = b;}
	
	public void setJumping(boolean b) {jumping = b;}

	//public void setAttacking(boolean b) {attacking = b;}
	
	public boolean notOnScreen() {
		//final position
		
		return x + xmap + width < 1 || //se o objeto está alem do lado esquedo
				x + xmap - width > GamePanel.WIDTH ||//se está além do lado direito
				y + ymap + height < 1 || //se está além do topo
				y + ymap - height > GamePanel.height; //se está além do chão
	}
	
	
	public void draw(Graphics2D g) {
		if(facingRight) {
			g.drawImage(
					animation.getImage(),
					(int) (x + xmap - width /2),
					(int) (y + ymap - height /2),
					null
					
					);
		}
		else {
			g.drawImage(
					animation.getImage(),
					(int)(x + xmap - width / 2 + width),
					(int)(y + ymap - height / 2),
					-width,
					height,
					null
					);
		}
	}
	
	
}
