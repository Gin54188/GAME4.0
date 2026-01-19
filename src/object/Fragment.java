/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package object;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

/**
 * Fragment class
 * Represents a collectible item in the game (like memory fragments).
 */
public class Fragment {

    private int x, y;           // Position of the fragment
    private int width = 32;      // Width of the fragment
    private int height = 32;     // Height of the fragment
    private Image img;           // Image representing the fragment
    private boolean collected = false; // Flag to check if collected

    /**
     * Constructor
     * @param x X position
     * @param y Y position
     * @param path Path to the fragment image
     */
    public Fragment(int x, int y, String path) {
        this.x = x;
        this.y = y;
        img = new ImageIcon(path).getImage();
    }

    /**
     * Draws the fragment if it hasn't been collected
     * @param g Graphics object to draw on
     * @param cameraX Current camera X offset
     */
    public void draw(Graphics g, int cameraX) {
        if (!collected) g.drawImage(img, x - cameraX, y, width, height, null);
    }

    /**
     * Get the collision bounds of the fragment
     * @return Rectangle representing the collision area
     */
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    /**
     * Check if the fragment has been collected
     * @return true if collected, false otherwise
     */
    public boolean isCollected() { return collected; }

    /**
     * Mark the fragment as collected
     */
    public void collect() { collected = true; }
}
