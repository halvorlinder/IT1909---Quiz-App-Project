package io;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.Question;
import core.Quiz;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class QuizAppModuleTest {
    private static ObjectMapper mapper;

    @BeforeAll
    public static void setUp() {
        mapper = QuizPersistence.createObjectMapper();
    }

    final static String quizWithTwoQuestions =
            """
                    {
                       "name" : "quiz101",
                       "questions" : [ {
                         "question" : "What?",
                         "answer" : 2,
                         "choices" : [ "a", "b", "c", "d" ]
                       }, {
                         "question" : "Where",
                         "answer" : 3,
                         "choices" : [ "1", "2", "3", "4" ]
                       }]
                     }
                                 
                     """;

    static Quiz createQuizWithTwoQuestions() {
        return new Quiz("quiz101", List.of(
                new Question("What?", List.of("a", "b", "c", "d"), 2),
                new Question("Where", List.of("1", "2", "3", "4"), 3)));
    }

    @Test
    public void testSerializers() {
        Quiz quiz = createQuizWithTwoQuestions();
        try {
            assertEquals(quizWithTwoQuestions.replaceAll("\\s+", ""), mapper.writeValueAsString(quiz));
        } catch (JsonProcessingException e) {
            fail(e.getMessage());
        }
    }

    static void checkQuestion(Question question1, Question question2) {
        assertEquals(question1.getQuestion(), question2.getQuestion());
        assertEquals(question1.getChoices().size(), question1.getChoices().size());
        for (int i = 0; i < question1.getChoices().size(); i++) {
            assertEquals(question1.getChoice(i), question2.getChoice(i));
        }
        assertEquals(question1.getAnswer(), question2.getAnswer());
    }

    static void checkQuiz(Quiz quiz1, Quiz quiz2) {
        assertEquals(quiz1.getName(), quiz2.getName());
        assertEquals(quiz1.getQuizLength(), quiz2.getQuizLength());
        for (int i = 0; i < quiz1.getQuizLength(); i++) {
            checkQuestion(quiz1.getQuestions().get(i), quiz2.getQuestions().get(i));
        }
    }

    @Test
    public void testDeserializers() {
        try {
            Quiz quiz = mapper.readValue(quizWithTwoQuestions, Quiz.class);
            checkQuiz(quiz, createQuizWithTwoQuestions());
        } catch (JsonProcessingException e) {
            fail(e.getMessage());
        }
    }

}
