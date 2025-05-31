package TriviaUCAB.models;

public class SquareSpecial extends Square {
    protected SquareCategory next;
    protected SquareCategory previous;

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
    public String paint() {
        if (cantidadFichas > 0) {
            return  "┌────┐\n"+
                    "│S "+cantidadFichas+" │\n"+
                    "└────┘";
        }
        return  "┌────┐\n"+
                "│ S  │\n"+
                "└────┘";
    }

    @Override
    public void action() {

    }
}
