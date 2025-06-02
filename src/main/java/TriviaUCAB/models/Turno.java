package TriviaUCAB.models;
import java.util.ArrayList;
import java.util.concurrent.*;
import java.util.Scanner;
public class Turno implements Callable<Void>{
    ArrayList<Ficha> jugadores;
    Scanner scanner;
    Questions questions;
    int jugadorActual;

    @Override
    public Void call() throws Exception {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("Turno del jugador: " + jugadores.get(jugadorActual).nickName);
        if (Thread.currentThread().isInterrupted()) {
            System.out.println("Turno cancelado antes de completarse.");
            return null;
        }
        jugadores.get(jugadorActual).avanzar(scanner, questions);
        System.out.println("Posición actual:\n" + jugadores.get(jugadorActual).posicion.paint());
        return null;
    }

    public Turno(ArrayList<Ficha> jugadores, Scanner scanner, Questions questions,int jugadorActual) {
        this.jugadores = jugadores;
        this.scanner = scanner;
        this.questions = questions;
        this.jugadorActual = jugadorActual;
    }
}
