/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ltim.joc001.joc;

import android.graphics.Canvas;

/**
 *
 * @author mascport
 */
public class JuegoLoopThread extends Thread {

    private VistaJuego view;
    private boolean running = false;
    static final long FPS = 25;

    public JuegoLoopThread(VistaJuego view) {
        this.view = view;
    }

    public void setRunning(boolean run) {
        running = run;
    }

    @Override
    public void run() {
        long ticksPS = 1000 / FPS;
        long startTime;
        long sleepTime;
        while (running) {
            Canvas c = null;
            startTime = System.currentTimeMillis();
            try {
                c = view.getHolder().lockCanvas();
                synchronized (view.getHolder()) {
                    view.onDraw(c);
                }
            } finally {
                if (c != null) {
                    view.getHolder().unlockCanvasAndPost(c);
                }
            }
            sleepTime = ticksPS - (System.currentTimeMillis() - startTime);
            try {
                if (sleepTime > 0) {
                    sleep(sleepTime);                       
                } else {
                    sleep(10);
                }
            } catch (Exception e) {
            }
        }
    }
}
