package TriviaUCAB.models;

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
        for (int i = 0; i < this.triangulos.length; i++) {
            this.triangulos[i] = false;
        }
        this.posicion = posicion;
        this.salido = false;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }

    public void avanzar(int move) {
        if (salido) {
            for (int i = 0; i < move; i++) {
                posicion = posicion.getNext();
            }
        } else if (posicion instanceof SquareCenter centro) {
            posicion=centro.salir(move, 0);
            posicion.cantidadFichas++;
        } else if (posicion instanceof SquareCategory categoria) {
            if (categoria.cantidadFichas > 0) {
                posicion = categoria.getNext();
                posicion.cantidadFichas++;
            } else {
                throw new IllegalStateException("No hay cantidadFichas en la categoría para avanzar");
            }
        } else {
            throw new IllegalStateException("Posición no válida para avanzar");
            
        }
        
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

