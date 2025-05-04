package TriviaUCAB.models;

public class Question {
    private String question;
    private String answer;
    private int condition = 0;    //si es 0 en espera, si es 1 aprobado, si es 2 no aprobado
    final String creator;
    private Category category;


    public Question(String question, String answer, String creator, Category category) {
        this.question = question;
        this.answer = answer;
        this.creator = creator;
        this.category = category;

    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {      //solo se usaran en la lista de espera
        if (condition == 0) {
            this.question = question;
        } else {
            System.out.println("Error");
        }
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {      //solo se usaran en la lista de espera
        if (condition == 0) {
            this.answer = answer;
        } else {
            System.out.println("Error");
        }
    }

    public int getCondition() {
        return condition;
    }

    public void setCondition(boolean approved) {
        if (approved) {
            this.condition = 1;
        } else {
            this.condition = 2;
        }
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;

    }

    @Override
    public String toString() {
        return "Question{" +
                "creator='" + creator + '\'' +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                ", category=" + category +
                '}';
    }

    public String tableFormat(int row) {
        if (question.length() > 60) {
            return String.format("| %-3d | %-60s | %-30s | %-18s| %-20s| \n" +
                                 "|     | %-60s  | " + " ".repeat(30) + " | " + " ".repeat(18) + " | " + " ".repeat(20) + "|",
                                  row, this.question.substring(0,60), this.answer, this.category, this.creator, this.question.substring(question.length() - 60));
        }
        return String.format("| %-3d | %-60s | %-30s | %-18s| %-20s |", row, this.question, this.answer, this.category, this.creator);
    }


}
