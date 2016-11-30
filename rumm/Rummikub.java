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
    private static int turno; //jugador en turno
    private static int numeroPartida;
    private static int ancho, alto; //de la pantalla

    private static Ficha seleccion; //ficha seleccionada
    private static GridPane soporte; //soporte grafico
    private static Tablero tablero; //tablero grafico y logico
    private static TablaPuntiaciones tablaPuntiaciones;
    private static Text turnoTexto; //texto que dice quien esta en turno


    public static Text getTurnoTexto() {
        return turnoTexto;
    }

    public static void setTurnoTexto(Text turnoTexto) {
        Rummikub.turnoTexto = turnoTexto;
    }

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

        //llama a start y inicia la aplicacion de javafx
        launch(args);
    }


    //toma la entrada de numero de jugadores, al presionar el boton llama a tomarnumero de jugadores
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

            //inicia el juego si el numero era valido
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

    //pone las variables iniciales del juego
    private static void inicializarJuego(){

        Jugador [] jugadores;

        setNumeroPartida(0);

        //crea los jugadores
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

        //si hay fichas en los jugadores las quita
        for (Object j : getJugadores()){
            ((Jugador)j).getFichas().clear();
        }

        //genera las fichas
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
        } //comodines
        fichas.add( new Ficha(Color.BLACK, 0));
        fichas.add( new Ficha(Color.BLACK, 0));

        //mezcla las fichas
        Collections.shuffle(fichas);

        //agarra pedazos de 14 fichas y se los pasa a cada jugador
        for (int i = 0; i<getNumeroJugadores(); i++){
            temp = new ArrayList(fichas.subList(0,14));
            getJugadores()[i].ponerFichas(temp);
            fichas.removeAll(temp);
        }

        //las fichas que sobran se las pone al tablero
        setFichasTablero(fichas);
    }


    //pone todos los botones y textos donde van, llama a  iniciarRonda
    private static void inicializarPantalla(){
        Pane layout = new Pane();
        GridPane soporte;
        Tablero tab;
        TablaPuntiaciones punt;

        Button botonSalir;
        Button botonTerminarTurno;
        Button botonComer;
        Button botonTerminarRonda;

        Text turnoTexto;

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

        turnoTexto = new Text();
        turnoTexto.relocate(getAlto()*1.2, getAlto()/11);
        turnoTexto.setStyle("-fx-font:" + String.valueOf(Rummikub.getAlto() / 25) + " arial;");
        layout.getChildren().add(turnoTexto);
        setTurnoTexto(turnoTexto);

        soporte = new GridPane();
        layout.getChildren().add(soporte);
        setSoporte(soporte);

        tab = new Tablero();
        tab.relocate(0, getAlto()/14);
        layout.getChildren().add(tab);

        punt = new TablaPuntiaciones();
        punt.relocate(getAlto() * 0.95, getAlto()/9);
        setTablaPuntiaciones(punt);
        layout.getChildren().add(punt);

        setTablero(tab);

        setEscenaPrincipal(new Scene(layout, 0, 0));
        getEscenarioPrincipal().setScene(getEscenaPrincipal());
        getEscenarioPrincipal().setFullScreen(true);

        iniciarRonda();
    }


    private static void iniciarRonda(){
        //elimina el tablero
        getTablero().getChildren().clear();

        //vuelbe a construir las puntuaciones
        getTablaPuntiaciones().actualizar();

        //aumenta el numero de partida
        setNumeroPartida(getNumeroPartida()+1);

        //vuelbe a construir el tablero
        getTablero().iniciar();

        repartirFichas();

        //pone el turno en -1, ya que iniciarsiguiente lo aumenta y lo deja en 0
        setTurno(-1);

        iniciarSiguienteTurno();
    }


    private static void terminarRonda(){
        AlertBox msj;

        int ganador = 0;
        int puntos = 0;

        //encuentra el que tiene menos fichas, si hay empate queda el primero
        for (int i = 0; i<getNumeroJugadores(); i++){
            if (getJugadores()[i].getFichas().size() < getJugadores()[ganador].getFichas().size()){
                ganador = i;
            }
        }

        //resta los puntos a los perdedores y los suma a los ganadores
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
        //aumenta el numero de turno
        setTurno((getTurno() + 1)%getNumeroJugadores());

        getTurnoTexto().setText("Turno de "+ String.valueOf(getTurno()+1));

        //quita las fichas del jguador anterior y pone las nuevas
        getSoporte().getChildren().clear();
        for (Object ficha: getJugadores()[getTurno()].getFichas()){
            getSoporte().add(((Ficha)ficha), getSoporte().getChildren().size(), 0);
        }
    }


    private static void terminarTurno() {
        AlertBox msj;

        //si la partida esta en estado valido
        if (getTablero().isValido() && !getSoporte().getChildren().equals(getJugadores()[getTurno()].getFichas())){
            //borra las fichas del jugador
            getJugadores()[getTurno()].getFichas().clear();
            //le pone las fichas que estan en el soporte
            for (Node n: getSoporte().getChildren() ){
                if (n instanceof Ficha){
                    getJugadores()[getTurno()].getFichas().add(n);
                }
            }
            getSoporte().getChildren().clear();

            setSeleccion(null);

            //si el jugador queda sin fichas termina la ronda
            if (getJugadores()[getTurno()].getFichas().isEmpty()){
                terminarRonda();
            }
            else {
                msj = new AlertBox("", "Siguente turno");
                msj.getBoton().setOnAction(event -> {
                    iniciarSiguienteTurno();
                    msj.cerrar();
                });
            }

        }
        //si la partida no esta en estado valido
        else{
            //si es porque no jugo
            if (getSoporte().getChildren().equals(getJugadores()[getTurno()].getFichas())){
                msj = new AlertBox("", "debe de jugar algo antes de pasar de turno");
            }
            //si es porque hay una jugada mala
            else {
                msj = new AlertBox("", "El tablero no es valido");
            }
        }
    }



    private static void comer(){
        //si no quedan fichas que comer
        if (getFichasTablero().isEmpty()){
            new AlertBox("", "No quedan fichas");
        }
        else {
            //anade una ficha
            getSoporte().add((Ficha) getFichasTablero().get(0), getSoporte().getChildren().size(), 0);
            //la quita del tablero
            getFichasTablero().remove(getFichasTablero().get(0));
        }
    }
}
