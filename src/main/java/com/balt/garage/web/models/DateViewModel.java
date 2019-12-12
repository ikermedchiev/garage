package com.balt.garage.web.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class DateViewModel {
    private String freeFrom;
    private String freeUntil;
    private BigDecimal price;
}
