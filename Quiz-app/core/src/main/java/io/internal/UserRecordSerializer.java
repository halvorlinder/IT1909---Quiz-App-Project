package io.internal;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import core.UserRecord;
import io.constants.JsonKeys;

import java.io.IOException;

/**
 * This class serializes a UserRecord object into a JSON file
 */
class UserRecordSerializer extends JsonSerializer<UserRecord> {

    @Override
    public void serialize(UserRecord userRecord, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField(JsonKeys.USER_NAME, userRecord.getUsername());
        jsonGenerator.writeNumberField(JsonKeys.USER_PASSWORD, userRecord.getPassword());
        jsonGenerator.writeEndObject();
    }
}
