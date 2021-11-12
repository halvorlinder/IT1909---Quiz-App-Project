package ui;

import com.fasterxml.jackson.core.JsonProcessingException;
import core.Question;
import core.Quiz;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import org.springframework.http.HttpStatus;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.QuizPersistence;

public class APIClientService{

    private static final String API_URL = "http://localhost:8080/api";
    private final HttpClient client = HttpClient.newHttpClient();
    private ObjectMapper objectMapper;

    public APIClientService() throws IOException, InterruptedException {
        objectMapper = QuizPersistence.createObjectMapper();
    }


    public Quiz getQuiz(String quizName) throws IOException, InterruptedException {
        HttpResponse<String> response = sendRequest("GET","/quizzes/"+quizName,"");
        Quiz quiz = objectMapper.readValue(response.body(),Quiz.class);
        return quiz;
    }

    public List<String> getListOfQuizNames() throws IOException, InterruptedException {
        HttpResponse<String> response = sendRequest("GET","/quizzes","");
        return objectMapper.readValue(response.body(), List.class);
    }

    public void postQuiz(Quiz quiz) throws IOException, InterruptedException {
       sendRequest("POST","/quizzes",objectMapper.writeValueAsString(quiz));
    }

    public void putQuestion(String quizName, int questionID, Question newQuestion) throws IOException, InterruptedException {
        sendRequest("PUT","/quizzes/" + quizName + "/" + questionID, objectMapper.writeValueAsString(newQuestion));
    }

    public void deleteQuiz(String quizName) throws IOException, InterruptedException {
        sendRequest("DELETE","/quizzes/"+quizName,"");
    }

    public void deleteQuestion(String quizName, int questionID) throws IOException, InterruptedException {
        sendRequest("DELETE","/quizzes/" + quizName + "/" + questionID, "");
    }


    private HttpResponse<String> sendRequest(String method, String relativePath, String body) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().method(method, HttpRequest.BodyPublishers.ofString(body)).uri(URI.create(API_URL+relativePath)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        int statusCode = response.statusCode();
        if(statusCode > 299){
            System.out.println(HttpStatus.valueOf(statusCode).getReasonPhrase());
            throw new IOException("Response from server is not valid. The status code is"+ statusCode);
        }
        return response;
    }

}
