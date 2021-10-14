package core;

public final class User {
    private static String username;

    private User() {

    }

    public static void setUserName(String name) {
        username = name;
    }

    public static String getUserName() {
        return username;
    }
}
