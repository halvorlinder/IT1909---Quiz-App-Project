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

    public AuthHandler(){
        userToTokenMapping = new HashMap<>();
    }

    public void addNewToken(String username){
        userToTokenMapping.put(username, getRandomToken());
    }

    private String getToken(String username){
        return userToTokenMapping.get(username);
    }

    private String getRandomToken() {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }

    public boolean hasAccess(String token, Quiz quiz){
        return userToTokenMapping.containsKey(quiz.getCreator()) &&
                token.equals(userToTokenMapping.get(quiz.getCreator()));
    }
}
