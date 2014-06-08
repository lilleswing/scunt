package com.lilleswing.scunt.core.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.lilleswing.scunt.core.DbModel;

import java.io.IOException;
import java.util.Collection;

public class IdSerializer extends JsonSerializer<Collection<DbModel>> {
    @Override
    public void serialize(Collection<DbModel> dbModels,
                          JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeStartArray();
        for (final DbModel dbModel : dbModels) {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeNumberField("id", dbModel.getId());
            jsonGenerator.writeEndObject();
        }
        jsonGenerator.writeEndArray();
    }
}
