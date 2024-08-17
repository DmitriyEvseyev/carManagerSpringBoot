package com.dmitriyevseyev.carmanagerspringboot.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

public class JsonValidator {
    public static JsonValidator instance;

    public static JsonValidator getInstance() {
        if (instance == null) {
            instance = new JsonValidator();
        }
        return instance;
    }

    private JsonValidator() {
    }

    public String isValidImport(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonSchemaFactory schemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V4);
        String schema = Files.readString(Path.of(Constants.PATH_TO_SCHEMA_JSON));

        System.out.println("111 isValidImport");

        JsonNode jsonNode = objectMapper.readTree(json);
        JsonSchema jsonSchema = schemaFactory.getSchema(schema);



        Set<ValidationMessage> validationResult = jsonSchema.validate(jsonNode);

        StringBuilder error = new StringBuilder();
        validationResult.forEach(vm -> error.append(vm.getMessage()));


        System.out.println("JJJJJJJKsonValidator" + error);


        return error.toString();
    }
}
