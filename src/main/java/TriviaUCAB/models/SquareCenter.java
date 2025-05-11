package TriviaUCAB.models;
// c++ es sublime
public class SquareCenter extends Square {
    //miembros
    protected SquareCategory rayos[] = new SquareCategory[6];

    //metodos
    @Override
    public void paint() {

    }

    @Override
    public void action() {

    }
    public SquareCenter(){
        int x=0;
        var categorias = Category.values();
        for (int i = 0; i < rayos.length; i++) {
            this.rayos[i] = new SquareCategory(categorias[i], null, this);
            SquareCategory squareActual= rayos[i];
            x = i+1;
            for (int j = 1; j < 5; j++) {
                if (6==x){x=0;}
                squareActual.next = new SquareCategory(categorias[x++], null, squareActual);
                squareActual=(SquareCategory)squareActual.next;
            }
        }
    }
}

