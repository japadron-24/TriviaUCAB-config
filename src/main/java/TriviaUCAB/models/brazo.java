package TriviaUCAB.models;

import java.util.Scanner;

public interface brazo {
    public Square salir(int move, int exit,Ficha jugador, Scanner scanner);
    public Square entrar(int move, int exit,Ficha jugador, Scanner scanner);

}
