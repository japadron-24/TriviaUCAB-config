package TriviaUCAB.models;

import java.util.Scanner;

public class SquareCategory extends Square implements brazo, movimientoBidireccional {
    protected Category categoria;
    protected Square next;
    protected Square previous;


    @Override
    public String paint() {
        if (cantidadFichas > 0) {
            return "┌────┐\n" +
                    "│C " + cantidadFichas + " │\n" +
                    "└────┘";

        }
        return "┌────┐\n" +
                "│ C  │\n" +
                "└────┘";
    }

    @Override
    public int action(Scanner scanner,Ficha jugador) {
        int a;
        do {
            a = Validator.validarInt(
                    "Tienes 2 posibles rutas, a donde te quieres mover?\n" +
                            "0, 1)",
                    scanner);
            if (a < 0 || a > 1) {
                System.out.println("ERROR vuelva a intetarlo ");
            }
        } while (a < 0 || a > 1);
        return a;
    }

    public Square salir(int move, int exit, Ficha jugador, Scanner scanner) {
        Square iter = this;
        this.cantidadFichas--;
        for (int i = 0; i < move; i++) {
            if (iter instanceof SquareRayo ray) {
                int salir= ray.action(scanner, jugador);
                jugador.salido = true;
                iter=ray.salir(move-i, salir, jugador);
                i=move;
            }else
                if (iter instanceof SquareCategory sc)
                iter = sc.getNext();
        }
        if (iter instanceof SquareRayo ray) {
            jugador.salido = true;
        }
        return iter;
    }

    public Square movimiento (int move, int exit, Ficha jugador){
        Square iter = this;
        this.cantidadFichas--;
        for (int i = 0; i < move; i++) {
            if (exit ==1 && iter instanceof movimientoBidireccional next)
                iter = next.getNext();
            else if (exit ==0 && iter instanceof movimientoBidireccional prev)
                iter = prev.getPrevious();
        }
        return iter;
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
        return this.next;
    }
}
//
