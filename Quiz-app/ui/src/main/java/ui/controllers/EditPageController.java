package ui.controllers;

import core.Question;
import core.Quiz;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import ui.APIClientService;
import ui.App;
import ui.Utilities;

import java.io.IOException;

public class EditPageController {
    @FXML
    private Label titleText;
    @FXML
    private VBox questionList;

    private final String quizName;
    private Quiz quiz;
    private APIClientService apiClientService;

    /**
     * @param quizName the name of the quiz to be edited
     */
    public EditPageController(String quizName) {
        this.quizName = quizName;
    }

    /**
     * initializes the page by filling in question rows and displaying name
     *
     * @throws IOException
     */
    @FXML
    private void initialize() throws IOException, InterruptedException {
        apiClientService = new APIClientService();
        display();
    }

    private void display() throws IOException, InterruptedException {
        questionList.getChildren().clear();
        quiz = apiClientService.getQuiz(quizName);
        titleText.setText("Endre " + quizName);
        for (int i = 0; i < quiz.getQuizLength(); i++) {
            addQuestionElement(i);
        }
    }

    /**
     * adds a gui element representing a question from the quiz
     *
     * @param questionId the id of the question
     */
    private void addQuestionElement(int questionId) {
        Question question = quiz.getQuestions().get(questionId);
        GridPane gridPane = new GridPane();
        ColumnConstraints column1 = new ColumnConstraints(300);
        ColumnConstraints column2 = new ColumnConstraints(100);
        ColumnConstraints column3 = new ColumnConstraints(100);
        gridPane.getColumnConstraints().addAll(column1, column2, column3);
        Label name = new Label();
        name.setText(question.getQuestion());
        gridPane.add(name, 0, 0, 1, 1);
        gridPane.getStyleClass().add("light-grid");

        Button editButton = new Button();
        editButton.setText("Endre");
        editButton.getStyleClass().add("green-button");
        editButton.setOnAction((ActionEvent ae) -> {
            showEditQuestion(questionId, question);
        });
        gridPane.add(editButton, 1, 0, 1, 1);

        Button deleteButton = new Button();
        deleteButton.setText("Slett");
        deleteButton.getStyleClass().add("red-button");
        deleteButton.setOnAction((ActionEvent ae) -> {
            deleteQuestion(questionId);
        });
        gridPane.add(deleteButton, 2, 0, 1, 1);

        questionList.getChildren().add(gridPane);
    }

    private void deleteQuestion(int questionId) {
        try {
            apiClientService.deleteQuestion(quizName, questionId);
            display();
        } catch (Exception e) {
            Utilities.alertUser();
        }
    }

    private void showEditQuestion(int questionId, Question question) {
        try {
            FXMLLoader loader = App.getFXMLLoader("NewQuestion.fxml");
            loader.setController(new NewQuestionController(quizName, questionId, question));
            titleText.getScene().setRoot(loader.load());
        } catch (IOException ioException) {
            ioException.printStackTrace();
            Utilities.alertUser();
        }
    }


    /**
     * renders the new question page
     */
    @FXML
    private void showNewQuestion() {
        try {
            FXMLLoader loader = App.getFXMLLoader("NewQuestion.fxml");
            loader.setController(new NewQuestionController(quizName));
            titleText.getScene().setRoot(loader.load());
        } catch (IOException ioException) {
            ioException.printStackTrace();
            Utilities.alertUser();
        }
    }

    @FXML
    private void deleteQuiz() throws IOException {
        try {
            apiClientService.deleteQuiz(quizName);
            // apiClientService.deleteLeaderboard(quizName);
        } catch (Exception e) {
            Utilities.alertUser();
        }
        questionList.getScene().setRoot(Utilities.getFXMLLoader("HomePage.fxml").load());
    }

}
