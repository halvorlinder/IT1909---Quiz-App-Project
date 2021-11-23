package core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class QuestionTest {

    private final String correctQuestionText = "Hvor mange føtter har mennesker?";
    private final List<String> correctChoices = List.of("1", "2", "3", "4");
    private final int correctAnswer = 1;
    private final Question correctQuestion = new Question(correctQuestionText, correctChoices, correctAnswer);

    /**
     * Test the Question constructor with both valid and invalid arguments
     */
    @Test
    public void testConstructor() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Question(correctQuestionText, correctChoices, -1));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Question(correctQuestionText, correctChoices, 4));
        List<String> wrongChoices = new ArrayList<>();
        wrongChoices.add("2");
        wrongChoices.add("3");
        wrongChoices.add("4");
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Question(correctQuestionText, wrongChoices, correctAnswer));
        wrongChoices.add("1");
        Assertions.assertDoesNotThrow(() -> {
            Question q = new Question(correctQuestionText, wrongChoices, correctAnswer);
            Assertions.assertEquals(correctQuestionText, q.getQuestion());
            Assertions.assertEquals(wrongChoices, q.getChoices());
        });
        wrongChoices.add("5");
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Question(correctQuestionText, wrongChoices, correctAnswer));
    }

    /**
     * Test whether an answer is correct
     */
    @Test
    public void testIsCorrectAnswer() {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                correctQuestion.isCorrect(-1));
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                correctQuestion.isCorrect(5));
        Assertions.assertFalse(correctQuestion.isCorrect(2));
        Assertions.assertTrue(correctQuestion.isCorrect(correctAnswer));
    }

    /**
     * Test getting all answers
     */
    @Test
    public void testGetChoice() {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                correctQuestion.getChoice(-1));
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                correctQuestion.getChoice(4));
        Assertions.assertEquals("1", correctQuestion.getChoice(0));
    }

    /**
     * Test getting the correct answers
     */
    @Test
    public void testGetCorrectAnswer() {
        Assertions.assertEquals(correctAnswer, correctQuestion.getAnswer());
        for (int i = 0; i < 4; i++) {
            if (correctQuestion.isCorrect(i)) {
                Assertions.assertEquals(i, correctQuestion.getAnswer());
            }
        }
    }

    /**
     * Test toString method
     */
    @Test
    public void testToString() {
        String test = correctQuestion.toString();
        Assertions.assertTrue(test.contains("question='" + correctQuestion.getQuestion() + "'"));
        Assertions.assertTrue(test.contains("choices=" + correctQuestion.getChoices()));
        Assertions.assertTrue(test.contains("answer=" + correctQuestion.getAnswer()));
    }
}
