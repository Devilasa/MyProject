package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public boolean upPressed, downPressed, rightPressed, leftPressed;
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        int code = keyEvent.getKeyCode();
        switch (code) {
            case KeyEvent.VK_W, KeyEvent.VK_UP -> upPressed = true;
            case KeyEvent.VK_A, KeyEvent.VK_LEFT -> leftPressed = true;
            case KeyEvent.VK_S, KeyEvent.VK_DOWN -> downPressed = true;
            case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> rightPressed = true;

        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        int code = keyEvent.getKeyCode();
        switch (code) {
            case KeyEvent.VK_W, KeyEvent.VK_UP -> upPressed = false;
            case KeyEvent.VK_A, KeyEvent.VK_LEFT -> leftPressed = false;
            case KeyEvent.VK_S, KeyEvent.VK_DOWN -> downPressed = false;
            case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> rightPressed = false;
        }
    }
}
