package rumm;

import javafx.scene.control.Button;
import javafx.scene.input.GestureEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import java.util.ArrayList;

public class Jugador {
    private ArrayList fichas;
    private int puntos;


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

    public Jugador() {
        setFichas(new ArrayList());
    }

    public void ponerFichas(ArrayList fichas){
        for(Object ficha: fichas){
            anadirFicha((Ficha)ficha);
        }
    }

    public void anadirFicha(Ficha ficha){
        getFichas().add(ficha);
    }

    public void quitarFicha(Ficha ficha){
        getFichas().remove(ficha);
    }
}
