package ui;

import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SystemTest {
    private static Process serverProcess;
    @BeforeAll
    public static void startServer() throws IOException {
        Path pathToRest = Paths.get("../rest");
        ProcessBuilder builder = new ProcessBuilder("dir")
                .redirectErrorStream(true)
                .directory(pathToRest.toFile());

        serverProcess = builder.start();
    }

    @AfterAll
    public static void stopServer(){

        InputStream stdout = serverProcess.getInputStream ();
        BufferedReader reader = new BufferedReader (new InputStreamReader(stdout));


        reader.lines().forEachOrdered(System.out::println);
        serverProcess.destroy();
    }


    @Test
    public void testServerRuns() throws IOException {
        startServer();
        APIClientService apiClientService = new APIClientService();
        Assertions.assertDoesNotThrow(apiClientService::getListOfQuizNames);
        stopServer();
    }
}
