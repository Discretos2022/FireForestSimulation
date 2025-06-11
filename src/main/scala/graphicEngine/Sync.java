package graphicEngine;

public class Sync {

    private long t1 = System.nanoTime();

    private long x1 = System.nanoTime();
    private long num = 0;
    private long x2 = 0;

    private long lastRender = 0;
    private float current_fps = 0;

    public void Sync(int fps) {
        Thread.yield();
        long t2 = 1000000000L / fps + t1;
        long now = System.nanoTime();

        /**
         * if(gapTo < now)
         * missed++;
         *
         * if(missed > 1){
         * System.out.println("[GraphicTimer] Missed frame (frame-rate too high)");
         * missed = 0;
         * }* */
        try {

            while (t2 > now) {
                Thread.sleep((t2 - now) / 10000000);
                now = System.nanoTime();
            }

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        t1 = now;
    }


    public void GetFPS(String Name){

        num += 1;

        if(num == 60){

            x2 = System.nanoTime();
            System.out.println(Name + " : " + (int)current_fps + " FPS | TIME : " + (float)(x2 - x1) / 1000000000L + " s");
            x1 = System.nanoTime();
            num = 0;

        }

        long now = System.nanoTime();

        if (lastRender != 0) {
            current_fps = 1000000000.0f / (now - lastRender);
        }
        lastRender = now;

    }


}
