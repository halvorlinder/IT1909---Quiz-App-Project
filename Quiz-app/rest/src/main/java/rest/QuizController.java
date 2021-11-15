package rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.Leaderboard;
import core.Question;
import core.Quiz;
import core.Score;
import io.LeaderboardPersistence;
import io.QuizPersistence;
import io.UserPersistence;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api")
public class QuizController {

    private final QuizPersistence quizPersistence;
    private final LeaderboardPersistence leaderboardPersistence;
    private final ObjectMapper objectMapper;

    /**
     * inits the controller
     * @throws IOException
     */
    public QuizController() throws IOException {
        this.objectMapper = UserPersistence.createObjectMapper();
        this.quizPersistence = new QuizPersistence();
        this.leaderboardPersistence = new LeaderboardPersistence();
    }

    /**
     * returns a response containing a quiz
     * @param name the name of the quiz
     * @param response
     * @return the quiz
     */
    @GetMapping("/quizzes/{name}")
    public String getQuiz(@PathVariable("name") String name, HttpServletResponse response) {
        try {
            return objectMapper.writeValueAsString(quizPersistence.loadQuiz(name));
        } catch (IOException e) {
            e.printStackTrace();
        }
        response.setStatus(404);
        return null;
    }

    /**
     *
     * @param response
     * @return a list of all quizzes
     */
    @GetMapping("/quizzes")
    public String getQuizzes(HttpServletResponse response) {
        try {
            return objectMapper.writeValueAsString(quizPersistence.getListOfQuizNames());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        response.setStatus(500);
        return null;
    }

    /**
     * posts a new quiz
     * @param quizJSON the new quiz
     * @param response
     * @return the new quiz
     */
    @PostMapping("/quizzes")
    public String postQuiz(@RequestBody String quizJSON, HttpServletResponse response) {
        try {
            Quiz quiz = objectMapper.readValue(quizJSON, Quiz.class);
            if (quizPersistence.getListOfQuizNames().stream().anyMatch(name -> name.equals(quiz.getName()))) {
                response.setStatus(403);
                return null;
            }
            quizPersistence.saveQuiz(quiz);
            Leaderboard leaderboard = new Leaderboard(quiz.getName(), quiz.getQuizLength());
            leaderboardPersistence.saveLeaderboard(leaderboard);
            return quizJSON;
        } catch (IOException e) {
            e.printStackTrace();
        }
        response.setStatus(500);
        return null;
    }

    /**
     *
     * @param question the new question
     * @param quizName the name of the quiz
     * @param response
     * @return the quiz
     */
    @PostMapping("quizzes/{name}")
    public String addQuestion(@RequestBody String question,
                              @PathVariable("name") String quizName, HttpServletResponse response) {
        try {
            Quiz quiz = quizPersistence.loadQuiz(quizName);
            quiz.addQuestion(objectMapper.readValue(question, Question.class));
            quizPersistence.saveQuiz(quiz);
            leaderboardPersistence.deleteLeaderboard(quizName);
            Leaderboard leaderboard = new Leaderboard(quiz.getName(), quiz.getQuizLength());
            leaderboardPersistence.saveLeaderboard(leaderboard);
            return objectMapper.writeValueAsString(quiz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        response.setStatus(404);
        return null;
    }

    /**
     *
     * @param question a new question
     * @param quizName the name of the quiz
     * @param questionId the id of the question
     * @param response
     * @return edits a question on a given index in a given quiz
     */
    @PutMapping("quizzes/{name}/{id}")
    public String editQuestion(@RequestBody String question,
                               @PathVariable("name") String quizName,
                               @PathVariable("id") int questionId, HttpServletResponse response) {
        try {
            Quiz quiz = quizPersistence.loadQuiz(quizName);
            quiz.setQuestion(questionId, objectMapper.readValue(question, Question.class));
            quizPersistence.saveQuiz(quiz);
            leaderboardPersistence.deleteLeaderboard(quizName);
            Leaderboard leaderboard = new Leaderboard(quiz.getName(), quiz.getQuizLength());
            leaderboardPersistence.saveLeaderboard(leaderboard);
            return objectMapper.writeValueAsString(quiz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.setStatus(404);
        return null;
    }

    /**
     * deletes a quiz
     * @param quizName the name of the quiz
     * @param response
     */
    @DeleteMapping("quizzes/{name}")
    public void deleteQuiz(@PathVariable("name") String quizName, HttpServletResponse response) {
        if (quizPersistence.deleteQuiz(quizName)) {
            leaderboardPersistence.deleteLeaderboard(quizName);
            response.setStatus(200);
        } else
            response.setStatus(404);
    }

    /**
     * deletes a question in a given position from a given quiz
     * @param quizName the name of the quiz
     * @param questionId the index of the question
     * @param response
     * @return the updated quiz
     */
    @DeleteMapping("quizzes/{name}/{id}")
    @ResponseBody
    public String deleteQuestion(@PathVariable("name") String quizName,
                                 @PathVariable("id") int questionId, HttpServletResponse response) {
        try {
            Quiz quiz = quizPersistence.loadQuiz(quizName);
            quiz.deleteQuestion(questionId);
            quizPersistence.saveQuiz(quiz);
            leaderboardPersistence.deleteLeaderboard(quizName);
            Leaderboard leaderboard = new Leaderboard(quiz.getName(), quiz.getQuizLength());
            leaderboardPersistence.saveLeaderboard(leaderboard);
            return objectMapper.writeValueAsString(quiz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.setStatus(404);
        return null;
    }

    @GetMapping("/leaderboards/{name}")
    public String getLeaderboard(@PathVariable("name") String name, HttpServletResponse response) {
        try {
            return objectMapper.writeValueAsString(leaderboardPersistence.loadLeaderboard(name));
        } catch (IOException e) {
            e.printStackTrace();
        }
        response.setStatus(404);
        return null;
    }

    @PostMapping("leaderboards/{name}")
    public String addScore(@RequestBody String score, @PathVariable("name") String quizName, HttpServletResponse response) {
        try {
            Leaderboard leaderboard = leaderboardPersistence.loadLeaderboard(quizName);
            leaderboard.addScore(objectMapper.readValue(score, Score.class));
            leaderboardPersistence.saveLeaderboard(leaderboard);
            return objectMapper.writeValueAsString(leaderboard);
        } catch (IOException e) {
            e.printStackTrace();
        }
        response.setStatus(404);
        return null;
    }

}
