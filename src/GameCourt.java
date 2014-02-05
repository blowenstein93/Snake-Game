/**
 * CIS 120 HW10
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

import java.awt.*;

import java.awt.event.*;

import javax.swing.*;

import java.util.ArrayList;
import java.util.Collections;

/**
 * GameCourt
 * 
 * This class holds the primary game logic of how different objects 
 * interact with one another.  Take time to understand how the timer 
 * interacts with the different methods and how it repaints the GUI 
 * on every tick().
 *
 */
@SuppressWarnings("serial")
public class GameCourt extends JPanel {

	// the state of the game logic
	private ArrayList<Square> square;	// the snake, keyboard control
	private Snitch snitch;          // the powerup snitch
	private Food food;          // the food the snake eats
	private ArrayList<Wall> walls; // the walls on each level
	
	public boolean playing = false;  // whether the game is running
	private JLabel points;      // Current points text
	private int numPoints;		// records points
	private JLabel lives;		// Current lives text
	private int crntLives;		// Current Number of Lives
	private JLabel highScore;	// current highScore text
	private int numHighScore;	// records Highscore for session
	private int speedTimer;	// keeps track of speed boost
	private int blinker;	// keeps track of blinking
	private boolean ate; 	// records if snake just ate
	
	// Game constants
	public static final int COURT_WIDTH = 600;
	public static final int COURT_HEIGHT = 600;
	public int SQUARE_VELOCITY = 1;
	// Update interval for timer in milliseconds 
	public static int INTERVAL = 100;

	public GameCourt(JLabel points, JLabel lives, JLabel highScore){
		// creates border around the court area, JComponent method
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        // The timer is an object which triggers an action periodically
        // with the given INTERVAL. One registers an ActionListener with
        // this timer, whose actionPerformed() method will be called 
        // each time the timer triggers. We define a helper method
        // called tick() that actually does everything that should
        // be done in a single timestep.
		Timer timer = new Timer(INTERVAL, new ActionListener(){
			public void actionPerformed(ActionEvent e){
				tick();
			}
		});
		timer.start(); // MAKE SURE TO START THE TIMER!

		// Enable keyboard focus on the court area
		// When this component has the keyboard focus, key
		// events will be handled by its key listener.
		setFocusable(true);

		// this key listener makes the square move in the direction of
		// the last arrow key press, by changing the square's
		// velocity accordingly. (The tick method below actually 
		// moves the square.)
		addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				Square head = square.get(square.size() - 1);
				int keyCode = e.getKeyCode();
				if (keyCode == KeyEvent.VK_LEFT &&
						!(head.v_x > 0)) {
					head.v_x = -SQUARE_VELOCITY;
					head.v_y = 0;
				} else if (keyCode == KeyEvent.VK_RIGHT &&
						!(head.v_x < 0)) {
					head.v_x = SQUARE_VELOCITY;
					head.v_y = 0;
				} else if (keyCode == KeyEvent.VK_DOWN &&
						!(head.v_y < 0)) {
					head.v_y = SQUARE_VELOCITY;
					head.v_x = 0;
				} else if (keyCode == KeyEvent.VK_UP &&
						!(head.v_y > 0)) {
					head.v_y = -SQUARE_VELOCITY;
					head.v_x = 0;
				}
			}
		});
		ate = false;
		speedTimer = -1;
		blinker = 0;
		walls = new ArrayList<Wall>();
		square = new ArrayList<Square>();
		numPoints = 0;
		this.points = points;
		crntLives = 3;
		this.lives = lives;
		this.highScore = highScore;
		JOptionPane.showMessageDialog(null, 
				"Welcome to Snake!\nEat the blue food to make the snake grow"
				+ "\nand get points \n"
				+ "Use the arrow keys to control the snake", "Hello World", JOptionPane.PLAIN_MESSAGE);
		JOptionPane.showMessageDialog(null, 
				"Game Rules: \n The yellow snitch is a random power \n up that is either: \n"
				+ "1) Equivalent of eating 2 foods \n2) Inserts randomly placed obstacles"
				+ "\n3) The snake reverses its direction \n"
				+ "4) HyperSpeed, 2x your speed!", "Rules", JOptionPane.PLAIN_MESSAGE);
		JOptionPane.showMessageDialog(null, "Ready to Start?");
		} 
	
	
	private int getNewX(Square sq) {
		int init_x = sq.pos_x;
		if (sq.v_x > 0) init_x += sq.v_x * sq.width;
		else if (sq.v_x < 0) init_x += sq.v_x * sq.width;
		return init_x;
	}
	private int getNewY(Square sq) {
		int init_y = sq.pos_y;
		if (sq.v_y > 0) init_y += sq.v_y * sq.height;
		else if (sq.v_y < 0) init_y += sq.v_y * sq.height;
		return init_y;
	}

	private boolean selfIntersect() {
		boolean doesHit = false;
		for (int i = 0; i < square.size() - 2; i++) {
			doesHit = doesHit || 
			(square.get(i).willIntersect(square.get(square.size() - 1)));
		}
		return doesHit;
	}
	
	//prints a new food in a random location not ontop of the snake or walls
	private void resetFood() {
		food.pos_x = (int) ((COURT_WIDTH - 20) * Math.random());
		food.pos_y = (int) ((COURT_HEIGHT - 20) * Math.random());
		for (int i = 0; i < square.size(); i++) {
			if (square.get(i).intersects(food))
				resetFood();
		}
		for (int j = 0; j < walls.size(); j++) {
			if (walls.get(j).intersects(food))
				resetFood();
		}
	}
	
	private void addLink() {
		Square head = square.get(square.size() - 1);
		int init_x = getNewX(head);
		int init_y = getNewY(head);
		square.add(new Square(init_x, init_y,
				head.v_x, head.v_y, COURT_HEIGHT, COURT_WIDTH));
		resetFood();
	}
	
	private void winner() {
		numPoints += 10;
		ate = true;
		points.setText("Points: " + numPoints);
		addLink();
	}
	
	/** (Re-)set the state of the game to its initial state.
	 */
	public void reset() {
		//reset the food and snitch
		food = new Food(COURT_WIDTH, COURT_HEIGHT);
		snitch = new Snitch(COURT_WIDTH, COURT_HEIGHT);
		//resets the game display
		playing = true;
		points.setText("Points: " + numPoints);
		lives.setText("Lives: " + crntLives);
		//deletes all obstacles
		walls.clear();
		//resets any power-up timers/effects
		speedTimer = -1;
		SQUARE_VELOCITY = 1;
		//resets snake to just three squares
		square.clear();
		square.add(0, new Square(5, 5, SQUARE_VELOCITY, 0, COURT_WIDTH, COURT_HEIGHT));
		addLink();
		addLink();
		
		// Make sure that this component has the keyboard focus
		requestFocusInWindow();
	}
	
	private void loser() {
		//sets game display
		crntLives--;
		lives.setText("Lives: " + crntLives);
		// if no lives left ask if player wants to play again
		if (crntLives <= 0) {
			JOptionPane.showMessageDialog(null, "Your score was " + numPoints
					+ "\nReady to play again?");
		//set new high-score
			if (numPoints >= numHighScore) {
				numHighScore = numPoints;
				highScore.setText("HighScore: " + numHighScore);
			}
			numPoints = 0;
			crntLives = 3;
		} else if (crntLives < 3){
			JOptionPane.showMessageDialog(null, crntLives + 
					" More Chance(s)... Ready to Start Again?");
		}
		points.setText("Points: " + numPoints);
		reset();
	}
	
	private void powerUp() {
		int power = (int) (4 * Math.random());
		//gives player extra 20 pts and two segments
		if (power == 0) {
			winner();
			winner();
		}//inserts random wall obstacles 
		//between 300 and 100 pixels long
		else if (power == 1) {
			int p1x = (int) (200 * Math.random()) + 100;
			int p1y = (int) (200 * Math.random()) + 100;
			int width = 0;
			int height = 0;
			//randomly determine if horizontal or vertical
			if ((p1y % 2) == 0) {
				width = 2;
				height = (int) (400 * Math.random());
			} else {
				height = 2;
				width = (int) (400 * Math.random());
			}
			Wall powered = new Wall(p1x, p1y, width, height,
					COURT_WIDTH, COURT_HEIGHT);
			walls.add(powered);
		} //"bounce" back in opposite direction
		else if (power == 2) {
			Square tail = square.get(0);
			tail.v_x *= -1;
			tail.v_y *= -1;
			Collections.reverse(square);
		} // hyper-speed, speed doubles for set time
		else if (power == 3) {
			Square head = square.get(square.size() - 1);
			SQUARE_VELOCITY *= 2;
			head.v_y = head.v_y * 2;
			head.v_x = head.v_x * 2;
			speedTimer = 40;
		}
		//randomly move the snitch
		snitch.pos_x = (int) (COURT_WIDTH * Math.random());
		snitch.pos_y = (int) (COURT_WIDTH * Math.random());
	}
	
    /**
     * This method is called every time the timer defined
     * in the constructor triggers.
     */
	void tick(){
		if (playing) {
			//handles speedboost powerup
			if (speedTimer > 0) 
				speedTimer--;
			else if (speedTimer == 0) {
				Square head = square.get(square.size() - 1);
				SQUARE_VELOCITY /= 2;
				head.v_x /= 2;
				head.v_y /= 2;
				speedTimer--;
			}
			//Delete the last square and add a new one to the head
			Square prevHead = square.get(square.size() - 1);
			Square newHead = new Square(getNewX(prevHead), getNewY(prevHead),
					prevHead.v_x, prevHead.v_y, 
					COURT_WIDTH, COURT_HEIGHT); 
			square.remove(0);
			square.add(newHead);
		//	System.out.println("pos_x: " + newHead.pos_x + " pos_y: " + newHead.pos_y + 
			//		" v_x: " + newHead.v_x + " v_y: " + newHead.v_y + " num of squares: " + square.size());
			
			ate = false;
			//update location of the snitch
			snitch.move();

			// make the snitch bounce off walls...
			snitch.bounce(snitch.hitWall());
			// ...and the mushroom
			snitch.bounce(snitch.hitObj(food));
		
			// check game end condition... hit wall
			for (int i = 0; i < walls.size(); i++) {
				if (square.get(square.size() - 1).willIntersect(walls.get(i))) { 
				loser();
				}
			}
			// check more game end conditions, hit border or itself
			if (selfIntersect()) { 
				loser();
			} else if (square.get(square.size() - 1).hitWall() instanceof Direction) {
				loser();
			}// check for point winning conditions
			else if (square.get(square.size()-1).intersects(food)) {
				winner();
			} // check for power-up conditions
			else if (square.get(square.size() - 1).willIntersect(snitch)) {
				powerUp();
			}

			// update the displays
			repaint();
		}
	}


	@Override 
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		//draw all the squares in the snake
		for (int i = 0; i < square.size(); i++) 
			square.get(i).draw(g); 
		//draw all the walls
		for (int j = 0; j < walls.size(); j++)
			walls.get(j).draw(g);
		food.draw(g);
		snitch.draw(g);
		
		//draws head details
		if (ate)
			blinker = 2;
		if (blinker > 0)
			g.setColor(Color.BLACK);
		else g.setColor(Color.YELLOW);
		blinker--;
		square.get(square.size()-1).drawHead(g);
	}

	@Override
	public Dimension getPreferredSize(){
		return new Dimension(COURT_WIDTH,COURT_HEIGHT);
	}
}
