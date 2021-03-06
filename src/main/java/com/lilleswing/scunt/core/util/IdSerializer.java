package com.lilleswing.scunt.core.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.lilleswing.scunt.core.DbModel;

import java.io.IOException;

public class IdSerializer extends JsonSerializer<DbModel> {
    @Override
    public void serialize(DbModel dbModel,
                          JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", dbModel.getId());
        jsonGenerator.writeEndObject();
    }
}
