package com.balt.garage.service.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class ReceiptServiceModel {
    private String date;
    private String type;
    private BigDecimal value;
    private String address;
    private String carNumber;
}
