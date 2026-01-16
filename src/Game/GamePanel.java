/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Game;

import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.ImageIcon;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.*;
import java.awt.Rectangle;

import character.SpriteAnimation;
/**
 *
 * @author yesho
 */
public class GamePanel extends JPanel {

    // ===== 菜单资源 =====
    private ImageIcon menuGif;
    private Image startButton;
    private Rectangle startBounds;

    // ===== 玩家动画 =====
    private SpriteAnimation idleAnimation;
    private SpriteAnimation runAnimation;
    private SpriteAnimation jumpAnimation;
    private SpriteAnimation currentAnimation;

    // ===== 玩家参数 =====
    private int playerX = 350;
    private int playerY = 250;
    private int playerWidth = 64;
    private int playerHeight = 70;

    private int velX = 0;
    private int velY = 0;
    private boolean jumping = false;

    private final int jumpStrength = -12;
    private final int gravity = 1;
    private final int groundY = 400;

    // ===== 角色朝向 =====
    private boolean facingRight = true;

    // ===== 定时器 =====
    private Timer timer;

    public GamePanel() {
        setBackground(Color.BLACK);
        setFocusable(true);

        // ===== 加载菜单资源 =====
        menuGif = new ImageIcon("images/Interface.gif");
        startButton = new ImageIcon("images/start.png").getImage();
        startBounds = new Rectangle(300, 350, 200, 80);

        // ===== 加载玩家动画 =====
        idleAnimation = new SpriteAnimation("images/player.png", 64, 80);
        runAnimation  = new SpriteAnimation("images/run.png", 80, 80);
        jumpAnimation = new SpriteAnimation("images/jump.png", 63, 64);

        currentAnimation = idleAnimation; // 默认idle

        // ===== 定时器刷新 =====
        timer = new Timer(30, e -> { // 30ms刷新一次
            if (GameState.currentState == GameState.PLAYING) {
                updateGame();
            }
            repaint();
        });
        timer.start();

        // ===== 键盘 =====
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (GameState.currentState != GameState.PLAYING) return;

                switch (e.getKeyCode()) {
                    case KeyEvent.VK_A -> {
                        velX = -5;        // 向左移动
                        facingRight = false; // 朝左
                    }
                    case KeyEvent.VK_D -> {
                        velX = 5;         // 向右移动
                        facingRight = true;  // 朝右
                    }
                    case KeyEvent.VK_SPACE -> {
                        if (!jumping) {
                            velY = jumpStrength;
                            jumping = true;
                        }
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_D) {
                    velX = 0; // 松开停止移动
                }
            }
        });

        // ===== 鼠标 =====
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (GameState.currentState == GameState.MENU) {
                    if (startBounds.contains(e.getPoint())) {
                        GameState.currentState = GameState.PLAYING;
                        requestFocusInWindow(); // 获得键盘焦点
                    }
                }
            }
        });
    }

    // ===== 更新玩家状态 =====
    private void updateGame() {
        // 水平移动
        playerX += velX;

        // 垂直跳跃+重力
        playerY += velY;
        if (playerY < groundY) {
            velY += gravity;
        } else {
            playerY = groundY;
            velY = 0;
            jumping = false;
        }

        // ===== 动画切换 =====
        if (jumping) {
            currentAnimation = jumpAnimation;
        } else if (velX != 0) {
            currentAnimation = runAnimation;
        } else {
            currentAnimation = idleAnimation;
        }

        // 切换动画帧
        currentAnimation.nextFrame();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (GameState.currentState == GameState.MENU) {
            drawMenu(g);
        } else if (GameState.currentState == GameState.PLAYING) {
            drawGame(g);
        }
    }

    // ===== 绘制菜单 =====
    private void drawMenu(Graphics g) {
        Image img = menuGif.getImage();
        g.drawImage(img, 0, 0, getWidth(), getHeight(), null); // 缩放GIF

        g.drawImage(startButton,
                startBounds.x,
                startBounds.y,
                startBounds.width,
                startBounds.height,
                null);
    }

    // ===== 绘制游戏 =====
    private void drawGame(Graphics g) {
        Image frame = currentAnimation.getCurrentFrame();
        if (frame == null) return;

        if (facingRight) {
            g.drawImage(frame, playerX, playerY, playerWidth, playerHeight, null);
        } else {
            // 朝左水平翻转
            g.drawImage(frame, playerX + playerWidth, playerY, -playerWidth, playerHeight, null);
        }

        // 画地面
        g.setColor(Color.GREEN);
        g.fillRect(0, groundY + playerHeight, getWidth(), 50);
    }
}