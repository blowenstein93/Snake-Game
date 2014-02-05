/**
 * CIS 120 HW10
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

import java.awt.*;

/** A basic game object displayed as a black square, starting in the 
 * upper left corner of the game court.
 *
 */
public class Square extends GameObj {
	public static int SIZE = 20;

	
    /** 
     * Note that because we don't do anything special
     * when constructing a Square, we simply use the
     * superclass constructor called with the correct parameters 
     */
    public Square(int x, int y, int v_x, int v_y,
    		int courtWidth, int courtHeight){
        super(v_x, v_y, x, y, 
        		SIZE, SIZE, courtWidth, courtHeight);
        }
    
    public void drawHead(Graphics g) {
    	int eye1x = super.pos_x;
    	int eye1y = super.pos_y;
    	int eye2x = super.pos_x;
    	int eye2y = super.pos_y;
    	if (super.v_x > 0) {
    		eye1y = super.pos_y;
    		eye2y = super.pos_y + super.height/2;
    		eye1x = super.pos_x + super.width - 5;
    		eye2x = super.pos_x + super.width - 5;
    	} else if (super.v_x < 0) {
    		eye1y = super.pos_y;
    		eye2y = super.pos_y + super.height/2;
    		eye1x = super.pos_x - 5;
    		eye2x = super.pos_x - 5;
    	} else if (super.v_y < 0) {
    		eye1y = super.pos_y - 5;
    		eye2y = super.pos_y - 5;
    		eye1x = super.pos_x;
    		eye2x = super.pos_x + super.width / 2;
    	} else if (super.v_y > 0) {
    		eye1y = super.pos_y + super.height - 5;
    		eye2y = super.pos_y + super.height - 5;
    		eye1x = super.pos_x;
    		eye2x = super.pos_x + super.width / 2;
    	}
    	g.fillOval(eye1x, eye1y, 10, 10);
    	g.fillOval(eye2x, eye2y, 10, 10);
    	g.setColor(Color.BLACK);
    	g.fillOval(eye1x + 2, eye1y + 2, 5, 5);
    	g.fillOval(eye2x + 2, eye2y + 2, 5, 5);
    	
    	//g.setColor(Color.RED);
    	//g.drawLine(tongueX, tongueY, tongueX + 10, tongueY + 10);
    	//g.drawArc(tongueX, tongueY, 10, 10, startDeg, endDeg);
    }
    
    @Override
    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(pos_x, pos_y, width, height);
        g.setColor(Color.BLACK);
        g.draw3DRect(pos_x, pos_y, SIZE, SIZE, false);
    }
    
}
