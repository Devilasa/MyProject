package entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {
    public int x, y;
    public int speed;

    public BufferedImage standing;
    public BufferedImage up;
    public BufferedImage down;
    public BufferedImage left;
    public BufferedImage right;
    public BufferedImage bonus;

    public BufferedImage end;
    public String direction;

    public int spriteCounter = 0;
    public int spriteNumber = 1;

    public Rectangle solidArea;
    public boolean collision = false;

    int TEXTURE_SHIFT_X;
    int TEXTURE_SHIFT_Y;
    public void update(){
    }
    public void draw(Graphics2D graphics2D){
    }

    public void updateSolidArea(){
        solidArea.x = x + TEXTURE_SHIFT_X;
        solidArea.y = y + TEXTURE_SHIFT_Y;
    }
}
