package TriviaUCAB.models;

import java.util.ArrayList;

public class Questions {
    public ArrayList<Question> waitApproved = new ArrayList<>();
    private ArrayList<Question> approved = new ArrayList<>();      //todo va guardado en archivos json
    private ArrayList<Question> negativeApproved = new ArrayList<>();

    //agregar preguntas
    public void addWaitApproved(Question question) {
        waitApproved.add(question);
    }


    //modificar preguntas
    public void modifyQuestion(int position, String question) {
        waitApproved.get(position).setQuestion(question);
    }

    public void modifyAnswer(int position, String answer) {
        waitApproved.get(position).setAnswer(answer);

    }

    public void modifyCategory(int position, Category category) {
        waitApproved.get(position).setCategory(category);

    }


    //eliminar preguntas
    public void addRejeter(int position) {
        negativeApproved.add(waitApproved.get(position));
        waitApproved.remove(position);

    }

    //eliminar pregunta de cualquier lista
    public void delete(int position, int lista) {
        switch (lista) {
            case 1:
                waitApproved.remove(position);
                break;
            case 2:
                this.approved.remove(position);
                break;
            case 3:
                this.negativeApproved.remove(position);
                break;
        }

    }


    //aprobar preguntas
    public void addApproved(int position) {
        approved.add(waitApproved.get(position));
        waitApproved.remove(position);
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
