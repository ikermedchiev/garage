package com.balt.garage.service.services.impl;

import com.balt.garage.base.TestBase;
import com.balt.garage.data.models.Car;
import com.balt.garage.data.models.User;
import com.balt.garage.data.models.UserProfile;
import com.balt.garage.data.repositories.CarRepository;
import com.balt.garage.data.repositories.UserProfileRepository;
import com.balt.garage.service.models.CarServiceModel;
import com.balt.garage.service.services.CarService;
import javassist.NotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CarServiceImplTest extends TestBase {

    @MockBean
    CarRepository carRepository;

    @MockBean
    UserProfileRepository userProfileRepository;

    @Autowired
    CarService carService;

    @Test
    void add_whenUserNotFound_shouldThrowNotFoundException() {
        String username = "No user";
        CarServiceModel carServiceModel = new CarServiceModel();
        LocalDateTime time=LocalDateTime.now();

        Mockito.when(userProfileRepository.findByUserUsername(username)).thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> carService.add(carServiceModel, username,time));
    }

    @Test
    void getByUser_whenUserNotFound_shouldThrowNotFoundException() {
        String username = "No user";

        Mockito.when(userProfileRepository.findByUserUsername(username)).thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> carService.getByUser(username));
    }

    @Test
    void getByUser_whenUserFound_shouldReturnCarServiceModel() throws NotFoundException {
        String username="Pesho";
        User user=new User();
        user.setUsername(username);
        UserProfile userProfile=new UserProfile();
        userProfile.setUser(user);
        Car car=new Car();
        car.setBrand("hyndai");

        Car car1=new Car();
        car.setBrand("vw");

        List<Car> cars=new ArrayList<>();
        cars.add(car);
        cars.add(car1);

        userProfile.setCars(cars);
        Mockito.when(userProfileRepository.findByUserUsername(username)).thenReturn(Optional.of(userProfile));
        Mockito.when(carRepository.findAllByUser(userProfile)).thenReturn(cars);


        int size=carService.getByUser(username).size();

        assertEquals(cars.size(),size);



    }

    }


