package TriviaUCAB.models;

public class SquareLightning extends Square {
    Category categoria;
    SquareCategory next;
    SquareCategory previous;
    SquareCategory toCenter;


    @Override
    public void paint() {

    }

    @Override
    public void action() {

    }

    public SquareLightning(Category categoria, SquareCategory next, SquareCategory previous, SquareCategory toCenter) {
        this.categoria = categoria;
        this.next = next;
        this.previous = previous;
        this.toCenter = toCenter;
    }

    public Category getCategoria() {
        return categoria;
    }

    public SquareCategory getNext() {
        return next;
    }

    public SquareCategory getToCenter() {
        return toCenter;
    }

    public SquareCategory getPrevious() {
        return previous;
    }
}
