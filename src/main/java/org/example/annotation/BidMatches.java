package org.example.annotation;

import org.example.validator.BidValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = {BidValidator.class}
)
public @interface BidMatches {
    String message() default "Bid is not valid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}