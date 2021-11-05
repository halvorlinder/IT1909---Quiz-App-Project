package io;

/**
 * This class exists to ease testing of features that require file handling by allowing
 * globally setting the saving path to QuizApp/test
 */
public final class SavePaths {
    private static final String ABSOLUTE_BASE_PATH = System.getProperty("user.home") + "/QuizApp/";
    private static String basePathExtension = "";

    private SavePaths() {

    }

    public static String getBasePath() {
        return ABSOLUTE_BASE_PATH + basePathExtension;
    }

    /**
     * ONLY USE FOR TESTS
     * Sets the default file save directory to the test directory
     */
    public static void enableTestMode() {
        SavePaths.basePathExtension = "test/";
    }
}
