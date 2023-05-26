package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class AlienShip extends Asteroid {

    int side;
    int func = 1;
    int counter = 0;
    public AlienShip(GamePanel gamePanel) {
        super(gamePanel);

        TEXTURE_SHIFT_X = 8;
        TEXTURE_SHIFT_Y = 23;
        RESPAWN_COUNTER_TARGET = 6;

        setDefaultValue();
        getAlienImage();

        solidArea = new Rectangle(x + TEXTURE_SHIFT_X, y + TEXTURE_SHIFT_Y, 50, 29);

    }

    @Override
    public void setDefaultValue() {
        Random rng = new Random();
        side = rng.nextInt(1,3);
        if(side == 1) {
            x = gamePanel.screenWidth + 40;
        } else {
            x = -40;
        }
        y = rng.nextInt(10, gamePanel.screenHeight - 20);
        speed = 1;
        direction = "up";
    }

    @Override
    public void respawn() {
        Random rng = new Random();
        side = rng.nextInt(1,3);
        if(side == 1) {
            x = gamePanel.screenWidth + 40;
        } else {
            x = -40;
        }
        y = rng.nextInt(10, gamePanel.screenHeight - 20);
        updateSolidArea();
        ++respawnCounter;
    }


    public void getAlienImage() {
        try {
            up = ImageIO.read(new File("res/asteroid/alienship_1.png"));
            down = ImageIO.read(new File("res/asteroid/alienship_2.png"));
            bonus = ImageIO.read(new File("res/asteroid/explosion.png"));

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        if(direction.equals("explosion")) {
            solidArea.y = -100;
            x += 1;
            y += 1;
        } else {
            if(side == 1) {
                if (func == 1) {
                    x -= speed + 1;
                    y -= speed;
                    if (counter > 100) {
                        func = 2;
                        counter = 0;
                    }
                } else {
                    x -= speed + 1;
                    y += speed;
                    if (counter > 100) {
                        func = 1;
                        counter = 0;
                    }
                }
            } else {
                if (func == 1) {
                    x += speed + 1;
                    y -= speed;
                    if (counter > 100) {
                        func = 2;
                        counter = 0;
                    }
                } else {
                    x += speed + 1;
                    y += speed;
                    if (counter > 100) {
                        func = 1;
                        counter = 0;
                    }
                }
            }
            ++counter;
        }
        if (x < -gamePanel.screenWidth + 100 || x > gamePanel.screenWidth + 100) {
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
            }
        }

        spriteCounter++;
        if(spriteCounter>28) {
            switch (direction) {
                case "up" -> direction = "down";
                case "down" -> direction = "up";
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
