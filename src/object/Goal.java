/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package object;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Rectangle;

/**
 * Goal class
 * Represents the level’s endpoint that the player must reach.
 */
public class Goal {

    private int x, y;       // Position of the goal
    private int width, height; // Size of the goal

    /**
     * Constructor
     * @param x X position
     * @param y Y position
     * @param width Width of the goal
     * @param height Height of the goal
     */
    public Goal(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Get collision bounds for detecting player reach
     * @return Rectangle representing the goal's area
     */
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    /**
     * Draw the goal on the screen
     * @param g Graphics object to draw with
     * @param cameraX Current camera X offset
     */
    public void draw(Graphics g, int cameraX){
        g.setColor(Color.YELLOW); // Use yellow to highlight goal
        g.fillRect(x - cameraX, y, width, height);
    }
}