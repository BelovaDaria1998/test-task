package org.example.validator;

import org.example.annotation.BidMatches;
import org.example.dto.QuoteDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class BidValidator implements ConstraintValidator<BidMatches, QuoteDto> {

    @Override
    public boolean isValid(QuoteDto quoteDto, ConstraintValidatorContext constraintValidatorContext) {
        return quoteDto.getAsk() != null
        && (quoteDto.getBid() == null
                || quoteDto.getBid().compareTo(quoteDto.getAsk()) < 0);
    }
}
