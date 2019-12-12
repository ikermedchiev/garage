package com.balt.garage.service.services;

import com.balt.garage.service.models.LoggerServiceModel;
import com.balt.garage.service.models.UserProfileServiceModel;
import com.balt.garage.service.models.UserServiceModel;
import javassist.NotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.time.LocalDateTime;
import java.util.List;

public interface AuthService  extends UserDetailsService {

     void register(UserServiceModel userO, UserProfileServiceModel userP, LocalDateTime time);
     UserProfileServiceModel getUser(String username) throws NotFoundException;


}
