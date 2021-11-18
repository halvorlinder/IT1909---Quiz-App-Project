package io;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import core.UserData;
import io.internal.QuizAppModule;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public final class UserPersistence {
    private final ObjectMapper mapper;
    private final String fileName = "users.json";
    private final String basePath = SavePaths.getBasePath();

    /**
     * Inits a new QuizPersistence Object
     *
     * @throws IOException
     */
    public UserPersistence() throws IOException {
        mapper = createObjectMapper();
        if (!Files.exists(Path.of(basePath))) {
            Files.createDirectory(Path.of(basePath));
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

    /**
     * @param reader a Reader containing a file with a Quiz
     * @return a Quiz object read from the Reader
     * @throws IOException
     */
    public UserData readUserData(Reader reader) throws IOException {
        return mapper.readValue(reader, UserData.class);
    }

    /**
     * writes a Quiz object to the file
     *
     * @param userData the Quiz to be written
     * @param writer   the Writer containing the file
     * @throws IOException
     */
    public void writeUserData(UserData userData, Writer writer) throws IOException {
        mapper.writerWithDefaultPrettyPrinter().writeValue(writer, userData);
    }

    /**
     * Loads a QuizAppModule from the saved file (saveFilePath) in the user.home folder.
     *
     * @return the loaded QuizAppModule
     */
    public UserData loadUserData() throws IOException {
        File file = new File(basePath + fileName);
        if (!file.exists()) {
            if (!file.createNewFile())
                throw new IOException();
            UserData userData = new UserData();
            saveUserData(userData);
            return userData;
        }
        try (Reader reader = new FileReader(basePath + fileName, StandardCharsets.UTF_8)) {
            return readUserData(reader);
        }
    }

    /**
     * Saves a Quiz to the saveFilePath in the user.home folder.
     *
     * @param userData the quiz to save
     */
    public void saveUserData(UserData userData) throws IOException {
        File file = new File(basePath + fileName);
        if (!file.exists()) {
            if (!file.createNewFile())
                throw new IOException();
        }
        try (Writer writer = new FileWriter(basePath + fileName, StandardCharsets.UTF_8)) {
            writeUserData(userData, writer);
        }
    }

}
