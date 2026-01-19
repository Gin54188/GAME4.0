/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Game;

import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.ImageIcon;
import java.awt.*;
import java.awt.event.*;
import java.awt.Rectangle;
import java.awt.Font;
import java.util.*;
import java.io.*;
import java.util.List;
import java.util.ArrayList;
import Level.Level;
import Level.Level1;
import object.Platform;
import object.Fragment;
import object.Goal;
import object.Background;
import character.SpriteAnimation;

public class GamePanel extends JPanel {

    // Menu Resources 
    private ImageIcon menuBg;           // Menu background image
    private Image startButton;           // Start button image
    private Rectangle startBounds;       // Start button clickable area

    // Story Resources
    private Image[] storyImages1_9 = new Image[9];      // Story images for first 9 slides
    private String[] storyTexts1_9;        // Corresponding story texts
    private Image[] storyImages10_44 = new Image[35];   // Story images after level
    private String[] storyTexts10_44;         // Corresponding story texts after level
    private int storyIndex = 0;                    // Current story slide index
    private boolean afterLevel = false;       // Flag for post-level story

    //Player Animations 
    private SpriteAnimation idleAnimation, runAnimation, jumpAnimation, attackAnimation, currentAnimation;

    //Player Parameters 
    private int playerX = 100, playerY = 400, playerWidth = 64, playerHeight = 70; // Player position & size
    private int velX = 0, velY = 0;      // Player velocity
    private boolean jumping = false, facingRight = true; // Jump state & facing direction
    private int playerHealth = 100;      // Player health

    private final int jumpStrength = -12;   // Jump speed
    private final int gravity = 1;        // Gravity acceleration
    private final int groundY = 400;        // Y coordinate of ground

    // Level
    private Level currentLevel;      // Current level object
    private int cameraX = 0;        // Camera x-position
    private final int SCREEN_WIDTH = 1280;     // Screen width for camera calculations

    // Timer
    private Timer timer;     // Main game loop timer

    //Enemy List
    private List<Enemy> enemies = new ArrayList<>();    // List of enemies on screen

    //Example 2D Array
    private int[][] map = new int[10][10];   // Example map array (can be used for tiles)

    //Static Variables
    private static int totalMonstersSpawned = 0;   // Track total spawned monsters

    //Player Attack 
    private boolean attacking = false;  // Is the player attacking
    private int attackDuration = 10;    // Duration of attack
    private int attackTimer = 0;      // Countdown timer for attack animation

    //Constructor
    public GamePanel() { this(true); }

    public GamePanel(boolean loadResources) {
        setBackground(Color.BLACK);    // Set panel background
        setFocusable(true);       // Allow keyboard focus

        if (loadResources) loadAssets();  // Load images and animations if true
        currentLevel = new Level1();      // Initialize first level
        initMap();         // Initialize sample map

        //Game loop timer
        timer = new Timer(30, e -> {
            if (GameState.currentState == GameState.PLAYING) updateGame(); // Update game if in PLAYING state
            repaint();     // Redraw the screen
        });
        timer.start();

        //Keyboard input
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) { handleKeyPress(e); }     // Handle key press events
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_D) velX = 0; // Stop horizontal movement
            }
        });

        //Mouse input
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) { handleMousePress(e); } // Handle mouse clicks
        });
    }

    // ===== Load Images and Animations =====
    private void loadAssets() {
        menuBg = new ImageIcon("images/Interface.png");     // Menu background
        startButton = new ImageIcon("images/start.png").getImage();    // Start button image
        startBounds = new Rectangle(500, 500, 200, 80);           // Start button clickable area

        // Load first story images (1-9)
        for (int i = 0; i < 9; i++) storyImages1_9[i] = new ImageIcon("images/story" + (i + 1) + ".png").getImage();
        storyTexts1_9 = new String[]{        // Story text for first 9 images
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

        // Load post-level story images (10-44)
        for (int i = 0; i < 35; i++) storyImages10_44[i] = new ImageIcon("images/story" + (i + 10) + ".png").getImage();
        storyTexts10_44 = new String[]{         // Post-level story texts
            "We have recovered all the fragments of memory. Now, let's start reassembling them.",
            "Let's piece these fragments together.",
            "……","It seems this is Wang Quan Fugui's memory.","My name is Wang Quan Fugui, and I am a Daoist soldier.",
            "From childhood, I only did two things every day: practice swordsmanship and slay demons.",
            "Until I met her...","Hello... I... my name is Qingtong.","A monster dares to intrude here?","I came to show you something.",
            " ","This is......","You've never gone outside...","So, not all monsters are evil.","……",
            "……","You may leave.","Thank you!","……","How dare you let a monster go? You shall be punished here!","……",
            "? ? ? ? ? ? ? ? ? ? ? ? ? ? ?","What are you doing here?!!","I am here to help you.","Then they were discovered.",
            "You actually love a monster?","Now, if you kill her, I can forgive you.","……","!!!!!!!!!!!!!!",
            "Sorry father, I cannot do it.","If we could go outside...","Would you accompany me to see all these rivers and mountains?",
            "You are standing against everyone and facing death!","!!!!!!!!!","I hope we meet again in the next life....."
        };

        // Load player animations
        idleAnimation = new SpriteAnimation("images/player.png",64,80);
        runAnimation = new SpriteAnimation("images/run.png",80,80);
        jumpAnimation = new SpriteAnimation("images/jump.png",63,64);
        attackAnimation = new SpriteAnimation("images/attack.png",48,48); // Attack animation
        currentAnimation = idleAnimation;  // Set default animation
    }

    // Initialize example map 
    private void initMap() {
        for(int i=0;i<map.length;i++)
            for(int j=0;j<map[0].length;j++)
                map[i][j] = (i+j)%2;   // Simple checkerboard pattern
    }

    // Handle key presses
    private void handleKeyPress(KeyEvent e){
        // Story navigation
        if(GameState.currentState == GameState.STORY){
            if(e.getKeyCode() == KeyEvent.VK_SPACE){
                storyIndex++;
                if(!afterLevel && storyIndex >= storyImages1_9.length){
                    GameState.currentState = GameState.PLAYING; // Start game after pre-level story
                    storyIndex = 0; afterLevel = true;
                    playerX = 100; playerY = groundY;
                } else if(afterLevel && storyIndex >= storyImages10_44.length){
                    GameState.currentState = GameState.END;    // End game after post-level story
                }
            }
            return;
        }

        if(GameState.currentState != GameState.PLAYING) 
            return; // Ignore input if not playing

        // Horizontal movement
        if(e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT){ velX=-5; facingRight=false; }
        if(e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT){ velX=5; facingRight=true; }

        // Jump
        if(e.getKeyCode() == KeyEvent.VK_SPACE && !jumping){ velY=-12; jumping=true; }

        // Attack
        if(e.getKeyCode() == KeyEvent.VK_J) attackEnemies();
    }

    // ===== Handle mouse clicks =====
    private void handleMousePress(MouseEvent e){
        if(GameState.currentState == GameState.MENU && startBounds.contains(e.getPoint())){
            GameState.currentState = GameState.STORY; // Start story when start button clicked
            storyIndex = 0; afterLevel=false;
            requestFocusInWindow();       // Focus for keyboard input
        }
    }

    //Player attack logic
    private void attackEnemies(){
        attacking = true;// Set attacking flag to true
        attackTimer = attackAnimation.getFrameCount(); // Set attack duration
        attackAnimation.reset(); // Reset animation to first frame

        // Check collision with each enemy
        Iterator<Enemy> it = enemies.iterator();
        while(it.hasNext()){
            Enemy e = it.next();
            Rectangle attackRect; // Define the area of attack
            if(facingRight){
                attackRect = new Rectangle(playerX, playerY, playerWidth + 40, playerHeight);
            } else {
                attackRect = new Rectangle(playerX - 40, playerY, playerWidth + 40, playerHeight);
            }
            if(attackRect.intersects(e.getBounds())){
                it.remove(); // Remove enemy if hit
            }
        }
    }

    //Update game logic
    private void updateGame(){
        // Player movement and gravity
        playerX += velX; playerY += velY;   // Update player position by velocity
        if(playerY < groundY) velY += gravity;// Applyravity if above grou gnd
        else{ playerY = groundY; velY=0; jumping=false; }// Reset position if on ground

        // Update player animation
        if(attacking) {
            currentAnimation = attackAnimation;// Use attack animation
            attackAnimation.nextFrame();// Advance animation frame
        } else {
            currentAnimation = jumping ? jumpAnimation : (velX!=0?runAnimation:idleAnimation);
            currentAnimation.nextFrame();// Advance animation frame
        }

        // Platform collision
        for(Platform p:currentLevel.getPlatforms()){
            Rectangle pr = p.getBounds();// Get platform bounds
            Rectangle pb = new Rectangle(playerX, playerY+playerHeight, playerWidth,5);
            if(pb.intersects(pr) && velY>=0){  // Collision from top only
                playerY = pr.y-playerHeight;// Position player on top of platform
                velY=0; // Stop falling
                jumping=false;  // Reset jump state
            }
        }

        // Collect fragments
        for(Fragment f:currentLevel.getFragments()){
            if(f!=null && !f.isCollected() && new Rectangle(playerX,playerY,playerWidth,playerHeight).intersects(f.getBounds()))
                f.collect();     // Mark fragment as collected if player touches it
        }

        // Random enemy spawning
        if(Math.random()<0.01){   // 1% chance per frame
            enemies.add(new Enemy(playerX + SCREEN_WIDTH, groundY)); // Spawn enemy off-screen to the right
            totalMonstersSpawned++;  // Update total spawn counter
        }

        // Enemy logic
     // ===================  ===============
        /**
      * Author: google
      * link：https://www.google.com/search?q=Java+collision+detection+rectangle+intersects&sca_esv=c03ec876592c9bde&sxsrf=ANbL-n7MuSmMU3_sVwIiq9udV71gpPUPzA%3A1768796359946&ei=x7Btaem-OYqc0PEPk8urmA8&ved=0ahUKEwipvtv135aSAxUKDjQIHZPlCvMQ4dUDCBE&uact=5&oq=Java+collision+detection+rectangle+intersects&gs_lp=Egxnd3Mtd2l6LXNlcnAiLUphdmEgY29sbGlzaW9uIGRldGVjdGlvbiByZWN0YW5nbGUgaW50ZXJzZWN0czIFEAAY7wUyCBAAGKIEGIkFMggQABiiBBiJBTIFEAAY7wVI3AZQAFj8AXABeACQAQCYAe4BoAHuAaoBAzItMbgBA8gBAPgBAvgBAZgCAqAC-QGoAhDCAgcQIxgnGOoCwgINECMYgAQYJxiKBRjqAsICChAjGCcYyQIY6gLCAg0QIxjwBRgnGMkCGOoCwgIKECMY8AUYJxjqAsICFxAAGIAEGJECGLQCGOcGGIoFGOoC2AEBmAMI8QWVF2DSj1ve_roGBggBEAEYAZIHBTEuMC4xoAf9ArIHAzItMbgH8QHCBwUwLjEuMcgHCIAIAA&sclient=gws-wiz-serp
      */
        Iterator<Enemy> it = enemies.iterator();
        while(it.hasNext()){
            Enemy e = it.next();
            // Create a rectangle representing the player for collision checks
            if(new Rectangle(playerX,playerY,playerWidth,playerHeight).intersects(e.getBounds()) && attacking){
                it.remove();// Enemy destroyed
                continue;  // Skip remaining logic for this enemy
            }
             // If player collides with enemy without attacking, take damage
            if(new Rectangle(playerX,playerY,playerWidth,playerHeight).intersects(e.getBounds())){
                playerHealth-=1;// Decrease health by 1
                playerHealth=Math.max(playerHealth,0);// Prevent negative health
//========================================
                // Player death
                if(playerHealth <= 0){
                    System.out.println("Player died! Game Over.");
                    System.exit(0);// End game immediately
                }
            }
            // Enemy follows player
            if(e.x<playerX) e.x+=2;  // Move right towards player
            else if(e.x>playerX) e.x-=2;  // Move left towards player
        }

        // Attack timer countdown
        if(attacking) {
            attackTimer--;// Reduce remaining attack duration
            if(attackTimer<=0) attacking=false;// Stop attacking when timer runs out
        }

        // Update camera position
        cameraX = playerX - SCREEN_WIDTH/2;

        // Goal / level completion check
        if(currentLevel.getGoal()!=null){
            Rectangle playerRect = new Rectangle(playerX,playerY,playerWidth,playerHeight);
            if(playerRect.intersects(currentLevel.getGoal().getBounds())){
                boolean allCollected = true;// Assume all fragments collected
                for(Fragment f:currentLevel.getFragments())
                    if(f==null || !f.isCollected()){ allCollected=false; break; }
                if(allCollected){
                    GameState.currentState=GameState.STORY; // Transition to story after level
                    storyIndex=0; // Reset story index
                    System.out.println("Level Completed! Entering story...");
                    return;
                }
            }
        }

        // Output player data to file
        try(PrintWriter pw = new PrintWriter("playerData.txt")){
            pw.println("Health: "+playerHealth);// Save current health
            pw.println("Monsters spawned: "+totalMonstersSpawned);// Save total enemies spawned
        }catch(Exception ex){ ex.printStackTrace(); // Print any file writing errors
        }
    }

    // Paint components 
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);// Clear panel before drawing
        // Draw based on current game state
        switch(GameState.currentState){
            case GameState.MENU -> drawMenu(g); // Draw main menu
            case GameState.STORY -> drawStory(g);// Draw story slides
            case GameState.PLAYING -> drawLevel(g);// Draw game level
            case GameState.END -> drawEnd(g);// Draw end screen
        }
    }

    //Draw Menu
    private void drawMenu(Graphics g){
        g.drawImage(menuBg.getImage(),0,0,getWidth(),getHeight(),null);// Background
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 48));
        String title = "Traces of the Past"; // Game title
        int titleWidth = g.getFontMetrics().stringWidth(title);
        g.drawString(title, (getWidth() - titleWidth) / 2, 100);   // Draw centered title
        g.drawImage(startButton,startBounds.x,startBounds.y,startBounds.width,startBounds.height,null);// Start button
    }

    //Draw Story 
    private void drawStory(Graphics g){
        Image[] images = afterLevel?storyImages10_44:storyImages1_9;// Choose story images
        String[] texts = afterLevel?storyTexts10_44:storyTexts1_9;// Choose story text
        if(storyIndex>=images.length) return;   // Check index bounds
        g.drawImage(images[storyIndex],0,0,getWidth(),getHeight(),null);// Draw image
        g.setColor(new Color(0,0,0,180));  // Semi-transparent text box
        g.fillRect(0,getHeight()-120,getWidth(),120); // Semi-transparent text background
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial",Font.PLAIN,18));
        g.drawString(texts[storyIndex],40,getHeight()-60); // Draw story text
        g.drawString("Press SPACE to continue",getWidth()-260,getHeight()-20);// Prompt

    }

    //Draw Level
    private void drawLevel(Graphics g){
        // Background scrolling
        if(currentLevel.getBackground()!=null){
            Background bg = currentLevel.getBackground();
            int bgWidth = getWidth();// Width of the panel
            int x1 = -cameraX % bgWidth;    // Calculate background offset
            g.drawImage(bg.getImg(), x1, 0, bgWidth, getHeight(), null); // Draw first copy
            g.drawImage(bg.getImg(), x1 + bgWidth, 0, bgWidth, getHeight(), null);// Draw second copy for loop
        }

        // Draw platforms
        for(Platform p:currentLevel.getPlatforms()) p.draw(g,cameraX);
        for(Fragment f:currentLevel.getFragments()) f.draw(g,cameraX); // Draw fragments
        if(currentLevel.getGoal()!=null) currentLevel.getGoal().draw(g,cameraX);// Draw goal

        // Draw player
        Image frame = currentAnimation.getCurrentFrame();
        if(facingRight) g.drawImage(frame, playerX-cameraX, playerY, playerWidth, playerHeight, null);// Facing right
        else g.drawImage(frame, playerX-cameraX+playerWidth, playerY, -playerWidth, playerHeight, null);// Facing left (flip)

        // Draw enemies
        for(Enemy e:enemies) e.draw(g,cameraX);

        // Draw ground
        g.setColor(Color.GREEN);
        g.fillRect(0,groundY+playerHeight,getWidth(),20);// Ground rectangle

        // Draw health bar
        g.setColor(Color.RED);
        g.fillRect(getWidth()-210,20,Math.max(playerHealth*2,0),20);// Health proportional
        g.setColor(Color.WHITE);
        g.drawRect(getWidth()-210,20,200,20);// Health bar frame

        // Display controls
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString("A: Move Left  D: Move Right  SPACE: Jump  J: Attack", 20, 30);
    }

    //Draw End Screen
    private void drawEnd(Graphics g){
        g.setColor(Color.BLACK); // Clear screen
        g.fillRect(0,0,getWidth(),getHeight());
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial",Font.BOLD,36));
        g.drawString("You have collected all the fragments.",getWidth()/2-200,getHeight()/2);// End message
    }

    //Enemy Inner Class
    private class Enemy {
        int x, y, width=40, height=40;  // Enemy position & size
        public Enemy(int x,int y){ this.x=x; this.y=y; } // Constructor
        public Rectangle getBounds(){ return new Rectangle(x,y,width,height); } // Collision box
        public void draw(Graphics g,int cameraX){ g.setColor(Color.RED); g.fillRect(x-cameraX,y,width,height); } // Draw enemy
    }
}