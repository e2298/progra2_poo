package rumm;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class Jugador {
    private ArrayList fichas;
    private int puntos;
    private GridPane soporte;

    public ArrayList getFichas() {
        return fichas;
    }

    public void setFichas(ArrayList fichas) {
        this.fichas = fichas;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public Pane getSoporte() {
        return soporte;
    }

    public void setSoporte(Pane soporte) {
        this.soporte = soporte;
    }

    public Jugador(ArrayList fichas) {
        setFichas(fichas);
        GridPane nuevo = new GridPane();



    }
}
