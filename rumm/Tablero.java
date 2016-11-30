package rumm;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.Collections;


public class Tablero extends GridPane {

    public Tablero(){
        super();
    }

    //boton sin texto que mueve una ficha seleccionada a su posicion
    private Button crearBotonVacio(int columna, int fila){
        final Button tmp = new Button();

        //le pone el tamano
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
                //de tablero a tablero
                if (getChildren().contains(campo)) {
                    getChildren().remove(Rummikub.getSeleccion());
                    add(Rummikub.getSeleccion(), getColumnIndex(campo), getRowIndex(campo));
                    add(crearBotonVacio(columna, fila), columna, fila);
                }
                //de tablero a soporte
                else{
                    if (Rummikub.getJugadores()[Rummikub.getTurno()].getFichas().contains(Rummikub.getSeleccion())) {
                        add(crearBotonVacio(columna, fila), columna, fila);
                        getChildren().remove(Rummikub.getSeleccion());
                        columna = Rummikub.getSoporte().getColumnIndex(campo);
                        Rummikub.getSoporte().add(Rummikub.getSeleccion(), columna, 0);
                        Rummikub.getSoporte().getChildren().remove(campo);
                    }
                }
            }
            //elimina el boton
            getChildren().remove(campo);
        }
    }

    //se llena con botones
    public void iniciar(){
        Button tmp;

        for (int i1 = 0; i1<13; i1++){
            for(int i2 = 0; i2<13; i2++){
                add(crearBotonVacio(i1, i2), i1, i2);
            }
        }
    }

    //true si todas las jugadas son legales, false si no
    public Boolean isValido(){
        //numeros de una "jugada" (fichas que estan juntas)
        ArrayList jugada = new ArrayList();
        //hash de los colores de una jugada
        ArrayList jugadaColores = new ArrayList();

        double tmp;

        Ficha [][] tab = new Ficha[13][13];

        //construye una matriz con las fichas
        for (Node n : getChildren()){
            try{
                //para que de excepcion si no es ficha
                ((Ficha)n).getColor();
                tab[getRowIndex(n)][getColumnIndex(n)] = (Ficha)n;
            }
            catch ( Exception e){
            }
        }

        //recorre la matriz
        for (int fila = 0; fila<13; fila++){
            for (int columna = 0; columna<13; columna++){
                jugada.clear();
                jugadaColores.clear();

                //pone la "jugada"(un conjunto de fichas) en un arreglo
                while(columna <13 && tab[fila][columna] != null){
                    jugada.add(tab[fila][columna].getNumero());
                    jugadaColores.add(new Long(tab[fila][columna].getColor().hashCode()));
                    columna ++;
                }

                //si no habia una jugada en la linea
                if (jugada.isEmpty()){
                    continue;
                }

                //si no es de suficiente tamano
                if (jugada.size()<3){
                    return false;
                }

                tmp = 0;

                //suma todos los contenidos de la jugada
                for (Object i : jugada){
                    tmp += (int) i;
                }

                //la suma de los elementos == primerElemento * (cantidadDeElementos-cantidadComodines)
                //lo que significa que se repite el mismo numero (restarle las veces que aparece 0 hace que los comodines se acepten)
                if ((int)jugada.get(0) !=0 && tmp == ((int) jugada.get(0)) * (jugada.size() - Collections.frequency(jugada, 0))) {
                        continue;
                }
                //si el primer elemento es comodin lo compara con el ultimo
                else if (tmp == ((int) jugada.get(2)) * (jugada.size() - Collections.frequency(jugada, 0))) {
                    continue;
                }

                //le resta los elementos que tendria la jugada si fuera una escalera a tmp
                if ((int) jugada.get(0) != 0) {
                    for (int i = (int) jugada.get(0); i < (int) jugada.get(0) + jugada.size(); i++) {

                        if (jugada.contains(i)){
                            continue;
                        }
                        if (jugada.contains(0)){
                            jugada.set(jugada.indexOf(0), i);
                        }
                        else{
                            return false;
                        }
                    }
                }
                //si el primer elemento era comodin empieza desde el fin
                else{
                    for (int i = (int) jugada.get(jugada.size()-1); i> (int) jugada.get(jugada.size()-1)-jugada.size() ;i--){
                        if (jugada.contains(i)){
                            continue;
                        }
                        if (jugada.contains(0)){
                            jugada.set(jugada.indexOf(0), i);
                        }
                        else{
                            return false;
                        }
                    }
                }

                tmp = 0;

                //suma los colores para revisarlo igual que repeticion
                for (Object i : jugadaColores){
                    tmp += (long) i;
                 }

                //funciona igual que el de arriba pero este es con colores
                if ((long)jugadaColores.get(0) != new Long(255) &&
                        tmp == ((long) jugadaColores.get(0)) * (jugadaColores.size() - Collections.frequency(jugadaColores, new Long(255))) + 255 * Collections.frequency(jugadaColores, new Long(255)) ) {
                        continue;
                }
                else if (tmp == ((long) jugadaColores.get(1)) * (jugadaColores.size() - Collections.frequency(jugadaColores, new Long(255))) + 255 * Collections.frequency(jugadaColores, new Long(255))) {
                        continue;
                }

                return false;
            }
        }
        return true;
    }

}

