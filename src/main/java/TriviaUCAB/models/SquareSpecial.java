package TriviaUCAB.models;

public class SquareSpecial extends Square {
    SquareCategory next;
    SquareCategory previous;

    public SquareSpecial(SquareCategory next, SquareCategory previous) {
        this.next = next;
        this.previous = previous;
    }

    public SquareCategory getNext() {
        return next;
    }

    public SquareCategory getPrevious() {
        return previous;
    }

    @Override
    public void paint() {

    }

    @Override
    public void action() {

    }
}
