package com.balt.garage.service.services.impl;

import com.balt.garage.base.TestBase;
import com.balt.garage.data.models.Garage;
import com.balt.garage.data.models.User;
import com.balt.garage.data.models.UserProfile;
import com.balt.garage.data.repositories.GarageRepository;
import com.balt.garage.data.repositories.UserProfileRepository;
import com.balt.garage.service.models.GarageServiceModel;
import com.balt.garage.service.services.GarageService;
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

class GarageServiceImplTest extends TestBase {

    @MockBean
    GarageRepository garageRepository;

    @MockBean
    UserProfileRepository userProfileRepository;

    @Autowired
    GarageService garageService;


    @Test
    void add_whenUserNotFound_shouldThrowNotFoundException() {
        String username = "No user";
        GarageServiceModel garageServiceModel = new GarageServiceModel();
        LocalDateTime time=LocalDateTime.now();

        Mockito.when(userProfileRepository.findByUserUsername(username)).thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> garageService.add(garageServiceModel, username,time));
    }

    @Test
    void getByUser_whenUserNotFound_shouldThrowNotFoundException() {
        String username = "No user";

        Mockito.when(userProfileRepository.findByUserUsername(username)).thenReturn(Optional.empty());

        assertThrows(
                UsernameNotFoundException.class,
                () -> garageService.getByUser(username));
    }

    @Test
    void getByUser_whenUserFound_shouldReturnGarageServiceModel() throws NotFoundException {
        String username="Pesho";
        User user=new User();
        user.setUsername(username);
        UserProfile userProfile=new UserProfile();
        userProfile.setUser(user);
        Garage garage=new Garage();
        garage.setAddress("sofia");

        Garage garage1=new Garage();
        garage1.setAddress("goce Delchev");

        List<Garage> garages=new ArrayList<>();
        garages.add(garage);
        garages.add(garage1);

        userProfile.setGarages(garages);
        Mockito.when(userProfileRepository.findByUserUsername(username)).thenReturn(Optional.of(userProfile));
        Mockito.when(garageRepository.findAllByUser(userProfile)).thenReturn(garages);


        int size=garageService.getByUser(username).size();

        assertEquals(garages.size(),size);

    }

    @Test
    void getAll_whenTwoGaragesExists_shouldReturnThem(){
         Garage garage=new Garage();
        garage.setFree(true);

        Garage garage1=new Garage();
        garage1.setFree(true);

        List<Garage> garages=new ArrayList<>();
        garages.add(garage);
        garages.add(garage1);

        Mockito.when(garageRepository.findAll()).thenReturn(garages);

        assertEquals(garages.size(),garageService.getAll().size());
    }

    @Test
    void getById_whenGarageExists_shouldReturnGarageServiceModel(){
        String id="id";
        Garage garage=new Garage();
        garage.setAddress("Sofia");
        garage.setId(id);

        Mockito.when(garageRepository.getOne(id)).thenReturn(garage);

        assertEquals(garage.getAddress(),garageService.getById(id).getAddress());




    }


}