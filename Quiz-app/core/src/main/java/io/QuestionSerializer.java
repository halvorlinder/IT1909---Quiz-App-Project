package io;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import core.Question;

import java.io.IOException;

class QuestionSerializer extends JsonSerializer<Question> {

    /*
     * format: { "text": "...", "checked": false, "deadline": ... }
     */

    @Override
    public void serialize(Question question, JsonGenerator jsonGen, SerializerProvider serializerProvider)
            throws IOException {
        jsonGen.writeStartObject();
        jsonGen.writeStringField("questionText", question.getQuestion());
        jsonGen.writeNumberField("correctAnswer", question.getAnswer());
        jsonGen.writeArrayFieldStart("choices");
        for (String choice : question.getChoices()) {
            jsonGen.writeObject(choice);
        }
        jsonGen.writeEndArray();
        jsonGen.writeEndObject();
    }

}

