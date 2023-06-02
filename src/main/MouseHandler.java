package main;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static main.GamePanel.*;

public class MouseHandler extends JFrame implements MouseListener {

    public String panelType = panel;
    @Override
    public void mousePressed(MouseEvent e) {

    }
    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }
    @Override
    public void mouseExited(MouseEvent e) {

    }
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        System.out.println(mouseEvent.getPoint());
        if(dashboardPlayButton.contains(mouseEvent.getPoint()) && panelType.equals("dashboard")) {
            panelType = "game";
            switchSong = true;
            retry = true;
            Username = textField.getText();
            removeTextField = true;
        }
        else if(dashboardLeaderboardButton.contains(mouseEvent.getPoint()) && panelType.equals("dashboard")) {
            panelType = "leaderboard";
        }
        else if(exitButton.contains(mouseEvent.getPoint()) && panelType.equals("dashboard")) {
            System.exit(0);
            //System.out.println("Coordinate: "+ mouseEvent.getPoint());
        }
        else if(leaderboardBackButton.contains(mouseEvent.getPoint()) && panelType.equals("leaderboard")) {
            panelType = "dashboard";
        }
        else if(tryAgainButton.contains(mouseEvent.getPoint()) && panelType.equals("gameover")) {
            panelType = "game";
            retry = true;
        }
        else if(gameOverBackButton.contains(mouseEvent.getPoint()) && panelType.equals("gameover")) {
            switchSong = true;
            panelType = "dashboard";
            addTextField = true;
        }
    }
}