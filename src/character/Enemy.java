/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package character;

/**
 *
 * @author yesho
 */
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Color;

public class Enemy {

    private int x, y;       // 坐标
    private int width = 40; // 宽
    private int height = 40; // 高

    // 构造函数
    public Enemy(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // 获取碰撞范围
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    // 绘制敌人
    public void draw(Graphics g, int cameraX) {
        g.setColor(Color.RED);
        g.fillRect(x - cameraX, y, width, height);
    }

    // 移动方法（向玩家移动）
    public void moveTowards(int targetX) {
        if (x < targetX) x += 2;
        else if (x > targetX) x -= 2;
    }

    // Getter / Setter
    public int getX() { return x; }
    public int getY() { return y; }
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
}