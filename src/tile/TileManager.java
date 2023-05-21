package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.util.Random;
import java.util.random.RandomGenerator;

public class TileManager {
    GamePanel gamePanel;
    Tile[] tile;
    public final int tilesNumber = 10;
    int[] tracer;

    int[][] mapTileNumber;

    public TileManager (GamePanel gamePanel){
        this.gamePanel = gamePanel;

        tile = new Tile[10];
        tracer = new int[tilesNumber];
        mapTileNumber = new int[gamePanel.maxScreenCol][gamePanel.maxScreenRow];
        getTileImages();
        loadMap();
    }

    public void getTileImages(){

        try {
                tile[0] = new Tile();
                tile[0].image = ImageIO.read(new File("res/tiles/cielo_stellato_1.png"));

                tile[1] = new Tile();
                tile[1].image = ImageIO.read(new File("res/tiles/cielo_stellato_2.png"));

                tile[2] = new Tile();
                tile[2].image = ImageIO.read(new File("res/tiles/cielo_stellato_6.png"));

                tile[3] = new Tile();
                tile[3].image = ImageIO.read(new File("res/tiles/cielo_stellato_4.png"));

                tile[4] = new Tile();
                tile[4].image = ImageIO.read(new File("res/tiles/cielo_stellato_5.png"));

                tile[5] = new Tile();
                tile[5].image = ImageIO.read(new File("res/tiles/cielo_stellato_6.png"));

                tile[6] = new Tile();
                tile[6].image = ImageIO.read(new File("res/tiles/cielo_stellato_7.png"));

                tile[7] = new Tile();
                tile[7].image = ImageIO.read(new File("res/tiles/cielo_stellato_8.png"));

                tile[8] = new Tile();
                tile[8].image = ImageIO.read(new File("res/tiles/cielo_stellato_9.png"));

                tile[9] = new Tile();
                tile[9].image = ImageIO.read(new File("res/tiles/cielo_stellato_10.png"));


        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void loadMap(){
        RandomGenerator rng = RandomGenerator.getDefault();
        for(int row = 0; row < gamePanel.maxScreenRow; ++row){
            for(int col = 0; col < gamePanel.maxScreenCol; ++col){
                mapTileNumber[col][row] = rng.nextInt(0,tilesNumber);
            }
        }
    }

    public void updateMap(){
        for(int row = gamePanel.maxScreenRow - 1; row > 0; --row){
            for(int col = 0; col < gamePanel.maxScreenCol; ++col){
                mapTileNumber[col][row] = mapTileNumber[col][row-1];
            }
        }
        RandomGenerator rng = RandomGenerator.getDefault();
        for(int col = 0; col < gamePanel.maxScreenCol; ++col){
            int number = rng.nextInt(0,tilesNumber);
            if(number == 7 || number == 8){
                if(tracer[number] < 15){
                    ++tracer[number];
                    number = 3;
                } else {
                    tracer[number] = 0;
                }
            }
            mapTileNumber[col][0] = number;
        }
    }
    public void draw(Graphics2D graphics2D) {
        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        while (col < gamePanel.maxScreenCol && row < gamePanel.maxScreenRow) {
            int tileNumber = mapTileNumber[col][row];
            graphics2D.drawImage(tile[tileNumber].image, x, y, gamePanel.tileSize, gamePanel.tileSize, null);
            col++;
            x += gamePanel.tileSize;
            if(col == gamePanel.maxScreenCol){
                col = 0;
                x = 0;
                row++;
                y += gamePanel.tileSize;
            }
        }
    }
}
