import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import com.fasterxml.jackson.module.jsonSchema.customProperties.ValidationSchemaFactoryWrapper;
import com.fasterxml.jackson.module.jsonSchema.factories.SchemaFactoryWrapper;
import com.fasterxml.jackson.module.jsonSchema.types.IntegerSchema;
import com.fasterxml.jackson.module.jsonSchema.types.ObjectSchema;
import org.junit.Test;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class JsonSchemaTest {
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

    class SomeValidatedObject {
        /**
         * by default only validation-api is supported. specifically the annotation are Size, Max, DecimalMax, Min, DecimalMin, Pattern, NotNull
         * those can be overridden by ValidationSchemaFactoryWrapper(new ValidationConstraintResolver{})
         * see https://github.com/FasterXML/jackson-module-jsonSchema/blob/master/src/main/java/com/fasterxml/jackson/module/jsonSchema/validation/AnnotationConstraintResolver.java
         *
         */
        @NotNull
        private String stringWithNotNull;
        @Size(min = 2)
        private String stringWithSizeMin;
        @Min(1)
        private String stringWithMin;
        @Min(1)
        private int intWithMin;
        @Size(min = 0, max = 150)
        private int intWithSizeMinAndMax;

        public String getStringWithNotNull() {
            return stringWithNotNull;
        }

        public String getStringWithSizeMin() {
            return stringWithSizeMin;
        }

        public String getStringWithMin() {
            return stringWithMin;
        }

        public int getIntWithMin() {
            return intWithMin;
        }

        public int getIntWithSizeMinAndMax() {
            return intWithSizeMinAndMax;
        }


    }
}
