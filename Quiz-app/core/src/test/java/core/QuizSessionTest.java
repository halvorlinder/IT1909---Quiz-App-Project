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


    @BeforeEach
    public void setUp() {
        questions = new ArrayList<>();
        for (int i = 0; i < num_questions; i++) {
            questions.add(new Question("Question number " + i, List.of("1", "2", "3", "4"), i % 4));
        }
        quizSession = new QuizSession(new Quiz("quiz101", questions));
    }

    @Test
    public void testGetCurrentQuestionNumber() {
        for (int i = 0; i < num_questions; i++) {
            Assertions.assertEquals(i, quizSession.getCurrentQuestionNumber());
            quizSession.submitAnswer(0);
        }
    }

    @Test
    public void testGetCurrentQuestion() {
        for (int i = 0; i < num_questions; i++) {
            Assertions.assertTrue(quizSession.isFinished());
            Assertions.assertTrue(quizSession.getCurrentQuestion().getQuestion().matches("Question number " + i));
            quizSession.submitAnswer(0);
        }
        Assertions.assertFalse(quizSession.isFinished());
    }

    @Test
    public void testCorrect1() {
        int num_correct = 0;
        for (int i = 0; i < num_questions; i++) {
            quizSession.submitAnswer(0);
            if (i % 4 == 0) {
                num_correct += 1;
            }
        }
        Assertions.assertEquals(num_correct, quizSession.getCorrect());
    }

    @Test
    public void testCorrect2() {
        int num_correct = 0;
        for (int i = 0; i < num_questions; i++) {
            quizSession.submitAnswer((i + 1) % 4);
        }
        Assertions.assertEquals(num_correct, quizSession.getCorrect());
    }
}
