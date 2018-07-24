import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.github.fge.jsonschema.main.JsonValidator;
import org.junit.Test;

import java.io.IOException;

import static com.github.fge.jackson.JsonLoader.fromPath;
import static com.github.fge.jackson.JsonLoader.fromString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class JsonValidatorTest {

    @Test
    public void validPerson() throws IOException, ProcessingException {
        JsonNode person = fromString(
                "{" +
                        "  \"firstName\": \"John\"," +
                        "  \"lastName\": \"Doe\"," +
                        "  \"age\": 21" +
                        "}");
        JsonNode personSchema = fromPath("src/test/resources/person-schema.json");
        ProcessingReport validate = validate(person, personSchema);
        assertThat(validate.isSuccess(), is(true));
    }

    @Test
    public void personInvalidAge() throws IOException, ProcessingException {
        JsonNode person = fromString(
                "{" +
                        "  \"firstName\": \"John\"," +
                        "  \"lastName\": \"Doe\"," +
                        "  \"age\": -21" +
                        "}");
        JsonNode personSchema = fromPath("src/test/resources/person-schema.json");
        ProcessingReport validate = validate(person, personSchema);
        assertThat(validate.isSuccess(), is(false));
    }

    @Test
    public void personInvalidDataType() throws IOException, ProcessingException {
        JsonNode person = fromString(
                "{" +
                        "  \"firstName\": \"John\"," +
                        "  \"lastName\": \"Doe\"," +
                        "  \"age\": \"21\"" +
                        "}");
        JsonNode personSchema = fromPath("src/test/resources/person-schema.json");
        ProcessingReport validate = validate(person, personSchema);
        assertThat(validate.isSuccess(), is(false));
    }

    private ProcessingReport validate(JsonNode requestDataJsonNode, JsonNode schemaNode) throws ProcessingException {
        final JsonSchemaFactory factory = JsonSchemaFactory.byDefault();
        final JsonValidator validator = factory.getValidator();
        return validator.validate(schemaNode, requestDataJsonNode);
    }

    @Test
    public void defaultSetup() throws ProcessingException, IOException {
        JsonNode schemaNode = JsonLoader.fromPath("src/test/resources/person-schema.json");
        JsonNode data = JsonLoader.fromPath("src/test/resources/person.json");
        JsonSchemaFactory schemaFactory = JsonSchemaFactory.byDefault();
        JsonSchema schema = schemaFactory.getJsonSchema(schemaNode);
        ProcessingReport report = schema.validate(data);
        assertThat(report.isSuccess(), is(true));

    }

}

