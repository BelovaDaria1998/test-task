package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.annotation.BidMatches;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@BidMatches
public class QuoteDto {

    @Length(min = 12, max = 12, message = "The length of isin must be 12")
    private String isin;
    private BigDecimal bid;
    private BigDecimal ask;
}
