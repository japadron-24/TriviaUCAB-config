package App_Juego_Proyecto.Jugador;

import java.util.ArrayList;

public class MoveTokenPlayer {
    private ArrayList<Integer> posiblePositions = new ArrayList<>();

    private boolean cardinalPositions(int positionOrigin){
        if (positionOrigin%12==7) {
            return true;
        }else{
            return false;
        }
    }

    private boolean afterCardinalpositions(int positionOrigin){
        if((positionOrigin-1)%12==0){
            return true;
        }
        else{
            return false;
        }
    }

    private int aroundZero(int iterator){
        int position=12*(iterator+1);
        if(position>72){
            position=position-72;
        }
        return position;
    }

    private void moveCenter(int positionCenter, int positionOrigin, int dice){
        dice--;
        for (int i = 0; i < 6; i++) {
            positionOrigin=aroundZero(i);
            if(positionCenter!=positionOrigin) {
                if(dice != 0) {
                    moveLeft(positionOrigin, dice);
                    if(cardinalPositions(positionOrigin-1)) {
                        positionOrigin--;
                        dice--;
                        if(dice!=0) {
                            moveRight(positionOrigin + 6, dice - 1);
                        }
                    }
                }
                else{
                    posiblePositions.add(positionOrigin);
                }
            }
        }
    }

    //INCOMPLETO
    private void moveRight(int positionOrigin, int dice){
        boolean center=false;
        if(positionOrigin==73){
            positionOrigin=1;
        }
        while(dice!=0){
             if (cardinalPositions(positionOrigin)) { //esto es para seguir moviendose en el circulo
                if(positionOrigin==67){
                    positionOrigin = 1;
                }else {
                    positionOrigin = positionOrigin+6;
                }
                dice--;
                moveRight(positionOrigin, dice);
            }
            else if(positionOrigin%12==0 && positionOrigin!=0) {
                int positionCenter=positionOrigin;  //cambiar esto que ahora dpenda de 0 y no de los mul de 12
                positionOrigin=0; //esto es para moverse normal en el circulo e ir a las lineas centrales
                dice--;
                 if(dice!=0) {
                     moveCenter(positionCenter, positionOrigin, dice);
                     center=true;
                 }
            }
            else{
                positionOrigin++;
                dice--;
            }
        }
        if(!center) {
            posiblePositions.add(positionOrigin); //corregir
        }
    }

    //INCOMPLETO
    private void moveLeft(int positionOrigin, int dice){
        while(dice!=0){
            if(cardinalPositions(positionOrigin-1)) {
                positionOrigin--;
                dice--;
                if(dice!=0) {
                    moveRight(positionOrigin + 6, dice - 1);
                }
            }
            else if (afterCardinalpositions(positionOrigin)) {
                if(positionOrigin==1){
                    positionOrigin=67; //numero necesario en el tablero
                }else{
                    positionOrigin = positionOrigin-6;
                }
                dice--;
                if(dice!=0){
                    moveRight(positionOrigin, dice);
                }
            } else {
                positionOrigin--;
                dice--;
            }
        }
        posiblePositions.add(positionOrigin);
    }

    public void move(int positionOrigin, int dice){
        if(positionOrigin==0){
            moveCenter(0, positionOrigin, dice);
        }else{
            moveRight(positionOrigin, dice);
            System.out.println("derecha");
            for(int i=0; i<posiblePositions.size(); i++){
                System.out.println(posiblePositions.get(i));
            }
            moveLeft(positionOrigin, dice);
            System.out.println("izquierda");
            for(int i=0; i<posiblePositions.size(); i++){
                System.out.println(posiblePositions.get(i));
            }
        }
    }

    public ArrayList<Integer> getPosiblePositions() {
        return posiblePositions;        // no hara falta, solo lo usamos para probar el funcionamiento
    }

    protected int chooseSquare(int decitionPosition){  //SI LLEGO A USAR SETS NO HARA FALTA ESTE METODO
        //decitionPosition determina la posicion que eligio el usuario que son las posibilidades del dado
        return (posiblePositions.get(decitionPosition-1));
    }


}
