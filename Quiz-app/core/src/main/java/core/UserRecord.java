package core;

public class UserRecord {

    private final String username;
    private final int passwordHash;

    public UserRecord(String username, String password){
        this.username = username;
        this.passwordHash  = UserData.hash(password);
    }

    public UserRecord(String username, int passwordHash){
        this.username = username;
        this.passwordHash = passwordHash;
    }

    public int getPassword() {
        return passwordHash;
    }

    public String getUsername() {
        return username;
    }


}
