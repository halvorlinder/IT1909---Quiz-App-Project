package rest;

import core.Quiz;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

public class AuthHandlerTest {
    private AuthHandler authHandler;
    private final String username1 = "username";

    /**
     * Initalize new AuthHandler
     */
    @BeforeEach
    public void setup() {
        authHandler = new AuthHandler();
    }

    /**
     * Test creating and getting new tokens
     */
    @Test
    public void testRegisterAndGetToken() {
        for (int i = 0; i < 1000; i++) {
            String token = authHandler.registerAndGetToken(username1);
            assertTrue(Pattern.matches("[0-9a-zA-Z\\-_]{32}", token));
            assertEquals(token, authHandler.getToken(username1));
        }
    }

    /**
     * Test if users have access to quizzes they created and
     * verify that they don't have access to quizzes they didn't create
     */
    @Test
    public void testHasAccess() {
        String username2 = "name";
        Quiz quiz1 = new Quiz("quiz1", new ArrayList<>(), username1);
        Quiz quiz2 = new Quiz("quiz2", new ArrayList<>(), username2);
        String token1 = authHandler.registerAndGetToken(username1);
        String token2 = authHandler.registerAndGetToken(username2);
        assertTrue(authHandler.hasAccess(token1, quiz1));
        assertFalse(authHandler.hasAccess(token1, quiz2));
        assertFalse(authHandler.hasAccess(token2, quiz1));
        assertTrue(authHandler.hasAccess(token2, quiz2));
    }
}
