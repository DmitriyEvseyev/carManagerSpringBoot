package com.dmitriyevseyev.carmanagerspringboot.utils;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
//import com.networknt.schema.JsonSchema;
//import com.networknt.schema.JsonSchemaFactory;
//import com.networknt.schema.SpecVersion;
//import com.networknt.schema.ValidationMessage;
//import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Set;


@Component
public class JsonValidator {

//    public String isValidImport(String json) throws IOException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        JsonSchemaFactory schemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V4);
//
//        File schemaFile = new File(ServletConstants.PATH_SCHEMA_JSON);
//        String schema = FileUtils.readFileToString(schemaFile, StandardCharsets.UTF_8);
//
//        JsonNode jsonNode = objectMapper.readTree(json);
//        JsonSchema jsonSchema = schemaFactory.getSchema(schema);
//        Set<ValidationMessage> validationResult = jsonSchema.validate(jsonNode);
//
//        StringBuilder error = new StringBuilder();
//        validationResult.forEach(vm -> error.append(vm.getMessage()));
//
//
//        System.out.println("JJJJJJJKsonValidator" + error);
//
//
//        return error.toString();
//    }
}
