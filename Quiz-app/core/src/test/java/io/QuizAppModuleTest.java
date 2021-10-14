package io;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.Quiz;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.TestHelpers.checkQuiz;
import static io.TestHelpers.createQuizWithTwoQuestions;
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

    @Test
    public void testSerializers() {
        Quiz quiz = createQuizWithTwoQuestions();
        try {
            assertEquals(quizWithTwoQuestions.replaceAll("\\s+", ""), mapper.writeValueAsString(quiz));
        } catch (JsonProcessingException e) {
            fail(e.getMessage());
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
