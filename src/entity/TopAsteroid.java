package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class TopAsteroid extends Asteroid{

    public TopAsteroid(GamePanel gamePanel){
        super(gamePanel);

        TEXTURE_SHIFT_X = 18;
        TEXTURE_SHIFT_Y = 26;
        RESPAWN_COUNTER_TARGET = 8;
        setDefaultValue();
        solidArea = new Rectangle( x + TEXTURE_SHIFT_X, y + TEXTURE_SHIFT_Y, 30, 29);
        getAsteroidImage();
    }

    @Override
    public void setDefaultValue() {
        Random rng = new Random();
        x = rng.nextInt(0, gamePanel.screenWidth);
        y = -50;
        speed = 3;
        direction = "up";
    }

    @Override
    public void respawn() {
        Random rng = new Random();
        x = rng.nextInt(0, gamePanel.screenWidth);
        y = -50;
        updateSolidArea();
        ++respawnCounter;
    }

    @Override
    public void getAsteroidImage() {
        try {
            up = ImageIO.read(new File("res/asteroid/asteroid_top_1.png"));
            right = ImageIO.read(new File("res/asteroid/asteroid_top_2.png"));
            down = ImageIO.read(new File("res/asteroid/asteroid_top_3.png"));
            left = ImageIO.read(new File("res/asteroid/asteroid_top_4.png"));
            bonus = ImageIO.read(new File("res/asteroid/explosion.png"));

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void update(){
        if(direction.equals("explosion")) {
            solidArea.y = -100;
            x += 1;
            y += 1;
        } else {
            y += speed;
        }
        if (x > gamePanel.screenWidth*2) {
            respawn();
        }
        if (y > gamePanel.screenHeight*2) {
            respawn();
        }
        if(!direction.equals("explosion")){
            updateSolidArea();
        }

        gamePanel.collisionChecker.checkCollision(this);
        if(collision) {
            direction = "explosion";
            if(collisionCounter == 1){
                solidArea.y = -100;
            }
            if (collisionCounter == 60) {
                collisionCounter = 0;
                collision = false;
                direction = "up";
                respawn();
            } else {
                collisionCounter++;
                System.out.println("Boom!");
            }
        }

        spriteCounter++;
        if(spriteCounter>10) {
            switch (direction) {
                case "up" -> direction = "right";
                case "right" -> direction = "down";
                case "down" -> direction = "left";
                case "left" -> direction = "up";
            }
            spriteCounter = 0;
        }

        if(respawnCounter > RESPAWN_COUNTER_TARGET){
            respawnCounter = 0;
            ++speed;
        }
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        super.draw(graphics2D);
    }
}
