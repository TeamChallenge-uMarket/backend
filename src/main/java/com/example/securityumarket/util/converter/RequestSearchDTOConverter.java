package com.example.securityumarket.util.converter;

import com.example.securityumarket.models.DTO.pages.catalog.request.RequestSearchDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RequestSearchDTOConverter implements AttributeConverter<RequestSearchDTO, String> { //TODO DELETE
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(RequestSearchDTO attribute) {
        try {
            return mapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public RequestSearchDTO convertToEntityAttribute(String dbData) {
        try {
            return mapper.readValue(dbData, RequestSearchDTO.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
