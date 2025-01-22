package org.example.dto;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class EnergyLevelDto {

    private BigDecimal value;
    private String isin;
}
