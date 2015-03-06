package ltim.joc001;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import ltim.joc001.joc.VistaJuego;

public class Joc001 extends Activity {

    /**
     * Called when the activity is first created.
     */
    private VistaJuego juego;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        juego = new VistaJuego(this);
        setContentView(juego);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(menu.NONE, 0, 0, "Inicio");
        menu.add(menu.NONE, 1, 1, "Pumkies");
        menu.add(menu.NONE, 2, 2, "Angelitos");
        menu.add(menu.NONE, 3, 3, "Salir");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 0) {
            juego.parar();
            juego = new VistaJuego(this);
            setContentView(juego);
        } else if (item.getItemId() == 1) {
            juego.poderPumky();
        } else if (item.getItemId() == 2) {
            juego.poderAngel();
        } else if (item.getItemId() == 3) {
            juego.parar();
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
