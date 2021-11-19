package io.internal;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import core.Question;
import core.Quiz;

import java.io.IOException;

/**
 * This class serializes a Quiz object into a JSON file
 */
class QuizSerializer extends JsonSerializer<Quiz> {

    @Override
    public void serialize(Quiz quiz, JsonGenerator jsonGen, SerializerProvider serializerProvider)
            throws IOException {
        jsonGen.writeStartObject();
        jsonGen.writeStringField("name", quiz.getName());
        jsonGen.writeStringField("creator", quiz.getCreator());
        jsonGen.writeArrayFieldStart("questions");
        for (Question question : quiz.getQuestions()) {
            jsonGen.writeObject(question);
        }
        jsonGen.writeEndArray();
        jsonGen.writeEndObject();
    }

}

