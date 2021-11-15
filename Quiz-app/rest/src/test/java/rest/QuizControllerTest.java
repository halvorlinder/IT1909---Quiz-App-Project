package rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.Question;
import core.Quiz;
import io.QuizPersistence;
import io.SavePaths;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = QuizServerApplication.class)
@WebAppConfiguration
public class QuizControllerTest {
    private MockMvc mvc;
    @Autowired
    WebApplicationContext webApplicationContext;

    private final ObjectMapper objectMapper = QuizPersistence.createObjectMapper();
    private final Quiz defaultQuiz = new Quiz("testQuiz", List.of(new Question("a", List.of("1", "2", "3", "4"), 0)));


    @BeforeAll
    public static void start() {
        SavePaths.enableTestMode();

    }

    @BeforeEach
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        request("POST", "/api/quizzes", objectMapper.writeValueAsString(defaultQuiz));
    }

    @Test
    public void getQuizNamesTest() throws Exception {
        String uri = "/api/quizzes";
        MvcResult mvcResult = request("GET", uri, "");
        assertEquals(200, mvcResult.getResponse().getStatus());
        List names = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), List.class);
        assertEquals(1, names.size());
        assertEquals("testQuiz", names.get(0));
    }

    @Test
    public void getQuizTest() throws Exception {
        String uri = "/api/quizzes/testQuiz";
        MvcResult mvcResult = request("GET", uri, "");
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(objectMapper.writeValueAsString(defaultQuiz), content);

        assertEquals(404, request("GET", uri + 1, "").getResponse().getStatus());
    }

    @Test
    public void postQuizTest() throws Exception {
        String uri = "/api/quizzes";

        assertEquals(403, request("POST", uri, objectMapper.writeValueAsString(defaultQuiz))
                .getResponse().getStatus());

        String exampleQuiz = objectMapper.writeValueAsString(getExampleQuiz());
        MvcResult mvcResult = request("POST", uri, exampleQuiz);
        assertEquals(200, mvcResult.getResponse().getStatus());
        assertEquals(exampleQuiz, mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void addQuestionTest() throws Exception {
        String uri = "/api/quizzes/testQuiz";
        String question = objectMapper.writeValueAsString(new Question("z", List.of("a", "b", "c", "d"), 3));
        MvcResult mvcResult = request("POST", uri, question);
        assertEquals(200, mvcResult.getResponse().getStatus());
        Quiz quiz = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Quiz.class);
        assertEquals(question,
                objectMapper.writeValueAsString(quiz.getQuestions().get(quiz.getQuizLength() - 1)));

        assertEquals(404, request("POST", uri + 1, question)
                .getResponse().getStatus());
    }

    @Test
    public void editQuestionTest() throws Exception {
        String uri = "/api/quizzes/testQuiz/0";
        String question = objectMapper.writeValueAsString(new Question("z", List.of("a", "b", "c", "d"), 3));
        MvcResult mvcResult = request("PUT", uri, question);
        assertEquals(200, mvcResult.getResponse().getStatus());
        Quiz quiz = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Quiz.class);
        String responseQuestion = objectMapper.writeValueAsString(quiz.getQuestions().get(quiz.getQuizLength() - 1));
        assertEquals(question, responseQuestion);

        assertEquals(404, request("PUT", "/api/quizzes/testQuiz/4", question)
                .getResponse().getStatus());
    }

    @Test
    public void deleteQuizTest() throws Exception {
        String uri = "/api/quizzes/testQuiz";
        assertEquals(200, request("DELETE", uri, "").getResponse().getStatus());
        assertEquals(404, request("DELETE", uri, "").getResponse().getStatus());
    }

    @Test
    public void deleteQuestionTest() throws Exception {
        String uri = "/api/quizzes/testQuiz/0";
        MvcResult mvcResult = request("DELETE", uri, "");
        assertEquals(200, mvcResult.getResponse().getStatus());

        Quiz quiz = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Quiz.class);
        assertEquals(0, quiz.getQuizLength());

        assertEquals(404, request("DELETE", uri, "")
                .getResponse().getStatus());
    }

    @AfterEach
    public void deleteFiles() throws IOException {
        FileUtils.cleanDirectory(new File(SavePaths.getBasePath() + "/Quizzes"));
    }

    private Quiz getExampleQuiz() {
        return new Quiz("exampleQuiz", List.of(new Question("b", List.of("11", "21", "31", "41"), 1)));
    }

    private MvcResult request(String httpMethod, String uri, String body) throws Exception {
        return mvc.perform(MockMvcRequestBuilders.request(httpMethod, URI.create(uri))
                        .content(body)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
    }

}