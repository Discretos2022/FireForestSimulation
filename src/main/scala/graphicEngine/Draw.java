package graphicEngine;

public class Draw extends Thread {

    private Thread t;
    private Main main;

    public Draw(Main main){
        this.main = main;
    }

    public void run() {
        while(true){main.Draw();}
    }

    public void start() {
        if (t == null) {
            t = new Thread(this);
            t.start();
        }
    }
}
