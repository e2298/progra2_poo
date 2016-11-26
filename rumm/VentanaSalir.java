package rumm;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class VentanaSalir {

    public VentanaSalir(){
        Stage window = new Stage();
        Button salir, no;


        //hace que solo esta ventana se pueda usar
        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinWidth(250);

        Label label = new Label("Seguro que desea salir?");

        VBox layout = new VBox(5);

        window.setOnCloseRequest(event -> {
            window.close();
        });

        no = new Button("Seguir jugando");
        no.setOnAction(event -> {
            window.close();
        });

        salir = new Button("Salir");
        salir.setOnAction(event -> {
            Platform.exit();
        });

        layout.getChildren().addAll(label,no,salir);

        //Display window and wait for it to be closed before returning
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.show();

    }

}
