package com.balt.garage.service.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class LoggerServiceModel {
    private String id;
    private String action;
    private String user;
    private LocalDateTime time;

}
