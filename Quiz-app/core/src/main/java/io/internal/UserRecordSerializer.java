package io.internal;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import core.UserRecord;

import java.io.IOException;

public class UserRecordSerializer extends JsonSerializer<UserRecord> {
    /**
     * serializes a UserRecord
     * @param userRecord the UserRecord
     * @param jsonGenerator
     * @param serializerProvider
     * @throws IOException
     */
    @Override
    public void serialize(UserRecord userRecord, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("username", userRecord.getUsername());
        jsonGenerator.writeNumberField("password", userRecord.getPassword());
        jsonGenerator.writeEndObject();
    }
}
