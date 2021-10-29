package ui;

import core.Question;
import core.Quiz;
import io.SavePaths;
import io.QuizPersistence;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import ui.controllers.HomePageController;

import java.io.IOException;
import java.util.List;

import static ui.TestHelpers.deleteQuiz;


public class HomePageTest extends ApplicationTest {

    private FXMLLoader loader;

    @Override
    public void start(final Stage stage) throws Exception {
        SavePaths.enableTestMode();
        loader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
        final Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void initQuiz() {
        clickOn("#quizNameField").write("testQuiz");
        clickOn("#addNewQuizButton");
    }

    @Test
    public void testCreateEmptyNameQuiz() {
        HomePageController controller = loader.getController();
        Assertions.assertThrows(IllegalArgumentException.class, controller::addNewQuizFile);
    }

    @Test
    public void testCreateQuiz() {
        initQuiz();
        VBox vBox = lookup("#quizList").query();
        Assertions.assertDoesNotThrow(() -> {
            from(vBox).lookup((Label label) -> label.getText().equals("testQuiz")).query();
        });
        deleteQuiz("testQuiz");
    }

    @Test
    public void testStartEmptyQuiz() {
        initQuiz();
        VBox vBox = lookup("#quizList").query();
        clickOn(from(vBox).lookup((Button b) -> b.getText().equals("Spill")).queryButton());
        Node dialogPane = lookup(".dialog-pane").query();
        Assertions.assertDoesNotThrow(() -> {
            from(dialogPane).lookup((Text t) -> t.getText().startsWith("Denne quizen har ingen spørsmål")).query();
        });
        deleteQuiz("testQuiz");
    }

    @Test
    public void testStartQuiz() {
        initQuiz();
        Quiz quiz = new Quiz("testQuiz", List.of(new Question()));
        try {
            QuizPersistence quizPersistence = new QuizPersistence();
            quizPersistence.saveQuiz(quiz);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        VBox vBox = lookup("#quizList").query();
        clickOn(from(vBox).lookup((Button b) -> b.getText().equals("Spill")).queryButton());
        Assertions.assertDoesNotThrow(() -> {
            lookup("#questionLabel").query();
        });
        deleteQuiz("testQuiz");
    }

}
