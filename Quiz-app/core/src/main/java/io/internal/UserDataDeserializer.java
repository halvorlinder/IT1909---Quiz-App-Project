package io.internal;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import core.UserData;
import core.UserRecord;

import java.io.IOException;
import java.util.Iterator;

class UserDataDeserializer extends JsonDeserializer<UserData> {

    @Override
    public UserData deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        return deserialize(jsonParser.getCodec().readTree(jsonParser));
    }

    UserData deserialize(JsonNode jsonNode) {
        UserData userData = new UserData();
        if (jsonNode instanceof ObjectNode objectNode) {
            for (Iterator<String> it = objectNode.fieldNames(); it.hasNext(); ) {
                String username = it.next();
                int passwordHash = objectNode.get(username).asInt();
                userData.addUser(new UserRecord(username, passwordHash));
            }
            return userData;

        }
        return null;
    }
}
