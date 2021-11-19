package rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.*;
import io.LeaderboardPersistence;
import io.QuizPersistence;
import io.UserPersistence;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;

@RestController
@RequestMapping("/api")
public class QuizController {

    private final QuizPersistence quizPersistence;
    private final UserPersistence userPersistence;
    private final LeaderboardPersistence leaderboardPersistence;
    private final ObjectMapper objectMapper;
    private final AuthHandler authHandler;

    private static final int ISE = 500;
    private static final int NF = 404;
    private static final int FBN = 403;

    /**
     * inits the controller
     *
     * @throws IOException
     */
    public QuizController() throws IOException {
        this.objectMapper = UserPersistence.createObjectMapper();
        this.quizPersistence = new QuizPersistence();
        this.userPersistence = new UserPersistence();
        this.leaderboardPersistence = new LeaderboardPersistence();
        this.authHandler = new AuthHandler();
    }

    /**
     * returns a response containing a quiz
     *
     * @param name     the name of the quiz
     * @param response
     * @return the quiz
     */
    @GetMapping("/quizzes/{name}")
    public String getQuiz(@PathVariable("name") String name, HttpServletResponse response) {
        try {
            return objectMapper.writeValueAsString(quizPersistence.loadQuiz(name));
        } catch (FileNotFoundException fileNotFoundException) {
            response.setStatus(NF);
            System.out.println(fileNotFoundException);
        } catch (IOException ioException) {
            response.setStatus(ISE);
            System.out.println(ioException);
        }
        return null;
    }

    /**
     * get quiz with given name
     *
     * @param response
     * @return a list of all quizzes
     */
    @GetMapping("/quizzes")
    public String getQuizzes(HttpServletResponse response) {
        try {
            return objectMapper.writeValueAsString(quizPersistence.getListOfQuizNames());
        } catch (IOException ioException) {
            System.out.println(ioException);
            response.setStatus(ISE);
        }
        return null;
    }

    /**
     * posts a new quiz
     *
     * @param quizJSON the new quiz
     * @param response
     * @return the new quiz
     */
    @PostMapping("/quizzes")
    public String postQuiz(@RequestBody String quizJSON, HttpServletResponse response) {
        try {
            Quiz quiz = objectMapper.readValue(quizJSON, Quiz.class);
            quiz.legalizeName();
            if (quizPersistence.getListOfQuizNames().stream().anyMatch(name -> name.equals(quiz.getName()))) {
                response.setStatus(FBN);
                return null;
            }
            quizPersistence.saveQuiz(quiz);
            Leaderboard leaderboard = new Leaderboard(quiz.getName(), quiz.getQuizLength());
            leaderboardPersistence.saveLeaderboard(leaderboard);
            return quizJSON;
        } catch (IOException ioException) {
            System.out.println(ioException);
            response.setStatus(ISE);
        }
        return null;
    }

    /**
     * @param token    authorization token
     * @param question the new question
     * @param quizName the name of the quiz
     * @param response
     * @return the quiz
     */
    @PostMapping("quizzes/{name}")
    public String addQuestion(@RequestHeader("Authorization") String token, @RequestBody String question,
                              @PathVariable("name") String quizName, HttpServletResponse response) {
        try {
            Quiz quiz = quizPersistence.loadQuiz(quizName);
            if (authHandler.hasAccess(token, quiz)) {
                quiz.addQuestion(objectMapper.readValue(question, Question.class));
                quizPersistence.saveQuiz(quiz);
                leaderboardPersistence.deleteLeaderboard(quizName);
                Leaderboard leaderboard = new Leaderboard(quiz.getName(), quiz.getQuizLength());
                leaderboardPersistence.saveLeaderboard(leaderboard);
                return objectMapper.writeValueAsString(quiz);
            }
            response.setStatus(FBN);
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println(fileNotFoundException);
            response.setStatus(NF);
        } catch (IOException ioException) {
            System.out.println(ioException);
            response.setStatus(ISE);
        }
        return null;
    }

    /**
     * @param token      authorization token
     * @param question   a new question
     * @param quizName   the name of the quiz
     * @param questionId the id of the question
     * @param response
     * @return edits a question on a given index in a given quiz
     */
    @PutMapping("quizzes/{name}/{id}")
    public String editQuestion(@RequestHeader("Authorization") String token, @RequestBody String question,
                               @PathVariable("name") String quizName,
                               @PathVariable("id") int questionId, HttpServletResponse response) {
        try {
            Quiz quiz = quizPersistence.loadQuiz(quizName);
            if (authHandler.hasAccess(token, quiz)) {
                quiz.setQuestion(questionId, objectMapper.readValue(question, Question.class));
                quizPersistence.saveQuiz(quiz);
                leaderboardPersistence.deleteLeaderboard(quizName);
                Leaderboard leaderboard = new Leaderboard(quiz.getName(), quiz.getQuizLength());
                leaderboardPersistence.saveLeaderboard(leaderboard);
                return objectMapper.writeValueAsString(quiz);
            }
            response.setStatus(FBN);
        } catch (FileNotFoundException | IndexOutOfBoundsException notFoundException) {
            System.out.println(notFoundException);
            response.setStatus(NF);
        } catch (IOException ioException) {
            System.out.println(ioException);
            response.setStatus(ISE);
        }
        return null;
    }

    /**
     * deletes a quiz
     *
     * @param quizName the name of the quiz
     * @param token    authorization token
     * @param response
     */
    @DeleteMapping("quizzes/{name}")
    public void deleteQuiz(@RequestHeader("Authorization") String token, @PathVariable("name") String quizName,
                           HttpServletResponse response) {
        try {
            if (authHandler.hasAccess(token, quizPersistence.loadQuiz(quizName))) {
                quizPersistence.deleteQuiz(quizName);
                leaderboardPersistence.deleteLeaderboard(quizName);
            } else
                response.setStatus(FBN);
        } catch (FileNotFoundException fileNotFoundException) {
            response.setStatus(NF);
            System.out.println(fileNotFoundException);
        } catch (IOException ioException) {
            response.setStatus(ISE);
            System.out.println(ioException);
        }
    }

    /**
     * deletes a question in a given position from a given quiz
     *
     * @param token      authorization token
     * @param quizName   the name of the quiz
     * @param questionId the index of the question
     * @param response
     * @return the updated quiz
     */
    @DeleteMapping("quizzes/{name}/{id}")
    @ResponseBody
    public String deleteQuestion(@RequestHeader("Authorization") String token,
                                 @PathVariable("name") String quizName,
                                 @PathVariable("id") int questionId, HttpServletResponse response) {
        try {
            Quiz quiz = quizPersistence.loadQuiz(quizName);
            if (authHandler.hasAccess(token, quiz)) {
                quiz.deleteQuestion(questionId);
                quizPersistence.saveQuiz(quiz);
                Leaderboard leaderboard = new Leaderboard(quiz.getName(), quiz.getQuizLength());
                leaderboardPersistence.saveLeaderboard(leaderboard);
                return objectMapper.writeValueAsString(quiz);
            } else
                response.setStatus(FBN);
        } catch (FileNotFoundException | IndexOutOfBoundsException notFoundException) {
            response.setStatus(NF);
            System.out.println(notFoundException);
        } catch (IOException ioException) {
            response.setStatus(ISE);
            System.out.println(ioException);
        }
        return null;
    }

    /**
     * gets a Leaderboard from its name
     *
     * @param name     the name of the quiz
     * @param response
     * @return a leaderboard for a quiz given its name
     */
    @GetMapping("/leaderboards/{name}")
    public String getLeaderboard(@PathVariable("name") String name, HttpServletResponse response) {
        try {
            return objectMapper.writeValueAsString(leaderboardPersistence.loadLeaderboard(name));
        } catch (FileNotFoundException fileNotFoundException) {
            response.setStatus(NF);
            System.out.println(fileNotFoundException);
        } catch (IOException ioException) {
            response.setStatus(ISE);
            System.out.println(ioException);
        }
        return null;
    }

    /**
     * posts a score to a given scoreboard
     *
     * @param score    the user with score
     * @param quizName the name of the quiz
     * @param response
     * @return the updated scoreboard
     */
    @PostMapping("leaderboards/{name}")
    public String addScore(@RequestBody String score,
                           @PathVariable("name") String quizName, HttpServletResponse response) {
        try {
            Leaderboard leaderboard = leaderboardPersistence.loadLeaderboard(quizName);
            leaderboard.addScore(objectMapper.readValue(score, Score.class));
            leaderboardPersistence.saveLeaderboard(leaderboard);
            return objectMapper.writeValueAsString(leaderboard);
        } catch (FileNotFoundException fileNotFoundException) {
            response.setStatus(NF);
            System.out.println(fileNotFoundException);
        } catch (IOException ioException) {
            response.setStatus(ISE);
            System.out.println(ioException);
        }
        return null;
    }

    /**
     * attempts to login in a user
     *
     * @param response
     * @param userDataJson the login information
     * @return the user's access token
     */
    @PostMapping("/users/login")
    public String loginUser(HttpServletResponse response, @RequestBody String userDataJson) {
        try {
            UserData userData = userPersistence.loadUserData();
            UserRecord userRecord = objectMapper.readValue(userDataJson, UserRecord.class);
            if (userData.attemptLogIn(userRecord)) {
                return authHandler.registerAndGetToken(userRecord.getUsername());
            }
            response.setStatus(FBN);
        } catch (IOException ioException) {
            response.setStatus(ISE);
            System.out.println(ioException);
        }
        return null;
    }

    /**
     * attempts to register a user
     *
     * @param response
     * @param userDataJson the registration information
     * @return the user's access token
     */
    @PostMapping("/users/register")
    public String registerUser(HttpServletResponse response, @RequestBody String userDataJson) {
        try {
            UserData userData = userPersistence.loadUserData();
            UserRecord userRecord = objectMapper.readValue(userDataJson, UserRecord.class);
            if (userData.attemptRegister(userRecord)) {
                userPersistence.saveUserData(userData);
                return authHandler.registerAndGetToken(userRecord.getUsername());
            }
            response.setStatus(FBN);
        } catch (IOException ioException) {
            response.setStatus(ISE);
            System.out.println(ioException);
        }
        return null;
    }
}
