/**
 * CIS 120 HW10
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

import java.awt.*;

/** A basic game object displayed as a yellow circle, starting in the 
 * upper left corner of the game court.
 *
 */
public class Food extends GameObj {

	public static final int SIZE = 20;       
	public static final int INIT_POS_X = 170;  
	public static final int INIT_POS_Y = 170; 

	public Food(int courtWidth, int courtHeight) {
		super(0, 0, INIT_POS_X, INIT_POS_Y, 
				SIZE, SIZE, courtWidth, courtHeight);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.BLUE);
		g.fill3DRect(pos_x, pos_y, SIZE, SIZE, true);
		
	}



}