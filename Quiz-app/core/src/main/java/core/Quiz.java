package core;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a Quiz session in the application
 */
public class Quiz {

    private final String name;
    private int correct;
    private int currentQuestionNumber;
    private final List<Question> questions;

    /**
     * @param name
     * @param questions a list of question objects
     */
    public Quiz(String name, List<Question> questions) {
        this.name = name;
        this.questions = new ArrayList<>(questions);
    }

    /**
     *
     */
    public Quiz() {
        this("", List.of(new Question()));
    }

    /**
     * @return the current question of the quiz, if no such question exists, null is returned
     */
    public Question getCurrentQuestion() {
        if (currentQuestionNumber >= questions.size())
            return null;
        return questions.get(currentQuestionNumber);
    }

    /**
     * @return a copy of the questions in the quiz
     */
    public List<Question> getQuestions() {
        return new ArrayList<>(questions);
    }

    /**
     * submits a question by checking its correctness, updating the correct counter as well as the
     * question counter
     *
     * @param answer the integer corresponding to the index of the answer
     * @return true if the answer is correct, else otherwise
     */
    public boolean submitAnswer(int answer) {
        boolean isCorrect = getCurrentQuestion().isCorrect(answer);
        if (isCorrect)
            correct++;
        currentQuestionNumber++;
        return isCorrect;
    }

    /**
     * adds a question object to the quiz
     *
     * @param question the question to be added
     */
    public void addQuestion(Question question) {
        questions.add(question);
    }

    /**
     * delete a question from the quiz
     *
     * @param questionId
     */
    public void deleteQuestion(int questionId) {
        questions.remove(questionId);
    }

    public void setQuestion(int questionId, Question question) {
        questions.set(questionId, question);
    }

    /**
     * @return the index of the current question
     */
    public int getCurrentQuestionNumber() {
        return currentQuestionNumber;
    }

    /**
     * @return the number of correct submitted answers
     */
    public int getCorrect() {
        return correct;
    }

    /**
     * @return the length of the quiz
     */
    public int getQuizLength() {
        return questions.size();
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "correct=" + correct +
                ", currentQuestionNumber=" + currentQuestionNumber +
                ", questions=" + questions +
                '}';
    }

    public String getName() {
        return name;
    }
}
