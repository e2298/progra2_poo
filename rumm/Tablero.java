package rumm;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class Tablero extends GridPane {

    public Tablero(){
        super();

        Button tmp;

        for (int i1 = 0; i1<13; i1++){
            for(int i2 = 0; i2<13; i2++){
                add(crearBotonVacio(i1, i2), i1, i2);
            }
        }
    }

    private Button crearBotonVacio(int columna, int fila){
        final Button tmp = new Button();

        tmp.setMinWidth(Rummikub.getAlto()/15);
        tmp.setMinHeight(Rummikub.getAlto()/15);
        tmp.setMaxWidth(Rummikub.getAlto()/15);
        tmp.setMinHeight(Rummikub.getAlto()/15);
        tmp.setUserData(new Coordenadas(fila, columna));

        tmp.setOnAction(event -> {
            moverCampo(tmp);
        });
        return tmp;
    }

    private void moverCampo(Button campo){
        int columna = getColumnIndex(Rummikub.getSeleccion());
        int fila = getRowIndex(Rummikub.getSeleccion());

        add(crearBotonVacio(columna, fila), columna, fila);

        getChildren().remove(Rummikub.getSeleccion());
        add(Rummikub.getSeleccion(), ((Coordenadas)campo.getUserData()).getColumna(), ((Coordenadas)campo.getUserData()).getFila());
        Rummikub.getSoporte().getChildren().remove(Rummikub.getSeleccion());
        getChildren().remove(campo);
    }

    private class Coordenadas{
        private int fila;
        private int columna;

        public int getFila() {
            return fila;
        }

        public void setFila(int fila) {
            this.fila = fila;
        }

        public int getColumna() {
            return columna;
        }

        public void setColumna(int columna) {
            this.columna = columna;
        }

        public Coordenadas(int fila, int columna){
            setColumna(columna);
            setFila(fila);
        }
    }

}

