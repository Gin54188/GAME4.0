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
        // 拉伸背景到整个面板
        g.drawImage(img, 0, 0, panelWidth, panelHeight, null);

    }

    // ===== 获取背景图片 =====
    public Image getImage() {
        return img;
    }
}