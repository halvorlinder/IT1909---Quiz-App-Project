package core;

import java.util.HashMap;
import java.util.OptionalInt;
import java.util.Set;

public final class UserData {
    private final HashMap<String, Integer> users = new HashMap<>();

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
            throw new IllegalStateException("No such user");
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
     * @param username the username to be checked
     * @param password the password to be checked
     * @return true if the combination of username and password exists, false otherwise
     */
    public boolean attemptLogIn(String username, String password) {
        return userExists(username) && users.get(username) == hash(password);
    }

    /**
     *
     * @param username the username to be registered
     * @param password the password to be registered
     * @return true if the registration was successful, false otherwise
     */
    public boolean attemptRegister(String username, String password) {
        if (userExists(username))
            return false;
        addUser(username, password);
        return true;
    }

    /**
     * adds a user to the object
     *
     * @param username the username of the user
     * @param password the password of the user
     */
    private void addUser(String username, String password) {
        users.put(username, hash(password));
    }

    /**
     * adds a user given a password and a username
     *
     * @param username the username of the user
     * @param password the password of the user
     */
    public void reAddUser(String username, int password) {
        users.put(username, password);
    }


    /**
     * @param password the password to be hashed
     * @return the hash of the password
     */
    public int hash(String password) {
        OptionalInt optionalInt = password.chars().reduce((x, y) -> (x * y) % 16384);
        return optionalInt.isPresent() ? optionalInt.getAsInt() : 0;
    }

}
