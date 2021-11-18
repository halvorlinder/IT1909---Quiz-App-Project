package core;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a Quiz session in the application
 */
public class Quiz {

    private String name;
    private final List<Question> questions;
    private final String creator;

    /**
     * @param name      the quiz name
     * @param questions a list of question objects
     * @param creator   the creator of the quiz
     */
    public Quiz(String name, List<Question> questions, String creator) {
        this.name = name;
        this.questions = new ArrayList<>(questions);
        this.creator = creator;
    }

    /**
     * @param n the index
     * @return the question at a given index
     */
    public Question getQuestion(int n) {
        if (n >= getQuizLength())
            throw new ArrayIndexOutOfBoundsException();
        return questions.get(n);
    }

    /**
     * @return a copy of the questions in the quiz
     */
    public List<Question> getQuestions() {
        return new ArrayList<>(questions);
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

    /**
     * sets a new question at a given index
     *
     * @param questionId the index of the question
     * @param question   the new question
     */
    public void setQuestion(int questionId, Question question) {
        questions.set(questionId, question);
    }

    /**
     * @return the length of the quiz
     */
    public int getQuizLength() {
        return questions.size();
    }

    /**
     * @return the creator of the quiz
     */
    public String getCreator() {
        return creator;
    }

    /**
     * @return string representation
     */

    @Override
    public String toString() {
        return "Quiz{" +
                ", questions=" + questions +
                '}';
    }

    /**
     * @return the name of the quiz
     */
    public String getName() {
        return name;
    }

    /**
     * strips and replaces spaces with $ in the quiz name in
     * order for it to be sent over http
     */
    public void legalizeName(){
        name = name.strip().replaceAll(" ", "$");
    }
}
