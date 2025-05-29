package TriviaUCAB.models;

public class SquareRayo extends Square {
    protected Category categoria;
    protected SquareCategory next;
    protected SquareCategory previous;
    protected SquareCategory toCenter;


    @Override
    public String paint() {
        return  "+----+\n"+
                "| R  |\n"+
                "+----+";
    }

    @Override
    public void action() {

    }

    public SquareRayo(Category categoria, SquareCategory next, SquareCategory previous, SquareCategory toCenter) {
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
