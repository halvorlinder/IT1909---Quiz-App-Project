package ui;


import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import io.SavePaths;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import ui.controllers.EditPageController;
import ui.controllers.InitializableController;
import ui.controllers.NewQuestionController;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class NewQuestionControllerTest extends ApplicationTest {


    private WireMockConfiguration config;
    private WireMockServer wireMockServer;

    @Override
    public void start(final Stage stage) throws Exception {
        SavePaths.enableTestMode();
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("NewQuestion.fxml"));
        NewQuestionController controller = new NewQuestionController("a");

        config = WireMockConfiguration.wireMockConfig().port(8080);
        wireMockServer = new WireMockServer(config.portNumber());
        wireMockServer.start();
        WireMock.configureFor("localhost", config.portNumber());
        stubFor(get(urlEqualTo("/api/quizzes/a"))
                .willReturn(aResponse()
                        .withBody("{\"name\":\"a\",\"questions\":[{\"question\":\"?\",\"answer\":0,\"choices\":[\"a\",\"b \",\"c \",\"d \"]}]}")
                        .withStatus(200)));

        EditPageController editPageController = new EditPageController("a");
        FXMLLoader loader2 = new FXMLLoader(getClass().getResource("EditPage.fxml"));
        loader2.setController(editPageController);
        controller.setPreviousPageInfo(editPageController, loader2.load());

        loader.setController(controller);
        final Parent root = loader.load();
        wireMockServer.stop();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @Test
    public void testSubmitEmptyFields() {
        writeQuestion("?", "1", "", "3", "4", 0);
        Node dialogPane = lookup(".dialog-pane").query();
        Assertions.assertDoesNotThrow(() -> {
            from(dialogPane).lookup((Text t) -> t.getText().startsWith("Du må fylle ut alle feltene!")).query();
        });
    }

    @Test
    public void testSubmitEmptyQuestion() {
        writeQuestion("", "1", "2", "3", "4", 0);
        Node dialogPane = lookup(".dialog-pane").query();
        Assertions.assertDoesNotThrow(() -> {
            from(dialogPane).lookup((Text t) -> t.getText().startsWith("Du må skrive inn et spørsmål")).query();
        });
    }

    @Test
    public void testSubmitQuestion() {
        config = WireMockConfiguration.wireMockConfig().port(8080);
        wireMockServer = new WireMockServer(config.portNumber());
        wireMockServer.start();
        WireMock.configureFor("localhost", config.portNumber());
        stubFor(post(urlEqualTo("/api/quizzes/a"))
                .withRequestBody(equalToJson("{\"question\":\"?\",\"answer\":0,\"choices\":[\"a\",\"b\",\"c\",\"d\"]}"))
                .willReturn(aResponse()
                        .withBody("[]")
                        .withStatus(200)));
        stubFor(get(urlEqualTo("/api/quizzes/a"))
                .willReturn(aResponse()
                        .withBody("{\"name\":\"a\",\"questions\":[{\"question\":\"?\",\"answer\":0,\"choices\":[\"a\",\"b \",\"c \",\"d \"]}]}")
                        .withStatus(200)));
        writeQuestion("?", "a", "b", "c", "d", 0);
    }

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

    @AfterEach
    public void stopServer() {
        if (wireMockServer != null)
            wireMockServer.stop();
    }
}