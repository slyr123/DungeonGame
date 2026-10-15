package com.mycompany.dungeongame;

import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;


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

    int playerX = 100;
    int playerY = 100;
    int playerHP = 10; // placeholder
    int enemyX = 500;
    int enemyY = 500;
    Rectangle[] obstacles = {
        new Rectangle(0, 0, 10, 700),
        new Rectangle(0, 690, 883, 10),
        new Rectangle(873, 0, 10, 700),
        new Rectangle(0, 0, 883, 10),
        new Rectangle(300, 200, 100, 50), // placeholder
        new Rectangle(500, 100, 50, 150),
        new Rectangle(150, 350, 200, 40)
    };
    Rectangle[] traps = {
        new Rectangle(100, 200, 50, 50) // placeholder
    };
    boolean[] trapActive = new boolean[traps.length];
    boolean battleOver;
       
    public GamePanel(DungeonGame game) {
        this.game = game;
        for (boolean trap : trapActive) {
            trap = true;
        }
        setFocusable(true);
        addKeyListener(this);
        this.setBackground(new Color(102, 102, 102));
    }
    
    public void generateDungeon() {
        
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLUE);
        g.fillRect(playerX, playerY, 50, 50);
        g.setColor(Color.RED);
        g.fillRect(enemyX, enemyY, 50, 50);
        g.setColor(Color.BLACK);
        for (Rectangle obstacle : obstacles) {
            g.fillRect(obstacle.x, obstacle.y, obstacle.width, obstacle.height);
        }
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
            if (player.intersects(obstacle)) {
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
    
    public void trapCollision(int x, int y) {
        Rectangle player = new Rectangle(x, y, 50, 50);
        int i = 0;
        for (Rectangle trap : traps) {
            if (player.intersects(trap) && trapActive[i]) {
                playerHP -= 5;                
            }
            i++;
        }
    }
                
    @Override
    public void keyReleased(KeyEvent e) {
        
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }   
}
