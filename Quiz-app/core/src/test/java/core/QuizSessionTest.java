package core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class QuizSessionTest {
    private int num_questions = 10;
    private QuizSession quizSession;
    List<Question> questions;

    /**
     * Set up a quiz session
     */
    @BeforeEach
    public void setUp() {
        questions = new ArrayList<>();
        for (int i = 0; i < num_questions; i++) {
            questions.add(new Question("Question number " + i, List.of("1", "2", "3", "4"), i % 4));
        }
        quizSession = new QuizSession(new Quiz("quiz101", questions, "hallvard"));
    }

    /**
     * Test getting the index of the current question
     */
    @Test
    public void testGetCurrentQuestionNumber() {
        for (int i = 0; i < num_questions; i++) {
            Assertions.assertEquals(i, quizSession.getCurrentQuestionNumber());
            quizSession.submitAnswer(0);
        }
    }

    /**
     * Test getting the current question
     */
    @Test
    public void testGetCurrentQuestion() {
        for (int i = 0; i < num_questions; i++) {
            Assertions.assertFalse(quizSession.isFinished());
            Assertions.assertTrue(quizSession.getCurrentQuestion().getQuestion().matches("Question number " + i));
            quizSession.submitAnswer(0);
        }
        Assertions.assertTrue(quizSession.isFinished());
    }

    /**
     *  Test asserting how many correct answers to a quiz
     */
    @Test
    public void testCorrect1() {
        int numOfCorrect = 0;
        for (int i = 0; i < num_questions; i++) {
            quizSession.submitAnswer(0);
            if (i % 4 == 0) {
                numOfCorrect += 1;
            }
        }
        Assertions.assertEquals(numOfCorrect, quizSession.getCorrect());
    }

    /**
     *  Test asserting how many correct answers to a quiz
     */
    @Test
    public void testCorrect2() {
        int numOfCorrect = 0;
        for (int i = 0; i < num_questions; i++) {
            quizSession.submitAnswer((i + 1) % 4);
        }
        Assertions.assertEquals(numOfCorrect, quizSession.getCorrect());
    }
}
