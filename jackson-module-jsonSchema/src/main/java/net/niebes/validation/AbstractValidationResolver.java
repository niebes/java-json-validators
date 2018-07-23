package net.niebes.validation;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.module.jsonSchema.validation.ValidationConstraintResolver;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;

public class AbstractValidationResolver extends ValidationConstraintResolver {

    <A extends Annotation> Function<BeanProperty, Optional<Boolean>> hasAnnotation(Class<A> annotation) {
        return property -> getAnnotation(property, annotation).map(ignored -> true);
    }


    <A extends Annotation> Optional<A> getAnnotation(BeanProperty prop, Class<A> annotation) {
        return Optional.ofNullable(prop.getAnnotation(annotation));
    }

    @SafeVarargs
    final <Type> Type firstOrNull(BeanProperty prop, Function<BeanProperty, Optional<Type>>... functions) {
        return Arrays.stream(functions)
                .map(func -> func.apply(prop))
                .filter(Optional::isPresent)
                .findFirst()
                .map(Optional::get)
                .orElse(null);
    }


    @Override
    public Integer getArrayMaxItems(BeanProperty prop) {
        return null;
    }

    @Override
    public Integer getArrayMinItems(BeanProperty prop) {
        return null;
    }

    @Override
    public Double getNumberMaximum(BeanProperty prop) {
        return null;
    }

    @Override
    public Double getNumberMinimum(BeanProperty prop) {
        return null;
    }

    @Override
    public Integer getStringMaxLength(BeanProperty prop) {
        return null;
    }

    @Override
    public Integer getStringMinLength(BeanProperty prop) {
        return null;
    }

    @Override
    public String getStringPattern(BeanProperty prop) {
        return null;
    }

    @Override
    public Boolean getRequired(BeanProperty prop) {
        return null;
    }
}
