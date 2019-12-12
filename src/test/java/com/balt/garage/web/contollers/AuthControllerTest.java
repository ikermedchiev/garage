package com.balt.garage.web.contollers;

import com.balt.garage.web.base.ViewTestBase;
import org.junit.jupiter.api.Test;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class AuthControllerTest extends ViewTestBase {
    @Test
    void login_whenUserNotLogged_shouldReturnView() throws Exception {
        mockMvc.perform(get("/login")).andExpect(status().isOk())
                .andExpect(view().name("/auth/login"));

    }
    @Test
    void register_whenUserNotLogged_shouldReturnView() throws Exception {
        mockMvc.perform(get("/register")).andExpect(status().isOk())
                .andExpect(view().name("/auth/register"));

    }


}