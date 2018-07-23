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
    public Integer getArrayMaxItems(BeanProperty property) {
        return firstOrNull(property,
                fetchSizeMax()
        );
    }

    @Override
    public Integer getArrayMinItems(BeanProperty property) {
        return firstOrNull(property,
                fetchSizeMin()
        );
    }

    @Override
    public Double getNumberMaximum(BeanProperty property) {
        return firstOrNull(property,
                fetchMax(),
                fetchDecimalMax()
        );
    }

    @Override
    public Double getNumberMinimum(BeanProperty property) {
        return firstOrNull(property,
                fetchMin(),
                fetchDecimalMin()
        );
    }

    @Override
    public Integer getStringMaxLength(BeanProperty property) {
        return firstOrNull(property,
                fetchSizeMax()
        );
    }

    @Override
    public Integer getStringMinLength(BeanProperty property) {
        return firstOrNull(property,
                fetchSizeMin()
        );
    }

    @Override
    public String getStringPattern(BeanProperty property) {
        return firstOrNull(property,
                fetchPatternRegexp()
        );
    }

    @Override
    public Boolean getRequired(BeanProperty property) {
        return firstOrNull(property,
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
                .filter(max -> max != Integer.MAX_VALUE);
    }

    private Function<BeanProperty, Optional<Integer>> fetchSizeMin() {
        return (property) -> getAnnotation(property, Size.class)
                .map(Size::min)
                .filter(min -> min != 0);
    }

    private Function<BeanProperty, Optional<Double>> fetchMin() {
        return (property) -> getAnnotation(property, Min.class)
                .map(Min::value)
                .map(Long::doubleValue);
    }

    private Function<BeanProperty, Optional<Double>> fetchDecimalMin() {
        return (property) -> getAnnotation(property, DecimalMin.class)
                .map(decimalMin -> new BigDecimal(decimalMin.value()).doubleValue());
    }

    private Function<BeanProperty, Optional<Double>> fetchMax() {
        return (property) -> getAnnotation(property, Max.class)
                .map(Max::value)
                .map(Long::doubleValue);
    }

    private Function<BeanProperty, Optional<Double>> fetchDecimalMax() {
        return (property) -> getAnnotation(property, DecimalMax.class)
                .map(decimalMax -> new BigDecimal(decimalMax.value()).doubleValue());
    }

}
