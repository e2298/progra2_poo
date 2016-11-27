package rumm;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.*;

import static java.lang.System.exit;

public class Rummikub extends Application {
    private static Scene escenaPrincipal;
    private static Stage escenarioPrincipal;
    private static Jugador[] jugadores;
    private static ArrayList fichasTablero;
    private static int numeroJugadores;
    private static int turno;
    private static int numeroPartida;
    private static int ancho, alto;
    private static Ficha seleccion;
    private static  GridPane soporte;


    public static GridPane getSoporte() {
        return soporte;
    }

    public static void setSoporte(GridPane soporte) {
        Rummikub.soporte = soporte;
    }

    public static int getTurno() {
        return turno;
    }

    public static void setTurno(int turno) {
        Rummikub.turno = turno;
    }

    public static Scene getEscenaPrincipal() {
        return escenaPrincipal;
    }

    public static void setEscenaPrincipal(Scene escenaPrincipal) {
        Rummikub.escenaPrincipal = escenaPrincipal;
    }

    public static Stage getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public static void setEscenarioPrincipal(Stage escenarioPrincipal) {
        Rummikub.escenarioPrincipal = escenarioPrincipal;
    }

    public static Jugador[] getJugadores() {
        return jugadores;
    }

    public static void setJugadores(Jugador[] jugadores) {
        Rummikub.jugadores = jugadores;
    }

    public static ArrayList getFichasTablero() {
        return fichasTablero;
    }

    public static void setFichasTablero(ArrayList fichasTablero) {
        Rummikub.fichasTablero = fichasTablero;
    }

    public static int getNumeroJugadores() {
        return numeroJugadores;
    }

    public static void setNumeroJugadores(int numeroJugadores) {
        Rummikub.numeroJugadores = numeroJugadores;
    }

    public static int getNumeroPartida() {
        return numeroPartida;
    }

    public static void setNumeroPartida(int numeroPartida) {
        Rummikub.numeroPartida = numeroPartida;
    }

    public static int getAncho() {
        return ancho;
    }

    public static void setAncho(int ancho) {
        Rummikub.ancho = ancho;
    }

    public static int getAlto() {
        return alto;
    }

    public static void setAlto(int alto) {
        Rummikub.alto = alto;
    }

    public static Ficha getSeleccion() {
        return seleccion;
    }

    public static void setSeleccion(Ficha seleccion) {
        Rummikub.seleccion = seleccion;
    }

    public static void main(String[] args) {
        numeroPartida = 0;

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        escenarioPrincipal = primaryStage;
        escenarioPrincipal.setTitle("Rmummikub");

        setAncho((int) Screen.getPrimary().getVisualBounds().getWidth());
        setAlto((int) Screen.getPrimary().getVisualBounds().getHeight());

        Pane layout = new Pane();
        Scene escena;

        TextField  campoTexto = new TextField();
        campoTexto.setText("2");
        campoTexto.setPrefWidth(280);
        campoTexto.relocate(10, 30);
        campoTexto.setOnAction(e-> tomarNumeroJugadores(campoTexto));
        layout.getChildren().add(campoTexto);

        Text texto = new Text("Inserte el numero de jugadores");
        texto.relocate(50, 10);
        layout.getChildren().add(texto);

        Button botonSalir = new Button("Salir");
        botonSalir.setOnAction(e->{escenarioPrincipal.close();});
        botonSalir.relocate(200,70);
        layout.getChildren().add(botonSalir);

        Button botonAceptar = new Button("Empezar");
        botonAceptar.setOnAction(e-> tomarNumeroJugadores(campoTexto) );
        botonAceptar.relocate(50, 70);
        layout.getChildren().add(botonAceptar);

        escena = new Scene(layout,0,0);

        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.show();
    }

    private void tomarNumeroJugadores( TextField campoEntrada ){
        //lee el campo de texto
        String entrada = campoEntrada.getText();
        int numJugadores;

        //adentro de bloque try por el valueOf
        try {
            numJugadores = Integer.valueOf(entrada);

            //verifica que sea valido
            if (numJugadores<2 || numJugadores>4){
                new AlertBox("", "Deben de haber 2-4 jugadores");
            }

            else{
                setNumeroJugadores(numJugadores);
                inicializarJuego();
            }
        }
        catch (Exception e){
            new AlertBox("Error", "Entrada no valida");
            campoEntrada.setText("2");
        }


    }



    private static void inicializarJuego(){
        ArrayList fichas;
        ArrayList temp;

        Jugador [] jugadores;

        fichas = new ArrayList();

        for (int i = 1; i<14;i++){
            fichas.add( new Ficha(Color.BLUE, i));
            fichas.add( new Ficha(Color.BLUE, i));
            fichas.add( new Ficha(Color.RED, i));
            fichas.add( new Ficha(Color.RED, i));
            fichas.add( new Ficha(Color.YELLOW, i));
            fichas.add( new Ficha(Color.YELLOW, i));
            fichas.add( new Ficha(Color.GREEN, i));
            fichas.add( new Ficha(Color.GREEN, i));
        }

        fichas.add( new Ficha(Color.BLACK, 0));
        fichas.add( new Ficha(Color.BLACK, 0));


        Collections.shuffle(fichas);


        jugadores = new Jugador[getNumeroJugadores()];

        for (int i = 0; i<getNumeroJugadores(); i++){
            temp = new ArrayList(fichas.subList(0,14));
            jugadores[i] = new Jugador(temp);
            fichas.removeAll(temp);
        }

        setFichasTablero(fichas);
        setJugadores(jugadores);

        inicializarPantalla();

    }


    private static void inicializarPantalla(){
        Pane layout = new Pane();
        GridPane soporte;
        Tablero tab;

        Button botonSalir;
        Button botonTerminarTurno;

        botonSalir= new Button("Salir");
        botonSalir.setOnAction(e->{
            new VentanaSalir();
        });
        botonSalir.relocate(1,alto-3);
        layout.getChildren().add(botonSalir);

        botonTerminarTurno = new Button("Terminar turno");
        botonTerminarTurno.setOnAction(event -> {
            terminarTurno();
        });
        botonTerminarTurno.relocate(getAncho()-117,1);
        layout.getChildren().add(botonTerminarTurno);

        soporte = new GridPane();
        layout.getChildren().add(soporte);
        setSoporte(soporte);

        tab = new Tablero();
        tab.relocate(0, getAlto()/14);
        layout.getChildren().add(tab);

        setEscenaPrincipal(new Scene(layout, 0, 0));
        getEscenarioPrincipal().setScene(getEscenaPrincipal());
        getEscenarioPrincipal().setFullScreen(true);

        iniciarSiguienteTurno();
    }

    private static void terminarTurno() {
        AlertBox msj = new AlertBox("", "Siguente turno");
        msj.getBoton().setOnAction(event -> {
            iniciarSiguienteTurno();
            msj.cerrar();
        });

        getSoporte().getChildren().clear();
    }

    private static void iniciarSiguienteTurno(){
        setTurno((getTurno() + 1)%getNumeroJugadores());
        for (Object ficha: getJugadores()[getTurno()].getFichas()){
            getSoporte().add(((Ficha)ficha), getSoporte().getChildren().size(), 0);
        }
    }


}
