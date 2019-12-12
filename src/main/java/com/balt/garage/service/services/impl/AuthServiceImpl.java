package com.balt.garage.service.services.impl;

import com.balt.garage.data.models.Logger;
import com.balt.garage.data.models.Role;
import com.balt.garage.data.models.User;
import com.balt.garage.data.models.UserProfile;
import com.balt.garage.data.repositories.LoggerRepository;
import com.balt.garage.data.repositories.RoleRepository;
import com.balt.garage.data.repositories.UserProfileRepository;
import com.balt.garage.data.repositories.UserRepository;
import com.balt.garage.service.models.LoggerServiceModel;
import com.balt.garage.service.models.UserProfileServiceModel;
import com.balt.garage.service.models.UserServiceModel;
import com.balt.garage.service.services.AuthService;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final ModelMapper modelMapper;
    private final RoleRepository roleRepository;
    private final LoggerRepository loggerRepository;

    @Autowired
    public AuthServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository, UserProfileRepository userProfileRepository, ModelMapper modelMapper, RoleRepository roleRepository, LoggerRepository loggerRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
        this.userProfileRepository = userProfileRepository;
        this.modelMapper = modelMapper;
        this.roleRepository = roleRepository;
        this.loggerRepository = loggerRepository;
    }

    @Override
    public void register(UserServiceModel userServiceModel, UserProfileServiceModel userProfileServiceModel, LocalDateTime time) {
        userServiceModel.setPassword(this.bCryptPasswordEncoder.encode(userServiceModel.getPassword()));

        User user = this.modelMapper.map(userServiceModel, User.class);
        UserProfile userProfile = this.modelMapper.map(userProfileServiceModel, UserProfile.class);
        user.setRoles(getRolesForRegistration());
        user.setUserProfile(userProfile);
        this.userRepository.saveAndFlush(user);
        this.userProfileRepository.saveAndFlush(userProfile);


        Logger logger = new Logger();
        logger.setTime(time);
        logger.setAction("user-register");
        logger.setUser(user.getUsername());
        this.loggerRepository.save(logger);
    }

    @Override
    public UserProfileServiceModel getUser(String username) throws NotFoundException {
        UserProfile userProfile = this.userProfileRepository.findByUserUsername(username).orElseThrow(() -> new UsernameNotFoundException("No user found"));

        return this.modelMapper
                .map(userProfile, UserProfileServiceModel.class);
    }


    private Set<Role> getRolesForRegistration() {
        Set<Role> roles = new HashSet<>();

        if (this.userRepository.count() == 0) {
            roles.add(this.roleRepository.findByName("ADMIN"));
        } else {
            roles.add(this.roleRepository.findByName("USER"));

        }

        return roles;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        return this.userRepository
                .findByUsername(s)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }
}
