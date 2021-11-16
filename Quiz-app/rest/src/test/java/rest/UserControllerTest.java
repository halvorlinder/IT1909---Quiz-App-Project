package rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.UserRecord;
import io.SavePaths;
import io.UserPersistence;
import org.assertj.core.util.Files;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = QuizServerApplication.class)
@WebAppConfiguration
public class UserControllerTest {
    private MockMvc mvc;
    @Autowired
    WebApplicationContext webApplicationContext;

    private final ObjectMapper objectMapper = UserPersistence.createObjectMapper();

    @BeforeAll
    public static void start() {
        SavePaths.enableTestMode();
    }

    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void successfulRegisterTest() throws Exception {
        String uri = "/api/users/register";
        MvcResult mvcResult = request("POST", uri, objectMapper.writeValueAsString(new UserRecord("a", "password")));
        assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    public void failedRegisterTest() throws Exception {
        String uri = "/api/users/register";
        MvcResult mvcResult = request("POST", uri, objectMapper.writeValueAsString(new UserRecord("a", "password")));
        MvcResult mvcResult2 = request("POST", uri, objectMapper.writeValueAsString(new UserRecord("a", "password")));
        assertEquals(403, mvcResult2.getResponse().getStatus());
    }

    @Test
    public void successfulLoginTest() throws Exception {
        String uri = "/api/users/register";
        String uri2 = "/api/users/login";
        MvcResult mvcResult = request("POST", uri, objectMapper.writeValueAsString(new UserRecord("a", "password")));
        MvcResult mvcResult2 = request("POST", uri2, objectMapper.writeValueAsString(new UserRecord("a", "password")));
        assertEquals(200, mvcResult2.getResponse().getStatus());
    }

    @Test
    public void failedLoginTest() throws Exception {
        String uri = "/api/users/register";
        String uri2 = "/api/users/login";
        MvcResult mvcResult = request("POST", uri, objectMapper.writeValueAsString(new UserRecord("a", "password")));
        MvcResult mvcResult2 = request("POST", uri2, objectMapper.writeValueAsString(new UserRecord("a", "password2")));
        MvcResult mvcResult3 = request("POST", uri2, objectMapper.writeValueAsString(new UserRecord("b", "password")));
        MvcResult mvcResult4 = request("POST", uri2, objectMapper.writeValueAsString(new UserRecord("b", "password2")));
        assertEquals(403, mvcResult2.getResponse().getStatus());
        assertEquals(403, mvcResult3.getResponse().getStatus());
        assertEquals(403, mvcResult4.getResponse().getStatus());
    }

    @AfterEach
    public void deleteFile() throws IOException {
        Files.delete(new File(SavePaths.getBasePath() + "/users.json"));
    }

    private MvcResult request(String httpMethod, String uri, String body) throws Exception {
        return mvc.perform(MockMvcRequestBuilders.request(httpMethod, URI.create(uri))
                .content(body)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
    }

}