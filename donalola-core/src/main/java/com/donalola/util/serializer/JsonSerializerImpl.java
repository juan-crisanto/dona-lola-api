package com.donalola.util.serializer;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class JsonSerializerImpl implements JsonSerializer {

    private final ObjectMapper objectMapper;

    public JsonSerializerImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public String objectToJson(Object oject) {
        try {
            return objectMapper.writeValueAsString(oject);
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}