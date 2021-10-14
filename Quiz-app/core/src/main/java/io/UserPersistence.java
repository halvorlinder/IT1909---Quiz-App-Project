package io;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import core.Quiz;
import io.internal.QuizAppModule;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public final class UserPersistence {
    private final ObjectMapper mapper;
    private static final String BASE_PATH = System.getProperty("user.home") + "/QuizApp/";

    /**
     * Inits a new QuizPersistence Object
     *
     * @throws IOException
     */
    public UserPersistence() throws IOException {
        mapper = createObjectMapper();
        if (!Files.exists(Path.of(BASE_PATH))) {
            Files.createDirectory(Path.of(BASE_PATH));
        }
    }

    /**
     * @return an ObjectMapper for handling quizzes
     */
    public static ObjectMapper createObjectMapper() {
        return new ObjectMapper().registerModule(createJacksonModule());
    }

    /**
     * @return a new QuizAppModule
     */
    public static SimpleModule createJacksonModule() {
        return new QuizAppModule();
    }

//    /**
//     * @param reader a Reader containing a file with a Quiz
//     * @return a Quiz object read from the Reader
//     * @throws IOException
//     */
//    public Quiz readQuiz(Reader reader) throws IOException {
//        return mapper.readValue(reader, Quiz.class);
//    }
//
//    /**
//     * writes a Quiz object to the file
//     *
//     * @param quiz   the Quiz to be written
//     * @param writer the Writer containing the file
//     * @throws IOException
//     */
//    public void writeQuiz(Quiz quiz, Writer writer) throws IOException {
//        mapper.writerWithDefaultPrettyPrinter().writeValue(writer, quiz);
//    }
//
//    /**
//     * Loads a QuizAppModule from the saved file (saveFilePath) in the user.home folder.
//     *
//     * @param quizName the name of the quiz
//     * @return the loaded QuizAppModule
//     */
//    public Quiz loadQuiz(String quizName) throws IOException {
//        try (Reader reader = new FileReader(BASE_PATH + quizName + ".json", StandardCharsets.UTF_8)) {
//            return readQuiz(reader);
//        }
//    }
//
//    /**
//     * Saves a Quiz to the saveFilePath in the user.home folder.
//     *
//     * @param quiz the quiz to save
//     */
//    public void saveQuiz(Quiz quiz) throws IOException {
//        String quizName = quiz.getName();
//
//        try (Writer writer = new FileWriter(BASE_PATH + quizName + ".json", StandardCharsets.UTF_8)) {
//            writeQuiz(quiz, writer);
//        }
//    }
    
}
