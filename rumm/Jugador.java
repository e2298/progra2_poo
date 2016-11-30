package rumm;

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

    private void anadirFicha(Ficha ficha){
        getFichas().add(ficha);
    }

}
