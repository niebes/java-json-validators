import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.github.fge.jsonschema.main.JsonValidator;

import java.io.IOException;

import static com.github.fge.jackson.JsonLoader.fromString;

public class JsonValidatorTest {


    boolean isValid(String schema, String requestData) throws IOException, ProcessingException {

        try {
            ProcessingReport processingReport = validate(schema, requestData);
            if (processingReport != null) {
                return processingReport.isSuccess();
            }

        } catch (Exception ignored) {
        }
        return false;
    }

    private ProcessingReport validate(String schema, String requestData) throws IOException, ProcessingException {
        final JsonNode requestDataJsonNode = fromString(requestData);
        final JsonNode schemaNode = fromString(schema);
        final JsonSchemaFactory factory = JsonSchemaFactory.byDefault();
        final JsonValidator validator = factory.getValidator();
        return validator.validate(schemaNode, requestDataJsonNode);
    }

}

