package io.internal;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import core.UserData;

import java.io.IOException;

class UserDataSerializer extends JsonSerializer<UserData> {

    @Override
    public void serialize(UserData userData, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException {
        jsonGenerator.writeStartObject();
        for (String name : userData.getUserNames()) {
            jsonGenerator.writeNumberField(name, userData.getPasswordHash(name));
        }
        jsonGenerator.writeEndObject();
    }
}
