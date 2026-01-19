/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Level;

import java.awt.Image;
import javax.swing.ImageIcon;

import object.Background;
import object.Platform;
import object.Fragment;
import object.Goal;

/**
 * Level1 class
 * This is a concrete implementation of a Level.
 * It defines the background, platforms, collectible fragments, and goal for the first level.
 * 
 * Author: jingzhen
 */
public class Level1 extends Level {

    //  Constructor
    public Level1() {
        // Background 
        // Load the background image for the level
        Image bgImg = new ImageIcon("images/level1_bg.png").getImage();
        background = new Background(bgImg); // Assign to Level's background

        // ----- Platforms -----
        // Add platforms with x, y, width, height
        // These platforms are where the player can stand or jump on
        platforms.add(new Platform(0, 500, 400, 20));
        platforms.add(new Platform(450, 400, 300, 20));
        platforms.add(new Platform(800, 350, 250, 20));
        platforms.add(new Platform(1100, 300, 200, 20));
        platforms.add(new Platform(1400, 350, 200, 20));
        platforms.add(new Platform(1650, 400, 200, 20));
        platforms.add(new Platform(1900, 500, 200, 20));
        platforms.add(new Platform(2100, 400, 200, 20));
        platforms.add(new Platform(2350, 400, 200, 20));
        platforms.add(new Platform(2600, 350, 200, 20));
        platforms.add(new Platform(2850, 300, 200, 20));
        platforms.add(new Platform(3100, 400, 200, 20));

        // Set the width of the level (used for camera and scrolling)
        levelWidth = 1400;

        // Fragments
        // Add collectible fragments at specific positions with an image
        fragments.add(new Fragment(200, 450, "images/fragment1.png"));
        fragments.add(new Fragment(550, 350, "images/fragment1.png"));
        fragments.add(new Fragment(850, 300, "images/fragment1.png"));
        fragments.add(new Fragment(1150, 250, "images/fragment1.png"));
        fragments.add(new Fragment(1450, 300, "images/fragment1.png"));
        fragments.add(new Fragment(1700, 350, "images/fragment1.png"));
        fragments.add(new Fragment(1950, 450, "images/fragment1.png"));
        fragments.add(new Fragment(1150, 250, "images/fragment1.png")); // Duplicate fragment
        fragments.add(new Fragment(1150, 250, "images/fragment1.png")); // Duplicate fragment

        // Goal 
        // Add the goal object, the endpoint of the level
        goal = new Goal(3200, 350, 50, 50); // x, y, width, height
    }
}