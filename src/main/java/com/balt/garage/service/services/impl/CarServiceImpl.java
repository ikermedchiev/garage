package com.balt.garage.service.services.impl;

import com.balt.garage.data.models.Car;
import com.balt.garage.data.models.Logger;
import com.balt.garage.data.models.UserProfile;
import com.balt.garage.data.repositories.CarRepository;
import com.balt.garage.data.repositories.LoggerRepository;
import com.balt.garage.data.repositories.UserProfileRepository;
import com.balt.garage.service.models.CarServiceModel;
import com.balt.garage.service.services.CarService;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {

    private final ModelMapper modelMapper;
    private final UserProfileRepository userProfileRepository;
    private final CarRepository carRepository;
    private final LoggerRepository loggerRepository;

    @Autowired
    public CarServiceImpl(ModelMapper modelMapper, UserProfileRepository userProfileRepository, CarRepository carRepository, LoggerRepository loggerRepository) {
        this.modelMapper = modelMapper;
        this.userProfileRepository = userProfileRepository;
        this.carRepository = carRepository;
        this.loggerRepository = loggerRepository;
    }


    @Override
    public boolean add(CarServiceModel car, String username, LocalDateTime time) throws NotFoundException {
        if (car == null || username == null) {
            return false;
        }

        Car car1 = this.modelMapper.map(car, Car.class);
        UserProfile userProfile = this.userProfileRepository.findByUserUsername(username).orElseThrow(() -> new NotFoundException("No user found"));
        car1.setUser(userProfile);
        this.carRepository.save(car1);

        Logger logger = new Logger();
        logger.setTime(time);
        logger.setAction("car-add");
        logger.setUser(username);
        this.loggerRepository.save(logger);
        return true;
    }

    @Override
    public List<CarServiceModel> getByUser(String username) throws NotFoundException {
        UserProfile userProfile = this.userProfileRepository.findByUserUsername(username).orElseThrow(() -> new NotFoundException("No garage found"));

        return this.carRepository
                .findAllByUser(userProfile)
                .stream()
                .map(c -> this.modelMapper.map(c, CarServiceModel.class))
                .collect(Collectors.toList());


    }
}
