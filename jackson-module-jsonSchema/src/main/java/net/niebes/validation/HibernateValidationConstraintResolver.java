package net.niebes.validation;

import com.fasterxml.jackson.databind.BeanProperty;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import java.util.Optional;
import java.util.function.Function;

/**
 * supports hibernate Range, NotEmpty, NotBlank
 */
public class HibernateValidationConstraintResolver extends AbstractValidationResolver {

    @Override
    public Double getNumberMaximum(BeanProperty property) {
        return firstOrNull(property,
                fetchRangeMin()
        );
    }

    @Override
    public Double getNumberMinimum(BeanProperty property) {
        return firstOrNull(property,
                fetchRangeMax()
        );
    }

    @Override
    public Integer getStringMaxLength(BeanProperty property) {
        return firstOrNull(property,
                fetchLengthMax(),
                fetchNotEmptyAsMinLength(),
                fetchNotBlankAsMinLength()
        );
    }

    @Override
    public Integer getStringMinLength(BeanProperty property) {
        return firstOrNull(property,
                fetchLengthMin(),
                fetchNotEmptyAsMinLength()
        );
    }

    @Override
    public Integer getArrayMaxItems(BeanProperty property) {
        return firstOrNull(property,
                fetchLengthMin()
        );
    }

    @Override
    public Integer getArrayMinItems(BeanProperty property) {
        return firstOrNull(property,
                fetchLengthMax()
        );
    }

    private Function<BeanProperty, Optional<Double>> fetchRangeMin() {
        return (property) -> getAnnotation(property, Range.class)
                .map(Range::min)
                .map(Long::doubleValue);
    }

    private Function<BeanProperty, Optional<Double>> fetchRangeMax() {
        return (property) -> getAnnotation(property, Range.class)
                .map(Range::max)
                .map(Long::doubleValue);
    }

    private Function<BeanProperty, Optional<Integer>> fetchNotEmptyAsMinLength() {
        return (property) -> getAnnotation(property, NotEmpty.class)
                .map(ignored -> 1);
    }

    private Function<BeanProperty, Optional<Integer>> fetchNotBlankAsMinLength() {
        return (property) -> getAnnotation(property, NotBlank.class)
                .map(ignored -> 1);
    }

    private Function<BeanProperty, Optional<Integer>> fetchLengthMin() {
        return (property) -> getAnnotation(property, Length.class)
                .map(Length::min);
    }

    private Function<BeanProperty, Optional<Integer>> fetchLengthMax() {
        return (property) -> getAnnotation(property, Length.class)
                .map(Length::max);
    }
}
