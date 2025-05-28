package TriviaUCAB.models;

import java.util.Arrays;

public class TableTop {
    public Ficha[] jugadores = new Ficha[6];
    private SquareCenter centro;
    public TableTop(){
        centro = new SquareCenter();
    }
    // Definimos el tamaño de cada celda (según la salida de paint())
    private static final int CELL_WIDTH = 7;
    private static final int CELL_HEIGHT = 3;
    // Suponemos una matriz “canvas” lo suficientemente grande para alojar el tablero
    // Aquí elegimos 7 celdas de ancho por 7 celdas de alto (esto depende del layout que definas)
    private static final int GRID_COLS = 7;
    private static final int GRID_ROWS = 7;

    public static void main(String[] args) {
        // Crea una instancia de tu tablero central (con sus 6 rayos)
        SquareCenter board = new SquareCenter();
        printBoard(board);
    }

    public static void printBoard(SquareCenter board) {
        int canvasWidth = GRID_COLS * CELL_WIDTH;
        int canvasHeight = GRID_ROWS * CELL_HEIGHT;
        // Inicializa el canvas con espacios
        char[][] canvas = new char[canvasHeight][canvasWidth];
        for (int i = 0; i < canvasHeight; i++) {
            Arrays.fill(canvas[i], ' ');
        }

        // Posiciona el centro en el medio del canvas
        int centerX = canvasWidth / 2 - CELL_WIDTH / 2;
        int centerY = canvasHeight / 2 - CELL_HEIGHT / 2;
        drawCell(canvas, board.paint(), centerX, centerY);

        // Definimos offsets para ubicar cada uno de los 6 rayos alrededor del centro.
        // Los offsets se pueden ajustar según el "layout" deseado.
        int[][] offsets = {
                { 0, -CELL_HEIGHT},                // Norte
                { CELL_WIDTH, -CELL_HEIGHT / 2},     // Noreste
                { CELL_WIDTH,  CELL_HEIGHT / 2},     // Sureste
                { 0, CELL_HEIGHT},                  // Sur
                {-CELL_WIDTH,  CELL_HEIGHT / 2},     // Suroeste
                {-CELL_WIDTH, -CELL_HEIGHT / 2}      // Noroeste
        };

        // Supongamos que board.rayos[] contiene cada casilla de rayo y estos implementan paint()
        for (int i = 0; i < 6; i++) {
            // Calcula la posición del rayo i-esimo relativo al centro
            int posX = centerX + offsets[i][0];
            int posY = centerY + offsets[i][1];
            // Si lo deseas, puedes llamar a paint() del rayo; aquí asumo que board.rayos[i] tiene ese método
            drawCell(canvas, board.rayos[i].paint(), posX, posY);
        }

        // Imprime el canvas en la terminal
        for (char[] row : canvas) {
            System.out.println(new String(row));
        }
    }

    // Método auxiliar para dibujar la representación de una casilla en el canvas
    private static void drawCell(char[][] canvas, String cellAscii, int startX, int startY) {
        String[] lines = cellAscii.split("\n");
        for (int i = 0; i < lines.length; i++) {
            int row = startY + i;
            if (row >= 0 && row < canvas.length) {
                for (int j = 0; j < lines[i].length(); j++) {
                    int col = startX + j;
                    if (col >= 0 && col < canvas[0].length) {
                        canvas[row][col] = lines[i].charAt(j);
                    }
                }
            }
        }
    }
}
