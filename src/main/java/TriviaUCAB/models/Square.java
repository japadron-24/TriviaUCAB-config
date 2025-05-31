package TriviaUCAB.models;

abstract public class Square {
    protected int cantidadFichas;

    abstract public String paint();

    abstract public void action();

    abstract public  Square getNext ();

}
