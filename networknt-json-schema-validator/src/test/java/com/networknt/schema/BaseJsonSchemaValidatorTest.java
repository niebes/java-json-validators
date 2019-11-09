package com.networknt.schema;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class BaseJsonSchemaValidatorTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    protected JsonNode getJsonNodeFromClasspath(String resourceUrl) throws IOException {
        return MAPPER.readTree(getResourceAsStream(resourceUrl));
    }

    protected JsonNode getJsonNodeFromStringContent(String string) throws IOException {
        return MAPPER.readTree(string);
    }

    protected JsonNode getJsonNodeFromUrl(String url) throws IOException {
        return MAPPER.readTree(new URL(url));
    }

    private InputStream getResourceAsStream(String name) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(name);
    }

    protected JsonSchema getJsonSchemaFromClasspath(String name) {
        JsonSchemaFactory factory = JsonSchemaFactory.getInstance();
        return factory.getSchema(getResourceAsStream(name));
    }


    protected JsonSchema getJsonSchemaFromStringContent(String schemaContent) {
        JsonSchemaFactory factory = JsonSchemaFactory.getInstance();
        return factory.getSchema(schemaContent);
    }

    protected JsonSchema getJsonSchemaFromUrl(String url) {
        JsonSchemaFactory factory = JsonSchemaFactory.getInstance();
        return factory.getSchema(url);
    }

    protected JsonSchema getJsonSchemaFromJsonNode(JsonNode jsonNode) {
        JsonSchemaFactory factory = JsonSchemaFactory.getInstance();
        return factory.getSchema(jsonNode);
    }
}
