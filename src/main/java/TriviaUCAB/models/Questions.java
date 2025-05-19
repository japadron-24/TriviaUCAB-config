package TriviaUCAB.models;

import java.util.ArrayList;
import java.util.Scanner;

public class Questions {
    public ArrayList<Question> waitApproved = new ArrayList<>();
    private ArrayList<Question> approved = new ArrayList<>();      //todo va guardado en archivos json
    private ArrayList<Question> negativeApproved = new ArrayList<>();
    private double time;

    public void setTime() {
        System.out.println("Ingrese el tiempo limite de las preguntas que no exceda de dos minutos");
        Scanner limitedTime = new Scanner(System.in);
        double time = limitedTime.nextDouble();
        while(time < 0 || time > 2) {
            System.out.print("El tiempo que introdujo se exccede del limite de las preguntas: ");
            time = limitedTime.nextDouble();
        }
        this.time = time;
    }

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
    public void addApproved(int position, Usuario currentUser) {
        if (!currentUser.userName.equals(waitApproved.get(position).getCreator())) {
            waitApproved.get(position).aprovedBy = currentUser.userName;
            approved.add(waitApproved.get(position));
            waitApproved.remove(position);
        } else System.out.println("No puedes aprobar tu propia pregunta.");
    }

    public int getSize(int lista) {
        return switch (lista) {
            case 1 -> waitApproved.size();
            case 2 -> approved.size();
            case 3 -> negativeApproved.size();
            default -> 0;
        };
    }

    public void visualAproved(Usuario usuarioActual) {  //pasando al lista
        int counter = 1;
        System.out.println("+-----+--------------------------------------------------------------+--------------------------------+-------------------+----------------------+");
        System.out.println("| #   | Pregunta                                                     | Respuesta                      | Categoria         |    Creador           |");
        System.out.println("+-----+--------------------------------------------------------------+--------------------------------+-------------------+----------------------+");
        for (Question question : this.waitApproved) {
            if (question.getQuestion().equals(usuarioActual.getUserName()))
                System.out.println(question.tableFormat(counter));
            counter++;
        }
        System.out.println("+-----+--------------------------------------------------------------+--------------------------------+-------------------+----------------------+");
    }

    public void visualQuestions(int numberList, Usuario currentUser) {  //pasando al lista
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
            if (numberList != 2) {
                System.out.println(question.tableFormat(counter));

            } else if ( (currentUser.getUserName().equals(question.aprovedBy) ||
                        currentUser.getUserName().equals(question.getCreator())))
                System.out.println(question.tableFormat(counter));
            counter++;
        }
        System.out.println("+-----+--------------------------------------------------------------+--------------------------------+-------------------+----------------------+");
    }


}
