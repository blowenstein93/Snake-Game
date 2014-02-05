
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/** A basic game object displayed as a yellow circle, starting in the 
 * upper left corner of the game court.
 *
 */
public class Snitch extends GameObj {
	private static final String img_file = "src/snitch.png";
	public static final int HEIGHT = 24; 
	public static final int WIDTH = 40;
	public static final int INIT_POS_X = 170;  
	public static final int INIT_POS_Y = 170; 
	public static final int INIT_VEL_X = -1;
	public static final int INIT_VEL_Y = 1;

	private static BufferedImage img;
	
	public Snitch(int courtWidth, int courtHeight) {
		super(INIT_VEL_X, INIT_VEL_Y, INIT_POS_X, INIT_POS_Y, 
				WIDTH, HEIGHT, courtWidth, courtHeight);
	
	try {
		if (img == null) {
			img = ImageIO.read(new File(img_file));
		}
	} catch (IOException e) {
		System.out.println("Internal Error: " + e.getMessage());
	}
}
	@Override
	public void draw(Graphics g) {
		g.drawImage(img, super.pos_x, super.pos_y, super.pos_x + WIDTH, 
				super.pos_y + HEIGHT, 0, 0, 
				1024, 482, null); 
	}



}