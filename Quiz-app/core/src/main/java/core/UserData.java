package core;

import java.util.HashMap;
import java.util.List;
import java.util.OptionalInt;
import java.util.Set;

public class UserData {
    private final HashMap<String, Integer> users = new HashMap<>();

    public UserData(HashMap<String, Integer> userMap) {
        for (String name : userMap.keySet()) {
            users.put(name, userMap.get(name));
        }
    }

    public UserData() {

    }

    public Set<String> getUserNames() {
        return users.keySet();
    }

    public int getPasswordHash(String userName) {
        if (!users.containsKey(userName))
            throw new IllegalStateException("No such user");
        return users.get(userName);
    }

    public boolean userExists(String username) {
        return users.containsKey(username);
    }

    public boolean userNameAndPasswordCorrelate(String userName, String password) {
        return userExists(userName) && users.get(userName) == hash(password);
    }

    public void addUser(String userName, String password) {
        if (userExists(userName))
            throw new IllegalStateException("The user already exists");
        users.put(userName, hash(password));
    }

    public void reAddUser(String userName, int password){
        if (userExists(userName))
            throw new IllegalStateException("The user already exists");
        users.put(userName, password);
    }

    private int hash(String password) {
        OptionalInt optionalInt = password.chars().reduce((x, y) -> (x * y) % 16384);
        return optionalInt.isPresent() ? optionalInt.getAsInt() : 0;
    }

}
