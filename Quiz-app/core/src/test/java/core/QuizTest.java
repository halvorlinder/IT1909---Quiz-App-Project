package core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class QuizTest {

    private int num_questions = 10;
    private Quiz quiz;
    List<Question> questions = new ArrayList<>();


    @BeforeEach
    public void setUp() {
        for (int i = 0; i < num_questions; i++) {
            String q_text = "Question number " + i;
            List<String> choices = List.of("1", "2", "3", "4");
            int answer = i % 4;
            Question question = new Question(q_text, choices, answer);
            questions.add(question);
        }
        quiz = new Quiz("quiz101", questions);
    }

    @Test
    public void testGetCurrentQuestionNumber() {
        for (int i = 0; i < num_questions; i++) {
            Assertions.assertEquals(i, quiz.getCurrentQuestionNumber());
            quiz.submitAnswer(0);
        }
    }

    @Test
    public void testGetCurrentQuestion() {
        for (int i = 0; i < num_questions; i++) {
            Assertions.assertTrue(quiz.getCurrentQuestion().getQuestion().matches("Question number " + i));
            quiz.submitAnswer(0);
        }
        Assertions.assertNull(quiz.getCurrentQuestion());
    }

    @Test
    public void testQuizLength() {
        Assertions.assertEquals(num_questions, quiz.getQuizLength());
        quiz.addQuestion(new Question("Question number 10", List.of("1", "2", "3", "4"), 10 % 4));
        Assertions.assertEquals(num_questions + 1, quiz.getQuizLength());
    }

    @Test
    public void testCorrect1() {
        int num_correct = 0;
        for (int i = 0; i < num_questions; i++) {
            quiz.submitAnswer(0);
            if (i % 4 == 0) {
                num_correct += 1;
            }
        }
        Assertions.assertEquals(num_correct, quiz.getCorrect());
    }

    @Test
    public void testCorrect2() {
        int num_correct = 0;
        for (int i = 0; i < num_questions; i++) {
            quiz.submitAnswer((i + 1) % 4);
        }
        Assertions.assertEquals(num_correct, quiz.getCorrect());
    }

    @Test
    public void testGetQuestions() {
        List<Question> actual_questions = quiz.getQuestions();
        for (int i = 0; i < num_questions; i++) {
            Assertions.assertEquals(questions.get(i), actual_questions.get(i));
        }
    }

    @Test
    public void testToString() {
        String test = quiz.toString();
        Assertions.assertTrue(test.contains("correct=" + quiz.getCorrect()));
        Assertions.assertTrue(test.contains("currentQuestionNumber=" + quiz.getCurrentQuestionNumber()));
        Assertions.assertTrue(test.contains("questions=" + quiz.getQuestions()));
    }
}
