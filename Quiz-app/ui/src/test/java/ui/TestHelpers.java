package ui;

import io.SavePaths;

import java.io.IOException;
import java.nio.file.Files;

public class TestHelpers {

    public static void deleteQuiz(String name) {
        String fileName = SavePaths.getBasePath() + "Quizzes/"+name+".json";
        try {
            Files.delete(java.nio.file.Paths.get(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
