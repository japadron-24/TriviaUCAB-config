package TriviaUCAB.models;

import java.util.ArrayList;
import java.util.Scanner;

public class Questions {
    public ArrayList<Question> waitApproved;
    private ArrayList<Question> approved;      //todo va guardado en archivos json
    private ArrayList<Question> negativeApproved;

    public void addWaitApproved(Question question) {
        waitApproved.add(question);
    }

    public void visualQuestions(int numberList) {  //pasando al lista
        ArrayList<Question> questions;
        switch (numberList) {
            case 1:
                questions = waitApproved;
                break;
            case 2:
                questions = approved;
                break;
            case 3:
                questions = negativeApproved;
                break;
            default:
                questions = waitApproved;
                break;
        }
        int counter = 1;
        System.out.println("+-----+--------------------------------+--------------------------------+-----------------+-----------------+");
        System.out.println("| #   | Pregunta                       | Respuesta                      | Categoria       |    Creador      |");
        System.out.println("+-----+--------------------------------+--------------------------------+-----------------+-----------------+");
        for (Question question : questions) {
            System.out.println(counter+" "+question);
            counter++;

        }
        for (int i = 0; i < waitApproved.size(); i++) {
            System.out.println(i + " " + waitApproved.get(i).getQuestion());
        }
    }


}
