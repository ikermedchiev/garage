package com.balt.garage.web.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GarageViewModel {
    private String id;
    private String town;
    private String address;
    private String laititude;
    private String longtitude;
    private boolean isFree;
    private List<DateViewModel> freeDates;




}
