package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Spaceship extends Entity {
    GamePanel gamePanel;
    KeyHandler keyHandler;
    static final int DEATH_ANIMATION_SPEED_MULTIPLIER = 70;


    public Spaceship(GamePanel gamePanel, KeyHandler keyHandler){
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;

        TEXTURE_SHIFT_X = 15;
        TEXTURE_SHIFT_Y = 11;

        setDefaultValues();

        solidArea = new Rectangle(TEXTURE_SHIFT_X + x, TEXTURE_SHIFT_Y + y, 36, 38);

    }

    public void setDefaultValues(){
        x = 460;
        y = 380;
        speed = 3;
        direction = "standing";
        spriteCounter = 0;
        getPlayerImage();
    }

    public void getPlayerImage(){
        try {
            standing = ImageIO.read(new File("res/spaceship/spaceship_standing.png"));
            up = ImageIO.read(new File("res/spaceship/spaceship_up.png"));
            down = ImageIO.read(new File("res/spaceship/spaceship_down.png"));
            left = ImageIO.read(new File("res/spaceship/spaceship_left.png"));
            right = ImageIO.read(new File("res/spaceship/spaceship_right.png"));
            up_right = ImageIO.read(new File("res/spaceship/spaceship_up_right.png"));
            up_left = ImageIO.read(new File("res/spaceship/spaceship_up_left.png"));
            down_right = ImageIO.read(new File("res/spaceship/spaceship_down_right.png"));
            down_left = ImageIO.read(new File("res/spaceship/spaceship_down_left.png"));
            bonus = ImageIO.read(new File("res/spaceship/spaceship_explosion.png"));

        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public void getFinalPlayerImage(){
        try {
            up = ImageIO.read(new File("res/spaceship/spaceship_broken_1.png"));
            down = ImageIO.read(new File("res/spaceship/spaceship_broken_2.png"));
            left = ImageIO.read(new File("res/spaceship/spaceship_broken_3.png"));
            right = ImageIO.read(new File("res/spaceship/spaceship_broken_4.png"));
            end = ImageIO.read(new File("res/spaceship/spaceship_broken_5.png"));

        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public void update(){
        if(spriteCounter > 0) {
            if(spriteCounter == DEATH_ANIMATION_SPEED_MULTIPLIER) {
                direction = "down";
            }
            if(spriteCounter == DEATH_ANIMATION_SPEED_MULTIPLIER * 2) {
                direction = "explosion";
            }
            if(spriteCounter == DEATH_ANIMATION_SPEED_MULTIPLIER * 3) {
                direction = "left";
                x -= 32;
                y -= 32;
            }
            if(spriteCounter == DEATH_ANIMATION_SPEED_MULTIPLIER * 4) {
                direction = "right";
            }
            if(spriteCounter == DEATH_ANIMATION_SPEED_MULTIPLIER * 5) {
                direction = "death";
                x -= 64;
                y -= 64;
                spriteCounter = 1;
                solidArea.y = -100;
            }
            if(!direction.equals("death")){
                spriteCounter++;
            }
        } else {
            if(!keyHandler.upPressed && !keyHandler.downPressed && !keyHandler.leftPressed && !keyHandler.rightPressed){
                direction = "standing";
            } else if (keyHandler.upPressed && keyHandler.rightPressed){
                direction = "up_right";
                y -= speed;
                x += speed;
            } else if (keyHandler.upPressed && keyHandler.leftPressed) {
                direction = "up_left";
                y -= speed;
                x -= speed;
            } else if (keyHandler.downPressed && keyHandler.rightPressed) {
                direction = "down_right";
                y += speed;
                x += speed;
            } else if(keyHandler.downPressed && keyHandler.leftPressed){
                direction = "down_left";
                y += speed;
                x -= speed;
            } else if (keyHandler.upPressed) {
                direction = "up";
                y -= speed;
            } else if (keyHandler.downPressed) {
                direction = "down";
                y += speed;
            } else if (keyHandler.leftPressed) {
                direction = "left";
                x -= speed;
            } else if (keyHandler.rightPressed) {
                direction = "right";
                x += speed;
            }
        }

        if (x > gamePanel.screenWidth - gamePanel.SCREEN_SHIFT_X) {
            x = -gamePanel.SCREEN_SHIFT_X;
        } else if(x < -gamePanel.SCREEN_SHIFT_X){
            x = gamePanel.screenWidth - gamePanel.SCREEN_SHIFT_X;
        }
        if(y > gamePanel.screenHeight - gamePanel.SCREEN_SHIFT_Y) {
            y = gamePanel.screenHeight - gamePanel.SCREEN_SHIFT_Y;
        } else if(y < 0){
            y = 0;
        }
        if(!direction.equals("death")) {
            updateSolidArea();
        }
        collision = false;
        gamePanel.collisionChecker.checkCollision(this);
        if(collision && spriteCounter == 0){
            getFinalPlayerImage();
            direction = "up";
            spriteCounter++;
        }

    }

    public void draw(Graphics2D graphics2D){

        BufferedImage image = null;

        switch(direction){
            case "standing" -> image = standing;
            case "up" -> image = up;
            case "down" -> image = down;
            case "left" -> image = left;
            case "right" -> image = right;
            case "up_right" -> image = up_right;
            case "up_left" -> image = up_left;
            case "down_right" -> image = down_right;
            case "down_left" -> image = down_left;
            case "explosion" -> image = bonus;
            case "death" -> image = end;
        }

        graphics2D.drawImage(image, x, y, image.getWidth(), image.getHeight(), null);
    }
}