package TriviaUCAB.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Ficha {

    String nickName;
    Usuario usuario;
    boolean[] triangulos;
    Square posicion;
    boolean salido;
    boolean entrado=false;
    boolean gano=false;

    public Ficha(String nickName, Usuario usuario, Category[] triangulos, Square posicion) {
        this.nickName = nickName;
        this.usuario = usuario;
        this.triangulos = new boolean[triangulos.length];
        Arrays.fill(this.triangulos, false);
        this.posicion = posicion;
        this.salido = false;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    private int tirarDado() {
        int dado = (int) (Math.random() * 6) + 1; // Genera un número entre 1 y 6
        System.out.println("Tirando el dado..." + dado);
        return dado;// Genera un número entre 1 y 6
    }

    public boolean avanzar(Scanner scanner, Questions questions) {
        if (!salido && posicion instanceof brazo saliendo) {
            if (posicion instanceof SquareCenter)
                posicion = saliendo.salir(tirarDado(), this.posicion.action(scanner, this), this, null);
            else posicion = saliendo.salir(tirarDado(), 1, this, scanner);
            posicion.cantidadFichas++;
            if (posicion instanceof CategoryQuestion cQ) {
                cQ.reaction(scanner, this, questions);
            }
        }
        else if(entrado) {
            if (posicion instanceof  brazo saliendo) {
                posicion = saliendo.entrar(tirarDado(), 1, this, scanner);
                if (posicion instanceof SquareCenter sC){
                    sC.reaction(scanner,this,questions);
                    if (this.gano) return true;
                }
            }
        }else{
            if (posicion instanceof movimientoBidireccional casilla) {
                posicion = casilla.movimiento(tirarDado(), this.posicion.action(scanner, this), this);
                posicion.cantidadFichas++;
                if (posicion instanceof SquareSpecial sS) {
                    posicion = sS.reaction(scanner, this);
                    posicion.cantidadFichas++;
                }
                if (posicion instanceof CategoryQuestion cQ) {
                    cQ.reaction(scanner, this, questions);
                }
            }
        }
        return false;
    }

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

    public boolean triangulo() {
        boolean complet = true;
        for (boolean estado : triangulos) {
            if (!estado) {
                return false;
            }
        }
        return complet;
    }

    public void incrementarPuntos(Category categoria) {
        int n =categoria.ordinal();
        this.triangulos[n] = true;
    }
}
