package rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.*;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = QuizServerApplication.class)
@WebAppConfiguration
public class QuizControllerTest {
    private MockMvc mvc;
    @Autowired
    private WebApplicationContext webApplicationContext;

    private final ObjectMapper objectMapper = QuizPersistence.createObjectMapper();
    private final Quiz defaultQuiz = new Quiz("testQuiz", List.of(new Question("a", List.of("1", "2", "3", "4"), 0)), "username");
    private final Score score1 = new Score("test1", 1);
    private final Score score2 = new Score("test2", 0);
    private final Score score3 = new Score("test3", 1);
    private final Leaderboard defaultLeaderboard = new Leaderboard(defaultQuiz.getName(),
            List.of(score1, score2), defaultQuiz.getQuizLength());
    private final UserRecord defaultUser = new UserRecord("username", "password");
    private String token;

    /**
     * Enable test mode (save paths to different folder)
     */
    @BeforeAll
    public static void start() {
        SavePaths.enableTestMode();
    }

    /**
     * Setup a mock server
     *
     * @throws Exception
     */
    @BeforeEach
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        token = request("POST", "/api/users/register",
                objectMapper.writeValueAsString(defaultUser), "")
                .getResponse().getContentAsString();
        System.out.println(token.equals("") ? "null" : token);
        System.out.println(49539);
        request("POST", "/api/quizzes", objectMapper.writeValueAsString(defaultQuiz), "");
    }

    /**
     * Test getting all quiz names
     *
     * @throws Exception
     */
    @Test
    public void testGetQuizNames() throws Exception {
        String uri = "/api/quizzes";
        MvcResult mvcResult = request("GET", uri, "", token);
        assertEquals(200, mvcResult.getResponse().getStatus());
        List names = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), List.class);
        assertEquals(1, names.size());
        assertEquals("testQuiz", names.get(0));
    }

    /**
     * Test getting a quiz with a given name
     *
     * @throws Exception
     */
    @Test
    public void testGetQuiz() throws Exception {
        System.out.println(SavePaths.getBasePath());
        String uri = "/api/quizzes/testQuiz";
        MvcResult mvcResult = request("GET", uri, "", token);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(objectMapper.writeValueAsString(defaultQuiz), content);

        assertEquals(404, request("GET", uri + 1, "", token).getResponse().getStatus());
    }

    /**
     * Test posting quizzes to the backend. Only unique quiz names should be posted
     *
     * @throws Exception
     */
    @Test
    public void testPostQuiz() throws Exception {
        String uri = "/api/quizzes";
        String uri2 = "/api/leaderboards/exampleQuiz";

        assertEquals(403, request("POST", uri, objectMapper.writeValueAsString(defaultQuiz), token)
                .getResponse().getStatus());

        assertEquals(404, request("GET", uri2, "", token).getResponse().getStatus());

        String exampleQuiz = objectMapper.writeValueAsString(getExampleQuiz());
        MvcResult mvcResult = request("POST", uri, exampleQuiz, token);
        assertEquals(200, mvcResult.getResponse().getStatus());
        assertEquals(200, request("GET", uri2, "", token).getResponse().getStatus());
        assertEquals(exampleQuiz, mvcResult.getResponse().getContentAsString());
    }

    /**
     * Test adding questions to a quiz
     *
     * @throws Exception
     */
    @Test
    public void testAddQuestion() throws Exception {
        String uri = "/api/quizzes/testQuiz";
        String question = objectMapper.writeValueAsString(new Question("z", List.of("a", "b", "c", "d"), 3));
        MvcResult mvcResult = request("POST", uri, question, token);
        assertEquals(200, mvcResult.getResponse().getStatus());
        Quiz quiz = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Quiz.class);
        assertEquals(question,
                objectMapper.writeValueAsString(quiz.getQuestions().get(quiz.getQuizLength() - 1)));

        assertEquals(404, request("POST", uri + 1, question, token)
                .getResponse().getStatus());
        assertEquals(403, request("POST", uri, question, "")
                .getResponse().getStatus());
    }

    /**
     * Test editing an existing question
     *
     * @throws Exception
     */
    @Test
    public void testEditQuestion() throws Exception {
        String uri = "/api/quizzes/testQuiz/0";
        String question = objectMapper.writeValueAsString(new Question("z", List.of("a", "b", "c", "d"), 3));
        MvcResult mvcResult = request("PUT", uri, question, token);
        assertEquals(200, mvcResult.getResponse().getStatus());
        Quiz quiz = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Quiz.class);
        String responseQuestion = objectMapper.writeValueAsString(quiz.getQuestions().get(quiz.getQuizLength() - 1));
        assertEquals(question, responseQuestion);

        assertEquals(404, request("PUT", "/api/quizzes/testQuiz/4", question, token)
                .getResponse().getStatus());
        assertEquals(403, request("PUT", uri, question, "")
                .getResponse().getStatus());
    }

    /**
     * Test deleting a quiz by name
     * Only existing quizzes can be deleted
     *
     * @throws Exception
     */
    @Test
    public void testDeleteQuiz() throws Exception {
        String uri = "/api/quizzes/testQuiz";
        String uri2 = "/api/leaderboards/testQuiz";
        assertEquals(403, request("DELETE", uri, "", "").getResponse().getStatus());
        assertEquals(200, request("DELETE", uri, "", token).getResponse().getStatus());
        assertEquals(404, request("DELETE", uri, "", token).getResponse().getStatus());
        assertEquals(404, request("GET", uri2, "", token).getResponse().getStatus());
    }

    /**
     * Test deleting a question from a quiz
     *
     * @throws Exception
     */
    @Test
    public void testDeleteQuestion() throws Exception {
        String uri = "/api/quizzes/testQuiz/0";
        assertEquals(403, request("DELETE", uri, "", "")
                .getResponse().getStatus());
        MvcResult mvcResult = request("DELETE", uri, "", token);
        assertEquals(200, mvcResult.getResponse().getStatus());

        Quiz quiz = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Quiz.class);
        assertEquals(0, quiz.getQuizLength());

        assertEquals(404, request("DELETE", uri, "", token)
                .getResponse().getStatus());
    }

    /**
     * Test posting a new score to a leaderboard
     *
     * @throws Exception
     */
    @Test
    public void testPostScore() throws Exception {
        String uri = "/api/leaderboards/testQuiz";
        assertEquals(200, request("POST", uri, objectMapper.writeValueAsString(score3), token)
                .getResponse().getStatus());
        assertEquals(404, request("POST", uri + 1, objectMapper.writeValueAsString(score3), token)
                .getResponse().getStatus());
    }

    /**
     * Teardown: delete all test files created
     *
     * @throws IOException
     */
    @AfterEach
    public void deleteFiles() throws IOException {
        FileUtils.cleanDirectory(new File(SavePaths.getBasePath() + "/Quizzes"));
        FileUtils.cleanDirectory(new File(SavePaths.getBasePath() + "/leaderboards"));
        Files.delete(Path.of(SavePaths.getBasePath() + "users.json"));

    }

    /**
     * @return a default quiz
     */
    private Quiz getExampleQuiz() {
        return new Quiz("exampleQuiz",
                List.of(new Question("b", List.of("11", "21", "31", "41"), 1)), "hallvard");
    }

    /**
     * @return a default leaderboard
     */
    private Leaderboard getExampleLeaderboard() {
        Quiz quiz = getExampleQuiz();
        return new Leaderboard(quiz.getName(), quiz.getQuizLength());
    }

    /**
     * @param httpMethod the method (POST/GET/DELETE)
     * @param uri        the uri destination
     * @param body       the body of the request
     * @param header     the auth token
     * @return the response of performing the request
     * @throws Exception
     */
    private MvcResult request(String httpMethod, String uri, String body, String header) throws Exception {
        return mvc.perform(MockMvcRequestBuilders.request(httpMethod, URI.create(uri))
                        .header("Authorization", header)
                        .content(body)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
    }

}