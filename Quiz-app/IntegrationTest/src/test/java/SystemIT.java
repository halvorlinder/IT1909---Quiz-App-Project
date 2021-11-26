import io.SavePaths;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.service.query.EmptyNodeQueryException;
import ui.App;
import ui.constants.Errors;
import ui.controllers.LoginPageController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class SystemIT extends ApplicationTest {

    private final String username = "user";
    private final String password = "pass";
    private final String wrongPassword = "ass";

    private final String username2 = "hallvard";
    private final String password2 = "hunter2";

    private final String quizName = "q";

    private final String questionText = "?";
    private final String choice1 = "1";
    private final String choice2 = "2";
    private final String choice3 = "3";
    private final String choice4 = "4";
    private final int correctChoice1 = 0;
    private final int correctChoice2 = 1;

    /**
     * Start the app
     *
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(final Stage stage) throws Exception {
        // Start the app (server is starten in POM file)
        LoginPageController logInPageController = new LoginPageController();
        final FXMLLoader loader = App.getFXMLLoader("LoginPage.fxml");
        loader.setController(logInPageController);
        final Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    // Temporary as we need to delete local files before starting test as there
    // are problems with SavePath.enableTestMode()
    @BeforeAll
    public static void preTestTeardown() {
        try {
            FileUtils.cleanDirectory(new File(SavePaths.getBasePath() + "/Quizzes"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileUtils.cleanDirectory(new File(SavePaths.getBasePath() + "/leaderboards"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Files.delete(Path.of(SavePaths.getBasePath() + "users.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void systemTest() {
        // Register a new user
        clickOn("#registerUserName").write(username);
        clickOn("#registerPassword").write(password);
        clickOn("#register");

        // Create a new quiz
        clickOn("#quizNameField").write(quizName);
        clickOn("#addNewQuizButton");
        VBox quizList = lookup("#quizList").query();

        // Enter edit quiz mode
        clickOn(from(quizList).lookup((Button b) -> b.getText().equals("Endre")).queryButton());

        // Write two questions for the quiz
        addQuestionToQuiz(questionText, choice1, choice2, choice3, choice4, correctChoice1);
        addQuestionToQuiz(questionText, choice1, choice2, choice3, choice4, correctChoice2);
        clickOn(lookup("#backButton").queryButton());

        // Play the quiz and get every answer correct
        clickOn(from(quizList).lookup((Button b) -> b.getText().equals("Spill")).queryButton());
        clickOn("#option" + (correctChoice1 + 1));
        clickOn("#submitAnswer");
        clickOn("#option" + (correctChoice2 + 1));
        clickOn("#submitAnswer");
        clickOn("#returnButton");
        quizList = lookup("#quizList").query();

        // View the leaderboard and see that you are at the top of the quiz
        clickOn(from(quizList).lookup((Button b) -> b.getText().equals("Ledertavle")).queryButton());
        VBox lbList = lookup("#leaderboardList").query();
        Assertions.assertDoesNotThrow(() -> {
            from(lbList).lookup((Label label) -> label.getText().equals("1")).query();
            from(lbList).lookup((Label label) -> label.getText().equals("2/2")).query();
            from(lbList).lookup((Label label) -> label.getText().equals(username)).query();
        });
        clickOn(lookup("#backButton").queryButton());

        // Change a question in the quiz
        quizList = lookup("#quizList").query();
        clickOn(from(quizList).lookup((Button b) -> b.getText().equals("Endre")).queryButton());
        VBox questionList = lookup("#questionList").query();
        clickOn(from(questionList).lookup((Button b) -> b.getText().equals("Endre")).queryButton());
        clickOn("#radioButton4");
        clickOn("#submitButton");

        // Verify that the leaderboard is now reset
        clickOn(lookup("#backButton").queryButton());
        quizList = lookup("#quizList").query();
        clickOn(from(quizList).lookup((Button b) -> b.getText().equals("Ledertavle")).queryButton());
        VBox lbList2 = lookup("#leaderboardList").query();
        Assertions.assertThrows(EmptyNodeQueryException.class, () -> from(lbList2).lookup((Label label) -> label.getText().equals(username)).query());

        // Log out of the app
        clickOn(lookup("#backButton").queryButton());
        clickOn("#signOut");

        // Register a new user
        clickOn("#registerUserName").write(username2);
        clickOn("#registerPassword").write(password2);
        clickOn("#register");

        // Verify that this user cannot delete the quiz created by the first user
        quizList = lookup("#quizList").query();
        clickOn(from(quizList).lookup((Button b) -> b.getText().equals("Endre")).queryButton());
        clickOn("#deleteQuizButton");
        Node dialogPane = lookup(".dialog-pane").query();
        Assertions.assertDoesNotThrow(() -> {
            from(dialogPane).lookup((Text t) -> t.getText().startsWith(Errors.DELETE_QUIZ_403)).query();
        });
        press(KeyCode.ENTER);
        release(KeyCode.ENTER);

        // Log out of the app
        clickOn(lookup("#backButton").queryButton());
        clickOn("#signOut");

        // Attempt to login as the first user, but you forgot your password
        clickOn("#logInUserName").write(username);
        clickOn("#logInPassword").write(wrongPassword);
        clickOn("#logIn");
        Node dialogPane2 = lookup(".dialog-pane").query();
        Assertions.assertDoesNotThrow(() -> {
            from(dialogPane2).lookup((Text t) -> t.getText().startsWith(Errors.LOGIN_403)).query();
        });
        press(KeyCode.ENTER);
        release(KeyCode.ENTER);

        // Remeber your password and login
        doubleClickOn("#logInPassword").write(password);
        clickOn("#logIn");

        // Log out
        clickOn("#signOut");
    }

    /**
     * Helper method to add a question to the quiz. Clicks on the newQuestionButton
     * and writes a new question using writeQuestion(...)
     *
     * @param question    the question text
     * @param choice1     the first choice
     * @param choice2     the second choice
     * @param choice3     the third choice
     * @param choice4     the fourth choice
     * @param rightAnswer the index of the correct answer (starts at 0)
     */
    private void addQuestionToQuiz(String question, String choice1, String choice2, String choice3, String choice4, int rightAnswer) {
        clickOn(lookup("#newQuestionButton").queryButton());
        writeQuestion(question, choice1, choice2, choice3, choice4, rightAnswer);
    }

    /**
     * Writes a question and submits it
     *
     * @param question    the question text
     * @param choice1     the first choice
     * @param choice2     the second choice
     * @param choice3     the third choice
     * @param choice4     the fourth choice
     * @param rightAnswer the index of the correct answer (starts at 0)
     */
    private void writeQuestion(String question, String choice1, String choice2, String choice3, String choice4, int rightAnswer) {
        clickOn("#questionText").write(question);
        clickOn("#choice1").write(choice1);
        clickOn("#choice2").write(choice2);
        clickOn("#choice3").write(choice3);
        clickOn("#choice4").write(choice4);
        if (rightAnswer >= 0)
            clickOn("#radioButton" + (rightAnswer + 1));
        clickOn("#submitButton");
    }

    /**
     * Delete all files created during the test
     */
    @AfterAll
    public static void teardown() {
        try {
            FileUtils.cleanDirectory(new File(SavePaths.getBasePath() + "/Quizzes"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileUtils.cleanDirectory(new File(SavePaths.getBasePath() + "/leaderboards"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Files.delete(Path.of(SavePaths.getBasePath() + "users.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

