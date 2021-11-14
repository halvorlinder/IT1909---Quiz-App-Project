package io.internal;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import core.Question;
import core.User;
import core.UserData;
import core.UserRecord;

import java.io.IOException;

public class UserSerializer extends JsonSerializer<UserRecord> {
    @Override
    public void serialize(UserRecord userRecord, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeStringField("username", userRecord.getUsername());
            jsonGenerator.writeNumberField("password",userRecord.getPassword());
            jsonGenerator.writeEndObject();
    }
}
