package graphicEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

import simulation.Simulator;

public class Main extends JPanel{


    private long x1 = System.nanoTime();
    private long num = 0;
    private long x2 = 0;

    private long lastRender = 0;
    private float current_fps = 0;

    private JFrame frame;


    private Update updateThread;
    private Draw drawThread;

    private Sync updateSync;
    private Sync drawSync;


    public void Init(){

        frame = new JFrame("Simulation");
        frame.setSize(1920/2, 1080/2 + 32);
        frame.setResizable(true);

        //frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        //frame.setUndecorated(true);

        this.setBackground(Color.BLACK);
        this.setPreferredSize(new Dimension(1920/2, 1080/2));
        frame.setContentPane(this);

        frame.addKeyListener(new KeyBoardInput());


        // Hide cursor
        Toolkit tk = frame.getToolkit();
        Cursor transparent = tk.createCustomCursor(tk.getImage(""), new Point(), "trans");
        frame.setCursor(transparent);
        frame.setVisible(true);

        updateSync = new Sync();
        drawSync = new Sync();

        updateThread = new Update(this);
        updateThread.start();
        drawThread = new Draw(this);
        drawThread.start();

        Simulator.init();


    }

    public void Update(){

        KeyBoardInput.update();
        Simulator.update();

        if(KeyBoardInput.isPressed(KeyEvent.VK_ESCAPE))
            System.exit(0);

        updateSync.Sync(500); // 244
        updateSync.GetFPS("Update");

    }

    public void Draw(){

        repaint();

        drawSync.Sync(60);
        drawSync.GetFPS("Draw");

    }

    int angle = 0;
    Graphics2D g2d;
    public void paint (Graphics g) {
        super.paint(g);

        if(g2d == null){
            g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }
        g2d = (Graphics2D) g;

        Simulator.draw(g2d);

    }






}
