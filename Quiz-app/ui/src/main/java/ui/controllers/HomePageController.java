package ui.controllers;

import core.Question;
import core.Quiz;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import ui.*;
import ui.constants.Errors;
import ui.constants.FilePaths;
import ui.constants.Style;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public final class HomePageController extends BaseController implements InitializableController {

    @FXML
    private Button addNewQuizButton;

    @FXML
    private Label nameDisplay;

    @FXML
    private VBox quizList;

    @FXML
    private TextField quizNameField;

    private APIClientService apiClientService;

    /**
     * @param user the current user
     */
    public HomePageController(User user) {
        super(user);
    }

    /**
     * sets the text to display username
     */
    @FXML
    public void initialize() {
        nameDisplay.setText("Logget inn som " + getUser().getUsername());
        apiClientService = new APIClientService();
        updateInitialQuizzes();
    }

    /**
     * adds all quiz names to a list so that they can be rendered
     */
    private void updateInitialQuizzes() {
        quizList.getChildren().clear();
        List<String> quizzes = null;
        try {
            quizzes = apiClientService.getListOfQuizNames();
        } catch (IOException ignored) {
            return;
        }
        for (String quizName : quizzes) {
            addQuizElement(quizName);
        }
    }

    /**
     * displays a quiz as a row on the page
     *
     * @param quizName the name of the quiz
     */
    private void addQuizElement(String quizName) {
        GridPane gridPane = new GridPane();
        ColumnConstraints column1 = new ColumnConstraints(Style.TABLE_SPACE_LARGE);
        ColumnConstraints column2 = new ColumnConstraints(Style.TABLE_SPACE_SMALL);
        ColumnConstraints column3 = new ColumnConstraints(Style.TABLE_SPACE_SMALL);
        ColumnConstraints column4 = new ColumnConstraints(Style.TABLE_SPACE_SMALL);
        gridPane.getColumnConstraints().addAll(column1, column2, column3, column4);
        gridPane.getStyleClass().add("light-grid");
        Label name = new Label();
        name.setText(quizName);
        gridPane.add(name, 0, 0, 1, 1);

        Button playButton = new Button();
        playButton.setText("Spill");
        playButton.setOnAction((ActionEvent ae) -> startQuiz(quizName));
        playButton.getStyleClass().add("green-button");
        gridPane.add(playButton, 1, 0, 1, 1);

        Button editButton = new Button();
        editButton.setText("Endre");
        editButton.setOnAction((ActionEvent ae) -> showEditPage(quizName));
        editButton.getStyleClass().add("red-button");
        gridPane.add(editButton, 2, 0, 1, 1);

        Button leaderboardButton = new Button();
        leaderboardButton.setText("Ledertavle");
        leaderboardButton.getStyleClass().add("blue-button");
        leaderboardButton.setOnAction((ActionEvent ae) -> showLeaderboardPage(quizName));
        gridPane.add(leaderboardButton, 3, 0, 1, 1);

        quizList.getChildren().add(gridPane);
    }

    /**
     * displays the edit page for a given quiz
     *
     * @param quizName the name of the quiz
     */
    private void showEditPage(String quizName) {

        FXMLLoader loader = null;
        try {
            loader = App.getFXMLLoader(FilePaths.EDIT_PAGE);
            EditPageController controller = new EditPageController(quizName, getUser());
            loader.setController(controller);
            controller.setPreviousPageInfo(this, getScene().getRoot());
        } catch (IOException ioException) {
            Utilities.alertUser(Errors.LOAD_PAGE);
            return;
        }
        try {
            Parent root = loader.load();
            getScene().setRoot(root);
        } catch (IOException ignored) {
            Utilities.alertUser();
        }
    }

    /**
     * displays the leaderboard page for a given quiz
     *
     * @param quizName the name of the quiz
     */
    private void showLeaderboardPage(String quizName) {
        FXMLLoader loader = null;
        try {
            loader = App.getFXMLLoader(FilePaths.LEADERBOARD_PAGE);
            LeaderboardPageController controller = new LeaderboardPageController(quizName, getUser());
            loader.setController(controller);
            controller.setPreviousPageInfo(this, getScene().getRoot());
        } catch (IOException ignored) {
            Utilities.alertUser(Errors.LOAD_PAGE);
            return;
        }
        try {
            Parent root = loader.load();
            getScene().setRoot(root);
        } catch (IOException ignored) {
        }
    }

    /**
     * Sets the current root to be the question page
     *
     * @param quizName the name of the quiz to be played
     */
    @FXML
    public void startQuiz(String quizName) {
        FXMLLoader loader = null;
        try {
            Quiz quiz = apiClientService.getQuiz(quizName);
            if (quiz.getQuizLength() == 0) {
                Utilities.alertUser(Errors.EMPTY_QUIZ);
                return;
            }
            loader = App.getFXMLLoader(FilePaths.QUIZ_PAGE);
            QuizPageController controller = new QuizPageController(quizName, getUser());
            loader.setController(controller);
        } catch (Exception e) {
            Utilities.alertUser(Errors.LOAD_PAGE);
            return;
        }
        try {
            Parent root = loader.load();
            getScene().setRoot(root);
        } catch (IOException ignored) {
        }
    }


    /**
     * Creates a new quiz file with a given name and displays it in the app
     *
     * @throws IOException
     */
    @FXML
    public void addNewQuizFile() {
        String newQuizName = quizNameField.getText().strip().replaceAll(" ", "\\$");
        if (newQuizName.isEmpty()) {
            Utilities.alertUser(Errors.EMPTY_NAME);
            return;
        }
        List<Question> noQuestions = new ArrayList<>();
        Quiz newQuiz = new Quiz(newQuizName, noQuestions, getUser().getUsername());

        try {
            apiClientService.postQuiz(newQuiz);
        } catch (IOException ioException) {
            return;
        }
        updateInitialQuizzes();
    }

    /**
     * signs out of the application
     *
     * @param actionEvent
     */
    @FXML
    public void signOut(ActionEvent actionEvent) {
        try {
            final FXMLLoader loader = Utilities.getFXMLLoader(FilePaths.LOGIN_PAGE);
            LoginPageController controller = new LoginPageController();
            loader.setController(controller);
            final Parent root = loader.load();
            ((Node) actionEvent.getSource()).getScene().setRoot(root);
        } catch (IOException ioException) {
            Utilities.alertUser(Errors.LOG_OUT);
        }
    }

    /**
     * @return the current scene
     */
    private Scene getScene() {
        return quizList.getScene();
    }
}
