import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class PluginGeneratedSchemaTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    protected JsonNode getJsonNodeFromFile(String filePath) throws IOException {

        return getJsonNodeFromStream(new FileInputStream(filePath));
    }

    protected JsonNode getJsonNodeFromStream(InputStream resourceAsStream) throws IOException {
        return MAPPER.readTree(resourceAsStream);
    }

    @Test
    public void generatedSchemaIsEqualToCopy() throws IOException {
        final JsonNode expectedSchema = getJsonNodeFromFile("src/test/resources/schema-copy.json");
        final JsonNode generatedSchema = getJsonNodeFromFile("target/schema.json");
        assertThat(generatedSchema, equalTo(expectedSchema));

    }
}
