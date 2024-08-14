package com.dmitriyevseyev.carmanagerspringboot.utils.strategy;

import com.dmitriyevseyev.carmanagerspringboot.utils.ExportDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@AllArgsConstructor
public class JSONDeserSer {
    private ObjectMapper objectMapper;

    public ExportDTO deserialization(String json) {
        ExportDTO exportDTO = null;
        try {
            exportDTO = objectMapper.readValue(json, ExportDTO.class);
        } catch (IOException e) {
            System.out.println("JSONDeserialization. " + e.getMessage());
        }
        return exportDTO;
    }

    public String serialization(ExportDTO exportDTO) throws JsonProcessingException {

        System.out.println("JSONDeserSer, serialization - " + exportDTO);

        return objectMapper.writeValueAsString(exportDTO);
    }
}

