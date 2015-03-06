/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ltim.joc001.joc;

import android.content.Context;
import android.graphics.*;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.util.ArrayList;
import java.util.Random;
import ltim.joc001.R;

/**
 *
 * @author mascport
 */
public class VistaJuego extends SurfaceView {
//public class VistaJuego extends View {

    private Bitmap bmp;
    public final int morir = 100;
    private ArrayList<Sprite> sprites;
    private SurfaceHolder holder;
    private JuegoLoopThread juegoLoopThread;

    public VistaJuego(Context cont) {
        super(cont);
        juegoLoopThread = new JuegoLoopThread(this);
        holder = getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {

            public void surfaceCreated(SurfaceHolder arg0) {
                juegoLoopThread.setRunning(true);
                juegoLoopThread.start();
            }

            public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
            }

            public void surfaceDestroyed(SurfaceHolder arg0) {
                juegoLoopThread.setRunning(false);
            }
        });
        sprites = new ArrayList<Sprite>();
        int x, y;
        Random ran = new Random();
        for (int i = 0; i < 10; i++) {
            x = ran.nextInt(200);
            y = ran.nextInt(200);
            sprites.add(new Sprite(this, x, y, 4, 3, R.drawable.bueno));
            sprites.add(new Sprite(this, x, y, 4, 3, R.drawable.malo));
        }
        bmp = BitmapFactory.decodeResource(this.getResources(), R.drawable.desierto);
    }

    protected void onDraw(Canvas canvas) {
        /*
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.YELLOW);
        canvas.drawRect(0, 0, this.getWidth(), this.getHeight(), paint);
         */
        Rect rorigen = new Rect(0, 0, bmp.getWidth(), bmp.getHeight());
        Rect rdestino = new Rect(0, 0, this.getWidth(), this.getHeight());
        canvas.drawBitmap(bmp, rorigen, rdestino, null);
        verColisiones();
        int x, y;
        for (int i = 0; i < sprites.size(); i++) {
            Sprite sprite = sprites.get(i);
            sprite.onDraw(canvas);
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        for (int i = sprites.size() - 1; i >= 0; i--) {
            Sprite sprite = sprites.get(i);
            if (sprite.isCollition(event.getX(), event.getY())) {
                sprite.invertir();
            }
        }
        return super.onTouchEvent(event);
    }

    private void verColisiones() {
        Sprite sprite, spraux;
        for (int i = 0; i < sprites.size(); i++) {
            sprites.get(i).setColisiona(false);
        }
        for (int i = 0; i < sprites.size(); i++) {
            sprite = sprites.get(i);
            for (int j = 0; j < sprites.size(); j++) {
                if (i != j) {
                    spraux = sprites.get(j);
                    if (colisionan(sprite, spraux)) {
                        sprite.setColisiona(true);
                        spraux.setColisiona(true);
                    }
                }
            }
        }
        int i = 0;
        while (i < sprites.size()) {
            if (sprites.get(i).getNumColisiones() >= morir) {
                sprites.remove(i);
            } else {
                i++;
            }
        }
    }

    private boolean colisionan(Sprite s1, Sprite s2) {
        boolean res = false;
        int x2, y2;
        int w2, h2;
        x2 = s2.getX();
        y2 = s2.getY();
        w2 = s2.getSprWidth();
        h2 = s2.getSprHeight();
        res = s1.isCollition(x2, y2) || res;
        res = s1.isCollition(x2 + w2, y2) || res;
        res = s1.isCollition(x2, y2 + h2) || res;
        res = s1.isCollition(x2 + w2, y2 + h2) || res;
        return res;
    }

    public void parar() {
        juegoLoopThread.setRunning(false);
    }

    public void poderPumky() {
        for (int i = 0; i < sprites.size(); i++) {
            sprites.get(i).poder(R.drawable.malo);
        }
    }

    public void poderAngel() {
        for (int i = 0; i < sprites.size(); i++) {
            sprites.get(i).poder(R.drawable.bueno);
        }
    }
}
