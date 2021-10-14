package core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserDataTest {
    UserData userData;

    @BeforeEach
    public void setup() {
        userData = new UserData();
        userData.attemptRegister("user1", "pWord");
    }

    @Test
    public void testAttemptRegister() {
        assertFalse(userData.attemptRegister("user1", "pWord1"));
        assertTrue(userData.attemptRegister("user2", "pWord"));
        assertEquals(userData.getUserNames().size(), 2);
    }

    @Test
    public void testAttemptLogIn() {
        assertFalse(userData.attemptLogIn("user1", "pWord1"));
        assertFalse(userData.attemptLogIn("user2", "pWord"));
        assertTrue(userData.attemptLogIn("user1", "pWord"));
    }

    @Test
    public void testHash() {
        assertEquals(userData.hash(""), 0);
        userData.attemptRegister("user2", "password");
        assertEquals(userData.hash("password"), userData.getPasswordHash("user2"));
        assertThrows(IllegalStateException.class, () ->
                userData.getPasswordHash("user3"));
        System.out.println(userData.hash("password"));
        System.out.println(userData.hash("pWord"));
    }

    @Test
    public void testReAddUser() {
        userData.reAddUser("user2", 197);
        assertEquals(197, userData.getPasswordHash("user2"));
    }


}
