package io;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.UserRecord;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.TestHelpers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class UserRecordSerializeDeserializeTest {
    private static ObjectMapper mapper;

    @BeforeAll
    public static void setUp() {
        mapper = QuizPersistence.createObjectMapper();
    }

    final static String userWithPasswordHash =
            """
                    {
                       "username": "user1",
                       "password": 1234
                     }
                     """;

    @Test
    public void testSerializers() {
        UserRecord userRecord = new UserRecord("user1", 1234);
        try {
            assertEquals(userWithPasswordHash.replaceAll("\\s+", ""),
                    mapper.writeValueAsString(userRecord));
        } catch (JsonProcessingException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testDeserializers() {
        UserRecord userRecord = new UserRecord("user1", 1234);
        try {
            UserRecord userRecord1 = mapper.readValue(userWithPasswordHash, UserRecord.class);
            checkUserRecord(userRecord, userRecord1);
        } catch (JsonProcessingException e) {
            fail(e.getMessage());
        }
    }
}
