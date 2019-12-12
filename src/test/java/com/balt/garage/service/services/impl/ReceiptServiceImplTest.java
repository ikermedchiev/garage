package com.balt.garage.service.services.impl;

import com.balt.garage.base.TestBase;
import com.balt.garage.data.models.*;
import com.balt.garage.data.repositories.ReceiptRepository;
import com.balt.garage.data.repositories.UserProfileRepository;
import com.balt.garage.service.services.ReceiptService;
import javassist.NotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ReceiptServiceImplTest extends TestBase {

    @MockBean
    ReceiptRepository receiptRepository;

    @MockBean
    UserProfileRepository userProfileRepository;

    @Autowired
    ReceiptService receiptService;


    @Test
    void getReceipt_whenUserDoesNotExists_shouldThrowNotFoundException() {
        String username = "No user";

        Mockito.when(userProfileRepository.findByUserUsername(username)).thenReturn(Optional.empty());

        assertThrows(
                UsernameNotFoundException.class,
                () -> receiptService.getReceipts(username));
    }

    @Test
    void getReceipts_whenTwoLogsExists_shouldReturnThem() throws NotFoundException {
        String username="Pesho";
        User user=new User();
        user.setUsername(username);
        UserProfile userProfile=new UserProfile();
        userProfile.setUser(user);
        Receipt receipt=new Receipt();
        receipt.setType("outcome");

        Receipt receipt1=new Receipt();
        receipt.setType("income");

        List<Receipt> receipts=new ArrayList<>();
        receipts.add(receipt);
        receipts.add(receipt1);

        userProfile.setReceipts(receipts);
        Mockito.when(userProfileRepository.findByUserUsername(username)).thenReturn(Optional.of(userProfile));
        Mockito.when(receiptRepository.findAllByUser(userProfile)).thenReturn(receipts);


        int size=receiptService.getReceipts(username).size();

        assertEquals(receipts.size(),size);



    }



}