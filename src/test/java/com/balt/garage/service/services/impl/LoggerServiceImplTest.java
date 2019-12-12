package com.balt.garage.service.services.impl;

import com.balt.garage.base.TestBase;
import com.balt.garage.data.models.Logger;
import com.balt.garage.data.repositories.LoggerRepository;
import com.balt.garage.service.services.LoggerService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.junit.jupiter.api.Assertions.assertEquals;



import java.util.ArrayList;
import java.util.List;

public class LoggerServiceImplTest extends TestBase {

    @MockBean
    LoggerRepository loggerRepository;

    @Autowired
    LoggerService loggerService;


    @Test
    void getAllLogs_whenTwoLogsExists_shouldReturnThem(){
        Logger logger=new Logger();
        logger.setAction("outcome");

        Logger logger1=new Logger();
        logger.setAction("income");

        List<Logger> logs=new ArrayList<>();
        logs.add(logger);
        logs.add(logger1);

        Mockito.when(loggerRepository.findAll()).thenReturn(logs);

        assertEquals(logs.size(),loggerService.getAllLogs().size());
    }


}
