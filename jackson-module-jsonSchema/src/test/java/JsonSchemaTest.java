import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import org.jsonschema.Person;
import org.junit.Test;

public class JsonSchemaTest {
    @Test
    public void test() throws JsonMappingException {
        ObjectMapper mapper = new ObjectMapper();
        // configure mapper, if necessary, then create schema generator
        JsonSchemaGenerator schemaGen = new JsonSchemaGenerator(mapper);
        JsonSchema schema = schemaGen.generateSchema(Person.class);

    }
}
