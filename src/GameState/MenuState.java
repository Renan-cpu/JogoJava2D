package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.KeyEvent;

import javax.swing.JComboBox.KeySelectionManager;

import TileMap.Background;
import handlers.Keys;

public class MenuState extends GameState {
	
	private Background bg;
	
	
	private int currentChoice = 0;
	private String[] options = {
			"Start",
			"Help",
			"Quit"
	};
	
	private Color titleColor;
	private Font titleFont;
	
	private Font font;
	
	

	public MenuState(GameStateManager gsm) {

	this.gsm = gsm;
	
	try {
	
		bg = new Background("/assets/backhome.png", 500);
		
		bg.setVector(-0.8, 0);
		
		titleColor = new Color(	43, 36, 34);		
		titleFont = new Font("Sans Serif", Font.PLAIN, 78);
	
		
		font = new Font("Arial", Font.PLAIN, 28);
		
	}catch(Exception e) {
		e.printStackTrace();
	}
	
	
}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		bg.update();
		//handleInput();
	}

	@Override
	public void draw(Graphics2D g) {
		// TODO Auto-generated method stub
		bg.draw(g);
		
		//draw title
		g.setColor(titleColor);
		g.setFont(titleFont);
		g.drawString("Proviroment", 440, 240);
		
		//draw menu option
		g.setFont(font);
		for(int i = 0; i < options.length; i++) {
			
		 	
			
			if(i == currentChoice) {
				g.setColor(new Color(173, 17, 55));
				g.fillRect(565, 323 + i * 45, 158, 35);
				g.setColor(new Color(94, 16, 35));
				
			}else {
			
				//g.setColor(new Color(117, 21, 44));
				g.clearRect(0, 0, 0, 0);
				g.setColor(new Color(173, 17, 55));
			}
			

			g.drawString(options[i], 615, 350 + i * 45);
		
		}
	}
	
	
	public void select() {
		if(currentChoice == 0) {
			gsm.setState(GameStateManager.LEVEL1STATE);
		}
		if(currentChoice == 1) {
			
		}
		if(currentChoice == 2) {
			System.exit(0);
		}
	}
	
	
	
	/*public void handleInput() {
		
		if(Keys.isPressed(Keys.ENTER)) select();
		if(Keys.isPressed(Keys.UP)) {
			if(currentChoice > 0) {
				currentChoice--;
			}
		}
		
		if(Keys.isPressed(Keys.DOWN)) {
			if(currentChoice < options.length - 1) {
				currentChoice++;
			}
		}
		
	}*/
	
	

	@Override
	public void keyPressed(int k) {
		// TODO Auto-generated method stub
		if(k == KeyEvent.VK_ENTER) {
			select();
		}
		if(k == KeyEvent.VK_UP) {
			currentChoice--;
			if(currentChoice == -1) {
				currentChoice = options.length - 1;
			}
		}
		if(k == KeyEvent.VK_DOWN) {
			currentChoice++;
			if(currentChoice == options.length) {
				currentChoice = 0;
			}
		}
	}

	@Override
	public void keyReleased(int k) {
		// TODO Auto-generated method stub
		
	}



	
}
