package rumm;

import javafx.scene.control.Button;
import javafx.scene.input.GestureEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

import static java.lang.System.exit;

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

    public GridPane getSoporte() {
        return soporte;
    }

    public void setSoporte(GridPane soporte) {
        this.soporte = soporte;
    }

    public Jugador(ArrayList fichas) {
        setFichas(new ArrayList());

        GridPane soporte = new GridPane();
        setSoporte(soporte);

        for(Object ficha: fichas){
            anadirFicha((Ficha)ficha);
        }
    }

    public void anadirFicha(Ficha ficha){
        int indice;

        getFichas().add(ficha);

        indice = getSoporte().getChildren().indexOf(null);
        if (indice == -1){
            indice = getSoporte().getChildren().size();
        }

        getSoporte().add(ficha.getBoton(), indice, 0);

    }

    public void quitarFicha(){

    }
}
