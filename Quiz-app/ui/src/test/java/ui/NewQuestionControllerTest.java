package ui;


import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import core.Question;
import core.Quiz;
import io.QuizPersistence;
import io.SavePaths;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;


import ui.controllers.NewQuestionController;

import static org.junit.jupiter.api.Assertions.*;
import static ui.TestHelpers.deleteQuiz;

public class NewQuestionControllerTest extends ApplicationTest {

    private QuizPersistence quizPersistence;

    @Override
    public void start(final Stage stage) throws Exception {
        SavePaths.enableTestMode();
        quizPersistence = new QuizPersistence();
        quizPersistence.saveQuiz(new Quiz("testNewQuestion", new ArrayList<>()));
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("NewQuestion.fxml"));
        NewQuestionController controller = new NewQuestionController("testNewQuestion");
        loader.setController(controller);
        final Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @Test
    public void testSubmitEmptyFields() {
        writeQuestion("?","1","","3","4", 0);
        Node dialogPane = lookup(".dialog-pane").query();
        Assertions.assertDoesNotThrow(() -> {
            from(dialogPane).lookup((Text t) -> t.getText().startsWith("Du må fylle ut alle feltene!")).query();
        });
    }
    @Test
    public void testSubmitEmptyQuestion() {
        writeQuestion("","1","2","3","4", 0);
        Node dialogPane = lookup(".dialog-pane").query();
        Assertions.assertDoesNotThrow(() -> {
            from(dialogPane).lookup((Text t) -> t.getText().startsWith("Du må skrive inn et spørsmål")).query();
        });
    }

    @Test
    public void testSubmitQuestion() throws IOException {
        writeQuestion("?","1","2","3","4", 0);
        Quiz quiz = quizPersistence.loadQuiz("testNewQuestion");
        Question question = quiz.getQuestions().get(0);
        assertEquals("?", question.getQuestion());
        assertEquals(0, question.getAnswer());
        for(int i = 0; i<4; i++){
            assertEquals((i+1)+"", question.getChoice(i));
        }
    }

    @AfterAll
    public static void cleanUp(){
        deleteQuiz("testNewQuestion");
    }

    private void writeQuestion(String question, String choice1, String choice2, String choice3, String choice4, int rightAnswer){
        clickOn("#questionText").write(question);
        clickOn("#choice1").write(choice1);
        clickOn("#choice2").write(choice2);
        clickOn("#choice3").write(choice3);
        clickOn("#choice4").write(choice4);
        if(rightAnswer>=0)
            clickOn("#radioButton"+(rightAnswer+1));
        clickOn("#submitButton");
    }
}