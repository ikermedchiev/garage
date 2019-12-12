package com.balt.garage.config;

import com.balt.garage.data.models.Role;
import com.balt.garage.data.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DataBaseSeed {
    private final RoleRepository RoleRepository;

    @Autowired
    public DataBaseSeed(com.balt.garage.data.repositories.RoleRepository roleRepository) {
        RoleRepository = roleRepository;
    }

    @PostConstruct
    public void seed() {
        if (this.RoleRepository.findAll().isEmpty()) {
            Role userRole = new Role();
            userRole.setName("USER");

            Role adminRole = new Role();
            adminRole.setName("ADMIN");

            this.RoleRepository.save(userRole);
            this.RoleRepository.save(adminRole);
        }
    }

}
