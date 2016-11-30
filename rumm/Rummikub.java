package rumm;

import javafx.application.Application;
import javafx.scene.Node;
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
    private static Tablero tablero;
    private static TablaPuntiaciones tablaPuntiaciones;


    public static TablaPuntiaciones getTablaPuntiaciones() {
        return tablaPuntiaciones;
    }

    public static void setTablaPuntiaciones(TablaPuntiaciones tablaPuntiaciones) {
        Rummikub.tablaPuntiaciones = tablaPuntiaciones;
    }

    public static Tablero getTablero() {
        return tablero;
    }

    public static void setTablero(Tablero tablero) {
        Rummikub.tablero = tablero;
    }

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

        Jugador [] jugadores;

        jugadores = new Jugador[getNumeroJugadores()];

        for (int i = 0; i<getNumeroJugadores(); i++){
            jugadores[i] = new Jugador();
        }

        setJugadores(jugadores);

        repartirFichas();

        inicializarPantalla();

    }

    private static void repartirFichas(){
        ArrayList temp;
        ArrayList fichas;

        for (Object j : getJugadores()){
            ((Jugador)j).getFichas().clear();
        }
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

        for (int i = 0; i<getNumeroJugadores(); i++){
            temp = new ArrayList(fichas.subList(0,14));
            getJugadores()[i].ponerFichas(temp);
            fichas.removeAll(temp);
        }

        setFichasTablero(fichas);

    }


    private static void inicializarPantalla(){
        Pane layout = new Pane();
        GridPane soporte;
        Tablero tab;
        TablaPuntiaciones punt;

        Button botonSalir;
        Button botonTerminarTurno;
        Button botonComer;
        Button botonTerminarRonda;

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
        botonTerminarTurno.relocate(1,getAlto()-33);
        layout.getChildren().add(botonTerminarTurno);

        botonComer = new Button("Comer");
        botonComer.setOnAction(event -> {
            comer();
        });
        botonComer.relocate(1, getAlto()-65);
        layout.getChildren().add(botonComer);

        botonTerminarRonda = new Button("Terminar ronda");
        botonTerminarRonda.setOnAction(event -> {
            terminarRonda();
        });
        botonTerminarRonda.relocate(getAncho()-121, 2);
        layout.getChildren().add(botonTerminarRonda);

        soporte = new GridPane();
        layout.getChildren().add(soporte);
        setSoporte(soporte);

        tab = new Tablero();
        tab.relocate(0, getAlto()/14);
        layout.getChildren().add(tab);

        punt = new TablaPuntiaciones();
        punt.relocate(getAlto(), getAlto() /14);
        setTablaPuntiaciones(punt);
        layout.getChildren().add(punt);

        setTablero(tab);

        setEscenaPrincipal(new Scene(layout, 0, 0));
        getEscenarioPrincipal().setScene(getEscenaPrincipal());
        getEscenarioPrincipal().setFullScreen(true);

        iniciarRonda();
    }


    private static void iniciarRonda(){
        getTablero().getChildren().clear();

        getTablaPuntiaciones().actualizar();

        setNumeroPartida(getNumeroPartida()+1);

        getTablero().iniciar();

        repartirFichas();

        setTurno(0);

        iniciarSiguienteTurno();
    }


    private static void terminarRonda(){
        AlertBox msj;

        int ganador = 0;
        int puntos = 0;

        for (int i = 0; i<getNumeroJugadores(); i++){
            if (getJugadores()[i].getFichas().size() < getJugadores()[ganador].getFichas().size()){
                ganador = i;
            }
        }

        for (int i = 0; i<getNumeroJugadores(); i++){
            puntos = 0;
            if (i != ganador){
                for (Object f : getJugadores()[i].getFichas()){
                    if (((Ficha)f).getNumero() != 0){
                        puntos += ((Ficha)f).getNumero();
                    }
                    else{
                        puntos += 30;
                    }
                }
            }
            getJugadores()[i].setPuntos(getJugadores()[i].getPuntos() - puntos);
            getJugadores()[ganador].setPuntos(getJugadores()[ganador].getPuntos() + puntos);
        }

        msj = new AlertBox("", "Fin de la ronda " + String.valueOf(getNumeroPartida()) + "\nGanador: " + String.valueOf(ganador + 1));
        msj.getBoton().setOnAction(event -> {
            msj.cerrar();
            iniciarRonda();
        });

    }

    private static void iniciarSiguienteTurno(){
        setTurno((getTurno() + 1)%getNumeroJugadores());

        getSoporte().getChildren().clear();

        for (Object ficha: getJugadores()[getTurno()].getFichas()){
            getSoporte().add(((Ficha)ficha), getSoporte().getChildren().size(), 0);
        }
    }


    private static void terminarTurno() {
        AlertBox msj;

        if (getTablero().isValido() && !getSoporte().getChildren().equals(getJugadores()[getTurno()].getFichas())){
            msj = new AlertBox("", "Siguente turno");
            msj.getBoton().setOnAction(event -> {
                iniciarSiguienteTurno();
                msj.cerrar();
            });

            getJugadores()[getTurno()].getFichas().clear();
            for (Node n: getSoporte().getChildren() ){
                if (n instanceof Ficha){
                    getJugadores()[getTurno()].getFichas().add(n);
                }
            }

            getSoporte().getChildren().clear();
            setSeleccion(null);
        }
        else{
            if (getSoporte().getChildren().equals(getJugadores()[getTurno()].getFichas())){
                msj = new AlertBox("", "debe de jugar algo antes de pasar de turno");
            }
            else {
                msj = new AlertBox("", "El tablero no es valido");
            }
        }
    }



    private static void comer(){
        if (getFichasTablero().isEmpty()){
            new AlertBox("", "No quedan fichas");
        }
        else {
            getSoporte().add((Ficha) getFichasTablero().get(0), getSoporte().getChildren().size(), 0);
            getFichasTablero().remove(getFichasTablero().get(0));
        }
    }


}
