package core;

import java.util.HashMap;
import java.util.OptionalInt;

public class UserData {
    private final HashMap<String, Integer> users = new HashMap<>();

    public UserData(HashMap<String, Integer> userMap) {
        for (String name : userMap.keySet()) {
            users.put(name, userMap.get(name));
        }
    }

    public boolean userExists(String username) {
        return users.containsKey(username);
    }

    public boolean userNameAndPasswordCorrelate(String userName, String password) {
        return userExists(userName) && users.get(userName) == hash(password);
    }

    public void addUser(String userName, String password) {
        if (users.containsKey(userName))
            throw new IllegalStateException("The user already exists");
        users.put(userName, hash(password));
    }

    private int hash(String password) {
        OptionalInt optionalInt = password.chars().reduce((x, y) -> (x * y) % 16384);
        return optionalInt.isPresent() ? optionalInt.getAsInt() : 0;
    }

}
