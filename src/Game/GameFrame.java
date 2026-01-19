/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Game;

import javax.swing.JFrame;

/**
 * GameFrame class
 * This class creates the main window for the game.
 * It sets up the size, title, behavior, and adds the GamePanel where the game runs.
 * 
 * Author: yesho
 */
public class GameFrame extends JFrame {

    // Constructor =====
    public GameFrame() {
        // Set the window title
        setTitle("MyGame"); 

        // Set the size of the game window (width x height)
        setSize(1280, 720); 

        // Ensure the application exits when the window is closed
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 

        // Center the window on the screen
        setLocationRelativeTo(null); 

        // Add the main game panel to the frame
        // GamePanel contains all the game logic, rendering, and input handling
        add(new GamePanel()); 

        // Make the window visible
        setVisible(true); 
    }
}
