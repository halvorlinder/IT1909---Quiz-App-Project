package core;

import java.util.HashMap;
import java.util.OptionalInt;
import java.util.Set;

public class UserData {
    private final HashMap<String, Integer> users = new HashMap<>();

    /**
     * creates a UserDataObject
     * @param userMap a Hashmap mapping usernames to password hashes
     */
    public UserData(HashMap<String, Integer> userMap) {
        for (String name : userMap.keySet()) {
            users.put(name, userMap.get(name));
        }
    }

    /**
     * creates an empty UserDataObject
     */
    public UserData() {

    }

    /**
     *
     * @return a set containing all current usernames
     */
    public Set<String> getUserNames() {
        return users.keySet();
    }

    /**
     *
     * @param userName the user who's password hash is returned
     * @return the password hash of the user
     */
    public int getPasswordHash(String userName) {
        if (!users.containsKey(userName))
            throw new IllegalStateException("No such user");
        return users.get(userName);
    }

    /**
     *
     * @param username the username to be checked
     * @return true is the username exists, false otherwise
     */
    public boolean userExists(String username) {
        return users.containsKey(username);
    }

    /**
     *
     * @param userName the username to be checked
     * @param password the password to be checked
     * @return true if the combination of username and password exists, false otherwise
     */
    public boolean userNameAndPasswordCorrelate(String userName, String password) {
        return userExists(userName) && users.get(userName) == hash(password);
    }

    /**
     * adds a user to the object
     * @param userName the username of the user
     * @param password the password of the user
     */
    public void addUser(String userName, String password) {
        if (userExists(userName))
            throw new IllegalStateException("The user already exists");
        users.put(userName, hash(password));
    }

    /**
     * adds a user given a password and a username
     * @param userName the username of the user
     * @param password the password of the user
     */
    public void reAddUser(String userName, int password) {
        if (userExists(userName))
            throw new IllegalStateException("The user already exists");
        users.put(userName, password);
    }

    /**
     *
     * @param password the password to be hashed
     * @return the hash of the password
     */
    private int hash(String password) {
        OptionalInt optionalInt = password.chars().reduce((x, y) -> (x * y) % 16384);
        return optionalInt.isPresent() ? optionalInt.getAsInt() : 0;
    }

}
