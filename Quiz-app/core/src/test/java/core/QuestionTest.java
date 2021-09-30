package core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QuestionTest {

    private String question;
    private List<String> choices;
    private int answer;
    private Question realQ;

    @BeforeEach
    void setUp() {
        question = "Hvor mange føtter har mennesker?";
        choices = List.of("1","2","3","4");
        answer = 1;
        realQ = new Question(question,  choices, answer);
    }

    @Test
    void constructorTest(){
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
        Assertions.assertDoesNotThrow(() ->{
            Question q = new Question(question,choices,answer);
            Assertions.assertEquals(question,q.getQuestion());
        });

    }

    @Test
    void isCorrect() {
        setUp();
        answer = -1;
        assertChoicesThrows();
        answer = 4;
        assertChoicesThrows();
        Assertions.assertFalse(realQ.isCorrect(2));
        Assertions.assertTrue(realQ.isCorrect(1));
    }


    @Test
    void getChoice() {
        setUp();
        assertGetChoiceThrows(-1);
        assertGetChoiceThrows(5);
        Assertions.assertEquals("1",realQ.getChoice(0));
    }

    private void assertConstructorThrows(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> {Question q = new Question(question,choices,answer);});
    }

    private void assertChoicesThrows(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> {realQ.isCorrect(answer);});
    }

    private void assertGetChoiceThrows(int n){
        Assertions.assertThrows(IllegalArgumentException.class, () -> {realQ.getChoice(n);});
    }

}

