package graphicEngine;

public class Update extends Thread {

    private Thread t;
    private Main main;

    public Update(Main main){
        this.main = main;
    }

    public void run() {
        while(true){main.Update();}
    }

    public void start() {
        if (t == null) {
            t = new Thread(this);
            t.start();
        }
    }
}
