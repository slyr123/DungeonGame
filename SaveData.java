/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dungeongame;

/**
 *
 * @author Tejas Pahuja
 */
import java.io.Serializable;
import java.awt.Rectangle;

public class SaveData implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public int playerX;
    public int playerY;
    public int playerHP;
    public int enemyX;
    public int enemyY;
    public Rectangle obstacles[];

    public SaveData(int playerX, int playerY, int playerHP, int enemyX,
        int enemyY, Rectangle[] obstacles) {
        this.playerX = playerX;
        this.playerY = playerY;
        this.playerHP = playerHP;
        this.enemyX = enemyX;
        this.enemyY = enemyY;
        for (int i = 0; i < obstacles.length; i++) {
            this.obstacles = obstacles;
        }
    }
}

