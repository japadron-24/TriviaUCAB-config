package TriviaUCAB.models;
import java.util.Scanner;
public class SquareSpecial extends Square implements movimientoBidireccional {
    protected SquareCategory next;
    protected SquareCategory previous;

    public void setNext(SquareCategory next) {
        this.next = next;
    }

    @Override
    public SquareCategory getNext() {
        return next;
    }
    public Square movimiento (int move, int exit, Ficha jugador){
        this.cantidadFichas--;
        Square iter = this;
        for (int i = 0; i < move; i++) {
            if (exit ==1 && iter instanceof movimientoBidireccional next)
                iter = next.getNext();
            else if (exit ==0 && iter instanceof movimientoBidireccional prev)
                iter = prev.getPrevious();
        }
        return iter;
    }
    public SquareSpecial(SquareCategory next, SquareCategory previous) {
        this.next = next;
        this.previous = previous;
    }

    public SquareCategory advance() {
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
    public int action(Scanner scanner,Ficha jugador) {
        return 0;

    }
}
