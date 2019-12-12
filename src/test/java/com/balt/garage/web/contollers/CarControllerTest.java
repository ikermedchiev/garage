package com.balt.garage.web.contollers;

import com.balt.garage.data.repositories.CarRepository;
import com.balt.garage.data.repositories.UserProfileRepository;
import com.balt.garage.web.base.ViewTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class CarControllerTest extends ViewTestBase {

    @Test
    @WithMockUser(roles = "USER")
    void addCar_whenUserLogged_shouldReturnView() throws Exception {
        mockMvc.perform(get("/add-car")).andExpect(status().isOk())
                .andExpect(view().name("/cars/add-car"));

    }
    @Test
    @WithMockUser(roles = "ADMIN")
    void addCar_whenAdminLogged_shouldReturnView() throws Exception {
        mockMvc.perform(get("/add-car")).andExpect(status().isOk())
                .andExpect(view().name("/cars/add-car"));

    }
}