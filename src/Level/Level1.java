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
 *
 * @author yesho
 */
public class Level1 extends Level {

    public Level1() {
        // 背景
        Image bgImg = new ImageIcon("images/level1_bg.png").getImage();
        background = new Background(bgImg);

        // 平台
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
        levelWidth = 1400;

        // 碎片
        fragments.add(new Fragment(200, 450, "images/fragment1.png"));
        fragments.add(new Fragment(550, 350, "images/fragment1.png"));
        fragments.add(new Fragment(850, 300, "images/fragment1.png"));
        fragments.add(new Fragment(1150, 250, "images/fragment1.png"));
        fragments.add(new Fragment(1450, 300, "images/fragment1.png"));
        fragments.add(new Fragment(1700, 350, "images/fragment1.png"));
        fragments.add(new Fragment(1950, 450, "images/fragment1.png"));
        fragments.add(new Fragment(1150, 250, "images/fragment1.png"));
        fragments.add(new Fragment(1150, 250, "images/fragment1.png"));

        // 终点
        goal = new Goal(3200, 350, 50, 50);
    }
}
