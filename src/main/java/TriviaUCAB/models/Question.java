package TriviaUCAB.models;

public class Question {
    private String question;
    private String answer;
    final String creator;
    public String aprovedBy;
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
            this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {      //solo se usaran en la lista de espera
            this.answer = answer;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;

    }

    public String getCreator() {
        return creator;
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
            return String.format("| %-3d | %-60s | %-30s | %-18s| %-20s | \n" +
                                 "|     | %-60s | " + " ".repeat(30) + " | " + " ".repeat(18) + " | " + " ".repeat(20) + "|",
                                  row, this.question.substring(0,60), this.answer, this.category, this.creator, this.question.substring(question.length() - 60));
        }
        return String.format("| %-3d | %-60s | %-30s | %-18s| %-20s |", row, this.question, this.answer, this.category, this.creator);
    }


}
