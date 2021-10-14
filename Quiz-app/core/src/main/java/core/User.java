package core;

public final class User {
    private static String username;

    public static void setUserName(String name) {
        username = name;
    }

    public String getUserName() {
        return username;
    }
}
