package com.balt.garage.service.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class DateServiceModel {
    private String freeFrom;
    private String freeUntil;
    private BigDecimal price;
}
