package com.balt.garage.data.models;

import com.balt.garage.data.models.base.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "receipts")
public class Receipt extends BaseEntity {
    @Column(name = "date", nullable = false)
    private String date;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "value", nullable = false)
    private BigDecimal value;

    @Column(name = "address")
    private String address;

    @Column(name = "car_number")
    private String carNumber;


    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserProfile user;

}
