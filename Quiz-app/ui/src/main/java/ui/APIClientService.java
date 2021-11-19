package ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.*;
import io.QuizPersistence;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class APIClientService {

    private static final String API_URL = "http://localhost:8080/api";
    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper;
    private final Map<Integer, String> errorMessageMap;

    /**
     * Initialize a new APIClientService
     */
    public APIClientService() {
        objectMapper = QuizPersistence.createObjectMapper();
        errorMessageMap = new HashMap<>();
        errorMessageMap.put(500, "Beklager, noe gikk galt på serveren");
    }

    /**
     * fetches a quiz from the server
     *
     * @param quizName the name of the quiz to be fetched
     * @return the quiz
     * @throws IOException
     */
    public Quiz getQuiz(String quizName) throws IOException {
        errorMessageMap.put(404, "Beklager, quizen finnes ikke lenger");
        HttpResponse<String> response = sendRequest("GET", "/quizzes/" + quizName.replaceAll(" ", "\\$"),
                "", "");
        return objectMapper.readValue(response.body(), Quiz.class);
    }

    /**
     * fetches all quiz names from the server
     *
     * @return a list of quiz names
     * @throws IOException
     */
    public List<String> getListOfQuizNames() throws IOException {
        HttpResponse<String> response = sendRequest("GET", "/quizzes", "", "");
        List<String> names = objectMapper.readValue(response.body(), List.class);
        return names.stream().map(str -> str.replaceAll("\\$", " ")).collect(Collectors.toList());
    }

    /**
     * posts a quiz to the server
     *
     * @param quiz the quiz to be posted
     * @throws IOException
     */
    public void postQuiz(Quiz quiz) throws IOException {
        errorMessageMap.put(403, "Denne quizen finnes allerede");
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
     */
    public void putQuestion(String quizName, int questionID, Question newQuestion, String accessToken)
            throws IOException {
        errorMessageMap.put(403, "Du eier ikke denne quizen og du kan derfor ikke endre spørsmålet");
        errorMessageMap.put(404, "Beklager, dette spørsmålet finnes ikke lenger");
        sendRequest("PUT", "/quizzes/" + quizName.replaceAll(" ", "\\$") + "/" + questionID,
                objectMapper.writeValueAsString(newQuestion), accessToken);
    }

    /**
     * deletes a quiz from the server given its name
     *
     * @param quizName    the name of the quiz
     * @param accessToken token for authorization
     * @throws IOException
     */
    public void deleteQuiz(String quizName, String accessToken) throws IOException {
        errorMessageMap.put(403, "Du eier ikke denne quizen og du kan derfor ikke slette den");
        errorMessageMap.put(404, "Beklager, denne quizen finnes ikke lenger");
        sendRequest("DELETE", "/quizzes/" + quizName.replaceAll(" ", "\\$"), "", accessToken);
    }

    /**
     * deletes a given question from a given quiz on the server
     *
     * @param quizName    the name of the quiz
     * @param questionID  the id of the question
     * @param accessToken token for authorization
     * @throws IOException
     */
    public void deleteQuestion(String quizName, int questionID, String accessToken)
            throws IOException {
        errorMessageMap.put(403, "Du eier ikke denne quizen og du kan derfor ikke slette spørsmålet");
        errorMessageMap.put(404, "Beklager, dette spørsmålet finnes ikke lenger");
        sendRequest("DELETE", "/quizzes/" + quizName.replaceAll(" ", "\\$") + "/" + questionID, "", accessToken);
    }

    /**
     * adds a question to a given quiz
     *
     * @param quizName    the name of the quiz
     * @param newQuestion the name of the question
     * @param accessToken token for authorization
     * @throws IOException
     */
    public void addQuestion(String quizName, Question newQuestion, String accessToken)
            throws IOException {
        errorMessageMap.put(403, "Du eier ikke denne quizen og du kan derfor ikke legge til et spørsmål");
        errorMessageMap.put(404, "Beklager, denne quizen finnes ikke lenger");
        sendRequest("POST", "/quizzes/" + quizName.replaceAll(" ", "\\$"),
                objectMapper.writeValueAsString(newQuestion), accessToken);
    }

    /**
     * attempts to log the user in
     *
     * @param userRecord the user data
     * @return the active auth token
     * @throws IOException
     */
    public String loginUser(UserRecord userRecord) throws IOException {
        errorMessageMap.put(403, "Brukernavn eller passord er feil");
        return sendRequest("POST", "/users/login", objectMapper.writeValueAsString(userRecord), "").body();
    }

    /**
     * attempts to register the user
     *
     * @param userRecord the information about the user
     * @return the active auth token
     * @throws IOException
     */
    public String registerUser(UserRecord userRecord) throws IOException {
        errorMessageMap.put(403, "Beklager, dette brukernavnet er tatt");
        return sendRequest("POST", "/users/register", objectMapper.writeValueAsString(userRecord), "").body();
    }

    /**
     * fetches a leaderboard for a quiz from the server
     *
     * @param quizName the name of the quiz mapping to the leaderboard to be fetched
     * @return the leaderboard
     * @throws IOException
     */
    public Leaderboard getLeaderboard(String quizName) throws IOException {
        errorMessageMap.put(404, "Beklager, denne ledertavlen finnes ikke lenger");
        HttpResponse<String> response = sendRequest("GET", "/leaderboards/" + quizName.replaceAll(" ", "\\$"), "", "");
        return objectMapper.readValue(response.body(), Leaderboard.class);
    }


    /**
     * adds a given score on the server
     *
     * @param quizName the name of the quiz
     * @param newScore the new question object
     * @throws IOException
     */
    public void postScore(String quizName, Score newScore) throws IOException {
        errorMessageMap.put(404, "Beklager, kunne ikke registrere poengsum fordi quizen ikke finnes lenger");
        sendRequest("POST", "/leaderboards/" + quizName.replaceAll(" ", "\\$"),
                objectMapper.writeValueAsString(newScore), "");
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
