package io.internal;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import core.Question;

import java.io.IOException;

/**
 * This class serializes a Question object into a JSON file
 */
class QuestionSerializer extends JsonSerializer<Question> {

    @Override
    public void serialize(Question question, JsonGenerator jsonGen, SerializerProvider serializerProvider)
            throws IOException {
        jsonGen.writeStartObject();
        jsonGen.writeStringField("question", question.getQuestion());
        jsonGen.writeNumberField("answer", question.getAnswer());
        jsonGen.writeArrayFieldStart("choices");
        for (String choice : question.getChoices()) {
            jsonGen.writeObject(choice);
        }
        jsonGen.writeEndArray();
        jsonGen.writeEndObject();
    }

}

