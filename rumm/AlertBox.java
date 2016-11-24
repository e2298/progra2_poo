package rumm;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

//https://www.youtube.com/watch?v=SpL3EToqaXA&list=PL6gx4Cwl9DGBzfXLWLSYVy8EbTdpGbUIG&index=5

public class AlertBox {
    private Button boton;
    private Stage ventana;

    public Button getBoton() {
        return boton;
    }

    public void setBoton(Button boton) {
        this.boton = boton;
    }

    public Stage getVentana() {
        return ventana;
    }

    public void setVentana(Stage ventana) {
        this.ventana = ventana;
    }
    
    public void cerrar(){
        ventana.close();
    }
    
    public AlertBox(String title, String message) {
        Stage window = new Stage();

        //hace que solo esta ventana se pueda usar
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);

        Label label = new Label();
        label.setText(message);
        
        boton = new Button("Cerrar");
        boton.setOnAction(e-> cerrar());
        
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, boton);
        layout.setAlignment(Pos.CENTER);
        
        window.setOnCloseRequest(e -> e.consume());
        
        //Display window and wait for it to be closed before returning
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.show();
        
        setVentana(window);
    }

}
