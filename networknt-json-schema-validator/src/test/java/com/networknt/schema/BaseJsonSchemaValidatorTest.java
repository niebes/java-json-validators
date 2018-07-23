package com.networknt.schema;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.net.URL;

public class BaseJsonSchemaValidatorTest {
    protected JsonNode getJsonNodeFromClasspath(String name) throws Exception {
        InputStream is1 = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(name);

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(is1);
    }

    protected JsonNode getJsonNodeFromStringContent(String content) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(content);
    }

    protected JsonNode getJsonNodeFromUrl(String url) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(new URL(url));
    }

    protected JsonSchema getJsonSchemaFromClasspath(String name) {
        JsonSchemaFactory factory = JsonSchemaFactory.getInstance();
        InputStream is = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(name);
        return factory.getSchema(is);
    }


    protected JsonSchema getJsonSchemaFromStringContent(String schemaContent) {
        JsonSchemaFactory factory = JsonSchemaFactory.getInstance();
        return factory.getSchema(schemaContent);
    }

    protected JsonSchema getJsonSchemaFromUrl(String url) throws Exception {
        JsonSchemaFactory factory = JsonSchemaFactory.getInstance();
        return factory.getSchema(new URL(url));
    }

    protected JsonSchema getJsonSchemaFromJsonNode(JsonNode jsonNode) {
        JsonSchemaFactory factory = JsonSchemaFactory.getInstance();
        return factory.getSchema(jsonNode);
    }
}
