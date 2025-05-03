package TriviaUCAB.models;
import java.util.Scanner;

public class ModifyTime {
    private double time;

    public ModifyTime(double time) {
        this.time = time;

    }
    public double getTime() {
        return time;
    }
    public void setTime() {
        System.out.println("Ingrese el tiempo limite de las preguntas");
        Scanner limitedTime = new Scanner(System.in);
        double time = limitedTime.nextDouble();
        while(time < 0 || time > 2) {
            System.out.print("El tiempo que introdujo se exccede del limite de las preguntas: ");
            time = limitedTime.nextDouble();
        }
        this.time = time;
    }
}
