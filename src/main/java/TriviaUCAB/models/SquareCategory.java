package TriviaUCAB.models;

public class SquareCategory extends Square {
    protected Category categoria;
    protected Square next;
    protected Square previous;

    @Override
    public void paint() {

    }

    @Override
    public void action() {

    }

    public SquareCategory(Category categoria, Square next, Square previous) {
        this.categoria = categoria;
        this.next = next;
        this.previous = previous;
    }

    public Category getCategoria() {
        return categoria;
    }

    public Square getPrevious() {
        return previous;
    }

    public Square getNext() {
        return next;
    }
}
//
