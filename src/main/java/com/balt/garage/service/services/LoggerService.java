package com.balt.garage.service.services;

import com.balt.garage.service.models.LoggerServiceModel;

import java.util.List;

public interface LoggerService {
    List<LoggerServiceModel> getAllLogs();
    void deleteLog(String id);
}
