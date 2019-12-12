package com.balt.garage.data.repositories;

import com.balt.garage.data.models.Logger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoggerRepository extends JpaRepository<Logger, String> {

}
