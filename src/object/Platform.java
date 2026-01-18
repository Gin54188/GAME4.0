/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package object;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Rectangle;

public class Platform {
    private int x, y, width, height;

    public Platform(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public void draw(Graphics g, int cameraX) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect(x - cameraX, y, width, height);
    }
}