package ui.controllers;

import core.Question;
import core.Quiz;
import core.User;
import io.QuizPersistence;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import ui.App;
import ui.Utilities;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public final class HomePageController {

    private static final String BASE_PATH = System.getProperty("user.home") + "/QuizApp/Quizzes/";

    @FXML
    private Button addNewQuizButton;

    @FXML
    private Label nameDisplay;

    @FXML
    private VBox quizList;

    @FXML
    private TextField quizNameField;

    private List<String> quizzes = new ArrayList<>();


    /**
     * sets the text to display username
     */
    @FXML
    public void initialize() {
        nameDisplay.setText("Logget inn som " + User.getUserName());
        updateInitialQuizzes();
    }

    private void updateInitialQuizzes() {
        File[] files = new File(BASE_PATH).listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            if (file.isFile()) {
                String quizName  = file.getName().replaceFirst("[.][^.]+$", "");
                quizzes.add(quizName);
                addQuizElement(quizName);
            }
        }
    }

    private void addQuizElement(String quizName) {
        GridPane gridPane = new GridPane();
        ColumnConstraints column1 = new ColumnConstraints(300);
        ColumnConstraints column2 = new ColumnConstraints(100);
        ColumnConstraints column3 = new ColumnConstraints(100);
        ColumnConstraints column4 = new ColumnConstraints(100);
        gridPane.getColumnConstraints().addAll(column1, column2, column3, column4);
        Label name = new Label();
        name.setText(quizName);
        gridPane.add(name, 0, 0, 1, 1);

        Button playButton = new Button();
        playButton.setText("Spill");
        playButton.setOnAction((ActionEvent ae) -> startQuiz(quizName));
        gridPane.add(playButton, 1, 0, 1, 1);

        Button editButton = new Button();
        editButton.setText("Endre");
        gridPane.add(editButton, 2, 0, 1, 1);

        Button leaderboardButton = new Button();
        leaderboardButton.setText("Ledertavle");
        gridPane.add(leaderboardButton, 3, 0, 1, 1);

        quizList.getChildren().add(gridPane);
    }

    /**
     * Sets the current root to be the question page
     *
     * @param quizName
     * @throws IOException
     */
    @FXML
    public void startQuiz(String quizName) { // Switch scene to StartQuiz
        QuizPersistence quizPersistence = null;
        try {
            quizPersistence = new QuizPersistence();
            Quiz quiz = quizPersistence.loadQuiz(quizName);
            if (quiz.getQuizLength() == 0) {
                Utilities.alertUser("Denne quizen har ingen spørsmål");
                return;
            }
            FXMLLoader loader = App.getFXMLLoader("QuestionPage.fxml");
            QuizController controller = new QuizController(quiz);
            loader.setController(controller);
            quizList.getScene().setRoot(loader.load());
        } catch (IOException ioException) {
            Utilities.alertUser();
        }
    }

    /**
     * Sets the current root to be the new question page
     *
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    public void showNewQuestion(ActionEvent actionEvent) throws IOException { // Switch scene to StartQuiz
        // TODO: get name if quiz from button
        String currentQuiz = "oskar-spesial";
        System.out.println(currentQuiz);
        if (currentQuiz == null) {
            throw new IllegalStateException("No quiz selected");
        }
        FXMLLoader loader = App.getFXMLLoader("NewQuestion.fxml");
        NewQuestionController controller = new NewQuestionController(currentQuiz);
        loader.setController(controller);
        ((Node) actionEvent.getSource()).getScene().setRoot(loader.load());
    }

    /**
     * Sets the current root to be the leaderboard page
     *
     * @throws IOException
     */
    @FXML
    public void showLeaderboard() throws IOException { // Switch scene to StartQuiz
        //newQuestionButton.getScene().setRoot(Utilities.getFXMLLoader(".fxml").load());
    }

    /**
     * Creates a new quiz file with a given name and displays it in the app
     *
     * @throws IOException
     */


    @FXML
    public void addNewQuizFile() throws IOException {
        String newQuizName = quizNameField.getText();
        if (newQuizName.isEmpty()) {
            throw new IllegalArgumentException("You can't create a quiz with an empty name");
        }
        List<Question> noQuestions = new ArrayList<>();
        Quiz newQuiz = new Quiz(newQuizName, noQuestions);
        QuizPersistence quizPersistence = new QuizPersistence();
        quizPersistence.saveQuiz(newQuiz);
        addQuizElement(newQuizName);
        quizzes.add(newQuizName);
    }

    /**
     * signs out of the application
     * @param actionEvent
     */
    @FXML
    public void signOut(ActionEvent actionEvent) {
        try {
            ((Node) actionEvent.getSource()).getScene().setRoot(Utilities.getFXMLLoader("LogInPage.fxml").load());
            User.setUserName(null);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
