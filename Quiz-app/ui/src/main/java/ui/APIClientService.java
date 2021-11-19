package ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.*;
import io.QuizPersistence;
import ui.constants.Errors;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class APIClientService {

    private static final String API_URL = "http://localhost:8080/api";
    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper;
    private final Map<Integer, String> errorMessageMap;

    /**
     *
     */
    public APIClientService() {
        objectMapper = QuizPersistence.createObjectMapper();
        errorMessageMap = new HashMap<>();
        errorMessageMap.put(500, Errors.DEFAULT);
    }

    /**
     * fetches a quiz from the server
     *
     * @param quizName the name of the quiz to be fetched
     * @return the quiz
     * @throws IOException
     * @throws InterruptedException
     */
    public Quiz getQuiz(String quizName) throws IOException {
        errorMessageMap.put(404, Errors.GET_QUIZ_404);
        HttpResponse<String> response = sendRequest("GET", "/quizzes/" + quizName,
                "", "");
        return objectMapper.readValue(response.body(), Quiz.class);
    }

    /**
     * fetches all quiz names from the server
     *
     * @return a list of quiz names
     * @throws IOException
     * @throws InterruptedException
     */
    public List<String> getListOfQuizNames() throws IOException {
        HttpResponse<String> response = sendRequest("GET", "/quizzes", "", "");
        return objectMapper.readValue(response.body(), List.class);
    }

    /**
     * posts a quiz to the server
     *
     * @param quiz the quiz to be posted
     * @throws IOException
     * @throws InterruptedException
     */
    public void postQuiz(Quiz quiz) throws IOException {
        errorMessageMap.put(403, Errors.POST_QUIZ_403);
        sendRequest("POST", "/quizzes", objectMapper.writeValueAsString(quiz), "");
    }

    /**
     * updates a given question on the server
     *
     * @param quizName    the name of the quiz
     * @param questionID  the id of the question
     * @param newQuestion the new question object
     * @param accessToken token for authorization
     * @throws IOException
     * @throws InterruptedException
     */
    public void putQuestion(String quizName, int questionID, Question newQuestion, String accessToken)
            throws IOException {
        errorMessageMap.put(403, Errors.PUT_QUESTION_403);
        errorMessageMap.put(404, Errors.PUT_QUESTION_404);
        sendRequest("PUT", "/quizzes/" + quizName + "/" + questionID,
                objectMapper.writeValueAsString(newQuestion), accessToken);
    }

    /**
     * deletes a quiz from the server given its name
     *
     * @param quizName    the name of the quiz
     * @param accessToken token for authorization
     * @throws IOException
     * @throws InterruptedException
     */
    public void deleteQuiz(String quizName, String accessToken) throws IOException {
        errorMessageMap.put(403, Errors.DELETE_QUIZ_403);
        errorMessageMap.put(404, Errors.DELETE_QUIZ_404);
        sendRequest("DELETE", "/quizzes/" + quizName, "", accessToken);
    }

    /**
     * deletes a given question from a given quiz on the server
     *
     * @param quizName    the name of the quiz
     * @param questionID  the id of the question
     * @param accessToken token for authorization
     * @throws IOException
     * @throws InterruptedException
     */
    public void deleteQuestion(String quizName, int questionID, String accessToken)
            throws IOException {
        errorMessageMap.put(403, Errors.DELETE_QUESTION_403);
        errorMessageMap.put(404, Errors.DELETE_QUESTION_404);
        sendRequest("DELETE", "/quizzes/" + quizName + "/" + questionID, "", accessToken);
    }

    /**
     * adds a question to a given quiz
     *
     * @param quizName    the name of the quiz
     * @param newQuestion the name of the question
     * @param accessToken token for authorization
     * @throws IOException
     * @throws InterruptedException
     */
    public void addQuestion(String quizName, Question newQuestion, String accessToken)
            throws IOException {
        errorMessageMap.put(403, Errors.ADD_QUESTION_403);
        errorMessageMap.put(404, Errors.ADD_QUESTION_404);
        sendRequest("POST", "/quizzes/" + quizName, objectMapper.writeValueAsString(newQuestion), accessToken);
    }

    /**
     * attempts to log the user in
     *
     * @param userRecord the user data
     * @return the active auth token
     * @throws IOException
     * @throws InterruptedException
     */
    public String loginUser(UserRecord userRecord) throws IOException {
        errorMessageMap.put(403, Errors.LOGIN_403);
        return sendRequest("POST", "/users/login", objectMapper.writeValueAsString(userRecord), "").body();
    }

    /**
     * attempts to register the user
     *
     * @param userRecord the information about the user
     * @return the active auth token
     * @throws IOException
     * @throws InterruptedException
     */
    public String registerUser(UserRecord userRecord) throws IOException {
        errorMessageMap.put(403, Errors.REGISTER_403);
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
    public Leaderboard getLeaderboard(String quizName) throws IOException {
        errorMessageMap.put(404, Errors.GET_LEADERBOARD_404);
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
    public void postScore(String quizName, Score newScore) throws IOException {
        errorMessageMap.put(404, Errors.POST_SCORE_404);
        sendRequest("POST", "/leaderboards/" + quizName, objectMapper.writeValueAsString(newScore), "");
    }


    /**
     * sends a request to the server
     *
     * @param method       the http method
     * @param relativePath the path relative to /api
     * @param body         the body of the request
     * @param header       the header
     * @return the response of the request
     * @throws IOException
     * @throws InterruptedException
     */
    private HttpResponse<String> sendRequest(String method, String relativePath,
                                             String body, String header)
            throws IOException {
        HttpRequest request = HttpRequest.newBuilder().method(method, HttpRequest.BodyPublishers.ofString(body))
                .header("Authorization", header)
                .uri(URI.create(API_URL + relativePath)).build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (InterruptedException e) {
            Utilities.alertUser("Kunne ikke koble til serveren");
            throw new IOException("Connection failed");
        }
        int statusCode = response.statusCode();
        if (statusCode > 299) {
            Utilities.alertUser(errorMessageMap.getOrDefault(statusCode, "En ukjent feil oppstod"));
            throw new IOException("Response from server is not valid. The status code is " + statusCode);
        }
        return response;
    }

}
