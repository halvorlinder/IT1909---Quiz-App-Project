package ui;

public class User {
    private final String username;
    private final String accessToken;

    /**
     * @param username the username
     */
    public User(String username, String accessToken) {
        this.username = username;
        this.accessToken = accessToken;
    }

    /**
     * @return the username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return the user's accessToken
     */
    public String getAccessToken() {
        return accessToken;
    }
}
