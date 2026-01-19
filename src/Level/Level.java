/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Level;


import java.util.ArrayList;
import object.Platform;
import object.Fragment;
import object.Goal;
import object.Background;

/**
 * Level class (abstract)
 * This class represents a generic game level.
 * It stores the platforms, collectible fragments, goal, background, and the width of the level.
 * Specific levels will extend this class and define their own content.
 * 
 * Author: jingzhen
 */
public abstract class Level {

    // ===== Level elements =====
    protected ArrayList<Platform> platforms = new ArrayList<>(); // List of platforms in this level
    protected ArrayList<Fragment> fragments = new ArrayList<>(); // List of collectible fragments in this level
    protected Goal goal;      // The goal object for completing the level
    protected Background background;             // The background image of the level
    protected int levelWidth;                // The horizontal width of the level

    // ===== Getters =====
    // Return the list of platforms in the level
    public ArrayList<Platform> getPlatforms() { 
        return platforms; 
    }

    // Return the list of fragments in the level
    public ArrayList<Fragment> getFragments() { 
        return fragments; 
    }

    // Return the goal object for this level
    public Goal getGoal() { 
        return goal; 
    }

    // Return the background object for this level
    public Background getBackground() { 
        return background; 
    }

    // Return the total width of the level
    public int getLevelWidth() { 
        return levelWidth; 
    }
}
