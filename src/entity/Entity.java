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
    public BufferedImage up_right;
    public BufferedImage up_left;
    public BufferedImage down_right;
    public BufferedImage down_left;
    public BufferedImage bonus;

    public BufferedImage end;
    public String direction;

    public int spriteCounter = 0;

    public Rectangle solidArea;
    public boolean collision = false;

    int TEXTURE_SHIFT_X;
    int TEXTURE_SHIFT_Y;
    public void update(){
    }
    public void draw(Graphics2D graphics2D){
    }

    public void setDefaultValue(){
    }

    public void updateSolidArea(){
        solidArea.x = x + TEXTURE_SHIFT_X;
        solidArea.y = y + TEXTURE_SHIFT_Y;
    }
}

