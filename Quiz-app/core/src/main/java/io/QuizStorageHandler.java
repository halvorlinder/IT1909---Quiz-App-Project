package io;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.Question;
import core.Quiz;

import java.io.File;
import java.io.IOException;
import java.util.List;



public class QuizStorageHandler {
    private final File file;

    public QuizStorageHandler(String quizName){
        file = new File(System.getProperty("user.home")+"/QuizApp/"+quizName+".json");
    }

    public void writeQuestion(Question question, String quizName) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Quiz quiz = getQuiz(quizName);
        List<Question> questions = quiz.getQuestions();
        questions.add(question);
        objectMapper.writeValue(file, questions);
    }

    public Quiz getQuiz(String quizName) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(System.getProperty("user.home")+"/QuizApp/"+quizName+".json", Quiz.class);
    }
}
