package com.balt.garage.service.services.impl;

import com.balt.garage.data.models.Logger;
import com.balt.garage.data.repositories.LoggerRepository;
import com.balt.garage.error.LogNotFoundException;
import com.balt.garage.service.models.LoggerServiceModel;
import com.balt.garage.service.services.LoggerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoggerServiceImpl implements LoggerService {

    private final LoggerRepository loggerRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public LoggerServiceImpl(LoggerRepository loggerRepository, ModelMapper modelMapper) {
        this.loggerRepository = loggerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<LoggerServiceModel> getAllLogs() {
        return this.loggerRepository
                .findAll()
                .stream()
                .map(l -> this.modelMapper.map(l, LoggerServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteLog(String id) {
        Logger logger = this.loggerRepository.findById(id).orElseThrow(() -> new LogNotFoundException("No log found"));
        this.loggerRepository.delete(logger);

    }

}
