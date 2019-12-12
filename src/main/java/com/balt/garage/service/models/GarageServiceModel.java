package com.balt.garage.service.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GarageServiceModel {
    private String id;
    private String town;
    private String address;
    private String laititude;
    private String longtitude;
    private boolean isFree;
    private List<DateServiceModel> freeDates;



}
