package net.niebes.validation;

import com.fasterxml.jackson.databind.BeanProperty;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Function;

/**
 *
 */
public class JavaxValidationResolver extends AbstractValidationResolver {

    @Override
    public Integer getArrayMaxItems(BeanProperty prop) {
        return firstOrNull(prop,
                fetchSizeMax()
        );
    }

    @Override
    public Integer getArrayMinItems(BeanProperty prop) {
        return firstOrNull(prop,
                fetchSizeMin()
        );
    }

    @Override
    public Double getNumberMaximum(BeanProperty prop) {
        return firstOrNull(prop,
                fetchMax(),
                fetchDecimalMax()
        );
    }

    @Override
    public Double getNumberMinimum(BeanProperty prop) {
        return firstOrNull(prop,
                fetchMin(),
                fetchDecimalMin()
        );
    }

    @Override
    public Integer getStringMaxLength(BeanProperty prop) {
        return firstOrNull(prop,
                fetchSizeMax()
        );
    }

    @Override
    public Integer getStringMinLength(BeanProperty prop) {
        return firstOrNull(prop,
                fetchSizeMin()
        );
    }

    @Override
    public String getStringPattern(BeanProperty prop) {
        return firstOrNull(prop,
                fetchPatternRegexp()
        );
    }

    @Override
    public Boolean getRequired(BeanProperty prop) {
        return firstOrNull(prop,
                hasAnnotation(NotNull.class)
        );
    }

    private Function<BeanProperty, Optional<String>> fetchPatternRegexp() {
        return (property) -> getAnnotation(property, Pattern.class)
                .map(Pattern::regexp);
    }

    private Function<BeanProperty, Optional<Integer>> fetchSizeMax() {
        return (property) -> getAnnotation(property, Size.class)
                .map(Size::max)
                .filter(integer -> integer != Integer.MAX_VALUE);
    }

    private Function<BeanProperty, Optional<Integer>> fetchSizeMin() {
        return (property) -> getAnnotation(property, Size.class)
                .map(Size::min)
                .filter(integer -> integer != 0);
    }

    private Function<BeanProperty, Optional<Double>> fetchMin() {
        return (prop) -> getAnnotation(prop, Min.class)
                .map(Min::value)
                .map(Long::doubleValue);
    }

    private Function<BeanProperty, Optional<Double>> fetchDecimalMin() {
        return (prop) -> getAnnotation(prop, DecimalMin.class)
                .map(decMax -> new BigDecimal(decMax.value()).doubleValue());
    }

    private Function<BeanProperty, Optional<Double>> fetchMax() {
        return (prop) -> getAnnotation(prop, Max.class)
                .map(Max::value)
                .map(Long::doubleValue);
    }

    private Function<BeanProperty, Optional<Double>> fetchDecimalMax() {
        return (prop) -> getAnnotation(prop, DecimalMax.class)
                .map(decMax -> new BigDecimal(decMax.value()).doubleValue());
    }

}
