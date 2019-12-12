package com.balt.garage.service.services.impl;

import com.balt.garage.base.TestBase;
import com.balt.garage.data.repositories.LoggerRepository;
import com.balt.garage.data.repositories.UserProfileRepository;
import com.balt.garage.data.repositories.UserRepository;
import com.balt.garage.service.services.AuthService;
import javassist.NotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

class AuthServiceImplTest extends TestBase {

    @MockBean
    UserRepository userRepository;

    @MockBean
    UserProfileRepository userProfileRepository;

    @MockBean
    LoggerRepository loggerRepository;

    @Autowired
    AuthService authService;

    @Test
    void getUser_whenUserDoesNotExists_shouldThrowNotFoundException() {
        String username = "No user";

        Mockito.when(userProfileRepository.findByUserUsername(username)).thenReturn(Optional.empty());

        assertThrows(
                UsernameNotFoundException.class,
                () -> authService.getUser(username));
    }



}

