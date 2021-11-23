package core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class QuizTest {

    private final int questionCount = 10;
    private Quiz quiz;
    List<Question> questions;

    /**
     * Set up a quiz
     */
    @BeforeEach
    public void setUp() {
        questions = new ArrayList<>();
        for (int i = 0; i < questionCount; i++) {
            questions.add(new Question("Question number " + i, List.of("1", "2", "3", "4"), i % 4));
        }
        quiz = new Quiz("quiz101", questions, "hallvard");
    }

    /**
     * Test the length of the quiz
     */
    @Test
    public void testQuizLength() {
        Assertions.assertEquals(questionCount, quiz.getQuizLength());
        quiz.addQuestion(new Question("Question number 10", List.of("1", "2", "3", "4"), questionCount % 4));
        Assertions.assertEquals(questionCount + 1, quiz.getQuizLength());
    }

    /**
     * Test getting questions from the quiz
     */
    @Test
    public void testGetQuestions() {
        List<Question> actual_questions = quiz.getQuestions();
        for (int i = 0; i < questionCount; i++) {
            Assertions.assertEquals(questions.get(i), actual_questions.get(i));
        }
    }

    /**
     * Test the toString method
     */
    @Test
    public void testToString() {
        String test = quiz.toString();
        Assertions.assertTrue(test.contains("questions=" + quiz.getQuestions()));
    }

    /**
     * Test deleting questions from the quiz
     */
    @Test
    public void testDeleteQuestion() {
        quiz.deleteQuestion(0);
        Assertions.assertEquals(questionCount-1, quiz.getQuizLength());
    }

    /**
     * Test setting a question in the quiz
     */
    @Test
    public void testSetQuestion() {
        quiz.setQuestion(0, new Question("?", List.of("x", "y", "z", "w"), 3));
        Assertions.assertEquals("?", quiz.getQuestion(0).getQuestion());
        Assertions.assertEquals(questionCount, quiz.getQuizLength());
    }

    /**
     * Test creating a quiz without a name
     */
    @Test
    public void emptyNameQuiz() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Quiz("", List.of(), "per"));
    }

    /**
     * Test getting questions that don't exist
     */
    @Test
    public void getNonExistingQuestion() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> quiz.getQuestion(-1));
        Assertions.assertThrows(IllegalArgumentException.class, () -> quiz.getQuestion(quiz.getQuizLength()));
    }

    /**
     * Test deleting questions that don't exist
     */
    @Test
    public void deleteNonExistingQuestion() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> quiz.deleteQuestion(-1));
        Assertions.assertThrows(IllegalArgumentException.class, () -> quiz.deleteQuestion(quiz.getQuizLength()));
    }

    /**
     * Test setting questions that don't exist
     */
    @Test
    public void setNonExistingQuestion() {
        Question q = new Question("?", List.of("1", "2", "3", "4"), 0);
        Assertions.assertThrows(IllegalArgumentException.class, () -> quiz.setQuestion(-1, q));
        Assertions.assertThrows(IllegalArgumentException.class, () -> quiz.setQuestion(quiz.getQuizLength(), q));
    }

    /**
     * Test setting a question
     */
    @Test
    public void setQuestion2() {
        Question q = new Question("?", List.of("a", "b", "c", "d"), 3);
        quiz.setQuestion(0, q);
        Assertions.assertEquals(quiz.getQuestion(0).getQuestion(), q.getQuestion());
        Assertions.assertEquals(quiz.getQuestion(0).getAnswer(), q.getAnswer());
        for (int i = 0; i < 4; i++) {
            Assertions.assertEquals(quiz.getQuestion(0).getChoice(i), q.getChoice(i));
        }
    }

    /**
     * Test creating a quiz with no creator
     */
    @Test
    public void testEmptyCreator() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Quiz("test", questions, ""));
    }
}
