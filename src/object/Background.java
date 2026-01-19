/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package object;

import java.awt.Graphics;
import java.awt.Image;

/**
 * Background class
 * Handles a scrolling background image for the game.
 * Can draw the image repeatedly to create a looping effect.
 * 
 */
public class Background {
    
    private Image img; // The background image

    /**
     * Constructor: sets the background image
     * @param img Image object representing the background
     */
    public Background(Image img){
        this.img = img;
    }

    /**
     * Getter for the background image
     * @return the Image object
     */
    public Image getImg() {
        return img;
    }

    /**
     * Draw the background on screen with horizontal scrolling
     * @param g Graphics object to draw on
     * @param cameraX The current camera X offset (used for scrolling)
     * @param panelWidth Width of the panel/window
     * @param panelHeight Height of the panel/window
     * 
     * Author: google
     * link: https://www.google.com/search?q=Java+2D+scrolling+background&oq=Java+2D+scrolling+background&gs_lcrp=EgRlZGdlKgYIABBFGDkyBggAEEUYOTIHCAEQ6wcYQNIBBzU3OGowajGoAgCwAgA&sourceid=chrome&ie=UTF-8
     *
     */
    public void draw(Graphics g, int cameraX, int panelWidth, int panelHeight){
        if (img == null) return; // Do nothing if no image

        int imgWidth = img.getWidth(null); // Width of the image
        if (imgWidth <= 0) imgWidth = panelWidth; // Fallback to panel width

        // Calculate horizontal offset for looping background
        int xOffset = -cameraX % imgWidth;

        // Draw two copies of the image to create seamless looping
        g.drawImage(img, xOffset, 0, imgWidth, panelHeight, null);
        g.drawImage(img, xOffset + imgWidth, 0, imgWidth, panelHeight, null);
    }
}