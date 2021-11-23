package core;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * This class represents a dictionary of users, mapping their name to their hashed password
 */
public final class UserData {
    private final Map<String, Integer> users = new HashMap<>();
    private static final int HASH_MODULUS = 16384;

    /**
     * creates an empty UserDataObject
     */
    public UserData() {

    }

    /**
     * @return a set containing all current usernames
     */
    public Set<String> getUserNames() {
        return users.keySet();
    }

    /**
     * @param username the user who's password hash is returned
     * @return the password hash of the user
     */
    public int getPasswordHash(String username) {
        if (!users.containsKey(username))
            throw new NoSuchElementException("No such user");
        return users.get(username);
    }

    /**
     * @param username the username to be checked
     * @return true is the username exists, false otherwise
     */
    public boolean userExists(String username) {
        return users.containsKey(username);
    }

    /**
     * @param userRecord the username and password to be checked
     * @return true if the combination of username and password exists, false otherwise
     */
    public boolean attemptLogIn(UserRecord userRecord) {
        return userExists(userRecord.getUsername()) && users.get(userRecord.getUsername()) == userRecord.getPassword();
    }

    /**
     * @param userRecord the username and password to be registered
     * @return true if the registration was successful, false otherwise
     */
    public boolean attemptRegister(UserRecord userRecord) {
        if (userExists(userRecord.getUsername()))
            return false;
        addUser(userRecord);
        return true;
    }

    /**
     * adds a user to the object
     *
     * @param userRecord the username and password of the user
     */
    public void addUser(UserRecord userRecord) {
        users.put(userRecord.getUsername(), userRecord.getPassword());
    }

    /**
     * @param password the password to be hashed
     * @return the hash of the password
     */
    public static int hash(String password) {
        if (password.length() == 0)
            return 0;
        return password.chars().reduce(1, (x, y) -> (x * y) % HASH_MODULUS);
    }

}
