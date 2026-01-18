/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package object;

import java.awt.Graphics;
import java.awt.Image;

public class Background {
    private Image img;

    public Background(Image img){
        this.img = img;
    }

    public void draw(Graphics g, int cameraX){
        g.drawImage(img, -cameraX, 0, null);
    }
}
