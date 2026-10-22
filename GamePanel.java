package com.mycompany.dungeongame;

import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import java.util.HashSet;
import java.util.Set;
import javax.swing.ImageIcon;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Tejas Pahuja
 */
public class GamePanel extends JPanel implements KeyListener {
    DungeonGame game;
    int playerX;
    int playerY;
    int enemyX;
    int enemyY;

    int playerHP = 10; // placeholder
    Rectangle[] obstacles = new Rectangle[16];
    Rectangle testPlayer;
    private Set<String> prevPath = new HashSet<>();
            
    
    boolean battleOver;
    
    private final ImageIcon playerImage =
        new ImageIcon(GamePanel.class.getResource("/images/player.png"));

    private final ImageIcon enemyImage =
        new ImageIcon(GamePanel.class.getResource("/images/enemy.png"));
       
    public GamePanel(DungeonGame game) {
        this.game = game;
        setFocusable(true);
        addKeyListener(this);
        this.setBackground(new Color(102, 102, 102));
        obstacles[0] = new Rectangle(0, 0, 10, 700);
        obstacles[1] = new Rectangle(0, 690, 900, 10);
        obstacles[2] = new Rectangle(890, 0, 10, 700);
        obstacles[3] = new Rectangle(0, 0, 900, 10);
        generateDungeon();
    }
    
    public final void generateDungeon() {
        Random rand = new Random();
        obstacles[4] = new Rectangle(5 * rand.nextInt(84) + 10, 10 * rand.nextInt(64) + 10,
        50, 50);
        playerX = obstacles[4].x;
        playerY = obstacles[4].y;
        testPlayer = new Rectangle(obstacles[4].x, obstacles[4].y,
        50, 50);
        obstacles[5] = new Rectangle(5 * rand.nextInt(85) + 400, 10 * rand.nextInt(64) + 10,
        50, 50);
        enemyX = obstacles[5].x;
        enemyY = obstacles[5].y;
        do {       
        for (int i = 6; i <= 15; i++) {
            obstacles[i] = new Rectangle(10 * rand.nextInt(85), 10 * rand.nextInt(65),
            3 * rand.nextInt(40) + 50 , 3 * rand.nextInt(40) + 50);
            for (int j = 0; j < i; j++) {
                if (obstacles[i].intersects(obstacles[j])) {
                    i--;
                    break;
                }    
            } 
        } 
        } while (!checkPath());
    }
    
    public boolean checkPath() {
        int newX = testPlayer.x;
        int newY = testPlayer.y;
        int oldX = testPlayer.x;
        int oldY = testPlayer.y;
        if (testPlayer.intersects(obstacles[5])) {
            return true;
        }
        for (int i = 0; i <= 3; i++) {
            switch (i) {
                case 0 : newX += 10; break;
                case 1 : newX -= 10; break;
                case 2 : newY += 10; break;
                case 3 : newY -= 10; break;
            }
            if (!collisionCheck(newX, newY) && !prevPath.contains(newX + "," + newY)) {
                prevPath.add(testPlayer.x + "," + testPlayer.y);
                testPlayer.x = newX;
                testPlayer.y = newY;
                System.out.println(newX+" "+newY);
                if (checkPath()) {
                    return true;
                }
            }
            newX = testPlayer.x;
            newY = testPlayer.y;
        }
        testPlayer.x = oldX;
        testPlayer.y = oldY;
        
        return false;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        for (Rectangle obstacle : obstacles) {
            if (obstacle != obstacles[4] && obstacle != obstacles[5]) {
                g.fillRect(obstacle.x, obstacle.y, obstacle.width, obstacle.height);
            }
        }
        g.drawImage(playerImage.getImage(), playerX, playerY, 50, 50, this);
        g.drawImage(enemyImage.getImage(), enemyX, enemyY, 50, 50, this);
        
        
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        int newX = playerX;
        int newY = playerY;
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            game.changeCard("MENU");
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            newX -= 10;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) { 
            newX += 10;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            newY -= 10;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            newY += 10;
        }
        if (!collisionCheck(newX, newY)) {
            playerX = newX;
            playerY = newY;
        }
        enemyCollision(newX, newY);
        repaint(); 
    }
    
    public boolean collisionCheck(int newX, int newY) {
        Rectangle player = new Rectangle(newX, newY, 50, 50);
        for (Rectangle obstacle : obstacles) {
            if (player.intersects(obstacle) && obstacle != obstacles[4] && obstacle != obstacles[5]) {
                return true;
            }
        }
        return false;
    }
      

    
    public void enemyCollision(int x, int y) {
        Rectangle player = new Rectangle(x, y, 50, 50);
        Rectangle enemy = new Rectangle(enemyX, enemyY, 50, 50);
        if (player.intersects(enemy)) {
            game.changeCard("COMBAT");
        }
    }
                 
    @Override
    public void keyReleased(KeyEvent e) {
        
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }   
}
