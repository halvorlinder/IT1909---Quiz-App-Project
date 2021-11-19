package io.internal;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import core.UserRecord;

import java.io.IOException;

class UserRecordDeserializer extends JsonDeserializer<UserRecord> {

    @Override
    public UserRecord deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        return deserialize(jsonParser.getCodec().readTree(jsonParser));
    }

    UserRecord deserialize(JsonNode jsonNode) {
        if (jsonNode instanceof ObjectNode objectNode) {
            return new UserRecord(objectNode.get("username").asText(), objectNode.get("password").asInt());

        }
        return null;
    }
}
