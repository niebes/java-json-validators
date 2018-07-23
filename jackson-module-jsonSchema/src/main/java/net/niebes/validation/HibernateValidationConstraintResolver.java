package net.niebes.validation;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.module.jsonSchema.validation.AnnotationConstraintResolver;

public class HibernateValidationConstraintResolver extends AnnotationConstraintResolver {
    public Integer getArrayMaxItems(BeanProperty prop) {
        return super.getArrayMaxItems(prop);
    }

    public Integer getArrayMinItems(BeanProperty prop) {
        return super.getArrayMinItems(prop);
    }

    public Double getNumberMaximum(BeanProperty prop) {
        return super.getNumberMaximum(prop);
    }

    public Double getNumberMinimum(BeanProperty prop) {
        return super.getNumberMinimum(prop);
    }

    public Integer getStringMaxLength(BeanProperty prop) {
        return super.getStringMaxLength(prop);
    }

    public Integer getStringMinLength(BeanProperty prop) {
        return super.getStringMinLength(prop);
    }

    public String getStringPattern(BeanProperty prop) {
        return super.getStringPattern(prop);
    }

    public Boolean getRequired(BeanProperty prop) {
        return super.getRequired(prop);
    }

}
