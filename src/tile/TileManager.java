package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.util.random.RandomGenerator;

public class TileManager {
    GamePanel gamePanel;
    public Tile[] tile;
    public final int tilesNumber = 10;
    public int[] tracer;

    public int[][] mapTileNumber;
    public int y;

    public TileManager (GamePanel gamePanel){
        this.gamePanel = gamePanel;

        tile = new Tile[10];
        tracer = new int[tilesNumber];
        y = 0;
        getTileImages();
        loadMap();
    }

    public void getTileImages(){

        try {
                tile[0] = new Tile();
                tile[0].image = ImageIO.read(new File("res/tiles/sky_tile_1.png"));

                tile[1] = new Tile();
                tile[1].image = ImageIO.read(new File("res/tiles/sky_tile_2.png"));

                tile[2] = new Tile();
                tile[2].image = ImageIO.read(new File("res/tiles/sky_tile_3.png"));

                tile[3] = new Tile();
                tile[3].image = ImageIO.read(new File("res/tiles/sky_tile_4.png"));

                tile[4] = new Tile();
                tile[4].image = ImageIO.read(new File("res/tiles/sky_tile_5.png"));

                tile[5] = new Tile();
                tile[5].image = ImageIO.read(new File("res/tiles/sky_tile_6.png"));

                tile[6] = new Tile();
                tile[6].image = ImageIO.read(new File("res/tiles/sky_tile_7.png"));

                tile[7] = new Tile();
                tile[7].image = ImageIO.read(new File("res/tiles/sky_tile_8.png"));

                tile[8] = new Tile();
                tile[8].image = ImageIO.read(new File("res/tiles/sky_tile_9.png"));

                tile[9] = new Tile();
                tile[9].image = ImageIO.read(new File("res/tiles/sky_tile_10.png"));


        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void loadMap(){
        mapTileNumber = new int[gamePanel.maxScreenCol][gamePanel.maxScreenRow];
        RandomGenerator rng = RandomGenerator.getDefault();
        for(int row = 0; row < gamePanel.maxScreenRow; ++row){
            for(int col = 0; col < gamePanel.maxScreenCol; ++col){
                int number = rng.nextInt(0,tilesNumber);
                if(number == 7 || number == 8){
                    if(tracer[number] < 20){
                        ++tracer[number];
                        number = 3;
                    } else {
                        tracer[number] = 0;
                    }
                }
                mapTileNumber[col][row] = number;
            }
        }
    }

    public void draw(Graphics2D graphics2D) {
        int x1 = 0;
        int y1 = y;

        for(int row = 0; row < gamePanel.maxScreenRow; ++row){
            x1 = 0;
            if(row != 0) {
                y1 += gamePanel.tileSize;
            }
            for(int col = 0; col < gamePanel.maxScreenCol; ++col){
                graphics2D.drawImage((tile[mapTileNumber[col][row]]).image, x1, y1, gamePanel.tileSize, gamePanel.tileSize, null);
                x1 += gamePanel.tileSize;
            }
        }
    }
}
