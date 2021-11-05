package rest;


import core.Quiz;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import static org.mockito.Mockito.*;
class QuizControllerTest {

    private String testQuiz = """
            {
              "name" : "postTestQuiz",
              "questions" : [%s]
              }""";
    private String baseQuestion = """
             {
                    "question" : "How old i Halvor?",
                    "answer" : 1,
                    "choices" : [ "12", "20", "122", "36" ]
                  }
            """;
    private HttpServletResponse httpServletResponse;
    private QuizController quizController;

    @BeforeEach
    void setUp() {
        httpServletResponse = mock(HttpServletResponse.class);
        quizController.postQuiz(testQuiz.formatted(baseQuestion), httpServletResponse);
    }

    @Test
    void getQuiz() {
        setUp();
        quizController.getQuiz("wrongQuizName",httpServletResponse);
        System.out.println(httpServletResponse.getStatus());
        Assertions.assertEquals(testQuiz.formatted(baseQuestion),quizController.getQuiz("postTestQuiz",httpServletResponse));
    }

    @Test
    void getQuizzes() {
    }

    @Test
    void addQuestion() {
    }

    @Test
    void editQuestion() {
    }

    @Test
    void deleteQuiz() {
    }

    @Test
    void deleteQuestion() {
    }
}