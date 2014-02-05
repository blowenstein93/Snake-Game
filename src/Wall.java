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
public class Wall extends GameObj {
	
	private int x;
	private int y;
	private int gwallWidth;
	private int gwallHeight;
	
	public Wall(int x, int y, int wallWidth, int wallHeight, int courtWidth, int courtHeight) {
		super(0, 0, x, y, wallWidth, wallHeight, courtWidth, courtHeight);
		this.x = x;
		this.y = y;
		gwallWidth = wallWidth;
		gwallHeight = wallHeight;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillRect(x, y, gwallWidth, gwallHeight);
		
	}



}