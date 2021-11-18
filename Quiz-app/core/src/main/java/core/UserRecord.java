package core;

public class UserRecord {

    private final String username;
    private final int passwordHash;

    /**
     * constructs a UserRecord with a username and password string
     *
     * @param username the username
     * @param password the password
     */
    public UserRecord(String username, String password) {
        this.username = username;
        this.passwordHash = UserData.hash(password);
    }

    /**
     * constructs a UserRecord with a username and password int
     *
     * @param username     the username
     * @param passwordHash the password
     */
    public UserRecord(String username, int passwordHash) {
        this.username = username;
        this.passwordHash = passwordHash;
    }

    /**
     * @return the hashed password
     */
    public int getPassword() {
        return passwordHash;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }


}
