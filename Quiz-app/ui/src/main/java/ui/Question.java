package ui;

import java.util.List;

public class Question {
    private final String question;
    private final List<String> choices;
    private final int answer;

    public Question(String question, List<String> choices, int answer){
        if (answer>=choices.size() || answer<0)
            throw new IllegalArgumentException("The answer must map to a choice");
        this.question = question;
        this.choices = choices;
        this.answer = answer;
    }

    public boolean isCorrect(int answer){
        if (answer<0 || answer>=choices.size())
            throw new IllegalArgumentException("The choice does not exist");
        return answer==this.answer;
    }

    public String getQuestion() {
        return question;
    }

    public String getChoice(int n) {
        if (n<0 || n>=choices.size())
            throw new IllegalArgumentException("The choice does not exist");
        return choices.get(n);
    }
}
