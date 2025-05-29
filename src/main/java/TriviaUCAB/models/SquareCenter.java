package TriviaUCAB.models;

// c++ es sublime
public class SquareCenter extends Square {
    //miembros
    protected SquareCategory rayos[] = new SquareCategory[6];

    //metodos
    @Override
    public String paint() {
        return  "/----\\\n"+
                "|     |\n"+
                "\\----/";
    }

    @Override
    public void action() {

    }

    public SquareCenter() {
        int x = 0;
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
}

