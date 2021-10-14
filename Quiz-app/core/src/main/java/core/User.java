package core;

public abstract class User {
    private static String userName;

    public static void setUserName(String name) {
        userName = name;
    }

    public String getUserName() {
        return userName;
    }
}
