package TriviaUCAB.models;

import java.util.Scanner;

public class SquareRayo extends Square implements movimientoBidireccional {
    protected Category categoria;
    protected SquareCategory next;
    protected SquareCategory previous;
    protected SquareCategory toCenter;


    @Override
    public String paint() {
        if (cantidadFichas > 0) {
            return "┌────┐\n" +
                    "│R " + cantidadFichas + " │\n" +
                    "└────┘";
        }
        return "┌────┐\n" +
                "│ R  │\n" +
                "└────┘";
    }

    @Override
    public int action(Scanner scanner, Ficha jugador) {
        if (jugador.triangulo()) {
            return 2;
        } else {
            System.out.println("Quisieras ir hacia adelante o hacia atrás?");
            int exit = Validator.validarInt("1. Adelante\n0.Atras", scanner);
            if (exit == 1) {
                return 1;
            } else if (exit == 0) {
                return 0;
            } else {
                System.out.println("Ingrese una opción válida");
            }
        }
        return 0;
    }
    public Square salir(int move, int exit, Ficha jugador) {
        Square iter = this;
        for (int i = 0; i < move; i++) {
            if (exit == 1 && iter instanceof movimientoBidireccional next)
                iter = next.getNext();
            else if (exit == 0 && iter instanceof movimientoBidireccional prev)
                iter = prev.getPrevious();
            else  if(exit ==2){
                iter = this.toCenter;
            }
        }
        return iter;
    }
    public Square movimiento(int move, int exit, Ficha jugador) {
        Square iter = this;
        this.cantidadFichas--;
        for (int i = 0; i < move; i++) {
            if (exit == 1 && iter instanceof movimientoBidireccional next)
                iter = next.getNext();
            else if (exit == 0 && iter instanceof movimientoBidireccional prev)
                iter = prev.getPrevious();
            else  if(exit ==2){
                iter = this.toCenter;
            }
        }
        return iter;
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

    public SquareCategory getToCenter() {
        return toCenter;
    }

    public Square getNext() {
        return this.next;
    }

    public Square getPrevious() {
        return previous;
    }
}
