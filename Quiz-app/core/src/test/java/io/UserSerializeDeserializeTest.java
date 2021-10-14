package io;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.Quiz;
import core.UserData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.TestHelpers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class UserSerializeDeserializeTest {
    private static ObjectMapper mapper;

    @BeforeAll
    public static void setUp() {
        mapper = QuizPersistence.createObjectMapper();
    }

    final static String usersWithTwoEntries =
            """
                    {
                       "user1": 2944 ,
                       "user2": 16256
                     }
                     """;

    @Test
    public void testSerializers() {
        UserData userData = new UserData();
        userData.attemptRegister("user1", "password");
        userData.attemptRegister("user2", "pWord");
        try {
            assertEquals(usersWithTwoEntries.replaceAll("\\s+", ""), mapper.writeValueAsString(userData));
        } catch (JsonProcessingException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testDeserializers() {
        UserData userData = new UserData();
        userData.attemptRegister("user1", "password");
        userData.attemptRegister("user2", "pWord");
        try {
            UserData userData1 = mapper.readValue(usersWithTwoEntries, UserData.class);
            checkUserData(userData, userData1);
        } catch (JsonProcessingException e) {
            fail(e.getMessage());
        }
    }
}
