package TriviaUCAB.models;

import java.util.Scanner;

public interface CategoryQuestion {
    abstract public Square reaction(Scanner scanner, Ficha jugador, Questions questions);
    abstract public boolean revisarRespuesta(Scanner scanner, Question question);
}
