/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package object;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Rectangle;

/**
 * Platform class
 * Represents a platform that the player can stand or jump on.
 */
public class Platform {

    private int x, y;         // Position of the platform
    private int width, height; // Size of the platform

    /**
     * Constructor
     * @param x X position of the platform
     * @param y Y position of the platform
     * @param width Width of the platform
     * @param height Height of the platform
     */
    public Platform(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Get collision bounds for the platform
     * @return Rectangle representing the platform's area
     */
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    /**
     * Draw the platform on the screen
     * @param g Graphics object to draw with
     * @param cameraX Current camera X offset
     */
    public void draw(Graphics g, int cameraX) {
        g.setColor(Color.DARK_GRAY); // Platform color
        g.fillRect(x - cameraX, y, width, height); // Draw with camera offset
    }
}