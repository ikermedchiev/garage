package com.balt.garage.web.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class LoggerViewModel {
    private String id;
    private String action;
    private String user;
    private LocalDateTime time;

}
