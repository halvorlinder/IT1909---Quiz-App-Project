package io.internal;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import core.UserRecord;
import io.constants.JsonKeys;

import java.io.IOException;

/**
 * This class deserializes a JSON file to a UserRecord object
 */
class UserRecordDeserializer extends JsonDeserializer<UserRecord> {

    @Override
    public UserRecord deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        return deserialize(jsonParser.getCodec().readTree(jsonParser));
    }

    UserRecord deserialize(JsonNode jsonNode) {
        if (jsonNode instanceof ObjectNode objectNode) {
            return new UserRecord(objectNode.get(JsonKeys.USER_NAME).asText(),
                    objectNode.get(JsonKeys.USER_PASSWORD).asInt());

        }
        return null;
    }
}
