package com.balt.garage.web.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CarViewModel {
    private String brand;
    private String model;
    private String carNumber;
    private String colour;
}
