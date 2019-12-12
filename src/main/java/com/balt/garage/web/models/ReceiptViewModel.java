package com.balt.garage.web.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class ReceiptViewModel {
    private String date;
    private String type;
    private BigDecimal value;
    private String address;
}
