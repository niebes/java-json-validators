package net.niebes.random.pack.age;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public class SomeValidatedObject {
    /**
     * by default only validation-api is supported. specifically the annotation are Size, Max, DecimalMax, Min, DecimalMin, Pattern, NotNull
     * those can be overridden by ValidationSchemaFactoryWrapper(new ValidationConstraintResolver{})
     * @see com.fasterxml.jackson.module.jsonSchema.validation.AnnotationConstraintResolver
     */
    @NotNull
    private String stringWithNotNull;
    @Size(min = 2)
    private String stringWithSizeMin;
    private String string;
    @Size(min = 2)
    private Map<String, BigInteger> stringBigIntMapWithSize;
    @Min(1)
    private int intWithMin;
    @Size(min = 12, max = 150)
    private List<Object> objWithSizeMinAndMax;

    public List<Object> getObjWithSizeMinAndMax() {
        return objWithSizeMinAndMax;
    }

    public String getStringWithNotNull() {
        return stringWithNotNull;
    }

    public String getStringWithSizeMin() {
        return stringWithSizeMin;
    }

    public String getString() {
        return string;
    }

    public int getIntWithMin() {
        return intWithMin;
    }


    public Map<String, BigInteger> getStringBigIntMapWithSize() {
        return stringBigIntMapWithSize;
    }
}
