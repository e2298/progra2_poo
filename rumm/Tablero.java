package rumm;

import javafx.scene.Node;
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

        tmp.setOnAction(event -> {
            moverCampo(tmp);
        });
        return tmp;
    }

    private void moverCampo(Button campo){
        if (Rummikub.getSeleccion()!= null) {

            int columna = getColumnIndex(Rummikub.getSeleccion());
            int fila = getRowIndex(Rummikub.getSeleccion());

            //si la ficha que se va a mover esta en el soporte
            if (Rummikub.getSoporte().getChildren().contains(Rummikub.getSeleccion())) {
                columna = Rummikub.getSoporte().getColumnIndex(Rummikub.getSeleccion());

                Rummikub.getSoporte().getChildren().remove(Rummikub.getSeleccion());

                //de soporte a tablero
                if (getChildren().contains(campo)) {
                    add(Rummikub.getSeleccion(), getColumnIndex(campo), getRowIndex(campo));
                }
                //de soporte a soporte
                else{
                    Rummikub.getSoporte().add(Rummikub.getSeleccion(), Rummikub.getSoporte().getColumnIndex(campo), 0);
                    Rummikub.getSoporte().getChildren().remove(campo);
                }
                Rummikub.getSoporte().add(crearBotonVacio(columna, 0), columna, 0);
            }
            //si esta en el tablero
            else {
                getChildren().remove(Rummikub.getSeleccion());
                //de tablero a tablero
                if (getChildren().contains(campo)) {
                    add(Rummikub.getSeleccion(), getColumnIndex(campo), getRowIndex(campo));
                    add(crearBotonVacio(columna, fila), columna, fila);
                }
                //de tablero a soporte
                else{
                    add(crearBotonVacio(columna, fila), columna, fila);
                    columna = Rummikub.getSoporte().getColumnIndex(campo);
                    Rummikub.getSoporte().add(Rummikub.getSeleccion(), columna ,0);
                    Rummikub.getSoporte().getChildren().remove(campo);
                }
            }

            getChildren().remove(campo);
        }
        isValido();
    }

    public Boolean isValido(){
        int reps;

        Ficha tmp;
        Ficha [][] tab = new Ficha[13][13];

        for (Node n : getChildren()){
            try{
                //para que de excepcion si no es ficha
                ((Ficha)n).getColor();
                tab[getRowIndex(n)][getColumnIndex(n)] = (Ficha)n;
            }
            catch ( Exception e){
            }
        }

        for (int fila = 0; fila<13; fila++){
            for (int columna = 0; columna<13; columna++){
                try{
                }
                catch (Exception e){
                }
            }
        }
        return true;
    }

}

