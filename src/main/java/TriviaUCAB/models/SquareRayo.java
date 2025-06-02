package TriviaUCAB.models;

import java.util.Scanner;

public class SquareRayo extends Square implements movimientoBidireccional ,CategoryQuestion {
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
            jugador.entrado = true;
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

    @Override
    public Square reaction(Scanner scanner,Ficha jugador) {
        return null;
    }
    public boolean revisarRespuesta(Scanner scanner, Question question) {
        System.out.print("Ingrese su respuesta: ");
        String respuesta = scanner.nextLine();
        if (
                respuesta.equalsIgnoreCase(question.getAnswer()) ||
                        question.getAnswer().toLowerCase().contains(respuesta.toLowerCase()) ||
                        respuesta.toLowerCase().contains(question.getAnswer().toLowerCase())
        ) {
            return true;
        } else {
            return false;
        }
    }
    public Square reaction(Scanner scanner, Ficha jugador, Questions questions) {
        Question question = questions.getRandomQuestion(categoria);

        if (question == null) {
            System.out.println("No hay preguntas disponibles para esta categoría.");
            return this;
        }

        System.out.println("Pregunta: " + question.getQuestion());
        boolean respuestaCorrecta = revisarRespuesta(scanner, question);

        if (respuestaCorrecta) {
            System.out.println("¡Respuesta correcta!");
            jugador.incrementarPuntos(categoria);
            return getNext();
        } else {
            System.out.println("Respuesta incorrecta.");
            return this;
        }
    }




}
