package TriviaUCAB.models;

import java.util.ArrayList;
import java.util.Scanner;

public class Questions {
    public ArrayList<Question> waitApproved = new ArrayList<>();
    private ArrayList<Question> approved= new ArrayList<>();      //todo va guardado en archivos json
    private ArrayList<Question> negativeApproved=new ArrayList<>();

    public void addWaitApproved(Question question) {
        waitApproved.add(question);
    }

    public void visualQuestions(int numberList) {  //pasando al lista
        ArrayList<Question> questions = switch (numberList) {
            case 1 -> waitApproved;
            case 2 -> approved;
            case 3 -> negativeApproved;
            default -> waitApproved;
        };
        int counter = 1;
        System.out.println("+-----+--------------------------------------------------------------+--------------------------------+-------------------+----------------------+");
        System.out.println("| #   | Pregunta                                                     | Respuesta                      | Categoria         |    Creador           |");
        System.out.println("+-----+--------------------------------------------------------------+--------------------------------+-------------------+----------------------+");
        for (Question question : questions) {
            System.out.println(question.tableFormat(counter));
            counter++;
        }
        System.out.println("+-----+--------------------------------------------------------------+--------------------------------+-------------------+----------------------+");
    }


}
