/**
 * CIS 120 HW10

 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

// imports necessary libraries for Java swing
import java.awt.*;

import javax.swing.*;

/** 
 * Game
 * Main class that specifies the frame and widgets of the GUI
 */
public class Game implements Runnable {
    public void run(){
        // NOTE : recall that the 'final' keyword notes inmutability
		  // even for local variables. 

        // Top-level frame in which game components live
		  // Be sure to change "TOP LEVEL FRAME" to the name of your game
        final JFrame frame = new JFrame("Snake");
        frame.setLocation(300,300);
        frame.setBackground(Color.GRAY);

		  // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel highScore = new JLabel("HighScore: 0");
        status_panel.add(highScore);
        final JLabel status = new JLabel("");
        status_panel.add(status);
        final JLabel lives = new JLabel("Lives: 3");
        status_panel.add(lives);

        
        // Main playing area
        final GameCourt court = new GameCourt(status, lives, highScore);
        frame.add(court, BorderLayout.CENTER);

         // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
        // Start game
        court.reset();
    }

    /*
     * Main method run to start and run the game
     * Initializes the GUI elements specified in Game and runs it
     * NOTE: Do NOT delete! You MUST include this in the final submission of your game.
     */
    public static void main(String args[]){
        SwingUtilities.invokeLater(new Game());
        String s = "CIS120";
        System.out.println(s =="CIS120");
    }
}
