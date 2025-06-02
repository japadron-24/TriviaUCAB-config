package TriviaUCAB.models;

import java.util.Scanner;

// c++ es sublime
public class SquareCenter extends Square implements brazo, CategoryQuestion {
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
    public int action(Scanner scanner, Ficha jugador) {
        int a;
        do {
            a = Validator.validarInt(
                    "Tienes 6 posibles rutas, a donde te quieres mover?\n" +
                            "0. Derecha\n"+
                            "1. Abajo a la Derecha\n"+
                            "2. Abajo a la izquierda\n"+
                            "3. Izquierda\n"+
                            "4. Arriba a la Izquierda\no\n"+
                            "5. Arriba a la Derecha)",
                    scanner);
            if (a < 0 || a > 5) {
                System.out.println("ERROR vuelva a intetarlo ");
            }
        } while (a < 0 || a > 5);
        return a;
    }

    public Square salir(int move, int exit, Ficha jugador, Scanner scanner) {
        if (move < 1 || move > 6) {
            throw new IllegalArgumentException("El movimiento debe estar entre 1 y 6");
        }
        SquareCategory ciclar = this.rayos[exit];
        Square salida = ciclar;
        if (this.cantidadFichas > 0) {
            this.cantidadFichas--;
            for (int i = 1; i < move; i++) {
                if (ciclar.next instanceof SquareCategory sig) {
                    ciclar = sig;
                    salida = ciclar;
                } else if (ciclar.next instanceof SquareRayo sig) {
                    salida = sig;
                    jugador.salido = true;
                }
            }

        } else {
            throw new IllegalStateException("No hay cantidadFichas en el Centro para salir");
        }
        return salida;
    }

    public SquareCenter(int jugadores) {
        int position=0;
        this.position=position;
        position++;
        int currentCategory = 0;
        this.cantidadFichas = jugadores;
        var categorias = Category.values();
        SquareCategory lastSquare = null;
        for (int i = 0; i < this.rayos.length; i++) {
            this.rayos[i] = new SquareCategory(categorias[i], null, this, position++);
            SquareCategory squareActual = this.rayos[i];
            currentCategory = i + 1;
            if (categorias.length == currentCategory) currentCategory = 0;
            for (int j = 1; j < 5; j++) {
                squareActual.next = new SquareCategory(categorias[currentCategory++], null, squareActual, position++);
                if (categorias.length == currentCategory) currentCategory = 0;
                squareActual = (SquareCategory) squareActual.next;
            }
            squareActual.next = new SquareRayo(categorias[currentCategory++], null, lastSquare, squareActual,position++);
            if (lastSquare != null) lastSquare.next = squareActual.next;
            SquareRayo rayoActual = (SquareRayo) squareActual.next;
            if (categorias.length == currentCategory) currentCategory = 0;
            squareActual = rayoActual.next = new SquareCategory(categorias[currentCategory++], null, rayoActual, position++);
            if (categorias.length == currentCategory) currentCategory = 0;
            for (int j = 1; j < 6; j++) {
                if (j == 1 || j == 4) {
                    squareActual.next = new SquareSpecial(null, squareActual,position++);
                    ((SquareSpecial) squareActual.next).next = new SquareCategory(categorias[currentCategory++], null, squareActual.next, position++);
                    j++;
                    if (categorias.length == currentCategory) currentCategory = 0;
                    squareActual = ((SquareSpecial) squareActual.next).next;
                } else {
                    squareActual.next = new SquareCategory(categorias[currentCategory++], null, squareActual, position++);
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

    @Override
    public Square reaction(Scanner scanner, Ficha jugador, Questions questions) {

        Category[] categorias = Category.values();
        int seleccion;
        do {
            System.out.println("Seleccione una categoría:");
            for (int i = 0; i < categorias.length; i++) {
                System.out.println(i + ": " + categorias[i]);
            }
            seleccion = Validator.validarInt("", scanner);
        } while (seleccion < 0 || seleccion > categorias.length - 1);
        Category categoria = categorias[seleccion];
        Question question = questions.getRandomQuestion(categoria);

        if (question == null) {
            System.out.println("No hay preguntas disponibles para esta categoría.");
            return this;
        }

        System.out.println("Pregunta: " + question.getQuestion());
        boolean respuestaCorrecta = revisarRespuesta(scanner, question);

        if (respuestaCorrecta) {
            System.out.println("¡Respuesta correcta!");
            jugador.gano = true;
            return this;
        } else {
            System.out.println("Respuesta incorrecta.");
            return this;
        }
    }

    @Override
    public Square reaction(Scanner scanner, Ficha jugador) {
        return this;
    }

    @Override
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

    @Override
    public Square entrar(int move, int exit, Ficha jugador, Scanner scanner) {
        return this;
    }

    ;


}

