package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;


public class Sound {
    public static int songNumber = 2; // 1 = GameSong 2 = MenuSong
    Clip clip;
    File gameSong;
    File menuSong;

    AudioInputStream audioInputStream;

    public Sound() {
        try{
            gameSong = new File("res/sound/GameSong.wav");
            menuSong = new File("res/sound/MenuSong.wav");
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public void setFile(int songNumber){
        try{
            if(songNumber == 1) {
                audioInputStream = AudioSystem.getAudioInputStream(gameSong);
            } else {
                audioInputStream = AudioSystem.getAudioInputStream(menuSong);
            }
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void switchSong(){
        if(songNumber == 2){
            songNumber = 1;
        } else {
            songNumber = 2;
        }
        stopMusic();
        playMusic();
    }
    public void playMusic(){
        setFile(songNumber);
        play();
        loop();
    }

    public void play(){
        clip.start();
    }

    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stopMusic(){
        clip.stop();
    }
}

