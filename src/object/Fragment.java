/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package object;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class Fragment {
    private int x, y;
    private int width = 32, height = 32;
    private Image img;
    private boolean collected = false;

    public Fragment(int x, int y, String path) {
        this.x = x;
        this.y = y;
        img = new ImageIcon(path).getImage();
    }

    public void draw(Graphics g, int cameraX) {
        if (!collected) g.drawImage(img, x - cameraX, y, width, height, null);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public boolean isCollected() { return collected; }
    public void collect() { collected = true; }
}
