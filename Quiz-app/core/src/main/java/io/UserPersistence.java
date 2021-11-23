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
    private static final String FILE_NAME = "users.json";
    private final String basePath;

    /**
     * Inits a new UserPersistence Object
     *
     * @throws IOException
     */
    public UserPersistence() throws IOException {
        basePath = SavePaths.getBasePath();
        mapper = createObjectMapper();
        if (!Files.exists(Path.of(basePath))) {
            Files.createDirectory(Path.of(basePath));
        }
    }

    /**
     * @return an ObjectMapper for handling UserData
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
     * @param reader a Reader containing a file with UserData
     * @return a UserData object read from the Reader
     * @throws IOException
     */
    public UserData readUserData(Reader reader) throws IOException {
        return mapper.readValue(reader, UserData.class);
    }

    /**
     * writes a UserData object to the file
     *
     * @param userData the UserData to be written
     * @param writer   the Writer containing the file
     * @throws IOException
     */
    public void writeUserData(UserData userData, Writer writer) throws IOException {
        mapper.writerWithDefaultPrettyPrinter().writeValue(writer, userData);
    }

    /**
     * Loads UserData from the saved file (saveFilePath) in the user.home folder.
     *
     * @return the loaded UserData
     */
    public UserData loadUserData() throws IOException {
        File file = new File(basePath + FILE_NAME);
        if (!file.exists()) {
            if (!file.createNewFile())
                throw new IOException();
            UserData userData = new UserData();
            saveUserData(userData);
            return userData;
        }
        try (Reader reader = new FileReader(basePath + FILE_NAME, StandardCharsets.UTF_8)) {
            return readUserData(reader);
        }
    }

    /**
     * Saves UserData to the saveFilePath in the user.home folder.
     *
     * @param userData the UserData to save
     */
    public void saveUserData(UserData userData) throws IOException {
        File file = new File(basePath + FILE_NAME);
        if (!file.exists()) {
            if (!file.createNewFile())
                throw new IOException();
        }
        try (Writer writer = new FileWriter(basePath + FILE_NAME, StandardCharsets.UTF_8)) {
            writeUserData(userData, writer);
        }
    }
}
