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
            p[i+1].add(0);
        }

        setPunttuaciones(p);
        actualizar();
    }

    public void actualizar(){
        Text tmp;

        getChildren().clear();

        for (int i = 0; i< Rummikub.getNumeroJugadores() ; i++){
            tmp = new Text("Jugador "+String.valueOf(i+1));
            tmp.setStyle("-fx-font:"+ String.valueOf(Rummikub.getAlto()/40) +" arial;");
            add(tmp, i+1, 0);

            getPunttuaciones()[i+1].add(Rummikub.getJugadores()[i].getPuntos());

            tmp = new Text(String.valueOf(0));
            tmp.setStyle("-fx-font:"+ String.valueOf(Rummikub.getAlto()/40) +" arial;");
            add(tmp,i+1,1);
        }


        tmp = new Text("Total:");
        tmp.setStyle("-fx-font:"+ String.valueOf(Rummikub.getAlto()/40) +" arial;");
        add(tmp, 0, 1);


    }
}
