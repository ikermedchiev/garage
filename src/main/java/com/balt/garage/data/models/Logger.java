package com.balt.garage.data.models;


import com.balt.garage.data.models.base.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@Entity
public class Logger extends BaseEntity {

    @Column(name = "action")
    private String action;
    @Column(name = "user")
    private String user;
    @Column(name = "time")
    private LocalDateTime time;


}
