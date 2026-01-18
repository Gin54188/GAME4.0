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
 *
 * @author yesho
 */
public abstract class Level {
    protected ArrayList<Platform> platforms = new ArrayList<>();
    protected ArrayList<Fragment> fragments = new ArrayList<>();
    protected Goal goal;
    protected Background background;
    protected int levelWidth;

    public ArrayList<Platform> getPlatforms() { return platforms; }
    public ArrayList<Fragment> getFragments() { return fragments; }
    public Goal getGoal() { return goal; }
    public Background getBackground() { return background; }
    public int getLevelWidth() { return levelWidth; }
}
