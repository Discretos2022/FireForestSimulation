package graphicEngine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyBoardInput implements KeyListener {

    //private static List<Integer> keys = new ArrayList<Integer>();
    //private static List<Integer> oldkeys = new ArrayList<>();

    private static boolean[] keysSys = new boolean[1000];

    public static boolean[] keys = new boolean[1000];
    public static boolean[] oldkeys = new boolean[1000];

    public static void update(){
        oldkeys = keys.clone();
        keys = keysSys.clone();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keysSys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keysSys[e.getKeyCode()] = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    public static boolean isPressed(int keycode){
        return keys[keycode];
    }

    public static boolean isOldPressed(int keycode){
        return oldkeys[keycode];
    }

}
