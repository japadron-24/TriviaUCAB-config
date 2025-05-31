package TriviaUCAB.models;
import java.util.Scanner;
abstract public class Square {
    protected int cantidadFichas;

    abstract public String paint();

    abstract public int action(Scanner scanner,Ficha jugador);

}
