package TriviaUCAB.models;
import java.util.Scanner;
abstract public class Square {
    protected int cantidadFichas;
    protected int position;
    abstract public String paint();

    abstract public int action(Scanner scanner,Ficha jugador);

    abstract  public Square reaction(Scanner scanner,Ficha jugador);
}
