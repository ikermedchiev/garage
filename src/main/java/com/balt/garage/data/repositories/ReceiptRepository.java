package com.balt.garage.data.repositories;

import com.balt.garage.data.models.Receipt;
import com.balt.garage.data.models.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, String> {
    List<Receipt> findAllByUser(UserProfile userProfile);

}
