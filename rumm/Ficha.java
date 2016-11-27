package rumm;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;

public class Ficha  extends Button{

    //0 es comodin
    private int numero;
    private Color color;


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
        super();

        String num = String.valueOf(numero);

        setColor(color);
        setNumero(numero);

        if(numero == 0){
            num = "C ";
        }

        setText(num);

        setTextFill(color);
        setStyle("-fx-font:"+ String.valueOf(Rummikub.getAlto()/40) +" arial;");

        setMinWidth(Rummikub.getAlto()/15);
        setMinHeight(Rummikub.getAlto()/15);
        setMaxWidth(Rummikub.getAlto()/15);
        setMinHeight(Rummikub.getAlto()/15);

        setOnAction(event -> {
            Rummikub.setSeleccion(this);
        });

    }

}
