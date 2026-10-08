package com.mycompany.dungeongame;

import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Tejas Pahuja
 */
public class GamePanel extends JPanel implements KeyListener {

    private int playerX = 100;
    private int playerY = 100;
    private int playerHP = 10; // placeholder
    private int enemyX = 500;
    private int enemyY = 500;
    private Rectangle[] obstacles = {
        new Rectangle(0, 0, 10, 853),
        new Rectangle(0, 843, 883, 10),
        new Rectangle(873, 0, 10, 853),
        new Rectangle(0, 0, 883, 10),
        new Rectangle(300, 200, 100, 50), // placeholder
        new Rectangle(500, 100, 50, 150),
        new Rectangle(150, 350, 200, 40)
    };
    private Rectangle[] traps = {
        new Rectangle(100, 200, 50, 50) // placeholder
    };
    private boolean[] trapActive = new boolean[traps.length];
    private boolean battleOver;
       
    public GamePanel() {
        for (boolean trap : trapActive) {
            trap = true;
        }
        setFocusable(true);
        addKeyListener(this);
        this.setBackground(new Color(102, 102, 102));
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
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            newX -= 5;
        }

        if (e.getKeyCode() == KeyEvent.VK_RIGHT) { 
            newX += 5;
        }

        if (e.getKeyCode() == KeyEvent.VK_UP) {
            newY -= 5;
        }

        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            newY += 5;
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
            startBattle();
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
    
    public void startBattle() { // placeholder
        System.out.println("Battle");
    }
        
    @Override
    public void keyReleased(KeyEvent e) {
        
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }   
}
