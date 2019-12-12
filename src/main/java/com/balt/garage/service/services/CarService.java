package com.balt.garage.service.services;

import com.balt.garage.service.models.CarServiceModel;
import javassist.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

public interface CarService {
    boolean add(CarServiceModel car, String username, LocalDateTime time) throws NotFoundException;
    List<CarServiceModel> getByUser(String username) throws NotFoundException;
}
