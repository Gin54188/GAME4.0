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
    
    public Image getImg() {
        return img;
    }


    public void draw(Graphics g, int cameraX, int panelWidth, int panelHeight){
        if (img == null) return;

        int imgWidth = img.getWidth(null);
        if (imgWidth <= 0) imgWidth = panelWidth;

        // 计算偏移量
        int xOffset = -cameraX % imgWidth;

        // 绘制两张背景图，循环拼接
        g.drawImage(img, xOffset, 0, imgWidth, panelHeight, null);
        g.drawImage(img, xOffset + imgWidth, 0, imgWidth, panelHeight, null);
    }
}