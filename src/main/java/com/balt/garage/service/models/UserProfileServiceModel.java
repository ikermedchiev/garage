package com.balt.garage.service.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserProfileServiceModel {
    private String fullName;
    private String phone;
    private List<CarServiceModel> cars;
}
