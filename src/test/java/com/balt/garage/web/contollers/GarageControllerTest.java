package com.balt.garage.web.contollers;

import com.balt.garage.web.base.ViewTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class GarageControllerTest extends ViewTestBase {
    @Test
    @WithMockUser(roles = "USER")
    void addGarage_whenUserLogged_shouldReturnView() throws Exception {
        mockMvc.perform(get("/add-garage")).andExpect(status().isOk())
                .andExpect(view().name("/garages/add-garage"));

    }
    @Test
    @WithMockUser(roles = "ADMIN")
    void addGarage_whenAdminLogged_shouldReturnView() throws Exception {
        mockMvc.perform(get("/add-garage")).andExpect(status().isOk())
                .andExpect(view().name("/garages/add-garage"));

    }
}