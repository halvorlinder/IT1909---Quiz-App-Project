package core;

import java.util.ArrayList;

/**
 * This class represents a Quiz session in the application
 */
public class QuizSession {

    private final Quiz quiz;
    private int correct;
    private int currentQuestionNumber;


    /**
     * @param quiz the quiz to be played
     */
    public QuizSession(Quiz quiz) {
        this.quiz = new Quiz(quiz.getName(), new ArrayList<>(quiz.getQuestions()));
    }

    /**
     * @return the current question of the quiz, if no such question exists, null is returned
     */
    public Question getCurrentQuestion() {
        if (currentQuestionNumber >= quiz.getQuizLength())
            throw new ArrayIndexOutOfBoundsException("The quiz does not contain more questions");
        return quiz.getQuestion(currentQuestionNumber);
    }

    /**
     * @return true if there are more questions left in the quiz
     */
    public boolean isFinished() {
        return currentQuestionNumber >= quiz.getQuizLength();
    }

    /**
     * submits a question by checking its correctness, updating the correct counter as well as the
     * question counter
     *
     * @param answer the integer corresponding to the index of the answer
     */
    public void submitAnswer(int answer) {
        if (isFinished())
            throw new IllegalStateException("All questions have already been answered");
        boolean isCorrect = getCurrentQuestion().isCorrect(answer);
        if (isCorrect)
            correct++;
        currentQuestionNumber++;
    }

    /**
     * @return the length of the quiz
     */
    public int getQuizLength() {
        return quiz.getQuizLength();
    }

    /**
     * @return the name of the quiz
     */
    public String getQuizName() {
        return quiz.getName();
    }

    /**
     * @return the number of correct submitted answers
     */
    public int getNumberOfCorrect() {
        return correct;
    }

    /**
     * @return the index of the current question
     */
    public int getCurrentQuestionNumber() {
        return currentQuestionNumber;
    }

    /**
     * @return the number of correctly submitted answers
     */
    public int getCorrect() {
        return correct;
    }
}
