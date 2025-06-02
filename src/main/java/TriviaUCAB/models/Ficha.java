package TriviaUCAB.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Representa una ficha de un jugador dentro del juego.
 * Administra el estado del jugador, su posición en el tablero,
 * los triángulos obtenidos y el avance en el juego.
 */
public class Ficha {

    /**
     * Apodo del jugador asociado a esta ficha.
     */
    String nickName;

    /**
     * Usuario al que pertenece esta ficha.
     */
    Usuario usuario;

    /**
     * Array que representa los triángulos (categorías ganadas).
     */
    ArrayList<Integer> triangulos;

    /**
     * Casilla actual en la que se encuentra esta ficha.
     */
    Square posicion;

    /**
     * Indica si el jugador ya salió del centro.
     */
    boolean salido;

    /**
     * Indica si el jugador ya ha entrado al centro.
     */
    boolean entrado = false;

    /**
     * Indica si el jugador ha ganado.
     */
    boolean gano = false;

    /**
     * Posición numérica de la ficha en el tablero.
     */
    int positionTable;

    /**
     * Constructor de la ficha.
     *
     * @param nickName Apodo del jugador.
     * @param usuario Usuario propietario.
     * @param triangulos Categorías disponibles.
     * @param posicion Posición inicial de la ficha.
     */
    public Ficha(String nickName, Usuario usuario, Category[] triangulos, Square posicion) {
        this.nickName = nickName;
        this.usuario = usuario;
        this.triangulos = new boolean[triangulos.length];
        Arrays.fill(this.triangulos, false);
        this.posicion = posicion;
        this.salido = false;
    }

    /**
     * Retorna el usuario asociado a esta ficha.
     *
     * @return El usuario.
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Simula la tirada de un dado (valores entre 1 y 6).
     *
     * @return Número aleatorio del dado.
     */
    private int tirarDado() {
        int dado = (int) (Math.random() * 6) + 1;
        System.out.println("Tirando el dado..." + dado);
        return dado;
    }

    /**
     * Lógica para avanzar en el tablero dependiendo del estado de la ficha.
     *
     * @param scanner Entrada del usuario.
     * @param questions Banco de preguntas del juego.
     * @return {@code true} si el jugador ha ganado, {@code false} en otro caso.
     */
    public boolean avanzar(Scanner scanner, Questions questions) {
        if (!salido && posicion instanceof brazo saliendo) {
            if (posicion instanceof SquareCenter)
                posicion = saliendo.salir(tirarDado(), this.posicion.action(scanner, this), this, null);
            else
                posicion = saliendo.salir(tirarDado(), 1, this, scanner);
            posicion.cantidadFichas++;
            this.positionTable = posicion.position;
            if (posicion instanceof CategoryQuestion cQ) {
                cQ.reaction(scanner, this, questions);
            }
        } else if (entrado) {
            if (posicion instanceof brazo saliendo) {
                posicion = saliendo.entrar(tirarDado(), 1, this, scanner);
                posicion.cantidadFichas++;
                this.positionTable = posicion.position;
                if (posicion instanceof SquareCenter sC) {
                    sC.reaction(scanner, this, questions);
                    if (this.gano) return true;
                }
            }
        } else {
            if (posicion instanceof movimientoBidireccional casilla) {
                posicion = casilla.movimiento(tirarDado(), this.posicion.action(scanner, this), this);
                posicion.cantidadFichas++;
                this.positionTable = posicion.position;
                if (posicion instanceof SquareSpecial sS) {
                    posicion = sS.reaction(scanner, this);
                    posicion.cantidadFichas++;
                    this.positionTable = posicion.position;
                }
                if (posicion instanceof CategoryQuestion cQ) {
                    cQ.reaction(scanner, this, questions);
                }
            }
        }
        return false;
    }

    /**
     * Obtiene las preguntas aprobadas de una categoría específica.
     *
     * @param questions Banco de preguntas.
     * @param category Categoría deseada.
     * @return Lista de preguntas filtradas por categoría.
     */
    public ArrayList<Question> getQuestions(Questions questions, Category category) {
        ArrayList<Question> aprobadas = questions.getApproved();
        ArrayList<Question> filtradas = new ArrayList<>();
        for (Question q : aprobadas) {
            if (q.getCategory().equals(category)) {
                filtradas.add(q);
            }
        }
        return filtradas;
    }

    /**
     * Verifica si el jugador ha completado todos los triángulos.
     *
     * @return {@code true} si el jugador tiene todos los triángulos, {@code false} si no.
     */
    public int triangulo(int i) {
        return this.triangulos.get(i);
    }

    /**
     * Marca un triángulo como completado para una categoría específica.
     *
     * @param categoria Categoría ganada.
     */
    public void incrementarPuntos(Category categoria) {
        int n = categoria.ordinal();
        triangulos.add(n);
    }

    public int getPosition(){
        return positionTable;
    }

}
