package Entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class HUD {

	
	private Player player;
	
	private BufferedImage image;
	private Font font;
	
	
	public HUD(Player p) {
		player = p;
		
		try {
			image = ImageIO.read(
					getClass().getResourceAsStream(
							"/HUD/hud.gif"
							)
					);
			
			font = new Font("Sans-Serif", Font.BOLD, 25);
		
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void draw(Graphics2D g) {
		g.drawImage(image, 0, 20, 196, 100, null);
		g.setFont(font);
		g.setColor(Color.white);
		g.drawString(player.getHealth() *20 + "/" + (player.getMaxHealth() * 20) ,
				65, 52);
		
		
		}
	
}
