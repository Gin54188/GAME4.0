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
import java.awt.Font;

import character.SpriteAnimation;

import Level.Level;
import Level.Level1;
import object.Platform;
import object.Fragment;
import object.Goal;
import object.Background;
/**
 *
 * @author yesho
 */
public class GamePanel extends JPanel {

    // ===== 菜单资源 =====
    private ImageIcon menuBg;
    private Image startButton;
    private Rectangle startBounds;

    // ===== 剧情资源 =====
    private Image[] storyImages = new Image[9];
    private String[] storyTexts;
    private int storyIndex = 0;

    // ===== 玩家动画 =====
    private SpriteAnimation idleAnimation;
    private SpriteAnimation runAnimation;
    private SpriteAnimation jumpAnimation;
    private SpriteAnimation currentAnimation;

    // ===== 玩家参数 =====
    private int playerX = 100;
    private int playerY = 400;
    private int playerWidth = 64;
    private int playerHeight = 70;

    private int velX = 0;
    private int velY = 0;
    private boolean jumping = false;

    private final int jumpStrength = -12;
    private final int gravity = 1;
    private final int groundY = 400;

    private boolean facingRight = true;

    // ===== 关卡 =====
    private Level currentLevel;
    private int cameraX = 0;
    private final int SCREEN_WIDTH = 1280;

    private Timer timer;

    public GamePanel() {
        setBackground(Color.BLACK);
        setFocusable(true);

        // ===== 菜单资源 =====
        menuBg = new ImageIcon("images/Interface.png");
        startButton = new ImageIcon("images/start.png").getImage();
        startBounds = new Rectangle(300, 350, 200, 80);

        // ===== 剧情资源 =====
        for (int i = 0; i < 9; i++) {
            storyImages[i] = new ImageIcon("images/story" + (i + 1) + ".png").getImage();
        }
        storyTexts = new String[] {
            "This is a world where humans and demons coexist...",
            "And you are a Red Thread Immortal, your name is Bai Yuechu.",
            "Your partner is Susu, he likes to call you Daoist Brother.",
            "You two are the best combination.",
            "Your mission is to help fated lovers recover their memories.",
            "Hello, I am Wang Fugui.",
            "Hello, I am Qingtong.",
            "You will enter their memories. Be careful.",
            "Mission start!"
        };

        // ===== 玩家动画 =====
        idleAnimation = new SpriteAnimation("images/player.png", 64, 80);
        runAnimation  = new SpriteAnimation("images/run.png", 80, 80);
        jumpAnimation = new SpriteAnimation("images/jump.png", 63, 64);
        currentAnimation = idleAnimation;

        // ===== 关卡 =====
        currentLevel = new Level1();

        // ===== 定时器 =====
        timer = new Timer(30, e -> {
            if (GameState.currentState == GameState.PLAYING) {
                updateGame();
            }
            repaint();
        });
        timer.start();

        // ===== 键盘控制 =====
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

                // ===== 剧情空格切换 =====
                if (GameState.currentState == GameState.STORY) {
                    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                        storyIndex++;
                        if (storyIndex >= storyImages.length) {
                            GameState.currentState = GameState.PLAYING;
                            playerX = 100; // 重置玩家位置
                            playerY = groundY;
                        }
                    }
                    return;
                }

                if (GameState.currentState != GameState.PLAYING) return;

                switch (e.getKeyCode()) {
                    case KeyEvent.VK_A -> {
                        velX = -5;
                        facingRight = false;
                    }
                    case KeyEvent.VK_D -> {
                        velX = 5;
                        facingRight = true;
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
                    velX = 0;
                }
            }
        });

        // ===== 鼠标控制 =====
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (GameState.currentState == GameState.MENU) {
                    if (startBounds.contains(e.getPoint())) {
                        GameState.currentState = GameState.STORY;
                        storyIndex = 0;
                        requestFocusInWindow();
                    }
                }
            }
        });
    }

    private void updateGame() {
        // ===== 玩家移动 =====
        playerX += velX;
        playerY += velY;

        if (playerY < groundY) velY += gravity;
        else {
            playerY = groundY;
            velY = 0;
            jumping = false;
        }

        if (jumping) currentAnimation = jumpAnimation;
        else if (velX != 0) currentAnimation = runAnimation;
        else currentAnimation = idleAnimation;

        currentAnimation.nextFrame();

        // ===== 碰撞检测与碎片收集 =====
        for (Platform p : currentLevel.getPlatforms()) {
            Rectangle pr = p.getBounds();
            Rectangle pb = new Rectangle(playerX, playerY + playerHeight, playerWidth, 5);
            if (pb.intersects(pr) && velY >= 0) {
                playerY = pr.y - playerHeight;
                velY = 0;
                jumping = false;
            }
        }

        for (Fragment f : currentLevel.getFragments()) {
            if (!f.isCollected() && new Rectangle(playerX, playerY, playerWidth, playerHeight).intersects(f.getBounds())) {
                f.collect();
            }
        }

        // ===== 相机滚动 =====
        cameraX = playerX - SCREEN_WIDTH / 2;
        if (cameraX < 0) cameraX = 0;
        if (cameraX > currentLevel.getLevelWidth() - SCREEN_WIDTH)
            cameraX = currentLevel.getLevelWidth() - SCREEN_WIDTH;

        // ===== 判断是否到达终点 =====
        if (currentLevel.getGoal() != null) {
            if (new Rectangle(playerX, playerY, playerWidth, playerHeight)
                    .intersects(currentLevel.getGoal().getBounds())) {
                boolean allCollected = true;
                for (Fragment f : currentLevel.getFragments()) {
                    if (!f.isCollected()) allCollected = false;
                }
                if (allCollected) {
                    GameState.currentState = GameState.END;
                    System.out.println("Level Completed!");
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (GameState.currentState == GameState.MENU) {
            drawMenu(g);
        } else if (GameState.currentState == GameState.STORY) {
            drawStory(g);
        } else if (GameState.currentState == GameState.PLAYING) {
            drawLevel(g);
        } else if (GameState.currentState == GameState.END) {
            drawEnd(g);
        }
    }

    private void drawMenu(Graphics g) {
        g.drawImage(menuBg.getImage(), 0, 0, getWidth(), getHeight(), null);
        g.drawImage(startButton, startBounds.x, startBounds.y,
                startBounds.width, startBounds.height, null);
    }

    private void drawStory(Graphics g) {
        if (storyIndex >= storyImages.length) return;
        g.drawImage(storyImages[storyIndex], 0, 0, getWidth(), getHeight(), null);

        g.setColor(new Color(0, 0, 0, 180));
        g.fillRect(0, getHeight() - 120, getWidth(), 120);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 18));
        g.drawString(storyTexts[storyIndex], 40, getHeight() - 60);
        g.drawString("Press SPACE to continue", getWidth() - 260, getHeight() - 20);
    }

    private void drawLevel(Graphics g) {
        // 背景
        if (currentLevel.getBackground() != null) {
            currentLevel.getBackground().draw(g, cameraX);
        }

        // 平台
        for (Platform p : currentLevel.getPlatforms()) p.draw(g, cameraX);

        // 碎片
        for (Fragment f : currentLevel.getFragments()) f.draw(g, cameraX);

        // 终点
        if (currentLevel.getGoal() != null) currentLevel.getGoal().draw(g, cameraX);

        // 玩家
        Image frame = currentAnimation.getCurrentFrame();
        if (facingRight) {
            g.drawImage(frame, playerX - cameraX, playerY, playerWidth, playerHeight, null);
        } else {
            g.drawImage(frame, playerX - cameraX + playerWidth, playerY, -playerWidth, playerHeight, null);
        }

        // 地面
        g.setColor(Color.GREEN);
        g.fillRect(0, groundY + playerHeight, getWidth(), 50);
    }

    private void drawEnd(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 36));
        g.drawString("Level Completed!", getWidth()/2 - 150, getHeight()/2);
    }
}