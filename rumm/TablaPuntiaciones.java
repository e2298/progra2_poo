package rumm;

import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class TablaPuntiaciones extends GridPane {
    //puntuaciones anteriores
    private ArrayList<Integer>[] punttuaciones;


    private ArrayList<Integer>[] getPunttuaciones() {
        return punttuaciones;
    }

    private void setPunttuaciones(ArrayList<Integer>[] punttuaciones) {
        this.punttuaciones = punttuaciones;
    }

    public TablaPuntiaciones(){
        super();

        ArrayList<Integer>[] p = new ArrayList[Rummikub.getNumeroJugadores()+1];

        setVgap(5);
        setHgap(50);

        //crea la matriz de puntuaciones
        for (int i = 0; i< Rummikub.getNumeroJugadores() ; i++){
            p[i+1] = new ArrayList<Integer>();
        }
        setPunttuaciones(p);
    }

    public void actualizar(){
        Text tmp;

        //se limpia
        getChildren().clear();

        //pone los nombres de los jugadores y pone las puntuaciones en la matriz
        for (int i = 0; i< Rummikub.getNumeroJugadores() ; i++){
            tmp = new Text("Jugador "+String.valueOf(i+1));
            tmp.setStyle("-fx-font:"+ String.valueOf(Rummikub.getAlto()/40) +" arial;");
            add(tmp, i+1, 0);

            getPunttuaciones()[i+1].add(Rummikub.getJugadores()[i].getPuntos());
        }


        for (int partida = 1; partida < getPunttuaciones()[1].size(); partida++) {
            //pone el numero de partida
            tmp = new Text("Partida " + String.valueOf(Rummikub.getNumeroPartida()));
            tmp.setStyle("-fx-font:" + String.valueOf(Rummikub.getAlto() / 40) + " arial;");
            add(tmp, 0, partida);

            //pone los puntos de todos los jugadores
            for (int jugador = 0; jugador < Rummikub.getNumeroJugadores(); jugador++) {
                //la puntuacion en una ronda es la total al final de la ronda - la total al final de la ronda anterior
                tmp = new Text(String.valueOf(getPunttuaciones()[jugador + 1].get(partida) - getPunttuaciones()[jugador + 1].get(partida - 1)));
                tmp.setStyle("-fx-font:" + String.valueOf(Rummikub.getAlto() / 40) + " arial;");
                add(tmp, jugador + 1, partida);
            }
        }

        tmp = new Text("Total:");
        tmp.setStyle("-fx-font:"+ String.valueOf(Rummikub.getAlto()/40) +" arial;");
        add(tmp, 0, getPunttuaciones()[1].size());

        //pone los totales (que son la puntuacion en la ultima ronda)
        for(int jugador = 0; jugador< Rummikub.getNumeroJugadores(); jugador++){
            tmp = new Text(String.valueOf(getPunttuaciones()[jugador+1].get(getPunttuaciones()[1].size()-1)));
            tmp.setStyle("-fx-font:"+ String.valueOf(Rummikub.getAlto()/40) +" arial;");
            add(tmp, jugador+1, getPunttuaciones()[1].size());
        }

    }
}
