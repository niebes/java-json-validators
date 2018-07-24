import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import com.fasterxml.jackson.module.jsonSchema.customProperties.ValidationSchemaFactoryWrapper;
import com.fasterxml.jackson.module.jsonSchema.factories.SchemaFactoryWrapper;
import com.fasterxml.jackson.module.jsonSchema.types.ArraySchema;
import com.fasterxml.jackson.module.jsonSchema.types.IntegerSchema;
import com.fasterxml.jackson.module.jsonSchema.types.ObjectSchema;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import net.niebes.random.pack.age.SomeValidatedObject;
import net.niebes.validation.HibernateValidationConstraintResolver;
import net.niebes.validation.JavaxValidationResolver;
import net.niebes.validation.ValidationConstraintResolverChain;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONCompareResult;
import org.skyscreamer.jsonassert.comparator.DefaultComparator;
import org.skyscreamer.jsonassert.comparator.JSONComparator;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class JsonSchemaTest {
    private static final JSONComparator JSON_COMPARATOR = new DefaultComparator(
            JSONCompareMode.LENIENT);

    @Test
    public void annotationReachesSchema() throws JsonMappingException {
        ValidationSchemaFactoryWrapper visitor = new ValidationSchemaFactoryWrapper();
        JsonSchema schemaFromGenerator = getSchemaFromGenerator(SomeValidatedObject.class, visitor);
        JsonSchema schemaFromVisitor = getSchemaFromVisitor(SomeValidatedObject.class, visitor);

        assertThat(schemaFromGenerator, equalTo(schemaFromVisitor));
        ObjectSchema objectSchema = schemaFromGenerator.asObjectSchema();
        IntegerSchema age = objectSchema.getProperties().get("intWithMin").asIntegerSchema();
        assertThat(age.getMinimum(), is(1d));
    }

    @Test
    public void chainedAnnotationResolverMapFields() throws JsonProcessingException {
        ValidationSchemaFactoryWrapper visitor = new ValidationSchemaFactoryWrapper(ValidationConstraintResolverChain.of(
                new JavaxValidationResolver(),
                new HibernateValidationConstraintResolver())
        );
        JsonSchema schemaFromGenerator = getSchemaFromGenerator(SomeValidatedObject.class, visitor);

        assertThat(schemaFromGenerator.getType(), equalTo(JsonFormatTypes.OBJECT));
        ObjectSchema objectSchema = schemaFromGenerator.asObjectSchema();
        Map<String, JsonSchema> properties = objectSchema.getProperties();
        IntegerSchema intWithMin = properties.get("intWithMin").asIntegerSchema();
        ArraySchema objWithSizeMinAndMax = properties.get("objWithSizeMinAndMax").asArraySchema();
        assertThat(intWithMin.getMinimum(), is(1d));
        assertThat(objWithSizeMinAndMax.getMinItems(), is(12));
        assertThat(objWithSizeMinAndMax.getMaxItems(), is(150));
        //assertThat(properties.get("stringBigIntMapWithSize").asObjectSchema().getAdditionalProperties(), is(150));
        compareWithDocumentation(schemaFromGenerator);

    }

    private void compareWithDocumentation(JsonSchema schemaFromGenerator) {
        try {

            String generatedSchema = getAsString(schemaFromGenerator);
            String documentedSchema = getFromNamespace(schemaFromGenerator.getId());
            JSONCompareResult jsonCompareResult = compareJSON(documentedSchema, generatedSchema);
            if (jsonCompareResult.failed()){
                fail("comparison failed: " + jsonCompareResult);
            }

        } catch (IOException | JSONException e) {
            fail("exception: " + e.getMessage());
        }
    }

    private JSONCompareResult compareJSON(String documentedSchema, String generatedSchema) throws JSONException {
        return JSON_COMPARATOR.compareJSON(new JSONObject(generatedSchema), new JSONObject(documentedSchema));
    }

    private String getFileContent(String pathname) throws IOException {
        return Files.asCharSource(new File(pathname), Charsets.UTF_8).read();
    }

    private String getFromNamespace(String id) throws IOException {
        return getFileContent(getFilePathFromNamespace(id));
    }

    private String getFilePathFromNamespace(String id) {
        String prefix = "src/test/resources/";
        String content = id.replace("urn:jsonschema:", "").replace(':', '/');
        String suffix = ".json";
        return prefix + content + suffix;
    }

    private String getAsString(JsonSchema schema) throws JsonProcessingException {
        return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(schema);
    }

    private JsonSchema getSchemaFromGenerator(Class<?> type, SchemaFactoryWrapper schemaFactoryWrapper) throws JsonMappingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonSchemaGenerator schemaGen = new JsonSchemaGenerator(mapper, schemaFactoryWrapper);
        return schemaGen.generateSchema(type);
    }

    private JsonSchema getSchemaFromVisitor(Class<?> type, SchemaFactoryWrapper schemaFactoryWrapper) throws JsonMappingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.acceptJsonFormatVisitor(type, schemaFactoryWrapper);
        return schemaFactoryWrapper.finalSchema();
    }

}
