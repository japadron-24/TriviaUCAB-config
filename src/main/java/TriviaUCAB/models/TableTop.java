package TriviaUCAB.models;

public class TableTop {
    public Ficha[] jugadores = new Ficha[6];
    private SquareCenter centro;
    public TableTop(){
        centro = new SquareCenter();
    }
}
