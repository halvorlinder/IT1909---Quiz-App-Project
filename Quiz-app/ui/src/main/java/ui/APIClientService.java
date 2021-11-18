package ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.Leaderboard;
import core.Question;
import core.Quiz;
import core.UserRecord;
import core.Score;
import io.QuizPersistence;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class APIClientService {

    private static final String API_URL = "http://localhost:8080/api";
    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper;

    /**
     *
     */
    public APIClientService() {
        objectMapper = QuizPersistence.createObjectMapper();
    }

    /**
     * fetches a quiz from the server
     * @param quizName the name of the quiz to be fetched
     * @return the quiz
     * @throws IOException
     * @throws InterruptedException
     */
    public Quiz getQuiz(String quizName) throws IOException, InterruptedException {
        HttpResponse<String> response = sendRequest("GET", "/quizzes/" + quizName, "", "");
        return objectMapper.readValue(response.body(), Quiz.class);
    }

    /**
     * fetches all quiz names from the server
     * @return a list of quiz names
     * @throws IOException
     * @throws InterruptedException
     */
    public List<String> getListOfQuizNames() throws IOException, InterruptedException {
        HttpResponse<String> response = sendRequest("GET", "/quizzes", "", "");
        return objectMapper.readValue(response.body(), List.class);
    }

    /**
     * posts a quiz to the server
     * @param quiz the quiz to be posted
     * @throws IOException
     * @throws InterruptedException
     */
    public void postQuiz(Quiz quiz) throws IOException, InterruptedException {
        sendRequest("POST", "/quizzes", objectMapper.writeValueAsString(quiz), "");
    }

    /**
     * updates a given question on the server
     *
     * @param quizName    the name of the quiz
     * @param questionID  the id of the question
     * @param newQuestion the new question object
     * @throws IOException
     * @throws InterruptedException
     */
    public void putQuestion(String quizName, int questionID, Question newQuestion, String accessToken)
            throws IOException, InterruptedException {
        sendRequest("PUT", "/quizzes/" + quizName + "/" + questionID,
                objectMapper.writeValueAsString(newQuestion), accessToken);
    }

    /**
     * deletes a quiz from the server given its name
     * @param quizName the name of the quiz
     * @throws IOException
     * @throws InterruptedException
     */
    public void deleteQuiz(String quizName, String accessToken) throws IOException, InterruptedException {
        sendRequest("DELETE", "/quizzes/" + quizName, "", accessToken);
    }

    /**
     * deletes a given question from a given quiz on the server
     *
     * @param quizName   the name of the quiz
     * @param questionID the id of the question
     * @throws IOException
     * @throws InterruptedException
     */
    public void deleteQuestion(String quizName, int questionID, String accessToken) throws IOException, InterruptedException {
        sendRequest("DELETE", "/quizzes/" + quizName + "/" + questionID, "", accessToken);
    }

    /**
     * adds a question to a given quiz
     *
     * @param quizName    the name of the quiz
     * @param newQuestion the name of the question
     * @throws IOException
     * @throws InterruptedException
     */
    public void addQuestion(String quizName, Question newQuestion, String accessToken) throws IOException, InterruptedException {
        sendRequest("POST", "/quizzes/" + quizName, objectMapper.writeValueAsString(newQuestion), accessToken);
    }

    /**
     * attempts to log the user in
     * @param userRecord the user data
     * @throws IOException
     * @throws InterruptedException
     */
    public String loginUser(UserRecord userRecord) throws IOException, InterruptedException {
        return sendRequest("POST", "/users/login", objectMapper.writeValueAsString(userRecord), "").body();
    }

    /**
     * attempts to register the user
     * @param userRecord the information about the user
     * @throws IOException
     * @throws InterruptedException
     */
    public String registerUser(UserRecord userRecord) throws IOException, InterruptedException {
        return sendRequest("POST", "/users/register", objectMapper.writeValueAsString(userRecord), "").body();
    }

    /**
     * fetches a leaderboard for a quiz from the server
     *
     * @param quizName the name of the quiz mapping to the leaderboard to be fetched
     * @return the leaderboard
     * @throws IOException
     * @throws InterruptedException
     */
    public Leaderboard getLeaderboard(String quizName) throws IOException, InterruptedException {
        HttpResponse<String> response = sendRequest("GET", "/leaderboards/" + quizName, "", "");
        return objectMapper.readValue(response.body(), Leaderboard.class);
    }


    /**
     * adds a given score on the server
     *
     * @param quizName the name of the quiz
     * @param newScore the new question object
     * @throws IOException
     * @throws InterruptedException
     */
    public void postScore(String quizName, Score newScore) throws IOException, InterruptedException {
        sendRequest("POST", "/leaderboards/" + quizName, objectMapper.writeValueAsString(newScore), "");
    }


    /**
     * sends a request to the server
     *
     * @param method       the http method
     * @param relativePath the path relative to /api
     * @param body         the body of the request
     * @return the response of the request
     * @throws IOException
     * @throws InterruptedException
     */
    private HttpResponse<String> sendRequest(String method, String relativePath, String body, String header)
            throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().method(method, HttpRequest.BodyPublishers.ofString(body)).header("Authorization", header)
                .uri(URI.create(API_URL + relativePath)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        int statusCode = response.statusCode();
        if (statusCode > 299) {
            System.out.println("Fail: " + HttpStatus.valueOf(statusCode).getReasonPhrase());
            throw new IOException("Response from server is not valid. The status code is " + statusCode);
        }
        return response;
    }

}
