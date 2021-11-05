package rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.Question;
import core.Quiz;
import io.QuizPersistence;
import io.UserPersistence;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api")
public class QuizController {

    private final QuizPersistence quizPersistence;
    private final ObjectMapper objectMapper;

    public QuizController() throws IOException {
        this.objectMapper = UserPersistence.createObjectMapper();
        this.quizPersistence = new QuizPersistence();
    }


    @GetMapping("/quizzes/{name}")
    public String getQuiz (@PathVariable("name")String name, HttpServletResponse response) {
        try {
            return objectMapper.writeValueAsString(quizPersistence.loadQuiz(name));
        } catch (IOException e) {
            e.printStackTrace();
        }
        response.setStatus(404);
        return null;
    }

    @GetMapping("/quizzes")
    public String getQuizzes(HttpServletResponse response){
        try {
            return objectMapper.writeValueAsString(quizPersistence.getListOfQuizNames());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        response.setStatus(500);
        return null;
    }

    @PostMapping("/quizzes")
    public String postQuiz(@RequestBody String quiz, HttpServletResponse response){
        try {
            quizPersistence.saveQuiz(objectMapper.readValue(quiz, Quiz.class));
            return quiz;
        } catch (IOException e) {
            e.printStackTrace();
        }
        response.setStatus(404);
        return null;
    }

    @PostMapping("quizzes/{name}")
    public String addQuestion(@RequestBody String question, @PathVariable("name") String quizName, HttpServletResponse response) {
        try {
            Quiz quiz = quizPersistence.loadQuiz(quizName);
            quiz.addQuestion(objectMapper.readValue(question, Question.class));
            quizPersistence.saveQuiz(quiz);
            return objectMapper.writeValueAsString(quiz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        response.setStatus(404);
        return null;
    }

    @PutMapping("quizzes/{name}/{id}")
    public String editQuestion(@RequestBody String question, @PathVariable("name") String quizName, @PathVariable("id") int questionId, HttpServletResponse response) {
        try {
            Quiz quiz = quizPersistence.loadQuiz(quizName);
            quiz.setQuestion(questionId,objectMapper.readValue(question,Question.class));
            quizPersistence.saveQuiz(quiz);
            return objectMapper.writeValueAsString(quiz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.setStatus(404);
        return null;
    }

    @DeleteMapping("quizzes/{name}")
    public void deleteQuiz(@PathVariable("name") String quizName, HttpServletResponse response) {
        if (quizPersistence.deleteQuiz(quizName))
            response.setStatus(200);
        else
            response.setStatus(404);
    }

    @DeleteMapping("quizzes/{name}/{id}")
    @ResponseBody
    public String deleteQuestion(@PathVariable("name") String quizName, @PathVariable("id") int questionId, HttpServletResponse response){
        try {
            Quiz quiz = quizPersistence.loadQuiz(quizName);
            quiz.deleteQuestion(questionId);
            quizPersistence.saveQuiz(quiz);
            return objectMapper.writeValueAsString(quiz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.setStatus(404);
        return null;
    }
}
