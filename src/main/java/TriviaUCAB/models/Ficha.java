package TriviaUCAB.models;

import java.awt.desktop.AboutEvent;
import java.util.Arrays;
import java.util.Scanner;

public class Ficha {

    String nickName;
    Usuario usuario;
    boolean[] triangulos;
    Square posicion;
    boolean salido;

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
        return (int) (Math.random() * 6) + 1; // Genera un número entre 1 y 6
    }

    public void avanzar(Scanner scanner) {
        if (!salido && posicion instanceof brazo saliendo) {
            if (posicion instanceof SquareCenter)
                posicion = saliendo.salir(tirarDado(), this.posicion.action(scanner, this), this,null);
            else posicion = saliendo.salir(tirarDado(), 1, this,scanner);
            posicion.cantidadFichas++;

        } else {
            if (posicion instanceof movimientoBidireccional casilla) {
                posicion = casilla.movimiento(tirarDado(), this.posicion.action(scanner, this), this);
            }
            ;
        }

    }

    public boolean triangulo() {
        boolean complet= true;
        for (boolean estado : triangulos) {
            if (!estado) {
                return false;
            }
        }
        return complet;
    }
}

/*
fa.triangulos[categoriaCarta]=true;

fa.triangulos[categoriaCarta]=categoriaCatarta;

for(String t: fa.triangulos){
    if(t){
       if(t.equals(categoriaCarta)){
            return 0;
       }
    }else{
        t=categoriaCarta
        return 1;
    }
}
*/

