package rumm;

import javafx.scene.control.Button;
import javafx.scene.paint.Color;

public class Ficha {

    //0 es comodin
    private int numero;
    private Color color;
    private Button boton;

    public Button getBoton() {
        return boton;
    }

    public void setBoton(Button boton) {
        this.boton = boton;
    }

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
        String num = String.valueOf(numero);
        final Button bt;

        setColor(color);
        setNumero(numero);

        if(num.length() < 2){
            num += " ";
        }

        if(numero == 0){
            num = "C ";
        }


        bt = new Button(num);
        bt.setTextFill(color);
        bt.setStyle("-fx-font: 24 arial;");
        bt.setUserData(this);
        bt.setOnAction(event -> {
            Rummikub.setSeleccion(bt);
        });

        setBoton(bt);

    }

}
