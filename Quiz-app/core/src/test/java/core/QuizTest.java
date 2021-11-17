package core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class QuizTest {

    private final int num_questions = 10;
    private Quiz quiz;
    List<Question> questions;


    @BeforeEach
    public void setUp() {
        questions = new ArrayList<>();
        for (int i = 0; i < num_questions; i++) {
            questions.add(new Question("Question number " + i, List.of("1", "2", "3", "4"), i % 4));
        }
        quiz = new Quiz("quiz101", questions);
    }

    @Test
    public void testQuizLength() {
        Assertions.assertEquals(num_questions, quiz.getQuizLength());
        quiz.addQuestion(new Question("Question number 10", List.of("1", "2", "3", "4"), 10 % 4));
        Assertions.assertEquals(num_questions + 1, quiz.getQuizLength());
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
        Assertions.assertTrue(test.contains("questions=" + quiz.getQuestions()));
    }

    @Test
    public void testDeleteQuestion(){
        quiz.deleteQuestion(0);
        Assertions.assertEquals(9, quiz.getQuizLength());
    }

    @Test
    public void testSetQuestion(){
        quiz.setQuestion(0, new Question("?", List.of("x","y","z","w"), 3));
        Assertions.assertEquals("?", quiz.getQuestion(0).getQuestion());
        Assertions.assertEquals(10, quiz.getQuizLength());
    }
}
