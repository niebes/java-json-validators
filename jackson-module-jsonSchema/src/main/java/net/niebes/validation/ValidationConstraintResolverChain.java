package net.niebes.validation;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.module.jsonSchema.validation.ValidationConstraintResolver;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class ValidationConstraintResolverChain extends ValidationConstraintResolver {
    private final List<ValidationConstraintResolver> resolvers;

    public ValidationConstraintResolverChain(List<ValidationConstraintResolver> resolvers) {
        this.resolvers = Collections.unmodifiableList(resolvers);
    }

    public static ValidationConstraintResolverChain of(ValidationConstraintResolver... resolvers) {
        return new ValidationConstraintResolverChain(Arrays.asList(resolvers));
    }

    private <Type> Type firstOrNull(Function<ValidationConstraintResolver, Type> function) {
        return resolvers.stream()
                .map(function)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Integer getArrayMaxItems(BeanProperty prop) {
        return firstOrNull(resolver -> resolver.getArrayMaxItems(prop));
    }

    @Override
    public Integer getArrayMinItems(BeanProperty prop) {
        return firstOrNull(resolver -> resolver.getArrayMinItems(prop));
    }

    @Override
    public Double getNumberMaximum(BeanProperty prop) {
        return firstOrNull(resolver -> resolver.getNumberMaximum(prop));
    }

    @Override
    public Double getNumberMinimum(BeanProperty prop) {
        return firstOrNull(resolver -> resolver.getNumberMinimum(prop));
    }

    @Override
    public Integer getStringMaxLength(BeanProperty prop) {
        return firstOrNull(resolver -> resolver.getStringMaxLength(prop));
    }

    @Override
    public Integer getStringMinLength(BeanProperty prop) {
        return firstOrNull(resolver -> resolver.getStringMinLength(prop));
    }

    @Override
    public String getStringPattern(BeanProperty prop) {
        return firstOrNull(resolver -> resolver.getStringPattern(prop));
    }

    @Override
    public Boolean getRequired(BeanProperty prop) {
        return firstOrNull(resolver -> resolver.getRequired(prop));
    }
}
