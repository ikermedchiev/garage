package com.balt.garage.data.repositories;

import com.balt.garage.data.models.Car;
import com.balt.garage.data.models.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, String> {
    List<Car> findAllByUser(UserProfile userProfile);
}
