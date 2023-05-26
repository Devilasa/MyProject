package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class Sound {

    Clip clip;
    File song;

    public Sound() {
        try{
            song = new File("res/sound/GameSong.wav");
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public void setFile(){
        try{
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(song);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void play(){
        clip.start();
    }

    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop(){
        clip.stop();
    }
}
