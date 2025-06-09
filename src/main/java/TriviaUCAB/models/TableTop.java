package TriviaUCAB.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.concurrent.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Clase que representa el tablero de un juego de mesa.
 * Esta clase maneja el inicio del juego, el turno de los jugadores, y la visualización del tablero.
 */
public class TableTop {
    /**
     * Lista de jugadores que participan en el juego.
     */
    public ArrayList<Ficha> jugadores = new ArrayList<Ficha>();

    /**
     * El centro del tablero.
     */
    private final SquareCenter centro;

    /**
     * Número máximo de jugadores en el juego.
     */
    int MAX_PLAYERS = 6;

    /**
     * Indicador de si el juego ha terminado o no.
     */
    boolean ganador = false;

    /**
     * Carpeta de inicio del sistema donde se guardarán los datos de los jugadores.
     */
    String homeFolder = System.getProperty("user.home");

    /**
     * Constructor de la clase TableTop.
     * Inicializa el tablero y asigna la posición de los jugadores en el centro.
     *
     * @param jugadores Lista de jugadores que participan en el juego.
     * @param scanner Objeto Scanner para capturar entradas del usuario.
     * @param questions Preguntas que se usarán durante el juego.
     * @throws IOException Si ocurre un error de entrada/salida al guardar datos.
     */
    public TableTop(ArrayList<Ficha> jugadores, Scanner scanner, Questions questions) throws IOException {
        centro = new SquareCenter(jugadores.size());
        this.jugadores = jugadores;
        for (Ficha jugadorActual : this.jugadores) {
            jugadorActual.posicion = this.centro;
        }
        startGame(scanner, questions);
    }/**
     * Constructor de la clase TableTop.
     * Inicializa el tablero y asigna la posición de los jugadores en el centro.
     *
     * @param scanner Objeto Scanner para capturar entradas del usuario.
     * @param questions Preguntas que se usarán durante el juego.
     * @throws IOException Si ocurre un error de entrada/salida al guardar datos.
     */
    public TableTop(Scanner scanner, Questions questions) throws IOException {
        centro = new SquareCenter(0);
        this.cargarPositions();
        startGame(scanner, questions);
    }

    /**
     * Guarda el estado actual de las fichas de los jugadores en un archivo JSON.
     *
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    private void saveFichaJson() throws IOException {
        String destinyFolder = homeFolder + File.separator + ".config";
        File destinyFolderFile = new File(destinyFolder);
        if (!destinyFolderFile.exists()) {
            boolean created = destinyFolderFile.mkdir();
            if (!created)
                throw new IOException();
        }
        Gson gson = new Gson();

        Square listaSquare[]= new Square[MAX_PLAYERS];
        int contador = 0;
        for (Ficha fa:jugadores){
            listaSquare[contador++]=fa.posicion;
            fa.posicion=null;
        }
        String json = gson.toJson(this.jugadores);
        File data = new File(destinyFolder + File.separator + "fichas.json");
        contador=0;
        for (Ficha fa:jugadores){
            fa.posicion=listaSquare[contador++];
        }
        try (FileWriter writer = new FileWriter(data)) {
            writer.write(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadFichaJson() {
        Gson gson = new Gson();
        String destinyFolder = homeFolder + File.separator + ".config";
        File destinyFolderFile = new File(destinyFolder);
        if (!destinyFolderFile.exists()) {
            boolean created = destinyFolderFile.mkdir();
            if (!created) {
                throw new RuntimeException();
            }
        }
        var a = new File(destinyFolderFile + File.separator + "fichas.json");
        if (!(a.exists())) {
            try {
                boolean created = a.createNewFile();
                if (!created)
                    throw new IOException();
                this.jugadores = new ArrayList<Ficha>();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            try (FileReader r = new FileReader(destinyFolderFile + File.separator + "fichas.json")) {
                BufferedReader bufferedReader = new BufferedReader(r);
                Type listType = new TypeToken<ArrayList<Ficha>>() {
                }.getType();
                jugadores = gson.fromJson(bufferedReader, listType);
            } catch (IOException e) {
                throw new RuntimeException("Error al leer el archivo JSON", e);
            }
        }

    }

    public void cargarPositions(){
        loadFichaJson();
        for(int i=0; i<jugadores.size(); i++){
            for(Square squareActual: centro.rayos){
                for (int j = 0; j <13 ; j++) {
                    if (squareActual instanceof  SquareRayo sr){
                        if (jugadores.get(i).getPosition()==sr.position) {
                            jugadores.get(i).posicion = sr;
                            jugadores.get(i).positionTable = sr.position;
                            jugadores.get(i).posicion.cantidadFichas++;
                            break;
                        }
                        squareActual=(Square) sr.next;
                    }else if (squareActual instanceof SquareCategory sC)
                    {
                        if (jugadores.get(i).getPosition()==sC.position) {
                            jugadores.get(i).posicion = sC;
                            jugadores.get(i).positionTable = sC.position;
                            jugadores.get(i).posicion.cantidadFichas++;
                            break;
                        }
                        squareActual=((Square) sC.next);
                    } else if (squareActual instanceof SquareSpecial sS) {
                        if (jugadores.get(i).getPosition()==sS.position) {
                            jugadores.get(i).posicion = sS;
                            jugadores.get(i).positionTable = sS.position;
                            jugadores.get(i).posicion.cantidadFichas++;
                            break;
                        }
                        squareActual=((Square) sS.next);
                    }

                }
            }
        }
    }

    /**
     * Inicia el juego, realizando los turnos de los jugadores hasta que uno gane.
     *
     * @param scanner Objeto Scanner para capturar entradas del usuario.
     * @param questions Preguntas que se usarán durante el juego.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    public void startGame(Scanner scanner, Questions questions) throws IOException {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        int jugadorActual = 0;
        int enums=0;
        var categories = Category.values();
        while (!ganador) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            this.printBoard();
            System.out.println("Turno del jugador: " + jugadores.get(jugadorActual).nickName);
            System.out.println("estadisticas: ");
            System.out.println("victorias: "+jugadores.get(jugadorActual).usuario.getVictory());
            for(int j=0; j<6; j++) {
                for (int i = 0; i < jugadores.get(jugadorActual).triangulos.size(); i++) {
                    if(j==jugadores.get(jugadorActual).triangulos.get(i)) {
                        enums++;
                    }
                }
                System.out.println("categoria: "+categories[j]+" hay un total de respondidas: "+enums);
                enums=0;
            }
            ganador=jugadores.get(jugadorActual).avanzar(scanner, questions);
            System.out.println("Posición actual:\n" + jugadores.get(jugadorActual).posicion.paint());
            System.out.print("Fin del turno del jugador: " + jugadores.get(jugadorActual).nickName+"\nPresione Enter para continuar");
            scanner.nextLine();

            if (ganador) {
                jugadores.get(jugadorActual).usuario.setVictory(jugadores.get(jugadorActual).usuario.getVictory()+1);
            }jugadorActual++;
            try {
                saveFichaJson();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (jugadorActual == jugadores.size()) jugadorActual = 0;
        }
        System.out.println("Jugador actual ganó la partida: " + jugadores.get(jugadorActual).nickName);
    }

    /**
     * Definimos el tamaño de cada celda en el tablero.
     */
    private static final int CELL_WIDTH = 6;
    private static final int CELL_HEIGHT = 4;

    /**
     * Número de columnas y filas en el tablero.
     */
    private static final int GRID_COLS = 11;
    private static final int GRID_ROWS = 8;

    /**
     * Imprime el tablero en la terminal.
     * El tablero se centra en la terminal y los rayos se dibujan alrededor del centro.
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
        // Imprime el canvas en la terminal
        for (char[] row : canvas) {
            System.out.println(new String(row));
        }
    }

    /**
     * Método auxiliar para dibujar la representación de una casilla en el canvas.
     *
     * @param canvas El lienzo donde se dibuja la casilla.
     * @param cellAscii La representación ASCII de la casilla.
     * @param startX Coordenada X donde empieza a dibujar.
     * @param startY Coordenada Y donde empieza a dibujar.
     */
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

    /**
     * Dibuja una línea simple entre dos puntos usando el algoritmo de Bresenham.
     *
     * @param canvas El lienzo donde se dibuja la línea.
     * @param x0 Coordenada X del primer punto.
     * @param y0 Coordenada Y del primer punto.
     * @param x1 Coordenada X del segundo punto.
     * @param y1 Coordenada Y del segundo punto.
     * @param c El carácter con el que se dibuja la línea.
     */
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
