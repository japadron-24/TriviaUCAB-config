package TriviaUCAB.models;

import java.util.concurrent.*;
import java.util.Scanner;

public class RevisarRespuesta implements Callable<Boolean> {
Question question;
Scanner scanner;

    @Override
    public Boolean call() throws Exception {
        System.out.print("Ingrese su respuesta: ");
        String respuesta = scanner.nextLine();
        return respuesta.equalsIgnoreCase(question.getAnswer()) ||
                question.getAnswer().toLowerCase().contains(respuesta.toLowerCase()) ||
                respuesta.toLowerCase().contains(question.getAnswer().toLowerCase());
    }

    public RevisarRespuesta(Question question, Scanner scanner) {
        this.question = question;
        this.scanner = scanner;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }
}
