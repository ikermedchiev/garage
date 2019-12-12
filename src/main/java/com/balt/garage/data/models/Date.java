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
@Table(name = "dates")
public class Date extends BaseEntity {

    @Column(name = "free_from")
    private String freeFrom;

    @Column(name = "free_until")
    private String freeUntil;

    @ManyToOne
    @JoinColumn(name = "garage_id", referencedColumnName = "id", nullable = false)
    private Garage garage;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "is_given")
    private boolean isGiven;

}
