package TriviaUCAB.models;

public interface movimientoBidireccional {
    public Square getNext();
    public Square getPrevious();
    public Square movimiento (int move, int exit, Ficha jugador);
}

