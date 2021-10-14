package io.internal;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import core.Question;
import core.User;
import core.UserData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UserDataDeserializer extends JsonDeserializer<UserData> {
    @Override
    public UserData deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return deserialize(jsonParser.getCodec().readTree(jsonParser));
    }

    UserData deserialize(JsonNode jsonNode) {
        UserData userData = new UserData();
        if (jsonNode instanceof ObjectNode objectNode) {
            for (Iterator<String> it = objectNode.fieldNames(); it.hasNext(); ) {
                String userName = it.next();
                int passwordHash = objectNode.get(userName).asInt();
                userData.reAddUser(userName, passwordHash);
            }
            return new UserData();

        }
        return null;
    }
}
