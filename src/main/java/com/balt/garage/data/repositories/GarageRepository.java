package com.balt.garage.data.repositories;

import com.balt.garage.data.models.Garage;
import com.balt.garage.data.models.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GarageRepository extends JpaRepository<Garage, String> {
    List<Garage> findAllByUser(UserProfile userProfile);

}
