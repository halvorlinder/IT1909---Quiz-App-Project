package io;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.Quiz;
import io.internal.QuizAppModule;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class QuizPersistence {
    private final ObjectMapper mapper;
    private final static String basePath = System.getProperty("user.home") + "/QuizApp/";

    public QuizPersistence() throws IOException {
        mapper = new ObjectMapper().registerModule(new QuizAppModule());
        if(!Files.exists(Path.of(basePath))){
            Files.createDirectory(Path.of(basePath));
        }
    }

    public Quiz readQuiz(Reader reader) throws IOException {
        return mapper.readValue(reader, Quiz.class);
    }

    public void writeQuiz(Quiz quiz, Writer writer) throws IOException {
        mapper.writerWithDefaultPrettyPrinter().writeValue(writer, quiz);
    }

    /**
     * Loads a QuizAppModule from the saved file (saveFilePath) in the user.home folder.
     *
     * @return the loaded QuizAppModule
     */
    public Quiz loadQuiz(String quizName) throws IOException, IllegalStateException {
        try (Reader reader = new FileReader(basePath+quizName+".json", StandardCharsets.UTF_8)) {
            return readQuiz(reader);
        }
    }

    /**
     * Saves a Quiz to the saveFilePath in the user.home folder.
     *
     * @param quiz the quiz to save
     */
    public void saveQuiz(Quiz quiz) throws IOException, IllegalStateException {
        String quizName = quiz.getName();

        try (Writer writer = new FileWriter(basePath+quizName+".json", StandardCharsets.UTF_8)) {
            writeQuiz(quiz, writer);
        }
    }

}
