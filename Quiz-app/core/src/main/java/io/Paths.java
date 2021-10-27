package io;

public final class Paths {
    private static final String ABSOLUTE_BASE_PATH = System.getProperty("user.home") + "/QuizApp/";
    private static String basePathExtension = "";

    private Paths() {

    }

    public static String getBasePath() {
        return ABSOLUTE_BASE_PATH + basePathExtension;
    }

    public static void setBasePath(String basePathExtension) {
        Paths.basePathExtension = basePathExtension + "/";
    }
}
