package entity;



import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class BackDisplay extends Entity{

    public int scale = 0;
    GamePanel gamePanel;

    public BackDisplay(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        getImage();
        setDefaultValues();
    }

    public void setDefaultValues() {
        x = 910;
        y = 530;
    }

    public void getImage(){
        try {
            end = ImageIO.read(new File("res/spaceship/back.png"));

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        graphics2D.drawImage(end, x - scale / 2, y - scale / 2, end.getWidth() + scale - 50, end.getHeight() + scale - 50,
                null);

        if(scale < 500) {
            scale += 2;
        }
    }
}

