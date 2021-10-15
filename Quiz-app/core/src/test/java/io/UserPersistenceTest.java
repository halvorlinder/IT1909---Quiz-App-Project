package io;

import core.Quiz;
import core.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import static io.TestHelpers.checkUserData;
import static io.TestHelpers.createUserDataWithTwoEntries;
import static org.junit.jupiter.api.Assertions.fail;

public class UserPersistenceTest {

    private UserPersistence userPersistence;

    @BeforeEach
    public void setup() throws IOException {
        userPersistence = new UserPersistence();
    }

    @Test
    public void testSerializersDeserializers() {
        UserData userData = createUserDataWithTwoEntries();
        try {
            StringWriter writer = new StringWriter();
            userPersistence.writeUserData(userData, writer);
            String json = writer.toString();
            UserData userData1 = userPersistence.readUserData(new StringReader(json));
            checkUserData(userData, userData1);
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }
}

