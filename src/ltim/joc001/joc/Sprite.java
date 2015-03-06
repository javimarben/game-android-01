/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ltim.joc001.joc;

import android.graphics.*;
import java.util.Random;

/**
 *
 * @author mascport
 */
public class Sprite {

    private int[] diranimacion = {3, 1, 0, 2};
    private Bitmap bmp;
    private int x;
    private int y;
    private int xPaso;
    private int yPaso;
    private int xVeloc = 0;
    private int yVeloc = 0;
    private VistaJuego gameView;
    private int sprwidth;
    private int sprheight;
    private int filas;
    private int columnas;
    private int ciclo;
    private boolean colisiona;
    private int colisiones;
    private int dibujo;

    public Sprite(VistaJuego gameView, int x, int y,
            int fil, int col, int dib) {
        dibujo = dib;
        colisiones = 0;
        this.x = x;
        this.y = y;
        this.filas = fil;
        this.columnas = col;
        Random ran = new Random();
        xPaso = ran.nextInt(8) + 1;
        yPaso = ran.nextInt(8) + 1;
        int sig = -1;
        if (ran.nextInt(2) == 0) {
            sig = 1;
        }
        xVeloc = xPaso * sig;
        sig = -1;
        if (ran.nextInt(2) == 0) {
            sig = 1;
        }
        yVeloc = yPaso * sig;
        this.gameView = gameView;
        bmp = BitmapFactory.decodeResource(gameView.getResources(),
                dibujo);
        this.sprwidth = bmp.getWidth() / columnas;
        this.sprheight = bmp.getHeight() / filas;
        ciclo = 0;
        colisiona = false;
    }

    private void update() {
        if (colisiona) {
            masColision();
        }
        if (x >= gameView.getWidth() - this.sprwidth) {
            xVeloc = -xPaso;
        }
        if (x <= 0) {
            xVeloc = xPaso;
        }
        if (y >= gameView.getHeight() - this.sprheight) {
            yVeloc = -yPaso;
        }
        if (y <= 0) {
            yVeloc = yPaso;
        }
        x = x + xVeloc;
        y = y + yVeloc;
    }

    public void onDraw(Canvas canvas) {
        update();
        int despx = ciclo * sprwidth;
        double dirDouble = (Math.atan2(xVeloc, yVeloc) / (Math.PI / 2) + 2);
        int direccion = diranimacion[(int) Math.round(dirDouble) % filas];
        int despy = direccion * sprheight;
        Rect src = new Rect(despx, despy, despx + sprwidth, despy + sprheight);
        ciclo++;
        if (ciclo == columnas) {
            ciclo = 0;
        }
        Rect dst = new Rect(x, y, x + sprwidth, y + sprheight);
        canvas.drawBitmap(bmp, src, dst, null);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        if (colisiona) {
            paint.setColor(Color.RED);
            canvas.drawRect(x, y, x + sprwidth, y + sprheight, paint);
        }
        paint.setColor(Color.GREEN);
        //String str = Integer.toString(colisiones);
        //canvas.drawText(str, x, y + sprheight + 12, paint);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        int bx = (int) (sprwidth *
                ((gameView.morir-colisiones) * 1.0 / gameView.morir));
        canvas.drawRect(x, y + sprheight + 3, x + bx, y + sprheight + 8, paint);
    }

    public boolean isCollition(float x2, float y2) {
        return x2 > x && x2 < x + sprwidth && y2 > y && y2 < y + sprheight;
    }

    public void invertir() {
        xVeloc = xVeloc * -1;
        yVeloc = yVeloc * -1;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSprWidth() {
        return sprwidth;
    }

    public int getSprHeight() {
        return sprheight;
    }

    public void setColisiona(boolean b) {
        colisiona = b;
    }

    public boolean getColisiona() {
        return colisiona;
    }

    public void masColision() {
        colisiones++;
    }

    public int getNumColisiones() {
        return colisiones;
    }
    
    public void poder(int dib) {
        if (dib == dibujo) {
            colisiones = 0;
        }
    }
}
