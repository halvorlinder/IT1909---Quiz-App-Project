package core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserDataTest {
    UserData userData;
    UserRecord userRecord1 = new UserRecord("user1", "pWord");
    UserRecord userRecord2 = new UserRecord("user2", "pWord");

    @BeforeEach
    public void setup() {
        userData = new UserData();
        userData.attemptRegister(userRecord1);
    }

    @Test
    public void testAttemptRegister() {
        assertFalse(userData.attemptRegister(userRecord1));
        assertTrue(userData.attemptRegister(userRecord2));
        assertEquals(userData.getUserNames().size(), 2);
    }

    @Test
    public void testAttemptLogIn() {
        assertFalse(userData.attemptLogIn(userRecord1));
        assertFalse(userData.attemptLogIn(userRecord2));
        assertTrue(userData.attemptLogIn(userRecord1));
    }

    @Test
    public void testHash() {
        assertEquals(userData.hash(""), 0);
        UserRecord userRecord3 = new UserRecord("user2", "password");
        userData.attemptRegister(userRecord3);
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
