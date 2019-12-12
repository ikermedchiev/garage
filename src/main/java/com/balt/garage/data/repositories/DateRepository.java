package com.balt.garage.data.repositories;

import com.balt.garage.data.models.Date;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DateRepository extends JpaRepository<Date, String> {
    Date getByFreeFromAndGarageId(String freeFrom, String id);


}
