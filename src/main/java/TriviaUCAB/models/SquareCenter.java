package TriviaUCAB.models;

// c++ es sublime
public class SquareCenter extends Square {
    //miembros
    protected SquareCategory rayos[] = new SquareCategory[6];
    //metodos
    @Override
    public String paint() {
        if (cantidadFichas > 0) {
            return  "/────\\\n"+
                    "│  "+cantidadFichas+" │\n"+
                    "\\────/";
        }
        return  "/────\\\n"+
                "│    │\n"+
                "\\────/";
    }

    @Override
    public void action() {

    }

    public Square salir(int move, int exit) {
        if (move < 1 || move > 6) {
            throw new IllegalArgumentException("El movimiento debe estar entre 1 y 6");
        }
        SquareCategory rayo = this.rayos[exit];
        if (this.cantidadFichas > 0) {
            this.cantidadFichas--;
            for (int i = 0; i < move; i++) {
                if (rayo.next instanceof SquareCategory) {
                    rayo = (SquareCategory) rayo.next;
                } else if (rayo.next instanceof SquareRayo) {
                    rayo = ((SquareRayo) rayo.next).next;
                } else {
                    throw new IllegalStateException("Movimiento no válido, no se puede avanzar más allá de un rayo");
                }
            }
            return rayo;
        } else {
            throw new IllegalStateException("No hay cantidadFichas en el Centro para salir");
        }
        
    }

    public SquareCenter(int jugadores) {
        int x = 0;
        this.cantidadFichas = jugadores;
        var categorias = Category.values();
        SquareCategory lastSquare = null;
        for (int i = 0; i < this.rayos.length; i++) {
            this.rayos[i] = new SquareCategory(categorias[i], null, this);
            SquareCategory squareActual = this.rayos[i];
            x = i + 1;
            if (6 == x) x = 0;
            for (int j = 1; j < 5; j++) {
                squareActual.next = new SquareCategory(categorias[x++], null, squareActual);
                if (6 == x) x = 0;
                squareActual = (SquareCategory) squareActual.next;
            }
            squareActual.next = new SquareRayo(categorias[x++], null, lastSquare, squareActual);
            if (lastSquare!=null) lastSquare.next=squareActual.next;
            SquareRayo rayoActual = (SquareRayo) squareActual.next;
            if (6 == x) x = 0;
            squareActual = rayoActual.next = new SquareCategory(categorias[x++], null, rayoActual);
            if (6 == x) x = 0;
            for (int j = 1; j < 6; j++) {
                if (j == 1 || j == 4) {
                    squareActual.next = new SquareSpecial(null, squareActual);
                    ((SquareSpecial) squareActual.next).next = new SquareCategory(categorias[x++], null, squareActual.next);
                    j++;
                    if (6 == x) x = 0;
                    squareActual = ((SquareSpecial) squareActual.next).next;
                } else {
                    squareActual.next = new SquareCategory(categorias[x++], null, squareActual);
                    if (6 == x) x = 0;
                    squareActual = (SquareCategory) squareActual.next;
                }
            }
            lastSquare = squareActual;
        }
        var iter = this.rayos[0];
        for (int i = 1; i < 5; i++) {
            iter =(SquareCategory) iter.next;
        }
        if (iter.next instanceof SquareRayo a) {
            a.previous = lastSquare;
            lastSquare.next=a;
        }
    }

    @Override
    public Square getNext() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getNext'");
    }
}

