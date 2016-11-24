package rumm;

import javafx.scene.control.Button;
import javafx.scene.paint.Color;

public class Ficha {

    //0 es comodin
    private int numero;
    private Color color;
    //private Button


    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Ficha(Color color, int numero){
        setColor(color);
        setNumero(numero);
    }

}
