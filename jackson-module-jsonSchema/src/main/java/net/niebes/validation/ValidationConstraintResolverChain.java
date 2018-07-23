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
    public Integer getArrayMaxItems(BeanProperty property) {
        return firstOrNull(resolver -> resolver.getArrayMaxItems(property));
    }

    @Override
    public Integer getArrayMinItems(BeanProperty property) {
        return firstOrNull(resolver -> resolver.getArrayMinItems(property));
    }

    @Override
    public Double getNumberMaximum(BeanProperty property) {
        return firstOrNull(resolver -> resolver.getNumberMaximum(property));
    }

    @Override
    public Double getNumberMinimum(BeanProperty property) {
        return firstOrNull(resolver -> resolver.getNumberMinimum(property));
    }

    @Override
    public Integer getStringMaxLength(BeanProperty property) {
        return firstOrNull(resolver -> resolver.getStringMaxLength(property));
    }

    @Override
    public Integer getStringMinLength(BeanProperty property) {
        return firstOrNull(resolver -> resolver.getStringMinLength(property));
    }

    @Override
    public String getStringPattern(BeanProperty property) {
        return firstOrNull(resolver -> resolver.getStringPattern(property));
    }

    @Override
    public Boolean getRequired(BeanProperty property) {
        return firstOrNull(resolver -> resolver.getRequired(property));
    }
}
