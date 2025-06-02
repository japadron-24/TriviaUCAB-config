package TriviaUCAB.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class TableTop {
    public ArrayList<Ficha> jugadores = new ArrayList<Ficha>();
    private final SquareCenter centro;
    int MAX_PLAYERS = 6;
    boolean ganador = false;

    public TableTop(ArrayList<Ficha> jugadores, Scanner scanner, Questions questions) {
        centro = new SquareCenter(jugadores.size());
        this.jugadores = jugadores;
        for (Ficha jugadorActual : this.jugadores) {
            jugadorActual.posicion = this.centro;
        }
        startGame(scanner, questions);
    }

    public void startGame(Scanner scanner, Questions questions) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        int jugadorActual = 0;

        while (!ganador) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            this.printBoard();
            System.out.println("Turno del jugador: " + jugadores.get(jugadorActual).nickName);
            ganador=jugadores.get(jugadorActual).avanzar(scanner, questions);
            if (ganador) {
                jugadores.get(jugadorActual).usuario.setVictory(jugadores.get(jugadorActual).usuario.getVictory()+1);
            }jugadorActual++;
            if (jugadorActual == jugadores.size()) jugadorActual = 0;
            System.out.println("Posición actual:\n" + jugadores.get(jugadorActual).posicion.paint());

//            Turno t = new Turno(jugadores, scanner, questions, jugadorActual);
//            Future<Void> futuro = executor.submit(t);
//            // Ejecutar el turno
//            try {
//                futuro.get(questions.getTime(), TimeUnit.SECONDS); // El límite de tiempo solo afecta al turno (pregunta)
//                printBoard();
//                System.out.println("Presione enter para continuar...");
//                scanner.nextLine(); // Aquí no hay límite de tiempo
//                jugadorActual++;
//                if (jugadorActual == jugadores.size()) jugadorActual = 0;
//            } catch (TimeoutException e) {
//                // Solo si se acabó el tiempo en la pregunta
//                System.out.println("\nSe te acabó el tiempo en la pregunta!");
//                futuro.cancel(true); // Cancelar la tarea
//                jugadorActual++;
//                if (jugadorActual == jugadores.size()) jugadorActual = 0;
//
//            } catch (Exception e) {
//                System.out.println("Error inesperado: " + e.getMessage());
//            }
        }
        System.out.println("Jugador actual ganó la partida: " + jugadores.get(jugadorActual).nickName);
//        executor.shutdown();
    }

    // Definimos el tamaño de cada celda (según la salida de paint())
    private static final int CELL_WIDTH = 6;
    private static final int CELL_HEIGHT = 4;
    // Suponemos una matriz “canvas” lo suficientemente grande para alojar el
    // tablero
    // Aquí elegimos 7 celdas de ancho por 7 celdas de alto (esto depende del layout
    // que definas)
    private static final int GRID_COLS = 11;
    private static final int GRID_ROWS = 8;

    /**
     * Imprime el tablero en la terminal.
     * El tablero se centra en la terminal y los rayos se dibujan alrededor del
     * centro.
     */
    public void printBoard() {
        int brazoLen = 6; // longitud de cada brazo (ahora 6 casillas)
        int canvasWidth = (GRID_COLS + 4) * CELL_WIDTH; // canvas más grande para brazos largos
        int canvasHeight = (GRID_ROWS + 4) * CELL_HEIGHT;
        // Inicializa el canvas con espacios
        char[][] canvas = new char[canvasHeight][canvasWidth];
        for (int i = 0; i < canvasHeight; i++) {
            Arrays.fill(canvas[i], ' ');
        }

        // Posiciona el centro en el medio del canvas
        int centerX = canvasWidth / 2 - CELL_WIDTH / 2;
        int centerY = canvasHeight / 2 - CELL_HEIGHT / 2;
        drawCell(canvas, this.centro.paint(), centerX, centerY);

        // Definimos offsets unitarios para cada dirección de los 6 rayos (hexágono
        // regular, ángulos de 60°)
        double[][] dirOffsets = {
                {1, 0}, // Este
                {0.5, 0.75}, // Noreste
                {-0.5, 0.75}, // Noroeste
                {-1, 0}, // Oeste
                {-0.5, -0.75}, // Suroeste
                {0.5, -0.75} // Sureste
        };
        // Dibuja los brazos
        Square[] extremosSquares = new Square[6];
        double[][] extremos = new double[6][2];
        for (int i = 0; i < 6; i++) {
            double dx = dirOffsets[i][0];
            double dy = dirOffsets[i][1];
            double posX = centerX;
            double posY = centerY;
            Square actual = centro.rayos[i];
            for (int paso = 1; actual != null && paso <= brazoLen; paso++) {
                posX += dx * CELL_WIDTH;
                posY += dy * CELL_HEIGHT;
                drawCell(canvas, actual.paint(), posX, posY);
                if (paso == brazoLen) {
                    extremos[i][0] = posX;
                    extremos[i][1] = posY;
                    extremosSquares[i] = actual; // Guardar antes de avanzar getNext()
                }
                if (actual instanceof SquareCategory) {
                    actual = ((SquareCategory) actual).getNext();
                } else if (actual instanceof SquareRayo) {
                    actual = ((SquareRayo) actual).getNext();
                } else if (actual instanceof SquareSpecial) {
                    actual = ((SquareSpecial) actual).getNext();
                } else {
                    actual = null;
                }
            }
        }

        // Dibuja el círculo exterior con celdas usando getNext en cada paso
        for (int i = 0; i < 6; i++) {
            int next = (i + 1) % 6;
            double x0 = extremos[i][0];
            double y0 = extremos[i][1];
            double x1 = extremos[next][0];
            double y1 = extremos[next][1];
            // Ajuste para extremos
            if (i == 0) {
                x1 += CELL_WIDTH; // Mueve hacia la derecha
                x0 += CELL_WIDTH; // Mueve hacia la derecha
            } else if (i == 1) {
                y0 += CELL_HEIGHT - 1; // Mueve hacia abajo
                y1 += CELL_HEIGHT - 1; // Mueve hacia abajo
                x0 += CELL_WIDTH; // Mueve hacia la derecha
            } else if (i == 2) {
                x0 -= CELL_WIDTH - 3; // Mueve hacia la izquierda
                x1 -= CELL_WIDTH; // Mueve hacia la izquierda
                y0 += CELL_HEIGHT - 1; // Mueve hacia abajo
                y1 += CELL_HEIGHT - 1; // Mueve hacia abajo
            } else if (i == 3) {
                x0 -= CELL_WIDTH; // Mueve hacia la izquierda
                x1 -= CELL_WIDTH; // Mueve hacia la izquierda
            } else if (i == 4) {
                x0 -= CELL_WIDTH; // Mueve hacia la izquierda
                y0 -= CELL_HEIGHT - 1; // Mueve hacia arriba
                y1 -= CELL_HEIGHT - 1; // Mueve hacia arriba
            } else if (i == 5) {
                y0 -= CELL_HEIGHT - 1; // Mueve hacia arriba\
                y1 -= CELL_HEIGHT - 1; // Mueve hacia arriba
                x0 += CELL_WIDTH - 2; // Mueve hacia la derecha
                x1 += CELL_WIDTH; // Mueve hacia la derecha
            }

            int steps = 6;
            Square actual = extremosSquares[i];
            for (int s = 1; s <= steps; s++) { // <= para incluir la última casilla
                double px = x0 + (x1 - x0) * s / steps;
                double py = y0 + (y1 - y0) * s / steps;
                // Avanza al siguiente Square en cada paso
                if (actual != null) {
                    if (actual instanceof SquareCategory) {
                        actual = ((SquareCategory) actual).getNext();
                    } else if (actual instanceof SquareRayo) {
                        actual = ((SquareRayo) actual).getNext();
                    } else if (actual instanceof SquareSpecial) {
                        actual = ((SquareSpecial) actual).advance();
                    } else {
                        actual = null;
                    }
                }
                if (actual != null) {
                    drawCell(canvas, actual.paint(), px, py);
                }
            }
        }
        // Dibuja líneas para cerrar el círculo (opcional, para mayor densidad)
        /*
         * for (int i = 0; i < 6; i++) {
         * int x0 = extremos[i][0] + CELL_WIDTH / 2;
         * int y0 = extremos[i][1] + CELL_HEIGHT / 2;
         * int x1 = extremos[(i + 1) % 6][0] + CELL_WIDTH / 2;
         * int y1 = extremos[(i + 1) % 6][1] + CELL_HEIGHT / 2;
         * drawLine(canvas, x0, y0, x1, y1, '*');
         * }
         */
        // Imprime el canvas en la terminal
        for (char[] row : canvas) {
            System.out.println(new String(row));
        }
    }

    // Método auxiliar para dibujar la representación de una casilla en el canvas
    private static void drawCell(char[][] canvas, String cellAscii, double startX, double startY) {
        String[] lines = cellAscii.split("\n");
        for (int i = 0; i < lines.length; i++) {
            int row = (int) startY + i;
            if (row >= 0 && row < canvas.length) {
                for (int j = 0; j < lines[i].length(); j++) {
                    int col = (int) startX + j;
                    if (col >= 0 && col < canvas[0].length) {
                        canvas[row][col] = lines[i].charAt(j);
                    }
                }
            }
        }
    }

    // Dibuja una línea simple entre dos puntos usando el algoritmo de Bresenham
    private static void drawLine(char[][] canvas, int x0, int y0, int x1, int y1, char c) {
        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);
        int sx = x0 < x1 ? 1 : -1;
        int sy = y0 < y1 ? 1 : -1;
        int err = dx - dy;
        while (true) {
            if (y0 >= 0 && y0 < canvas.length && x0 >= 0 && x0 < canvas[0].length) {
                canvas[y0][x0] = c;
            }
            if (x0 == x1 && y0 == y1)
                break;
            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x0 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y0 += sy;
            }
        }
    }
}



