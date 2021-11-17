package core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class QuestionTest {

    private String question;
    private List<String> choices;
    private int answer;
    private Question realQ;

    @BeforeEach
    public void setUp() {
        question = "Hvor mange føtter har mennesker?";
        choices = List.of("1", "2", "3", "4");
        answer = 1;
        realQ = new Question(question, choices, answer);
    }

    @Test
    public void testConstructor() {
        setUp();
        answer = -1;
        assertConstructorThrows();
        answer = 4;
        assertConstructorThrows();
        choices = new ArrayList<>();
        choices.add("2");
        choices.add("3");
        choices.add("4");
        assertConstructorThrows();
        choices.add("1");
        answer = 2;
        Assertions.assertDoesNotThrow(() -> {
            Question q = new Question(question, choices, answer);
            Assertions.assertEquals(question, q.getQuestion());
            Assertions.assertEquals(choices, q.getChoices());
        });
        choices.add("5");
        assertConstructorThrows();

    }

    @Test
    public void testIsCorrect() {
        setUp();
        answer = -1;
        assertChoicesThrows();
        answer = 4;
        assertChoicesThrows();
        Assertions.assertFalse(realQ.isCorrect(2));
        Assertions.assertTrue(realQ.isCorrect(1));
    }


    @Test
    public void testGetChoice() {
        setUp();
        assertGetChoiceThrows(-1);
        assertGetChoiceThrows(5);
        Assertions.assertEquals("1", realQ.getChoice(0));
    }

    @Test
    public void testGetAnswer() {
        setUp();
        Assertions.assertEquals(answer, realQ.getAnswer());
        for (int i = 0; i < 4; i++) {
            if (realQ.isCorrect(i)) {
                Assertions.assertEquals(i, realQ.getAnswer());
            }
        }
    }

    @Test
    public void testToString() {
        String test = realQ.toString();
        Assertions.assertTrue(test.contains("question='" + realQ.getQuestion() + "\'"));
        Assertions.assertTrue(test.contains("choices=" + realQ.getChoices()));
        Assertions.assertTrue(test.contains("answer=" + realQ.getAnswer()));
    }

    private void assertConstructorThrows() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Question q = new Question(question, choices, answer);
        });
    }

    private void assertChoicesThrows() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            realQ.isCorrect(answer);
        });
    }

    private void assertGetChoiceThrows(int n) {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            realQ.getChoice(n);
        });
    }

}

