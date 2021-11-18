package rest;

import core.Quiz;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

public class AuthHandlerTest {
    private AuthHandler authHandler;

    @BeforeEach
    public void setup() {
        authHandler = new AuthHandler();
    }

    @Test
    public void testRegisterAndGetToken() {
        for (int i = 0; i < 1000; i++) {
            String token = authHandler.registerAndGetToken("username");
            assertTrue(Pattern.matches("[0-9a-zA-Z\\-_]{32}", token));
            assertEquals(token, authHandler.getToken("username"));
        }
    }

    @Test
    public void testHasAccess() {
        Quiz quiz1 = new Quiz("quiz1", new ArrayList<>(), "username");
        Quiz quiz2 = new Quiz("quiz2", new ArrayList<>(), "name");
        String token1 = authHandler.registerAndGetToken("username");
        String token2 = authHandler.registerAndGetToken("name");
        assertTrue(authHandler.hasAccess(token1, quiz1));
        assertFalse(authHandler.hasAccess(token1, quiz2));
        assertFalse(authHandler.hasAccess(token2, quiz1));
        assertTrue(authHandler.hasAccess(token2, quiz2));
    }
}
