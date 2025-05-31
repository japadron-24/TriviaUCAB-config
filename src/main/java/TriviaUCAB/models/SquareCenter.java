package TriviaUCAB.models;

import java.util.Scanner;

// c++ es sublime
public class SquareCenter extends Square implements  brazo{
    //miembros
    protected SquareCategory rayos[] = new SquareCategory[6];

    //metodos
    @Override
    public String paint() {
        if (cantidadFichas > 0) {
            return "/────\\\n" +
                    "│  " + cantidadFichas + " │\n" +
                    "\\────/";
        }
        return "/────\\\n" +
                "│    │\n" +
                "\\────/";
    }
    @Override
    public int action(Scanner scanner,Ficha jugador) {
        int a;
        do {
            a = Validator.validarInt(
                    "Tienes 6 posibles rutas, a donde te quieres mover?\n" +
                            "0, 1, 2, 3, 4 o 5)",
                    scanner);
            if (a < 0 || a > 5) {
                System.out.println("ERROR vuelva a intetarlo ");
            }
        } while (a < 0 || a > 5);
        return a;
    }

    public Square salir(int move, int exit, Ficha jugador,Scanner scanner) {
        if (move < 1 || move > 6) {
            throw new IllegalArgumentException("El movimiento debe estar entre 1 y 6");
        }
        SquareCategory ciclar = this.rayos[exit];
        Square salida;
        if (this.cantidadFichas > 0) {
            this.cantidadFichas--;
            for (int i = 1; i < move; i++) {
                if (ciclar.next instanceof SquareCategory sig) {
                    ciclar= sig;
                } else if (ciclar.next instanceof SquareRayo sig) {
                    salida = sig;
                    jugador.salido = true;
                }
            }

        } else {
            throw new IllegalStateException("No hay cantidadFichas en el Centro para salir");
        }
        return salida=ciclar;
    }

    public SquareCenter(int jugadores) {
        int currentCategory = 0;
        this.cantidadFichas = jugadores;
        var categorias = Category.values();
        SquareCategory lastSquare = null;
        for (int i = 0; i < this.rayos.length; i++) {
            this.rayos[i] = new SquareCategory(categorias[i], null, this);
            SquareCategory squareActual = this.rayos[i];
            currentCategory = i + 1;
            if (categorias.length == currentCategory) currentCategory = 0;
            for (int j = 1; j < 5; j++) {
                squareActual.next = new SquareCategory(categorias[currentCategory++], null, squareActual);
                if (categorias.length == currentCategory) currentCategory = 0;
                squareActual = (SquareCategory) squareActual.next;
            }
            squareActual.next = new SquareRayo(categorias[currentCategory++], null, lastSquare, squareActual);
            if (lastSquare != null) lastSquare.next = squareActual.next;
            SquareRayo rayoActual = (SquareRayo) squareActual.next;
            if (categorias.length == currentCategory) currentCategory = 0;
            squareActual = rayoActual.next = new SquareCategory(categorias[currentCategory++], null, rayoActual);
            if (categorias.length == currentCategory) currentCategory = 0;
            for (int j = 1; j < 6; j++) {
                if (j == 1 || j == 4) {
                    squareActual.next = new SquareSpecial(null, squareActual);
                    ((SquareSpecial) squareActual.next).next = new SquareCategory(categorias[currentCategory++], null, squareActual.next);
                    j++;
                    if (categorias.length == currentCategory) currentCategory = 0;
                    squareActual = ((SquareSpecial) squareActual.next).next;
                } else {
                    squareActual.next = new SquareCategory(categorias[currentCategory++], null, squareActual);
                    if (categorias.length == currentCategory) currentCategory = 0;
                    squareActual = (SquareCategory) squareActual.next;
                }
            }
            lastSquare = squareActual;
        }
        SquareCategory iter = this.rayos[0];
        for (int i = 1; i < 5; i++) {
            iter = (SquareCategory) iter.next;
        }
        if (iter.next instanceof SquareRayo a) {
            a.previous = lastSquare;
            lastSquare.next = a;
        }
    }

}

