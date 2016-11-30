package rumm;

import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;

public class TablaPuntiaciones extends GridPane {

    private ArrayList<Integer>[] punttuaciones;


    public ArrayList<Integer>[] getPunttuaciones() {
        return punttuaciones;
    }

    public void setPunttuaciones(ArrayList<Integer>[] punttuaciones) {
        this.punttuaciones = punttuaciones;
    }

    public TablaPuntiaciones(){
        super();

        Text tmp;

        ArrayList<Integer>[] p = new ArrayList[Rummikub.getNumeroJugadores()+1];

        setVgap(50);
        setHgap(20);
        for (int i = 0; i< Rummikub.getNumeroJugadores() ; i++){
            p[i+1] = new ArrayList<Integer>();
        }

        setPunttuaciones(p);
    }

    public void actualizar(){
        Text tmp;

        getChildren().clear();

        for (int i = 0; i< Rummikub.getNumeroJugadores() ; i++){
            tmp = new Text("Jugador "+String.valueOf(i+1));
            tmp.setStyle("-fx-font:"+ String.valueOf(Rummikub.getAlto()/40) +" arial;");
            add(tmp, i+1, 0);

            getPunttuaciones()[i+1].add(Rummikub.getJugadores()[i].getPuntos());
        }


        if (Rummikub.getNumeroPartida() != 0) {
            for (int partida = 1; partida < getPunttuaciones()[1].size(); partida++) {
                tmp = new Text("Partida " + String.valueOf(Rummikub.getNumeroPartida()));
                tmp.setStyle("-fx-font:" + String.valueOf(Rummikub.getAlto() / 40) + " arial;");
                add(tmp, 0, partida);

                for (int jugador = 0; jugador < Rummikub.getNumeroJugadores(); jugador++) {
                    tmp = new Text(String.valueOf(getPunttuaciones()[jugador + 1].get(partida) - getPunttuaciones()[jugador + 1].get(partida - 1)));
                    tmp.setStyle("-fx-font:" + String.valueOf(Rummikub.getAlto() / 40) + " arial;");
                    add(tmp, jugador + 1, partida);
                }
            }
        }


        tmp = new Text("Total:");
        tmp.setStyle("-fx-font:"+ String.valueOf(Rummikub.getAlto()/40) +" arial;");
        add(tmp, 0, getPunttuaciones()[1].size());

        for(int jugador = 0; jugador< Rummikub.getNumeroJugadores(); jugador++){
            tmp = new Text(String.valueOf(getPunttuaciones()[jugador+1].get(getPunttuaciones()[1].size()-1)));
            tmp.setStyle("-fx-font:"+ String.valueOf(Rummikub.getAlto()/40) +" arial;");
            add(tmp, jugador+1, getPunttuaciones()[1].size());
        }

    }
}
