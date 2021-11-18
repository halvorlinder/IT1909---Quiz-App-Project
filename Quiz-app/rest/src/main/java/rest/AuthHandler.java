package rest;

import core.Quiz;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class AuthHandler {
    private final Map<String, String> userToTokenMapping;
    private final SecureRandom secureRandom = new SecureRandom();
    private final Base64.Encoder base64Encoder = Base64.getUrlEncoder();

    /**
     *
     */
    public AuthHandler() {
        userToTokenMapping = new HashMap<>();
    }

    /**
     * stores a token on a given username
     *
     * @param username the username of the user
     * @return the token
     */
    public String registerAndGetToken(String username) {
        userToTokenMapping.put(username, getRandomToken());
        return userToTokenMapping.get(username);
    }

    /**
     * @param username the username of the user
     * @return the token associated with the user
     */
    public String getToken(String username) {
        return userToTokenMapping.get(username);
    }

    /**
     * @return a random auth token
     */
    private String getRandomToken() {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }

    /**
     * @param token the access token
     * @param quiz  the quiz in question
     * @return checks if the requester has write access to the quiz in question
     */
    public boolean hasAccess(String token, Quiz quiz) {
        return userToTokenMapping.containsKey(quiz.getCreator()) &&
                token.equals(getToken(quiz.getCreator()));
    }
}
