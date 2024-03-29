package com.balt.garage.web.base;

import com.balt.garage.base.TestBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
public class ViewTestBase extends TestBase {
    @Autowired
    protected MockMvc mockMvc;
}