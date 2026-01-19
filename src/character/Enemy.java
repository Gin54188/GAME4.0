/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package character;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Color;

/**
 * Enemy class
 * Represents a basic enemy character in the game.
 * The enemy has a position, size, can be drawn on the screen, and can move toward the player.
 * 
 * Author: jingzhen
 */
public class Enemy {

    //Enemy position and size 
    private int x, y;    // Enemy coordinates (top-left corner)
    private int width = 40;  // Width of the enemy
    private int height = 40; // Height of the enemy

    // Constructor
    // Initialize enemy with starting coordinates
    public Enemy(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //Collision box
    // Return the rectangle representing the enemy's collision area
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    //Draw method
    //Draw the enemy as a red rectangle on the screen
    // cameraX is used to adjust for scrolling
    public void draw(Graphics g, int cameraX) {
        g.setColor(Color.RED);
        g.fillRect(x - cameraX, y, width, height);
    }

    // Movement method 
    // Move enemy toward a target x position (usually the player)
    public void moveTowards(int targetX) {
        if (x < targetX) x += 2; // Move right if target is to the right
        else if (x > targetX) x -= 2; // Move left if target is to the left
    }

    // Getters and Setters
    public int getX() { return x; }  // Get x-coordinate
    public int getY() { return y; }  // Get y-coordinate
    public void setX(int x) { this.x = x; } // Set x-coordinate
    public void setY(int y) { this.y = y; } // Set y-coordinate
}