package com.balt.garage.service.services;

import com.balt.garage.service.models.DateServiceModel;
import com.balt.garage.service.models.GarageServiceModel;
import com.google.maps.errors.ApiException;
import javassist.NotFoundException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public interface  GarageService {
    void add(GarageServiceModel garage, String username, LocalDateTime time) throws InterruptedException, ApiException, IOException, NotFoundException;
    List<GarageServiceModel> getByUser(String username) throws NotFoundException;
    List<GarageServiceModel> getAll();
    GarageServiceModel getById(String id);
    void lendGarage(String id, DateServiceModel date) throws NotFoundException;
    void borrowGarage(String id, String freeFrom,String username,String carNumber) throws NotFoundException;

}
