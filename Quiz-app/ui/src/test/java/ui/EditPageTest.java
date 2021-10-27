package ui;

import org.testfx.framework.junit5.ApplicationTest;
import core.Question;
import core.Quiz;
import io.Paths;
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
import java.nio.file.Files;
import java.util.List;

import static ui.TestHelpers.deleteQuiz;

public class EditPageTest extends ApplicationTest {
    @Override
    public void start(final Stage stage) throws Exception {
        Paths.setBasePath("test");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
        final Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void initQuiz(){
        clickOn("#quizNameField").write("testQuiz");
        clickOn("#addNewQuizButton");
        Quiz quiz = new Quiz("testQuiz", List.of(new Question("a", List.of("a", "a", "a", "a"), 0), new Question("b", List.of("a", "a", "a", "a"), 0)));
        try {
            QuizPersistence quizPersistence = new QuizPersistence();
            quizPersistence.saveQuiz(quiz);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        VBox vBox = lookup("#quizList").query();
        clickOn(from(vBox).lookup((Button b) -> b.getText().equals("Endre")).queryButton());
    }

    @Test
    public void testDataDisplay(){
        initQuiz();
        VBox vBox = lookup("#questionList").query();
        Assertions.assertDoesNotThrow(() -> {
            from(vBox).lookup((Label label) -> label.getText().equals("a")).query();
            from(vBox).lookup((Label label) -> label.getText().equals("b")).query();
        });
        Label label = lookup("#titleText").query();
        Assertions.assertEquals("Endre testQuiz", label.getText());
        deleteQuiz("testQuiz");
    }

    @Test
    public void testAddQuestion(){
        initQuiz();
        clickOn("#newQuestionButton");
        Assertions.assertDoesNotThrow(() -> {
            lookup("#headline").query();
        });
    }
}
